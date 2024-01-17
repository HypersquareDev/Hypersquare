package hypersquare.hypersquare.dev.codefile.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class CodeActionData {
    public String action;
    public List<CodeActionData> actions = new ArrayList<>();

    public CodeActionData() {}

    public CodeActionData(JsonObject data) {
        action = data.get("action").getAsString();
        if (data.has("actions")) {
            for (JsonElement element : data.get("actions").getAsJsonArray()) {
                actions.add(new CodeActionData(element.getAsJsonObject()));
            }
        }
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("action", action);
        if (!actions.isEmpty()) {
            JsonArray actions = new JsonArray();
            for (CodeActionData action : this.actions) {
                actions.add(action.toJson());
            }
            data.add("actions", actions);
        }
        return data;
    }
}
