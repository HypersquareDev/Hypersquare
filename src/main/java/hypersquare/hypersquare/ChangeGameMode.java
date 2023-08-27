package hypersquare.hypersquare;

import hypersquare.dev.LoadItems;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ChangeGameMode {
    public static void devMode(Player player, int plotID){
        String worldName = "hs." + plotID +".dev";
        Plot.loadPlot(plotID,"dev", player);
        player.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
        Bukkit.getWorld(worldName).setTime(1000);
        LoadItems.devItems(player);
        player.setGameMode(GameMode.CREATIVE);
    }
    public static void playMode(Player player, int plotID){
        String worldName = "hs." + plotID +".build";
        Plot.loadPlot(plotID,"build", player);
    }
    public static void spawn(Player player){
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().setItem(0,ItemManager.getItem("myPlots"));
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
    }


}
