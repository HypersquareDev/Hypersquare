package hypersquare.hypersquare.menus;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.utils.Utilities;
import hypersquare.hypersquare.utils.managers.ItemManager;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerEventMenu extends Gui {
    public PlayerEventMenu(Player player) {
        super(player, "playerEvents", "Player Events", 5);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        for (String itemStackKey : ItemManager.registeredItems().keySet()) {
            if (itemStackKey.startsWith("player_event_categories")) {

                Icon item = new Icon(ItemManager.getItem(itemStackKey));
                int slot = ItemManager.getItem(itemStackKey).getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"slot"), PersistentDataType.INTEGER);
                addItem(slot, item);

                item.onClick(e -> {
                    e.setCancelled(true);
                    Utilities.sendClickMenuSound(player);
                    if ("Plot and Server Events".equals(ChatColor.stripColor(ItemManager.getItem(itemStackKey).getItemMeta().getDisplayName()))) {
                        new PlotAndServerEvents((Player) event.getPlayer()).open();
                    }
                    if ("Click Events".equals(ChatColor.stripColor(ItemManager.getItem(itemStackKey).getItemMeta().getDisplayName()))) {
                        new ClickEventsMenu((Player) event.getPlayer()).open();
                    }
                    if ("Movement Events".equals(ChatColor.stripColor(ItemManager.getItem(itemStackKey).getItemMeta().getDisplayName()))) {
                        new MovementEventsMenu((Player) event.getPlayer()).open();
                    }
                    if ("Damage Events".equals(ChatColor.stripColor(ItemManager.getItem(itemStackKey).getItemMeta().getDisplayName()))) {
                        new DamageEventsMenu((Player) event.getPlayer()).open();
                    }
                    if ("Item Events".equals(ChatColor.stripColor(ItemManager.getItem(itemStackKey).getItemMeta().getDisplayName()))) {
                        new ItemEventsMenu((Player) event.getPlayer()).open();
                    }
                    if ("Death Events".equals(ChatColor.stripColor(ItemManager.getItem(itemStackKey).getItemMeta().getDisplayName()))) {
                        new DeathEventsMenu((Player) event.getPlayer()).open();
                    }


                });
            }
        }
    }
}

