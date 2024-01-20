package hypersquare.hypersquare.menu.actions.parameter;

import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.item.DisplayValue;
import hypersquare.hypersquare.menu.system.MenuItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MenuParameter extends MenuItem {

    /*
       Example:
       ----------
       String(s)*
       String to change

       *Optional
     */
    private static final Component optionalDesc = Component.newline()
                    .append(Component.text("*Optional")
                    .color(NamedTextColor.GRAY));
    private final int slotId;
    private final Action.ActionParameter p;

    public MenuParameter(Action.ActionParameter p, int slotId) {
        super(DisplayValue.menuPaneColor.get(p.type()));
        this.slotId = slotId;
        this.p = p;

        Component typeName = p.type().getName();
        if (p.plural()) typeName = typeName.append(Component.text("(s)"));
        if (p.optional()) typeName = typeName.append(Component.text("*").color(NamedTextColor.GRAY));

        name(typeName.decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        lore.add(p.description().color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        if (p.optional()) lore.add(optionalDesc);
        lore(lore);
    }

    public boolean isValid(ItemStack item) {
        return true; //TODO check
    }

    public ItemStack replaceValue(CodeActionData action, ItemStack newItem) {
        Bukkit.broadcast(Component.text(action.toJson().toString()));
        Bukkit.broadcast(Component.text(p.id() + " " + slotId));
        return newItem; //TODO update action
    }
}
