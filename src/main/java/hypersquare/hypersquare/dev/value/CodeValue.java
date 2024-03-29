package hypersquare.hypersquare.dev.value;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.util.color.Colors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public interface CodeValue<T, S> {
    Component getName();
    Material getMaterial();
    String getTypeId();
    List<Component> getDescription();
    List<Component> getHowToSet();

    JsonObject getVarItemData(T type);
    default boolean isType(JsonObject d) {
        try {
            return d.get("type").getAsString().equals(getTypeId());
        } catch (Exception err) {
            return false;
        }
    }
    T fromJson(JsonObject obj);
    T defaultValue();
    T fromString(String data, T previous);
    Component getValueName(T value);
    S realValue(T value);

    default ItemStack getItem(T value) {
        ItemStack item = new ItemStack(getMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.displayName(getValueName(value));

        JsonObject obj = getVarItemData(value);
        obj.addProperty("type", getTypeId());
        meta.getPersistentDataContainer().set(
                new NamespacedKey(Hypersquare.pluginName, "varitem"),
                PersistentDataType.STRING,
                obj.toString()
        );

        item.setItemMeta(meta);
        return item;
    }

    default T fromItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        String varItemData = meta.getPersistentDataContainer().get(
                new NamespacedKey(Hypersquare.pluginName, "varitem"),
                PersistentDataType.STRING
        );
        if (varItemData == null) return null;

        try {
            JsonObject value = JsonParser.parseString(varItemData).getAsJsonObject();
            if (!value.get("type").getAsString().equals(getTypeId())) throw new IllegalArgumentException("Invalid fromItem call.");
            return fromJson(value);
        } catch (Exception err) {
            return null;
        }
    }

    default ItemStack emptyValue() {
        ItemStack item = getItem(defaultValue());
        ItemMeta meta = item.getItemMeta();

        meta.displayName(getName().decoration(TextDecoration.ITALIC, false));

        List<Component> lore = new ArrayList<>(List.of());
        lore.addAll(getDescription());

        lore.add(Component.empty());
        lore.add(Component.text("How to set:").color(Colors.PINK_LIGHT).decoration(TextDecoration.ITALIC, false));

        lore.addAll(getHowToSet());

        meta.lore(lore);

        item.setItemMeta(meta);
        return item;
    }
}
