package hypersquare.hypersquare;

import org.bukkit.entity.Player;

public class LoadItems {
    public static void devItems(Player player){
        player.getInventory().setItem(0,ItemManager.getItem("playerEvent"));
    }
}
