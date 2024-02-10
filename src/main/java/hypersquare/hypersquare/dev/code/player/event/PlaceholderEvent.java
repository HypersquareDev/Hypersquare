package hypersquare.hypersquare.dev.code.player.event;

import hypersquare.hypersquare.item.action.player.PlayerEventItems;
import hypersquare.hypersquare.item.event.Event;
import hypersquare.hypersquare.item.event.EventItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PlaceholderEvent implements Event {
    @Override
    public ItemStack item() {
        return new EventItem()
            .setName(Component.text("Placeholder Event"))
            .setMaterial(Material.BARRIER)
            .build();
    }

    @Override
    public String getId() {
        return "placeholder";
    }

    @Override
    public String getCodeblockId() {
        return "player_event";
    }

    @Override
    public String getSignName() {
        return "Placeholder";
    }

    @Override
    public PlayerEventItems getCategory() {
        return PlayerEventItems.DEATH_EVENTS_CATEGORY;
    }
}
