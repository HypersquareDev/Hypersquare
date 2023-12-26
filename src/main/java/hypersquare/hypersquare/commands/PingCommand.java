package hypersquare.hypersquare.commands;

import hypersquare.hypersquare.utils.Colors;
import hypersquare.hypersquare.utils.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            int ping = player.getPing();
            Utilities.sendInfo(player,"Your ping is " + Colors.PRIMARY_INFO + ping + "ms");
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
