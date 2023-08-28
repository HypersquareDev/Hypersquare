package hypersquare.hypersquare.dev;


import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTItem;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class CodeItems {
    public static void register() {
        ItemStack playerEvent = new ItemBuilder(Material.DIAMOND_BLOCK)
                .name(ChatColor.AQUA + "Player Event")
                .lore(ChatColor.GRAY + "Used to execute code when something")
                .lore(ChatColor.GRAY + "is done by (or happens to) a player.")
                .lore("")
                .lore(ChatColor.WHITE + "Example:")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Detect when a player joins the plot")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Detect when a player right clicks")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Detect when a player dies")
                .make();

        ItemManager.addItem("dev.playerEvent", playerEvent);
    }
}
