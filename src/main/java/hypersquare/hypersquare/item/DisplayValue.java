package hypersquare.hypersquare.item;


import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.function.Function;

public enum DisplayValue {
    NUMBER(NamedTextColor.RED, v -> v == CodeValues.NUMBER),
    ITEM(NamedTextColor.GOLD, v -> false),
    BLOCK(NamedTextColor.GOLD, v -> false),
    SPAWN_EGG(NamedTextColor.GOLD, v -> false),
    ANY(TextColor.color(255, 212, 127), v -> true),
    VARIABLE(NamedTextColor.YELLOW, v -> false),
    TEXT(TextColor.color(127, 212, 42), v -> v == CodeValues.TEXT),
    LIST(NamedTextColor.DARK_GREEN, v -> false),
    VECTOR(TextColor.color(42, 255, 170), v -> false),
    STRING(NamedTextColor.AQUA, v -> v == CodeValues.STRING),
    DICTIONARY(TextColor.color(85, 170, 255), v -> false),
    SOUND(NamedTextColor.BLUE, v -> false),
    PARTICLE(TextColor.color(170, 85, 255), v -> false),
    POTION(TextColor.color(255, 85, 127), v -> false),
    ;

    public static HashMap<DisplayValue, Material> menuPaneColor = new HashMap<>() {{
        put(DisplayValue.NUMBER, Material.RED_STAINED_GLASS_PANE);
        put(DisplayValue.ITEM, Material.ORANGE_STAINED_GLASS_PANE);
        put(DisplayValue.BLOCK, Material.ORANGE_STAINED_GLASS_PANE);
        put(DisplayValue.SPAWN_EGG, Material.ORANGE_STAINED_GLASS_PANE);
        put(DisplayValue.ANY, Material.WHITE_STAINED_GLASS_PANE);
        put(DisplayValue.VARIABLE, Material.YELLOW_STAINED_GLASS_PANE);
        put(DisplayValue.TEXT, Material.LIME_STAINED_GLASS_PANE);
        put(DisplayValue.LIST, Material.GREEN_STAINED_GLASS_PANE);
        put(DisplayValue.VECTOR, Material.CYAN_STAINED_GLASS_PANE);
        put(DisplayValue.STRING, Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        put(DisplayValue.DICTIONARY, Material.BLUE_STAINED_GLASS);
        put(DisplayValue.SOUND, Material.BLUE_STAINED_GLASS_PANE);
        put(DisplayValue.PARTICLE, Material.PURPLE_STAINED_GLASS_PANE);
        put(DisplayValue.POTION, Material.MAGENTA_STAINED_GLASS_PANE);
    }};

    private final TextColor color;
    private final Function<CodeValues, Boolean> isValid;

    DisplayValue(TextColor color, Function<CodeValues, Boolean> isValid) {
        this.color = color;
        this.isValid = isValid;
    }

    public Component getName() {
        return Component.text(Utilities.capitalize(toString().toLowerCase())).color(color);
    }

    public boolean isValid(CodeValues v) {
        return isValid.apply(v);
    }
}
