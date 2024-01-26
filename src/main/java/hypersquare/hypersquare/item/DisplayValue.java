package hypersquare.hypersquare.item;


import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.dev.value.impl.ItemValue;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.function.BiFunction;

public enum DisplayValue {
    NUMBER(CodeValues.NUMBER, NamedTextColor.RED, (v, t) -> v == CodeValues.NUMBER),
    ITEM(CodeValues.ITEM, NamedTextColor.GOLD, (v, t) -> v == CodeValues.ITEM),
    BLOCK(CodeValues.ITEM, NamedTextColor.GOLD, (v, t) -> v == CodeValues.ITEM && t.getType().isBlock()),
    SPAWN_EGG(CodeValues.ITEM, NamedTextColor.GOLD, (v, t) -> v == CodeValues.ITEM && t.getType().getKey().getKey().endsWith("_spawn_egg")),
    ANY(null, TextColor.color(255, 212, 127), (v, t) -> true),
    VARIABLE(CodeValues.VARIABLE, NamedTextColor.YELLOW, (v, t) -> v == CodeValues.VARIABLE),
    TEXT(CodeValues.TEXT, TextColor.color(127, 212, 42), (v, t) -> v == CodeValues.TEXT),
    LIST(null, NamedTextColor.DARK_GREEN, (v, t) -> false),
    VECTOR(null, TextColor.color(42, 255, 170), (v, t) -> false),
    STRING(null, NamedTextColor.AQUA, (v, t) -> v == CodeValues.STRING),
    DICTIONARY(null, TextColor.color(85, 170, 255), (v, t) -> false),
    SOUND(null, NamedTextColor.BLUE, (v, t) -> false),
    PARTICLE(null, TextColor.color(170, 85, 255), (v, t) -> false),
    POTION(null, TextColor.color(255, 85, 127), (v, t) -> false),
    ;

    public static final HashMap<DisplayValue, Material> menuPaneColor = new HashMap<>() {{
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

    public final CodeValues codeVal;
    public final TextColor color;
    private final BiFunction<CodeValues, ItemStack, Boolean> isValid;

    DisplayValue(@Nullable CodeValues codeVal, TextColor color, BiFunction<CodeValues, ItemStack, Boolean> isValid) {
        this.codeVal = codeVal;
        this.color = color;
        this.isValid = isValid;
    }

    public Component getName() {
        return Component.text(Utilities.capitalize(toString().toLowerCase())).color(color);
    }

    public boolean isValid(CodeValues v, @Nullable ItemStack t) {
        return isValid.apply(v, t);
    }
}
