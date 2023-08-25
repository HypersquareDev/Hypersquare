package hypersquare.hypersquare.Listeners;

import hypersquare.hypersquare.ItemManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        spawn(player);
    }
    public void spawn(Player player){
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().addItem(ItemManager.getItem("myPlots"));
    }
}
