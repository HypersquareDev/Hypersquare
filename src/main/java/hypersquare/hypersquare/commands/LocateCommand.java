package hypersquare.hypersquare.commands;

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
            Player target;
            String color = "<#AAD4AA>";
            String color2 = "<#2AD4D4>";
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
                List<String> messages = new ArrayList<>();
                messages.add(color + "<st>                                       " + color);
                messages.add(color + targetName + " currently <white>" + mode + color + " on:");
                messages.add(color2 + "→ " + color + "Server: <white>Node 1");
                messages.add(color + "<st>                                       " + color);
                Utilities.sendMultiMiniMessage(player, messages);
            } else {
                int plotID = Utilities.getPlotID(target.getWorld());
                String cmd = "<click:run_command:/join " + plotID + "><hover:show_text:'<color:#AAD4AA>Click to join'>";
                List<String> messages = new ArrayList<>();
                String plotName = PlotManager.getPlotName(plotID);
                messages.add(color + cmd + "<st>                                       " + color);
                messages.add(color + targetName + cmd + "currently <white>" + mode + color + " on:");
                messages.add("");
                messages.add(color2 + cmd + "→ " + "<white>" + plotName +  " <dark_gray>[" + color + plotID + "<dark_gray>]");
                messages.add(color2 + cmd + "→ " + color + "Owner: <white>" + Bukkit.getOfflinePlayer(UUID.fromString(PlotManager.getPlotOwner(plotID))).getName());
                messages.add(color2 + cmd + "→ " + color + "Server: <white>Node " + PlotManager.getPlotNode(plotID));
                messages.add(color + cmd + "<st>                                       " + color);
                Utilities.sendMultiMiniMessage(player, messages);
            }
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
