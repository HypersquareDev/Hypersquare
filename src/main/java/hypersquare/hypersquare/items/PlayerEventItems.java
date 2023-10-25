package hypersquare.hypersquare.items;

import hypersquare.hypersquare.utils.ItemBuilder;
import hypersquare.hypersquare.utils.Utilities;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static hypersquare.hypersquare.Hypersquare.mm;

public enum PlayerEventItems {
    PLOT_AND_SERVER_EVENTS_CATEGORY(Material.COMMAND_BLOCK, "<gold>Plot and Server Events","%n<gray>Joining and leaving plots,%n<gray>plot commands", 10),
    CLICK_EVENTS_CATEGORY(Material.DIAMOND_PICKAXE, "<blue>Click Events","%n<gray>Right and left clicking,%n<gray>breaking blocks", 13),
    MOVEMENT_EVENTS_CATEGORY(Material.LEATHER_BOOTS, "<dark_aqua>Movement Events","%n<gray>Jumping, walking,%n<gray>and flying", 16),
    ITEM_EVENTS_CATEGORY(Material.ITEM_FRAME, "<yellow>Item Events","%n<gray>Dropping, picking up%n<gray>and eating items", 28),
    DAMAGE_EVENTS_CATEGORY(Material.IRON_SWORD, "<red>Damage Events","%n<gray>Getting damaged,%n<gray>shooting a bow", 31),
    DEATH_EVENTS_CATEGORY(Material.SKELETON_SKULL, "<dark_red>Death Events","%n<gray>Dying, and respawning", 34),

    PLAYER_JOIN_GAME_EVENT(Material.POTATO, "<green>Player Join Game Event","%n<gray>Executes code when a%n<gray>player joins the plot.", PLOT_AND_SERVER_EVENTS_CATEGORY),
    PLAYER_REJOIN_EVENT(Material.PLAYER_HEAD, "<yellow>Player Rejoin","%n<gray>Executes code when the player%n<gray>re-enters the world with the%n<gray>/play command.", PLOT_AND_SERVER_EVENTS_CATEGORY),
    PLAYER_LEAVE_GAME_EVENT(Material.POISONOUS_POTATO, "<green>Player Leave Game Event","%n<gray>Executes code when a%n<gray>player leaves the plot.", PLOT_AND_SERVER_EVENTS_CATEGORY),
    PLAYER_COMMAND_EVENT(Material.COMMAND_BLOCK, "<green>Player Command Event","%n<gray>Executes code when a player%n<gray>types a command on the plot.", PLOT_AND_SERVER_EVENTS_CATEGORY),
    RESOURCE_PACK_LOAD_EVENT(Material.GREEN_SHULKER_BOX, "<dark_aqua>Resource Pack Load","%n<gray>Executes code when a%n<gray>player finishes loading a%n<gray>plot resource pack.", PLOT_AND_SERVER_EVENTS_CATEGORY),
    RESOURCE_PACK_DECLINE_EVENT(Material.RED_SHULKER_BOX, "<dark_aqua>Resource Pack Decline","%n<gray>Executes code when a%n<gray>player declines a plot%n<gray>resource pack prompt.", PLOT_AND_SERVER_EVENTS_CATEGORY),
    PLAYER_CHAT_EVENT(Material.WRITABLE_BOOK, "<blue>Player Chat Event","%n<gray>Executes code when a%n<gray>player sends a message%n<gray>in chat", PLOT_AND_SERVER_EVENTS_CATEGORY),

