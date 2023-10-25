package hypersquare.hypersquare.menus.codeblockmenus.playerEvent;

import hypersquare.hypersquare.items.PlayerEventItems;
import hypersquare.hypersquare.utils.Utilities;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class PlayerEventMenu extends Gui {
    public PlayerEventMenu(Player player) {
        super(player, "playerEvents", "Player Events", 5);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        for (PlayerEventItems playerEventItem : PlayerEventItems.values()) {
            if (playerEventItem.getCategory() != null) continue;
            int slot = playerEventItem.getSlot();
            Icon item = new Icon(playerEventItem.build());

            addItem(slot, item);
            System.out.println("Added item " + playerEventItem.name() + " to slot " + slot);

            item.onClick(e -> {
                e.setCancelled(true);
                Utilities.sendClickMenuSound(player);
                for (PlayerEventItems action : PlayerEventItems.getEvents(playerEventItem)) {
                    Icon eventItem = new Icon(action.build());
                    int eventSlot = action.getSlot();
                    addItem(eventSlot, eventItem);

                    eventItem.onClick(e1 -> {
                        e1.setCancelled(true);
                        Utilities.sendClickMenuSound(player);
                        Block block = player.getTargetBlock(null, 5);
                        Utilities.setEvent(block,item,player);
                    });
                }
            });
        }
    }
}

