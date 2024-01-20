package hypersquare.hypersquare.menu.system;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MenuListeners implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (!Menu.openMenus.containsKey(player)) return;

            switch (event.getAction()) {
                case PICKUP_ALL, PICKUP_ONE, PICKUP_SOME, PICKUP_HALF -> {
                    event.setCancelled(true);
                    Menu.openMenus.get(player).performClick(event);
                }
                default -> event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (!Menu.openMenus.containsKey(player)) return;
            Menu.openMenus.remove(player);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (!Menu.openMenus.containsKey(event.getPlayer())) return;
        Menu.openMenus.remove(event.getPlayer());
    }

}
