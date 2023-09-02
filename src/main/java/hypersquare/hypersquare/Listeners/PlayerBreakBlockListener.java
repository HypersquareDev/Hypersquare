package hypersquare.hypersquare.Listeners;

import com.infernalsuite.aswm.api.SlimePlugin;
import hypersquare.hypersquare.CodeBlockManagement;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.Utilities;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.HashMap;

import static hypersquare.hypersquare.Utilities.deserializeLocation;

public class PlayerBreakBlockListener implements Listener {
    Plugin plugin = Hypersquare.getPlugin(Hypersquare.class);
    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        if (Hypersquare.mode.get(event.getPlayer()).equals("coding")) {
            event.setCancelled(true);
            int size = 2;
            Location blockLoc = event.getBlock().getLocation();
            Location signLoc = blockLoc.clone().add(-1,0,0);
            Location chestLoc = blockLoc.clone().add(0,1,0);
            Location stoneLoc = blockLoc.clone().add(0,0,1);

            signLoc.getBlock().setType(Material.AIR);
            blockLoc.getBlock().setType(Material.AIR);
            boolean hadBrackets = false;

            if (stoneLoc.getBlock().getType() == Material.PISTON){
                CodeBlockManagement.findCorrespBracket(blockLoc).getBlock().setType(Material.AIR);
                size = (int) CodeBlockManagement.findCorrespBracket(blockLoc).distance(blockLoc);
                event.getPlayer().sendMessage(CodeBlockManagement.findCorrespBracket(blockLoc).distance(blockLoc) + "");
                hadBrackets = true;
            }
            stoneLoc.getBlock().setType(Material.AIR);
            chestLoc.getBlock().setType(Material.AIR);

            CodeBlockManagement.moveCodeLine(blockLoc.clone().add(0,0,1), size*-1);
        }
    }


}
