package hypersquare.hypersquare.item;

import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.menu.actions.ActionMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.block.Barrel;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public interface Action {

    void executeBlockAction(List<Entity> targets, Barrel barrel);

    ActionParameter[] parameters();
    String getId();
    String getSignName();

    String getName();

    PlayerActionItems getCategory();

    ItemStack item();

    ActionMenu actionMenu(CodeActionData data);

    default ActionParameter getParameter(String id) {
        for (ActionParameter param : parameters()) {
            if (Objects.equals(param.id, id)) return param;
        }
        return null;
    }

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
