package hypersquare.hypersquare;

import hypersquare.hypersquare.dev.LoadItems;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ChangeGameMode {
    public static void devMode(Player player, int plotID){
        String worldName = "hs." + plotID +".dev";
        Plot.loadPlot(plotID,"dev", player);
        player.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
        Bukkit.getWorld(worldName).setTime(1000);
        LoadItems.devInventory(player);
        player.setGameMode(GameMode.CREATIVE);
        Hypersquare.mode.put(player,"coding");

    }
    public static void playMode(Player player, int plotID){
        String worldName = "hs." + plotID +".build";
        Plot.loadPlot(plotID,"build", player);
        player.closeInventory();
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        Hypersquare.mode.put(player,"playing");

    }

    public static void buildMode(Player player){
        int plotID = Utilities.getPlotID(player.getWorld());
        String worldName = "hs." + plotID +".build";
        player.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
        player.closeInventory();
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);
        Hypersquare.mode.put(player,"building");

    }
    public static void spawn(Player player){
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().setItem(0,ItemManager.getItem("myPlots"));
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        Hypersquare.mode.put(player,"at spawn");

    }


}
