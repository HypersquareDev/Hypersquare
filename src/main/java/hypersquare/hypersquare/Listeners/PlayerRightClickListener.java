package hypersquare.hypersquare.Listeners;

import hypersquare.hypersquare.ItemManager;
import hypersquare.hypersquare.Menus.MyPlotsMenu;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerRightClickListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            Player player = event.getPlayer();
            if (event.getItem().getItemMeta().equals(ItemManager.getItem("myPlots").getItemMeta())){
                new MyPlotsMenu(event.getPlayer()).open();
            }
            if (event.getClickedBlock().getType() == Material.LEVER){
                 TNTPrimed tnt = (TNTPrimed) event.getClickedBlock().getWorld().spawnEntity(event.getClickedBlock().getLocation(), EntityType.PRIMED_TNT);
                 tnt.setFuseTicks(1);
            }

        }
    }
}
