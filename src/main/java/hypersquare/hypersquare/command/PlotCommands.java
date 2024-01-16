package hypersquare.hypersquare.command;

import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.plot.*;
import hypersquare.hypersquare.util.Colors;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlotCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (Hypersquare.mode.get(Bukkit.getPlayer(sender.getName())).equals("coding")) {
                int plotID = Utilities.getPlotID((player).getWorld());
                if (args.length >= 1) {
                    switch (args[0]) {
                        case "name": {
                            if (player.getUniqueId().toString().equals(PlotManager.getPlotOwner(plotID))) {
                                if (args.length >= 2) {
                                    StringBuilder name = new StringBuilder();

                                    for (int i = 1; i <= args.length - 1; i++) {
                                        name.append(args[i]);
                                        if (i < 100) {
                                            name.append(" ");
                                        }
                                    }
                                    PlotDatabase.changePlotName(Utilities.getPlotID((player).getWorld()), String.valueOf(name).strip());
                                    Utilities.sendInfo(player, "Successfully changed the plot name to " + String.valueOf(name).strip() + ".");
                                    PlotManager.loadPlot(plotID);
                                } else {
                                    Utilities.sendError(player, "You cannot set the plot name to noting");
                                }
                            } else
                                Utilities.sendError(player, "Only the plot owner can do that!");
                            break;
                        }
                        case "icon": {
                            if (player.getUniqueId().toString().equals(PlotManager.getPlotOwner(plotID))) {
                                if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                                    PlotDatabase.changePlotIcon(Utilities.getPlotID((player).getWorld()), (player).getInventory().getItemInMainHand().getType().toString());
                                    Utilities.sendInfo(player, "Successfully changed the plot icon to " + Utilities.capitalize((player).getInventory().getItemInMainHand().getType().toString()) + ".");
                                } else {
                                    Utilities.sendError(player, "You cannot set the plot's icon to nothing.");
                                }
                            } else
                                Utilities.sendError(player, "Only the plot owner can do that!");
                            break;
                        }
                        case "unclaim": {
                            if (player.getUniqueId().toString().equals(PlotManager.getPlotOwner(plotID))) {
                                World world = player.getWorld();
                                PlayerDatabase.removePlot(player.getUniqueId(), world.getPersistentDataContainer().get(new NamespacedKey(Hypersquare.instance, "plotType"), PersistentDataType.STRING).toLowerCase());
                                for (Player player1 : player.getWorld().getPlayers()) {
                                    ChangeGameMode.spawn(player1);
                                    Utilities.sendRedInfo(player1, "The plot that you were currently on was unclaimed.");
                                }

                                Utilities.sendInfo(player, "Plot " + plotID + " has been unclaimed.");
                                PlotDatabase.deletePlot(plotID);
                                try {
                                    Plot.deletePlot(plotID);
                                } catch (UnknownWorldException | IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else
                                Utilities.sendError(player, "Only the plot owner can do that!");
                            break;
                        }
                        case "dev": {
                            if (player.getUniqueId().toString().equals(PlotManager.getPlotOwner(plotID))) {
                                try {
                                    switch (args[1]) {
                                        case "add": {
                                            if (!args[2].equalsIgnoreCase(sender.getName())) {
                                                if (Bukkit.getOfflinePlayer(args[2]).hasPlayedBefore()) {
                                                    if (PlotDatabase.getRawDevs(plotID).contains(Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString())) {
                                                        Utilities.sendError(player, "That player is already a dev.");
                                                    } else {
                                                        PlotDatabase.addDev(plotID, Bukkit.getOfflinePlayer(args[2]).getUniqueId());
                                                        Utilities.sendInfo(player, ("&f" + Bukkit.getOfflinePlayer(args[2]).getName() + " &7now has dev permissions for " + Utilities.convertToChatColor(PlotDatabase.getPlotName(plotID))));
                                                        if (Utilities.playerOnline(args[2])) {
                                                            Utilities.sendInfo(Bukkit.getPlayer(args[2]), "You now have dev permissions for " + Utilities.convertToChatColor(PlotDatabase.getPlotName(plotID)));
                                                        }
                                                    }
                                                } else {
                                                    Utilities.sendError(player, "Could not find that player.");
                                                }
                                            } else {
                                                Utilities.sendError(player, "You cannot add yourself as a dev.");
                                            }
                                            break;

                                        }
                                        case "list": {
                                            StringBuilder devs = new StringBuilder("&a");
                                            for (String name : PlotDatabase.getPlotDevs(plotID)) {
                                                devs.append(Bukkit.getOfflinePlayer(UUID.fromString(name)).getName()).append(", ");
                                            }

                                            if (devs.length() > 2) {
                                                devs = new StringBuilder(devs.substring(0, devs.length() - 2));
                                            }

                                            sender.sendMessage(Component.text("Plot Devs: ").color(NamedTextColor.AQUA)
                                                    .append(Component.text(devs.toString()).color(NamedTextColor.GREEN)));

                                            break;
                                        }
                                        case "remove": {
                                            if (!args[2].equalsIgnoreCase(sender.getName())) {
                                                if (Bukkit.getOfflinePlayer(args[2]).hasPlayedBefore()) {
                                                    if (!PlotDatabase.getRawDevs(plotID).contains(Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString())) {
                                                        Utilities.sendError(player, "That player is not a dev.");
                                                    } else {
                                                        PlotDatabase.removeDev(plotID, Bukkit.getOfflinePlayer(args[2]).getUniqueId());
                                                        Utilities.sendInfo(player, ("&f" + Bukkit.getOfflinePlayer(args[2]).getName() + " &7no longer has dev permissions for " + Utilities.convertToChatColor(PlotDatabase.getPlotName(plotID))));
                                                        if (Utilities.playerOnline(args[2])) {
                                                            Utilities.sendRedInfo(Bukkit.getPlayer(args[2]), "You no longer have dev permissions for " + Utilities.convertToChatColor(PlotDatabase.getPlotName(plotID)));
                                                            if (Hypersquare.mode.get(Bukkit.getPlayer(args[2])).equals("coding")) {
                                                                ChangeGameMode.spawn(Bukkit.getPlayer(args[2]));
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    Utilities.sendError(player, "Could not find that player.");
                                                }
                                            } else {
                                                Utilities.sendError(player, "You cannot remove yourself as a dev.");
                                            }
                                            break;

                                        }
                                    }
                                } catch (Exception IndexOutOfBoundsException) {
                                    Utilities.sendUsageError(player, "/plot <dev/builder> <add/remove/list/clear> [player]");
                                }


                            } else
                                Utilities.sendError(player, "Only the plot owner can do that!");
                            break;
                        }
                        case "stats": {
                            if (Hypersquare.mode.get(player).equals("coding")) {
                                List<String> messages = new ArrayList<>();
                                messages.add(Colors.PRIMARY_INFO + "Plot stats for:");
                                messages.add("");
                                messages.add(Colors.DECORATION + "→ <reset>" + PlotManager.getPlotName(plotID) + "<reset>" + Colors.PRIMARY_INFO + " by <white>" + Bukkit.getOfflinePlayer(UUID.fromString(PlotManager.getPlotOwner(plotID))).getName() + " <dark_gray>[" + Colors.PRIMARY_INFO + plotID + "<dark_gray>]");
                                messages.add(Colors.DECORATION + "→ " + Colors.SECONDARY_INFO + "Total unique joins: <white>" + PlotStats.getTotalUniquePlayers(plotID));
                                messages.add(Colors.DECORATION + "→ " + Colors.SECONDARY_INFO + "Total playtime: <white>" + PlotStats.calculateTotalTimePlayed(plotID));

                                Utilities.sendMultiMiniMessage(player, messages);
                            }
                        }
                    }
                } else {
                    Utilities.sendError(player, "Invalid command usage.");
                }
            }
            Hypersquare.plotData.put(player, PlotDatabase.getPlot(String.valueOf(Bukkit.getPlayer(sender.getName()).getUniqueId().toString())));
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
