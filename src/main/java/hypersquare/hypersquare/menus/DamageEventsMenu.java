package hypersquare.hypersquare.menus;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.utils.Utilities;
import hypersquare.hypersquare.utils.managers.ItemManager;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.persistence.PersistentDataType;

public class DamageEventsMenu extends Gui {
    public DamageEventsMenu(Player player) {
        super(player, "damageEvents", "Damage events", 5);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        for (String itemStackKey : ItemManager.registeredItems().keySet()) {
            if (itemStackKey.contains("player_event.damage_events.")) {
                Icon item = new Icon(ItemManager.getItem(itemStackKey));
                int slot = ItemManager.getItem(itemStackKey).getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "slot"), PersistentDataType.INTEGER);
                addItem(slot, item);

                item.onClick(e -> {
                    e.setCancelled(true);
                    Utilities.sendClickMenuSound(player);
                });
            }
        }

    }
}
