package hypersquare.hypersquare.command;

import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.util.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EditSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("hypersquare.editspawn")) {
                ChangeGameMode.editSpawn(player);
            } else {
                Utilities.sendError(player, "You do not have permission to execute this command.");
            }
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
