package hypersquare.hypersquare.item;

import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import static hypersquare.hypersquare.Hypersquare.cleanMM;

public enum PlayerEventItems {
    PLOT_AND_SERVER_EVENTS_CATEGORY(Material.COMMAND_BLOCK, "<gold>Plot and Server Events","<gray>Joining and leaving plots,%n<gray>plot commands", 10),
    CLICK_EVENTS_CATEGORY(Material.DIAMOND_PICKAXE, "<blue>Click Events","<gray>Right and left clicking,%n<gray>breaking blocks", 13),
    MOVEMENT_EVENTS_CATEGORY(Material.LEATHER_BOOTS, "<dark_aqua>Movement Events","<gray>Jumping, walking,%n<gray>and flying", 16),
    ITEM_EVENTS_CATEGORY(Material.ITEM_FRAME, "<yellow>Item Events","<gray>Dropping, picking up%n<gray>and eating items", 28),
    DAMAGE_EVENTS_CATEGORY(Material.IRON_SWORD, "<red>Damage Events","<gray>Getting damaged,%n<gray>shooting a bow", 31),
    DEATH_EVENTS_CATEGORY(Material.SKELETON_SKULL, "<dark_red>Death Events","<gray>Dying, and respawning", 34);

    public final Material material;
    public final String id;
    public final String name;
    public final String lore;
    public final PlayerEventItems category;
    public final int slot;
    PlayerEventItems(Material material, String name, String lore, int slot) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.slot = slot;
        this.category = null;
        this.id = null;
    }

    PlayerEventItems(Material material, String id, String name, String lore, PlayerEventItems category) {
        this.material = material;
        this.id = id;
        this.name = name;
        this.lore = lore;
        this.category = category;
        this.slot = 0;
    }

    public ItemStack build() {
        return Utilities.formatItem(lore, material, name);
    }

    public static ArrayList<PlayerEventItems> getEvents(PlayerEventItems category) {
        ArrayList<PlayerEventItems> actions = new ArrayList<>();
        for (PlayerEventItems playerEventItem : PlayerEventItems.values()) {
            if (Objects.equals(playerEventItem.category, category)) {
                actions.add(playerEventItem);
            }
        }
        return actions;
    }

    public @NotNull Component getName() {
        return cleanMM.deserialize(name);
    }

    public Component getLore() {
        return cleanMM.deserialize(lore.replace("%n", "\n"));
    }
}
