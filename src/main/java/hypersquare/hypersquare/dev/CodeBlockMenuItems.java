package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class CodeBlockMenuItems {
    static Plugin plugin = Hypersquare.getPlugin(Hypersquare.class);
    public static void PayerEventCategories(){
        ItemStack example = new ItemBuilder(Material.LAPIS_ORE)
                .name(ChatColor.BLUE + "Call Function")
                .lore(ChatColor.GRAY + "Used to call functions declared by")
                .lore(ChatColor.GRAY + "a Function block.")
                .lore(ChatColor.GRAY + "Code will note continue past this block")
                .lore(ChatColor.GRAY + "until the Function completes/returns.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),1)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event_categories.example", example);
    }
}
