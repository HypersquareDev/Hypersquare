package hypersquare.hypersquare.play;

import com.google.gson.JsonObject;

import java.util.HashMap;

public class CodeVariableScope {

    private final HashMap<String, JsonObject> values = new HashMap<>();

    public void set(String name, JsonObject value) {
        values.put(name, value);
    }

    public JsonObject get(String name) {
        return values.get(name);
    }
}
