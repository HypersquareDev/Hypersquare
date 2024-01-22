package hypersquare.hypersquare.dev.value;

import com.google.gson.JsonObject;
import hypersquare.hypersquare.dev.value.impl.NumberValue;
import hypersquare.hypersquare.dev.value.impl.StringValue;
import hypersquare.hypersquare.dev.value.impl.TextValue;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public enum CodeValues implements CodeValue {
    NUMBER(new NumberValue()),
    STRING(new StringValue()),
    TEXT(new TextValue())
    ;

    private final CodeValue v;
    CodeValues(CodeValue v) {
        this.v = v;
    }

    @Override
    public Component getValueName(Object value) {
        return v.getValueName(value);
    }

    @Override
    public Object realValue(Object val) {
        return v.realValue(val);
    }

    @Override
    public Material getMaterial() {
        return v.getMaterial();
    }

    @Override
    public String getTypeId() {
        return v.getTypeId();
    }

    @Override
    public List<Component> getDescription() {
        return v.getDescription();
    }

    @Override
    public List<Component> getHowToSet() {
        return v.getHowToSet();
    }

    @Override
    public JsonObject getVarItemData(Object type) {
        return v.getVarItemData(type);
    }

    @Override
    public Object fromJson(JsonObject obj) {
        return v.fromJson(obj);
    }

    @Override
    public Object defaultValue() {
        return v.defaultValue();
    }

    @Override
    public Object fromString(String data, Object previous) {
        return v.fromString(data, previous);
    }

    @Override
    public Component getName() {
        return v.getName();
    }

    @Override
    public ItemStack getItem(Object value) {
        return v.getItem(value);
    }
}
