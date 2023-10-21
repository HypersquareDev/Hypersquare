package hypersquare.hypersquare.serverside.menus;

import com.infernalsuite.aswm.api.SlimePlugin;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.serverside.plot.PlotDatabase;
import hypersquare.hypersquare.serverside.utils.Utilities;
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
        List<Document> playerPlots = PlotDatabase.getPlotsByOwner(player.getUniqueId().toString());

        for (Document plotDocument : playerPlots) {
            ItemStack plotItem = new ItemStack(Material.matchMaterial(plotDocument.getString("icon")));
            ItemMeta meta = plotItem.getItemMeta();
            if (Hypersquare.plotVersion == plotDocument.getInteger("version")) {
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Utilities.convertToChatColor(plotDocument.getString("name"))));
            } else {
                String name = ChatColor.translateAlternateColorCodes('&', Utilities.convertToChatColor(plotDocument.getString("name")));
                meta.setDisplayName(name + ChatColor.RED + " (Out of date)");
            }
            List<Component> lore = new ArrayList<>();
            lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>" + plotDocument.getString("size") + " Plot").decoration(TextDecoration.ITALIC,false));
            lore.add(MiniMessage.miniMessage().deserialize(""));
            lore.add(MiniMessage.miniMessage().deserialize("<gray>Tags: <dark_gray>" + plotDocument.getString("tags")).decoration(TextDecoration.ITALIC,false));
            lore.add(MiniMessage.miniMessage().deserialize("<gray>Votes: <yellow>" + plotDocument.getInteger("votes") + "<dark_gray> (last 2 weeks)").decoration(TextDecoration.ITALIC,false));
            lore.add(MiniMessage.miniMessage().deserialize(""));
            lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>ID: " + plotDocument.getInteger("plotID")).decoration(TextDecoration.ITALIC,false));
            lore.add(MiniMessage.miniMessage().deserialize("<blue>↓ Node " + plotDocument.getInteger("node")).decoration(TextDecoration.ITALIC,false));
            if (Hypersquare.plotVersion == plotDocument.getInteger("version")){
                lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>Plot version: " + plotDocument.getInteger("version")).decoration(TextDecoration.ITALIC,false));
            } else {
                Component aa = MiniMessage.miniMessage().deserialize("<red>Plot version: " + plotDocument.getInteger("version")).decoration(TextDecoration.ITALIC,false);
                lore.add(aa);
            }

            meta.lore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            meta.addItemFlags(ItemFlag.HIDE_DYE);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
            meta.setDisplayName(ChatColor.RESET + meta.getDisplayName());

            plotItem.setItemMeta(meta);
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
