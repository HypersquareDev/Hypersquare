package hypersquare.hypersquare.Commands;

import hypersquare.hypersquare.Database;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.ItemManager;
import hypersquare.hypersquare.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
                            sender.sendMessage(ChatColor.RED + "Only the plot owner can do this");
                        break;
                    }
                    case "icon":{
                        if (Database.doesPlayerOwnPlot(String.valueOf(Bukkit.getPlayer(sender.getName()).getUniqueId())))
                        {
                            Database.changePlotIcon(Utilities.getPlotID(((Player) sender).getWorld()),((Player) sender).getInventory().getItemInMainHand().getType().toString());
                        } else
                            sender.sendMessage(ChatColor.RED + "Only the plot owner can do this");
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
