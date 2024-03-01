package hypersquare.hypersquare.dev.codefile.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hypersquare.hypersquare.dev.*;
import hypersquare.hypersquare.dev.value.impl.VariableValue;
import hypersquare.hypersquare.item.value.DisplayValue;
import hypersquare.hypersquare.menu.barrel.BarrelMenu;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CodeLineData implements ArgumentsData, TagOptionsData, BarrelParamSupplier, BarrelTagSupplier {

    public int position;
    private final HashMap<String, List<JsonObject>> arguments = new HashMap<>();
    public final List<CodeActionData> actions = new ArrayList<>();
    private final HashMap<String, Pair<String, VariableValue.HSVar>> tags = new HashMap<>();
    public String type, event;

    public CodeLineData() {
    }

    public CodeLineData(@NotNull JsonObject data) {
        position = data.get("position").getAsInt();
        type = data.get("type").getAsString();
        event = data.get("event").getAsString();
        for (JsonElement action : data.get("actions").getAsJsonArray()) {
            actions.add(new CodeActionData(action.getAsJsonObject()));
        }
        CodeData.parseArgsAndTags(data, arguments, tags);
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

    @Override
    public HashMap<String, List<JsonObject>> getArguments() {
        return arguments;
    }

    @Override
    public HashMap<String, Pair<String, VariableValue.HSVar>> getTags() {
        return tags;
    }

    @Override
    public BarrelParameter[] parameters() {
        if (type.equals("function") || type.equals("process")) return new BarrelParameter[]{
            new BarrelParameter(DisplayValue.NULL, true, true, Component.text("Parameter(s)"), "paramValues"),
            new BarrelParameter(DisplayValue.ITEM, false, true, Component.text("Function Icon"), "icon"),
        };
        else return new BarrelParameter[]{};
    }

    @Override
    public BarrelTag[] tags() {
        return new BarrelTag[]{};
    }

    public BarrelMenu menu() {
        return new BarrelMenu(event, 4, this)
            .parameter("paramValues", 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26)
            .parameter("icon", 35);
    }
}
