package hypersquare.hypersquare.dev.value.impl;

import com.google.gson.JsonObject;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.value.CodeValue;
import hypersquare.hypersquare.item.DisplayValue;
import hypersquare.hypersquare.util.component.BasicComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class TextValue implements CodeValue<TextValue.TxtComponent, Component> {
    @Override
    public Component getName() {
        return Component.text("Text").color(DisplayValue.TEXT.color);
    }

    @Override
    public Material getMaterial() {
        return Material.BOOK;
    }

    @Override
    public String getTypeId() {
        return "text";
    }

    @Override
    public TxtComponent defaultValue() {
        return new TxtComponent(false,"");
    }

    @Override
    public List<Component> getDescription() {
        return List.of (
                BasicComponent.gray("Text with extra formatting via"),
                Hypersquare.cleanMM.deserialize("<!italic><white>MiniMessage</white> <gray>tags such as <white>\\<color></white>."),
                BasicComponent.gray("Recommended for text displayed"),
                BasicComponent.gray("through chat, item names, and others.")
        );
    }

    @Override
    public List<Component> getHowToSet() {
        return List.of (
                BasicComponent.gray("Type in chat while holding"),
                BasicComponent.gray("this item."),
                Hypersquare.cleanMM.deserialize("<!italic><gray>E.g. '<white>\\<rainbow>Rainbow text!</white>'")
        );
    }

    @Override
    public JsonObject getVarItemData(TxtComponent type) {
        JsonObject obj = new JsonObject();
        obj.addProperty("legacy", type.isLegacy);
        obj.addProperty("value", type.value);
        return obj;
    }

    @Override
    public TxtComponent fromJson(JsonObject obj) {
        return new TxtComponent(obj.get("legacy").getAsBoolean(), obj.get("value").getAsString());
    }

    @Override
    public TxtComponent fromString(String data, TxtComponent previous) {
        boolean isLegacy = previous != null && previous.isLegacy;

        data = data.trim();

        for (String alias : List.of("--legacy", "-l")) {
            if (data.startsWith(alias)) {
                isLegacy = true;
                data = data.substring(alias.length());
                break;
            }
            if (data.endsWith(alias)) {
                isLegacy = true;
                data = data.substring(0, data.length() - alias.length());
            }
        }
        data = data.trim();

        return new TxtComponent(isLegacy, data);
    }

    @Override
    public Component getValueName(TxtComponent comp) {
        return BasicComponent.create(comp.value);
    }

    @Override
    public Component realValue(TxtComponent value) {
        if (!value.isLegacy) {
            return Hypersquare.fullMM.deserialize(value.value)
                    .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        } else {
            return LegacyComponentSerializer.legacyAmpersand().deserialize(value.value)
                    .colorIfAbsent(NamedTextColor.WHITE)
                    .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        }
    }

    @Override
    public ItemStack getItem(TxtComponent comp) {
        ItemStack item = CodeValue.super.getItem(comp);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(realValue(comp));

        List<Component> lore = List.of(
                !comp.isLegacy ? BasicComponent.create("Expression:")
                        : BasicComponent.create("Expression ")
                            .append(Component.text("(LEGACY)")
                                .color(NamedTextColor.GRAY)
                            )
                            .append(Component.text(":")
                        ),
                BasicComponent.gray(comp.value)
        );

        meta.lore(lore);
        item.setItemMeta(meta);
        System.out.println(lore+"\n"+item);
        return item;
    }

    public record TxtComponent(boolean isLegacy, String value) {}
}
