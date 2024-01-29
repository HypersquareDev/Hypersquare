package hypersquare.hypersquare.item;


import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.util.Utilities;
import hypersquare.hypersquare.util.color.Colors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.function.Function;

public enum DisplayValue {
    NUMBER(Colors.RED_LIGHT, v -> v == CodeValues.NUMBER),
    ITEM(Colors.GOLD, v -> false),
    BLOCK(Colors.GOLD, v -> false),
    SPAWN_EGG(Colors.GOLD, v -> false),
    ANY(Colors.GOLD_LIGHT, v -> true),
    VARIABLE(Colors.YELLOW, v -> false),
    TEXT(Colors.LIME_DARK, v -> v == CodeValues.TEXT),
    LIST(Colors.GREEN_DARK_2, v -> false),
    VECTOR(Colors.TEAL, v -> false),
    STRING(Colors.AQUA_LIGHT, v -> v == CodeValues.STRING),
    DICTIONARY(Colors.SKY, v -> false),
    SOUND(Colors.BLUE_DARK_2, v -> false),
    PARTICLE(Colors.PURPLE, v -> false),
    POTION(Colors.ROSE_DARK, v -> false),
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

    public final TextColor color;
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
