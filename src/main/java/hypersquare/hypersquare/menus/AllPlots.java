package hypersquare.hypersquare.menus;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.plot.PlotDatabase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static hypersquare.hypersquare.Hypersquare.mm;

public class AllPlots {

    public static PaginatedGui create() {
        PaginatedGui gui = Gui.paginated()
                .title(Component.text("GUI Title!"))
                .rows(6)
                .create();

        gui.setItem(6, 3, ItemBuilder.from(Material.PAPER).setName("Previous").asGuiItem(event -> {gui.previous(); event.setCancelled(true);}));
        gui.setItem(6, 7, ItemBuilder.from(Material.PAPER).setName("Next").asGuiItem(event -> {gui.next(); event.setCancelled(true);}));

        List<Document> plots = PlotDatabase.getAllPlots();

        for (Document plotDocument : plots) {
            ItemStack plotItem = new ItemStack(Material.matchMaterial(plotDocument.getString("icon")));
            ItemMeta meta = plotItem.getItemMeta();
            if (Hypersquare.plotVersion == plotDocument.getInteger("version")) {
                meta.displayName(mm.deserialize(plotDocument.getString("name")));
            } else {
                String name = plotDocument.getString("name");
                meta.displayName(mm.deserialize(name + "<red>" + " (Out of date)"));
            }
            int plotID = plotDocument.getInteger("plotID");
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

            gui.addItem(ItemBuilder.from(plotItem).asGuiItem(event -> ChangeGameMode.playMode((Player) event.getWhoClicked(),plotID)));
        }

        return gui;
    }
}
