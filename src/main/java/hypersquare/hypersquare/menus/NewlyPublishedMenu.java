package hypersquare.hypersquare.menus;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import hypersquare.hypersquare.items.MiscItems;
import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.plot.Plot;
import hypersquare.hypersquare.plot.PlotDatabase;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NewlyPublishedMenu {
    public static Gui create(){
        Gui gui = Gui.gui()
                .title(Component.text("Newly Published"))
                .rows(5)
                .create();

        gui.disableAllInteractions();
        int i = 0;
        for (int plotID : PlotDatabase.getRecentPlots()) {
            if (plotID == 0)
                continue;
            if (i > 45)
                break;
            ItemStack itemStack = Plot.getPlotItem(plotID);
            gui.setItem(i, ItemBuilder.from(itemStack).asGuiItem(event -> ChangeGameMode.playMode((Player) event.getWhoClicked(),plotID)));
            i+=2;
        }
        return gui;
    }
}
