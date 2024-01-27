package hypersquare.hypersquare.item.event;

import hypersquare.hypersquare.item.action.player.PlayerEventItems;
import org.bukkit.inventory.ItemStack;

public interface Event {
    String getId();
    String getCodeblockId();
    String getSignName();
    String getName();
    PlayerEventItems getCategory();
    ItemStack item();
}
