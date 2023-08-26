package hypersquare.hypersquare;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ChangeGameMode {
    public static void devMode(Player player, int plotID){
        String worldName = "hs." + plotID +".dev";
        Location loc = new Location(Bukkit.getWorld("hs." + plotID + ".dev"), 0,-55,0);
        player.teleport(loc);
        Bukkit.getWorld(worldName).setTime(1000);
    }
    public static void playMode(Player player, int plotID){
        String worldName = "hs." + plotID +".build";
        Plot.loadPlot(plotID,"build", player);
        Location loc = new Location(Bukkit.getWorld("hs." + plotID + ".build"), 0,64,0);
        player.teleport(loc);

    }
}
