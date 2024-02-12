package hypersquare.hypersquare.play.execution;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.google.gson.JsonObject;
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
import hypersquare.hypersquare.play.CodeError;
import hypersquare.hypersquare.play.CodeErrorType;
import hypersquare.hypersquare.play.CodeSelection;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class CodeExecutor {
    public final static int RUNNING_LIMIT = 100;
    public final int plotId;
    public SetMultimap<Event, CompletableFuture<Void>> running = MultimapBuilder.hashKeys().hashSetValues().build();

    public CodeExecutor(int plotId) {
        this.plotId = plotId;
    }

    public void cancel() {
        for (var r : running.values()) r.cancel(false);
        running = MultimapBuilder.hashKeys().hashSetValues().build();
    }

    public <T extends org.bukkit.event.Event> void trigger(Event event, T bukkitEvent, CodeSelection selection) {
        if (running.size() >= RUNNING_LIMIT) {
            CodeError.sendError(plotId, CodeErrorType.RUN_LIMIT);
            return;
        }
        if (event == null) {
            CodeError.sendError(plotId, CodeErrorType.INVALID_EVENT);
            return;
        }
        CodeData data = new CodeFile(plotId).getCodeData();
        var completable = CompletableFuture.runAsync(() -> {
            for (CodeLineData line : data.codelines) {
                if (!line.event.equals(event.getId()) || !line.type.equals(event.getCodeblockId())) continue;
                // run every line that has the same event
                CodeStacktrace trace = new CodeStacktrace(event, bukkitEvent, new CodeStacktrace.Frame(line.actions, selection));
                if (continueEval(plotId, trace)) break;
            }
        }, Executors.newSingleThreadExecutor());
        running.put(event, completable);
        completable.thenRun(() -> running.remove(event, completable));
    }

    private boolean continueEval(int plotId, CodeStacktrace trace) {
        boolean cancelEval = false;
        try {
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
                    if (frame == null) break;
                    data = frame.current();

                    Actions action = Actions.getAction(data.action, data.codeblock);
                    if (action != null && action.a instanceof CallbackAfterAction cb) {
                        if (execute(cb::after, action, trace, frame, data, plotId)) {
                            cancelEval = true;
                            break;
                        }
                    }
                    continue;
                }

                Action action = Actions.getAction(data.action, data.codeblock);
                if (action == null) {
                    CodeError.sendError(plotId, CodeErrorType.INVALID_ACT);
                    cancelEval = true;
                    break;
                }
                if (execute(action::execute, action, trace, frame, data, plotId)) {
                    cancelEval = true;
                    break;
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
            cancelEval = true;
            CodeError.sendError(plotId, CodeErrorType.INTERNAL_ERROR);
        }
        return cancelEval;
    }

    private boolean execute(RunFunction run, Action action, CodeStacktrace trace, CodeStacktrace.Frame frame, CodeActionData data, int plotId) {
        ExecutionContext ctx = getCtx(action, trace, data, plotId);
        if (ctx == null) return true;
        CodeSelection targetSel = null;
        if (data.target == null) {
            TargetType targetType = TargetType.ofEvent(trace.bukkitEvent);
            if (targetType == null) {
                CodeError.sendError(plotId, CodeErrorType.FAILED_TARGET);
                return true;
            }
            else {
                TargetSet set = TargetPriority.ofType(targetType);
                for (Target t : set.targets()) {
                    try { targetSel = t.get(trace.bukkitEvent, frame.selection); } catch (Exception ignored) {
                        CodeError.sendError(plotId, CodeErrorType.FAILED_TARGET);
                        return true;
                    }
                }
            }
        }
        else {
            try {
                targetSel = Objects.requireNonNull(Target.getTarget(data.target)).get(trace.bukkitEvent, frame.selection);
            } catch (Exception ignored) {
                CodeError.sendError(plotId, CodeErrorType.FAILED_TARGET);
                return true;
            }
        }
        if (targetSel == null) {
            CodeError.sendError(plotId, CodeErrorType.FAILED_TARGET);
            return true;
        }
        run.apply(ctx, targetSel);
        return false;
    }

    private ExecutionContext getCtx(Action action, CodeStacktrace trace, CodeActionData data, int plotId) {
        HashMap<String, List<JsonObject>> arguments = new HashMap<>();
        for (Action.ActionParameter param : action.parameters()) {
            List<JsonObject> args = data.arguments.getOrDefault(param.id(), List.of());
            if (args.isEmpty() && !param.optional()) {
                CodeError.sendError(plotId, CodeErrorType.MISSING_PARAM);
                return null;
            }
            arguments.put(param.id(), args);
        }
        ActionArguments args = new ActionArguments(arguments);
        ExecutionContext ctx = new ExecutionContext(args, trace, data.actions, action, data);
        args.bind(ctx);
        return ctx;
    }
}

interface RunFunction {
    void apply(ExecutionContext ctx, CodeSelection targetSel);
}
