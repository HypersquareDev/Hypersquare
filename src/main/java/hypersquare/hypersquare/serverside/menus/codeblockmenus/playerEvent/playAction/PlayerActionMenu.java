package hypersquare.hypersquare.serverside.menus.codeblockmenus.playerEvent.playAction;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.serverside.menus.codeblockmenus.playerEvent.*;
import hypersquare.hypersquare.serverside.utils.Utilities;
import hypersquare.hypersquare.serverside.utils.managers.ItemManager;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerActionMenu extends Gui {
    public PlayerActionMenu(Player player) {
        super(player, "playerActions", "Player Actions", 6);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        for (String itemStackKey : ItemManager.registeredItems().keySet()) {
            if (itemStackKey.startsWith("player_action_categories")) {

                Icon item = new Icon(ItemManager.getItem(itemStackKey));
                int slot = ItemManager.getItem(itemStackKey).getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"slot"), PersistentDataType.INTEGER);
                addItem(slot, item);

                item.onClick(e -> {
                    e.setCancelled(true);
                    Utilities.sendClickMenuSound(player);
                    if ("Item Management".equals(ChatColor.stripColor(ItemManager.getItem(itemStackKey).getItemMeta().getDisplayName()))) {
                        new PlayerActionItemManagementMenu((Player) event.getPlayer()).open();
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
