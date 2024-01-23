package hypersquare.hypersquare.item;

import org.bukkit.inventory.ItemStack;

public interface Event {
    String getId();
    String getSignName();
    String getName();
    PlayerEventItems getCategory();
    ItemStack item();
}
