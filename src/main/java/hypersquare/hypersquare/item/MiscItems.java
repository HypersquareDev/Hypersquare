package hypersquare.hypersquare.item;

import hypersquare.hypersquare.util.Utilities;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum MiscItems {
    MY_PLOTS(Material.ENCHANTED_BOOK, "<aqua>◇ <green>My Plots <aqua>◇", "<gray>Click to access your plots."),
    CLAIM_PLOT(Material.GREEN_STAINED_GLASS, "<#7FFF7F>Claim new plot", "<gray>Click here to claim a new plot!");

    public final Material material;
    public final String name;
    public final String lore;

    MiscItems(Material material, String name, String lore) {
        this.material = material;
        this.name = name;
        this.lore = lore;
    }

    public ItemStack build() {
        return Utilities.formatItem(lore, material, name);
    }
}
