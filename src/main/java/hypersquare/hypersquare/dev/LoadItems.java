package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.ItemManager;
import org.bukkit.entity.Player;

public class LoadItems {
    public static void devInventory(Player player){
        player.getInventory().setItem(0, ItemManager.getItem("dev.playerEvent"));
    }
}
