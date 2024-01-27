package hypersquare.hypersquare.item.action.var;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.item.action.ActionMenuItem;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum SetVariableItems implements ActionMenuItem {

    //TODO: add other categories and maybe change this one
    GENERAL(Material.ANVIL, "<white>General", "<gray>General actions that do not%n<gray>belong in other categories.", 0),
    ;

    public final Material material;
    public final String name;
    public final String lore;
    public final int slot;

    SetVariableItems(Material material, String name, String lore, int slot) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.slot = slot;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    public ItemStack build() {
        return Utilities.formatItem(lore, material, name);
    }

    @Override
    public Component getName() {
        return Hypersquare.cleanMM.deserialize(name);
    }
}
