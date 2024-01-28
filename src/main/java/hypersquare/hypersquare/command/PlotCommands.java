package hypersquare.hypersquare.command;

import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import hypersquare.hypersquare.HSKeys;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.plot.*;
import hypersquare.hypersquare.util.Colors;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.permissions.ServerOperator;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static hypersquare.hypersquare.Hypersquare.cleanMM;

public class PlotCommands implements HyperCommand {
    @Override
    public void register(CommandDispatcher<CommandSourceStack> cd) {
        for (String alias : List.of("plot", "p")) {
            cd.register(literal(alias)
                    .then(literal("name")
                            .then(argument("name", StringArgumentType.greedyString())
                                    .executes(this::setName)))
                    .then(literal("icon").executes(this::setIcon))
                    .then(literal("unclaim").executes(this::unclaimPlot))
                    .then(literal("dev")
                            .then(literal("add")
                                    .then(argument("player", StringArgumentType.string()).executes(this::addDev))
                            )
                            .then(literal("remove")
                                    .then(argument("player", StringArgumentType.string()).executes(this::addDev))
                            )
                            .then(literal("list").executes(this::listDevs))
                    )
                    .then(literal("stats").executes(this::getStats))
            );
        }
    }

    private int listDevs(CommandContext<CommandSourceStack> ctx) {
        if (ctx.getSource().getBukkitSender() instanceof Player player) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (!player.getUniqueId().toString().equals(PlotManager.getPlotOwner(plotID))) {
                Utilities.sendError(ctx.getSource().getBukkitSender(), "You are not allowed to do this.");
                return DONE;
            }
            StringBuilder devs = new StringBuilder("&a");
            for (String name : PlotDatabase.getPlotDevs(plotID)) {
                devs.append(Bukkit.getOfflinePlayer(UUID.fromString(name)).getName()).append(", ");
            }
            if (devs.length() > 2) devs = new StringBuilder(devs.substring(0, devs.length() - 2));
            player.sendMessage(Component.text("Plot Devs: ").color(NamedTextColor.AQUA)
                    .append(Component.text(devs.toString()).color(NamedTextColor.GREEN)));
        }
        return DONE;
    }

    private int addDev(CommandContext<CommandSourceStack> ctx) {
        if (ctx.getSource().getBukkitSender() instanceof Player player) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (!player.getUniqueId().toString().equals(PlotManager.getPlotOwner(plotID))) {
                Utilities.sendError(ctx.getSource().getBukkitSender(), "You are not allowed to do this.");
                return DONE;
            }
            String recipient = ctx.getArgument("player", String.class);
            if (!ctx.getArgument("player", String.class).equalsIgnoreCase(player.getName())) {
                OfflinePlayer offlineRecipentPlayer = Bukkit.getOfflinePlayer(recipient);
                if (offlineRecipentPlayer.hasPlayedBefore()) {
                    if (Objects.requireNonNull(PlotDatabase.getRawDevs(plotID)).contains(offlineRecipentPlayer.getUniqueId().toString())) {
                        // TODO: don't use getRawDevs
                        Utilities.sendError(player, "That player is already a dev.");
                    } else {
                        PlotDatabase.addDev(plotID, offlineRecipentPlayer.getUniqueId());
                        Utilities.sendInfo(player, (Hypersquare.fullMM.deserialize("<reset>" + offlineRecipentPlayer.getName() + " <gray>now has dev permissions for " + PlotDatabase.getPlotName(plotID))));
                    }
                } else Utilities.sendError(player, "Could not find that player.");
            } else Utilities.sendError(player, "You cannot add yourself as a dev.");
        }
        return DONE;
    }

    private int getStats(CommandContext<CommandSourceStack> ctx) {
        if (ctx.getSource().getBukkitSender() instanceof Player player) {
            int plotID = Utilities.getPlotID(player.getWorld());
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
        return DONE;
    }

    private int unclaimPlot(CommandContext<CommandSourceStack> ctx) {
        if (ctx.getSource().getBukkitSender() instanceof Player player) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (player.getUniqueId().toString().equals(PlotManager.getPlotOwner(plotID))) {
                World world = player.getWorld();
                if (world.getName().startsWith("hs.code.")) world = Bukkit.getWorld("hs." + Utilities.getPlotID(world));
                assert world != null;
                PlayerDatabase.removePlot(player.getUniqueId(), Objects.requireNonNull(world.getPersistentDataContainer().get(HSKeys.PLOT_TYPE, PersistentDataType.STRING)).toLowerCase());
                for (Player playerInWorld : player.getWorld().getPlayers()) {
                    ChangeGameMode.spawn(playerInWorld);
                    Utilities.sendRedInfo(playerInWorld, Component.text("The plot that you were currently on was unclaimed."));
                }

                Utilities.sendInfo(player, Component.text("Plot " + plotID + " has been unclaimed."));
                PlotDatabase.deletePlot(plotID);
                try {
                    Plot.deletePlot(plotID);
                } catch (UnknownWorldException | IOException e) {
                    throw new RuntimeException(e);
                }
            } else
                Utilities.sendError(player, "Only the plot owner can do that!");
        }
        return DONE;
    }

    private int setName(CommandContext<CommandSourceStack> ctx) {
        if (ctx.getSource().getBukkitSender() instanceof Player player) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (player.getUniqueId().toString().equals(PlotManager.getPlotOwner(plotID))) {
                String name = ctx.getArgument("name", String.class).strip();
                PlotDatabase.changePlotName(Utilities.getPlotID((player).getWorld()), name);
                Utilities.sendInfo(player, Component.text("Successfully changed the plot name to " + name + "."));
                PlotManager.loadPlot(plotID);
            } else Utilities.sendError(player, "Only the plot owner can do that!");
        } else {
            Utilities.sendError(ctx.getSource().getBukkitSender(), "Only a player can do this");
        }
        return DONE;
    }

    private int setIcon(CommandContext<CommandSourceStack> ctx) {
        if (ctx.getSource().getBukkitSender() instanceof Player player) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (!player.getUniqueId().toString().equals(PlotManager.getPlotOwner(plotID))) {
                Utilities.sendError(ctx.getSource().getBukkitSender(), "You are not allowed to do this.");
                return DONE;
            }
            String icon = player.getInventory().getItemInMainHand().getType().toString();
            PlotDatabase.changePlotIcon(plotID, icon);
            Utilities.sendInfo(player, Component.text("Successfully changed the plot icon to " + icon + "."));
            PlotManager.loadPlot(plotID);
        } else Utilities.sendError(ctx.getSource().getBukkitSender(), "Only a player can do this");
        return DONE;
    }
}

