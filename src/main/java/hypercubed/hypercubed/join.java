package hypercubed.hypercubed;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class join implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        spawn(player);
    }
    public void spawn(Player player){
        Bukkit.broadcastMessage("SPAWN!!");
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().addItem(items.myPlots());
    }
}
