package hypersquare.hypersquare.menu.actions;

import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.dev.codefile.CodeFileHelper;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.codefile.data.CodeData;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.menu.actions.parameter.MenuParameter;
import hypersquare.hypersquare.menu.system.Menu;
import hypersquare.hypersquare.menu.system.MenuItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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

    @Override
    public void performClick(InventoryClickEvent event) {
        if (!items.containsKey(event.getSlot())) return;
        if (items.get(event.getSlot()) instanceof MenuParameter param) {
            HumanEntity p = event.getWhoClicked();
            CodeFile file = new CodeFile(block.getWorld());
            CodeData data = file.getCodeData();
            CodeActionData action = CodeFileHelper.getActionAt(block, data);
            if (action == null) return;
            if (p.getItemOnCursor().isEmpty()) {
                p.setItemOnCursor(param.getValue(action, true));
                slot(event.getSlot(), param.updated(action));
                file.setCode(data.toJson().toString());
                return;
            }
            p.setItemOnCursor(param.replaceValue(action, p.getItemOnCursor()));
            slot(event.getSlot(), param.updated(action));
            file.setCode(data.toJson().toString());
        }
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
            p.getInventory().addItem(param.getValue(action, true));
            slot(event.getSlot(), param.updated(action));
            file.setCode(data.toJson().toString());
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
