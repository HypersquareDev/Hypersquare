package hypersquare.hypersquare.dev;


import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTItem;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

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
                .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
                .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "false")

                .make();


        ItemManager.addItem("dev.event", playerEvent);
        ItemStack ifPlayer = new ItemBuilder(Material.OAK_PLANKS)
                .name(ChatColor.GOLD + "If Player")
                .lore(ChatColor.GRAY + "Used to execute the code inside it")
                .lore(ChatColor.GRAY + "if a certain condition related to a")
                .lore(ChatColor.GRAY + "player is met.")
                .lore("")
                .lore(ChatColor.WHITE + "Example:")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check if a player is swimming")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check if a player has an item")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check a player's username")
                .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "true")
                .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
                .make();
        ItemManager.addItem("dev.if_player", ifPlayer);

        ItemStack playerAction = new ItemBuilder(Material.COBBLESTONE)
                .name(ChatColor.GREEN + "Player Action")
                .lore(ChatColor.GRAY + "Used to do something related to")
                .lore(ChatColor.GRAY + "a player or multiple players.")
                .lore("")
                .lore(ChatColor.WHITE + "Example:")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Send a message to a player")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Damage or heal a player")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Clear a player's inventory")
                .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
                .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
                .make();
        ItemManager.addItem("dev.player_action", playerAction);

        ItemStack callFunction = new ItemBuilder(Material.LAPIS_ORE)
                .name(ChatColor.BLUE + "Call Function")
                .lore(ChatColor.GRAY + "Used to call functions declared by")
                .lore(ChatColor.GRAY + "a Function block.")
                .lore(ChatColor.GRAY + "Code will note continue past this block")
                .lore(ChatColor.GRAY + "until the Function completes/returns.")
                .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
                .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "false")
                .make();
        ItemManager.addItem("dev.call_function", callFunction);

        ItemStack startProcess = new ItemBuilder(Material.EMERALD_ORE)
                .name(ChatColor.DARK_GREEN + "Start Process")
                .lore(ChatColor.GRAY + "Used to call functions declared by")
                .lore(ChatColor.GRAY + "a Function block.")
                .lore(ChatColor.GRAY + "Code will note continue past this block")
                .lore(ChatColor.GRAY + "until the Function completes/returns.")
                .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
                .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "false")
                .make();
        ItemManager.addItem("dev.call_function", callFunction);


    }
}
