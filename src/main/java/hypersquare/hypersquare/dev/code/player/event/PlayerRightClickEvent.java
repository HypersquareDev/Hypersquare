package hypersquare.hypersquare.dev.code.player.event;

import hypersquare.hypersquare.item.Event;
import hypersquare.hypersquare.item.EventItem;
import hypersquare.hypersquare.item.PlayerEventItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PlayerRightClickEvent implements Event {
    public ItemStack item() {
        return new EventItem()
                .setMaterial(Material.DIAMOND_PICKAXE)
                .setName(Component.text("Player Right Click Event").color(NamedTextColor.AQUA))
                .setDescription(
                        Component.text("Executes code when a player"),
                        Component.text("right clicks while looking at a"),
                        Component.text("block or holding an item."))
                .setCancelable(true)
                .build();
    }

    public String getId() {
        return "right_click";
    }
    @Override
    public String getCodeblockId() {
        return "player_event";
    }

    public String getSignName() {
        return "RightClick";
    }

    public String getName() {
        return "Player Right Click Event";
    }

    @Override
    public PlayerEventItems getCategory() {
        return PlayerEventItems.CLICK_EVENTS_CATEGORY;
    }
}
