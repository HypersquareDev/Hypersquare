package hypersquare.hypersquare.dev;

import org.bukkit.Material;

public record ActionTag(String id, String name, Enum<?> defaultOption, Option... options) {

    public Option getOption(String id) {
        for (Option o : options) {
            if (o.id.name().equals(id)) return o;
        }
        return null;
    }

    public record Option(Enum<?> id, String text, Material icon) {
    }
}
