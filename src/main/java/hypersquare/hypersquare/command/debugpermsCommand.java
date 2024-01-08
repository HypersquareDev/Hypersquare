package hypersquare.hypersquare.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class debugpermsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = (Player) commandSender;
        if (player.hasPermission("hypersquare.changeperms")){
            switch (args[0]){
                case "add":{
                    Player target = (Player) Bukkit.getOfflinePlayer(args[1]);

                }
                case "remove":{

                }

            }
        }
        return true;
    }
}
