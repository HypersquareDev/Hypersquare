package hypersquare.hypersquare.menu;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.item.MiscItems;
import hypersquare.hypersquare.menu.system.Menu;
import hypersquare.hypersquare.menu.system.MenuItem;
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
        Menu menu = new Menu(player, Component.text("My Plots"), 2);

        menu.slot(menu.getSize() - 1, new MenuItem(MiscItems.CLAIM_PLOT.build()).onClick(() -> {
            CreatePlotsMenu.open(player);
        }));

        Utilities.sendOpenMenuSound(player);

        int i = 0;

        // Retrieve all plots owned by the player
        List<Document> playerPlots = PlotDatabase.getPlotsByOwner(player.getUniqueId().toString());

        for (Document plotDocument : playerPlots) {
            ItemStack plotItem = new ItemStack(Material.matchMaterial(plotDocument.getString("icon")));
            ItemMeta meta = plotItem.getItemMeta();
            if (Hypersquare.plotVersion == plotDocument.getInteger("version")) {
                meta.displayName(cleanMM.deserialize(plotDocument.getString("name")));
            } else {
                String name = plotDocument.getString("name");
                meta.displayName(cleanMM.deserialize(name + "<red>" + " (Out of date)"));
            }
            List<Component> lore = new ArrayList<>();
            lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>" + plotDocument.getString("size") + " Plot").decoration(TextDecoration.ITALIC, false));
            lore.add(MiniMessage.miniMessage().deserialize(""));
            lore.add(MiniMessage.miniMessage().deserialize("<gray>Tags: <dark_gray>" + plotDocument.getString("tags")).decoration(TextDecoration.ITALIC, false));
            lore.add(MiniMessage.miniMessage().deserialize("<gray>Votes: <yellow>" + plotDocument.getInteger("votes") + "<dark_gray> (last 2 weeks)").decoration(TextDecoration.ITALIC, false));
            lore.add(MiniMessage.miniMessage().deserialize(""));
            lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>ID: " + plotDocument.getInteger("plotID")).decoration(TextDecoration.ITALIC, false));
            lore.add(MiniMessage.miniMessage().deserialize("<blue>↓ Node " + plotDocument.getInteger("node")).decoration(TextDecoration.ITALIC, false));
            if (Hypersquare.plotVersion == plotDocument.getInteger("version")) {
                lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>Plot version: " + plotDocument.getInteger("version")).decoration(TextDecoration.ITALIC, false));
            } else {
                Component aa = MiniMessage.miniMessage().deserialize("<red>Plot version: " + plotDocument.getInteger("version")).decoration(TextDecoration.ITALIC, false);
                lore.add(aa);
            }

            meta.lore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            meta.addItemFlags(ItemFlag.HIDE_DYE);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
            meta.displayName(meta.displayName());

            plotItem.setItemMeta(meta);
            MenuItem plot = new MenuItem(plotItem);

            menu.slot(i, plot);

            final MenuItem finalPlot = plot;
            plot.onClick(() -> {
                ChangeModeMenu.open(player, finalPlot, plotDocument.getInteger("plotID"));
                Utilities.sendClickMenuSound(player);
            });
            i++;
        }

        menu.open();
    }
}
