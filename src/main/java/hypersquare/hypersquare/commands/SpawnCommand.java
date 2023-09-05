package hypersquare.hypersquare.commands;

import hypersquare.hypersquare.ChangeGameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ChangeGameMode.spawn(player);
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
