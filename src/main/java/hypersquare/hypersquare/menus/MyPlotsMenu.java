package hypersquare.hypersquare.menus;


import com.infernalsuite.aswm.api.SlimePlugin;
import hypersquare.hypersquare.*;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;

public class MyPlotsMenu extends Gui {
    public static Logger logger = getLogger();
    SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");

    public MyPlotsMenu(Player player) {
        super(player, "myPlots", "My Plots", 2);
         int i = 0;
        for (Object list : Hypersquare.plotData.get(player)){
            if (list != null) {
                i++;
                if (i >= this.getSize()){
                    this.setSize(this.getSize()+9);
                }

            }
        }
    }
    @Override
    public void onOpen(InventoryOpenEvent event) {
        Icon createPlot = new Icon(Material.GREEN_STAINED_GLASS);
        createPlot.setName(ChatColor.GREEN + "" + ChatColor.BOLD + "Create New Plot");
        addItem(this.getSize()-1, createPlot);
        int i = 0;
        Icon plot = null;
        Utilities.sendOpenMenuSound(player);
        for (Object list : Hypersquare.plotData.get(player)){
            if (list != null) {
                List list1 = (List) list;
                plot = new Icon(Material.matchMaterial((String) list1.get(4)));
                plot.setName(ChatColor.translateAlternateColorCodes('&', Utilities.convertToChatColor((String) list1.get(5))));
                List lore = new ArrayList();
                lore.add(ChatColor.DARK_GRAY + "" + list1.get(9) + " Plot");
                lore.add("");
                lore.add(ChatColor.GRAY + "Tags: " + ChatColor.DARK_GRAY + list1.get(7));
                lore.add(ChatColor.GRAY + "Votes: " + ChatColor.YELLOW + list1.get(8) + ChatColor.DARK_GRAY + " (last 2 weeks)");
                lore.add("");
                lore.add(ChatColor.DARK_GRAY + "ID: " + list1.get(0));
                lore.add(ChatColor.BLUE + "↓ Node " + list1.get(6));
                plot.setLore(lore);
                addItem(i, plot);
                i++;
                Icon finalPlot = plot;
                plot.onClick(e -> {
                    ChangeModeMenu.initItems(finalPlot, (Integer) list1.get(0));
                    new ChangeModeMenu((Player) event.getPlayer()).open();
                    Utilities.sendClickMenuSound(player);
                });


            }
        }

        createPlot.onClick(e -> {
            e.setCancelled(true);
            new CreatePlotsMenu((Player) event.getPlayer()).open();
            Utilities.sendSecondaryMenuSound(player);
        });


    }
}
