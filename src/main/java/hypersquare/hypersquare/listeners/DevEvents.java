package hypersquare.hypersquare.listeners;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.CodeBlocks;
import hypersquare.hypersquare.dev.CodeItems;
import hypersquare.hypersquare.menus.codeblockmenus.PlayerEventMenu;
import hypersquare.hypersquare.menus.codeblockmenus.PlayerActionMenu;
import hypersquare.hypersquare.plot.CodeBlockManagement;
import hypersquare.hypersquare.plot.LoadCodeTemplate;
import hypersquare.hypersquare.plot.PlotDatabase;
import hypersquare.hypersquare.plot.RestrictMovement;
import hypersquare.hypersquare.utils.Utilities;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DevEvents implements Listener {
    private static final Plugin plugin = Hypersquare.getPlugin(Hypersquare.class);

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        if (!Hypersquare.mode.get(event.getPlayer()).equals("coding")) {
            if (event.getBlock().getLocation().getBlockX() < 0 && !Hypersquare.mode.get(event.getPlayer()).equals("editing spawn")){
                event.setCancelled(true);
            }
            if (!blockInPlot(event.getBlockPlaced().getLocation())) {
                event.setCancelled(true);
            }
            return;
        }
        if (!blockInPlot(event.getBlockPlaced().getLocation())) {
            event.setCancelled(true);
        }
        if (event.getBlock().getLocation().getX() > -0.5) {
            return;
        }


        event.setCancelled(true);
        placeCodeTemplate(event, plugin);

        if (event.getBlock().getLocation().getY() < 0 || event.getBlockAgainst().getLocation().getY() < 0 && event.getBlockAgainst().getType() == Material.STONE) {
            return;
        }
        if (event.getBlock().getLocation().add(1, 0, 0).getX() > 0) {
            processPlace(event);
        } else {
            if (event.getBlock().getLocation().add(1, 0, 0).getBlock().getType() != Material.AIR && event.getBlockAgainst().getType() != Material.STONE) {
                Utilities.sendError(event.getPlayer(), "Invalid block placement");
            } else {
                processPlace(event);
            }
        }

    }

    public static boolean blockInPlot(Location location) {
        String plotType = location.getWorld().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "plotType"), PersistentDataType.STRING);
        RestrictMovement.commonVars(location);
        boolean go = true;
        switch (plotType) {
            case "Basic":
                if (!Utilities.locationWithin(location, RestrictMovement.commonStart, RestrictMovement.basic.clone().add(-1,0,-1))) {
                    go = false;
                }
                break;
            case "Large":
                if (!Utilities.locationWithin(location, RestrictMovement.commonStart, RestrictMovement.large.clone().add(-1,0,-1))) {
                    go = false;
                }
                break;
            case "Huge":
                if (!Utilities.locationWithin(location, RestrictMovement.commonStart, RestrictMovement.huge.clone().add(-1,0,-1))) {
                    go = false;
                }
                break;
            case "Massive":
                if (!Utilities.locationWithin(location, RestrictMovement.commonStart, RestrictMovement.massive.clone().add(-1,0,-1))) {
                    go = false;
                }
                break;
            case "Gigantic":
                if (!Utilities.locationWithin(location, RestrictMovement.commonStart, RestrictMovement.gigantic.clone().add(-1,0,-1))) {
                    go = false;
                }
                break;
        }
        return go;
    }


    public void processPlace(BlockPlaceEvent event) {
        boolean brackets = CodeBlocks.getByMaterial(event.getItemInHand().getType()).isBrackets();
        boolean chest = CodeBlocks.getByMaterial(event.getItemInHand().getType()).isChest();
        String name = CodeBlocks.getByMaterial(event.getItemInHand().getType()).getName();
        if (event.getBlock().getLocation().add(-1, 0, 0).getX() < 0) {
            Location location = event.getBlock().getLocation();
            Location againstLocation = event.getBlockAgainst().getLocation();
            int size = brackets ? 4 : 2;

            if (CodeBlockManagement.findCodelineStartLoc(location.clone()) == null) {
                if (event.getBlockAgainst().getType() == Material.STONE || (event.getBlockAgainst().getType() == Material.PISTON && againstLocation.getY() >= 0)) {

                } else {
                    Utilities.sendError(event.getPlayer(), "Your code must start with an Event, Function, Process or Entity event.");
                    return;
                }
            }
            if (location.clone().add(0, 0, 2).getBlock().getType() == Material.PISTON || location.clone().add(0, 0, 2).getBlock().getType() == Material.STICKY_PISTON) {
                CodeBlockManagement.moveCodeLine(location.clone().add(0, 0, 2), 1);
            }
            if (event.getBlockAgainst().getType() == Material.STONE || (event.getBlockAgainst().getType() == Material.PISTON && againstLocation.getY() >= 0)) {
                if (event.getBlockAgainst().getLocation().add(0,0,2).getBlock().getType() == Material.AIR) {
                    size = size/2;
                }
                CodeBlockManagement.moveCodeLine(againstLocation.clone().add(0, 0, 1), size);
                Vector difference = againstLocation.toVector().subtract(event.getBlock().getLocation().toVector());
                location.add(difference).add(0, 0, 1);
            } else {
                if (event.getBlock().getLocation().getBlockY() % 6 == 0) {
                    CodeBlockManagement.moveCodeLine(event.getBlock().getLocation().clone().add(0, 0, 1), 1);
                }
            }

            if (location.getY() >= 0) {
                if (location.clone().getBlockY() % 6 == 0) {
                    placeBlock(event.getItemInHand(), location, brackets, chest, name);
                }

            }
        }
    }

    private static void placeBlock(ItemStack codeblockItem, Location location, boolean brackets, boolean chest, String name) {
        Location signLocation = location.clone().add(-1, 0, 0);

        signLocation.getBlock().setType(Material.OAK_WALL_SIGN);
        BlockData blockData = signLocation.getBlock().getBlockData();
        ((Directional) blockData).setFacing(BlockFace.WEST);
        Sign sign = (Sign) signLocation.getBlock().getState();
        sign.setEditable(true);
        sign.getSide(Side.FRONT).setLine(0, name);
        sign.update();
        signLocation.getBlock().setBlockData(blockData);


        new BukkitRunnable() {
            @Override
            public void run() {
                Location codeblockLocation = location.clone();
                codeblockLocation.getBlock().setType(codeblockItem.getType());

                Location stoneLocation = location.clone().add(0, 0, 1);
                Location closeBracketLocation = location.clone().add(0, 0, 3);
                Location chestLocation = location.add(0, 1, 0);
                Location openBracketLocation = location.clone().add(0, -1, 1);

                if (brackets) {
                    // Open Bracket
                    openBracketLocation.getBlock().setType(Material.PISTON);
                    BlockData pistonData = openBracketLocation.getBlock().getBlockData();
                    ((Directional) pistonData).setFacing(BlockFace.SOUTH);
                    openBracketLocation.getBlock().setBlockData(pistonData);

                    // Close bracket
                    CodeBlockManagement.moveCodeLine(closeBracketLocation,1);
                    closeBracketLocation.getBlock().setType(Material.PISTON);
                    pistonData = closeBracketLocation.getBlock().getBlockData();
                    ((Directional) pistonData).setFacing(BlockFace.NORTH);
                    closeBracketLocation.getBlock().setBlockData(pistonData);
                } else {
                    // Stone Separator
                    stoneLocation.getBlock().setType(Material.STONE);
                }

                if (chest) {
                    chestLocation.getBlock().setType(Material.CHEST);
                    BlockData chestBlockData = chestLocation.getBlock().getBlockData();
                    ((Directional) chestBlockData).setFacing(BlockFace.WEST);
                    chestLocation.getBlock().setBlockData(chestBlockData);
                }
            }
        }.runTaskLater(plugin, 1);
    }


    public static void placeCodeTemplate(BlockPlaceEvent event, Plugin plugin) {
        if (event.getItemInHand().getType() == Material.ENDER_CHEST) {
            String data = LoadCodeTemplate.load(event.getItemInHand());
            JsonArray blocksArray = JsonParser.parseString(Utilities.decode(data)).getAsJsonObject().getAsJsonArray("blocks");

            new BukkitRunnable() {
                @Override
                public void run() {
                    int move = 0;
                    Location location = event.getBlock().getLocation();
                    Location loc = location;

                    for (JsonElement blockElement : blocksArray) {
                        JsonObject blockObject = blockElement.getAsJsonObject();

                        if (blockObject.has("direct")) {
                            String bracket = blockObject.get("direct").getAsString();

                            if (bracket.equals("close")) {
                                move++;
                            }

                            CodeBlockManagement.placeBracket(loc.clone().add(0, 0, move), "bracket", bracket, "");
                            loc = location;
                            move++;
                        }

                        if (blockObject.has("block")) {
                            String id = blockObject.get("block").getAsString();
                            boolean brackets = CodeBlocks.getById(id).isBrackets();
                            boolean chest = CodeBlocks.getById(id).isChest();
                            placeBlock(event.getItemInHand(), loc, brackets, chest, CodeBlocks.getById(id).getName());
                        }
                    }
                }
            }.runTaskLater(plugin, 1);
        }
    }


    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {

        if (!Hypersquare.mode.get(event.getPlayer()).equals("coding")) {
            if (event.getBlock().getLocation().getBlockX() < 0 && !Hypersquare.mode.get(event.getPlayer()).equals("editing spawn")){
                event.setCancelled(true);
            }
            return;
        }

        if (event.getBlock().getLocation().getX() > -0.5) {
            return;
        }

        event.setCancelled(true);
        Location blockLoc = event.getBlock().getLocation();

        if (blockLoc.getY() < 0) {
            return;
        }
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) {
            if (event.getPlayer().getInventory().getItemInMainHand().equals(CodeItems.GLITCH_STICK_ITEM)) {
                if (event.getBlock().getType() == Material.DIAMOND_BLOCK) {
                    PlotDatabase.removeEventByKey(Utilities.getPlotID(event.getBlock().getWorld()), Utilities.LocationToString(event.getBlock().getLocation()));
                }
                event.setCancelled(false);
            } else {
                if (blockLoc.getBlock().getType() == Material.OAK_WALL_SIGN) {
                    blockLoc.add(1, 0, 0);
                }
                Block block = blockLoc.getBlock();
                Block signBlock = blockLoc.clone().add(-1, 0, 0).getBlock();
                if (block.getType() == Material.DIAMOND_BLOCK) {
                    PlotDatabase.removeEventByKey(Utilities.getPlotID(event.getBlock().getWorld()), Utilities.LocationToString(block.getLocation()));
                }

                if (signBlock.getType() == Material.OAK_WALL_SIGN) {
                    Location signLoc = signBlock.getLocation();
                    Location chestLoc = blockLoc.clone().add(0, 1, 0);
                    Location stoneLoc = blockLoc.clone().add(0, 0, 1);

                    Location bracketLoc = CodeBlockManagement.findCorrespBracket(blockLoc.clone());
                    signBlock.setType(Material.AIR);
                    block.setType(Material.AIR);
                    if (stoneLoc.getBlock().getType() == Material.PISTON || stoneLoc.getBlock().getType() == Material.STICKY_PISTON) {
                        bracketLoc.getBlock().setType(Material.AIR);
                    }
                    stoneLoc.getBlock().setType(Material.AIR);
                    chestLoc.getBlock().setType(Material.AIR);

                    if (bracketLoc != null) {
                        CodeBlockManagement.moveCodeLine(blockLoc.clone().add(0, 0, 2), -2);
                    } else {
                        CodeBlockManagement.moveCodeLine(blockLoc.clone().add(0, 0, 2), -2);
                    }
                }
            }
        } else {
            if (blockLoc.getBlock().getType() == Material.OAK_WALL_SIGN) {
                blockLoc.add(1, 0, 0);
            }
            Block block = blockLoc.getBlock();
            Block signBlock = blockLoc.clone().add(-1, 0, 0).getBlock();
            if (block.getType() == Material.DIAMOND_BLOCK) {
                PlotDatabase.removeEventByKey(Utilities.getPlotID(event.getBlock().getWorld()), Utilities.LocationToString(block.getLocation()));
            }

            if (signBlock.getType() == Material.OAK_WALL_SIGN) {
                Location signLoc = signBlock.getLocation();
                Location chestLoc = blockLoc.clone().add(0, 1, 0);
                Location stoneLoc = blockLoc.clone().add(0, 0, 1);

                Location bracketLoc = CodeBlockManagement.findCorrespBracket(blockLoc.clone());
                signBlock.setType(Material.AIR);
                block.setType(Material.AIR);
                if (stoneLoc.getBlock().getType() == Material.PISTON || stoneLoc.getBlock().getType() == Material.STICKY_PISTON) {
                    bracketLoc.getBlock().setType(Material.AIR);
                }
                stoneLoc.getBlock().setType(Material.AIR);
                chestLoc.getBlock().setType(Material.AIR);

                if (bracketLoc != null) {
                    CodeBlockManagement.moveCodeLine(blockLoc.clone().add(0, 0, 2), -2);
                } else {
                    CodeBlockManagement.moveCodeLine(blockLoc.clone().add(0, 0, 2), -2);
                }
            }
        }


    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) {
            return;
        }
        if (!Hypersquare.mode.get(event.getPlayer()).equals("coding")) {
            if (event.getClickedBlock().getLocation().getBlockX() < 0 && !Hypersquare.mode.get(event.getPlayer()).equals("editing spawn")){
                event.setCancelled(true);
            }
            return;
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (event.getClickedBlock().getLocation().getX() > -0) {
                return;
            }

            if (event.getClickedBlock().getType() == Material.OAK_WALL_SIGN) {
                event.setCancelled(true);
                Sign sign = (Sign) event.getClickedBlock().getState();
                sign.getSide(Side.FRONT).getLine(1);
                switch (sign.getSide(Side.FRONT).getLine(0)){
                    case ("PLAYER EVENT") :{
                        PlayerEventMenu.create().open(event.getPlayer());
                        break;
                    }
                    case ("PLAYER ACTION") :{
                        PlayerActionMenu.create().open(event.getPlayer());
                        break;
                    }
                }
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1, 1.75f);
            } else {
                if (CodeBlocks.getByMaterial(event.getItem().getType()) == null)
                    event.setCancelled(true);
            }
        }

    }
    @EventHandler
    public void onExplode(EntityExplodeEvent event){
        event.setCancelled(true);
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if (!Hypersquare.mode.get(event.getDamager()).equals("playing")) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onExplode(BlockExplodeEvent event){
        event.setCancelled(true);
    }

    public static Location basic = null;
    public static Location large = null;
    public static Location huge = null;
    public static Location massive = null;
    public static Location gigantic = null;
    public static Location commonStart = null;

    public static void commonVars(Location location) {
        basic = new Location(location.getWorld(), 64, -640, 64);
        large = new Location(location.getWorld(), 128, -640, 128);
        huge = new Location(location.getWorld(), 256, -640, 256);
        massive = new Location(location.getWorld(), 512, -640, 512);
        gigantic = new Location(location.getWorld(), 1024, -640, 1024);
        commonStart = new Location(location.getWorld(), 0, 255, 0);
    }
    @EventHandler
    public void onSpread(BlockFromToEvent event){
        commonVars(event.getToBlock().getLocation());
        String plotType = event.getToBlock().getWorld().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"plotType"), PersistentDataType.STRING);

        switch (plotType){

            case "Basic" : {
                if (!Utilities.locationWithin(event.getToBlock().getLocation(),commonStart,basic)) {
                    event.setCancelled(true);
                }
                break;
            }
            case "Large" : {
                if (!Utilities.locationWithin(event.getToBlock().getLocation(),commonStart,large)) {
                    event.setCancelled(true);

                }
                break;
            }
            case "Huge" : {
                if (!Utilities.locationWithin(event.getToBlock().getLocation(),commonStart,huge)) {
                    event.setCancelled(true);
                }
                break;
            }
            case "Massive" : {
                if (!Utilities.locationWithin(event.getToBlock().getLocation(),commonStart,massive)) {
                    event.setCancelled(true);
                }
                break;
            }
            case "Gigantic" : {
                if (!Utilities.locationWithin(event.getToBlock().getLocation(),commonStart,gigantic)) {
                    event.setCancelled(true);
                }
                break;
            }
        }
    }
    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void creatureSpawnEvent(CreatureSpawnEvent event) {
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL ||
                event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.COMMAND
        ){
         event.setCancelled(true);
        }
    }
}
