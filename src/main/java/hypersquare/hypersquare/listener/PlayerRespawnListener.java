package hypersquare.hypersquare.listener;

import hypersquare.hypersquare.Hypersquare;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        event.setRespawnLocation(Hypersquare.lastDeathLoc.get(player).getSpawnLocation());
        Hypersquare.lastDeathLoc.remove(player);
    }
}
