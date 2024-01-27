package hypersquare.hypersquare.dev.code.player.event;

import hypersquare.hypersquare.item.event.Event;
import hypersquare.hypersquare.item.event.EventItem;
import hypersquare.hypersquare.item.action.player.PlayerEventItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PlayerLeaveEvent implements Event {

    public ItemStack item() {
        return new EventItem()
                .setMaterial(Material.POISONOUS_POTATO)
                .setName(Component.text("Player Leave Game Event").color(NamedTextColor.RED))
                .setDescription(Component.text("Executes code when a player"),
                                Component.text("leaves the plot."))
                .build();
    }

    public String getId() {
        return "leave";
    }
    @Override
    public String getCodeblockId() {
        return "player_event";
    }

    public String getSignName() {
        return "Leave";
    }

    public String getName() {
        return "Player Leave Game Event";
    }

    @Override
    public PlayerEventItems getCategory() {
        return PlayerEventItems.PLOT_AND_SERVER_EVENTS_CATEGORY;
    }
}
