package hypersquare.hypersquare.Listeners;

import hypersquare.hypersquare.Hypersquare;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Bukkit.broadcastMessage("HELLLOOOOO");
        Player player = event.getEntity();
        Hypersquare.lastDeathLoc.put(player, player.getWorld());
        Bukkit.getLogger().info(Hypersquare.lastDeathLoc.toString());
    }
}
