package hypersquare.hypersquare.plot;

import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.exceptions.*;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.utils.Utilities;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.UUID;

import static hypersquare.hypersquare.utils.Utilities.savePersistentData;

public class Plot {

    public static void createPlot(int plotID, SlimePlugin plugin, String ownerUUID, String plotType){
        String worldName = "hs." + plotID;
        SlimeLoader file = plugin.getLoader("mysql");
        SlimePropertyMap properties = new SlimePropertyMap();

        SlimeWorld world = null;
        SlimeWorld cloned = null;
        try{
             world = plugin.loadWorld(file, plotType, false, properties);
             properties = world.getPropertyMap();

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
        plotType = plotType.replace("plot_template_", "");
        plotType = Utilities.capitalize(plotType);

        Database.addPlot(plotID,ownerUUID,"map",Utilities.randomHSVHex(0,360,97,62) + Bukkit.getPlayer( UUID.fromString(ownerUUID)).getName() + "'s Game",1,"None",0,plotType);
        Bukkit.getWorld(worldName).getPersistentDataContainer().set(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "plotType"), PersistentDataType.STRING,plotType);
        savePersistentData(Bukkit.getWorld(worldName),plugin);
    }

    public static void loadPlot(int plotID, Player player){
        SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        String worldName = "hs." + plotID;
        SlimeLoader file = plugin.getLoader("mysql");
        SlimePropertyMap properties = new SlimePropertyMap();
        SlimeWorld test = plugin.getWorld(worldName);
        SlimeWorld world = null;
        if (!plugin.getLoadedWorlds().contains(test)){
            try {
                world = plugin.loadWorld(file, worldName, false, properties);
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
            player.teleport(new Location(Bukkit.getWorld(worldName),10,0,10));
            Utilities.getWorldDataFromSlimeFun(player.getWorld());



        } else {
            player.teleport(new Location(Bukkit.getWorld(worldName),10,0,10));

        }

    }
    public static void loadRules(int plotID){
        String worldName = "hs." + plotID;
        Bukkit.getWorld(worldName).setTime(1000);
        Bukkit.getWorld(worldName).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_MOB_SPAWNING, false);
    }


}
