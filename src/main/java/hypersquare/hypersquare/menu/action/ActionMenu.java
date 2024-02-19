package hypersquare.hypersquare.menu.action;

import com.google.gson.JsonObject;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.dev.codefile.CodeFileHelper;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.codefile.data.CodeData;
import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.dev.value.impl.VariableValue;
import hypersquare.hypersquare.menu.action.parameter.MenuParameter;
import hypersquare.hypersquare.menu.action.tag.MenuTag;
import hypersquare.hypersquare.menu.system.Menu;
import hypersquare.hypersquare.menu.system.MenuItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import oshi.util.tuples.Pair;

public class ActionMenu extends Menu {

    private final Action action;
    private Location block;
    private final CodeActionData data;

    public ActionMenu(Action action, int rows, CodeActionData data) {
        super(Component.text(action.getName()), rows);
        this.action = action;
        this.data = data;
        for (int i = 0; i < rows * 9; i++) {
            slot(i, new MenuItem(Material.GRAY_STAINED_GLASS_PANE).name(Component.empty()));
        }
    }

    public ActionMenu parameter(String id, int... slots) {
        int slotId = 0;
        for (int slot : slots) {
            this.slot(slot, new MenuParameter(action.getParameter(id), slotId, data));
            slotId++;
        }
        return this;
    }

    public ActionMenu tag(String id, int slot) {
        this.slot(slot, new MenuTag(action.getTag(id), data));
        return this;
    }

    @Override
    public void performClick(InventoryClickEvent event) {
        MenuItem menuItem = items.get(event.getSlot());
        if (menuItem == null) return;
        CodeFile file = new CodeFile(block.getWorld());
        CodeData data = file.getCodeData();
        if (menuItem instanceof MenuParameter param) {
            HumanEntity p = event.getWhoClicked();
            CodeActionData action = CodeFileHelper.getActionAt(block, data);
            if (action == null) return;
            ItemStack cursor = p.getItemOnCursor();
            if (cursor.isEmpty()) p.setItemOnCursor(param.getValue(action, true));
            else p.setItemOnCursor(param.replaceValue(action, cursor));
            slot(event.getSlot(), param.updated(action));
        }
        else if (menuItem instanceof MenuTag tag) {
            CodeActionData action = CodeFileHelper.getActionAt(block, data);
            if (action == null) return;
            VariableValue.HSVar varValue = action.tags.getOrDefault(tag.tag.id(), new Pair<>(null, null)).getB();

            ItemStack clickedItem = event.getWhoClicked().getItemOnCursor();
            JsonObject itemData = CodeValues.getVarItemData(clickedItem);
            int direction = event.isRightClick() ? -1 : 1;

            if (CodeValues.VARIABLE.isType(itemData)) {
                varValue = (VariableValue.HSVar) CodeValues.VARIABLE.fromItem(clickedItem);
                direction = 0;
                ItemStack item = event.getWhoClicked().getItemOnCursor();
                item.setAmount(item.getAmount() - 1);
                event.getWhoClicked().setItemOnCursor(item);
            }

            int position = -1;
            for (int i = 0; i < tag.tag.options().length; i++) {
                if (tag.tag.options()[i] == MenuTag.currentOption(tag.tag, action)) {
                    position = i;
                    break;
                }
            }
            if (position == -1) throw new IllegalStateException(); //TODO Add error message
            position += direction;
            position = (position + tag.tag.options().length) % tag.tag.options().length;

            action.tags.put(tag.tag.id(), new Pair<>(
                    tag.tag.options()[position].id().name(),
                    varValue
            ));
            slot(event.getSlot(), new MenuTag(tag.tag, action));
            Player player = (Player) event.getWhoClicked();
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1.5f);
        }
        file.setCode(data.toJson().toString());
    }

    @Override
    public void open(Player player) {
        throw new IllegalStateException("Regular open call on ActionMenu");
    }

    public void open(Player player, Location block) {
        this.block = block;
        super.open(player);
    }

    @Override
    public void shiftClick(InventoryClickEvent event) {
        HumanEntity p = event.getWhoClicked();
        CodeFile file = new CodeFile(block.getWorld());
        CodeData data = file.getCodeData();
        CodeActionData action = CodeFileHelper.getActionAt(block, data);
        if (action == null) return;
        if (event.getClickedInventory() == p.getInventory()) {
            ItemStack item = p.getInventory().getItem(event.getSlot());
            for (int i = 0; i < getSize(); i++) {
                if (!items.containsKey(i)) continue;
                if (items.get(i) instanceof MenuParameter param) {
                    if (!param.isEmpty(action)) continue;
                    if (!param.isValid(item)) continue;
                    param.replaceValue(action, item);
                    slot(i, param.updated(action));
                    file.setCode(data.toJson().toString());
                    p.getInventory().setItem(event.getSlot(), null);
                    return;
                }
            }
        } else if (items.get(event.getSlot()) instanceof MenuParameter param) {
            ItemStack item = param.getValue(action, true);
            if (item != null) p.getInventory().addItem(item);
            slot(event.getSlot(), param.updated(action));
            file.setCode(data.toJson().toString());
        } else if (items.get(event.getSlot()) instanceof MenuTag tag) {
            VariableValue.HSVar varValue = action.tags.getOrDefault(tag.tag.id(), new Pair<>(null, null)).getB();
            if (varValue == null) return;

            p.setItemOnCursor(CodeValues.VARIABLE.getItem(varValue));

            action.tags.put(tag.tag.id(), new Pair<>(
                    action.tags.getOrDefault(tag.tag.id(), new Pair<>(tag.tag.defaultOption().name(), null)).getA(),
                    null
            ));
            file.setCode(data.toJson().toString());
            slot(event.getSlot(), new MenuTag(tag.tag, action));
            Player player = (Player) event.getWhoClicked();
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1.5f);
        }
    }

    @Override
    public void middleClick(InventoryClickEvent event) {
        HumanEntity player = event.getWhoClicked();
        CodeFile file = new CodeFile(block.getWorld());
        CodeData data = file.getCodeData();
        CodeActionData action = CodeFileHelper.getActionAt(block, data);
        if (action == null) return;
        if (event.getClickedInventory() == player.getInventory()) {
            event.setCancelled(false);
        } else if (items.get(event.getSlot()) instanceof MenuParameter param) {
            ItemStack item = param.getValue(action, false);
            item.setAmount(64);
            player.setItemOnCursor(item);
        }
    }
}
