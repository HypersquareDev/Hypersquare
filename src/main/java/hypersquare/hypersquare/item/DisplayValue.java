package hypersquare.hypersquare.item;


import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;

import static hypersquare.hypersquare.Hypersquare.cleanMM;

public enum DisplayValue {
    VARIABLE("<yellow>"),
    ITEM("<gold>"),
    NUMBER("<red>"),
    TEXT("<#idk>"),
    STRING("<aqua>"),
    POTION("<#idk>"),
    PARTICLE("<#idk>"),
    LIST("<dark_green>"),
    DICTIONARY("<#idk>"),
    ANY("<#idk>");

    private final String color;

    DisplayValue(String color) {
        this.color = color;
    }

    public Component getName(DisplayValue type) {
        return cleanMM.deserialize(color + Utilities.capitalize(type.toString().toLowerCase()));
    }
}
