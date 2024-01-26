package hypersquare.hypersquare.menu.actions.tag;

import hypersquare.hypersquare.dev.ActionTag;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.value.impl.VariableValue;
import hypersquare.hypersquare.menu.system.MenuItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class MenuTag extends MenuItem {

    public final CodeActionData data;
    public final ActionTag tag;

    public MenuTag(ActionTag tag, CodeActionData data) {
        super(currentOption(tag, data).icon());
        this.data = data;
        this.tag = tag;

        name(Component.text("Tag: ")
                .decoration(TextDecoration.ITALIC, false)
                .color(TextColor.color(0xFFE942))
                .append(Component.text(tag.name()))
        );

        List<Component> lore = new ArrayList<>();
        lore.add(Component.empty());

        VariableValue.HSVar var = data.tags.getOrDefault(tag.id(), new Pair<>(null, null)).getB();
        if (var != null) {
            lore.add(Component.text("[").decoration(TextDecoration.ITALIC, false)
                .append(Component.text(var.scope().letter))
                .append(Component.text("]"))
                .color(var.scope().color)
                .append(Component.text(" " + var.name())
                    .color(NamedTextColor.WHITE))
            );
            lore.add(Component.text("» ").decoration(TextDecoration.ITALIC, false)
                .color(NamedTextColor.GREEN)
                .decoration(TextDecoration.BOLD, false)
                .append(Component.text(" Shift click to remove.")
                    .color(NamedTextColor.GRAY)
                    .decoration(TextDecoration.BOLD, false ))
            );
            lore.add(Component.empty());
        }

        for (ActionTag.Option option : tag.options()) {
            if (option == currentOption(tag, data)) {
                lore.add(Component.text("» ")
                        .decoration(TextDecoration.ITALIC, false)
                        .color(TextColor.color(0x2487CC))
                        .append(Component.text(option.text())
                                .color(TextColor.color(0x42C7FF)))
                );
            } else {
                lore.add(Component.text("» ")
                        .decoration(TextDecoration.ITALIC, false)
                        .color(TextColor.color(0x505050))
                        .append(Component.text(option.text())
                                .color(TextColor.color(0x808080)))
                );
            }
        }

        lore(lore);
        hideAllFlags();
    }

    public static ActionTag.Option currentOption(ActionTag tag, CodeActionData data) {
        return tag.getOption(data.tags.getOrDefault(tag.id(), new Pair<>(tag.defaultOption().name(), null)).getA());
    }
}
