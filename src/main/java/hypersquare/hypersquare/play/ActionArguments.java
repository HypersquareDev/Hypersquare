package hypersquare.hypersquare.play;

import com.google.gson.JsonObject;
import hypersquare.hypersquare.dev.value.CodeValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({"unchecked"})
public class ActionArguments {

    private final HashMap<String, List<JsonObject>> values;
    private ExecutionContext ctx;

    public ActionArguments(HashMap<String, List<JsonObject>> values) {
        this.values = values;
    }

    public void bind(ExecutionContext ctx) {
        this.ctx = ctx;
    }

    public <T> List<T> allNonNull(String id) {
        List<T> ret = new ArrayList<>();
        for (Object o : this.allNullable(id)) {
            if (o != null) ret.add((T) o);
        }
        return ret;
    }

    public <T> List<T> allNullable(String id) {
        List<T> ret = new ArrayList<>();
        for (JsonObject o : values.get(id)) {
            ret.add(getAs(o, ctx.action().getParameter(id).type().codeVal));
        }
        return ret;
    }

    public <T> T single(String id) {
        return values.get(id).getFirst() == null ? null
                : (T) getAs(values.get(id).getFirst(), ctx.action().getParameter(id).type().codeVal);
    }

    public boolean has(String id) {
        return values.containsKey(id) && !values.get(id).isEmpty();
    }

    public <T> T getOr(String id, T def) {
        return has(id) ? single(id) : def;
    }

    private <T> T getAs(JsonObject o, CodeValues target) {
        if (!o.has("type")) return null;
        if (CodeValues.VARIABLE.isType(o)) {
            CodeVariable var = (CodeVariable) CodeValues.VARIABLE.realValue(CodeValues.VARIABLE.fromJson(o));
            var.bind(ctx);
            if (target != null && target.getTypeId().equals(CodeValues.VARIABLE.getTypeId())) {
                return (T) var;
            }
            return var.get(target);
        }
        if (target == null) {
            target = CodeValues.getType(o);
        }
        if (!target.isType(o)) {
            T ret = (T) target.realValue(target.coerce(o));
            if (ret instanceof CodeVariable v) v.bind(ctx);
            return ret;
        }
        T ret = (T) target.realValue(target.fromJson(o));
        if (ret instanceof CodeVariable v) v.bind(ctx);
        return ret;
    }
}
