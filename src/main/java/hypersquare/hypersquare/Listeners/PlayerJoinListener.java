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
        player.sendMessage(JSON.toJSON(Utilities.decode("H4sIAAAAAAAA/6WNQQqDQAxF75K1J5irFJFp/C2D42SIoSAyd29EV4KL0lX4n5f/Nnpm4Wmh8NgojRSOTN15A+GDYp6jvp1yyDDveN+8Y0tSHKLW3byn11BzXKE/TGjkCbtzTAo2r6SieLa1wlMRne+Nh244h/+xcpYFV23fvkgWfqk0AQAA")).toString());
    }

}
