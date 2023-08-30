package hypersquare.hypersquare.Listeners;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hypersquare.hypersquare.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Locale;

public class PlayerPlaceBlockListener implements Listener {
    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        if (Hypersquare.mode.get(event.getPlayer()).equals("coding")) {
            event.setCancelled(true);
            if (event.getItemInHand().getType() == Material.ENDER_CHEST) {
                String data = LoadCodeTemplate.load(event.getItemInHand());
                JsonArray blocksArray = JsonParser.parseString(Utilities.decode(data)).getAsJsonObject().getAsJsonArray("blocks");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        int move = 0;
                        Location location = event.getBlock().getLocation();
                        Location loc = location;
                        event.getPlayer().sendMessage(location + "");
                        for (JsonElement blockElement : blocksArray) {
                            JsonObject blockObject = blockElement.getAsJsonObject();
                            if (blockObject.has("direct")){
                                String bracket = blockObject.get("direct").getAsString();
                                if (bracket.equals("close"))
                                    move++;
                                CodeBlockManagement.placeCodeBlock(loc.add(0,0,move),"bracket",bracket,"");
                                loc = event.getBlock().getLocation();
                                move++;
                            }
                            if (blockObject.has("block")) {
                                event.getPlayer().sendMessage(move + "");
                                String id = blockObject.get("block").getAsString();
                                ItemStack item = ItemManager.getItem("dev." + id);
                                if (!item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"brackets"),PersistentDataType.STRING).equals("true")) {

                                    JsonElement action = blockObject.get("data");
                                    if (action == null)
                                        action = blockObject.get("action");


                                    CodeBlockManagement.placeCodeBlock(loc.add(0, 0, move), id, "stone",action.getAsString());
                                    loc = event.getBlock().getLocation();;
                                    move+=2;

                                } else {
                                    event.getPlayer().sendMessage("if_player" + move);
                                    CodeBlockManagement.placeCodeBlock(loc.add(0, 0, move), id, "none",blockObject.get("action").getAsString());
                                    loc = event.getBlock().getLocation();;
                                    move++;
                                }


                            }
                            event.getPlayer().sendMessage(move + " " + loc + "");
                        }
                    }
                }.runTaskLater(Hypersquare.getPlugin(Hypersquare.class),1);



                event.getPlayer().sendMessage(Utilities.decode(data));
            }
            event.getPlayer().sendMessage(ItemManager.getItemID(event.getItemInHand()));
            if (ItemManager.getItemID(event.getItemInHand()).startsWith("dev.")) {
                Location location = event.getBlock().getLocation();

                if (event.getBlockAgainst().getType() == Material.STONE){
                    CodeBlockManagement.moveCodeLine(event.getBlockAgainst().getLocation().add(0,0,1), 2);
                    Vector against = event.getBlockAgainst().getLocation().toVector();
                    Vector original = event.getBlock().getLocation().toVector();
                    Vector difference = against.subtract(original);
                    location.add(difference);
                    location.add(0,0,1);
                }


                Location signLocation = location.clone().add(-1,0,0);

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
                        Location stoneLocation = location.clone().add(0,0,1);
                        if (event.getItemInHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), PersistentDataType.STRING).equals("true")) {

                            //Open Bracket
                            Location openBracketLocation = location.clone().add(0,0,1);
                            openBracketLocation.getBlock().setType(Material.PISTON);
                            BlockData pistonData = openBracketLocation.getBlock().getBlockData();
                            ((Directional) pistonData).setFacing(BlockFace.SOUTH);
                            openBracketLocation.getBlock().setBlockData(pistonData);

                            //Close bracket
                            Location closeBracketLocation = location.clone().add(0,0,3);
                            closeBracketLocation.getBlock().setType(Material.PISTON);
                            pistonData = closeBracketLocation.getBlock().getBlockData();
                            ((Directional) pistonData).setFacing(BlockFace.NORTH);
                            closeBracketLocation.getBlock().setBlockData(pistonData);

                        } else
                            //Stone Bracket

                            stoneLocation.getBlock().setType(Material.STONE);
                        if (event.getItemInHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), PersistentDataType.STRING).equals("true")) {
                            Location chestLocation = location.add(0,1,0);
                            chestLocation.getBlock().setType(Material.CHEST);
                            BlockData blockData = chestLocation.getBlock().getBlockData();
                            ((Directional) blockData).setFacing(BlockFace.WEST);
                            chestLocation.getBlock().setBlockData(blockData);

                        }

                    }
                }.runTaskLater(Hypersquare.getPlugin(Hypersquare.class), 1);

            }

        }
    }

}
