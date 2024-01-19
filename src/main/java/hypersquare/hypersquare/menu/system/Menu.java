package hypersquare.hypersquare.menu.system;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class Menu {

    public static final HashMap<Player, Menu> openMenus = new HashMap<>();
    private final Inventory inventory;
    private final HashMap<Integer, MenuItem> items = new HashMap<>();
    private final Player player;

    /**
     * Creates a new menu
     * @param player Player to create menu for
     * @param title Inventory title
     * @param rows Amount of rows (1-6)
     */
    public Menu(Player player, Component title, int rows) {
        inventory = Bukkit.createInventory(player, rows * 9, title);
        this.player = player;
    }

    /**
     * Sets an item in the menu
     * @param slot Slot to set item in
     * @param item Item to set
     * @return {@link Menu}
     */
    public Menu slot(int slot, MenuItem item) {
        inventory.setItem(slot, item.item);
        items.put(slot, item);
        return this;
    }

    /**
     * Sets all the items in a menu
     * @implNote Menu items don't clear before this
     * @param items Items to set
     * @return {@link Menu}
     */
    public Menu items(List<MenuItem> items) {
        for (int i = 0; i < items.size(); i++) {
            slot(i, items.get(i));
        }
        return this;
    }

    /**
     * Gets the inventory size of the menu
     * @return Inventory size
     */
    public int getSize() {
        return inventory.getSize();
    }

    /**
     * Inserts an item to the menu, replacing the first empty slot,
     * or does nothing if no empty slot is found.
     * @param item Item to insert
     * @return {@link Menu}
     */
    public Menu addItem(MenuItem item) {
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack current = inventory.getItem(i);
            if (current == null || current.getType() == Material.AIR) {
                slot(i, item);
                return this;
            }
        }
        return this;
    }

    /**
     * Runs the click event for the item in the specified slot
     * @param slot Slot to click
     */
    public void performClick(int slot) {
        if (!items.containsKey(slot)) return;
        items.get(slot).performClick();
    }

    /**
     * Opens the menu for the player
     */
    public void open() {
        player.openInventory(inventory);
        openMenus.put(player, this);
    }
}
