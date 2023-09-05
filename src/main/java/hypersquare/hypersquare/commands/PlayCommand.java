package hypersquare.hypersquare.commands;

import hypersquare.hypersquare.ChangeGameMode;
import hypersquare.hypersquare.Utilities;
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
            int plotID = Utilities.getPlotID(player.getWorld());
            ChangeGameMode.playMode(player,plotID);

        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
