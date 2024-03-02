package hypersquare.hypersquare.dev.code.player.event;

import hypersquare.hypersquare.dev.action.CancellableEvent;
import hypersquare.hypersquare.dev.target.Target;
import hypersquare.hypersquare.item.action.player.PlayerEventItems;
import hypersquare.hypersquare.item.event.EventItem;
import hypersquare.hypersquare.util.color.Colors;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PlayerPlaceBlockEvent implements CancellableEvent {
    @Override
    public ItemStack item() {
         return new EventItem()
            .setName(Component.text("Player Place Block Event").color(Colors.SKY))
            .setDescription(
                Component.text("Executes code when a player"),
                Component.text("places a block."))
            .setMaterial(Material.GRASS_BLOCK)
             .setCancellable(true)
            .build();
    }

    @Override
    public String getId() {
        return "place_block";
    }

    @Override
    public String getCodeblockId() {
        return "player_event";
    }

    @Override
    public String getSignName() {
        return "PlaceBlock";
    }

    @Override
    public PlayerEventItems getCategory() {
        return PlayerEventItems.CLICK_EVENTS_CATEGORY;
    }

    @Override
    public Target[] compatibleTargets() {
        return new Target[]{Target.DEFAULT_PLAYER};
    }
}