    PLAYER_RIGHT_CLICK_EVENT(Material.DIAMOND_PICKAXE, "<aqua>Player Right Click Event","%n<gray>Executes code when a player%n<gray>right clicks while looking at a%n<gray>block or holding an item.", CLICK_EVENTS_CATEGORY),
    PLAYER_LEFT_CLICK_EVENT(Material.IRON_PICKAXE, "<aqua>Player Left Click Event","%n<gray>Executes code when a player%n<gray>left clicks.", CLICK_EVENTS_CATEGORY),
    PLAYER_RIGHT_CLICK_ENTITY_EVENT(Material.ARMOR_STAND, "<aqua>Player Right Click Entity Event","%n<gray>Executes code when a player%n<gray>right clicks an entity.", CLICK_EVENTS_CATEGORY),
    PLAYER_RIGHT_CLICK_PLAYER_EVENT(Material.PLAYER_HEAD, "<aqua>Player Right Click Player Event","%n<gray>Executes code when a player%n<gray>clicks another player.", CLICK_EVENTS_CATEGORY),
    PLAYER_PLACE_BLOCK_EVENT(Material.POLISHED_ANDESITE, "<gold>Player Place Block Event","%n<gray>Executes code when a player%n<gray>places a block.", CLICK_EVENTS_CATEGORY),
    PLAYER_BREAK_BLOCK_EVENT(Material.COBBLESTONE, "<gold>Player Break Block Event","%n<gray>Executes code when a player%n<gray>breaks a block.", CLICK_EVENTS_CATEGORY),
    PLAYER_SWAP_HANDS_EVENT(Material.NETHERITE_SCRAP, "<blue>Player Swap Hands Event","%n<gray>Executes code when a player%n<gray>swaps an item or items between%n<gray>their main hand and off hand.", CLICK_EVENTS_CATEGORY),
    PLAYER_CHANGE_SLOT_EVENT(Material.SLIME_BALL, "<blue>Player Change Slot Event","%n<gray>Executes code when a player%n<gray>changes their hotbar slot.", CLICK_EVENTS_CATEGORY),
    PLAYER_TAME_MOB_EVENT(Material.BONE, "<yellow>Player Tame Mob Event","%n<gray>Executes code when a player%n<gray>tames a mob.", CLICK_EVENTS_CATEGORY),

