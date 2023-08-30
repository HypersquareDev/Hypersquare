package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.ItemManager;
import org.bukkit.entity.Player;

public class LoadItems {
    public static void devInventory(Player player){
        player.getInventory().setItem(0, ItemManager.getItem("dev.event"));
        player.getInventory().setItem(1,ItemManager.getItem("dev.if_player"));
        player.getInventory().setItem(2,ItemManager.getItem("dev.player_action"));
        player.getInventory().setItem(3,ItemManager.getItem("dev.call_function"));
        player.getInventory().setItem(4,ItemManager.getItem("dev.start_process"));
        player.getInventory().setItem(9,ItemManager.getItem("dev.control"));
        player.getInventory().setItem(10,ItemManager.getItem("dev.select_object"));
        player.getInventory().setItem(11,ItemManager.getItem("dev.repeat"));
        player.getInventory().setItem(12,ItemManager.getItem("dev.else"));
        player.getInventory().setItem(18,ItemManager.getItem("dev.set_var"));
        player.getInventory().setItem(19,ItemManager.getItem("dev.if_var"));




    }
}
