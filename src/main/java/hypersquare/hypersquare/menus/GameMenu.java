package hypersquare.hypersquare.menus;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.items.MiscItems;
import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.plot.PlotDatabase;
import hypersquare.hypersquare.utils.Utilities;
import mc.obliviate.inventory.Icon;
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

public class GameMenu {
    public static Gui create(){
        Gui gui = Gui.gui()
                .title(Component.text("Game Menu"))
                .rows(5)
                .create();

        gui.disableAllInteractions();
        gui.getFiller().fillBorder(ItemBuilder.from(MiscItems.MENU_FILLER.build()).asGuiItem());
        gui.setItem(1,5, ItemBuilder.from(MiscItems.FEATURED_PLOTS.build()).asGuiItem());
        gui.setItem(5,2, ItemBuilder.from(MiscItems.NEWLY_PUBLISHED.build()).asGuiItem(event -> NewlyPublishedMenu.create().open(event.getWhoClicked())));

        return gui;
    }
}
