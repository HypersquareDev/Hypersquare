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
    public static void PlayerEventCategories(){
        ItemStack Plot_and_Server_Events = new ItemBuilder(Material.COMMAND_BLOCK)
                .name(ChatColor.YELLOW + "Plot and Server Events")
                .lore(ChatColor.GRAY + "Joining and leaving plots,")
                .lore(ChatColor.GRAY + "plot commands")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),10)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event_categories.Plot_and_Server_Events", Plot_and_Server_Events);
        ItemStack Click_Events = new ItemBuilder(Material.DIAMOND_PICKAXE)
                .name(ChatColor.AQUA + "Click Events")
                .lore(ChatColor.GRAY + "Right and left clicking,")
                .lore(ChatColor.GRAY + "breaking blocks")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),13)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event_categories.Click_Events", Click_Events);
        ItemStack Movement_Events = new ItemBuilder(Material.LEATHER_BOOTS)
                .name(ChatColor.DARK_AQUA + "Movement Events")
                .lore(ChatColor.GRAY + "Jumping, walking,")
                .lore(ChatColor.GRAY + "and flying")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),16)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event_categories.Movement_Events", Movement_Events);
        ItemStack Item_Events = new ItemBuilder(Material.ITEM_FRAME)
                .name(ChatColor.GOLD + "Item Events")
                .lore(ChatColor.GRAY + "Dropping, picking up")
                .lore(ChatColor.GRAY + "and eating items")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),28)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event_categories.Item_Events", Item_Events);
        ItemStack Damage_Events = new ItemBuilder(Material.IRON_SWORD)
                .name(ChatColor.RED + "Damage Events")
                .lore(ChatColor.GRAY + "Getting damaged,")
                .lore(ChatColor.GRAY + "shooting a bow")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),31)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event_categories.Damage_Events", Damage_Events);
        ItemStack Death_Events = new ItemBuilder(Material.SKELETON_SKULL)
                .name(ChatColor.DARK_RED + "Death Events")
                .lore(ChatColor.GRAY + "Dying, and respawning")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),34)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event_categories.Death_Events", Death_Events);

    }

    public static void PlayerEvent_PlotAndServerEvents(){
        ItemStack Player_Join_Game_Event = new ItemBuilder(Material.POTATO)
                .name(ChatColor.GREEN + "Player Join Game Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player joins the plot.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),0)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.Player_Join_Game_Event", Player_Join_Game_Event);
        ItemStack Player_Leave_Game_Event = new ItemBuilder(Material.POISONOUS_POTATO)
                .name(ChatColor.GREEN + "Player Leave Game Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player leaves the plot.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),1)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.Player_Leave_Game_Event", Player_Leave_Game_Event);
        ItemStack Player_Command_Event = new ItemBuilder(Material.COMMAND_BLOCK)
                .name(ChatColor.GREEN + "Player Command Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "types a command on the plot.")
                .lore("")
                .lore(ChatColor.WHITE + "Example:")
                .lore(ChatColor.AQUA + "\"@command\"")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),2)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.Player_Command_Event", Player_Command_Event);
        ItemStack Resource_Pack_Load = new ItemBuilder(Material.GREEN_SHULKER_BOX)
                .name(ChatColor.DARK_AQUA + "Resource Pack Load")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player finishes loading a")
                .lore(ChatColor.GRAY + "plot resource pack.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),3)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.Resource_Pack_Load", Resource_Pack_Load);
        ItemStack Resource_Pack_Decline = new ItemBuilder(Material.RED_SHULKER_BOX)
                .name(ChatColor.DARK_AQUA + "Resource Pack Decline")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player declines a plot")
                .lore(ChatColor.GRAY + "resource pack prompt.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),4)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.Resource_Pack_Decline", Resource_Pack_Decline);

    }

    public static void PlayerEvent_ClickEvents(){
        ItemStack Player_Right_Click_Event = new ItemBuilder(Material.DIAMOND_PICKAXE)
                .name(ChatColor.AQUA + "Player Right Click Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "right clicks while looking at a")
                .lore(ChatColor.GRAY + "block or holding an item.")
                .lore("")
                .lore(ChatColor.BLUE + "Additional Info:")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Executes multiple times if a")
                .lore(ChatColor.GRAY + "player holds down right click.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),0)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.Player_Right_Click_Event", Player_Right_Click_Event);
        ItemStack Player_Left_Click_Event = new ItemBuilder(Material.IRON_PICKAXE)
                .name(ChatColor.AQUA + "Player Left Click Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "left clicks.")
                .lore("")
                .lore(ChatColor.BLUE + "Additional Info:")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Does not execute on left")
                .lore(ChatColor.GRAY + "clicks where a player deals")
                .lore(ChatColor.GRAY + "damage.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),1)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.Player_Left_Click_Event", Player_Left_Click_Event);
        ItemStack Player_Right_Click_Entity_Event = new ItemBuilder(Material.ARMOR_STAND)
                .name(ChatColor.AQUA + "Player Right Click Entity Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "right clicks an entity.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),2)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.Player_Right_Click_Entity_Event", Player_Right_Click_Entity_Event);
        ItemStack Player_Right_Click_Player_Event = new ItemBuilder(Material.PLAYER_HEAD)
                .name(ChatColor.AQUA + "Player Right Click Player Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "clicks another player.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),3)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.Player_Right_Click_Player_Event", Player_Right_Click_Player_Event);
        ItemStack Player_Place_Block_Event = new ItemBuilder(Material.POLISHED_ANDESITE)
                .name(ChatColor.GOLD + "Player Place Block Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player places a block.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),4)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.Player_Place_Block_Event", Player_Place_Block_Event);
        ItemStack Player_Break_Block_Event = new ItemBuilder(Material.COBBLESTONE)
                .name(ChatColor.GOLD + "Player Break Block Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player breaks a block.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),5)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.Player_Break_Block_Event", Player_Break_Block_Event);
        ItemStack Player_Swap_Hands_Event = new ItemBuilder(Material.NETHERITE_SCRAP)
                .name(ChatColor.BLUE + "Player Swap Hands Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "swaps an item or items between")
                .lore(ChatColor.GRAY + "their main hand and off hand.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),6)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.Player_Swap_Hands_Event", Player_Swap_Hands_Event);
        ItemStack Player_Change_Slot_Event = new ItemBuilder(Material.SLIME_BALL)
                .name(ChatColor.BLUE + "Player Change Slot Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "changes their hotbar slot.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),7)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.Player_Change_Slot_Event", Player_Change_Slot_Event);
        ItemStack Player_Tame_Mob_Event = new ItemBuilder(Material.BONE)
                .name(ChatColor.YELLOW + "Player Tame Mob Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "tames a mob.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),8)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.Player_Tame_Mob_Event", Player_Tame_Mob_Event);

    }
}