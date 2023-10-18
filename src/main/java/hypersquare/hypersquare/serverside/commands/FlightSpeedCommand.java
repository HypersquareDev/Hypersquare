package hypersquare.hypersquare.serverside.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlightSpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.setFlySpeed((float) Integer.parseInt(args[0])/1000);
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
