package hypersquare.hypersquare.serverside.codeblockmenuitems;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.serverside.dev.ItemBuilder;
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
                .name(ChatColor.translateAlternateColorCodes('&', "&6Item Management"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Giving, removing, setting"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7and saving items"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 1)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.item_management", itemManagement);

        ItemStack communication = new ItemBuilder(Material.JUNGLE_SIGN)
                .name(ChatColor.translateAlternateColorCodes('&', "&cCommunication"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Displaying text and"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7playing sounds"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 3)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.communication", communication);

        ItemStack inventoryMenus = new ItemBuilder(Material.ITEM_FRAME)
                .name(ChatColor.translateAlternateColorCodes('&', "&eInventory Menus"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Display and modification"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7of item menus"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 5)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.inventory_menus", inventoryMenus);

        ItemStack scoreboardManipulation = new ItemBuilder(Material.PAINTING)
                .name(ChatColor.translateAlternateColorCodes('&', "&bScoreboard Manipulation"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Scoreboard manipulation,"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7addition and removal"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 7)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.scoreboard_manipulation", scoreboardManipulation);

        ItemStack statistics = new ItemBuilder(Material.APPLE)
                .name(ChatColor.translateAlternateColorCodes('&', "&aStatistics"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Modification of player"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7statistics such as health,"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7hunger and XP"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 19)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.statistics", statistics);

        ItemStack settings = new ItemBuilder(Material.ANVIL)
                .name(ChatColor.translateAlternateColorCodes('&', "&dSettings"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Changing player abilities,"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7permissions and other"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7settings"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 21)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.settings", settings);

        ItemStack movement = new ItemBuilder(Material.LEATHER_BOOTS)
                .name(ChatColor.translateAlternateColorCodes('&', "&3Movement"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Launching, teleportation, and"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7other means of moving players"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 23)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.movement", movement);

        ItemStack world = new ItemBuilder(Material.SPECTRAL_ARROW)
                .name(ChatColor.translateAlternateColorCodes('&', "&cWorld"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Targeted world actions"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7and actions that change"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7the worlds appearance"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 25)
                .setCustomTag(new NamespacedKey(plugin, "short"), "")
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.world", world);

        ItemStack visualEffects = new ItemBuilder(Material.WHITE_DYE)
                .name(Utilities.convertToChatColor("#AA55FFVisual Effects"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Displaying short visual"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7effects and particles"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7to the target"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 38)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.visual_effects", visualEffects);

        ItemStack appearance = new ItemBuilder(Material.PLAYER_HEAD)
                .name(ChatColor.translateAlternateColorCodes('&', "&eAppearance"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Actions that affect the"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7target's appearance, such"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7as disguises"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 40)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.appearance", appearance);

        ItemStack miscellaneous = new ItemBuilder(Material.BEDROCK)
                .name(ChatColor.translateAlternateColorCodes('&', "&5Miscellaneous"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Actions that do not belong"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7in other categories"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 42)
                .hideFlags()
                .make();
        ItemManager.addItem("player_action_categories.miscellaneous", miscellaneous);
    }

    public static void PlayerActionItemManagement() {
        ItemStack giveItems = new ItemBuilder(Material.CHEST)
                .name(ChatColor.translateAlternateColorCodes('&', "&aGive Items"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Gives a player all of the"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7items in the chest."))
                .lore("")
                .lore(ChatColor.translateAlternateColorCodes('&', "&fChest Parameters:"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&6Item(s)&8 - &7Item(s) to give"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&cNumber&f*&8 - &7Amount to give"))
                .lore("")
                .lore(ChatColor.translateAlternateColorCodes('&', "&7*Optional"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 0)
                .setCustomTag(new NamespacedKey(plugin, "short"), "GiveItems")
                .hideFlags()
                .make();
        ItemManager.addItem("player_action.item_management.give_items", giveItems);
    }



}
