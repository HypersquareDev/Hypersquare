package hypersquare.hypersquare.Listeners;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.ItemManager;
import hypersquare.hypersquare.LoadCodeTemplate;
import hypersquare.hypersquare.Utilities;
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
                                placeCodeBlock(loc.add(0,0,move),"bracket",bracket,blockObject.get("action").getAsString());
                                loc = event.getBlock().getLocation();
                                move++;
                            }
                            if (blockObject.has("block")) {
                                event.getPlayer().sendMessage(move + "");
                                String id = blockObject.get("block").getAsString();
                                if (!id.equals("if_player")) {
                                    placeCodeBlock(loc.add(0, 0, move), id, "stone",blockObject.get("action").getAsString());
                                    loc = event.getBlock().getLocation();;
                                    move+=2;

                                } else {
                                    event.getPlayer().sendMessage("if_player" + move);
                                    placeCodeBlock(loc.add(0, 0, move), id, "none",blockObject.get("action").getAsString());
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
                String signText = ChatColor.stripColor(event.getItemInHand().getItemMeta().getDisplayName().toUpperCase());
                event.getBlock().getLocation().add(-1, 0, 0).getBlock().setType(Material.OAK_WALL_SIGN);
                BlockData blockData = event.getBlock().getLocation().add(-1, 0, 0).getBlock().getBlockData();
                ((Directional) blockData).setFacing(BlockFace.WEST);
                Sign sign = (Sign) event.getBlock().getLocation().add(-1, 0, 0).getBlock().getState();
                sign.setEditable(true);
                sign.getSide(Side.FRONT).setLine(0, signText);
                sign.update();
                event.getBlock().getLocation().add(-1, 0, 0).getBlock().setBlockData(blockData);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        event.getBlock().getLocation().getBlock().setType(event.getItemInHand().getType());
                        if (event.getItemInHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), PersistentDataType.STRING).equals("true")) {
                            event.getBlock().getLocation().add(0, 0, 1).getBlock().setType(Material.PISTON);
                            BlockData pistonData = event.getBlock().getLocation().add(0, 0, 1).getBlock().getBlockData();
                            ((Directional) pistonData).setFacing(BlockFace.SOUTH);
                            event.getBlock().getLocation().add(0, 0, 1).getBlock().setBlockData(pistonData);
                            event.getBlock().getLocation().add(0, 0, 3).getBlock().setType(Material.PISTON);
                            pistonData = event.getBlock().getLocation().add(0, 0, 3).getBlock().getBlockData();
                            ((Directional) pistonData).setFacing(BlockFace.NORTH);
                            event.getBlock().getLocation().add(0, 0, 3).getBlock().setBlockData(pistonData);
                        } else
                            event.getBlock().getLocation().add(0, 0, 1).getBlock().setType(Material.STONE);
                        if (event.getItemInHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), PersistentDataType.STRING).equals("true")) {
                            event.getBlock().getLocation().add(0, 1, 0).getBlock().setType(Material.CHEST);
                            BlockData blockData = event.getBlock().getLocation().add(0, 1, 0).getBlock().getBlockData();
                            ((Directional) blockData).setFacing(BlockFace.WEST);
                            event.getBlock().getLocation().add(0, 1, 0).getBlock().setBlockData(blockData);

                        }

                    }
                }.runTaskLater(Hypersquare.getPlugin(Hypersquare.class), 1);

            }

        }
    }

    public static void placeCodeBlock(Location location, String codeblock, String bracket, String action) {
        Location loc = location.getBlock().getLocation();
        if (!codeblock.equals("bracket")){

            ItemStack item = ItemManager.getItem("dev." + codeblock);

            //Sign
            Location signLocation = location.getBlock().getLocation();
            location.getBlock().setType(ItemManager.getItem("dev." + codeblock).getType());
            String signText = ChatColor.stripColor(item.getItemMeta().getDisplayName().toUpperCase());
            signLocation.add(-1, 0, 0).getBlock().setType(Material.OAK_WALL_SIGN);
            BlockData signData = signLocation.getBlock().getBlockData();
            ((Directional) signData).setFacing(BlockFace.WEST);
            Sign sign = (Sign) signLocation.getBlock().getState();
            sign.setEditable(true);
            sign.getSide(Side.FRONT).setLine(0, signText);
            sign.getSide(Side.FRONT).setLine(1, action);
            sign.update();
            signLocation.getBlock().setBlockData(signData);

            if (item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), PersistentDataType.STRING).equals("true")) {
                loc.add(0, 1, 0).getBlock().setType(Material.CHEST);
                BlockData blockData = loc.getBlock().getBlockData();
                ((Directional) blockData).setFacing(BlockFace.WEST);
                loc.getBlock().setBlockData(blockData);

            }
        }
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
}
