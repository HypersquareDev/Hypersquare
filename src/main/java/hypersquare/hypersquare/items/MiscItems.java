package hypersquare.hypersquare.items;

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
    MY_PLOTS(Material.ENCHANTED_BOOK, "<aqua>◇ <green>My Plots <aqua>◇", "<gray>Click to access your plots."),
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
