package hypersquare.hypersquare.codeblockmenuitems;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.ItemBuilder;
import hypersquare.hypersquare.utils.Utilities;
import hypersquare.hypersquare.utils.managers.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerEventItems {
    static Plugin plugin = Hypersquare.getPlugin(Hypersquare.class);

    public static void initItems(){
        PlayerEventDeathEvents();
        PlayerEventCategories();
        PlayerEventDamageEvents();
        PlayerEventItemEvents();
        PlayerEventMovementEvents();
        PlayerEventPlotAndServerEvents();
        PlayerEventClickEvents();
    }
    public static void PlayerEventCategories(){
        ItemStack plotandServerEvents = new ItemBuilder(Material.COMMAND_BLOCK)
                .name(ChatColor.translateAlternateColorCodes('&', "&ePlot and Server Events"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Joining and leaving plots,"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7plot commands"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 0)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event_categories.plot_and_server_events", plotandServerEvents);
        ItemStack clickEvents = new ItemBuilder(Material.DIAMOND_PICKAXE)
                .name(ChatColor.translateAlternateColorCodes('&', "&bClick Events"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Right and left clicking,"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7breaking blocks"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 13)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event_categories.click_events", clickEvents);
        ItemStack movementEvents = new ItemBuilder(Material.LEATHER_BOOTS)
                .name(ChatColor.translateAlternateColorCodes('&', "&3Movement Events"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Jumping, walking,"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7and flying"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 16)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event_categories.movement_events", movementEvents);
        ItemStack itemEvents = new ItemBuilder(Material.ITEM_FRAME)
                .name(ChatColor.translateAlternateColorCodes('&', "&6Item Events"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Dropping, picking up"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7and eating items"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 28)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event_categories.item_events", itemEvents);
        ItemStack damageEvents = new ItemBuilder(Material.IRON_SWORD)
                .name(ChatColor.translateAlternateColorCodes('&', "&cDamage Events"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Getting damaged,"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7shooting a bow"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 31)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event_categories.damage_events", damageEvents);
        ItemStack deathEvents = new ItemBuilder(Material.SKELETON_SKULL)
                .name(ChatColor.translateAlternateColorCodes('&', "&4Death Events"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Dying, and respawning"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 34)
                .hideFlags()
                .make();
        ItemManager.addItem("player_event_categories.death_events", deathEvents);

    }

    public static void PlayerEventPlotAndServerEvents() {
        ItemStack playerJoinGameEvent = new ItemBuilder(Material.POTATO)
                .name(ChatColor.translateAlternateColorCodes('&', "&aPlayer Join Game Event"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Executes code when a"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7player joins the plot."))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 0)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Join")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.player_join_game_event", playerJoinGameEvent);

        ItemStack playerRejoin = new ItemBuilder(Material.PLAYER_HEAD)
                .name(ChatColor.translateAlternateColorCodes('&', "&ePlayer Rejoin"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Executes code when the player"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7re-enters the world with the"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7/play command."))
                .lore("")
                .lore(ChatColor.translateAlternateColorCodes('&', "&4ø &cCancellable"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 1)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Rejoin")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.player_rejoin", playerRejoin);

        ItemStack Player_Leave_Game_Event = new ItemBuilder(Material.POISONOUS_POTATO)
                .name(ChatColor.GREEN + "Player Leave Game Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player leaves the plot.")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 2)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Leave")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.player_leave_game_event", Player_Leave_Game_Event);

        ItemStack Player_Command_Event = new ItemBuilder(Material.COMMAND_BLOCK)
                .name(ChatColor.GREEN + "Player Command Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "types a command on the plot.")
                .lore("")
                .lore(ChatColor.WHITE + "Example:")
                .lore(ChatColor.AQUA + "\"@command\"")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 3)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Command")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.player_command_event", Player_Command_Event);

        ItemStack Resource_Pack_Load = new ItemBuilder(Material.GREEN_SHULKER_BOX)
                .name(ChatColor.DARK_AQUA + "Resource Pack Load")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player finishes loading a")
                .lore(ChatColor.GRAY + "plot resource pack.")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 4)
                .setCustomTag(new NamespacedKey(plugin, "short"), "PackLoad")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.resource_pack_load", Resource_Pack_Load);

        ItemStack Resource_Pack_Decline = new ItemBuilder(Material.RED_SHULKER_BOX)
                .name(ChatColor.DARK_AQUA + "Resource Pack Decline")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player declines a plot")
                .lore(ChatColor.GRAY + "resource pack prompt.")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 5)
                .setCustomTag(new NamespacedKey(plugin, "short"), "PackDecline")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.resource_pack_decline", Resource_Pack_Decline);
        ItemStack Player_Chat_Event = new ItemBuilder(Material.WRITABLE_BOOK)
                .name(ChatColor.BLUE + "Player Chat Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player sends a message")
                .lore(ChatColor.GRAY + "in chat")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 6)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Chat")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.plot_and_server_events.player_chat_event", Player_Chat_Event);
    }

    public static void PlayerEventClickEvents() {
        ItemStack playerRightClickEvent = new ItemBuilder(Material.DIAMOND_PICKAXE)
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
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 0)
                .setCustomTag(new NamespacedKey(plugin, "short"), "RightClick")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.player_right_click_event", playerRightClickEvent);

        ItemStack playerLeftClickEvent = new ItemBuilder(Material.IRON_PICKAXE)
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
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 1)
                .setCustomTag(new NamespacedKey(plugin, "short"), "LeftClick")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.player_left_click_event", playerLeftClickEvent);

        ItemStack playerRightClickEntityEvent = new ItemBuilder(Material.ARMOR_STAND)
                .name(ChatColor.AQUA + "Player Right Click Entity Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "right clicks an entity.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 2)
                .setCustomTag(new NamespacedKey(plugin, "short"), "ClickEntity")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.player_right_click_entity_event", playerRightClickEntityEvent);

        ItemStack playerRightClickPlayerEvent = new ItemBuilder(Material.PLAYER_HEAD)
                .name(ChatColor.AQUA + "Player Right Click Player Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "clicks another player.")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 3)
                .setCustomTag(new NamespacedKey(plugin, "short"), "ClickPlayer")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.player_right_click_player_event", playerRightClickPlayerEvent);

        ItemStack playerPlaceBlockEvent = new ItemBuilder(Material.POLISHED_ANDESITE)
                .name(ChatColor.GOLD + "Player Place Block Event")
                .lore(ChatColor.GRAY + "Executes code when a player places a block.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 4)
                .setCustomTag(new NamespacedKey(plugin, "short"), "PlaceBlock")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.player_place_block_event", playerPlaceBlockEvent);

        ItemStack playerBreakBlockEvent = new ItemBuilder(Material.COBBLESTONE)
                .name(ChatColor.GOLD + "Player Break Block Event")
                .lore(ChatColor.GRAY + "Executes code when a player breaks a block.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 5)
                .setCustomTag(new NamespacedKey(plugin, "short"), "BreakBlock")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.player_break_block_event", playerBreakBlockEvent);

        ItemStack playerSwapHandsEvent = new ItemBuilder(Material.NETHERITE_SCRAP)
                .name(ChatColor.BLUE + "Player Swap Hands Event")
                .lore(ChatColor.GRAY + "Executes code when a player swaps an item or items between their main hand and off hand.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 6)
                .setCustomTag(new NamespacedKey(plugin, "short"), "SwapHands")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.player_swap_hands_event", playerSwapHandsEvent);

        ItemStack playerChangeSlotEvent = new ItemBuilder(Material.SLIME_BALL)
                .name(ChatColor.BLUE + "Player Change Slot Event")
                .lore(ChatColor.GRAY + "Executes code when a player changes their hotbar slot.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 7)
                .setCustomTag(new NamespacedKey(plugin, "short"), "ChangeSlot")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.player_change_slot_event", playerChangeSlotEvent);

        ItemStack playerTameMobEvent = new ItemBuilder(Material.BONE)
                .name(ChatColor.YELLOW + "Player Tame Mob Event")
                .lore(ChatColor.GRAY + "Executes code when a player tames a mob.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 8)
                .setCustomTag(new NamespacedKey(plugin, "short"), "TameMob")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.click_events.player_tame_mob_event", playerTameMobEvent);
    }


    public static void PlayerEventMovementEvents() {
        ItemStack playerWalkEvent = new ItemBuilder(Material.GRASS_BLOCK)
                .name(Utilities.convertToChatColor("#2AFFAAPlayer Walk Event"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Executes code while"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7a player is walking."))
                .lore("")
                .lore(ChatColor.translateAlternateColorCodes('&', "&4∅ &cCancellable"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 0)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Walk")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.player_walk_event", playerWalkEvent);

        ItemStack playerJumpEvent = new ItemBuilder(Material.RABBIT_FOOT)
                .name(Utilities.convertToChatColor("#2AFFAAPlayer Jump Event"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Executes code when a"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7player jumps."))
                .lore("")
                .lore(ChatColor.translateAlternateColorCodes('&', "&4∅ &cCancellable"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 1)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Jump")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.player_jump_event", playerJumpEvent);

        ItemStack playerSneakEvent = new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
                .name(ChatColor.BLUE + "Player Sneak Event")
                .lore(ChatColor.GRAY + "Executes code when a player sneaks.")
                .lore("")
                .lore(ChatColor.BLUE + "Additional Info:")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Cancelling this event will")
                .lore(ChatColor.GRAY + "not update what the sneaking player sees!")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 2)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Sneak")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.player_sneak_event", playerSneakEvent);

        ItemStack playerUnsneakEvent = new ItemBuilder(Material.IRON_LEGGINGS)
                .name(ChatColor.BLUE + "Player Unsneak Event")
                .lore(ChatColor.GRAY + "Executes code when a player stops sneaking.")
                .lore("")
                .lore(ChatColor.BLUE + "Additional Info:")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Cancelling this event will")
                .lore(ChatColor.GRAY + "not update what the sneaking player sees!")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 3)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Unsneak")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.player_unsneak_event", playerUnsneakEvent);

        ItemStack playerStartSprintEvent = new ItemBuilder(Material.GOLDEN_BOOTS)
                .name(Utilities.convertToChatColor("#2AFFAAPlayer Start Sprint Event"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Executes code when a"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7player starts sprinting."))
                .lore("")
                .lore(ChatColor.translateAlternateColorCodes('&', "&9Additional Info:"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&b» &7Cancelling this event will"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7only result in the sprint"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7particles being hidden."))
                .lore(ChatColor.translateAlternateColorCodes('&', "&b» &7Client side particles are"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7not hidden!"))
                .lore("")
                .lore(ChatColor.translateAlternateColorCodes('&', "&4∅ &cCancellable"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 4)
                .setCustomTag(new NamespacedKey(plugin, "short"), "StartSprint")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.player_start_sprint_event", playerStartSprintEvent);

        ItemStack playerStopSprintingEvent = new ItemBuilder(Material.IRON_BOOTS)
                .name(Utilities.convertToChatColor("#2AFFAAPlayer Stop Sprinting Event"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Executes code when a"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7player stops sprinting."))
                .lore("")
                .lore(ChatColor.translateAlternateColorCodes('&', "&9Additional Info:"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&b» &7Cancelling this event will"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7only result in the sprint"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7particles being shown."))
                .lore(ChatColor.translateAlternateColorCodes('&', "&b» &7Client side particles are"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7not shown!"))
                .lore("")
                .lore(ChatColor.translateAlternateColorCodes('&', "&4∅ &cCancellable"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 5)
                .setCustomTag(new NamespacedKey(plugin, "short"), "StopSprint")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.player_stop_sprinting_event", playerStopSprintingEvent);

        ItemStack playerStartFlightEvent = new ItemBuilder(Material.ELYTRA)
                .name(Utilities.convertToChatColor("#2AFFAAPlayer Start Flight Event"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Executes code when a"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7player starts flying."))
                .lore("")
                .lore(ChatColor.translateAlternateColorCodes('&', "&4∅ &cCancellable"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 6)
                .setCustomTag(new NamespacedKey(plugin, "short"), "StartFly")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.player_start_flight_event", playerStartFlightEvent);

        ItemStack playerStopFlightEvent = new ItemBuilder(Material.ELYTRA)
                .name(Utilities.convertToChatColor("#2AFFAAPlayer Stop Flight Event"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Executes code when a"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7player stops flying."))
                .lore("")
                .lore(ChatColor.translateAlternateColorCodes('&', "&4∅ &cCancellable"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 7)
                .setCustomTag(new NamespacedKey(plugin, "short"), "StopFly")
                .hideFlags()
                .setUnbreakable(true)
                .damage(431)
                .make();
        ItemManager.addItem("player_event.movement_events.player_stop_flight_event", playerStopFlightEvent);

        ItemStack playerRiptideEvent = new ItemBuilder(Material.TRIDENT)
                .name(Utilities.convertToChatColor("#2AFFAAPlayer Riptide Event"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Executes code when a player"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7throws a riptide trident."))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 8)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Riptide")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.player_riptide_event", playerRiptideEvent);

        ItemStack playerDismountEvent = new ItemBuilder(Material.DARK_OAK_BOAT)
                .name(ChatColor.DARK_AQUA + "Player Dismount Event")
                .lore(ChatColor.GRAY + "Executes code when a player dismounts a vehicle or other entity.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 9)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Dismount")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.player_dismount_event", playerDismountEvent);

        ItemStack playerHorseJumpEvent = new ItemBuilder(Material.GOLDEN_HORSE_ARMOR)
                .name(Utilities.convertToChatColor("#2AFFAAPlayer Horse Jump Event"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Executes code when a player"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7causes a horse to jump."))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 10)
                .setCustomTag(new NamespacedKey(plugin, "short"), "HorseJump")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.player_horse_jump_event", playerHorseJumpEvent);

        ItemStack playerVehicleJumpEvent = new ItemBuilder(Material.FURNACE_MINECART)
                .name(Utilities.convertToChatColor("#2AFFAAPlayer Vehicle Jump Event"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Executes code when a"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7player presses the jump key"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7while riding a vehicle"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7or other entity."))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 11)
                .setCustomTag(new NamespacedKey(plugin, "short"), "VehicleJump")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.movement_events.player_vehicle_jump_event", playerVehicleJumpEvent);
    }


    public static void PlayerEventItemEvents() {
        ItemStack playerClickMenuSlotEvent = new ItemBuilder(Material.CRAFTING_TABLE)
                .name(ChatColor.AQUA + "Player Click Menu Slot Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "clicks a slot in an inventory")
                .lore(ChatColor.GRAY + "menu.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancelled automatically")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 0)
                .setCustomTag(new NamespacedKey(plugin, "short"), "ClickMenuSlot")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.player_click_menu_slot_event", playerClickMenuSlotEvent);

        ItemStack playerClickInventorySlotEvent = new ItemBuilder(Material.LIGHT_GRAY_SHULKER_BOX)
                .name(ChatColor.AQUA + "Player Click Inventory Slot Event")
                .lore(ChatColor.GRAY + "Executes code when a player clicks")
                .lore(ChatColor.GRAY + "a slot inside their inventory.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 1)
                .setCustomTag(new NamespacedKey(plugin, "short"), "ClickInvSlot")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.player_click_inventory_slot_event", playerClickInventorySlotEvent);

        ItemStack playerPickUpItemEvent = new ItemBuilder(Material.GLOWSTONE_DUST)
                .name(ChatColor.YELLOW + "Player Pick Up Item Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player picks up an item.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 2)
                .setCustomTag(new NamespacedKey(plugin, "short"), "PickUpItem")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.player_pick_up_item_event", playerPickUpItemEvent);

        ItemStack playerDropItemEvent = new ItemBuilder(Material.SUGAR)
                .name(ChatColor.YELLOW + "Player Drop Item Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player drops an item.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 3)
                .setCustomTag(new NamespacedKey(plugin, "short"), "DropItem")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.player_drop_item_event", playerDropItemEvent);

        ItemStack playerConsumeItemEvent = new ItemBuilder(Material.COOKED_CHICKEN)
                .name(ChatColor.YELLOW + "Player Consume Item Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "eats or drinks an item.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 4)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Consume")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.player_consume_item_event", playerConsumeItemEvent);

        ItemStack playerBreakItemEvent = new ItemBuilder(Material.STONE_PICKAXE)
                .name(ChatColor.YELLOW + "Player Break Item Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player breaks an item.")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 5)
                .setCustomTag(new NamespacedKey(plugin, "short"), "BreakItem")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.player_break_item_event", playerBreakItemEvent);

        ItemStack playerCloseInventoryEvent = new ItemBuilder(Material.STRUCTURE_VOID)
                .name(ChatColor.GOLD + "Player Close Inventory Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player closes an inventory.")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 6)
                .setCustomTag(new NamespacedKey(plugin, "short"), "CloseInv")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.player_close_inventory_event", playerCloseInventoryEvent);

        ItemStack playerFishEvent = new ItemBuilder(Material.FISHING_ROD)
                .name(ChatColor.GOLD + "Player Fish Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "fishes an entity,")
                .lore(ChatColor.GRAY + "player, or nothing.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 7)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Fish")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.item_events.player_fish_event", playerFishEvent);
    }



    public static void PlayerEventDamageEvents() {
        ItemStack playerTakeDamageEvent = new ItemBuilder(Material.DEAD_BUSH)
                .name(ChatColor.RED + "Player Take Damage Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player takes damage.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 0)
                .setCustomTag(new NamespacedKey(plugin, "short"), "PlayerTakeDmg")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.player_take_damage_event", playerTakeDamageEvent);

        ItemStack playerDamagePlayerEvent = new ItemBuilder(Material.IRON_SWORD)
                .name(ChatColor.RED + "Player Damage Player Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "damages another player.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 1)
                .setCustomTag(new NamespacedKey(plugin, "short"), "PlayerDmgPlayer")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.player_damage_player_event", playerDamagePlayerEvent);

        ItemStack playerDamageEntityEvent = new ItemBuilder(Material.STONE_SWORD)
                .name(ChatColor.RED + "Player Damage Entity Event")
                .lore(ChatColor.GRAY + "Executes code when a")
                .lore(ChatColor.GRAY + "player damages an entity.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 2)
                .setCustomTag(new NamespacedKey(plugin, "short"), "DamageEntity")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.player_damage_entity_event", playerDamageEntityEvent);

        ItemStack entityDamagePlayerEvent = new ItemBuilder(Material.WOODEN_SWORD)
                .name(ChatColor.RED + "Entity Damage Player Event")
                .lore(ChatColor.GRAY + "Executes code when an")
                .lore(ChatColor.GRAY + "entity damages a player.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 3)
                .setCustomTag(new NamespacedKey(plugin, "short"), "EntityDmgPlayer")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.entity_damage_player_event", entityDamagePlayerEvent);

        ItemStack playerHealEvent = new ItemBuilder(Material.SPLASH_POTION)
                .name(Utilities.convertToChatColor("#2AFFAAPlayer Heal Event"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7Executes code when a player"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7regains health from any"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&7source."))
                .lore("")
                .lore(ChatColor.translateAlternateColorCodes('&', "&4∅ &cCancellable"))
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 4)
                .setCustomTag(new NamespacedKey(plugin, "short"), "PlayerHeal")
                .hideFlags()
                .setPotionColor(Color.fromRGB(255, 0, 0))
                .make();
        ItemManager.addItem("player_event.damage_events.player_heal_event", playerHealEvent);

        ItemStack playerShootBowEvent = new ItemBuilder(Material.BOW)
                .name(ChatColor.BLUE + "Player Shoot Bow Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "fires an arrow with a bow.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 5)
                .setCustomTag(new NamespacedKey(plugin, "short"), "ShootBow")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.player_shoot_bow_event", playerShootBowEvent);

        ItemStack playerShootProjectileEvent = new ItemBuilder(Material.SNOWBALL)
                .name(ChatColor.BLUE + "Player Shoot Projectile Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "throws a projectile such")
                .lore(ChatColor.GRAY + "as snowballs or eggs.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 6)
                .setCustomTag(new NamespacedKey(plugin, "short"), "ShootProjectile")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.player_shoot_projectile_event", playerShootProjectileEvent);

        ItemStack playerProjectileHitEvent = new ItemBuilder(Material.SPECTRAL_ARROW)
                .name(ChatColor.DARK_AQUA + "Player Projectile Hit Event")
                .lore(ChatColor.GRAY + "Executes code when a projectile")
                .lore(ChatColor.GRAY + "launched by a player hits a block,")
                .lore(ChatColor.GRAY + "an entity, or another player.")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 7)
                .setCustomTag(new NamespacedKey(plugin, "short"), "ProjHit")
                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.player_projectile_hit_event", playerProjectileHitEvent);

        ItemStack playerProjectileDamagePlayerEvent = new ItemBuilder(Material.ARROW)
                .name(ChatColor.RED + "Projectile Damage Player Event")
                .lore(ChatColor.GRAY + "Executes code when a projectile")
                .lore(ChatColor.GRAY + "damages a player.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 8)
                .setCustomTag(new NamespacedKey(plugin, "short"), "ProjDmgPlayer")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.damage_events.player_projectile_damage_player_event", playerProjectileDamagePlayerEvent);

        ItemStack potionCloudImbuePlayerEvent = new ItemBuilder(Material.ARROW)
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
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 9)
                .setCustomTag(new NamespacedKey(plugin, "short"), "CloudImbuePlayer")
                .hideFlags()
                .setPotionColor(Color.fromRGB(255, 255, 0))
                .make();
        ItemManager.addItem("player_event.damage_events.potion_cloud_imbue_player_event", potionCloudImbuePlayerEvent);
    }


    public static void PlayerEventDeathEvents() {
        ItemStack playerDeathEvent = new ItemBuilder(Material.REDSTONE)
                .name(ChatColor.DARK_RED + "Player Death Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "dies, not as a result of another")
                .lore(ChatColor.GRAY + "player or entity.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 0)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Death")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.death_events.player_death_event", playerDeathEvent);

        ItemStack playerKillPlayerEvent = new ItemBuilder(Material.SKELETON_SKULL)
                .name(ChatColor.DARK_RED + "Player Kill Player Event")
                .lore(ChatColor.GRAY + "Executes code when a player")
                .lore(ChatColor.GRAY + "kills another player.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 1)
                .setCustomTag(new NamespacedKey(plugin, "short"), "KillPlayer")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.death_events.player_kill_player_event", playerKillPlayerEvent);

        ItemStack playerResurrectEvent = new ItemBuilder(Material.TOTEM_OF_UNDYING)
                .name(ChatColor.DARK_AQUA + "Player Resurrect Event")
                .lore(ChatColor.GRAY + "Executes code when")
                .lore(ChatColor.GRAY + "a player resurrects with")
                .lore(ChatColor.GRAY + "a totem of undying.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 2)
                .setCustomTag(new NamespacedKey(plugin, "short"), "PlayerResurrect")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.death_events.player_resurrect_event", playerResurrectEvent);

        ItemStack playerKillMobEvent = new ItemBuilder(Material.SKELETON_SPAWN_EGG)
                .name(ChatColor.DARK_RED + "Player Kill Mob Event")
                .lore(ChatColor.GRAY + "Executes code when")
                .lore(ChatColor.GRAY + "a player kills a mob.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 3)
                .setCustomTag(new NamespacedKey(plugin, "short"), "KillMob")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.death_events.player_kill_mob_event", playerKillMobEvent);

        ItemStack mobKillPlayerEvent = new ItemBuilder(Material.BONE)
                .name(ChatColor.DARK_RED + "Mob Kill Player Event")
                .lore(ChatColor.GRAY + "Executes code when")
                .lore(ChatColor.GRAY + "a mob kills a player.")
                .lore("")
                .lore(ChatColor.DARK_RED + "∅ " + ChatColor.RED + "Cancellable")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 4)
                .setCustomTag(new NamespacedKey(plugin, "short"), "MobKillPlayer")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.death_events.mob_kill_player_event", mobKillPlayerEvent);

        ItemStack playerRespawnEvent = new ItemBuilder(Material.OAK_SAPLING)
                .name(ChatColor.DARK_AQUA + "Player Respawn Event")
                .lore(ChatColor.GRAY + "Executes code when")
                .lore(ChatColor.GRAY + "a player respawns.")
                .setCustomIntTag(new NamespacedKey(plugin, "slot"), 5)
                .setCustomTag(new NamespacedKey(plugin, "short"), "Respawn")

                .hideFlags()
                .make();
        ItemManager.addItem("player_event.death_events.player_respawn_event", playerRespawnEvent);
    }
}
