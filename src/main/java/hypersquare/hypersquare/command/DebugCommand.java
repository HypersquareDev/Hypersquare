package hypersquare.hypersquare.command;

import hypersquare.hypersquare.dev.codefile.CodeFile;
import net.luckperms.api.model.user.User;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static hypersquare.hypersquare.Hypersquare.lpPlugin;

public class DebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player player) {
            CodeFile file = new CodeFile(player.getWorld());
            player.sendMessage(file.getCodeJson().getAsString());
        }
        return true;
    }
}
