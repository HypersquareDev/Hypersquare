package hypersquare.hypersquare.play;

import hypersquare.hypersquare.plot.PlotDatabase;
import hypersquare.hypersquare.util.PlotUtilities;
import hypersquare.hypersquare.util.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CodeError {
    public static void sendError(int plotId, CodeErrorType type) {
        for (String uuid : PlotDatabase.getPlotDevs(plotId)) {
            Player player = Bukkit.getPlayer(UUID.fromString(uuid));
            if (player == null || PlotUtilities.getPlotId(player.getWorld()) != plotId) continue;
            // TODO: Nicer message styling
            // TODO: Add location to error messages (clicking message to tp as well)
            Utilities.sendError(player, type.message);
        }
    }
}
