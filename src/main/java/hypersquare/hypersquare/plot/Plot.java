package hypersquare.hypersquare.plot;

import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.exceptions.*;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.util.Utilities;
import hypersquare.hypersquare.util.WorldUtilities;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static hypersquare.hypersquare.util.Utilities.savePersistentData;

public class Plot {

    public static ItemStack getPlotItem(int plotID){
        org.bukkit.inventory.ItemStack plotItem = new ItemStack(Material.matchMaterial(PlotDatabase.getPlotIcon(plotID)));
        ItemMeta meta = plotItem.getItemMeta();
        if (Hypersquare.plotVersion == PlotDatabase.getPlotVersion(plotID)) {
            meta.displayName(Hypersquare.fullMM.deserialize(PlotDatabase.getPlotName(plotID)));
        } else {
            String name = PlotDatabase.getPlotName(plotID);
            meta.displayName(Hypersquare.fullMM.deserialize(name + "<red>" + " (Out of date)"));
        }
        List<Component> lore = new ArrayList<>();
        lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>" + PlotDatabase.getPlotSize(plotID) + " Plot").decoration(TextDecoration.ITALIC,false));
        lore.add(MiniMessage.miniMessage().deserialize(""));
        lore.add(MiniMessage.miniMessage().deserialize(""));
        lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>ID: " + plotID).decoration(TextDecoration.ITALIC,false));
        if (Hypersquare.plotVersion == PlotDatabase.getPlotVersion(plotID)){
            lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>Plot version: " + PlotDatabase.getPlotVersion(plotID)).decoration(TextDecoration.ITALIC,false));
        } else {
            Component aa = MiniMessage.miniMessage().deserialize("<red>Plot version: " + PlotDatabase.getPlotVersion(plotID)).decoration(TextDecoration.ITALIC,false);
            lore.add(aa);
        }

        // Make sure absolutely nothing is italic
        lore.replaceAll(component -> component.decoration(TextDecoration.ITALIC, false));

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
        PlayerDatabase.addPlot(player.getUniqueId(), plotType.replace("plot_template_", ""));
        Utilities.sendInfo(player, Component.text("Starting creation of new " + Utilities.capitalize(plotType.replace("plot_template_", "")) + " plot."));
        String worldName = "hs." + plotID;
        WorldUtilities.cloneWorld(plotType, worldName, (buildWorld) -> {
            World w = Bukkit.getWorld(worldName);
            String capitalized = Utilities.capitalize(plotType.replace("plot_template_", ""));
            w.getPersistentDataContainer().set(new NamespacedKey(Hypersquare.instance, "plotType"), PersistentDataType.STRING, capitalized);
            w.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            w.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            w.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            w.setSpawnLocation(25, -55, 4);

            WorldUtilities.cloneWorld("dev_template", "hs.code." + plotID, (codeWorld) -> {
                PlotDatabase.addPlot(plotID, ownerUUID, "map", "<" + Utilities.randomHSVHex(0, 360, 97, 62) + ">" + Bukkit.getOfflinePlayer(UUID.fromString(ownerUUID)).getName() + "'s Game", 1, "None", 0, Utilities.capitalize(plotType.replace("plot_template_", "")), Hypersquare.plotVersion);
                Bukkit.getWorld("hs.code." + plotID).getPersistentDataContainer().set(new NamespacedKey(Hypersquare.instance, "plotType"), PersistentDataType.STRING, "Code");
                new CodeFile(Bukkit.getWorld("hs.code." + plotID)).setCode("[]");
                savePersistentData(w, plugin);
                PlotManager.loadPlot(plotID);
                ChangeGameMode.devMode(player, plotID);
                Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
            });
        });
    }


    public static void loadPlot(int plotID, Player player) throws WorldLockedException, CorruptedWorldException, NewerFormatException, UnknownWorldException, IOException {
        SlimePlugin plugin = Hypersquare.slimePlugin;
        String worldName = "hs." + plotID;
        String codeWorldName = "hs.code." + plotID;
        SlimeLoader file = plugin.getLoader("mongodb");
        SlimePropertyMap properties = new SlimePropertyMap();
        SlimeWorld buildTest = plugin.getWorld(worldName);
        SlimeWorld codeTest = plugin.getWorld(codeWorldName);
        // Load both dev and build worlds
        if (!plugin.getLoadedWorlds().contains(buildTest)) {
            SlimeWorld world = plugin.loadWorld(file, worldName, false, properties);
            plugin.loadWorld(world);
            Utilities.getWorldDataFromSlimeWorlds(player.getWorld());
        }
        if (!plugin.getLoadedWorlds().contains(codeTest)) {
            SlimeWorld world = plugin.loadWorld(file, codeWorldName, false, properties);
            plugin.loadWorld(world);
            Utilities.getWorldDataFromSlimeWorlds(player.getWorld());
        }
        // Configure worlds
        loadRules(worldName);
        loadRules(codeWorldName);
    }

    public static void loadRules(String worldName) {
        World w = Bukkit.getWorld(worldName);
        w.setTime(1000);
        w.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        w.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        w.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        w.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
        w.setGameRule(GameRule.DO_FIRE_TICK, false);
        w.setGameRule(GameRule.DO_VINES_SPREAD, false);
    }

    public static void deletePlot(int plotID) throws UnknownWorldException, IOException {
        String worldName = "hs." + plotID;
        String codeWorldName = "hs.code." + plotID;
        SlimeLoader file = Hypersquare.slimePlugin.getLoader("mongodb");
        Bukkit.unloadWorld(Bukkit.getWorld(worldName), true);
        Bukkit.unloadWorld(Bukkit.getWorld(codeWorldName), true);
        file.deleteWorld(worldName);
        file.deleteWorld(codeWorldName);
    }
}
