package hypersquare.hypersquare.commands;

import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.plot.Database;
import hypersquare.hypersquare.plot.Plot;
import hypersquare.hypersquare.utils.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DeleteAllPlotsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()){
                Utilities.sendInfo(player,"Dumped all plots");
                Database.deleteAllPlots();
            } else {
                Utilities.sendError(player,"You do not have permission to execute that command!");
            }

        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
