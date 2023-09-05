package hypersquare.hypersquare.listeners;

import hypersquare.hypersquare.Hypersquare;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        Hypersquare.lastDeathLoc.put(player, player.getWorld());
        Bukkit.getLogger().info(Hypersquare.lastDeathLoc.toString());
    }
}
