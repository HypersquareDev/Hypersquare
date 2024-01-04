package hypersquare.hypersquare.commands;

import hypersquare.hypersquare.utils.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestPermissions implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.hasPermission("hypersquare.test")){
            commandSender.sendMessage("AAAAAAAAAAAAAA NO U HAVE PERMS :(");
        } else {
            Utilities.sendError((Player) commandSender,"womp womp");
        }
        return true;
    }
}
