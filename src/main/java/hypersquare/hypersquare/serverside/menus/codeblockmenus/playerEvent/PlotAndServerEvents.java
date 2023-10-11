package hypersquare.hypersquare.serverside.menus.codeblockmenus.playerEvent;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.serverside.utils.Utilities;
import hypersquare.hypersquare.serverside.utils.managers.ItemManager;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlotAndServerEvents extends Gui {
    public PlotAndServerEvents(Player player) {
        super(player, "plotEvents", "Plot and server events", 5);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        for (String itemStackKey : ItemManager.registeredItems().keySet()) {
            if (itemStackKey.contains("player_event.plot_and_server_events.")) {
                Icon item = new Icon(ItemManager.getItem(itemStackKey));
                int slot = ItemManager.getItem(itemStackKey).getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "slot"), PersistentDataType.INTEGER);
                addItem(slot, item);

                item.onClick(e -> {
                    e.setCancelled(true);
                    Utilities.sendClickMenuSound(player);
                    Block block = player.getTargetBlock(null, 5);
                    Utilities.setEvent(block,item,player);

                });
            }
        }

    }
}
