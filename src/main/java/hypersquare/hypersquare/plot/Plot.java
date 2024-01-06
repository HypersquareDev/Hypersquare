package hypersquare.hypersquare.plot;

import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.exceptions.*;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.util.Utilities;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static hypersquare.hypersquare.util.Utilities.savePersistentData;

public class Plot {

    public static void createPlot(Player player, int plotID, SlimePlugin plugin, String ownerUUID, final String plotType) {
        PlayerDatabase.addPlot(player.getUniqueId(),plotType.replace("plot_template_", ""));
        Utilities.sendInfo(player, "Starting creation of new " + Utilities.capitalize(plotType.replace("plot_template_", "")) + " plot.");
        AtomicReference<SlimeWorld> cloned = new AtomicReference<>(null);
        String worldName = "hs." + plotID;
        SlimeLoader file = plugin.getLoader("mongodb");
        AtomicReference<SlimePropertyMap> properties = new AtomicReference<>(new SlimePropertyMap());
        AtomicReference<SlimeWorld> world = new AtomicReference<>(null);
        AtomicBoolean isThreadFinished = new AtomicBoolean(false);

        Thread thread = new Thread(() -> {
            try {
                world.set(plugin.loadWorld(file, plotType, false, properties.get()));
                properties.set(world.get().getPropertyMap());
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                     WorldLockedException err) {
                return;
            }
            try {
                cloned.set(world.get().clone(worldName, file));
            } catch (WorldAlreadyExistsException | IOException err) {
            }
            isThreadFinished.set(true); // Set the flag to indicate that the thread has finished
        });

        thread.start();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (isThreadFinished.get()) {
                    try {
                        cloned.set(plugin.loadWorld(file, worldName, false, properties.get()));
                        plugin.loadWorld(cloned.get());
                    } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException |
                             WorldLockedException err) {
                        return;
                    }

                    Bukkit.getWorld(worldName).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
                    Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                    Bukkit.getWorld(worldName).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                    Bukkit.getWorld(worldName).setGameRule(GameRule.DO_MOB_SPAWNING, false);
                    Bukkit.getWorld(worldName).setSpawnLocation(25, -55, 4);

                    PlotDatabase.addPlot(plotID, ownerUUID, "map", "<" + Utilities.randomHSVHex(0, 360, 97, 62) + ">" + Bukkit.getPlayer(UUID.fromString(ownerUUID)).getName() + "'s Game", 1, "None", 0, Utilities.capitalize(plotType.replace("plot_template_", "")), Hypersquare.plotVersion);
                    String capitalized = Utilities.capitalize(plotType.replace("plot_template_", ""));
                    Bukkit.getWorld(worldName).getPersistentDataContainer().set(new NamespacedKey(Hypersquare.instance, "plotType"), PersistentDataType.STRING, capitalized);
                    new CodeFile(Bukkit.getWorld(worldName)).setCode("[]");
                    savePersistentData(Bukkit.getWorld(worldName), plugin);
                    PlotManager.loadPlot(plotID);
                    ChangeGameMode.devMode(player, plotID);
                    Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
                    this.cancel();
                }
            }
        }.runTaskTimer(Hypersquare.instance, 0L, 5L);
    }


        public static void loadPlot(int plotID, Player player) throws WorldLockedException, CorruptedWorldException, NewerFormatException, UnknownWorldException, IOException {
        SlimePlugin plugin = Hypersquare.slimePlugin;
        String worldName = "hs." + plotID;
        SlimeLoader file = plugin.getLoader("mongodb");
        SlimePropertyMap properties = new SlimePropertyMap();
        SlimeWorld test = plugin.getWorld(worldName);
        SlimeWorld world = null;
        if (!plugin.getLoadedWorlds().contains(test)){

                world = plugin.loadWorld(file, worldName, false, properties);
                plugin.loadWorld(world);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (plugin.getLoadedWorlds().contains(test)) {
                        this.cancel();
                    }

                }
            }.runTaskTimer(Hypersquare.instance,1,100);
            player.teleport(new Location(Bukkit.getWorld(worldName),10,0,10));
            Utilities.getWorldDataFromSlimeWorlds(player.getWorld());



        } else {
            player.teleport(new Location(Bukkit.getWorld(worldName),10,0,10));

        }
        loadRules(plotID);

    }
    public static void loadRules(int plotID){
        String worldName = "hs." + plotID;
        Bukkit.getWorld(worldName).setTime(1000);
        Bukkit.getWorld(worldName).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_MOB_SPAWNING, false);
        Bukkit.getWorld(worldName).setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
        Bukkit.getWorld(worldName).setGameRule(GameRule.DO_FIRE_TICK, false);


    }
    public static void deletePlot(int plotID) throws UnknownWorldException, IOException {
        String worldName = "hs." + plotID;
        SlimeLoader file = Hypersquare.slimePlugin.getLoader("mongodb");
        SlimePropertyMap properties = new SlimePropertyMap();

        SlimeWorld world = null;
        SlimeWorld cloned = null;
        Bukkit.unloadWorld(Bukkit.getWorld(worldName),true);
        file.deleteWorld(worldName);
    }


}