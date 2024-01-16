package hypersquare.hypersquare.dev.codefile.data;

import com.google.gson.JsonObject;

public class CodeActionData {
    public String action;
    public CodeActionData() {
    }

    public CodeActionData(JsonObject data) {
        action = data.get("action").getAsString();
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("action", action);
        return data;
    }

}
