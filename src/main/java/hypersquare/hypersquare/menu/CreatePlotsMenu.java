package hypersquare.hypersquare.menu;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.item.CreatePlotMenuItems;
import hypersquare.hypersquare.plot.Plot;
import hypersquare.hypersquare.plot.PlotDatabase;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.HashMap;

import static hypersquare.hypersquare.Hypersquare.lastUsedWorldNumber;

public class CreatePlotsMenu extends Gui {
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

        Icon basic = new Icon(CreatePlotMenuItems.BASIC_PLOT.build(usedBasic, maxBasic));
        Icon large = new Icon(CreatePlotMenuItems.LARGE_PLOT.build(usedLarge, maxLarge));
        Icon huge = new Icon(CreatePlotMenuItems.HUGE_PLOT.build(usedhuge, maxhuge));
        Icon massive = new Icon(CreatePlotMenuItems.MASSIVE_PLOT.build(usedmassive, maxmassive));
        Icon gigantic = new Icon(CreatePlotMenuItems.GIGANTIC_PLOT.build(usedGigantic, maxGigantic));

        addItem(0, basic);
        addItem(2, large);
        addItem(4, huge);
        addItem(6, massive);
        addItem(8, gigantic);

        basic.onClick(e -> {
            e.setCancelled(true);
            if (usedBasic < maxBasic) {
                int plotID = lastUsedWorldNumber;
                Plot.createPlot(player, plotID, Hypersquare.slimePlugin, player.getUniqueId().toString(), "plot_template_basic");
                lastUsedWorldNumber++;
                PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
                player.closeInventory();
            }
        });

        large.onClick(e -> {
            e.setCancelled(true);
            if (usedLarge < maxLarge) {
                int plotID = lastUsedWorldNumber;
                Plot.createPlot(player, plotID, Hypersquare.slimePlugin, player.getUniqueId().toString(), "plot_template_large");
                lastUsedWorldNumber++;
                PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
                player.closeInventory();
            }
        });

        huge.onClick(e -> {
            e.setCancelled(true);
            if (usedhuge < maxhuge) {
                int plotID = lastUsedWorldNumber;
                Plot.createPlot(player, plotID, Hypersquare.slimePlugin, player.getUniqueId().toString(), "plot_template_massive");
                lastUsedWorldNumber++;
                PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
                player.closeInventory();
            }
        });

        massive.onClick(e -> {
            e.setCancelled(true);
            if (usedmassive < maxmassive) {
                int plotID = lastUsedWorldNumber;
                Plot.createPlot(player, plotID, Hypersquare.slimePlugin, player.getUniqueId().toString(), "plot_template_huge");
                lastUsedWorldNumber++;
                PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
                player.closeInventory();
            }
        });

        gigantic.onClick(e -> {
            e.setCancelled(true);
            if (usedGigantic < maxGigantic) {
                int plotID = lastUsedWorldNumber;
                Plot.createPlot(player, plotID, Hypersquare.slimePlugin, player.getUniqueId().toString(), "plot_template_gigantic");
                lastUsedWorldNumber++;
                PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
                player.closeInventory();
            }
        });
    }
}
