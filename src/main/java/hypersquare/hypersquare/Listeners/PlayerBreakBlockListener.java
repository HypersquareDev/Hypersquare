package hypersquare.hypersquare.Listeners;

import com.infernalsuite.aswm.api.SlimePlugin;
import hypersquare.hypersquare.CodeBlockManagement;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.ItemManager;
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
            if (blockLoc.getY() >= 0) {
                if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null && ItemManager.getItemID(event.getPlayer().getInventory().getItemInMainHand()).equals("dev.glitch"))
                {
                    event.setCancelled(false);

                } else {
                    if (blockLoc.getBlock().getType() == Material.OAK_WALL_SIGN) {
                        blockLoc.add(1, 0, 0);
                    }
                    if (blockLoc.clone().add(-1, 0, 0).getBlock().getType() == Material.OAK_WALL_SIGN) {
                        Location signLoc = blockLoc.clone().add(-1, 0, 0);
                        Location chestLoc = blockLoc.clone().add(0, 1, 0);
                        Location stoneLoc = blockLoc.clone().add(0, 0, 1);

                        Location bracketLoc = CodeBlockManagement.findCorrespBracket(blockLoc.clone());
                        signLoc.getBlock().setType(Material.AIR);
                        blockLoc.clone().getBlock().setType(Material.AIR);
                        stoneLoc.getBlock().setType(Material.AIR);
                        chestLoc.getBlock().setType(Material.AIR);
                        if (bracketLoc != null) {
                            bracketLoc.getBlock().setType(Material.AIR);
                            CodeBlockManagement.moveCodeLine(bracketLoc.add(0, 0, 1), -1);
                            CodeBlockManagement.moveCodeLine(blockLoc.add(0, 0, 2), -2);
                        } else {
                            CodeBlockManagement.moveCodeLine(blockLoc.clone().add(0, 0, 2), -2);
                        }
                    }
                }
            }
        }
    }


}
