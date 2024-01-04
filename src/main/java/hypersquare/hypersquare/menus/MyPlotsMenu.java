package hypersquare.hypersquare.menus;

import com.infernalsuite.aswm.api.SlimePlugin;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.items.MiscItems;
import hypersquare.hypersquare.plot.Plot;
import hypersquare.hypersquare.plot.PlotDatabase;
import hypersquare.hypersquare.utils.Utilities;
import mc.obliviate.inventory.Icon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bson.Document;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static hypersquare.hypersquare.Hypersquare.mm;
import static org.bukkit.Bukkit.getLogger;

public class MyPlotsMenu {
    public static Logger logger = getLogger();
    SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");


    public static PaginatedGui create(Player player){
        PaginatedGui gui = Gui.paginated()
                .title(Component.text("My Plots"))
                .rows(6)
                .create();

        gui.disableAllInteractions();

        List<Document> playerPlots = PlotDatabase.getPlotsByOwner(player.getUniqueId().toString());
        gui.getFiller().fillBorder(ItemBuilder.from(MiscItems.MENU_FILLER.build()).asGuiItem());


        for (Document plotDocument : playerPlots) {
            int plotID = plotDocument.getInteger("plotID");
            ItemStack plotItem = Plot.getPlotItem(plotID);
            Icon plot = new Icon(plotItem);

            gui.addItem(ItemBuilder.from(Plot.getPlotItem(plotID)).asGuiItem(event -> {
                ChangeModeMenu.initItems(plot, plotDocument.getInteger("plotID"));
                new ChangeModeMenu((Player) event.getWhoClicked()).open();
                Utilities.sendClickMenuSound((Player) event.getWhoClicked());
            }));
        }

        gui.setItem(6, 3, ItemBuilder.from(MiscItems.MENU_PREVIOUS.build()).asGuiItem(event -> gui.previous()));
        gui.setItem(6,5, ItemBuilder.from(MiscItems.CLAIM_PLOT.build()).asGuiItem(event -> {
            new CreatePlotsMenu((Player) event.getWhoClicked()).open();
            Utilities.sendSecondaryMenuSound((Player) event.getWhoClicked());
        }));
        gui.setItem(6, 7, ItemBuilder.from(MiscItems.MENU_NEXT.build()).asGuiItem(event -> gui.next()));


        return gui;
    }
}
