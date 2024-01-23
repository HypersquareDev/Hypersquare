package hypersquare.hypersquare.menu.actions.parameter;

import com.google.gson.JsonObject;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.item.DisplayValue;
import hypersquare.hypersquare.menu.system.MenuItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

    public MenuParameter(Action.ActionParameter p, int slotId, CodeActionData action) {
        super(DisplayValue.menuPaneColor.get(p.type()));
        this.slotId = slotId;
        this.p = p;

        List<JsonObject> current = action.arguments.computeIfAbsent(p.id(), id -> new ArrayList<>());

        Component typeName = p.type().getName();
        if (p.plural()) typeName = typeName.append(Component.text("(s)"));
        if (p.optional()) typeName = typeName.append(Component.text("*").color(NamedTextColor.GRAY));

        name(typeName.decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        lore.add(p.description().color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        if (p.optional()) lore.add(optionalDesc);
        lore(lore);

        if (current.size() > slotId) {
            JsonObject data = current.get(slotId);
            if (data.has("type")) {
                CodeValues v = CodeValues.getType(data);
                ItemStack item = v.getItem(v.fromJson(data));
                ItemMeta meta = item.getItemMeta();
                List<Component> itemLore = meta.lore();
                if (itemLore == null) itemLore = new ArrayList<>();
                lore.addAll(0, itemLore);
                lore.add(itemLore.size(), Component.empty());
                lore.add(itemLore.size() + 1, typeName.decoration(TextDecoration.ITALIC, false));

                lore(lore);
                material(item.getType());
                name(meta.displayName());
            }
        }
    }

    public boolean isValid(ItemStack item) {
        if (item == null) return false;
        JsonObject data = CodeValues.getVarItemData(item);
        if (data == null) return false;
        CodeValues v = CodeValues.getType(data);
        return p.type().isValid(v);
    }

    public ItemStack replaceValue(CodeActionData action, ItemStack newItem) {
        ItemStack oldItem = reset(action);
        List<JsonObject> previous = action.arguments.computeIfAbsent(p.id(), id -> new ArrayList<>());

        JsonObject data = CodeValues.getVarItemData(newItem);
        CodeValues v = CodeValues.getType(data);
        JsonObject json = v.getVarItemData(v.fromItem(newItem));
        json.addProperty("type", v.getTypeId());
        while (previous.size() <= slotId) previous.add(new JsonObject());
        previous.set(slotId, json);

        return oldItem;
    }

    public MenuParameter updated(CodeActionData action) {
        return new MenuParameter(p, slotId, action);
    }

    public boolean isEmpty(CodeActionData action) {
        List<JsonObject> previous = action.arguments.computeIfAbsent(p.id(), id -> new ArrayList<>());

        return previous.size() <= slotId || !previous.get(slotId).has("type");
    }

    public ItemStack reset(CodeActionData action) {
        List<JsonObject> previous = action.arguments.computeIfAbsent(p.id(), id -> new ArrayList<>());

        if (previous.size() > slotId) {
            JsonObject data = previous.get(slotId);
            if (data != null && data.has("type")) {
                CodeValues v = CodeValues.getType(data);
                previous.set(slotId, new JsonObject());
                while (!previous.isEmpty() && !previous.get(previous.size() - 1).has("type")) {
                    previous.remove(previous.size() - 1);
                }
                return v.getItem(v.fromJson(data));
            }
        }
        return null;
    }
}
