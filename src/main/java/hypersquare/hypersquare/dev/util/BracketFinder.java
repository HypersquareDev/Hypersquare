package hypersquare.hypersquare.dev.util;

import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.dev.codefile.CodeFileHelper;
import hypersquare.hypersquare.plot.CodeBlockManagement;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Piston;


public class BracketFinder {

    public static boolean isInIfStatement(Location origLoc){
        int depth = 0;
        Location end = CodeBlockManagement.findCodeEnd(origLoc.clone());
        Location location = origLoc.clone().set(origLoc.getBlockX(),origLoc.getBlockY(),1);
        while (location.getBlockZ() <= end.getBlockZ() + 5) {
            if (location.getBlock().getType() == Material.PISTON){
                Piston piston = (Piston) location.getBlock().getBlockData();
                if (piston.getFacing() == BlockFace.SOUTH) {
                    depth++;
                }
                if (piston.getFacing() == BlockFace.NORTH){
                    depth--;
                }
            }
            if (depth > 0 && location.getBlockZ() == origLoc.getBlockZ()) {
                return true;
            }
            if (location == end){
                return false;
            }
            location.add(0,0,1);
        }
        return false;
    }

    public static int codeIndexInIf(Location origLoc){
        int depth = 0;
        Location end = CodeBlockManagement.findCodeEnd(origLoc.clone());
        Location location = origLoc.clone().set(origLoc.getBlockX(),origLoc.getBlockY(),1);
        while (location.getBlockZ() <= end.getBlockZ() + 5) {
            if (location.getBlock().getType() == Material.PISTON){
                Piston piston = (Piston) location.getBlock().getBlockData();
                if (piston.getFacing() == BlockFace.SOUTH) {
                    depth++;
                }
                if (piston.getFacing() == BlockFace.NORTH){
                    depth--;
                }
            }
            if (depth > 0 && location.getBlockZ() == origLoc.getBlockZ()) {
                return CodeFileHelper.getCodeblockIndex(origLoc);
            }
            if (location == end){
                return 0;
            }
            location.add(0,0,1);
        }
        return 0;
    }

}
