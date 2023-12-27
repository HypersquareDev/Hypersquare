package hypersquare.hypersquare.command;

import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.util.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuildCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            int plotID = Utilities.getPlotID(player.getWorld());
            if (plotID != 0) {
                ChangeGameMode.buildMode(player, plotID);
            } else {
                Utilities.sendError(player,"You must be on a plot!");
            }
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
