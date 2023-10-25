package hypersquare.hypersquare.plot;

import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.sign.Side;

import java.util.ArrayList;

public class CodeBlockManagement {
    @Deprecated
    public static void placeBracket(Location location, String codeblock, String bracket, String action) {
        Location loc = location.getBlock().getLocation();
        if (bracket.equals("open")) {
            //Open Bracket
            location.add(0, 0, 0).getBlock().setType(Material.PISTON);
            BlockData pistonData = location.getBlock().getBlockData();
            ((Directional) pistonData).setFacing(BlockFace.SOUTH);
            location.getBlock().setBlockData(pistonData);
        }

        if (bracket.equals("close")) {
            //Close Bracket
            location.add(0, 0, 0).getBlock().setType(Material.PISTON);
            BlockData pistonData = location.getBlock().getBlockData();
            ((Directional) pistonData).setFacing(BlockFace.NORTH);
            location.getBlock().setBlockData(pistonData);
        }

        if (bracket.equals("none")) {
        }

        if (bracket.equals("stone")){
            location.add(0, 0, 1).getBlock().setType(Material.STONE);
        }
    }

    public static Location findCorrespBracket(Location location){
            int i = 0;
            while (location.clone().add(0,0,1).getBlock().getType() != Material.AIR || location.clone().add(0,0,2).getBlock().getType() != Material.AIR || location.clone().getBlock().getType() != Material.AIR){
                location.add(0,0,1);
                if (location.getBlock().getType() == Material.PISTON) {
                    Piston piston = (Piston) location.getBlock().getBlockData();
                    if (piston.getFacing() == BlockFace.SOUTH)
                        i--;
                    if (piston.getFacing() == BlockFace.NORTH)
                        i++;
                    if (i == 0){
                        return location;
                    }
                }

            }

        return null;

    }

    public static Location findCodelineStartLoc(Location location){
        while (location.clone().add(0,0,-1).getBlock().getType() != Material.AIR || location.clone().add(0,0,-2).getBlock().getType() != Material.AIR || location.clone().getBlock().getType() != Material.AIR){
            if (location.getBlock().getType() == Material.DIAMOND_BLOCK ||location.getBlock().getType() ==  Material.LAPIS_BLOCK ||location.getBlock().getType() ==  Material.EMERALD_BLOCK ||location.getBlock().getType() ==  Material.GOLD_BLOCK){
                return location;
            }
            location.add(0,0,-1);
        }
        return null;
    }




    public static void moveCodeLine(Location location, int amount){

        Location loc1 = location.clone().add(-1,0,0);
        Location loc2 = location.clone().add(0,1,320);
        ArrayList clipboard = new ArrayList<ArrayList>();
        for (Double x = loc1.getX(); x <= loc2.getX(); x++) {
            for (Double y = loc1.getY(); y <= loc2.getY(); y++) {
                for (Double z = loc1.getZ(); z <= loc2.getZ(); z++) {
                    Location currentLoc = new Location(location.getWorld(), x.intValue(), y.intValue(), z.intValue());
                    ArrayList blocks = new ArrayList();
                    blocks.add(currentLoc.clone().add(0,0,amount));
                    blocks.add(currentLoc.getBlock().getType());
                    blocks.add(currentLoc.getBlock().getBlockData());
                    if (currentLoc.getBlock().getState() instanceof Sign) {
                        Sign sign = (Sign) currentLoc.getBlock().getState();
                        blocks.add(sign.getSide(Side.FRONT).getLines());
                    }
                    clipboard.add(blocks);
                    currentLoc.getBlock().setType(Material.AIR);
                }
            }
        }
        for (Object list : clipboard){
            ArrayList list1 = (ArrayList) list;
            Location location1 = (Location) list1.get(0);
            location1.getBlock().setType((Material) list1.get(1));
            location1.getBlock().setBlockData((BlockData) list1.get(2));
            if (list1.size() == 4){
                Sign sign = (Sign) location1.getBlock().getState();
                sign.setEditable(true);

                sign.update();
                int i = 0;
                for (String text : (String[]) list1.get(3)){
                    sign.getSide(Side.FRONT).setLine(i,text);
                    i++;
                }
                sign.update();
            }

        }

    }
}
