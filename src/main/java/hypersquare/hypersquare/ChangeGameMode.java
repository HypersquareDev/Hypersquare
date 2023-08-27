package hypersquare.hypersquare;

import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import com.infernalsuite.aswm.api.exceptions.WorldLockedException;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;

import java.io.IOException;
import java.util.List;

public class ChangeGameMode {
    public static void devMode(Player player, int plotID){
        String worldName = "hs." + plotID +".dev";
        Plot.loadPlot(plotID,"dev", player);
        Location loc = new Location(Bukkit.getWorld("hs." + plotID + ".dev"), 0,-55,0);
        player.teleport(loc);
        Bukkit.getWorld(worldName).setTime(1000);
        LoadItems.devItems(player);
    }
    public static void playMode(Player player, int plotID){
        String worldName = "hs." + plotID +".build";
        Plot.loadPlot(plotID,"build", player);
    }


}
