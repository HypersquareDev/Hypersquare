package hypersquare.hypersquare.item.action.control;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.item.action.ActionMenuItem;
import hypersquare.hypersquare.util.Utilities;
import hypersquare.hypersquare.util.color.Colors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public enum ControlItems implements ActionMenuItem {
    THREAD(Material.PRISMARINE_SHARD, Colors.TEAL, "Thread Manipulation.", "<gray>Actions that%n<gray>manipulate the%n<gray>current thread.", 11)
        ;

    public final Material material;
    public final TextColor nameColor;
    public final String name;
    public final String lore;
    public final int slot;

    ControlItems(Material material, TextColor nameColor, String name, String lore, int slot) {
        this.material = material;
        this.nameColor = nameColor;
        this.name = name;
        this.lore = lore;
        this.slot = slot;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    public ItemStack build() {
        return Utilities.formatItem(lore, material, "<" + nameColor.asHexString() + ">" + name);
    }

    @Override
    public @NotNull Component getName() {
        return Hypersquare.cleanMM.deserialize(name);
    }
}
