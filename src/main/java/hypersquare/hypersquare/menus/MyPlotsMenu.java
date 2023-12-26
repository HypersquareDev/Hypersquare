package hypersquare.hypersquare.menus;

import com.infernalsuite.aswm.api.SlimePlugin;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.items.MiscItems;
import hypersquare.hypersquare.plot.Plot;
import hypersquare.hypersquare.plot.PlotDatabase;
import hypersquare.hypersquare.utils.Utilities;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bson.Document;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static hypersquare.hypersquare.Hypersquare.mm;
import static org.bukkit.Bukkit.getLogger;

public class MyPlotsMenu extends Gui {
    public static Logger logger = getLogger();
    SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");

    public MyPlotsMenu(Player player) {
        super(player, "myPlots", "My Plots", 2);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        Icon createPlot = new Icon(MiscItems.CLAIM_PLOT.build());
        addItem(this.getSize() - 1, createPlot);

        int i = 0;
        Utilities.sendOpenMenuSound(player);

        // Retrieve all plots owned by the player
        List<Document> playerPlots = PlotDatabase.getPlotsByOwner(player.getUniqueId().toString());

        for (Document plotDocument : playerPlots) {
            int plotID = plotDocument.getInteger("plotID");
            ItemStack plotItem = Plot.getPlotItem(plotID);
            Icon plot = new Icon(plotItem);

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
