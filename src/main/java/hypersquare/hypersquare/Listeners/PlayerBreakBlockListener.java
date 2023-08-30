package hypersquare.hypersquare.Listeners;

import hypersquare.hypersquare.Hypersquare;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerBreakBlockListener implements Listener {
    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        if (Hypersquare.mode.get(event.getPlayer()).equals("coding")) {
            event.setCancelled(true);

        }
    }
}
