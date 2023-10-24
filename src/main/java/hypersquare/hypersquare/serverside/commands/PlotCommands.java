package hypersquare.hypersquare.serverside.commands;

import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import hypersquare.hypersquare.serverside.plot.*;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.serverside.utils.managers.ItemManager;
import hypersquare.hypersquare.serverside.utils.Utilities;
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
                                    PlotDatabase.changePlotName(Utilities.getPlotID(((Player) sender).getWorld()), String.valueOf(name));
                                    Utilities.sendInfo(player,"Successfully changed plot name to " + name);
                                } else {
                                    Utilities.sendError(player, "You cannot set the plot name to nothing.");
                                }
                            } else
                                Utilities.sendError((Player) sender, "Only the plot owner can do that!");
                            break;
                        }
                        case "icon": {
                            if (PlotManager.getPlotOwner(plotID).equals(((Player) sender).getUniqueId().toString())) {
                                if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                                    PlotDatabase.changePlotIcon(Utilities.getPlotID(((Player) sender).getWorld()), ((Player) sender).getInventory().getItemInMainHand().getType().toString());
                                    Utilities.sendInfo(player,"Successfully changed plot icon.");
                                } else {
                                    Utilities.sendError(player,"You cannot set the plot's icon to nothing.");
                                }
                            } else
                                Utilities.sendError((Player) sender, "Only the plot owner can do that!");
                            break;
                        }
                        case "unclaim": {
                            if (PlotManager.getPlotOwner(plotID).equals(((Player) sender).getUniqueId().toString())) {
                                World world = player.getWorld();
                                PlayerDatabase.removePlot(player.getUniqueId(),world.getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"plotType"), PersistentDataType.STRING).toLowerCase());
                                for (Player player1 : player.getWorld().getPlayers()) {
                                    ChangeGameMode.spawn(player1);
                                    Utilities.sendRedInfo(player1, "The plot that you were currently on was unclaimed.");
                                }

                                Utilities.sendInfo(player, "Plot "+ plotID + " has been unclaimed.");
                                PlotDatabase.deletePlot(plotID);
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
                                        messages.add("<dark_aqua><st>------<aqua> PLOT HELP (Page 1/3) <dark_aqua><st>------");
                                        messages.add("<green>/plot name <name>");
                                        messages.add("<aqua>- Rename your minigame. Supports colors! (E.g, &4)");
                                        messages.add("<green>/plot editname");
                                        messages.add("<aqua>- Prepares a /plot name command with the plot's name.");
                                        messages.add("<green>/plot icon");
                                        messages.add("<aqua>- Sets your plot icon to the item in your hand.");
                                        messages.add("<green>/plot tags");
                                        messages.add("<aqua>- Change the categories your plot shows up in.");
                                        messages.add("<green>/plot <dev/builder> <add/remove/list/clear> [player]");
                                        messages.add("<aqua>- Give a player build or dev access to your plot, list who has access, or clear all builders/devs.");
                                        messages.add("<green>/plot codespace add|remove");
                                        messages.add("<aqua>- Add a glass layer to your codespace to code on. When adding a layer, you can add -c, -l, and -d for different styles.");
                                        messages.add("<green>/plot setspawn");
                                        messages.add("<aqua>- Set your plot's spawnpoint to your current location.");
                                        messages.add("<green>/plot spawn");
                                        messages.add("<aqua>- Teleports you to the plot spawn.");
                                        messages.add("<green>/plot ad <purchase/[message]>");
                                        messages.add("<aqua>- Advertise your plot.");
                                        messages.add("<aqua>/plot help <page number>");
                                        break;
                                    }
                                    case "2": {
                                        messages.add("<dark_aqua><st>------<aqua> PLOT HELP (Page 2/3) <dark_aqua><st>------");
                                        messages.add("<green>/plot codespace <add|remove> [color] [-l] [-c]");
                                        messages.add("<aqua>- Add another layer to your plot's code area! Use the -l flag to generate lines rather than a full layer.");
                                        messages.add("<green>/plot kick <player>");
                                        messages.add("<aqua>- Kick a player from your plot.");
                                        messages.add("<green>/plot ban <player>");
                                        messages.add("<aqua>- Bans a player from your plot.");
                                        messages.add("<green>/plot banlist");
                                        messages.add("<aqua>- List bans on your plot.");
                                        messages.add("<green>/plot unban <player>");
                                        messages.add("<aqua>- Unbans a player from your plot.");
                                        messages.add("<green>/plot whitelist <on/off/add/remove> <player>");
                                        messages.add("<aqua>- Whitelist your plot.");
                                        messages.add("<green>/plot vars [name/value]");
                                        messages.add("<aqua>- List plot variables.");
                                        messages.add("<yellow>/plot help [page number]");
                                        break;
                                    }
                                    case "3": {
                                        messages.add("<dark_aqua><st>------<aqua> PLOT HELP (Page 3/3) <dark_aqua><st>------");
                                        messages.add("<red>DANGER ZONE");
                                        messages.add("<green>/plot varpurge [name]");
                                        messages.add("<aqua>- Clears plot variables (all if no name is specified).");
                                        messages.add("<green>/plot clear");
                                        messages.add("<aqua>- Clears the build and code areas of your plot.");
                                        messages.add("<green>/plot glitch OR /plot g");
                                        messages.add("<aqua>- Get a Glitch Stick to break glitched code blocks.");
                                        messages.add("<green>/plot unclaim");
                                        messages.add("<aqua>- Unclaim your plot.");
                                        messages.add("<yellow>/plot help <page number>");
                                        break;

                                    }
                                }
                            } else {
                                messages.add("<dark_aqua><st>------<aqua> PLOT HELP (Page 1/3) <dark_aqua><st>------");
                                messages.add("<green>/plot name <name>");
                                messages.add("<aqua>- Rename your minigame. Supports colors! (E.g, &4)");
                                messages.add("<green>/plot editname");
                                messages.add("<aqua>- Prepares a /plot name command with the plot's name.");
                                messages.add("<green>/plot icon");
                                messages.add("<aqua>- Sets your plot icon to the item in your hand.");
                                messages.add("<green>/plot tags");
                                messages.add("<aqua>- Change the categories your plot shows up in.");
                                messages.add("<green>/plot <dev/builder> <add/remove/list/clear> [player]");
                                messages.add("<aqua>- Give a player build or dev access to your plot, list who has access, or clear all builders/devs.");
                                messages.add("<green>/plot codespace add|remove");
                                messages.add("<aqua>- Add a glass layer to your codespace to code on. When adding a layer, you can add -c, -l, and -d for different styles.");
                                messages.add("<green>/plot setspawn");
                                messages.add("<aqua>- Set your plot's spawnpoint to your current location.");
                                messages.add("<green>/plot spawn");
                                messages.add("<aqua>- Teleports you to the plot spawn.");
                                messages.add("<green>/plot ad <purchase/[message]>");
                                messages.add("<aqua>- Advertise your plot.");
                                messages.add("<yellow>/plot help <page number>");
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
                                                    if (PlotDatabase.getRawDevs(plotID).contains(Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString())) {
                                                        Utilities.sendError((Player) sender, "That player is already a dev.");
                                                    } else {
                                                        PlotDatabase.addDev(plotID, Bukkit.getOfflinePlayer(args[2]).getUniqueId());
                                                        Utilities.sendInfo((Player) sender, ("<reset>" + Bukkit.getOfflinePlayer(args[2]).getName() + " <gray>now has dev permissions for " + PlotDatabase.getPlotName(plotID)));
                                                        if (Utilities.playerOnline(args[2])) {
                                                            Utilities.sendInfo(Bukkit.getPlayer(args[2]), "You now have dev permissions for " + PlotDatabase.getPlotName(plotID));
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
                                            String devs = "<green>";
                                            for (String name : PlotDatabase.getPlotDevs(plotID)) {
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
                                                    if (!PlotDatabase.getRawDevs(plotID).contains(Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString())) {
                                                        Utilities.sendError((Player) sender, "That player is not a dev.");
                                                    } else {
                                                        PlotDatabase.removeDev(plotID, Bukkit.getOfflinePlayer(args[2]).getUniqueId());
                                                        Utilities.sendInfo((Player) sender, ("<reset>" + Bukkit.getOfflinePlayer(args[2]).getName() + " <gray>no longer has dev permissions for " + PlotDatabase.getPlotName(plotID)));
                                                        if (Utilities.playerOnline(args[2])) {
                                                            Utilities.sendRedInfo(Bukkit.getPlayer(args[2]), "You no longer have dev permissions for " + PlotDatabase.getPlotName(plotID));
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
            Hypersquare.plotData.put((Player) sender, PlotDatabase.getPlot(String.valueOf(Bukkit.getPlayer(sender.getName()).getUniqueId().toString())));
        }
         else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
