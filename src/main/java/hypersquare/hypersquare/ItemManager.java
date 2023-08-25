package hypersquare.hypersquare;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public static ItemStack myPlots() {
        ItemStack myPlots = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta meta = myPlots.getItemMeta();
        List lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Click to access your plots");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.AQUA + "◇" + ChatColor.GREEN + " My Plots " + ChatColor.AQUA + "◇");
        myPlots.setItemMeta(meta);
        return myPlots;
    }

}

