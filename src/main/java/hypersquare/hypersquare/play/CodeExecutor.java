package hypersquare.hypersquare.play;

import com.google.gson.JsonObject;
import hypersquare.hypersquare.dev.Actions;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.codefile.data.CodeData;
import hypersquare.hypersquare.dev.codefile.data.CodeLineData;
import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.item.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CodeExecutor {

    public static void trigger(int plotId, Event event, CodeSelection selection) {
        CodeData data = new CodeFile(plotId).getCodeData();
        for (CodeLineData line : data.codelines) {
            if (!line.event.equals(event.getId())) continue;

            CodeStacktrace trace = new CodeStacktrace(plotId, new CodeStacktrace.Frame(line.actions, selection));
            continueEval(plotId, trace);
        }
    }

    private static void continueEval(int plotId, CodeStacktrace trace) {
        try {
            eval: while (true) {
                CodeStacktrace.Frame frame = trace.next();
                if (frame == null) break;
                CodeActionData data = frame.next();
                if (data == null) {
                    trace.popFrame();
                    return;
                }

                Action action = Actions.getAction(data.action);
                if (action == null) {
                    CodeError.sendError(plotId, CodeErrorType.INVALID_ACT);
                    break;
                }
                HashMap<String, List<Object>> arguments = new HashMap<>();
                for (Action.ActionParameter param : action.parameters()) {
                    List<JsonObject> args = data.arguments.getOrDefault(param.id(), List.of());
                    if (args.isEmpty() && !param.optional()) {
                        CodeError.sendError(plotId, CodeErrorType.MISSING_PARAM);
                        break eval;
                    }
                    List<Object> values = new ArrayList<>();
                    for (JsonObject arg : args) {
                        CodeValues t = CodeValues.getType(arg);
                        if (t == null) {
                            CodeError.sendError(plotId, CodeErrorType.INVALID_PARAM);
                            break eval;
                        }
                        values.add(t.realValue(t.fromJson(arg)));
                    }
                    arguments.put(param.id(), values);
                }
                action.execute(frame.selection, new ActionArguments(arguments));
            }
        } catch (Exception err) {
            err.printStackTrace();
            CodeError.sendError(plotId, CodeErrorType.INTERNAL_ERROR);
        }
    }

}
