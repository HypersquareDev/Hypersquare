package hypersquare.hypersquare.Listeners;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.ItemManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerPlaceBlockListener implements Listener {
    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        if (Hypersquare.mode.get(event.getPlayer()).equals("dev")) {
            event.setCancelled(true);
            if (ItemManager.getItemID(event.getItemInHand()).startsWith("dev.")) {
                event.getBlock().getLocation().add(0, 0, 1).getBlock().setType(Material.OAK_WALL_SIGN);
            }
        }
    }
}
