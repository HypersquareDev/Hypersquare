package hypersquare.hypersquare.play.execution;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.google.gson.JsonObject;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.Actions;
import hypersquare.hypersquare.dev.target.Target;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.action.CallbackAfterAction;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.codefile.data.CodeData;
import hypersquare.hypersquare.dev.codefile.data.CodeLineData;
import hypersquare.hypersquare.dev.target.TargetPriority;
import hypersquare.hypersquare.dev.target.TargetSet;
import hypersquare.hypersquare.dev.target.TargetType;
import hypersquare.hypersquare.item.event.Event;
import hypersquare.hypersquare.play.ActionArguments;
import hypersquare.hypersquare.play.error.HSException;
import hypersquare.hypersquare.play.error.CodeErrorType;
import hypersquare.hypersquare.play.CodeSelection;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CodeExecutor {
    public final static int RUNNING_LIMIT = 100;

    public final int plotId;
    public SetMultimap<Event, BukkitRunnable> running = MultimapBuilder.hashKeys().hashSetValues().build();

    public CodeExecutor(int plotId) {
        this.plotId = plotId;
    }

    public void cancel() {
        for (var r : running.values()) r.cancel();
        running.clear();
    }

    public <T extends org.bukkit.event.Event> void trigger(Event event, T bukkitEvent, CodeSelection selection) {
        if (running.size() >= RUNNING_LIMIT) throw new HSException(CodeErrorType.RUN_LIMIT, null);
        if (event == null) throw new HSException(CodeErrorType.INVALID_EVENT, null);
        CodeData data = new CodeFile(plotId).getCodeData();
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                for (CodeLineData line : data.codelines) {
                    if (!line.event.equals(event.getId()) || !line.type.equals(event.getCodeblockId())) continue;
                    // run every line that has the same event
                    CodeStacktrace trace = new CodeStacktrace(event, bukkitEvent, new CodeStacktrace.Frame(line.actions, selection));
                    try { continueEval(trace); } catch (Exception e) {
                        new HSException(plotId, CodeErrorType.RUNTIME_ERROR, e).sendMessage();
                    }
                }
                running.remove(event, this);
            }
        };
        task.runTask(Hypersquare.instance);
        running.put(event, task);
    }

    public void continueEval(@NotNull CodeStacktrace trace) {
        while (true) {
            // TODO: Check world tick time
            // (urgent)

            // NOTE: Temporary server TPS check removed as it uses spigot timings which is marked for removal
            // (we are using spark in prod server)
                /*
                if (Bukkit.getServer().getAverageTickTime() <= 17) {
                    CodeError.sendError(plotId, CodeErrorType.LOW_MSPT);
                    cancelEval = true;
                    break;
                }
                */
            if (trace.isDone()) break;
            CodeStacktrace.Frame frame = trace.next();
            if (frame == null) break;
            CodeActionData data = frame.next();

            if (data == null) {
                trace.popFrame();
                if (trace.isDone()) break;
                frame = trace.next();
                data = frame.current();

                Actions action = Actions.getAction(data.action, data.codeblock);
                if (action != null && action.a instanceof CallbackAfterAction cb) {
                    execute(cb::after, action, trace, frame, data);
                }
            }
            else {
                Action action = Actions.getAction(data.action, data.codeblock);
                if (action == null) {
                    throw new HSException(CodeErrorType.INVALID_ACT, new NullPointerException("CodeActionData is invalid?"));
                }
                execute(action::execute, action, trace, frame, data);
            }
            if (trace.cancel) break;
        }
    }

    private void execute(RunFunction run, Action action, CodeStacktrace trace, CodeStacktrace.Frame frame, CodeActionData data) {
        ExecutionContext ctx;
        try { ctx = getCtx(action, trace, data); } catch (Exception e) {
            throw new HSException(CodeErrorType.FAILED_CONTEXT, e);
        }
        CodeSelection targetSel;
        try { targetSel = getTargetSel(data.target, action, trace, frame.selection); } catch (Exception e) {
            throw new HSException(CodeErrorType.FAILED_TARGET, e);
        }
        run.apply(ctx, targetSel);
    }

    private CodeSelection getTargetSel(String target, Action action, CodeStacktrace trace, CodeSelection selection) {
        CodeSelection targetSel = null;
        if (target == null) {
            String codeblock = action.getCodeblockId();
            TargetType type = TargetType.ofCodeblock(codeblock);
            if (type == null) return selection; // codeblock can't be targeted, preserve original selection
            TargetSet set = TargetPriority.ofType(type);
            for (Target t : set.targets()) {
                try { targetSel = t.get(trace.bukkitEvent, selection); } catch (Exception ignored) {}
            }
            if (targetSel == null) throw new NullPointerException("Couldn't find any target prioritization for " + codeblock);
        }
        else targetSel = Objects.requireNonNull(Target.getTarget(target)).get(trace.bukkitEvent, selection);
        return targetSel;
    }

    private @NotNull ExecutionContext getCtx(@NotNull Action action, CodeStacktrace trace, CodeActionData data) {
        HashMap<String, List<JsonObject>> arguments = new HashMap<>();
        for (Action.ActionParameter param : action.parameters()) {
            List<JsonObject> args = data.arguments.getOrDefault(param.id(), List.of());
            if (args.isEmpty() && !param.optional()) throw new HSException(CodeErrorType.MISSING_PARAM, null);
            arguments.put(param.id(), args);
        }
        ActionArguments args = new ActionArguments(arguments);
        ExecutionContext ctx = new ExecutionContext(this, args, trace, action, data);
        args.bind(ctx);
        return ctx;
    }
}

interface RunFunction {
    void apply(ExecutionContext ctx, CodeSelection targetSel);
}
