package hypersquare.hypersquare.command;

import hypersquare.hypersquare.util.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                Utilities.sendInfo(player, "Flight disabled.");
            } else {
                player.setAllowFlight(true);
                Utilities.sendInfo(player, "Flight enabled.");
            }
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
