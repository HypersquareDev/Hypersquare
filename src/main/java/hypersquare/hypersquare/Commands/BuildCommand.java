package hypersquare.hypersquare.Commands;

import hypersquare.hypersquare.ChangeGameMode;
import hypersquare.hypersquare.Hypersquare;
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
            ChangeGameMode.buildMode(player);
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
