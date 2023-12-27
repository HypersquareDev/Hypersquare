package hypersquare.hypersquare.command;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.util.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!Hypersquare.mode.get(player).equals("at spawn")) {
                int plotID = Utilities.getPlotID(player.getWorld());
                ChangeGameMode.playMode(player, plotID);
            }
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
