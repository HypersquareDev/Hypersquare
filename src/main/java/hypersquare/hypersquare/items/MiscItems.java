package hypersquare.hypersquare.items;

import hypersquare.hypersquare.utils.Colors;
import hypersquare.hypersquare.utils.ItemBuilder;
import hypersquare.hypersquare.utils.Utilities;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static hypersquare.hypersquare.Hypersquare.mm;

public enum MiscItems {


    MY_PLOTS(Material.ENCHANTED_BOOK, Colors.DECORATION + "«" + Colors.PRIMARY_INFO + " My Plots " + Colors.DECORATION + "»", "<gray>Click to access your plots."),
    GAME_MENU(Material.DIAMOND, Colors.DECORATION + "«" + Colors.PRIMARY_INFO + " Game Menu " + Colors.DECORATION + "»",Colors.SECONDARY_INFO + "Click to access other people's plots."),
    MENU_FILLER(Material.GRAY_STAINED_GLASS_PANE," ", ""),
    FEATURED_PLOTS(Material.ENCHANTED_BOOK, Colors.CELEBRATORY + "⭐" + Colors.PRIMARY_INFO + " Featured Plots " + Colors.CELEBRATORY + "⭐", "<gray>Plots that are featured by administrators."),
    NEWLY_PUBLISHED(Material.WRITABLE_BOOK,Colors.DECORATION + "⌚" + Colors.PRIMARY_INFO + " Newly Published " + Colors.DECORATION + "⌚","<gray>Newly released games by players"),
    CLAIM_PLOT(Material.GREEN_STAINED_GLASS, "<#7FFF7F>Claim new plot", "<gray>Click here to claim a new plot!");

    @Getter final Material material;
    @Getter final String name;
    @Getter final String lore;
    MiscItems(Material material, String name, String lore) {
        this.material = material;
        this.name = name;
        this.lore = lore;
    }

    public ItemStack build() {
        return Utilities.formatItem(lore, material, name);
    }
}
