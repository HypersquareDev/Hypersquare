package hypersquare.hypersquare.listeners;

import com.google.gson.*;
import hypersquare.hypersquare.*;
import hypersquare.hypersquare.menus.MyPlotsMenu;
import hypersquare.hypersquare.menus.PlayerEventMenu;
import hypersquare.hypersquare.plot.Database;
import hypersquare.hypersquare.utils.managers.ItemManager;
import hypersquare.hypersquare.plot.CodeBlockManagement;
import hypersquare.hypersquare.plot.LoadCodeTemplate;
import hypersquare.hypersquare.plot.RestrictMovement;
import hypersquare.hypersquare.utils.Utilities;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Objects;

public class DevEvents implements Listener {
    private final Plugin plugin = Hypersquare.getPlugin(Hypersquare.class);

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        String plotType = event.getBlock().getWorld().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"plotType"), PersistentDataType.STRING);
        RestrictMovement.commonVars(event.getBlockPlaced().getLocation());
        boolean go = true;
        switch (plotType){

            case "Basic" : {
                if (!Utilities.locationWithin(event.getBlockPlaced().getLocation(),RestrictMovement.commonStart,RestrictMovement.basic)) {
                    go = false;
                }
            }
            case "Large" : {
                if (!Utilities.locationWithin(event.getBlockPlaced().getLocation(),RestrictMovement.commonStart,RestrictMovement.large)) {
                    go = false;

                }

            }
            case "Massive" : {
                if (!Utilities.locationWithin(event.getBlockPlaced().getLocation(),RestrictMovement.commonStart,RestrictMovement.massive)) {
                    go = false;
                }
            }
            case "Huge" : {
                if (!Utilities.locationWithin(event.getBlockPlaced().getLocation(),RestrictMovement.commonStart,RestrictMovement.huge)) {
                    go = false;
                }
            }
            case "Gigantic" : {
                if (!Utilities.locationWithin(event.getBlockPlaced().getLocation(),RestrictMovement.commonStart,RestrictMovement.massive)) {
                    go = false;
                }
            }
        }
        if (!go)
            event.setCancelled(true);
        if (!Hypersquare.mode.get(event.getPlayer()).equals("coding")) {
            return;
        }
        if (event.getBlock().getLocation().getX() > -0.5){
            return;
        }



        event.setCancelled(true);
        placeCodeTemplate(event, plugin);

        if (event.getBlock().getLocation().getY() < 0 || event.getBlockAgainst().getLocation().getY() < 0 && event.getBlockAgainst().getType() == Material.STONE) {
            return;
        }
        if (event.getBlock().getLocation().add(1,0,0).getX() > 0){
            processPlace(event);
        } else {
            if (event.getBlock().getLocation().add(1,0,0).getBlock().getType() != Material.AIR && event.getBlockAgainst().getType() != Material.STONE)
            {
                Utilities.sendError(event.getPlayer(),"Invalid block placement");
            } else {
                processPlace(event);
            }
        }

    }

    public void processPlace(BlockPlaceEvent event){
        if (event.getBlock().getLocation().add(-1,0,0).getX() < 0) {
            if (ItemManager.getItemID(event.getItemInHand()).startsWith("dev.")) {

                Location location = event.getBlock().getLocation();

                Location againstLocation = event.getBlockAgainst().getLocation();
                int size = event.getItemInHand().getItemMeta().getPersistentDataContainer()
                        .getOrDefault(new NamespacedKey(plugin, "brackets"), PersistentDataType.STRING, "false").equals("true") ? 4 : 2;

                if (event.getBlockAgainst().getType() == Material.STONE || (event.getBlockAgainst().getType() == Material.PISTON && againstLocation.getY() >= 0)) {
                    CodeBlockManagement.moveCodeLine(againstLocation.clone().add(0, 0, 1), size);
                    Vector difference = againstLocation.toVector().subtract(event.getBlock().getLocation().toVector());
                    location.add(difference).add(0, 0, 1);
                } else {
                    CodeBlockManagement.moveCodeLine(event.getBlock().getLocation().clone().add(0, 0, 1), 1);
                }
                if (CodeBlockManagement.findCodelineStartLoc(location.clone()) == null) {
                    Utilities.sendError(event.getPlayer(), "Your code must start with an Event, Function, Process or Entity event.");
                    return;
                }

                if (location.getY() >= 0) {


                    Location signLocation = location.clone().add(-1, 0, 0);
                    String signText = ChatColor.stripColor(event.getItemInHand().getItemMeta().getDisplayName().toUpperCase());

                    signLocation.getBlock().setType(Material.OAK_WALL_SIGN);
                    BlockData blockData = signLocation.getBlock().getBlockData();
                    ((Directional) blockData).setFacing(BlockFace.WEST);
                    Sign sign = (Sign) signLocation.getBlock().getState();
                    sign.setEditable(true);
                    sign.getSide(Side.FRONT).setLine(0, signText);
                    sign.update();
                    signLocation.getBlock().setBlockData(blockData);



                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Location codeblockLocation = location.clone();
                            codeblockLocation.getBlock().setType(event.getItemInHand().getType());

                            Location stoneLocation = location.clone().add(0, 0, 1);
                            Location closeBracketLocation = location.clone().add(0, 0, 3);
                            Location chestLocation = location.add(0, 1, 0);
                            Location openBracketLocation = location.clone().add(0, -1, 1);

                            if (event.getItemInHand().getItemMeta().getPersistentDataContainer()
                                    .getOrDefault(new NamespacedKey(plugin, "brackets"), PersistentDataType.STRING, "false").equals("true")) {
                                // Open Bracket
                                openBracketLocation.getBlock().setType(Material.PISTON);
                                BlockData pistonData = openBracketLocation.getBlock().getBlockData();
                                ((Directional) pistonData).setFacing(BlockFace.SOUTH);
                                openBracketLocation.getBlock().setBlockData(pistonData);

                                // Close bracket
                                closeBracketLocation.getBlock().setType(Material.PISTON);
                                pistonData = closeBracketLocation.getBlock().getBlockData();
                                ((Directional) pistonData).setFacing(BlockFace.NORTH);
                                closeBracketLocation.getBlock().setBlockData(pistonData);
                            } else {
                                // Stone Bracket
                                stoneLocation.getBlock().setType(Material.STONE);
                            }

                            if (event.getItemInHand().getItemMeta().getPersistentDataContainer()
                                    .getOrDefault(new NamespacedKey(plugin, "chest"), PersistentDataType.STRING, "false").equals("true")) {
                                chestLocation.getBlock().setType(Material.CHEST);
                                BlockData chestBlockData = chestLocation.getBlock().getBlockData();
                                ((Directional) chestBlockData).setFacing(BlockFace.WEST);
                                chestLocation.getBlock().setBlockData(chestBlockData);
                            }
                        }
                    }.runTaskLater(plugin, 1);

                }
            }
        }
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

                            CodeBlockManagement.placeCodeBlock(loc.clone().add(0, 0, move), "bracket", bracket, "");
                            loc = location;
                            move++;
                        }

                        if (blockObject.has("block")) {
                            String id = blockObject.get("block").getAsString();
                            ItemStack item = ItemManager.getItem("dev." + id);

                            if (!item.getItemMeta().getPersistentDataContainer()
                                    .getOrDefault(new NamespacedKey(plugin, "brackets"), PersistentDataType.STRING, "false")
                                    .equals("true")) {

                                JsonElement action = blockObject.get("data");
                                if (action == null) {
                                    action = blockObject.get("action");
                                }

                                CodeBlockManagement.placeCodeBlock(loc.clone().add(0, 0, move), id, "stone", action.getAsString());
                                loc = location;
                                move += 2;
                            } else {
                                CodeBlockManagement.placeCodeBlock(loc.clone().add(0, 0, move), id, "none",
                                        blockObject.get("action").getAsString());
                                loc = location;
                                move++;
                            }
                        }
                    }
                }
            }.runTaskLater(plugin, 1);
        }
    }



    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        if (!Hypersquare.mode.get(event.getPlayer()).equals("coding")) {
            return;
        }

        if (event.getBlock().getLocation().getX() > -0.5){
            return;
        }

        event.setCancelled(true);
        Location blockLoc = event.getBlock().getLocation();

        if (blockLoc.getY() < 0) {
            return;
        }
            if (Objects.equals(ItemManager.getItemID(event.getPlayer().getInventory().getItemInMainHand()), "dev.glitch")) {
                if (event.getBlock().getType() == Material.DIAMOND_BLOCK){
                    Database.removeEventByKey(Utilities.getPlotID(event.getBlock().getWorld()),Utilities.LocationToString(event.getBlock().getLocation()));
                }
                event.setCancelled(false);
            } else {
                if (blockLoc.getBlock().getType() == Material.OAK_WALL_SIGN) {
                    blockLoc.add(1, 0, 0);
                }
                Block block = blockLoc.getBlock();
                Block signBlock = blockLoc.clone().add(-1, 0, 0).getBlock();
                if (block.getType() == Material.DIAMOND_BLOCK){
                    Database.removeEventByKey(Utilities.getPlotID(event.getBlock().getWorld()),Utilities.LocationToString(block.getLocation()));
                }

                if (signBlock.getType() == Material.OAK_WALL_SIGN) {
                    Location signLoc = signBlock.getLocation();
                    Location chestLoc = blockLoc.clone().add(0, 1, 0);
                    Location stoneLoc = blockLoc.clone().add(0, 0, 1);

                    Location bracketLoc = CodeBlockManagement.findCorrespBracket(blockLoc.clone());
                    signBlock.setType(Material.AIR);
                    block.setType(Material.AIR);
                    stoneLoc.getBlock().setType(Material.AIR);
                    chestLoc.getBlock().setType(Material.AIR);

                    if (bracketLoc != null) {
                        bracketLoc.getBlock().setType(Material.AIR);
                        CodeBlockManagement.moveCodeLine(bracketLoc.clone().add(0, 0, 1), -1);
                        CodeBlockManagement.moveCodeLine(blockLoc.clone().add(0, 0, 2), -2);
                    } else {
                        CodeBlockManagement.moveCodeLine(blockLoc.clone().add(0, 0, 2), -2);
                    }
                }



        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!Hypersquare.mode.get(event.getPlayer()).equals("coding")) {
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
                        new PlayerEventMenu(event.getPlayer()).open();
                    }
                }
            }
        }

    }
}
