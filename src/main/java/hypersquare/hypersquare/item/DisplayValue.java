package hypersquare.hypersquare.item;


import hypersquare.hypersquare.command.codeValue.Text;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

import java.util.HashMap;

import static hypersquare.hypersquare.Hypersquare.cleanMM;

public enum DisplayValue {
    NUMBER(NamedTextColor.RED),
    ITEM(NamedTextColor.GOLD),
    BLOCK(NamedTextColor.GOLD),
    SPAWN_EGG(NamedTextColor.GOLD),
    ANY(TextColor.color(255, 212, 127)),
    VARIABLE(NamedTextColor.YELLOW),
    TEXT(TextColor.color(127, 212, 42)),
    LIST(NamedTextColor.DARK_GREEN),
    VECTOR(TextColor.color(42, 255, 170)),
    STRING(NamedTextColor.AQUA),
    DICTIONARY(TextColor.color(85, 170, 255)),
    SOUND(NamedTextColor.BLUE),
    PARTICLE(TextColor.color(170, 85, 255)),
    POTION(TextColor.color(255, 85, 127)),
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

    DisplayValue(TextColor color) {
        this.color = color;
    }

    public Component getName() {
        return Component.text(Utilities.capitalize(toString().toLowerCase())).color(color);
    }
}
