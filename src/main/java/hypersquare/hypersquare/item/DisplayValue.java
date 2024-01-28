package hypersquare.hypersquare.item;


import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.util.Utilities;
import hypersquare.hypersquare.util.color.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.function.Function;

public enum DisplayValue {
    NUMBER(Color.RED_LIGHT.color(), v -> v == CodeValues.NUMBER),
    ITEM(Color.GOLD.color(), v -> false),
    BLOCK(Color.GOLD.color(), v -> false),
    SPAWN_EGG(Color.GOLD.color(), v -> false),
    ANY(Color.GOLD_LIGHT.color(), v -> true),
    VARIABLE(Color.YELLOW.color(), v -> false),
    TEXT(Color.LIME_DARK.color(), v -> v == CodeValues.TEXT),
    LIST(Color.GREEN_DARK_2.color(), v -> false),
    VECTOR(Color.TEAL.color(), v -> false),
    STRING(Color.AQUA_LIGHT.color(), v -> v == CodeValues.STRING),
    DICTIONARY(Color.SKY.color(), v -> false),
    SOUND(Color.BLUE_DARK_2.color(), v -> false),
    PARTICLE(Color.PURPLE.color(), v -> false),
    POTION(Color.ROSE_DARK.color(), v -> false),
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
