package hypersquare.hypersquare.menus;

import com.infernalsuite.aswm.api.SlimePlugin;
import hypersquare.hypersquare.*;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;

public class CreatePlotsMenu extends Gui{

        public static Logger logger = getLogger();
        SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        public CreatePlotsMenu(Player player) {
            super(player, "createPlot", "Choose a plot size", 2);
        }
        @Override
        public void onOpen(InventoryOpenEvent event) {

            Icon basic = new Icon(ItemManager.getItem("menu.basic_plot"));
            addItem(0, basic);

            basic.onClick(e -> {
                e.setCancelled(true);
                int plotID = Hypersquare.lastUsedWorldNumber;
                Plot.createPlot(plotID,plugin,player.getUniqueId().toString(),"plot_template_basic");
                ChangeGameMode.devMode((Player) event.getPlayer(), plotID);
                Hypersquare.lastUsedWorldNumber++;
                Hypersquare.plotData.put(player,Database.getPlot(player.getUniqueId().toString()));
            });

            Icon large = new Icon(ItemManager.getItem("menu.large_plot"));
            addItem(2, large);

            large.onClick(e -> {
                e.setCancelled(true);
                int plotID = Hypersquare.lastUsedWorldNumber;
                Plot.createPlot(plotID,plugin,player.getUniqueId().toString(),"plot_template_large");
                ChangeGameMode.devMode((Player) event.getPlayer(), plotID);
                Hypersquare.lastUsedWorldNumber++;
                Hypersquare.plotData.put(player,Database.getPlot(player.getUniqueId().toString()));
            });

            Icon massive = new Icon(ItemManager.getItem("menu.massive_plot"));
            addItem(4, massive);

            massive.onClick(e -> {
                e.setCancelled(true);
                int plotID = Hypersquare.lastUsedWorldNumber;
                Plot.createPlot(plotID,plugin,player.getUniqueId().toString(),"plot_template_massive");
                ChangeGameMode.devMode((Player) event.getPlayer(), plotID);
                Hypersquare.lastUsedWorldNumber++;
                Hypersquare.plotData.put(player,Database.getPlot(player.getUniqueId().toString()));
            });

            Icon huge = new Icon(ItemManager.getItem("menu.huge_plot"));
            addItem(6, huge);

            huge.onClick(e -> {
                e.setCancelled(true);
                int plotID = Hypersquare.lastUsedWorldNumber;
                Plot.createPlot(plotID,plugin,player.getUniqueId().toString(),"plot_template_huge");
                ChangeGameMode.devMode((Player) event.getPlayer(), plotID);
                Hypersquare.lastUsedWorldNumber++;
                Hypersquare.plotData.put(player,Database.getPlot(player.getUniqueId().toString()));
            });

            Icon gigantic = new Icon(ItemManager.getItem("menu.gigantic_plot"));
            addItem(8, gigantic);

            gigantic.onClick(e -> {
                e.setCancelled(true);
                int plotID = Hypersquare.lastUsedWorldNumber;
                Plot.createPlot(plotID,plugin,player.getUniqueId().toString(),"plot_template_gigantic");
                ChangeGameMode.devMode((Player) event.getPlayer(), plotID);
                Hypersquare.lastUsedWorldNumber++;
                Hypersquare.plotData.put(player,Database.getPlot(player.getUniqueId().toString()));
            });

        }
    }


