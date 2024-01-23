package hypersquare.hypersquare.menu;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.item.CreatePlotMenuItems;
import hypersquare.hypersquare.menu.system.Menu;
import hypersquare.hypersquare.menu.system.MenuItem;
import hypersquare.hypersquare.plot.Plot;
import hypersquare.hypersquare.plot.PlotDatabase;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static hypersquare.hypersquare.Hypersquare.lastUsedWorldNumber;

public class CreatePlotsMenu {
    public static void open(Player player) {
        HashMap<String, Integer> playerData = Hypersquare.localPlayerData.get(player.getUniqueId());

        Menu menu = new Menu(Component.text("Choose a plot size"), 1);
        
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

        MenuItem basic = new MenuItem(CreatePlotMenuItems.BASIC_PLOT.build(usedBasic, maxBasic));
        MenuItem large = new MenuItem(CreatePlotMenuItems.LARGE_PLOT.build(usedLarge, maxLarge));
        MenuItem huge = new MenuItem(CreatePlotMenuItems.HUGE_PLOT.build(usedhuge, maxhuge));
        MenuItem massive = new MenuItem(CreatePlotMenuItems.MASSIVE_PLOT.build(usedmassive, maxmassive));
        MenuItem gigantic = new MenuItem(CreatePlotMenuItems.GIGANTIC_PLOT.build(usedGigantic, maxGigantic));

        menu.slot(0, basic)
                .slot(2, large)
                .slot(4, huge)
                .slot(6, massive)
                .slot(8, gigantic);

        basic.onClick(() -> {
            if (usedBasic < maxBasic) {
                player.closeInventory();
                int plotID = lastUsedWorldNumber;
                Plot.createPlot(player, plotID, Hypersquare.slimePlugin, player.getUniqueId().toString(), "plot_template_basic");
                lastUsedWorldNumber++;
                PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
            }
        });

        large.onClick(() -> {
            if (usedLarge < maxLarge) {
                player.closeInventory();
                int plotID = lastUsedWorldNumber;
                Plot.createPlot(player, plotID, Hypersquare.slimePlugin, player.getUniqueId().toString(), "plot_template_large");
                lastUsedWorldNumber++;
                PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
            }
        });

        huge.onClick(() -> {
            if (usedhuge < maxhuge) {
                player.closeInventory();
                int plotID = lastUsedWorldNumber;
                Plot.createPlot(player, plotID, Hypersquare.slimePlugin, player.getUniqueId().toString(), "plot_template_massive");
                lastUsedWorldNumber++;
                PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
            }
        });

        massive.onClick(() -> {
            if (usedmassive < maxmassive) {
                player.closeInventory();
                int plotID = lastUsedWorldNumber;
                Plot.createPlot(player, plotID, Hypersquare.slimePlugin, player.getUniqueId().toString(), "plot_template_huge");
                lastUsedWorldNumber++;
                PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
            }
        });

        gigantic.onClick(() -> {
            if (usedGigantic < maxGigantic) {
                player.closeInventory();
                int plotID = lastUsedWorldNumber;
                Plot.createPlot(player, plotID, Hypersquare.slimePlugin, player.getUniqueId().toString(), "plot_template_gigantic");
                lastUsedWorldNumber++;
                PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
            }
        });

        menu.open(player);
    }
}
