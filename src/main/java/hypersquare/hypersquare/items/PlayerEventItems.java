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
    PLOT_AND_SERVER_EVENTS_CATEGORY(Material.COMMAND_BLOCK, "<gold>Plot and Server Events","<gray>Joining and leaving plots,%n<gray>plot commands", 10),
    CLICK_EVENTS_CATEGORY(Material.DIAMOND_PICKAXE, "<blue>Click Events","<gray>Right and left clicking,%n<gray>breaking blocks", 13),
    MOVEMENT_EVENTS_CATEGORY(Material.LEATHER_BOOTS, "<dark_aqua>Movement Events","<gray>Jumping, walking,%n<gray>and flying", 16),
    ITEM_EVENTS_CATEGORY(Material.ITEM_FRAME, "<yellow>Item Events","<gray>Dropping, picking up%n<gray>and eating items", 28),
    DAMAGE_EVENTS_CATEGORY(Material.IRON_SWORD, "<red>Damage Events","<gray>Getting damaged,%n<gray>shooting a bow", 31),
    DEATH_EVENTS_CATEGORY(Material.SKELETON_SKULL, "<dark_red>Death Events","<gray>Dying, and respawning", 34),

    PLAYER_JOIN_GAME_EVENT(Material.POTATO, "Join", "<green>Player Join Game Event","<gray>Executes code when a%n<gray>player joins the plot.", PLOT_AND_SERVER_EVENTS_CATEGORY),
    PLAYER_REJOIN_EVENT(Material.PLAYER_HEAD, "Rejoin", "<yellow>Player Rejoin","<gray>Executes code when the player%n<gray>re-enters the world with the%n<gray>/play command.%n%n<dark_red>∅ <red>Cancellable", PLOT_AND_SERVER_EVENTS_CATEGORY),
    PLAYER_LEAVE_GAME_EVENT(Material.POISONOUS_POTATO, "Leave", "<green>Player Leave Game Event","<gray>Executes code when a%n<gray>player leaves the plot.", PLOT_AND_SERVER_EVENTS_CATEGORY),
    PLAYER_COMMAND_EVENT(Material.COMMAND_BLOCK, "Command", "<green>Player Command Event","<gray>Executes code when a player%n<gray>types a command on the plot.", PLOT_AND_SERVER_EVENTS_CATEGORY),
    RESOURCE_PACK_LOAD_EVENT(Material.GREEN_SHULKER_BOX, "PackLoad", "<dark_aqua>Resource Pack Load","<gray>Executes code when a%n<gray>player finishes loading a%n<gray>plot resource pack.", PLOT_AND_SERVER_EVENTS_CATEGORY),
    RESOURCE_PACK_DECLINE_EVENT(Material.RED_SHULKER_BOX, "PackDecline", "<dark_aqua>Resource Pack Decline","<gray>Executes code when a%n<gray>player declines a plot%n<gray>resource pack prompt.", PLOT_AND_SERVER_EVENTS_CATEGORY),
    PLAYER_CHAT_EVENT(Material.WRITABLE_BOOK, "Chat", "<blue>Player Chat Event","<gray>Executes code when a%n<gray>player sends a message%n<gray>in chat.%n%n<dark_red>∅ <red>Cancellable", PLOT_AND_SERVER_EVENTS_CATEGORY),

    PLAYER_RIGHT_CLICK_EVENT(Material.DIAMOND_PICKAXE, "RightClick", "<aqua>Player Right Click Event","<gray>Executes code when a player%n<gray>right clicks while looking at a%n<gray>block or holding an item.%n%n<dark_red>∅ <red>Cancellable", CLICK_EVENTS_CATEGORY),
    PLAYER_LEFT_CLICK_EVENT(Material.IRON_PICKAXE, "LeftClick", "<aqua>Player Left Click Event","<gray>Executes code when a player%n<gray>left clicks.%n%n<dark_red>∅ <red>Cancellable", CLICK_EVENTS_CATEGORY),
    PLAYER_RIGHT_CLICK_ENTITY_EVENT(Material.ARMOR_STAND, "ClickEntity", "<aqua>Player Right Click Entity Event","<gray>Executes code when a player%n<gray>right clicks an entity.%n%n<dark_red>∅ <red>Cancellable", CLICK_EVENTS_CATEGORY),
    PLAYER_RIGHT_CLICK_PLAYER_EVENT(Material.PLAYER_HEAD, "ClickPlayer", "<aqua>Player Right Click Player Event","<gray>Executes code when a player%n<gray>clicks another player.%n%n<dark_red>∅ <red>Cancellable", CLICK_EVENTS_CATEGORY),
    PLAYER_PLACE_BLOCK_EVENT(Material.POLISHED_ANDESITE, "PlaceBlock", "<gold>Player Place Block Event","<gray>Executes code when a player%n<gray>places a block.%n%n<dark_red>∅ <red>Cancellable", CLICK_EVENTS_CATEGORY),
    PLAYER_BREAK_BLOCK_EVENT(Material.COBBLESTONE, "BreakBlock", "<gold>Player Break Block Event","<gray>Executes code when a player%n<gray>breaks a block.%n%n<dark_red>∅ <red>Cancellable", CLICK_EVENTS_CATEGORY),
    PLAYER_SWAP_HANDS_EVENT(Material.NETHERITE_SCRAP, "SwapHands", "<blue>Player Swap Hands Event","<gray>Executes code when a player%n<gray>swaps an item or items between%n<gray>their main hand and off hand.%n%n<dark_red>∅ <red>Cancellable", CLICK_EVENTS_CATEGORY),
    PLAYER_CHANGE_SLOT_EVENT(Material.SLIME_BALL, "ChangeSlot", "<blue>Player Change Slot Event","<gray>Executes code when a player%n<gray>changes their hotbar slot.%n%n<dark_red>∅ <red>Cancellable", CLICK_EVENTS_CATEGORY),
    PLAYER_TAME_MOB_EVENT(Material.BONE, "TameMob", "<yellow>Player Tame Mob Event","<gray>Executes code when a player%n<gray>tames a mob.%n%n<dark_red>∅ <red>Cancellable", CLICK_EVENTS_CATEGORY),

    PLAYER_WALK_EVENT(Material.GRASS_BLOCK, "Walk", "<#2affaa>Player Walk Event","<gray>Executes code while%n<gray>a player is walking.%n%n<dark_red>∅ <red>Cancellable", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_JUMP_EVENT(Material.RABBIT_FOOT, "Jump", "<#2affaa>Player Jump Event","<gray>Executes code when a%n<gray>player jumps.%n%n<dark_red>∅ <red>Cancellable", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_SNEAK_EVENT(Material.CHAINMAIL_LEGGINGS, "Sneak", "<blue>Player Sneak Event","<gray>Executes code when a player%n<gray>sneaks.%n%n<dark_red>∅ <red>Cancellable", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_UNSNEAK_EVENT(Material.IRON_LEGGINGS, "Unsneak", "<blue>Player Unsneak Event","<gray>Executes code when a player%n<gray>stops sneaking.%n%n<dark_red>∅ <red>Cancellable", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_START_SPRINT_EVENT(Material.GOLDEN_BOOTS, "StartSprint", "<#2affaa>Player Start Sprint Event","<gray>Executes code when a%n<gray>player starts sprinting.%n%n<dark_red>∅ <red>Cancellable", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_STOP_SPRINTING_EVENT(Material.IRON_BOOTS, "StopSprint", "<#2affaa>Player Stop Sprinting Event","<gray>Executes code when a%n<gray>player stops sprinting.%n%n<dark_red>∅ <red>Cancellable", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_START_FLIGHT_EVENT(Material.ELYTRA, "StartFly", "<#2affaa>Player Start Flight Event","<gray>Executes code when a%n<gray>player starts flying.%n%n<dark_red>∅ <red>Cancellable", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_STOP_FLIGHT_EVENT(Material.ELYTRA, "StopFly", "<#2affaa>Player Stop Flight Event","<gray>Executes code when a%n<gray>player stops flying.%n%n<dark_red>∅ <red>Cancellable", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_RIPTIDE_EVENT(Material.TRIDENT, "Riptide", "<#2affaa>Player Riptide Event","<gray>Executes code when a%n<gray>player throws a riptide trident.%n%n<dark_red>∅ <red>Cancellable", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_DISMOUNT_EVENT(Material.DARK_OAK_BOAT, "Dismount", "<#2affaa>Player Dismount Event","<gray>Executes code when a%n<gray>player dismounts a vehicle or%n<gray>other entity.%n%n<dark_red>∅ <red>Cancellable", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_HORSE_JUMP_EVENT(Material.GOLDEN_HORSE_ARMOR, "HorseJump", "<#2affaa>Player Horse Jump Event","<gray>Executes code when a%n<gray>player causes a horse to jump.%n%n<dark_red>∅ <red>Cancellable", MOVEMENT_EVENTS_CATEGORY),
    PLAYER_VEHICLE_JUMP_EVENT(Material.FURNACE_MINECART, "VehicleJump", "<#2affaa>Player Vehicle Jump Event","<gray>Executes code when a%n<gray>player presses the jump key%n<gray>while riding a vehicle%n<gray>or other entity.%n%n<dark_red>∅ <red>Cancellable", MOVEMENT_EVENTS_CATEGORY),

    PLAYER_CLICK_MENU_SLOT_EVENT(Material.CRAFTING_TABLE, "ClickMenuSlot", "<#2affaa>Player Click Menu Slot Event","<gray>Executes code when a player%n<gray>clicks a slot in an inventory%n<gray>menu.%n%n<dark_red>∅ <red>Cancellable", ITEM_EVENTS_CATEGORY),
    PLAYER_CLICK_INVENTORY_SLOT_EVENT(Material.LIGHT_GRAY_SHULKER_BOX, "ClickInvSlot", "<#2affaa>Player Click Inventory Slot Event","<gray>Executes code when a player%n<gray>clicks a slot inside their%n<gray>inventory.%n%n<dark_red>∅ <red>Cancellable", ITEM_EVENTS_CATEGORY),
    PLAYER_PICK_UP_ITEM_EVENT(Material.GLOWSTONE_DUST, "PickUpItem", "<#2affaa>Player Pick Up Item Event","<gray>Executes code when a%n<gray>player picks up an item.%n%n<dark_red>∅ <red>Cancellable", ITEM_EVENTS_CATEGORY),
    PLAYER_DROP_ITEM_EVENT(Material.SUGAR, "DropItem", "<#2affaa>Player Drop Item Event","<gray>Executes code when a%n<gray>player drops an item.%n%n<dark_red>∅ <red>Cancellable", ITEM_EVENTS_CATEGORY),
    PLAYER_CONSUME_ITEM_EVENT(Material.COOKED_CHICKEN, "Consume", "<#2affaa>Player Consume Item Event","<gray>Executes code when a player%n<gray>eats or drinks an item.%n%n<dark_red>∅ <red>Cancellable", ITEM_EVENTS_CATEGORY),
    PLAYER_BREAK_ITEM_EVENT(Material.STONE_PICKAXE, "BreakItem", "<#2affaa>Player Break Item Event","<gray>Executes code when a%n<gray>player breaks an item.%n%n<dark_red>∅ <red>Cancellable", ITEM_EVENTS_CATEGORY),
    PLAYER_CLOSE_INVENTORY_EVENT(Material.STRUCTURE_VOID, "CloseInv", "<#2affaa>Player Close Inventory Event","<gray>Executes code when a%n<gray>player closes an inventory.%n%n<dark_red>∅ <red>Cancellable", ITEM_EVENTS_CATEGORY),
    PLAYER_FISH_EVENT(Material.FISHING_ROD, "Fish", "<#2affaa>Player Fish Event","<gray>Executes code when a player%n<gray>fishes an entity, player, or%n<gray>nothing.%n%n<dark_red>∅ <red>Cancellable", ITEM_EVENTS_CATEGORY),

    PLAYER_TAKE_DAMAGE_EVENT(Material.DEAD_BUSH, "TakeDamage", "<red>Player Take Damage Event","<gray>Executes code when a%n<gray>player takes damage.%n%n<dark_red>∅ <red>Cancellable", DAMAGE_EVENTS_CATEGORY),
    PLAYER_DAMAGE_PLAYER_EVENT(Material.IRON_SWORD, "DamagePlayer", "<red>Player Damage Player Event","<gray>Executes code when a%n<gray>player damages another player.%n%n<dark_red>∅ <red>Cancellable", DAMAGE_EVENTS_CATEGORY),
    PLAYER_DAMAGE_ENTITY_EVENT(Material.STONE_SWORD, "DamageEntity", "<red>Player Damage Entity Event","<gray>Executes code when a%n<gray>player damages an entity.%n%n<dark_red>∅ <red>Cancellable", DAMAGE_EVENTS_CATEGORY),
    ENTITY_DAMAGE_PLAYER_EVENT(Material.WOODEN_SWORD, "EntityDmgPlayer", "<red>Entity Damage Player Event","<gray>Executes code when an%n<gray>entity damages a player.%n%n<dark_red>∅ <red>Cancellable", DAMAGE_EVENTS_CATEGORY),
    PLAYER_HEAL_EVENT(Material.SPLASH_POTION, "Heal", "<#2affaa>Player Heal Event","<gray>Executes code when a player%n<gray>regains health from any%n<gray>source.%n%n<dark_red>∅ <red>Cancellable", DAMAGE_EVENTS_CATEGORY),
    PLAYER_SHOOT_BOW_EVENT(Material.BOW, "ShootBow", "<red>Player Shoot Bow Event","<gray>Executes code when a player%n<gray>fires an arrow with a bow.%n%n<dark_red>∅ <red>Cancellable", DAMAGE_EVENTS_CATEGORY),
    PLAYER_SHOOT_PROJECTILE_EVENT(Material.SNOWBALL, "ShootProjectile", "<red>Player Shoot Projectile Event","<gray>Executes code when a player%n<gray>throws a projectile such%n<gray>as snowballs or eggs.%n%n<dark_red>∅ <red>Cancellable", DAMAGE_EVENTS_CATEGORY),
    PLAYER_PROJECTILE_HIT_EVENT(Material.SPECTRAL_ARROW, "ProjHit", "<dark_aqua>Player Projectile Hit Event","<gray>Executes code when a projectile%n<gray>launched by a player hits a block,%n<gray>an entity, or another player.%n%n<dark_red>∅ <red>Cancellable", DAMAGE_EVENTS_CATEGORY),
    PLAYER_PROJECTILE_DAMAGE_PLAYER_EVENT(Material.ARROW, "ProjDamage", "<red>Projectile Damage Player Event","<gray>Executes code when a projectile%n<gray>damages a player.%n%n<dark_red>∅ <red>Cancellable", DAMAGE_EVENTS_CATEGORY),
    POTION_CLOUD_IMBUE_PLAYER_EVENT(Material.ARROW, "CloudImbue", "<dark_aqua>Potion Cloud Imbue Player Event","<gray>Executes code when an area of%n<gray>effect cloud applies its potion%n<gray>effect(s) to a player.%n%n<dark_red>∅ <red>Cancellable", DAMAGE_EVENTS_CATEGORY),

    PLAYER_DEATH_EVENT(Material.REDSTONE, "Death", "<dark_red>Player Death Event","<gray>Executes code when a%n<gray>player dies.%n%n<dark_red>∅ <red>Cancellable", DEATH_EVENTS_CATEGORY),
    PLAYER_KILL_PLAYER_EVENT(Material.SKELETON_SKULL, "KillPlayer", "<dark_red>Player Kill Player Event","<gray>Executes code when a%n<gray>player kills another player.%n%n<dark_red>∅ <red>Cancellable", DEATH_EVENTS_CATEGORY),
    PLAYER_RESURRECT_EVENT(Material.TOTEM_OF_UNDYING, "Resurrect", "<dark_aqua>Player Resurrect Event","<gray>Executes code when a%n<gray>player resurrects with a%n<gray>totem of undying.%n%n<dark_red>∅ <red>Cancellable", DEATH_EVENTS_CATEGORY),
    PLAYER_KILL_MOB_EVENT(Material.SKELETON_SPAWN_EGG, "KillMob", "<dark_red>Player Kill Mob Event","<gray>Executes code when a%n<gray>player kills a mob.%n%n<dark_red>∅ <red>Cancellable", DEATH_EVENTS_CATEGORY),
    MOB_KILL_PLAYER_EVENT(Material.BONE, "MobKillPlayer", "<dark_red>Mob Kill Player Event","<gray>Executes code when a%n<gray>mob kills a player.%n%n<dark_red>∅ <red>Cancellable", DEATH_EVENTS_CATEGORY),
    PLAYER_RESPAWN_EVENT(Material.OAK_SAPLING, "Respawn", "<dark_aqua>Player Respawn Event","<gray>Executes code when a%n<gray>player respawns.%n%n<dark_red>∅ <red>Cancellable", DEATH_EVENTS_CATEGORY);

    @Getter final Material material;
    @Getter String id = null;
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

    PlayerEventItems(Material material, String id, String name, String lore, PlayerEventItems category) {
        this.material = material;
        this.id = id;
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
