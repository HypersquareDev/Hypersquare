package hypersquare.hypersquare.dev.codefile.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class CodeData {
    public final List<CodeLineData> codelines = new ArrayList<>();

    public CodeData(JsonArray code) {
        for (JsonElement elm : code) {
            codelines.add(new CodeLineData(elm.getAsJsonObject()));
        }
    }

    public JsonArray toJson() {
        JsonArray out = new JsonArray();
        for (CodeLineData line : codelines) {
            out.add(line.toJson());
        }
        return out;
    }
}
