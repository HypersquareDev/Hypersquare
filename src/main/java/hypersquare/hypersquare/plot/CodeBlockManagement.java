package hypersquare.hypersquare.plot;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.blocks.Blocks;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
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
    public static Location findCodeEnd(Location location){
        while (location.clone().add(0,0,1).getBlock().getType() != Material.AIR || location.clone().add(0,0,2).getBlock().getType() != Material.AIR || location.clone().getBlock().getType() != Material.AIR){
            location.add(0,0,1);
        }
        return location;
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

    public static Location findNextBracket(Location location){
        while (location.clone().add(0,0,1).getBlock().getType() != Material.AIR || location.clone().add(0,0,2).getBlock().getType() != Material.AIR || location.clone().getBlock().getType() != Material.AIR){
            location.add(0,0,1);
            if (location.getBlock().getType() == Material.PISTON || location.getBlock().getType() == Material.STICKY_PISTON) {
                return location;
            }

        }

        return null;

    }




    public static void moveCodeLine(Location location, int amount){
        Location loc1 = location.clone().add(-1, 0, 0);
        Location loc2 = findCodeEnd(location.clone()).add(0, 1, 0);
        World world = BukkitAdapter.adapt(loc1.getWorld());
        Location pasteLoc = location.clone().add(0, 0, amount);

        Region region = new CuboidRegion(world, BlockVector3.at(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ()), BlockVector3.at(loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ()));
        BlockArrayClipboard clipboard = new BlockArrayClipboard(region);

        ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(
                world, region, clipboard, BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ())
        );

        Operations.complete(forwardExtentCopy);
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            BlockState block = BukkitAdapter.adapt(Material.AIR.createBlockData());
            editSession.setBlocks(region, block);
        }

        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(pasteLoc.getBlockX(), pasteLoc.getBlockY(), pasteLoc.getBlockZ()))
                    .build();

            Operations.complete(operation);
        }
    }
}
