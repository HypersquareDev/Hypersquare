package hypersquare.hypersquare.serverside.commands;

import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.serverside.plot.PlayerDatabase;
import hypersquare.hypersquare.serverside.plot.Plot;
import hypersquare.hypersquare.serverside.plot.PlotDatabase;
import hypersquare.hypersquare.serverside.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DeleteAllPlotsCommand implements CommandExecutor {
    @Override

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("hypersquare.dumplots")) {

                Utilities.sendInfo(player,"Dumped all plots");
                for (Player player1 : Bukkit.getOnlinePlayers()){
                    player1.kickPlayer(ChatColor.translateAlternateColorCodes('&',"&c&lKick from Hypersquare \n &fReason: Huge dump"));
                }
                PlotDatabase.deleteAllPlots();
                PlayerDatabase.deleteAllPlayers();
                for (int i = 0; i <= Hypersquare.lastUsedWorldNumber;i++){
                    int finalI = i;
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            try {
                                Plot.deletePlot(finalI);
                            } catch (UnknownWorldException | IOException ignored) {
                            }
                        }
                    }.runTaskLater(Hypersquare.getPlugin(Hypersquare.class), 1);

                }
                Hypersquare.lastUsedWorldNumber = 0;
            } else {
                Utilities.sendError(player,"You do not have permission to execute this command.");
            }

        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
