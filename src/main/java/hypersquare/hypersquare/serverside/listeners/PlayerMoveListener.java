package hypersquare.hypersquare.serverside.listeners;

import hypersquare.hypersquare.serverside.plot.RestrictMovement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.IOException;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void playerMove(PlayerMoveEvent event) throws IOException {
        Player player = event.getPlayer();

        RestrictMovement.movementCheck(player);

    }
}
