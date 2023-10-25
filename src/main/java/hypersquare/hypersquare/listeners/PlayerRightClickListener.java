package hypersquare.hypersquare.listeners;


import hypersquare.hypersquare.items.MiscItems;
import hypersquare.hypersquare.menus.MyPlotsMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerRightClickListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            if (event.getItem() != null && event.getItem() == MiscItems.MY_PLOTS.build()){
                new MyPlotsMenu(event.getPlayer()).open();
            }
        }
    }
}
