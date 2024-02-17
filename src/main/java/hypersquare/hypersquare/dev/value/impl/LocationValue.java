package hypersquare.hypersquare.dev.value.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hypersquare.hypersquare.dev.value.CodeValue;
import hypersquare.hypersquare.item.value.DisplayValue;
import hypersquare.hypersquare.util.component.BasicComponent;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationValue implements CodeValue<LocationValue.HSLocation, Location> {

    @Override
    public Component getName() {
        return Component.text("Location").color(DisplayValue.LOCATION.color);
    }

    @Override
    public Material getMaterial() {
        return Material.PAPER;
    }

    @Override
    public String getTypeId() {
        return "location";
    }

    @Override
    public List<Component> getDescription() {
        return List.of(
            BasicComponent.gray("Represents a location,"),
            BasicComponent.gray("based on world coordinates")
        );
    }

    @Override
    public List<Component> getHowToSet() {
        return List.of(
            BasicComponent.gray("Right click this item to set it"),
            BasicComponent.gray("to your current location in build mode."),
            BasicComponent.gray("Left click a block to set the location"),
            BasicComponent.gray("of the block in build mode."),
            BasicComponent.gray("Shift left click to teleport to the set location."),
            BasicComponent.gray("Type in chat while holding this item to set the location."),
            BasicComponent.gray("Ex: 69 420 69 (0 0)*")
        );
    }

    @Override
    public JsonObject getVarItemData(HSLocation type) {
        JsonObject obj = new JsonObject();
        JsonObject loc = new JsonObject();
        loc.addProperty("x", type.x);
        loc.addProperty("y", type.y);
        loc.addProperty("z", type.z);
        loc.addProperty("pitch", type.pitch);
        loc.addProperty("yaw", type.yaw);
        obj.add("value", loc);
        return obj;
    }

    @Override
    public Component getValueName(HSLocation value) {
        return getName();
    }

    @Override
    public HSLocation fromJson(JsonObject obj) {
        JsonObject data = obj.get("value").getAsJsonObject();
        return new HSLocation(
            data.get("x").getAsDouble(),
            data.get("y").getAsDouble(),
            data.get("z").getAsDouble(),
            data.get("pitch").getAsLong(),
            data.get("yaw").getAsLong()
        );
    }

    @Override
    public HSLocation defaultValue() {
        return new HSLocation(0, 0, 0, 0, 0);
    }


    @Override
    public HSLocation fromString(String data, HSLocation previous) {
        String[] dataArray = data.split("\\s+");
        return new HSLocation(Long.parseLong(dataArray[0]), Long.parseLong(dataArray[1]), Long.parseLong(dataArray[2]), Long.parseLong(dataArray[3]), Long.parseLong(dataArray[4]));
    }

    @Override
    public Location realValue(HSLocation value) {
        return new Location(null, value.x, value.y, value.z, value.pitch, value.yaw);
    }

    @Override
    public JsonObject serialize(Object obj) {
        if (obj instanceof HSLocation hsLocation) return getVarItemData(hsLocation);
        if (obj instanceof Location location)
            return getVarItemData(new HSLocation(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw()));
        return null;
    }

    public record HSLocation(double x, double y, double z, float pitch, float yaw) {
    }
}
