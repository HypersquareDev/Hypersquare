package hypersquare.hypersquare.menus;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.utils.Utilities;
import hypersquare.hypersquare.utils.managers.ItemManager;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class PlotAndServerEvents extends Gui {
    public PlotAndServerEvents(Player player) {
        super(player, "plotEvents", "Plot and server events", 5);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        for (String itemStackKey : ItemManager.registeredItems().keySet()) {
            if (itemStackKey.startsWith("player_event_categories.Plot_and_Server_Events")) {

                ItemStack item = ItemManager.getItem(itemStackKey);
                int slot = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"slot"), PersistentDataType.INTEGER);
                addItem(slot, ItemManager.getItem(itemStackKey));
                Icon icon = new Icon(ItemManager.getItem(itemStackKey));

            }
        }
    }
}