    PLAYER_WALK_EVENT(Material.GRASS_BLOCK, "<#2affaa>Player Walk Event","%n<gray>Executes code while%n<gray>a player is walking.", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_JUMP_EVENT(Material.RABBIT_FOOT, "<#2affaa>Player Jump Event","%n<gray>Executes code when a%n<gray>player jumps.", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_SNEAK_EVENT(Material.CHAINMAIL_LEGGINGS, "<blue>Player Sneak Event","%n<gray>Executes code when a player%n<gray>sneaks.", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_UNSNEAK_EVENT(Material.IRON_LEGGINGS, "<blue>Player Unsneak Event","%n<gray>Executes code when a player%n<gray>stops sneaking.", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_START_SPRINT_EVENT(Material.GOLDEN_BOOTS, "<#2affaa>Player Start Sprint Event","%n<gray>Executes code when a%n<gray>player starts sprinting.", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_STOP_SPRINTING_EVENT(Material.IRON_BOOTS, "<#2affaa>Player Stop Sprinting Event","%n<gray>Executes code when a%n<gray>player stops sprinting.", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_START_FLIGHT_EVENT(Material.ELYTRA, "<#2affaa>Player Start Flight Event","%n<gray>Executes code when a%n<gray>player starts flying.", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_STOP_FLIGHT_EVENT(Material.ELYTRA, "<#2affaa>Player Stop Flight Event","%n<gray>Executes code when a%n<gray>player stops flying.", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_RIPTIDE_EVENT(Material.TRIDENT, "<#2affaa>Player Riptide Event","%n<gray>Executes code when a%n<gray>player throws a riptide trident.", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_DISMOUNT_EVENT(Material.DARK_OAK_BOAT, "<#2affaa>Player Dismount Event","%n<gray>Executes code when a%n<gray>player dismounts a vehicle or%n<gray>other entity.", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_HORSE_JUMP_EVENT(Material.GOLDEN_HORSE_ARMOR, "<#2affaa>Player Horse Jump Event","%n<gray>Executes code when a%n<gray>player causes a horse to jump.", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_VEHICLE_JUMP_EVENT(Material.FURNACE_MINECART, "<#2affaa>Player Vehicle Jump Event","%n<gray>Executes code when a%n<gray>player presses the jump key%n<gray>while riding a vehicle%n<gray>or other entity.", MOVEMENT_EVENTS_CATEGORY),

    PLAYER_CLICK_MENU_SLOT_EVENT(Material.CRAFTING_TABLE, "<#2affaa>Player Click Menu Slot Event","%n<gray>Executes code when a player%n<gray>clicks a slot in an inventory%n<gray>menu.", ITEM_EVENTS_CATEGORY),
    PLAYER_CLICK_INVENTORY_SLOT_EVENT(Material.LIGHT_GRAY_SHULKER_BOX, "<#2affaa>Player Click Inventory Slot Event","%n<gray>Executes code when a player%n<gray>clicks a slot inside their%n<gray>inventory.", ITEM_EVENTS_CATEGORY),
    PLAYER_PICK_UP_ITEM_EVENT(Material.GLOWSTONE_DUST, "<#2affaa>Player Pick Up Item Event","%n<gray>Executes code when a%n<gray>player picks up an item.", ITEM_EVENTS_CATEGORY),
    PLAYER_DROP_ITEM_EVENT(Material.SUGAR, "<#2affaa>Player Drop Item Event","%n<gray>Executes code when a%n<gray>player drops an item.", ITEM_EVENTS_CATEGORY),
    PLAYER_CONSUME_ITEM_EVENT(Material.COOKED_CHICKEN, "<#2affaa>Player Consume Item Event","%n<gray>Executes code when a player%n<gray>eats or drinks an item.", ITEM_EVENTS_CATEGORY),
    PLAYER_BREAK_ITEM_EVENT(Material.STONE_PICKAXE, "<#2affaa>Player Break Item Event","%n<gray>Executes code when a%n<gray>player breaks an item.", ITEM_EVENTS_CATEGORY),
    PLAYER_CLOSE_INVENTORY_EVENT(Material.STRUCTURE_VOID, "<#2affaa>Player Close Inventory Event","%n<gray>Executes code when a%n<gray>player closes an inventory.", ITEM_EVENTS_CATEGORY),
    PLAYER_FISH_EVENT(Material.FISHING_ROD, "<#2affaa>Player Fish Event","%n<gray>Executes code when a player%n<gray>fishes an entity, player, or%n<gray>nothing.", ITEM_EVENTS_CATEGORY),

    PLAYER_TAKE_DAMAGE_EVENT(Material.DEAD_BUSH, "<red>Player Take Damage Event","%n<gray>Executes code when a%n<gray>player takes damage.", DAMAGE_EVENTS_CATEGORY),
    PLAYER_DAMAGE_PLAYER_EVENT(Material.IRON_SWORD, "<red>Player Damage Player Event","%n<gray>Executes code when a%n<gray>player damages another player.", DAMAGE_EVENTS_CATEGORY),
    PLAYER_DAMAGE_ENTITY_EVENT(Material.STONE_SWORD, "<red>Player Damage Entity Event","%n<gray>Executes code when a%n<gray>player damages an entity.", DAMAGE_EVENTS_CATEGORY),
    ENTITY_DAMAGE_PLAYER_EVENT(Material.WOODEN_SWORD, "<red>Entity Damage Player Event","%n<gray>Executes code when an%n<gray>entity damages a player.", DAMAGE_EVENTS_CATEGORY),
    PLAYER_HEAL_EVENT(Material.SPLASH_POTION, "<#2affaa>Player Heal Event","%n<gray>Executes code when a player%n<gray>regains health from any%n<gray>source.", DAMAGE_EVENTS_CATEGORY),
    PLAYER_SHOOT_BOW_EVENT(Material.BOW, "<red>Player Shoot Bow Event","%n<gray>Executes code when a player%n<gray>fires an arrow with a bow.", DAMAGE_EVENTS_CATEGORY),
    PLAYER_SHOOT_PROJECTILE_EVENT(Material.SNOWBALL, "<red>Player Shoot Projectile Event","%n<gray>Executes code when a player%n<gray>throws a projectile such%n<gray>as snowballs or eggs.", DAMAGE_EVENTS_CATEGORY),
    PLAYER_PROJECTILE_HIT_EVENT(Material.SPECTRAL_ARROW, "<dark_aqua>Player Projectile Hit Event","%n<gray>Executes code when a projectile%n<gray>launched by a player hits a block,%n<gray>an entity, or another player.", DAMAGE_EVENTS_CATEGORY),
    PLAYER_PROJECTILE_DAMAGE_PLAYER_EVENT(Material.ARROW, "<red>Projectile Damage Player Event","%n<gray>Executes code when a projectile%n<gray>damages a player.", DAMAGE_EVENTS_CATEGORY),
    POTION_CLOUD_IMBUE_PLAYER_EVENT(Material.ARROW, "<dark_aqua>Potion Cloud Imbue Player Event","%n<gray>Executes code when an area of%n<gray>effect cloud applies its potion%n<gray>effect(s) to a player.", DAMAGE_EVENTS_CATEGORY),

    PLAYER_DEATH_EVENT(Material.TOTEM_OF_UNDYING, "<dark_red>Player Death Event","%n<gray>Executes code when a%n<gray>player dies.", DEATH_EVENTS_CATEGORY),
    PLAYER_KILL_PLAYER_EVENT(Material.SKELETON_SKULL, "<dark_red>Player Kill Player Event","%n<gray>Executes code when a%n<gray>player kills another player.", DEATH_EVENTS_CATEGORY),
    PLAYER_KILL_MOB_EVENT(Material.SKELETON_SPAWN_EGG, "<dark_red>Player Kill Mob Event","%n<gray>Executes code when a%n<gray>player kills a mob.", DEATH_EVENTS_CATEGORY),
    MOB_KILL_PLAYER_EVENT(Material.BONE, "<dark_red>Mob Kill Player Event","%n<gray>Executes code when a%n<gray>mob kills a player.", DEATH_EVENTS_CATEGORY),
    PLAYER_RESURRECT_EVENT(Material.TOTEM_OF_UNDYING, "<dark_aqua>Player Resurrect Event","%n<gray>Executes code when a%n<gray>player resurrects with a%n<gray>totem of undying.", DEATH_EVENTS_CATEGORY),
    PLAYER_RESPAWN_EVENT(Material.OAK_SAPLING, "<dark_aqua>Player Respawn Event","%n<gray>Executes code when a%n<gray>player respawns.", DEATH_EVENTS_CATEGORY),

    PLAYER_EVENT_DEATH_EVENTS(Material.REDSTONE, "<dark_red>Player Death Event","%n<gray>Executes code when a player%n<gray>dies, not as a result of another%n<gray>player or entity.", DEATH_EVENTS_CATEGORY),
    PLAYER_EVENT_KILL_PLAYER_EVENT(Material.SKELETON_SKULL, "<dark_red>Player Kill Player Event","%n<gray>Executes code when a player%n<gray>kills another player.", DEATH_EVENTS_CATEGORY),
    PLAYER_EVENT_RESURRECT_EVENT(Material.TOTEM_OF_UNDYING, "<dark_aqua>Player Resurrect Event","%n<gray>Executes code when a%n<gray>player resurrects with a%n<gray>totem of undying.", DEATH_EVENTS_CATEGORY),
    PLAYER_EVENT_KILL_MOB_EVENT(Material.SKELETON_SPAWN_EGG, "<dark_red>Player Kill Mob Event","%n<gray>Executes code when a%n<gray>player kills a mob.", DEATH_EVENTS_CATEGORY),
    MOB_EVENT_KILL_PLAYER_EVENT(Material.BONE, "<dark_red>Mob Kill Player Event","%n<gray>Executes code when a%n<gray>mob kills a player.", DEATH_EVENTS_CATEGORY),
    PLAYER_EVENT_RESPAWN_EVENT(Material.OAK_SAPLING, "<dark_aqua>Player Respawn Event","%n<gray>Executes code when a%n<gray>player respawns.", DEATH_EVENTS_CATEGORY);

    @Getter final Material material;
    @Getter final String name;
    @Getter final String lore;
    @Getter PlayerEventItems category = null;
    @Getter int slot = 0;
    PlayerEventItems(Material material, String name, String lore, int slot) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.slot = slot;
    }

    PlayerEventItems(Material material, String name, String lore, PlayerEventItems category) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.category = category;
    }

    public ItemStack build() {
        return Utilities.formatItem(lore, material, name);
    }

    public static ArrayList<PlayerEventItems> getEvents(PlayerEventItems category) {
        ArrayList<PlayerEventItems> actions = new ArrayList<>();
        for (PlayerEventItems playerEventItem : PlayerEventItems.values()) {
            if (Objects.equals(playerEventItem.getCategory(), category)) {
                actions.add(playerEventItem);
            }
        }
        return actions;
    }

    public @NotNull Component getName() {
        return mm.deserialize(name);
    }

    public Component getLore() {
        return mm.deserialize(lore.replace("%n", "\n"));
    }
}
