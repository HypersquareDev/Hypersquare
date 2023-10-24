package hypersquare.hypersquare.serverside.codeblockmenuitems;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.serverside.ItemBuilder;
import hypersquare.hypersquare.serverside.utils.Utilities;
import hypersquare.hypersquare.serverside.utils.managers.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerActionItems {
    static Plugin plugin = Hypersquare.getPlugin(Hypersquare.class);

    public static void initItems(){
        PlayerActionCategories();
        PlayerActionItemManagement();
    }

    public static void PlayerActionCategories(){
        ItemStack itemManagement = new ItemBuilder(Material.CHEST)
                .name("<gold>Item Management")
                .lore("<gray>Giving, removing, setting")
                .lore("<gray>and saving items")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 1)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.item_management", itemManagement);

        ItemStack communication = new ItemBuilder(Material.JUNGLE_SIGN)
                .name("<red>Communication")
                .lore("<gray>Displaying text and")
                .lore("<gray>playing sounds")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 3)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.communication", communication);

        ItemStack inventoryMenus = new ItemBuilder(Material.ITEM_FRAME)
                .name("<yellow>Inventory Menus")
                .lore("<gray>Display and modification")
                .lore("<gray>of item menus")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 5)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.inventory_menus", inventoryMenus);

        ItemStack scoreboardManipulation = new ItemBuilder(Material.PAINTING)
                .name("<aqua>Scoreboard Manipulation")
                .lore("<gray>Scoreboard manipulation,")
                .lore("<gray>addition and removal")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 7)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.scoreboard_manipulation", scoreboardManipulation);

        ItemStack statistics = new ItemBuilder(Material.APPLE)
                .name("<green>Statistics")
                .lore("<gray>Modification of player")
                .lore("<gray>statistics such as health,")
                .lore("<gray>hunger and XP")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 19)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.statistics", statistics);

        ItemStack settings = new ItemBuilder(Material.ANVIL)
                .name("<light_purple>Settings")
                .lore("<gray>Changing player abilities,")
                .lore("<gray>permissions and other")
                .lore("<gray>settings")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 21)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.settings", settings);

        ItemStack movement = new ItemBuilder(Material.LEATHER_BOOTS)
                .name("<dark_aqua>Movement")
                .lore("<gray>Launching, teleportation, and")
                .lore("<gray>other means of moving players")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 23)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.movement", movement);

        ItemStack world = new ItemBuilder(Material.SPECTRAL_ARROW)
                .name("<red>World")
                .lore("<gray>Targeted world actions")
                .lore("<gray>and actions that change")
                .lore("<gray>the worlds appearance")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 25)
                .setCustomTag(new NamespacedKey(plugin, "short"), "")
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.world", world);

        ItemStack visualEffects = new ItemBuilder(Material.WHITE_DYE)
                .name("<color:#AA55FF>Visual Effects")
                .lore("<gray>Displaying short visual")
                .lore("<gray>effects and particles")
                .lore(ChatColor.translateAlternateColorCodes('&', "<gray>to the target"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 38)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.visual_effects", visualEffects);

        ItemStack appearance = new ItemBuilder(Material.PLAYER_HEAD)
                .name(ChatColor.translateAlternateColorCodes('&', "<yellow>Appearance"))
                .lore(ChatColor.translateAlternateColorCodes('&', "<gray>Actions that affect the"))
                .lore(ChatColor.translateAlternateColorCodes('&', "<gray>target's appearance, such"))
                .lore(ChatColor.translateAlternateColorCodes('&', "<gray>as disguises"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 40)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.appearance", appearance);

        ItemStack miscellaneous = new ItemBuilder(Material.BEDROCK)
                .name(ChatColor.translateAlternateColorCodes('&', "&5Miscellaneous"))
                .lore(ChatColor.translateAlternateColorCodes('&', "<gray>Actions that do not belong"))
                .lore(ChatColor.translateAlternateColorCodes('&', "<gray>in other categories"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 42)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.miscellaneous", miscellaneous);
    }

    public static void PlayerActionItemManagement() {
        ItemStack giveItems = new ItemBuilder(Material.CHEST)
                .name(ChatColor.translateAlternateColorCodes('&', "<green>Give Items"))
                .lore(ChatColor.translateAlternateColorCodes('&', "<gray>Gives a player all of the"))
                .lore(ChatColor.translateAlternateColorCodes('&', "<gray>items in the chest."))
                .lore("")
                .lore(ChatColor.translateAlternateColorCodes('&', "<reset>Chest Parameters:"))
                .lore(ChatColor.translateAlternateColorCodes('&', "<gold>Item(s)&8 - <gray>Item(s) to give"))
                .lore(ChatColor.translateAlternateColorCodes('&', "<red>Number<reset>*&8 - <gray>Amount to give"))
                .lore("")
                .lore(ChatColor.translateAlternateColorCodes('&', "<gray>*Optional"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 0)
                .setCustomTag(new NamespacedKey(plugin, "short"), "GiveItems")
                .hideFlags()
                .make();
        ItemManager.addItem("player_action.item_management.give_items", giveItems);
    }



}
