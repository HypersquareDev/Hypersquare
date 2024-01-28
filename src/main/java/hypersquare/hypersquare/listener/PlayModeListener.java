package hypersquare.hypersquare.listener;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.Events;
import hypersquare.hypersquare.play.CodeExecutor;
import hypersquare.hypersquare.play.CodeSelection;
import hypersquare.hypersquare.util.Utilities;
import io.papermc.paper.event.player.PlayerArmSwingEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayModeListener implements Listener {
    @EventHandler
    public void playerInteractListener(PlayerInteractEvent event) {
        if (cannotExecute(event.getPlayer())) return;
        if (event.getPlayer().getGameMode() == GameMode.ADVENTURE) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            CodeExecutor.trigger(getPlayerPlotId(event.getPlayer()), Events.PLAYER_RIGHT_CLICK, new CodeSelection(event.getPlayer()));
        }
    }

    @EventHandler
    public void playerSwingArmListener(PlayerArmSwingEvent event) {
        if (cannotExecute(event.getPlayer())) return;
        if (event.getPlayer().getGameMode() != GameMode.ADVENTURE) return;
        if (event.getAnimationType() == PlayerAnimationType.OFF_ARM_SWING) return;

        CodeExecutor.trigger(getPlayerPlotId(event.getPlayer()), Events.PLAYER_RIGHT_CLICK, new CodeSelection(event.getPlayer()));
    }

    private boolean cannotExecute(Player player) {
        return !Hypersquare.mode.get(player).equals("playing");
    }

    private int getPlayerPlotId(Player player) {
        return Utilities.getPlotID(player.getWorld());
    }
}
