package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.utils.managers.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Color;
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
                .setCustomTag(new NamespacedKey(plugin,"short"),"Join")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.Player_Join_Game_Event", Player_Join_Game_Event);
        ItemStack Player_Leave_Game_Event = new ItemBuilder(Material.POISONOUS_POTATO)
                .name(ChatColor.GREEN + "Player Leave Game Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player leaves the plot.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),1)
                .setCustomTag(new NamespacedKey(plugin,"short"),"Leave")

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
                .setCustomTag(new NamespacedKey(plugin,"short"),"Command")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.Player_Command_Event", Player_Command_Event);
        ItemStack Resource_Pack_Load = new ItemBuilder(Material.GREEN_SHULKER_BOX)
                .name(ChatColor.DARK_AQUA + "Resource Pack Load")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player finishes loading a")
                .lore(ChatColor.GRAY + "plot resource pack.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),3)
                .setCustomTag(new NamespacedKey(plugin,"short"),"PackLoad")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.Resource_Pack_Load", Resource_Pack_Load);
        ItemStack Resource_Pack_Decline = new ItemBuilder(Material.RED_SHULKER_BOX)
                .name(ChatColor.DARK_AQUA + "Resource Pack Decline")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player declines a plot")
                .lore(ChatColor.GRAY + "resource pack prompt.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),4)
                .setCustomTag(new NamespacedKey(plugin,"short"),"PackDecline")

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

    public static void PlayerEvent_MovementEvents(){
        ItemStack Player_Walk_Event = new ItemBuilder(Material.GRASS_BLOCK)
                .name("Player Walk Event")
                .lore(ChatColor.GRAY + "Executes code while")
                .lore(ChatColor.GRAY + "a player is walking.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),0)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.Player_Walk_Event", Player_Walk_Event);
        ItemStack Player_Jump_Event = new ItemBuilder(Material.RABBIT_FOOT)
                .name("Player Jump Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player jumps.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),1)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.Player_Jump_Event", Player_Jump_Event);
        ItemStack Player_Sneak_Event = new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
                .name(ChatColor.BLUE + "Player Sneak Event")
                .lore(ChatColor.GRAY + "Executes code when")
                .lore(ChatColor.GRAY + "a player sneaks.")
                .lore("")
                .lore(ChatColor.BLUE + "Additional Info:")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Cancelling this event will")
                .lore(ChatColor.GRAY + "not update what the sneaking")
                .lore(ChatColor.GRAY + "player sees!")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),2)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.Player_Sneak_Event", Player_Sneak_Event);
        ItemStack Player_Unsneak_Event = new ItemBuilder(Material.IRON_LEGGINGS)
                .name(ChatColor.BLUE + "Player Unsneak Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player stops sneaking.")
                .lore("")
                .lore(ChatColor.BLUE + "Additional Info:")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Cancelling this event will")
                .lore(ChatColor.GRAY + "not update what the sneaking")
                .lore(ChatColor.GRAY + "player sees!")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),3)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.Player_Unsneak_Event", Player_Unsneak_Event);
        ItemStack Player_Start_Sprint_Event = new ItemBuilder(Material.GOLDEN_BOOTS)
                .name("Player Start Sprint Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player starts sprinting.")
                .lore("")
                .lore(ChatColor.BLUE + "Additional Info:")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Cancelling this event will")
                .lore(ChatColor.GRAY + "only result in the sprint")
                .lore(ChatColor.GRAY + "particles being hidden.")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Client side particles are")
                .lore(ChatColor.GRAY + "not hidden!")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),4)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.Player_Start_Sprint_Event", Player_Start_Sprint_Event);
        ItemStack Player_Stop_Sprint_Event = new ItemBuilder(Material.IRON_BOOTS)
                .name("Player Stop Sprinting Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player stops sprinting.")
                .lore("")
                .lore(ChatColor.BLUE + "Additional Info:")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Cancelling this event will")
                .lore(ChatColor.GRAY + "only result in the sprint")
                .lore(ChatColor.GRAY + "particles being shown.")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Client side particles are")
                .lore(ChatColor.GRAY + "not shown!")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),5)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.Player_Stop_Sprint_Event", Player_Stop_Sprint_Event);
        ItemStack Player_Start_Flight_Event = new ItemBuilder(Material.ELYTRA)
                .name("Player Start Flight Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player starts flying.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),6)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.Player_Start_Flight_Event", Player_Start_Flight_Event);
        ItemStack Player_Stop_Flight_Event = new ItemBuilder(Material.ELYTRA)
                .name("Player Stop Flight Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player stops flying.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),7)
                .hideFlags()
                .setUnbreakable(true)
                .damage(431)
                .make();
        ItemManager.addItem("player_event.movement_events.Player_Stop_Flight_Event", Player_Stop_Flight_Event);
        ItemStack Player_Riptide_Event = new ItemBuilder(Material.TRIDENT)
                .name("Player Riptide Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "throws a riptide trident.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),8)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.Player_Riptide_Event", Player_Riptide_Event);
        ItemStack Player_Dismount_Event = new ItemBuilder(Material.DARK_OAK_BOAT)
                .name(ChatColor.DARK_AQUA + "Player Dismount Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player dismounts a vehicle")
                .lore(ChatColor.GRAY + "or other entity.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),9)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.Player_Dismount_Event", Player_Dismount_Event);
        ItemStack Player_Horse_Jump_Event = new ItemBuilder(Material.GOLDEN_HORSE_ARMOR)
                .name("Player Horse Jump Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "causes a horse to jump.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),10)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.Player_Horse_Jump_Event", Player_Horse_Jump_Event);
        ItemStack Player_Vehicle_Jump_Event = new ItemBuilder(Material.FURNACE_MINECART)
                .name("Player Vehicle Jump Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player presses the jump key")
                .lore(ChatColor.GRAY + "while riding a vehicle")
                .lore(ChatColor.GRAY + "or other entity.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),11)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.Player_Vehicle_Jump_Event", Player_Vehicle_Jump_Event);

    }

    public static void PlayerEvent_ItemEvents(){
        ItemStack Player_Click_Menu_Slot_Event = new ItemBuilder(Material.CRAFTING_TABLE)
                .name(ChatColor.AQUA + "Player Click Menu Slot Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "clicks a slot in an inventory")
                .lore(ChatColor.GRAY + "menu.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancelled automatically")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),0)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.Player_Click_Menu_Slot_Event", Player_Click_Menu_Slot_Event);
        ItemStack Player_Click_Inventory_Slot_Event = new ItemBuilder(Material.LIGHT_GRAY_SHULKER_BOX)
                .name(ChatColor.AQUA + "Player Click Inventory Slot Event")
                .lore(ChatColor.GRAY + "Executes code when a player clicks")
                .lore(ChatColor.GRAY + "a slot inside their inventory.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),1)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.Player_Click_Inventory_Slot_Event", Player_Click_Inventory_Slot_Event);
        ItemStack Player_Pick_Up_Item_Event = new ItemBuilder(Material.GLOWSTONE_DUST)
                .name(ChatColor.YELLOW + "Player Pick Up Item Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player picks up an item.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),2)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.Player_Pick_Up_Item_Event", Player_Pick_Up_Item_Event);
        ItemStack Player_Drop_Item_Event = new ItemBuilder(Material.SUGAR)
                .name(ChatColor.YELLOW + "Player Drop Item Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player drops an item.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),3)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.Player_Drop_Item_Event", Player_Drop_Item_Event);
        ItemStack Player_Consume_Item_Event = new ItemBuilder(Material.COOKED_CHICKEN)
                .name(ChatColor.YELLOW + "Player Consume Item Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "eats or drinks an item.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),4)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.Player_Consume_Item_Event", Player_Consume_Item_Event);
        ItemStack Player_Break_Item_Event = new ItemBuilder(Material.STONE_PICKAXE)
                .name(ChatColor.YELLOW + "Player Break Item Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player breaks an item.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),5)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.Player_Break_Item_Event", Player_Break_Item_Event);
        ItemStack Player_Close_Inventory_Event = new ItemBuilder(Material.STRUCTURE_VOID)
                .name(ChatColor.GOLD + "Player Close Inventory Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player closes an inventory.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),6)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.Player_Close_Inventory_Event", Player_Close_Inventory_Event);
        ItemStack Player_Fish_Event = new ItemBuilder(Material.FISHING_ROD)
                .name(ChatColor.GOLD + "Player Fish Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "fishes an entity,")
                .lore(ChatColor.GRAY + "player, or nothing.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),7)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.Player_Fish_Event", Player_Fish_Event);

    }

    public static void PlayerEvent_DamageEvents(){
        ItemStack Player_Take_Damage_Event = new ItemBuilder(Material.DEAD_BUSH)
                .name(ChatColor.RED + "Player Take Damage Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player takes damage.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),0)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.Player_Take_Damage_Event", Player_Take_Damage_Event);
        ItemStack Player_Damage_Player_Event = new ItemBuilder(Material.IRON_SWORD)
                .name(ChatColor.RED + "Player Damage Player Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "damages another player.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),1)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.Player_Damage_Player_Event", Player_Damage_Player_Event);
        ItemStack Player_Damage_Entity_Event = new ItemBuilder(Material.STONE_SWORD)
                .name(ChatColor.RED + "Player Damage Entity Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player damages an entity.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),2)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.Player_Damage_Entity_Event", Player_Damage_Entity_Event);
        ItemStack Entity_Damage_Player_Event = new ItemBuilder(Material.WOODEN_SWORD)
                .name(ChatColor.RED + "Entity Damage Player Event")
                .lore(ChatColor.GRAY + "Executes code when an")
                .lore(ChatColor.GRAY + "entity damages a player.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),3)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.Entity_Damage_Player_Event", Entity_Damage_Player_Event);
        ItemStack Player_Heal_Event = new ItemBuilder(Material.SPLASH_POTION)
                .name("Player Heal Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "regains health from any")
                .lore(ChatColor.GRAY + "source.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),4)
                .hideFlags()
                .setPotionColor(Color.fromRGB(255, 0 ,0))
                .make();
        ItemManager.addItem("player_event.damage_events.Player_Heal_Event", Player_Heal_Event);
        ItemStack Player_Shoot_Bow_Event = new ItemBuilder(Material.BOW)
                .name(ChatColor.BLUE + "Player Shoot Bow Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "fires an arrow with a bow.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),5)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.Player_Shoot_Bow_Event", Player_Shoot_Bow_Event);
        ItemStack Player_Shoot_Projectile_Event = new ItemBuilder(Material.SNOWBALL)
                .name(ChatColor.BLUE + "Player Shoot Projectile Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "throws a projectile such")
                .lore(ChatColor.GRAY + "as snowballs or eggs.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),6)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.Player_Shoot_Projectile_Event", Player_Shoot_Projectile_Event);
        ItemStack Player_Projectile_Hit_Event = new ItemBuilder(Material.SPECTRAL_ARROW)
                .name(ChatColor.DARK_AQUA + "Player Projectile Hit Event")
                .lore(ChatColor.GRAY + "Executes code when a projectile")
                .lore(ChatColor.GRAY + "launched by a player hits a block,")
                .lore(ChatColor.GRAY + "an entity, or another player.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),7)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.Player_Projectile_Hit_Event", Player_Projectile_Hit_Event);
        ItemStack Player_Projectile_Damage_Player_Event = new ItemBuilder(Material.ARROW)
                .name(ChatColor.RED + "Projectile Damage Player Event")
                .lore(ChatColor.GRAY + "Executes code when a projectile")
                .lore(ChatColor.GRAY + "damages a player.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),8)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.Player_Projectile_Damage_Player_Event", Player_Projectile_Damage_Player_Event);
        ItemStack Potion_Cloud_Imbue_Player_Event = new ItemBuilder(Material.ARROW)
                .name(ChatColor.DARK_AQUA + "Potion Cloud Imbue Player Event")
                .lore(ChatColor.GRAY + "Executes code when an area of")
                .lore(ChatColor.GRAY + "effect cloud applies its potion")
                .lore(ChatColor.GRAY + "effect(s) to a player.")
                .lore("")
                .lore(ChatColor.BLUE + "Additional Info:")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Area of effect clouds that do")
                .lore(ChatColor.GRAY + "not apply any potion effects")
                .lore(ChatColor.GRAY + "will not trigger this event.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),9)
                .hideFlags()
                .setPotionColor(Color.fromRGB(255, 255, 0))
                .make();
        ItemManager.addItem("player_event.damage_events.Potion_Cloud_Imbue_Player_Event", Potion_Cloud_Imbue_Player_Event);

    }

    public static void PlayerEvent_DeathEvents(){
        ItemStack Player_Death_Event = new ItemBuilder(Material.REDSTONE_WIRE)
                .name(ChatColor.DARK_RED + "Player Death Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "dies, not as a result of another")
                .lore(ChatColor.GRAY + "player or entity.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),0)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.death_events.Player_Death_Event", Player_Death_Event);
        ItemStack Player_Kill_Player_Event = new ItemBuilder(Material.SKELETON_SKULL)
                .name(ChatColor.DARK_RED + "Player Kill Player Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "kills another player.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),1)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.death_events.Player_Kill_Player_Event", Player_Kill_Player_Event);
        ItemStack Player_Resurrect_Event = new ItemBuilder(Material.TOTEM_OF_UNDYING)
                .name(ChatColor.DARK_AQUA + "Player Resurrect Event")
                .lore(ChatColor.GRAY + "Executes code when")
                .lore(ChatColor.GRAY + "a player resurrects with")
                .lore(ChatColor.GRAY + "a totem of undying.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),2)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.death_events.Player_Resurrect_Event", Player_Resurrect_Event);
        ItemStack Player_Kill_Mob_Event = new ItemBuilder(Material.SKELETON_SPAWN_EGG)
                .name(ChatColor.DARK_RED + "Player Kill Mob Event")
                .lore(ChatColor.GRAY + "Executes code when")
                .lore(ChatColor.GRAY + "a player kills a mob.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),3)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.death_events.Player_Kill_Mob_Event", Player_Kill_Mob_Event);
        ItemStack Mob_Kill_Player_Event = new ItemBuilder(Material.BONE)
                .name(ChatColor.DARK_RED + "Mob Kill Player Event")
                .lore(ChatColor.GRAY + "Executes code when")
                .lore(ChatColor.GRAY + "a mob kills a player.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),4)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.death_events.Mob_Kill_Player_Event", Mob_Kill_Player_Event);
        ItemStack Player_Respawn_Event = new ItemBuilder(Material.BONE)
                .name(ChatColor.DARK_AQUA + "Player Respawn Event")
                .lore(ChatColor.GRAY + "Executes code when")
                .lore(ChatColor.GRAY + "a player respawns.")
                .setCustomIntTag(new NamespacedKey(plugin,"slot"),5)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.death_events.Player_Respawn_Event", Player_Respawn_Event);

    }
}
