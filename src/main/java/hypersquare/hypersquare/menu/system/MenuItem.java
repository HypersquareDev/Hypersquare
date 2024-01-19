package hypersquare.hypersquare.menu.system;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MenuItem {
    public final ItemStack item;
    private Runnable clickHandler;

    public MenuItem(Material mat) {
        item = new ItemStack(Material.STONE);
        item.setType(mat);
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
