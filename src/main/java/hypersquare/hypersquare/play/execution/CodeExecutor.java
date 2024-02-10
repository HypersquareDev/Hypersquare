package hypersquare.hypersquare.play.execution;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.google.gson.JsonObject;
import hypersquare.hypersquare.dev.Actions;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.action.CallbackAfterAction;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.codefile.data.CodeData;
import hypersquare.hypersquare.dev.codefile.data.CodeLineData;
import hypersquare.hypersquare.item.event.Event;
import hypersquare.hypersquare.play.ActionArguments;
import hypersquare.hypersquare.play.CodeError;
import hypersquare.hypersquare.play.CodeErrorType;
import hypersquare.hypersquare.play.CodeSelection;
import hypersquare.hypersquare.play.execution.CodeStacktrace.Frame;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
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

    public void trigger(Event event, CodeSelection selection) {
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
                CodeStacktrace trace = new CodeStacktrace(plotId, new CodeStacktrace.Frame(line.actions, selection));
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
                        ExecutionContext ctx = getCtx(action, trace, data, plotId, frame);
                        if (ctx == null) {
                            cancelEval = true;
                            break;
                        }
                        cb.after(ctx);
                    }
                    continue;
                }

                Action action = Actions.getAction(data.action, data.codeblock);
                if (action == null) {
                    CodeError.sendError(plotId, CodeErrorType.INVALID_ACT);
                    cancelEval = true;
                    break;
                }

                ExecutionContext ctx = getCtx(action, trace, data, plotId, frame);
                if (ctx == null) {
                    cancelEval = true;
                    break;
                }
                action.execute(ctx);
            }
        } catch (Exception err) {
            err.printStackTrace();
            cancelEval = true;
            CodeError.sendError(plotId, CodeErrorType.INTERNAL_ERROR);
        }
        return cancelEval;
    }

    private ExecutionContext getCtx(Action action, CodeStacktrace trace, CodeActionData data, int plotId, Frame frame) {
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
        ExecutionContext ctx = new ExecutionContext(frame.selection, args, trace, data.actions, action, data);
        args.bind(ctx);
        return ctx;
    }
}
