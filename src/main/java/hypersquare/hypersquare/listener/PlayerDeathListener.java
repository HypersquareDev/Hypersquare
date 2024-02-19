package hypersquare.hypersquare.listener;

import hypersquare.hypersquare.Hypersquare;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Hypersquare.lastDeathLoc.put(player, player.getWorld());
        Hypersquare.logger().info(Hypersquare.lastDeathLoc.toString());
        event.deathMessage(Component.text(""));
    }
}
