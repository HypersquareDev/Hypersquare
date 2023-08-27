package hypersquare.hypersquare.Menus;

import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import hypersquare.hypersquare.ChangeGameMode;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.Plot;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;

public class CreatePlotsMenu extends Gui{

        public static Logger logger = getLogger();
        SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        public CreatePlotsMenu(Player player) {
            super(player, "createPlot", "Choose a plot size", 1);
        }
        @Override
        public void onOpen(InventoryOpenEvent event) {

            Icon basic = new Icon(Material.GOLD_BLOCK);
            basic.setName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Basic plot");
            basic.setLore(ChatColor.GRAY + "300x300");
            addItem(2, basic);
            SlimeLoader file = plugin.getLoader("file");


            basic.onClick(e -> {
                e.setCancelled(true);
                int plotID = Hypersquare.lastUsedWorldNumber;
                Plot.createDev(plotID,plugin);
                Plot.createBuild(plotID,plugin);
                ChangeGameMode.devMode((Player) event.getPlayer(), plotID);
                Hypersquare.lastUsedWorldNumber++;
            });
            Icon large = new Icon(Material.DIAMOND_BLOCK);
            large.setName(ChatColor.BLUE + "" + ChatColor.BOLD + "Large plot");
            large.setLore(ChatColor.GRAY + "500x500");
            addItem(4, large);

            large.onClick(e -> {
//                e.setCancelled(true);
//                int plotID = Hypersquare.lastUsedWorldNumber;
//                Plot.createDev(plotID,plugin);
//                Plot.createBuild(plotID,plugin);
//                ChangeGameMode.devMode((Player) event.getPlayer(), plotID);
//                Hypersquare.lastUsedWorldNumber++;
            });
            Icon massive = new Icon(Material.NETHERITE_BLOCK);
            massive.setName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Massive plot");
            massive.setLore(ChatColor.GRAY + "700x700");
            addItem(6, massive);

            massive.onClick(e -> {
//                e.setCancelled(true);
//                int plotID = Hypersquare.lastUsedWorldNumber;
//                Plot.createDev(plotID,plugin);
//                Plot.createBuild(plotID,plugin);
//                ChangeGameMode.devMode((Player) event.getPlayer(), plotID);
//                Hypersquare.lastUsedWorldNumber++;
            });
        }
    }


