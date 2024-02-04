package hypersquare.hypersquare.dev.codefile.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class CodeLineData {

    public int position;
    public final List<CodeActionData> actions = new ArrayList<>();
    public String type;
    public String event;

    public CodeLineData() {
    }

    public CodeLineData(JsonObject json) {
        position = json.get("position").getAsInt();
        type = json.get("type").getAsString();
        event = json.get("event").getAsString();
        for (JsonElement action : json.get("actions").getAsJsonArray()) {
            actions.add(new CodeActionData(action.getAsJsonObject()));
        }
    }

    public JsonElement toJson() {
        JsonObject out = new JsonObject();
        out.addProperty("position", position);
        out.addProperty("type", type);
        out.addProperty("event", event);
        JsonArray actions = new JsonArray();
        for (CodeActionData action : this.actions) {
            actions.add(action.toJson());
        }
        out.add("actions", actions);
        return out;
    }
}
