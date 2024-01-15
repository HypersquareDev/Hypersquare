package hypersquare.hypersquare.command;

import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.plot.PlayerDatabase;
import hypersquare.hypersquare.plot.Plot;
import hypersquare.hypersquare.plot.PlotDatabase;
import hypersquare.hypersquare.plot.PlotStats;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static hypersquare.hypersquare.Hypersquare.lastUsedWorldNumber;

public class DeleteAllPlotsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("hypersquare.dumplots")) {
                Utilities.sendInfo(player, "Dumped all plots");
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    player1.kick(Component.text("Kick from Hypersquare\n").color(NamedTextColor.RED).decoration(TextDecoration.BOLD, true)
                            .append(Component.text("Reason: massive dump").color(NamedTextColor.RED))
                    );
                }
                PlotDatabase.deleteAllPlots();
                PlayerDatabase.deleteAllPlayers();
                PlotStats.deleteAllPlotStats();
                for (int i = 1; i <= lastUsedWorldNumber; i++) {
                    int finalI = i;
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            try {
                                Plot.deletePlot(finalI);
                            } catch (UnknownWorldException | IOException ignored) {
                            }
                        }
                    }.runTaskLater(Hypersquare.instance, 40);

                }
                lastUsedWorldNumber = 1;
                PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
            } else {
                Utilities.sendError(player, "You do not have permission to execute this command.");
            }
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
