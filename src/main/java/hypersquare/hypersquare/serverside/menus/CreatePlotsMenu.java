package hypersquare.hypersquare.serverside.menus;

import com.infernalsuite.aswm.api.SlimePlugin;
import hypersquare.hypersquare.*;
import hypersquare.hypersquare.serverside.plot.PlayerDatabase;
import hypersquare.hypersquare.serverside.utils.managers.ItemManager;
import hypersquare.hypersquare.serverside.plot.ChangeGameMode;
import hypersquare.hypersquare.serverside.plot.PlotDatabase;
import hypersquare.hypersquare.serverside.plot.Plot;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static hypersquare.hypersquare.Hypersquare.lastUsedWorldNumber;
import static org.bukkit.Bukkit.getLogger;

public class CreatePlotsMenu extends Gui{

        public static Logger logger = getLogger();
        SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        public CreatePlotsMenu(Player player) {
            super(player, "createPlot", "Choose a plot size", 2);
        }
        @Override
        public void onOpen(InventoryOpenEvent event) {

            HashMap<String, Integer> playerData = Hypersquare.localPlayerData.get(player.getUniqueId());


            int usedBasic = playerData.get("usedBasic");
            int maxBasic = playerData.get("maxBasic");
            int usedLarge = playerData.get("usedLarge");
            int maxLarge = playerData.get("maxLarge");
            int usedhuge = playerData.get("usedhuge");
            int maxhuge = playerData.get("maxhuge");
            int usedmassive = playerData.get("usedmassive");
            int maxmassive = playerData.get("maxmassive");
            int usedGigantic = playerData.get("usedGigantic");
            int maxGigantic = playerData.get("maxGigantic");

            Icon basic = basicPlot(usedBasic, maxBasic);
            Icon large = largePlot(usedLarge, maxLarge);
            Icon huge = hugePlot(usedhuge, maxhuge);
            Icon massive = massivePlot(usedmassive, maxmassive);
            Icon gigantic = giganticPlot(usedGigantic, maxGigantic);

            addItem(0, basic);
            addItem(2, large);
            addItem(4, huge);
            addItem(6, massive);
            addItem(8, gigantic);


            basic.onClick(e -> {
                e.setCancelled(true);
                if (usedBasic != maxBasic) {
                    int plotID = lastUsedWorldNumber;
                    Plot.createPlot(plotID, plugin, player.getUniqueId().toString(), "plot_template_basic");
                    ChangeGameMode.devMode((Player) event.getPlayer(), plotID);
                    PlayerDatabase.addPlot(player.getUniqueId(),"basic");
                    lastUsedWorldNumber++;
                    Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
                    PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
                }
            });

            large.onClick(e -> {
                e.setCancelled(true);
                if (usedLarge != maxLarge) {
                    int plotID = lastUsedWorldNumber;
                    Plot.createPlot(plotID, plugin, player.getUniqueId().toString(), "plot_template_large");
                    ChangeGameMode.devMode((Player) event.getPlayer(), plotID);
                    PlayerDatabase.addPlot(player.getUniqueId(),"large");
                    lastUsedWorldNumber++;
                    Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
                    PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
                }
            });

            huge.onClick(e -> {
                e.setCancelled(true);
                if (usedhuge != maxhuge) {
                    int plotID = lastUsedWorldNumber;
                    Plot.createPlot(plotID, plugin, player.getUniqueId().toString(), "plot_template_massive");
                    ChangeGameMode.devMode((Player) event.getPlayer(), plotID);
                    PlayerDatabase.addPlot(player.getUniqueId(),"huge");
                    lastUsedWorldNumber++;
                    Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
                    PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
                }
            });

            massive.onClick(e -> {
                e.setCancelled(true);
                if (usedmassive != maxmassive) {
                    int plotID = lastUsedWorldNumber;
                    Plot.createPlot(plotID, plugin, player.getUniqueId().toString(), "plot_template_huge");
                    ChangeGameMode.devMode((Player) event.getPlayer(), plotID);
                    PlayerDatabase.addPlot(player.getUniqueId(),"massive");
                    lastUsedWorldNumber++;
                    Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
                    PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
                }
            });

            gigantic.onClick(e -> {
                e.setCancelled(true);
                if (usedGigantic != maxGigantic) {
                    int plotID = lastUsedWorldNumber;
                    Plot.createPlot(plotID, plugin, player.getUniqueId().toString(), "plot_template_gigantic");
                    ChangeGameMode.devMode((Player) event.getPlayer(), plotID);
                    PlayerDatabase.addPlot(player.getUniqueId(),"gigantic");
                    lastUsedWorldNumber++;
                    Hypersquare.plotData.put(player, PlotDatabase.getPlot(player.getUniqueId().toString()));
                    PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
                }
            });

        }



