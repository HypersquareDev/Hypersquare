package hypersquare.hypersquare.dev.codefile.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CodeValueData {

    public String type;
    private JsonElement value;

    public CodeValueData(JsonObject data) {
        this.type = data.get("type").getAsString();
        this.value = data.get("value");
    }

    public JsonObject toJson() {
        JsonObject data = new JsonObject();
        data.addProperty("type", type);
        data.add("value", value);
        return data;
    }
}
