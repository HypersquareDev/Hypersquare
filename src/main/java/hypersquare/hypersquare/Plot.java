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
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.logging.Logger;

public class Plot {

    public static void createDev(int plotID, SlimePlugin plugin){
        String worldName = "hs." + plotID +".dev";
        SlimeLoader file = plugin.getLoader("file");
        SlimePropertyMap properties = new SlimePropertyMap();
        properties.setValue(SlimeProperties.DIFFICULTY, "peaceful");
        SlimeWorld world = null;
        SlimeWorld cloned = null;
        try{
             world = plugin.loadWorld(file, "dev_template", false, properties);

        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldLockedException err){
            System.out.println(err.getMessage());
        }
        try {
            cloned = world.clone(worldName, file);
        } catch (WorldAlreadyExistsException | IOException err){
            System.out.println(err.getMessage());
        }
        try{
            cloned = plugin.loadWorld(file, worldName, false, properties);
            plugin.loadWorld(cloned);

        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldLockedException err){
            System.out.println(err.getMessage());
        }

        Bukkit.getWorld(worldName).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_MOB_SPAWNING, false);
    }
    public static void createBuild(int plotID, SlimePlugin plugin){
        String worldName = "hs." + plotID +".build";
        SlimeLoader file = plugin.getLoader("file");
        SlimePropertyMap properties = new SlimePropertyMap();
        properties.setValue(SlimeProperties.DIFFICULTY, "peaceful");
        SlimeWorld cloned = null;

        try{
            SlimeWorld world = plugin.loadWorld(file, "play_template", false, properties);
            cloned = world.clone(worldName, file);
            cloned = plugin.loadWorld(file, worldName, false, properties);
            plugin.loadWorld(cloned);
        } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldLockedException | WorldAlreadyExistsException err){
            throw new RuntimeException(err);
        }

        if  (Bukkit.getServer().getWorlds().contains(worldName)) {
            Bukkit.getWorld(worldName).setTime(1000);
            Bukkit.getWorld(worldName).setSpawnLocation(0,-55,0);
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
            player.sendMessage("the world didnt exist");
            try {
                SlimeWorld world = plugin.loadWorld(file, worldName, false, properties);

                plugin.loadWorld(world);

            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                     WorldLockedException err) {
                if (err instanceof UnknownWorldException) {
                    player.sendMessage(ChatColor.RED + "This plot does not exist.");
                }
            }

        }
    }
}
