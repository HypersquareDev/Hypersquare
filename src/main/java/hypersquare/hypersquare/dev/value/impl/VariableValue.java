package hypersquare.hypersquare.dev.value.impl;

import com.google.gson.JsonObject;
import hypersquare.hypersquare.dev.value.CodeValue;
import hypersquare.hypersquare.item.value.DisplayValue;
import hypersquare.hypersquare.play.CodeVariable;
import hypersquare.hypersquare.util.color.Colors;
import hypersquare.hypersquare.util.component.BasicComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class VariableValue implements CodeValue<VariableValue.HSVar, CodeVariable> {

    @Override
    public Component getName() {
        return Component.text("Variable").color(DisplayValue.VARIABLE.color);
    }

    @Override
    public Material getMaterial() {
        return Material.MAGMA_CREAM;
    }

    @Override
    public String getTypeId() {
        return "variable";
    }

    @Override
    public List<Component> getDescription() {
        return List.of(
                BasicComponent.gray("Variables can be used in expressions"),
                BasicComponent.gray("to refer to other values.")
        );
    }

    @Override
    public List<Component> getHowToSet() {
        return List.of(
                BasicComponent.gray("Type in chat while holding"),
                BasicComponent.gray("this item to change its name."),
                BasicComponent.gray("Use -l -t or -g to change the scope.")
        );
    }

    @Override
    public JsonObject getVarItemData(HSVar type) {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", type.name);
        obj.addProperty("scope", type.scope.name());
        return obj;
    }

    @Override
    public Component getValueName(HSVar value) {
        return BasicComponent.create()
            .append(
                Component.text("[" + value.scope.letter + "] ").color(value.scope().color)
            )
            .append(
                Component.text(value.name).color(NamedTextColor.YELLOW)
            );
    }

    @Override
    public HSVar fromJson(JsonObject obj) {
        return new HSVar(
                obj.get("name").getAsString(),
                Scope.valueOf(obj.get("scope").getAsString())
        );
    }

    @Override
    public HSVar defaultValue() {
        return new HSVar("unnamed variable", Scope.THREAD);
    }

    @Override
    public HSVar fromString(String data, HSVar previous) {
        Scope scope = previous == null ? Scope.THREAD : previous.scope;
        for (Scope s : Scope.values()) {
            String flag = "-" + Character.toLowerCase(s.name().charAt(0));
            if (data.endsWith(flag)) {
                data = data.substring(0, data.length() - 2);
                data = data.trim();
                scope = s;
                break;
            }
            if (data.startsWith(flag)) {
                data = data.substring(2);
                data = data.trim();
                scope = s;
                break;
            }
        }
        return new HSVar(data, scope);
    }

    @Override
    public ItemStack getItem(HSVar value) {
        ItemStack item = CodeValue.super.getItem(value);
        ItemMeta meta = item.getItemMeta();
        meta.lore(List.of(Component.text(value.scope.name())
                .color(value.scope.color)
                .decoration(TextDecoration.ITALIC, false)));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public CodeVariable realValue(HSVar value) {
        return new CodeVariable(value.name, value.scope);
    }

    @Override
    public JsonObject serialize(Object obj) {
        return null;
    }

    public record HSVar(String name, Scope scope) {}
    public enum Scope {
        GLOBAL(Colors.GREEN_LIGHT, "G"),
        THREAD(Colors.AQUA_LIGHT, "T"),
        LINE(Colors.SKY, "L"),
        SAVE(Colors.YELLOW_LIGHT, "S"),
        ;

        public final TextColor color;
        public final String letter;

        Scope(TextColor color, String letter) {
            this.color = color;
            this.letter = letter;
        }
    }
}
