package hypersquare.hypersquare.util;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.google.gson.JsonParser;
import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.plot.PlotDatabase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import static hypersquare.hypersquare.Hypersquare.mm;

public class Utilities {
    static SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");

    public static int getPlotID(World world){
        String name = world.getName();
        if (name.contains("hs.")) {
            name = name.replace("hs.", "");
            int plotID = Integer.parseInt(name);
            return plotID;
        }
        return 0;
    }




    public static String decode(String data) {
        try {
            // First, decode Base64
            byte[] decodedBytes = Base64.getDecoder().decode(data);

            // Then, decompress GZIP
            InputStream inputStream = new ByteArrayInputStream(decodedBytes);
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return new String(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately.
            return null; // Or any other error handling mechanism you prefer.
        }
    }
    @Deprecated
    public static void sendMultiMessage(Player recipient, List<String> messages)
    {
        for (String message : messages)
        {
            message = convertToChatColor(message);
            recipient.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', message));
        }
    }


    public static int findLengthOfLongestString(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return 0; // Return 0 if the list is null or empty
        }


        int maxLength = PlainTextComponentSerializer.plainText().serialize(mm.deserialize(strings.get(0))).length();  // Initialize maxLength to the length of the first string

        for (String s : strings) {
            if (PlainTextComponentSerializer.plainText().serialize(mm.deserialize(s)).length() > maxLength) {
                maxLength = PlainTextComponentSerializer.plainText().serialize(mm.deserialize(s)).length();; // Update maxLength if a longer string is found
            }
        }

        return maxLength;
    }

    public static void sendMultiMiniMessage(Player recipient, List<String> messages)
    {
        int size = findLengthOfLongestString(messages);
        String before = "<#AAAAFF><strikethrough>" + " ".repeat((int) (size*1.5)) + "<#AAAAFF>";
        String message = String.join("<newline><reset>",messages);
        message = before + "<newline><reset>" + message + "<newline>" + before;
        recipient.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }

    public static String convertToChatColor(String input) {
        Pattern pattern = Pattern.compile("#[0-9A-Fa-f]{6}");
        Matcher matcher = pattern.matcher(input);
        StringBuilder formattedString = new StringBuilder();
        int prevEnd = 0;
        while (matcher.find()) {
            String colorCode = matcher.group();
            String chatColor = ChatColor.of(colorCode).toString();
            formattedString.append(input.substring(prevEnd, matcher.start()));
            formattedString.append(chatColor);
            prevEnd = matcher.end();
        }
        formattedString.append(input.substring(prevEnd));
        return formattedString.toString();
    }

    public static ItemStack formatItem(String lore, Material material, String name) {
        String[] parts = lore.split("%n");
        List<Component> list = new ArrayList<>(List.of());
        for (String part : parts) {
            list.add(mm.deserialize(part));
        }

        return new ItemBuilder(material)
                .name(mm.deserialize(name))
                .lore(list)
                .hideFlags()
                .build();
    }

    public static final String capitalize(String str)
    {
        if (str == null || str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static void sendError(Player player, String message){
        player.sendMessage(mm.deserialize("<red>Error: <gray>" + message));
        player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_HURT_CLOSED,1,1);
    }

    public static void sendInfo(Player player, String message){
        player.sendMessage(mm.deserialize("<b><green>»</b> <gray>" + message));
    }

    public static void sendRedInfo(Player player, String message){
        player.sendMessage(mm.deserialize("<b><red>»</b> <gray>" + message));
    }

    public static void sendOpenMenuSound(Player player){
        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_OFF, 1, 2);
    }

