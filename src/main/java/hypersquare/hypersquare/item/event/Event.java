package hypersquare.hypersquare.item.event;

import hypersquare.hypersquare.dev.targets.Target;
import hypersquare.hypersquare.item.action.player.PlayerEventItems;
import org.bukkit.inventory.ItemStack;

public interface Event {
    ItemStack item();
    String getId();
    String getCodeblockId();
    String getSignName();
    PlayerEventItems getCategory();

    /**
     * The targets that this event is compatible with
     * @implNote The SELECTED {@link Target} is always prepended to the list
     * @return The compatible targets
     */
    Target[] compatibleTargets();
}
