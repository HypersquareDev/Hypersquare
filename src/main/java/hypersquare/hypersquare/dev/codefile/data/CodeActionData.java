package hypersquare.hypersquare.dev.codefile.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeActionData {
    public String action;
    public String codeblock;
    public List<CodeActionData> actions = new ArrayList<>();
    public HashMap<String, List<JsonObject>> arguments = new HashMap<>();

    public CodeActionData() {
    }

    public CodeActionData(JsonObject data) {
        action = data.get("action").getAsString();
        codeblock = data.get("codeblock").getAsString();

        if (data.has("actions")) {
            for (JsonElement element : data.get("actions").getAsJsonArray()) {
                actions.add(new CodeActionData(element.getAsJsonObject()));
            }
        }
        if (data.has("arguments")) {
            for (Map.Entry<String, JsonElement> argument : data.get("arguments").getAsJsonObject().entrySet()) {
                List<JsonObject> values = new ArrayList<>();
                for (JsonElement value : argument.getValue().getAsJsonArray()) {
                    values.add(value.getAsJsonObject());
                }
                arguments.put(argument.getKey(), values);
            }
        }
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("action", action);
        data.addProperty("codeblock", codeblock);
        if (!actions.isEmpty()) {
            JsonArray actions = new JsonArray();
            for (CodeActionData action : this.actions) {
                actions.add(action.toJson());
            }
            data.add("actions", actions);
        }
        if (!arguments.isEmpty()) {
            JsonObject obj = new JsonObject();
            for (Map.Entry<String, List<JsonObject>> entry : arguments.entrySet()) {
                JsonArray values = new JsonArray();

                for (JsonObject value : entry.getValue()) {
                    values.add(value);
                }

                obj.add(entry.getKey(), values);
            }
            data.add("arguments", obj);
        }
        return data;
    }
}
