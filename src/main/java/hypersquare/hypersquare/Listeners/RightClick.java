package hypersquare.hypersquare.Listeners;

import hypersquare.hypersquare.ItemManager;
import org.bukkit.entity.Interaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RightClick implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            if (event.getItem().getItemMeta() == ItemManager.getItem("myPlots").getItemMeta())
            {

            }
        }
    }
}
