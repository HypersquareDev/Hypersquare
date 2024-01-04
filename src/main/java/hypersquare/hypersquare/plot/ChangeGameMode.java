package hypersquare.hypersquare.plot;

import com.infernalsuite.aswm.api.exceptions.CorruptedWorldException;
import com.infernalsuite.aswm.api.exceptions.NewerFormatException;
import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import com.infernalsuite.aswm.api.exceptions.WorldLockedException;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.LoadItems;
import hypersquare.hypersquare.items.MiscItems;
import hypersquare.hypersquare.listeners.PlaytimeEventExecuter;
import hypersquare.hypersquare.plot.PlayerDatabase;
import hypersquare.hypersquare.plot.Plot;
import hypersquare.hypersquare.plot.PlotDatabase;
import hypersquare.hypersquare.plot.PlotManager;
import hypersquare.hypersquare.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;

public class ChangeGameMode {
    public static void devMode(Player player, int plotID){
        String worldName = "hs." + plotID;
        try {
            Plot.loadPlot(plotID,player);

        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                 WorldLockedException ignored) {
        }



        if (player.getWorld().getName().equals(worldName)) {
            if (PlotDatabase.getRawDevs(plotID).contains(player.getUniqueId().toString()) || player.hasPermission("hypersquare.ignore.developers")) {

                if (Hypersquare.mode.get(player).equals("playing"))
                    PlaytimeEventExecuter.Leave(player);
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
            } else {
                Utilities.sendError(player, "You do not have dev permissions on this plot");
            }


        }


    }
    public static void playMode(Player player, int plotID){
        String worldName = "hs." + plotID;
        try {
            Plot.loadPlot(plotID,player);

        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                 WorldLockedException ignored) {
        }
        PlotManager.loadPlot(plotID);
        if (player.getWorld().getName().equals(worldName)) {
            if (Utilities.getPlotID(player.getWorld()) == plotID && Hypersquare.mode.get(player).equals("playing"))
                PlaytimeEventExecuter.Rejoin(player);
            if (Hypersquare.mode.get(player).equals("playing"))
                PlaytimeEventExecuter.Leave(player);
            Utilities.resetPlayerStats(player);
            PlotDatabase.updateEventsCache(plotID);
            player.closeInventory();
            player.getInventory().clear();
            player.setGameMode(GameMode.SURVIVAL);
            Hypersquare.mode.put(player, "playing");
            Utilities.sendInfo(player, "Joined game: <white>" +  PlotManager.getPlotName(plotID) + " <gray>by <reset>" + Bukkit.getOfflinePlayer(UUID.fromString(PlotManager.getPlotOwner(plotID))).getName());
            Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
            PlotDatabase.updateLocalData(plotID);
            PlotManager.loadPlot(plotID);
            PlaytimeEventExecuter.Join(player);
            PlotStats.addPlayer(plotID,player);

        }
    }

    public static void buildMode(Player player, int plotID){
        try {
            Plot.loadPlot(plotID,player);

        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                 WorldLockedException ignored) {
        }
        String worldName = "hs." + plotID;
        if (player.getWorld().getName().equals(worldName)) {
            if (PlotDatabase.getRawDevs(plotID).contains(player.getUniqueId().toString()) || player.hasPermission("hypersquare.ignore.builders")) {
                if (Hypersquare.mode.get(player).equals("playing"))
                    PlaytimeEventExecuter.Leave(player);
                Utilities.resetPlayerStats(player);
                player.closeInventory();
                player.getInventory().clear();
                player.setGameMode(GameMode.CREATIVE);
                Hypersquare.mode.put(player, "building");
                Utilities.sendInfo(player, "You are now in build mode.");
                Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
                PlotDatabase.updateLocalData(plotID);
                PlotManager.loadPlot(plotID);
            } else {
                Utilities.sendError(player,"You do not have build permissions on this plot.");
            }
        }
    }
    public static void editSpawn(Player player){
        Utilities.resetPlayerStats(player);
        player.setGameMode(GameMode.CREATIVE);
        Hypersquare.mode.put(player,"editing spawn");

    }
    public static void spawn(Player player){
        if (Hypersquare.mode.get(player).equals("playing"))
            PlaytimeEventExecuter.Leave(player);
        Utilities.resetPlayerStats(player);
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().setItem(0, MiscItems.MY_PLOTS.build());
        player.getInventory().setItem(4,MiscItems.GAME_MENU.build());
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        Hypersquare.mode.put(player,"at spawn");
        PlayerDatabase.updateLocalPlayerData(player);
        player.setHealth(20);
        player.setFoodLevel(20);
    }


}