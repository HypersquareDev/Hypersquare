package hypersquare.hypersquare.menu;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.item.MiscItems;
import hypersquare.hypersquare.menu.system.Menu;
import hypersquare.hypersquare.menu.system.MenuItem;
import hypersquare.hypersquare.plot.Plot;
import hypersquare.hypersquare.plot.PlotDatabase;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static hypersquare.hypersquare.Hypersquare.cleanMM;

public class MyPlotsMenu {

    public static void open(Player player) {
        Menu menu = new Menu(Component.text("My Plots"), 2);

        menu.slot(menu.getSize() - 1, new MenuItem(MiscItems.CLAIM_PLOT.build()).onClick(() -> {
            CreatePlotsMenu.open(player);
        }));

        Utilities.sendOpenMenuSound(player);

        int i = 0;

        // Retrieve all plots owned by the player
        List<Document> playerPlots = PlotDatabase.getPlotsByOwner(player.getUniqueId().toString());

        for (Document plotDocument : playerPlots) {
            int plotID = plotDocument.getInteger("plotID");
            ItemStack plotItem = Plot.getPlotItem(plotID);

            MenuItem plot = new MenuItem(plotItem);

            menu.slot(i, plot);

            final MenuItem finalPlot = plot;
            plot.onClick(() -> {
                ChangeModeMenu.open(player, finalPlot, plotDocument.getInteger("plotID"));
                Utilities.sendClickMenuSound(player);
            });
            i++;
        }

        menu.open(player);
    }
}
