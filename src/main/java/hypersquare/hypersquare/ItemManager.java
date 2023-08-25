package hypersquare.hypersquare;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemManager {

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

    public ItemManager() {
        items = new HashMap<>();
    }

    public static void addItem(String itemId, ItemStack item) {
        items.put(itemId, item);
    }

    public static ItemStack getItem(String itemId) {
        return items.get(itemId);
    }

}

