package hypersquare.hypersquare.listeners;


import hypersquare.hypersquare.items.MiscItems;
import hypersquare.hypersquare.menus.GameMenu;
import hypersquare.hypersquare.menus.MyPlotsMenu;
import hypersquare.hypersquare.utils.Utilities;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerRightClickListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            if (event.getItem() != null && event.getItem().getItemMeta().equals(MiscItems.MY_PLOTS.build().getItemMeta())){
                new MyPlotsMenu(event.getPlayer()).open();
            }
            if (event.getItem() != null && event.getItem().getItemMeta().equals(MiscItems.GAME_MENU.build().getItemMeta())){
                GameMenu.create().open(player);
                Utilities.sendOpenMenuSound(player);
            }
        }
    }
}
