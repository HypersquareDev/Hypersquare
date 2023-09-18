package hypersquare.hypersquare.commands;

import hypersquare.hypersquare.plot.Database;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.utils.Utilities;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LocateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);
            String targetName = target.getName() + " is";
            if (target == player){
                targetName = "You are";
            }
            String mode = Hypersquare.mode.get(target);


            if (mode.equals("at spawn") || mode.equals("editing spawn")) {
                ChatColor color = ChatColor.of("#AAD4AA");
                ChatColor color2 = ChatColor.of("#2AD4D4");
                String startmessage = color + "&m                                       " + color;
                String message1 = color + targetName + " currently &f" + mode + color + "";
                String message2 = color2 + "→ " + color + "Server: &rNode 1";
                String[] messages = {startmessage, message1, message2, startmessage};
                Utilities.sendMultiMessage(player, messages);
            } else {
                int plotID = Utilities.getPlotID(target.getWorld());
                ChatColor color = ChatColor.of("#AAD4AA");
                ChatColor color2 = ChatColor.of("#2AD4D4");
                String startmessage = color + "&m                                       " + color;
                String message1 = color + targetName + " currently &f" + mode + color + " on:";
                String message2 = color2 + "→ " + color + Database.getPlotName(plotID) + "&8[" + color + plotID + "&8]";
                String message3 = color2 + "→ " + color + "Owner: &r" + Database.getPlotOwner(plotID);
                String message4 = color2 + "→ " + color + "Server: &rNode " + Database.getPlotNode(plotID);
                String[] messages = {startmessage, message1, "", message2, message3, message4, startmessage};
                Utilities.sendMultiMessage(player, messages);
            }
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
