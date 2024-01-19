package hypersquare.hypersquare.listener;

import hypersquare.hypersquare.Hypersquare;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // Remove player from the hashmaps
        // TODO: player leave plot logic
        Hypersquare.lastDeathLoc.remove(player);
        Hypersquare.cooldownMap.remove(player.getUniqueId());
        Hypersquare.localPlayerData.remove(player.getUniqueId());
        Hypersquare.mode.remove(player);
        Hypersquare.plotData.remove(player);
        Hypersquare.lastSwapHands.remove(player);
        Hypersquare.lastDevLocation.remove(player);
        Hypersquare.lastBuildLocation.remove(player);

        event.quitMessage(Component.text(player.getName() + " left.").color(NamedTextColor.GRAY));
    }
}
