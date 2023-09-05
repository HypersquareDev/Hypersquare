package hypersquare.hypersquare;

import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import hypersquare.hypersquare.dev.LoadItems;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ChangeGameMode {
    public static void devMode(Player player, int plotID){
        String worldName = "hs." + plotID;
        Plot.loadPlot(plotID, player);
        Bukkit.getWorld(worldName).setTime(1000);
        LoadItems.devInventory(player);
        player.setGameMode(GameMode.CREATIVE);
        Hypersquare.mode.put(player,"coding");
        player.teleport(new Location(Bukkit.getWorld(worldName),-10,0,10,-90,0));

    }
    public static void playMode(Player player, int plotID){
        String worldName = "hs." + plotID;
        Plot.loadPlot(plotID, player);
        if (player.getWorld().getName().equals(worldName)) {
            player.closeInventory();
            player.getInventory().clear();
            player.setGameMode(GameMode.SURVIVAL);
            Hypersquare.mode.put(player, "playing");
        }

    }

    public static void buildMode(Player player, int plotID){
        Plot.loadPlot(plotID, player);
        String worldName = "hs." + plotID;
        player.closeInventory();
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);
        Hypersquare.mode.put(player,"building");

    }
    public static void editSpawn(Player player){
        player.setGameMode(GameMode.CREATIVE);
        Hypersquare.mode.put(player,"editing spawn");

    }
    public static void spawn(Player player){
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().setItem(0,ItemManager.getItem("myPlots"));
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        Hypersquare.mode.put(player,"at spawn");
        player.setHealth(20);
        player.setFoodLevel(20);


    }


}