    public static Icon basicPlot(int usedBasic, int maxBasic){
            ItemStack item = ItemManager.getItem("menu.basic_plot");
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Size: 64x64");
            lore.add("");
            if (usedBasic != maxBasic) {
                lore.add(ChatColor.GRAY + "You have used " + ChatColor.GREEN + usedBasic + "/" + maxBasic + ChatColor.GRAY + " of your Basic plots.");
            } else {
                lore.add(ChatColor.GRAY + "You have used " + ChatColor.RED + usedBasic + "/" + maxBasic + ChatColor.GRAY + " of your Basic plots.");
            }
            lore.add("");
            lore.add(ChatColor.GREEN + "Click to create!");
            meta.setLore(lore);
            item.setItemMeta(meta);
            Icon basic = new Icon(item);
            return basic;
        }
    public static Icon largePlot(int usedPlot, int maxPlot){
        ItemStack item = ItemManager.getItem("menu.large_plot");
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Size: 128x128");
        lore.add("");
        if (usedPlot != maxPlot) {
            lore.add(ChatColor.GRAY + "You have used " + ChatColor.GREEN + usedPlot + "/" + maxPlot + ChatColor.GRAY + " of your Large plots.");
        } else {
            lore.add(ChatColor.GRAY + "You have used " + ChatColor.RED + usedPlot + "/" + maxPlot + ChatColor.GRAY + " of your Large plots.");
        }
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to create!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        Icon basic = new Icon(item);
        return basic;
    }
    public static Icon hugePlot(int usedPlot, int maxPlot){
        ItemStack item = ItemManager.getItem("menu.huge_plot");
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Size: 256x256");
        lore.add("");
        if (usedPlot != maxPlot) {
            lore.add(ChatColor.GRAY + "You have used " + ChatColor.GREEN + usedPlot + "/" + maxPlot + ChatColor.GRAY + " of your huge plots.");
        } else {
            lore.add(ChatColor.GRAY + "You have used " + ChatColor.RED + usedPlot + "/" + maxPlot + ChatColor.GRAY + " of your huge plots.");
        }
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to create!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        Icon basic = new Icon(item);
        return basic;
    }
    public static Icon massivePlot(int usedPlot, int maxPlot){
        ItemStack item = ItemManager.getItem("menu.massive_plot");
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Size: 512x512");
        lore.add("");
        if (usedPlot != maxPlot) {
            lore.add(ChatColor.GRAY + "You have used " + ChatColor.GREEN + usedPlot + "/" + maxPlot + ChatColor.GRAY + " of your massive plots.");
        } else {
            lore.add(ChatColor.GRAY + "You have used " + ChatColor.RED + usedPlot + "/" + maxPlot + ChatColor.GRAY + " of your massive plots.");
        }
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to create!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        Icon basic = new Icon(item);
        return basic;
    }
    public static Icon giganticPlot(int usedPlot, int maxPlot){
        ItemStack item = ItemManager.getItem("menu.gigantic_plot");
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Size: 1024x1024");
        lore.add("");
        if (usedPlot != maxPlot) {
            lore.add(ChatColor.GRAY + "You have used " + ChatColor.GREEN + usedPlot + "/" + maxPlot + ChatColor.GRAY + " of your Gigantic plots.");
        } else {
            lore.add(ChatColor.GRAY + "You have used " + ChatColor.RED + usedPlot + "/" + maxPlot + ChatColor.GRAY + " of your Gigantic plots.");
        }
        lore.add("");
        lore.add(ChatColor.GREEN + "Click to create!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        Icon basic = new Icon(item);
        return basic;
    }
    }


