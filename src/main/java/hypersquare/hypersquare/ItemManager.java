package hypersquare.hypersquare;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@UtilityClass
public class ItemManager {
    static NamespacedKey key; // Declare the key without initializing it here

    public static void initializeKey(JavaPlugin plugin) {
        key = new NamespacedKey(plugin, "itemId"); // Initialize the key using the passed plugin instance
    }
    public static void registerItems() {
        ItemStack myPlots = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta meta = myPlots.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Click to access your plots");
        meta.setLore(lore);

        meta.setDisplayName(ChatColor.AQUA + "◇" + ChatColor.GREEN + " My Plots " + ChatColor.AQUA + "◇");
        myPlots.setItemMeta(meta);

        addItem("myPlots", myPlots);

//        ItemStack playerEvent = new ItemStack(Material.DIAMOND_BLOCK, 1);
//        meta = myPlots.getItemMeta();
//        lore = new ArrayList<String>();
//        lore.add(ChatColor.GRAY + "Used to execute code when something");
//        lore.add(ChatColor.GRAY + "is done by (or happens to) a player.");
//
//        lore.add("<!italic><aqua>» </aqua></!italic><!italic><gray>Detect when a player dies");
//        meta.setLore(lore);
//
//        meta.setDisplayName(ChatColor.AQUA + "◇" + ChatColor.GREEN + " My Plots " + ChatColor.AQUA + "◇");
//        playerEvent.setItemMeta(meta);
//        addItem("playerEvent",playerEvent);
    }
    private static HashMap<String, ItemStack> items;

    public static void addItem(String itemId, ItemStack item) {
        item.getItemMeta().getPersistentDataContainer().set(key, PersistentDataType.STRING, itemId);
        items.put(itemId, item);
    }

    public static ItemStack getItem(String itemId) {
        return items.get(itemId);
    }

    public static String getItemID(ItemStack item) {
        if (item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            return item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "itemId"), PersistentDataType.STRING);
        }
        return null;
    }

    public static ItemStack setItemTags(ItemStack item, String tagName, String value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), tagName), PersistentDataType.STRING, value);
        item.setItemMeta(meta);
        return item;
    }
}

