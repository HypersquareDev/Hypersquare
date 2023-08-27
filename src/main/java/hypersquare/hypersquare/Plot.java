package hypersquare.hypersquare;

import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.exceptions.*;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import com.infernalsuite.aswm.api.world.properties.SlimeProperties;
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class Plot {

    public static void createDev(int plotID, SlimePlugin plugin){
        String worldName = "hs." + plotID +".dev";
        SlimeLoader file = plugin.getLoader("file");
        SlimePropertyMap properties = new SlimePropertyMap();
        properties.setValue(SlimeProperties.DIFFICULTY, "peaceful");
        properties.setValue(SlimeProperties.SPAWN_X, 25);
        properties.setValue(SlimeProperties.SPAWN_Y, -55);
        properties.setValue(SlimeProperties.SPAWN_Z, 4);
        SlimeWorld world = null;
        SlimeWorld cloned = null;
        try{
             world = plugin.loadWorld(file, "dev_template", false, properties);

        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldLockedException err){
            System.out.println(err.getMessage());
            return;
        }
        try {
            cloned = world.clone(worldName, file);
        } catch (WorldAlreadyExistsException | IOException err){

        }
        try{
            cloned = plugin.loadWorld(file, worldName, false, properties);
            plugin.loadWorld(cloned);

        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldLockedException  err){
            return;
        }

        Bukkit.getWorld(worldName).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_MOB_SPAWNING, false);
        Bukkit.getWorld(worldName).setSpawnLocation(25,-55,4);
    }
    public static void createBuild(int plotID, SlimePlugin plugin){
        String worldName = "hs." + plotID +".build";
        SlimeLoader file = plugin.getLoader("file");
        SlimePropertyMap properties = new SlimePropertyMap();
        properties.setValue(SlimeProperties.SPAWN_X, 0);
        properties.setValue(SlimeProperties.SPAWN_Y, 64);
        properties.setValue(SlimeProperties.SPAWN_Z, 0);

        SlimeWorld cloned = null;

        try{
            SlimeWorld world = plugin.loadWorld(file, "play_template", false, properties);
            cloned = world.clone(worldName, file);
            cloned = plugin.loadWorld(file, worldName, false, properties);
            plugin.loadWorld(cloned);
        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldLockedException | WorldAlreadyExistsException err){
            if (err instanceof WorldAlreadyExistsException){
                Hypersquare.lastUsedWorldNumber++;
                return;
            }
        }

        if  (Bukkit.getServer().getWorlds().contains(worldName)) {
            Bukkit.getWorld(worldName).setTime(1000);
            Bukkit.getWorld(worldName).setSpawnLocation(0,64,0);
            Bukkit.getWorld(worldName).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            Bukkit.getWorld(worldName).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            Bukkit.getWorld(worldName).setGameRule(GameRule.DO_MOB_SPAWNING, false);

        } else {
        }
    }
    public static void loadPlot(int plotID, String mode, Player player){
        SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        String worldName = "hs." + plotID +"." + mode;
        SlimeLoader file = plugin.getLoader("file");
        SlimePropertyMap properties = new SlimePropertyMap();
        properties.setValue(SlimeProperties.DIFFICULTY, "peaceful");
        SlimeWorld test = plugin.getWorld(worldName);
        if (!plugin.getLoadedWorlds().contains(test)){
            try {
                SlimeWorld world = plugin.loadWorld(file, worldName, false, properties);
                plugin.loadWorld(world);


            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                     WorldLockedException err) {
                if (err instanceof UnknownWorldException) {
                    player.sendMessage(ChatColor.RED + "This plot does not exist.");
                    return;
                }
                if (err instanceof CorruptedWorldException){
                    player.sendMessage(ChatColor.RED + "This world is corrupted");
                    return;
                }
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (plugin.getLoadedWorlds().contains(test)) {
                        this.cancel();
                    }

                }
            }.runTaskTimer(Hypersquare.getPlugin(Hypersquare.class),1,100);
            Location loc = new Location(Bukkit.getWorld("hs." + plotID + ".build"), 0,64,0);
            player.teleport(loc);

        } else {
            Location loc = new Location(Bukkit.getWorld("hs." + plotID + ".build"), 0,64,0);
            player.teleport(loc);
        }
    }
    public static void loadRules(int plotID, String mode){
        String worldName = "hs." + plotID +"." + mode;
        Bukkit.getWorld(worldName).setTime(1000);
        Bukkit.getWorld(worldName).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_MOB_SPAWNING, false);
    }
}
