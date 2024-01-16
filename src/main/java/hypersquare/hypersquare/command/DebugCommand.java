package hypersquare.hypersquare.command;

import hypersquare.hypersquare.dev.codefile.CodeFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player player) {
            CodeFile file = new CodeFile(player.getWorld());
            player.sendMessage(file.getCodeData().toJson().getAsString());
        }
        return true;
    }
}
