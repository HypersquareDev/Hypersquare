package hypersquare.hypersquare.command;

import hypersquare.hypersquare.plot.PlayerDatabase;
import hypersquare.hypersquare.util.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GivePlotsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("hypersquare.giveplots")) {
                if (args.length >= 3){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null){
                            PlayerDatabase.increaseMaxPlots(target.getUniqueId(), args[1], Integer.parseInt(args[2]));
                            String plotsmessage = "plot";
                            if (Integer.parseInt(args[2]) > 1)
                                plotsmessage = "plots";
                            Utilities.sendInfo(player,"Gave " + args[2] + " "+ args[1] + " " +plotsmessage+ " to " + target.getName());
                            Utilities.sendInfo(target,"Recieved " + args[2] + " "+ args[1] + " " +plotsmessage+ " from " + sender.getName());
                    } else {
                        Utilities.sendError(player,"That player is not online.");
                    }
                } else {
                    Utilities.sendUsageError(player,"/giveplot [Player] [Type] [Amount]");
                }
            } else {
                Utilities.sendError(player,"You do not have permission to execute this command.");
            }
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
