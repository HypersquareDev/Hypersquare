package hypersquare.hypersquare.plot;

import com.infernalsuite.aswm.api.exceptions.CorruptedWorldException;
import com.infernalsuite.aswm.api.exceptions.NewerFormatException;
import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import com.infernalsuite.aswm.api.exceptions.WorldLockedException;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.LoadItems;
import hypersquare.hypersquare.item.MiscItems;
import hypersquare.hypersquare.listener.PlaytimeEventExecuter;
import hypersquare.hypersquare.util.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class ChangeGameMode {
    public static void devMode(Player player, int plotID) {
        String worldName = "hs." + plotID;
        try {
            Plot.loadPlot(plotID, player);
        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                 WorldLockedException ignored) {
        }


        if (player.getWorld().getName().equals(worldName)) {
            if (Hypersquare.mode.get(player).equals("playing"))
                PlaytimeEventExecuter.leave(player);
            Utilities.resetPlayerStats(player);
            Bukkit.getWorld(worldName).setTime(1000);
            LoadItems.devInventory(player);
            player.setGameMode(GameMode.CREATIVE);
            Hypersquare.mode.put(player, "coding");
            player.teleport(new Location(Bukkit.getWorld(worldName), -10, 0, 10, -90, 0));
            Utilities.sendInfo(player, "You are now in dev mode.");
            Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
            PlotDatabase.updateLocalData(plotID);
            PlotManager.loadPlot(plotID);
        }
    }

    public static void playMode(Player player, int plotID) {
        String worldName = "hs." + plotID;
        try {
            Plot.loadPlot(plotID, player);
        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                 WorldLockedException ignored) {
            Utilities.sendRedInfo(player, "Error loading plot. Please try again later.");
        }
        PlotManager.loadPlot(plotID);
        if (player.getWorld().getName().equals(worldName)) {
            if (Utilities.getPlotID(player.getWorld()) == plotID && Hypersquare.mode.get(player).equals("playing"))
                PlaytimeEventExecuter.Rejoin(player);
            if (Hypersquare.mode.get(player).equals("playing"))
                PlaytimeEventExecuter.leave(player);
            Utilities.resetPlayerStats(player);
            PlotDatabase.updateEventsCache(plotID);
            player.closeInventory();
            player.getInventory().clear();
            player.setGameMode(GameMode.SURVIVAL);
            Hypersquare.mode.put(player, "playing");
            String ownerName;
            try { // Edge case where the owner of the plot is not in the database
                ownerName = Bukkit.getOfflinePlayer(UUID.fromString(Objects.requireNonNull(PlotManager.getPlotOwner(plotID)))).getName();
            } catch (NullPointerException e) {
                ownerName = "Unknown"; // This should never happen!
            }
            Utilities.sendInfo(player, "Joined game: <white>" + PlotManager.getPlotName(plotID) + " <gray>by <reset>" + ownerName);
            Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
            PlotDatabase.updateLocalData(plotID);
            PlotManager.loadPlot(plotID);
            PlaytimeEventExecuter.Join(player);
            PlotStats.addPlayer(plotID, player);
        }
    }

    public static void buildMode(Player player, int plotID) {
        try {
            Plot.loadPlot(plotID, player);
        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                 WorldLockedException ignored) {
        }
        String worldName = "hs." + plotID;
        if (player.getWorld().getName().equals(worldName)) {
            if (Hypersquare.mode.get(player).equals("playing"))
                PlaytimeEventExecuter.leave(player);
            Utilities.resetPlayerStats(player);
            player.closeInventory();
            player.getInventory().clear();
            player.setGameMode(GameMode.CREATIVE);
            Hypersquare.mode.put(player, "building");
            Utilities.sendInfo(player, "You are now in build mode.");
            Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
            PlotDatabase.updateLocalData(plotID);
            PlotManager.loadPlot(plotID);
        }
    }

    public static void editSpawn(Player player) {
        Utilities.resetPlayerStats(player);
        player.setGameMode(GameMode.CREATIVE);
        Hypersquare.mode.put(player, "editing spawn");
    }

    public static void spawn(Player player) {
        if (Hypersquare.mode.get(player).equals("playing"))
            PlaytimeEventExecuter.leave(player);
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