package hypersquare.hypersquare.menus.codeblockmenus.playerEvent.playAction;

import hypersquare.hypersquare.items.PlayerActionItems;
import hypersquare.hypersquare.utils.Utilities;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class PlayerActionMenu extends Gui {
    public PlayerActionMenu(Player player) {
        super(player, "playerActions", "Player Actions", 6);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        for (PlayerActionItems playerActionItem : PlayerActionItems.values()) {
            if (playerActionItem.getCategory() != null) continue;
            int slot = playerActionItem.getSlot();
            Icon item = new Icon(playerActionItem.build());

            addItem(slot, item);

            item.onClick(e -> {
                e.setCancelled(true);
                Utilities.sendClickMenuSound(player);
                for (PlayerActionItems action : PlayerActionItems.getActions(playerActionItem)) {
                    Icon actionItem = new Icon(action.build());
                    int actionSlot = action.getSlot();
                    addItem(actionSlot, actionItem);

                    actionItem.onClick(e1 -> {
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
