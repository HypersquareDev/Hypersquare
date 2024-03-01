package hypersquare.hypersquare.dev.codefile.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hypersquare.hypersquare.dev.ArgumentsData;
import hypersquare.hypersquare.dev.TagOptionsData;
import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.dev.value.impl.VariableValue;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CodeActionData implements ArgumentsData, TagOptionsData {
    public String action;
    public String target;
    public String codeblock;
    public final List<CodeActionData> actions = new LinkedList<>();
    private final HashMap<String, List<JsonObject>> arguments = new HashMap<>();
    private final HashMap<String, Pair<String, VariableValue.HSVar>> tags = new HashMap<>();

    public CodeActionData() {
    }

    public CodeActionData(@NotNull JsonObject data) {
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
        CodeData.parseArgsAndTags(data, arguments, tags);
    }

    @Override
    public HashMap<String, List<JsonObject>> getArguments() {
        return arguments;
    }

    @Override
    public HashMap<String, Pair<String, VariableValue.HSVar>> getTags() {
        return tags;
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
