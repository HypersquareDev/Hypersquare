package hypersquare.hypersquare.Commands;

import hypersquare.hypersquare.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
            String type = Utilities.getPlotType(target.getWorld());
            if (type.equals("build"))
                type = "playing";
            else
                type = "coding";
            player.sendMessage(net.md_5.bungee.api.ChatColor.of("#xAAD4AA") + "                                       ");
            if (target == player)
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&fYou"+"#xAAD4AA" + " are currently &f" + type + "&x#AAD4AA on:\n"));
            else
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',target.getName() + "&fYou&x#AAD4AA is currently &f"+ type + "&x#AAD4AA on:\n"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&x#AAD4AA"+ plotID +"&8]&x#AAD4AA\n"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&x#AAD4AA&m                                       &x#AAD4AA\n"));

        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
