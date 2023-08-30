package hypersquare.hypersquare.Listeners;

import com.alibaba.fastjson.JSON;
import hypersquare.hypersquare.ChangeGameMode;
import hypersquare.hypersquare.ItemManager;
import hypersquare.hypersquare.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.util.HashMap;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();
        ChangeGameMode.spawn(player);
    }

}
