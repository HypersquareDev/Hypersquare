package hypersquare.hypersquare.serverside.plot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CodeExecuter {
    public static void  runThroughCode(Location startLoc, Player target){
        startLoc.add(0,0,1);
        while (startLoc.clone().add(0,0,1).getBlock().getType() != Material.AIR || startLoc.clone().add(0,0,2).getBlock().getType() != Material.AIR || startLoc.clone().getBlock().getType() != Material.AIR){

                Location signLocation = startLoc.clone().add(-1,0,0);
                if (signLocation.getBlock().getType() == Material.OAK_WALL_SIGN){
                    Sign sign = (Sign) signLocation.getBlock().getState();
                    String[] signInfo = sign.getSide(Side.FRONT).getLines();
                    if (signInfo.length > 0) {
                        Chest chest = (Chest) startLoc.clone().add(0,1,0).getBlock().getState();
                        target.sendMessage(signInfo[1]);
                    }
                }
                startLoc.add(0,0,1);
            }
    }
}
