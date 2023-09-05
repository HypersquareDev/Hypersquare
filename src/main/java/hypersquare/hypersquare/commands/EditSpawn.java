package hypersquare.hypersquare.commands;

import hypersquare.hypersquare.ChangeGameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EditSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (sender.isOp()) {
                Player player = (Player) sender;
                ChangeGameMode.editSpawn(player);
            }
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
