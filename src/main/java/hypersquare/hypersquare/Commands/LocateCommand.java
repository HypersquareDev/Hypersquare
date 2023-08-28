package hypersquare.hypersquare.Commands;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.Utilities;
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
            int plotID = Utilities.getPlotID(target.getWorld());
            String targetName = target.getName() + "is";
            if (target == player){
                targetName = "You are";
            }
            String mode = Hypersquare.mode.get(player);
            ChatColor color = ChatColor.of("#AAD4AA");
            ChatColor color2 = ChatColor.of("#2AD4D4");
            String startmessage = color + "&m                                       " + color;
            String message1 = color + targetName + " currently &f" + mode + color + " on:";
            String message2 = color2 + "→ " + color + "Insert plot name here XD &8[" + color + plotID + "&8]";
            String message3 = color2 + "→ " + color + "Owner: &fInsert owner here";
            String message4 = color2 + "→ " + color + "Server: &fNode 100000";

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',startmessage));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',message1));
            player.sendMessage("");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',message2));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',message3));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',message4));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',startmessage));



        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
