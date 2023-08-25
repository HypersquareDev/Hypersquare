package hypersquare.hypersquare;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUIManager {
    static NamespacedKey key = new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "itemId");

    public static void registerItems() {
        ItemStack myPlots = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta meta = myPlots.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Click to access your plots");
        meta.setLore(lore);

        meta.setDisplayName(ChatColor.AQUA + "◇" + ChatColor.GREEN + " My Plots " + ChatColor.AQUA + "◇");
        myPlots.setItemMeta(meta);

        addItem("myPlots", myPlots);
    }
    private static HashMap<String, ItemStack> items;

    public GUIManager() {
        items = new HashMap<>();
    }

    public static void addMenu(String itemId, ItemStack item) {
        item.getItemMeta().getPersistentDataContainer().set(key, PersistentDataType.STRING, itemId);
        items.put(itemId, item);
    }

    public static ItemStack getMenu(String itemId) {
        return items.get(itemId);
    }

    public static String getMenuID(ItemStack item) {
        if (item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            return item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "itemId"), PersistentDataType.STRING);
        }
        return null;
    }
}
