package hypersquare.hypersquare.menu.system;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MenuItem {
    public final ItemStack item;
    private @Nullable Runnable clickHandler;

    public MenuItem(Material mat) {
        item = new ItemStack(mat);

        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(""));

        item.setItemMeta(meta);
    }

    public MenuItem(ItemStack item) {
        this.item = item;
    }

    public MenuItem name(Component name) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public MenuItem lore(List<Component> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.lore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public MenuItem onClick(Runnable onClick) {
        clickHandler = onClick;
        return this;
    }

    public void performClick() {
        if (clickHandler != null) clickHandler.run();
    }
}
