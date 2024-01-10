package hypersquare.hypersquare.listener;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.blocks.Blocks;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.CodeBlocks;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.dev.codefile.CodeFileHelper;
import hypersquare.hypersquare.plot.CodeBlockManagement;
import hypersquare.hypersquare.plot.LoadCodeTemplate;
import hypersquare.hypersquare.plot.PlotDatabase;
import hypersquare.hypersquare.plot.RestrictMovement;
import hypersquare.hypersquare.util.Utilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class CodePlacement implements Listener {
    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        if (!Hypersquare.mode.get(event.getPlayer()).equals("coding")) {
            if (!Hypersquare.mode.get(event.getPlayer()).equals("editing spawn")){
                event.setCancelled(true);
            }
            if (!blockInPlot(event.getBlockPlaced().getLocation())) {
                event.setCancelled(true);
            }
            return;
        }
        event.setCancelled(true);

        if (event.getBlock().getLocation().add(1, 0, 0).getX() > 0) {
            processPlace(event);
        } else {
            if (event.getBlock().getLocation().add(1, 0, 0).getBlock().getType() != Material.AIR && !checkIfValidAgainst(event.getBlockAgainst())) {
                Utilities.sendError(event.getPlayer(), "Invalid block placement");
            } else {
                if (event.getBlock().getLocation().add(-1, 0, 0).getBlock().getType() != Material.AIR && !checkIfValidAgainst(event.getBlockAgainst())) {
                    Utilities.sendError(event.getPlayer(), "Invalid block placement");
                } else {
                    processPlace(event);
                }
            }
        }

    }

    private static boolean checkIfValidAgainst(Block againstLocation){
        Material type = againstLocation.getType();
        return type == Material.STONE || type == Material.PISTON || type == Material.STICKY_PISTON;
    }

    public static boolean blockInPlot(Location location) {
        String plotType = location.getWorld().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.instance, "plotType"), PersistentDataType.STRING);
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
        long cooldown = Hypersquare.cooldownMap.get(event.getPlayer().getUniqueId()) == null ? 0 : Hypersquare.cooldownMap.get(event.getPlayer().getUniqueId());
        event.setCancelled(true);
        if (cooldown <= System.currentTimeMillis()) {
            Hypersquare.cooldownMap.put(event.getPlayer().getUniqueId(),System.currentTimeMillis()+150);
            CodeBlocks codeblock = CodeBlocks.getByMaterial(event.getItemInHand().getType());
            boolean brackets = codeblock.hasBrackets();
            boolean chest = codeblock.hasChest();
            boolean threadStarter = codeblock.isThreadStarter();
            String name = CodeBlocks.getByMaterial(event.getItemInHand().getType()).getName();
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (event.getBlock().getLocation().getBlockX() < 0) {
                        Location againstLocation = event.getBlockAgainst().getLocation();
                        Block againstBlock = event.getBlockAgainst();
                        Location location = event.getBlock().getLocation();
                        Player player = event.getPlayer();

                        int size = brackets ? 3 : 2;
                        if (checkIfValidAgainst(againstBlock)) {
                            location = event.getBlockAgainst().getLocation().clone().add(0, 0, 1);
                        }

                        // Invalid placements
                        if (location.getBlockY() % 6 != 0 ||            // Codeblock on top of codeblock
                                location.getBlockX() % 3 != 0 ||        // Codelines not spaced 3 blocks apart
                                (location.getBlockZ() + 1) % 2 != 0     // Codeblocks not spaced 2 blocks apart
                        ) {
                            Utilities.sendError(player, "Invalid block placement.");
                            return;
                        } else if (threadStarter && location.getBlockZ() != 1) {
                            // Thread starter not at the very start
                            Utilities.sendError(player, "Events, Functions, and Processes must be placed at the very start of the code line.");
                            return;
                        }
                        JsonArray code = new CodeFile(event.getPlayer()).getCodeJson();
                        if (!threadStarter) {
                            try {
                                code.get(location.getBlockX() % 3);
                            } catch (IndexOutOfBoundsException e) {
                                // If we error while getting the codeblock, it means there is no thread starter.
                                Utilities.sendError(player, "Your code must start with an Event, Function, or Process.");
                                return;
                            }
                        }

                        if (!blockInPlot(CodeBlockManagement.findCodeEnd(location.clone()).add(0,0, size))) {
                            Utilities.sendError(player, "Your code has reached the end of the plot.");
                            return;
                        }

                        CodeBlockManagement.moveCodeLine(location.clone(), size);
                        placeBlock(event.getItemInHand(), location, brackets, chest, name);
                    }
                }
            }.runTaskLater(Hypersquare.instance,2);
        }
    }

    private static void placeBlock(ItemStack codeblockItem, Location location, boolean brackets, boolean chest, String name) {
        Location signLocation = location.clone().add(-1, 0, 0);

        CodeFile code = new CodeFile(location.getWorld());
        JsonArray codeJson = CodeFileHelper.addCodeblock(location.clone(), name, CodeFileHelper.getCodeblockIndex(location), code);
        code.setCode(codeJson.toString());

        if (name != null) { // Either invalid or empty codeblock
            signLocation.getBlock().setType(Material.OAK_WALL_SIGN);
            BlockData blockData = signLocation.getBlock().getBlockData();
            ((Directional) blockData).setFacing(BlockFace.WEST);
            Sign sign = (Sign) signLocation.getBlock().getState();
            sign.setEditable(true);
            sign.getSide(Side.FRONT).setLine(0, name);
            sign.update();
            signLocation.getBlock().setBlockData(blockData);
        }


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
                    // Stone Separator, skip if empty codeblock
                    if (!Objects.equals(name, "empty")) stoneLocation.getBlock().setType(Material.STONE);
                    if (stoneLocation.clone().add(0,0,1).getBlock().getType() == Material.PISTON ||stoneLocation.clone().add(0,0,1).getBlock().getType() == Material.STICKY_PISTON){
                        CodeBlockManagement.moveCodeLine(stoneLocation.clone().add(0,0,1),1);
                    }
                }

                if (chest) {
                    chestLocation.getBlock().setType(Material.CHEST);
                    BlockData chestBlockData = chestLocation.getBlock().getBlockData();
                    ((Directional) chestBlockData).setFacing(BlockFace.WEST);
                    chestLocation.getBlock().setBlockData(chestBlockData);
                }
            }
        }.runTaskLater(Hypersquare.instance, 1);
    }

    /**
        * @deprecated TODO: Use new CodeFile system
     **/
    @Deprecated
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
                            boolean brackets = CodeBlocks.getById(id).hasBrackets();
                            boolean chest = CodeBlocks.getById(id).hasChest();
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
            if (!Hypersquare.mode.get(event.getPlayer()).equals("editing spawn")){
                event.setCancelled(true);
            }
            return;
        }
        event.setCancelled(true);

        if (event.getBlock().getLocation().getX() > -0.5) {
            return;
        }

        Location blockLoc = event.getBlock().getLocation();

        if (blockLoc.getY() < 0) {
            return;
        }

        long cooldown = Hypersquare.cooldownMap.get(event.getPlayer().getUniqueId()) == null ? 0 : Hypersquare.cooldownMap.get(event.getPlayer().getUniqueId());
        if (cooldown <= System.currentTimeMillis()) {
            Hypersquare.cooldownMap.put(event.getPlayer().getUniqueId(), System.currentTimeMillis() + 150);

            if (blockLoc.getBlock().getType() == Material.OAK_WALL_SIGN) {
                blockLoc.add(1, 0, 0);
            }
            Block block = blockLoc.getBlock();
            Block signBlock = blockLoc.clone().add(-1, 0, 0).getBlock();
            if (block.getType() == Material.DIAMOND_BLOCK) {
                PlotDatabase.removeEventByKey(Utilities.getPlotID(event.getBlock().getWorld()), Utilities.LocationToString(block.getLocation()));
            }

            if (CodeBlocks.getByMaterial(block.getType()).isThreadStarter()) {
                Location endLoc = CodeBlockManagement.findCodeEnd(blockLoc.clone());
                BlockVector3 loc1 = BlockVector3.at(blockLoc.getBlockX() + 1, blockLoc.getBlockY(), blockLoc.getBlockZ());
                BlockVector3 loc2 = BlockVector3.at(endLoc.getBlockX(), endLoc.getBlockY() + 1, endLoc.getBlockZ());
                // Create the region based on the two locations
                Region selection = new CuboidRegion(loc1, loc2);
                // Create a FAWE world based on the world of the blockLoc
                World faweWorld = BukkitAdapter.adapt(blockLoc.getWorld());
                // Create an EditSession that auto-closes where we set all the blocks in the region to air
                try (EditSession editSession = WorldEdit.getInstance().newEditSession(faweWorld)) {
                    editSession.setBlocks(selection, BukkitAdapter.asBlockState(new ItemStack(Material.AIR)));
                }
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
                    CodeBlockManagement.moveCodeLine(bracketLoc.clone().add(0, 0, 1), -2);
                    CodeBlockManagement.moveCodeLine(blockLoc.clone().add(0, 0, 2), -2);
                } else {
                    CodeBlockManagement.moveCodeLine(blockLoc.clone().add(0, 0, 2), -2);
                }
            }
            CodeFile code = new CodeFile(blockLoc.getWorld());
            JsonArray codeJson = CodeFileHelper.removeCodeBlock(blockLoc.clone(), code);
            code.setCode(codeJson.toString());
        }
    }
}
