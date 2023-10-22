package hypersquare.hypersquare.serverside.commands;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.serverside.plot.PlotManager;
import hypersquare.hypersquare.serverside.utils.Utilities;
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
            Player target;
            if (args.length > 0) {
                target = Bukkit.getPlayer(args[0]);
            } else {
                target = player;
            }
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
                String color = "<color:#AAD4AA>";
                String color2 = "<color:#2AD4D4>";
                List<String> messages = new ArrayList<>();
                String plotName = PlotManager.getPlotName(plotID);
                plotName = Utilities.replaceHexWithColorTag(plotName);
                messages.add(color + "<click:run_command:/join " + plotID + "><hover:show_text:'<color:#AAD4AA>Click to join'><st>                                       " + color);
                messages.add(color + targetName + "<click:run_command:/join " + plotID + "><hover:show_text:'<color:#AAD4AA>Click to join'> currently <white>" + mode + color + " on:");
                messages.add("");
                messages.add(color2 + "<click:run_command:/join " + plotID + "><hover:show_text:'<color:#AAD4AA>Click to join'>→ " + color + plotName +  " <dark_gray>[" + color + plotID + "<dark_gray>]");
                messages.add(color2 + "<click:run_command:/join " + plotID + "><hover:show_text:'<color:#AAD4AA>Click to join'>→ " + color + "Owner: <white>" + Bukkit.getOfflinePlayer(UUID.fromString(PlotManager.getPlotOwner(plotID))).getName());
                messages.add(color2 + "<click:run_command:/join " + plotID + "><hover:show_text:'<color:#AAD4AA>Click to join'>→ " + color + "Server: <white>Node " + PlotManager.getPlotNode(plotID));
                messages.add(color + "<click:run_command:/join " + plotID + "><hover:show_text:'<color:#AAD4AA>Click to join'><st>                                       " + color);
                Utilities.sendMultiMiniMessage(player, messages);
            }
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
