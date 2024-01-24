package hypersquare.hypersquare.plot;

import com.infernalsuite.aswm.api.exceptions.CorruptedWorldException;
import com.infernalsuite.aswm.api.exceptions.NewerFormatException;
import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import com.infernalsuite.aswm.api.exceptions.WorldLockedException;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.CodeItems;
import hypersquare.hypersquare.dev.Events;
import hypersquare.hypersquare.item.MiscItems;
import hypersquare.hypersquare.play.CodeExecutor;
import hypersquare.hypersquare.play.CodeSelection;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static hypersquare.hypersquare.Hypersquare.cleanMM;

public class ChangeGameMode {
    public static void devMode(Player player, int plotID, boolean keepState) {
        String worldName = "hs.code." + plotID;
        try {
            Plot.loadPlot(plotID, player);
        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                 WorldLockedException ignored) {
        }

        if (Hypersquare.mode.get(player).equals("playing")) {
            CodeExecutor.trigger(Utilities.getPlotID(player.getWorld()), Events.PLAYER_LEAVE_EVENT, new CodeSelection(player));
        }
        Utilities.resetPlayerStats(player, !keepState);
        Bukkit.getWorld(worldName).setTime(1000);
        if (!keepState) {
            Inventory inv = player.getInventory();
            inv.setItem(7, CodeItems.BLOCKS_SHORTCUT);
            inv.setItem(8, CodeItems.VALUES_INGOT);
        }
        player.setGameMode(GameMode.CREATIVE);
        player.setFlying(true);
        Hypersquare.mode.put(player, "coding");

        if (keepState && Hypersquare.lastDevLocation.containsKey(player)
                && Hypersquare.lastDevLocation.get(player).getWorld().getName().equals(worldName)) {
            player.teleport(Hypersquare.lastDevLocation.get(player));
        } else {
            player.teleport(new Location(Bukkit.getWorld(worldName), -3.5, 0, 2.5, -90, 0));
        }

        Utilities.sendInfo(player, Component.text("You are now in dev mode."));
        Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
        PlotDatabase.updateLocalData(plotID);
        PlotManager.loadPlot(plotID);
    }

    public static void devMode(Player player, int plotID) {
        devMode(player, plotID, false);
    }

    public static void playMode(Player player, int plotID) {
        String worldName = "hs." + plotID;
        try {
            Plot.loadPlot(plotID, player);
        } catch (Exception err) {
            err.printStackTrace();
            Utilities.sendRedInfo(player, Component.text("Error loading plot. Please try again later."));
            return;
        }

        int oldPlotID = Utilities.getPlotID(player.getWorld());
        String oldMode = Hypersquare.mode.get(player);

        if (oldPlotID == plotID && oldMode.equals("playing")) {
            CodeExecutor.trigger(plotID, Events.PLAYER_LEAVE_EVENT, new CodeSelection(player));
            //todo trigger rejoin event for current plot
        } else if (oldMode.equals("playing")) {
            CodeExecutor.trigger(oldPlotID, Events.PLAYER_LEAVE_EVENT, new CodeSelection(player));
        }

        String ownerName;
        try { // Edge case where the owner of the plot is not in the database
            ownerName = Bukkit.getOfflinePlayer(UUID.fromString(Objects.requireNonNull(PlotManager.getPlotOwner(plotID)))).getName();
        } catch (NullPointerException e) {
            ownerName = "Unknown"; // This should never happen!
        }

        Utilities.sendInfo(player, cleanMM.deserialize("Joined game: <white>" + PlotManager.getPlotName(plotID) + " <gray>by <reset>" + ownerName));
        PlotManager.loadPlot(plotID);

        Utilities.resetPlayerStats(player);
        PlotDatabase.updateEventsCache(plotID);
        player.closeInventory();
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
        Hypersquare.mode.put(player, "playing");

        Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));

        CodeExecutor.trigger(plotID, Events.getEvent("player_event_join"), new CodeSelection(player));

    }

    public static void buildMode(Player player, int plotID, boolean keepState) {
        try {
            Plot.loadPlot(plotID, player);
        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                 WorldLockedException ignored) {
        }
        String worldName = "hs." + plotID;
        if (Hypersquare.mode.get(player).equals("playing")) {
            CodeExecutor.trigger(Utilities.getPlotID(player.getWorld()), Events.PLAYER_LEAVE_EVENT, new CodeSelection(player));
        }
        Utilities.resetPlayerStats(player, !keepState);
        player.closeInventory();
        player.setGameMode(GameMode.CREATIVE);
        player.setFlying(true);
        Hypersquare.mode.put(player, "building");
        Utilities.sendInfo(player, Component.text("You are now in build mode."));
        Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
        PlotDatabase.updateLocalData(plotID);
        PlotManager.loadPlot(plotID);

        if (keepState && Hypersquare.lastBuildLocation.containsKey(player)
                && Hypersquare.lastBuildLocation.get(player).getWorld().getName().equals(worldName)) {
            player.teleport(Hypersquare.lastBuildLocation.get(player));
        } else {
            player.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
        }
    }

    public static void buildMode(Player player, int plotId) {
        buildMode(player, plotId, false);
    }

    public static void editSpawn(Player player) {
        Utilities.resetPlayerStats(player);
        player.setGameMode(GameMode.CREATIVE);
        Hypersquare.mode.put(player, "editing spawn");
    }

    public static void spawn(Player player) {
        if (Hypersquare.mode.get(player).equals("playing")) {
            CodeExecutor.trigger(Utilities.getPlotID(player.getWorld()), Events.PLAYER_LEAVE_EVENT, new CodeSelection(player));
        }
        Utilities.resetPlayerStats(player);
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().setItem(0, MiscItems.MY_PLOTS.build());
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        Hypersquare.mode.put(player, "at spawn");
        PlayerDatabase.updateLocalPlayerData(player);
        player.setHealth(20);
        player.setFoodLevel(20);
    }


}
