package hypersquare.hypersquare.serverside.plot;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.serverside.listeners.PlaytimeEventExecuter;
import hypersquare.hypersquare.serverside.utils.managers.ItemManager;
import hypersquare.hypersquare.serverside.utils.Utilities;
import hypersquare.hypersquare.serverside.LoadItems;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ChangeGameMode {
    public static void devMode(Player player, int plotID){
        String worldName = "hs." + plotID;
        Plot.loadPlot(plotID, player);
        if (player.getWorld().getName().equals(worldName)) {
            if (Hypersquare.mode.get(player).equals("playing"))
            PlaytimeEventExecuter.Leave(player);
            Utilities.resetPlayerStats(player);
            Bukkit.getWorld(worldName).setTime(1000);
            LoadItems.devInventory(player);
            player.setGameMode(GameMode.CREATIVE);
            Hypersquare.mode.put(player,"coding");
            player.teleport(new Location(Bukkit.getWorld(worldName),-10,0,10,-90,0));
            Utilities.sendInfo(player, "You are now in dev mode.");
            Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
            PlotDatabase.updateLocalData(plotID);
            PlotManager.loadPlot(plotID);


        }


    }
    public static void playMode(Player player, int plotID){
        String worldName = "hs." + plotID;
        Plot.loadPlot(plotID, player);
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
            Utilities.sendInfo(player, "Joined game: " +  PlotManager.getPlotName(plotID) + " <gray>by <reset>" + Bukkit.getOfflinePlayer(UUID.fromString(PlotManager.getPlotOwner(plotID))).getName());
            Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
            PlotDatabase.updateLocalData(plotID);
            PlotManager.loadPlot(plotID);
            PlaytimeEventExecuter.Join(player);


        }

    }

    public static void buildMode(Player player, int plotID){
        Plot.loadPlot(plotID, player);
        String worldName = "hs." + plotID;
        if (player.getWorld().getName().equals(worldName)) {
            if (Hypersquare.mode.get(player).equals("playing"))
                PlaytimeEventExecuter.Leave(player);
            Utilities.resetPlayerStats(player);
            player.closeInventory();
            player.getInventory().clear();
            player.setGameMode(GameMode.CREATIVE);
            Hypersquare.mode.put(player,"building");
            Utilities.sendInfo(player, "You are now in build mode.");
            Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
            PlotDatabase.updateLocalData(plotID);
            PlotManager.loadPlot(plotID);
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
        player.getInventory().setItem(0, ItemManager.getItem("myPlots"));
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        Thread thread = new Thread(() -> {
            PlayerDatabase.updateLocalPlayerData(player);
        });
        thread.start();
        Hypersquare.mode.put(player,"at spawn");
        player.setHealth(20);
        player.setFoodLevel(20);
    }


}
