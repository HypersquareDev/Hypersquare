package hypersquare.hypersquare.dev.codefile.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.dev.value.impl.VariableValue;
import oshi.util.tuples.Pair;

import java.util.*;

public class CodeActionData {
    public String action;
    public String target;
    public String codeblock;
    public final List<CodeActionData> actions = new LinkedList<>();
    public HashMap<String, List<JsonObject>> arguments = new HashMap<>();
    public final HashMap<String, Pair<String, VariableValue.HSVar>> tags = new HashMap<>();

    public CodeActionData() {
    }

    public CodeActionData(JsonObject data) {
        action = data.get("action").getAsString();
        codeblock = data.get("codeblock").getAsString();

        if (data.has("target")) {
            if (!data.get("target").isJsonNull()) target = data.get("target").getAsString();
        }
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
        if (data.has("tags")) {
            for (Map.Entry<String, JsonElement> tag : data.get("tags").getAsJsonObject().entrySet()) {
                JsonObject obj = tag.getValue().getAsJsonObject();
                VariableValue.HSVar var = null;
                if (obj.has("var")) {
                    var = (VariableValue.HSVar) CodeValues.VARIABLE.fromJson(obj.get("var").getAsJsonObject());
                }

                tags.put(tag.getKey(), new Pair<>(obj.get("value").getAsString(), var));
            }
        }
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("action", action);
        data.addProperty("codeblock", codeblock);
        data.addProperty("target", target);
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
        if (!tags.isEmpty()) {
            JsonObject obj = new JsonObject();

            for (Map.Entry<String, Pair<String, VariableValue.HSVar>> entry : tags.entrySet()) {
                JsonObject tag = new JsonObject();
                tag.addProperty("value", entry.getValue().getA());
                if (entry.getValue().getB() != null) tag.add("var", CodeValues.VARIABLE.getVarItemData(entry.getValue().getB()));
                obj.add(entry.getKey(), tag);
            }

            data.add("tags", obj);
        }
        return data;
    }
}
