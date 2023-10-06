package hypersquare.hypersquare.commands;

import hypersquare.hypersquare.plot.Database;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.plot.PlotManager;
import hypersquare.hypersquare.utils.Utilities;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                List<String> messages = new ArrayList<>();
                messages.add(color + "&m                                       " + color);
                messages.add(color + targetName + " currently &f" + mode + color + "");
                messages.add(color2 + "→ " + color + "Server: &rNode 1");
                messages.add(color + "&m                                       " + color);
                Utilities.sendMultiMessage(player, messages);
            } else {
                int plotID = Utilities.getPlotID(target.getWorld());
                ChatColor color = ChatColor.of("#AAD4AA");
                ChatColor color2 = ChatColor.of("#2AD4D4");
                List<String> messages = new ArrayList<>();
                messages.add(color + "&m                                       " + color);
                messages.add(color + targetName + " currently &f" + mode + color + " on:");
                messages.add("");
                messages.add(color2 + "→ " + color + PlotManager.getPlotName(plotID) + "&8[" + color + plotID + "&8]");
                messages.add(color2 + "→ " + color + "Owner: &r" + Bukkit.getOfflinePlayer(UUID.fromString(PlotManager.getPlotOwner(plotID))).getName());
                messages.add(color2 + "→ " + color + "Server: &rNode " + PlotManager.getPlotNode(plotID));
                messages.add(color + "&m                                       " + color);
                Utilities.sendMultiMessage(player, messages);
            }
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
