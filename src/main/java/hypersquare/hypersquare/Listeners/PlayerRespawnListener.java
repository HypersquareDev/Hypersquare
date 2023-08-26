package hypersquare.hypersquare.Listeners;

import hypersquare.hypersquare.Hypersquare;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerRespawnListener implements Listener {
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        event.setRespawnLocation(Hypersquare.lastDeathLoc.get(player).getSpawnLocation());
        Hypersquare.lastDeathLoc.remove(player.getName());
    }
}
