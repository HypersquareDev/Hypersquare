package hypersquare.hypersquare.dev.action;

import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.item.ActionMenuItem;
import hypersquare.hypersquare.item.DisplayValue;
import hypersquare.hypersquare.menu.actions.ActionMenu;
import hypersquare.hypersquare.play.ActionArguments;
import hypersquare.hypersquare.play.CodeSelection;
import hypersquare.hypersquare.play.ExecutionContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public interface Action {
    ActionParameter[] parameters();
    String getId();
    String getSignName();

    String getName();

    ActionMenuItem getCategory();

    ItemStack item();

    ActionMenu actionMenu(CodeActionData data);

    default ActionParameter getParameter(String id) {
        for (ActionParameter param : parameters()) {
            if (Objects.equals(param.id, id)) return param;
        }
        return null;
    }

    void execute(ExecutionContext ctx);

    record ActionParameter(DisplayValue type, boolean plural, boolean optional, Component description, String id) {
        public ActionParameter(DisplayValue type, Component description, String id) {
            this(type, false, false, description, id);
        }

        public ActionParameter(DisplayValue type, boolean plural, boolean optional, Component description, List<Component> notes, String id) {
            this(type, false, false, addNotes(description, notes), id);
        }

        private static Component addNotes(Component description, List<Component> notes) {
            if (notes.isEmpty()) return description;
            for (Component note : notes) {
                description = description.appendNewline() // \n<blue>⏵ <gray>[TEXT]
                        .append(Component.text("⏵")
                                .color(NamedTextColor.BLUE)
                                .append(Component.text(" ")
                                        .color(NamedTextColor.GRAY)
                                        .append(note))
                        );
            }
            return description;
        }
    }
}
