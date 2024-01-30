package hypersquare.hypersquare.play;

import com.google.gson.JsonObject;
import hypersquare.hypersquare.dev.Actions;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.action.CallbackAfterAction;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.codefile.data.CodeData;
import hypersquare.hypersquare.dev.codefile.data.CodeLineData;
import hypersquare.hypersquare.item.event.Event;
import hypersquare.hypersquare.play.CodeStacktrace.Frame;
import hypersquare.hypersquare.util.Utilities;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class CodeExecutor {

    public static void trigger(Player player, Event event, CodeSelection selection) {
        trigger(Utilities.getPlotID(player.getWorld()), event, selection);
    }
    public static void trigger(int plotId, Event event, CodeSelection selection) {
        if (event == null) {
            CodeError.sendError(plotId, CodeErrorType.INVALID_EVENT);
            return;
        }

        CodeData data = new CodeFile(plotId).getCodeData();
        for (CodeLineData line : data.codelines) {
            if (!(line.event.equals(event.getId()) && line.type.equals(event.getCodeblockId()))) continue;

            CodeStacktrace trace = new CodeStacktrace(plotId, new CodeStacktrace.Frame(line.actions, selection));
            continueEval(plotId, trace);
        }
    }

    private static void continueEval(int plotId, CodeStacktrace trace) {
        try {
            long startTime = System.currentTimeMillis();
            while (true) {
                if (System.currentTimeMillis() - startTime > 1000) {
                    CodeError.sendError(plotId, CodeErrorType.LOW_MSPT); // TODO use actually mspt
                    break;
                }
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
                    if (action.a instanceof CallbackAfterAction cb) {
                        ExecutionContext ctx = getCtx(action, trace, data, plotId, frame);
                        if (ctx == null) break;
                        cb.after(ctx);
                    }
                    continue;
                }

                Action action = Actions.getAction(data.action, data.codeblock);
                if (action == null) {
                    CodeError.sendError(plotId, CodeErrorType.INVALID_ACT);
                    break;
                }

                ExecutionContext ctx = getCtx(action, trace, data, plotId, frame);
                if (ctx == null) break;
                action.execute(ctx);
            }
        } catch (Exception err) {
            err.printStackTrace();
            CodeError.sendError(plotId, CodeErrorType.INTERNAL_ERROR);
        }
    }
    private static ExecutionContext getCtx(Action action, CodeStacktrace trace, CodeActionData data, int plotId, Frame frame) {
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
