package hypersquare.hypersquare.play;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActionArguments {

    private final HashMap<String, List<Object>> values;

    public ActionArguments(HashMap<String, List<Object>> values) {
        this.values = values;
    }

    @SuppressWarnings("unchecked")
    public <T> T single(String id) {
        return (T) values.get(id).get(0);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> allNullable(String id) {
        return (List<T>) values.get(id);
    }

    public <T> List<T> allNonNull(String id) {
        List<T> ret = new ArrayList<>();
        for (T o : this.<T>allNullable(id)) {
            if (o != null) ret.add(o);
        }
        return ret;
    }

    public boolean has(String id) {
        return values.containsKey(id) && !values.get(id).isEmpty();
    }

    public <T> T getOr(String id, T def) {
        return has(id) ? single(id) : def;
    }
}
