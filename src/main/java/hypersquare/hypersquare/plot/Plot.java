package hypersquare.hypersquare.plot;

import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.exceptions.*;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.utils.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static hypersquare.hypersquare.Hypersquare.mm;
import static hypersquare.hypersquare.utils.Utilities.savePersistentData;
import static org.bukkit.Bukkit.getServer;

public class Plot {
    public static ItemStack getPlotItem(int plotID){
        org.bukkit.inventory.ItemStack plotItem = new ItemStack(Material.matchMaterial(PlotDatabase.getPlotIcon(plotID)));
        ItemMeta meta = plotItem.getItemMeta();
        if (Hypersquare.plotVersion == PlotDatabase.getPlotVersion(plotID)) {
            meta.displayName(mm.deserialize(PlotDatabase.getPlotName(plotID)));
        } else {
            String name = PlotDatabase.getPlotName(plotID);
            meta.displayName(mm.deserialize(name + "<red>" + " (Out of date)"));
        }
        List<Component> lore = new ArrayList<>();
        lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>" + PlotDatabase.getPlotSize(plotID) + " Plot").decoration(TextDecoration.ITALIC,false));
        lore.add(MiniMessage.miniMessage().deserialize(""));
        lore.add(MiniMessage.miniMessage().deserialize("<gray>Votes: <yellow>" + PlotDatabase.getPlotVotes(plotID) + "<dark_gray> (all time)").decoration(TextDecoration.ITALIC,false));
        if (PlotDatabase.isPlotPublished(plotID)){
            lore.add(MiniMessage.miniMessage().deserialize("<gray>Published: Published").decoration(TextDecoration.ITALIC,false));
        } else {
            lore.add(MiniMessage.miniMessage().deserialize("<gray>Published: Not Published").decoration(TextDecoration.ITALIC,false));
        }
        lore.add(MiniMessage.miniMessage().deserialize("<gray>Description: " + PlotDatabase.getPlotDescription(plotID)).decoration(TextDecoration.ITALIC,false));
        lore.add(MiniMessage.miniMessage().deserialize(""));
        lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>ID: " + plotID).decoration(TextDecoration.ITALIC,false));
        if (Hypersquare.plotVersion == PlotDatabase.getPlotVersion(plotID)){
            lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>Plot version: " + PlotDatabase.getPlotVersion(plotID)).decoration(TextDecoration.ITALIC,false));
        } else {
            Component aa = MiniMessage.miniMessage().deserialize("<red>Plot version: " + PlotDatabase.getPlotVersion(plotID)).decoration(TextDecoration.ITALIC,false);
            lore.add(aa);
        }

        meta.lore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
        meta.setDisplayName(ChatColor.RESET + meta.getDisplayName());
        plotItem.setItemMeta(meta);
        return plotItem;
    }

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

                    PlotDatabase.addPlot(plotID, ownerUUID, "map", "<" + Utilities.randomHSVHex(0, 360, 97, 62) + ">" + Bukkit.getPlayer(UUID.fromString(ownerUUID)).getName() + "'s Game",Utilities.capitalize(plotType.replace("plot_template_", "")),0, false, "Not Published");
                    String capitalized = Utilities.capitalize(plotType.replace("plot_template_", ""));
                    Bukkit.getWorld(worldName).getPersistentDataContainer().set(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "plotType"), PersistentDataType.STRING, capitalized);
                    savePersistentData(Bukkit.getWorld(worldName), plugin);
                    PlotManager.loadPlot(plotID);
                    PlotDatabase.setPlotSpawnLocation(plotID,new Location(Bukkit.getWorld(worldName),10,0,10,0,0));
                    ChangeGameMode.devMode(player, plotID);
                    Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
                    this.cancel();
                }
            }
        }.runTaskTimer(Hypersquare.getPlugin(Hypersquare.class), 0L, 5L);
    }


        public static void loadPlot(int plotID, Player player) throws WorldLockedException, CorruptedWorldException, NewerFormatException, UnknownWorldException, IOException {
        SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
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
            }.runTaskTimer(Hypersquare.getPlugin(Hypersquare.class),1,100);
            Bukkit.getWorld(worldName).setSpawnLocation(PlotDatabase.getPlotSpawnLocation(plotID));
            player.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
            Utilities.getWorldDataFromSlimeWorlds(player.getWorld());



        } else {
            Bukkit.getWorld(worldName).setSpawnLocation(PlotDatabase.getPlotSpawnLocation(plotID));
            player.teleport(Bukkit.getWorld(worldName).getSpawnLocation());

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
        SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        String worldName = "hs." + plotID;
        SlimeLoader file = plugin.getLoader("mongodb");
        SlimePropertyMap properties = new SlimePropertyMap();

        SlimeWorld world = null;
        SlimeWorld cloned = null;
        Bukkit.unloadWorld(Bukkit.getWorld(worldName),true);
        file.deleteWorld(worldName);
    }


}