    public static void sendClickMenuSound(Player player){
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1f, 1.5f);
    }

    public static void sendSecondaryMenuSound(Player player){
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
    }

    public static void sendSuccessClickMenuSound(Player player){
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
    }
    public static boolean locationWithin(Location targetLocation, Location location1, Location location2) {
        return targetLocation.getWorld().equals(location1.getWorld()) &&
                targetLocation.getWorld().equals(location2.getWorld()) &&
                targetLocation.getX() >= Math.min(location1.getX(), location2.getX()) &&
                targetLocation.getX() <= Math.max(location1.getX(), location2.getX()) &&
                targetLocation.getY() >= Math.min(location1.getY(), location2.getY()) &&
                targetLocation.getY() <= Math.max(location1.getY(), location2.getY()) &&
                targetLocation.getZ() >= Math.min(location1.getZ(), location2.getZ()) &&
                targetLocation.getZ() <= Math.max(location1.getZ(), location2.getZ());
    }

    public static boolean locationWithinIgnoreY(Location targetLocation, Location location1, Location location2) {
        return targetLocation.getWorld().equals(location1.getWorld()) &&
                targetLocation.getWorld().equals(location2.getWorld()) &&
                targetLocation.getX() >= Math.min(location1.getX(), location2.getX()) &&
                targetLocation.getX() <= Math.max(location1.getX(), location2.getX()) &&
                targetLocation.getZ() >= Math.min(location1.getZ(), location2.getZ()) &&
                targetLocation.getZ() <= Math.max(location1.getZ(), location2.getZ());
    }
    public static int findIndex(List<List<Integer>> listOfLists, int targetNumber) {
        for (int i = 0; i < listOfLists.size(); i++) {
            List<Integer> innerList = listOfLists.get(i);
            if (!innerList.isEmpty() && innerList.get(0) == targetNumber) {
                return i;
            }
        }
        return -1;
    }

    public static void movePlayerInsidePlot(Player player, Location minLoc, Location maxLoc) {
        player.teleport(new Location(player.getWorld(),
                Math.max(player.getLocation().getX(), minLoc.getX()),
                player.getLocation().getY(),
                Math.max(player.getLocation().getZ(), minLoc.getZ()),
                player.getLocation().getYaw(),
                player.getLocation().getPitch()));
        player.teleport(new Location(player.getWorld(),
                Math.min(player.getLocation().getX(), maxLoc.getX()),
                player.getLocation().getY(),
                Math.min(player.getLocation().getZ(), maxLoc.getZ()),
                player.getLocation().getYaw(),
                player.getLocation().getPitch()));
    }
    public static void moveEntityInsidePlot(Entity entity, Location minLoc, Location maxLoc) {
        entity.teleport(new Location(entity.getWorld(),
                Math.max(entity.getLocation().getX(), minLoc.getX()),
                entity.getLocation().getY(),
                Math.max(entity.getLocation().getZ(), minLoc.getZ()),
                entity.getLocation().getYaw(),
                entity.getLocation().getPitch()));
        entity.teleport(new Location(entity.getWorld(),
                Math.min(entity.getLocation().getX(), maxLoc.getX()),
                entity.getLocation().getY(),
                Math.min(entity.getLocation().getZ(), maxLoc.getZ()),
                entity.getLocation().getYaw(),
                entity.getLocation().getPitch()));
    }

    public static Location parseLocation(String input,World world) {
        try {
            String[] parts = input.split(",");
            if (parts.length == 3) {
                double x = Double.parseDouble(parts[0].trim());
                double y = Double.parseDouble(parts[1].trim());
                double z = Double.parseDouble(parts[2].trim());

                // You can set the world here if needed. In this example, it's set to null.
                return new Location(world, x, y, z);
            } else {
                throw new IllegalArgumentException("Input should be in the format 'x, y, z'");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in input.");
        }
    }



    public static void savePersistentData(World world, SlimePlugin plugin){
        SlimeWorld slimeWorld = plugin.getWorld(world.getName());

        if (slimeWorld == null){
            return;
        }

        CompoundMap extraData = slimeWorld.getExtraData().getValue();
        Optional<CompoundTag> optionalChunkData = extraData.getOrDefault("worldData", new CompoundTag("worldData", new CompoundMap())).getAsCompoundTag();
        if (optionalChunkData.isEmpty()){
            return;
        }
        CompoundTag chunkData = optionalChunkData.get();
        CompoundMap compoundMap = NBTUtils.dataToCompoundMap(world.getPersistentDataContainer());
        chunkData.getValue().put("worldData", new CompoundTag("worldData", compoundMap));
        slimeWorld.getExtraData().getValue().put("worldData", chunkData);
    }
    public static void getWorldDataFromSlimeWorlds(World world){

        SlimeWorld slimeWorld = plugin.getWorld(world.getName());

        if (slimeWorld == null){
            return;
        }

        CompoundMap extraData = slimeWorld.getExtraData().getValue();
        Optional<CompoundTag> optionalChunkData = extraData.getOrDefault("worldData", new CompoundTag("worldData", new CompoundMap())).getAsCompoundTag();
        if (optionalChunkData.isEmpty()){
            return;
        }
        CompoundTag chunkData = optionalChunkData.get();
        CompoundMap compoundMap = NBTUtils.dataToCompoundMap(world.getPersistentDataContainer());
        String pType1 = JsonParser.parseString(NBTUtils.Converter.convertTag(chunkData.getValue().get("worldData")).getAsString()).getAsJsonObject().get("hypersquare:plottype").getAsString();
        world.getPersistentDataContainer().set(new NamespacedKey(Hypersquare.instance,"plotType"), PersistentDataType.STRING,pType1);
    }



    public static String randomHSVHex(float minHue, float maxHue, float saturation, float value) {
        Random random = new Random();

        // Generate a random hue within the specified range
        float hue = minHue + random.nextFloat() * (maxHue - minHue);

        // Create a Color object using the specified HSV values
        Color color = Color.fromRGB(java.awt.Color.getHSBColor(hue / 360, saturation, value).getRed(),java.awt.Color.getHSBColor(hue / 360, saturation, value).getGreen(),java.awt.Color.getHSBColor(hue / 360, saturation, value).getBlue());

        // Convert the Color object to hex text
        String hexText = colorToHex(color);

        return hexText;
    }

    public static String colorToHex(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        String redHex = Integer.toHexString(red);
        String greenHex = Integer.toHexString(green);
        String blueHex = Integer.toHexString(blue);
        redHex = padWithZeroes(redHex);
        greenHex = padWithZeroes(greenHex);
        blueHex = padWithZeroes(blueHex);
        return "#" + redHex + greenHex + blueHex;
    }

    public static String padWithZeroes(String input) {
        if (input.length() == 1) {
            return "0" + input;
        }
        return input;
    }

    public static Boolean playerOnline(String player){
        if (Bukkit.getPlayerExact(player) != null){
            return true;
        } else {
            return false;
        }
    }
    public static void sendUsageError(Player player, String usage){
        player.sendMessage(org.bukkit.ChatColor.DARK_AQUA + "Usage: " + org.bukkit.ChatColor.GRAY + org.bukkit.ChatColor.translateAlternateColorCodes('&', usage));
    }
    public static void resetPlayerStats(Player player){
        player.setHealth(20);
        player.setHealthScale(20);
        player.setExp(0);
        player.setFreezeTicks(0);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getActivePotionEffects().clear();
        player.setSaturation(20);
        player.closeInventory();
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setFlySpeed(0.1f);
        player.setWalkSpeed(0.2f);
        player.setGameMode(GameMode.SURVIVAL);
    }

    public static void setAction(Block block, String id, Player player){
        if (block.getType() == Material.OAK_WALL_SIGN){
            Sign sign = (Sign) block.getState();
            sign.getSide(Side.FRONT).setLine(1, id);
            sign.update();
            int plotID = Utilities.getPlotID(player.getWorld());
            HashMap<String,String> map = new HashMap<String,String>();
            map.put(LocationToString(block.getLocation().add(1,0,0)), id);
            PlotDatabase.addEvents(plotID,map);
            PlotDatabase.updateEventsCache(plotID);
        }
        player.closeInventory();
    }
    public static String LocationToString(Location location){
        return String.valueOf(location.getBlockX()) + ", " + String.valueOf(location.getBlockY()) + ", " + String.valueOf(location.getBlockZ());
    }
    public static String getKeyFromValue(HashMap<String, String> map, String targetValue) {
        for (HashMap.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(targetValue)) {
                return entry.getKey();
            }
        }
        return null;
    }
    public static String replaceHexWithColorTag(String text) {
        String pattern = "#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "<color:" + m.group(0) + ">");
        }
        m.appendTail(sb);
        return sb.toString();
    }

}
