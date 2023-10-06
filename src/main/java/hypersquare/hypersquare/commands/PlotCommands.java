package hypersquare.hypersquare.commands;

import com.infernalsuite.aswm.api.exceptions.CorruptedWorldException;
import com.infernalsuite.aswm.api.exceptions.NewerFormatException;
import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import com.infernalsuite.aswm.api.exceptions.WorldLockedException;
import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.plot.Database;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.plot.Plot;
import hypersquare.hypersquare.plot.PlotManager;
import hypersquare.hypersquare.utils.managers.ItemManager;
import hypersquare.hypersquare.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlotCommands implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (Hypersquare.mode.get(Bukkit.getPlayer(sender.getName())).equals("coding")) {
                int plotID = Utilities.getPlotID(((Player) sender).getWorld());
                Player player = (Player) sender;
                if (args.length >= 1) {
                    switch (args[0]) {
                        case "glitch":
                        case "g": {
                            ((Player) sender).getInventory().addItem(ItemManager.getItem("dev.glitch"));
                            break;
                        }
                        case "name": {
                            if (PlotManager.getPlotOwner(plotID).equals(((Player) sender).getUniqueId().toString())) {
                                if (args.length >= 2) {
                                    StringBuilder name = new StringBuilder();

                                    for (int i = 1; i <= args.length - 1; i++) {
                                        name.append(args[i]);
                                        if (i < 100) {
                                            name.append(" ");
                                        }
                                    }
                                    Database.changePlotName(Utilities.getPlotID(((Player) sender).getWorld()), String.valueOf(name));
                                } else {
                                    Utilities.sendError(player, "You cannot set the plot name to noting");
                                }
                            } else
                                Utilities.sendError((Player) sender, "Only the plot owner can do that!");
                            break;
                        }
                        case "icon": {
                            if (PlotManager.getPlotOwner(plotID).equals(((Player) sender).getUniqueId().toString())) {
                                Database.changePlotIcon(Utilities.getPlotID(((Player) sender).getWorld()), ((Player) sender).getInventory().getItemInMainHand().getType().toString());
                            } else
                                Utilities.sendError((Player) sender, "Only the plot owner can do that!");
                            break;
                        }
                        case "unclaim": {
                            if (PlotManager.getPlotOwner(plotID).equals(((Player) sender).getUniqueId().toString())) {
                                for (Player player1 : player.getWorld().getPlayers()) {
                                    ChangeGameMode.spawn(player1);
                                    Utilities.sendRedInfo(player1, "The plot that you were currently on was unclaimed.");
                                }

                                Utilities.sendInfo(player, "Plot "+ plotID + " has been unclaimed.");
                                Database.deletePlot(plotID);
                                try {
                                    Plot.deletePlot(plotID);
                                } catch (UnknownWorldException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else
                                Utilities.sendError((Player) sender, "Only the plot owner can do that!");
                            break;
                        }
                        case "help": {
                            List<String> messages = new ArrayList<>();
                            if (args.length >= 2) {
                                player.sendMessage(args);
                                switch (args[1]) {
                                    case "1": {
                                        messages.add("&3&m------&b PLOT HELP (Page 1/3) &3&m------");
                                        messages.add("&a/plot name <name>");
                                        messages.add("&b- Rename your minigame. Supports colors! (E.g, &4)");
                                        messages.add("&a/plot editname");
                                        messages.add("&b- Prepares a /plot name command with the plot's name.");
                                        messages.add("&a/plot icon");
                                        messages.add("&b- Sets your plot icon to the item in your hand.");
                                        messages.add("&a/plot tags");
                                        messages.add("&b- Change the categories your plot shows up in.");
                                        messages.add("&a/plot <dev/builder> <add/remove/list/clear> [player]");
                                        messages.add("&b- Give a player build or dev access to your plot, list who has access, or clear all builders/devs.");
                                        messages.add("&a/plot codespace add|remove");
                                        messages.add("&b- Add a glass layer to your codespace to code on. When adding a layer, you can add -c, -l, and -d for different styles.");
                                        messages.add("&a/plot setspawn");
                                        messages.add("&b- Set your plot's spawnpoint to your current location.");
                                        messages.add("&a/plot spawn");
                                        messages.add("&b- Teleports you to the plot spawn.");
                                        messages.add("&a/plot ad <purchase/[message]>");
                                        messages.add("&b- Advertise your plot.");
                                        messages.add("&e/plot help <page number>");
                                        break;
                                    }
                                    case "2": {
                                        messages.add("&3&m------&b PLOT HELP (Page 2/3) &3&m------");
                                        messages.add("&a/plot codespace <add|remove> [color] [-l] [-c]");
                                        messages.add("&b- Add another layer to your plot's code area! Use the -l flag to generate lines rather than a full layer.");
                                        messages.add("&a/plot kick <player>");
                                        messages.add("&b- Kick a player from your plot.");
                                        messages.add("&a/plot ban <player>");
                                        messages.add("&b- Bans a player from your plot.");
                                        messages.add("&a/plot banlist");
                                        messages.add("&b- List bans on your plot.");
                                        messages.add("&a/plot unban <player>");
                                        messages.add("&b- Unbans a player from your plot.");
                                        messages.add("&a/plot whitelist <on/off/add/remove> <player>");
                                        messages.add("&b- Whitelist your plot.");
                                        messages.add("&a/plot vars [name/value]");
                                        messages.add("&b- List plot variables.");
                                        messages.add("&e/plot help [page number]");
                                        break;
                                    }
                                    case "3": {
                                        messages.add("&3&m------&b PLOT HELP (Page 3/3) &3&m------");
                                        messages.add("&cDANGER ZONE");
                                        messages.add("&a/plot varpurge [name]");
                                        messages.add("&b- Clears plot variables (all if no name is specified).");
                                        messages.add("&a/plot clear");
                                        messages.add("&b- Clears the build and code areas of your plot.");
                                        messages.add("&a/plot glitch OR /plot g");
                                        messages.add("&b- Get a Glitch Stick to break glitched code blocks.");
                                        messages.add("&a/plot unclaim");
                                        messages.add("&b- Unclaim your plot.");
                                        messages.add("&e/plot help <page number>");
                                        break;

                                    }
                                }
                            } else {
                                messages.add("&3&m------&b PLOT HELP (Page 1/3) &3&m------");
                                messages.add("&a/plot name <name>");
                                messages.add("&b- Rename your minigame. Supports colors! (E.g, &4)");
                                messages.add("&a/plot editname");
                                messages.add("&b- Prepares a /plot name command with the plot's name.");
                                messages.add("&a/plot icon");
                                messages.add("&b- Sets your plot icon to the item in your hand.");
                                messages.add("&a/plot tags");
                                messages.add("&b- Change the categories your plot shows up in.");
                                messages.add("&a/plot <dev/builder> <add/remove/list/clear> [player]");
                                messages.add("&b- Give a player build or dev access to your plot, list who has access, or clear all builders/devs.");
                                messages.add("&a/plot codespace add|remove");
                                messages.add("&b- Add a glass layer to your codespace to code on. When adding a layer, you can add -c, -l, and -d for different styles.");
                                messages.add("&a/plot setspawn");
                                messages.add("&b- Set your plot's spawnpoint to your current location.");
                                messages.add("&a/plot spawn");
                                messages.add("&b- Teleports you to the plot spawn.");
                                messages.add("&a/plot ad <purchase/[message]>");
                                messages.add("&b- Advertise your plot.");
                                messages.add("&e/plot help <page number>");
                            }
                            Utilities.sendMultiMessage(player, messages);
                            break;

                        }
                        case "dev": {
                            if (PlotManager.getPlotOwner(plotID).equals(((Player) sender).getUniqueId().toString())) {
                                try {
                                    switch (args[1]) {
                                        case "add": {
                                            if (!args[2].equalsIgnoreCase(sender.getName())) {
                                                if (Bukkit.getOfflinePlayer(args[2]).hasPlayedBefore()) {
                                                    if (Database.getRawDevs(plotID).contains(Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString())) {
                                                        Utilities.sendError((Player) sender, "That player is already a dev.");
                                                    } else {
                                                        Database.addDev(plotID, Bukkit.getOfflinePlayer(args[2]).getUniqueId());
                                                        Utilities.sendInfo((Player) sender, ("&f" + Bukkit.getOfflinePlayer(args[2]).getName() + " &7now has dev permissions for " + Utilities.convertToChatColor(Database.getPlotName(plotID))));
                                                        if (Utilities.playerOnline(args[2])) {
                                                            Utilities.sendInfo(Bukkit.getPlayer(args[2]), "You now have dev permissions for " + Utilities.convertToChatColor(Database.getPlotName(plotID)));
                                                        }
                                                    }
                                                } else {
                                                    Utilities.sendError((Player) sender, "Could not find that player.");
                                                }
                                            } else {
                                                Utilities.sendError((Player) sender, "You cannot add yourself as a dev.");
                                            }
                                            break;

                                        }
                                        case "list": {
                                            String devs = "&a";
                                            for (String name : Database.getPlotDevs(plotID)) {
                                                devs = devs + Bukkit.getOfflinePlayer(UUID.fromString(name)).getName() + ", ";
                                            }

                                            if (devs.length() > 2) {
                                                devs = devs.substring(0, devs.length() - 2);
                                            }

                                            sender.sendMessage(ChatColor.AQUA + "Plot Devs: " + ChatColor.translateAlternateColorCodes('&', String.valueOf(devs)));

                                            break;
                                        }
                                        case "remove": {
                                            if (!args[2].equalsIgnoreCase(sender.getName())) {
                                                if (Bukkit.getOfflinePlayer(args[2]).hasPlayedBefore()) {
                                                    if (!Database.getRawDevs(plotID).contains(Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString())) {
                                                        Utilities.sendError((Player) sender, "That player is not a dev.");
                                                    } else {
                                                        Database.removeDev(plotID, Bukkit.getOfflinePlayer(args[2]).getUniqueId());
                                                        Utilities.sendInfo((Player) sender, ("&f" + Bukkit.getOfflinePlayer(args[2]).getName() + " &7no longer has dev permissions for " + Utilities.convertToChatColor(Database.getPlotName(plotID))));
                                                        if (Utilities.playerOnline(args[2])) {
                                                            Utilities.sendRedInfo(Bukkit.getPlayer(args[2]), "You no longer have dev permissions for " + Utilities.convertToChatColor(Database.getPlotName(plotID)));
                                                            if (Hypersquare.mode.get(Bukkit.getPlayer(args[2])).equals("coding")) {
                                                                ChangeGameMode.spawn(Bukkit.getPlayer(args[2]));
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    Utilities.sendError((Player) sender, "Could not find that player.");
                                                }
                                            } else {
                                                Utilities.sendError((Player) sender, "You cannot remove yourself as a dev.");
                                            }
                                            break;

                                        }
                                    }
                                } catch (Exception IndexOutOfBoundsException) {
                                    Utilities.sendUsageError(player, "/plot <dev/builder> <add/remove/list/clear> [player]");
                                }


                            } else
                                Utilities.sendError((Player) sender, "Only the plot owner can do that!");
                            break;
                        }
                    }
                } else {
                    Utilities.sendError(player, "Invalid command usage. /plot help");
                }


            }

            Hypersquare.plotData.put((Player) sender, Database.getPlot(String.valueOf(Bukkit.getPlayer(sender.getName()).getUniqueId().toString())));
        }
         else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
