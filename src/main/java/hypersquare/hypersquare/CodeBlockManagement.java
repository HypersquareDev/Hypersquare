package hypersquare.hypersquare;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class CodeBlockManagement {
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






    public static String getCodeBlockFromItem(ItemStack itemStack){

        return ItemManager.getItemID(itemStack).replace("dev.", "");
    }
}
