package hypersquare.hypersquare.menus;

import com.infernalsuite.aswm.api.SlimePlugin;
import hypersquare.hypersquare.*;
import hypersquare.hypersquare.plot.Database;
import hypersquare.hypersquare.utils.Utilities;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bson.Document;
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
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        Icon createPlot = new Icon(Material.GREEN_STAINED_GLASS);
        createPlot.setName(ChatColor.GREEN + "" + ChatColor.BOLD + "Create New Plot");
        addItem(this.getSize() - 1, createPlot);

        int i = 0;
        Utilities.sendOpenMenuSound(player);

        // Retrieve all plots owned by the player
        List<Document> playerPlots = Database.getPlotsByOwner(player.getUniqueId().toString());

        for (Document plotDocument : playerPlots) {
            Icon plot = new Icon(Material.matchMaterial(plotDocument.getString("icon")));
            plot.setName(ChatColor.translateAlternateColorCodes('&', Utilities.convertToChatColor(plotDocument.getString("name"))));
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_GRAY + "" + plotDocument.getString("size") + " Plot");
            lore.add("");
            lore.add(ChatColor.GRAY + "Tags: " + ChatColor.DARK_GRAY + plotDocument.getString("tags"));
            lore.add(ChatColor.GRAY + "Votes: " + ChatColor.YELLOW + plotDocument.getInteger("votes") + ChatColor.DARK_GRAY + " (last 2 weeks)");
            lore.add("");
            lore.add(ChatColor.DARK_GRAY + "ID: " + plotDocument.getInteger("plotID"));
            lore.add(ChatColor.BLUE + "↓ Node " + plotDocument.getInteger("node"));
            plot.setLore(lore);
            addItem(i, plot);

            final Icon finalPlot = plot;
            plot.onClick(e -> {
                ChangeModeMenu.initItems(finalPlot, plotDocument.getInteger("plotID"));
                new ChangeModeMenu((Player) event.getPlayer()).open();
                Utilities.sendClickMenuSound(player);
            });

            i++;
        }

        createPlot.onClick(e -> {
            e.setCancelled(true);
            new CreatePlotsMenu((Player) event.getPlayer()).open();
            Utilities.sendSecondaryMenuSound(player);
        });
    }
}