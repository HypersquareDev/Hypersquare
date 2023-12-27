package hypersquare.hypersquare.item;

import hypersquare.hypersquare.util.Utilities;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
