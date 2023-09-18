package hypersquare.hypersquare.commands;

import hypersquare.hypersquare.plot.Database;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.utils.managers.ItemManager;
import hypersquare.hypersquare.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Plot implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (Hypersquare.mode.get(Bukkit.getPlayer(sender.getName())).equals("coding"))
            {
                switch (args[0]){
                    case "glitch":
                    case "g": {
                        ((Player) sender).getInventory().addItem(ItemManager.getItem("dev.glitch"));
                        break;
                    }
                    case "name":{
                        if (Database.doesPlayerOwnPlot(String.valueOf(Bukkit.getPlayer(sender.getName()).getUniqueId())))
                        {
                            StringBuilder name = new StringBuilder();

                            for (int i = 1; i <= args.length-1; i++) {
                                name.append(args[i]);
                                if (i < 100) {
                                    name.append(" ");
                                }
                            }
                            Database.changePlotName(Utilities.getPlotID(((Player) sender).getWorld()), String.valueOf(name));
                        } else
                            Utilities.sendError((Player) sender,"Only the plot owner can do that!");
                        break;
                    }
                    case "icon":{
                        if (Database.doesPlayerOwnPlot(String.valueOf(Bukkit.getPlayer(sender.getName()).getUniqueId())))
                        {
                            Database.changePlotIcon(Utilities.getPlotID(((Player) sender).getWorld()),((Player) sender).getInventory().getItemInMainHand().getType().toString());
                        } else
                            Utilities.sendError((Player) sender,"Only the plot owner can do that!");
                        break;
                    }
                    case "dev":{
                        if (Database.doesPlayerOwnPlot(String.valueOf(Bukkit.getPlayer(sender.getName()).getUniqueId())))
                        {
                            int plotID = Utilities.getPlotID(((Player) sender).getWorld());

                            switch (args[1]){
                                case "add":{
                                    if (Bukkit.getOfflinePlayer(args[2]).hasPlayedBefore()){
                                        if (Database.getRawDevs(plotID).contains(Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString()))
                                        {
                                            Utilities.sendError((Player) sender,"That player is already a dev.");
                                        } else {
                                            Database.addDev(plotID, Bukkit.getOfflinePlayer(args[2]).getUniqueId());
                                            Utilities.sendInfo((Player) sender,("&f" +Bukkit.getOfflinePlayer(args[2]).getName() + " &7now has dev permissions for " + Utilities.convertToChatColor(Database.getPlotName(plotID))));
                                        }
                                    } else {
                                        Utilities.sendError((Player) sender,"Could not find that player.");
                                    }
                                    break;
                                }
                                case "list":{
                                    String devs = "&a";
                                    for (String name : Database.getPlotDevs(plotID)){
                                        devs = devs + ", " + Bukkit.getOfflinePlayer(UUID.fromString(name)).getName();
                                    }
                                    sender.sendMessage(ChatColor.BLUE + "Plot Devs: " + ChatColor.translateAlternateColorCodes('&',devs));
                                    break;
                                }
                            }
                        } else
                            Utilities.sendError((Player) sender,"Only the plot owner can do that!");
                        break;
                    }
                }
                Hypersquare.plotData.put((Player) sender,Database.getPlot(String.valueOf(Bukkit.getPlayer(sender.getName()).getUniqueId().toString())));
            }

        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
