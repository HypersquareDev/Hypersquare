package hypersquare.hypersquare.utils;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.google.gson.JsonParser;
import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import hypersquare.hypersquare.Hypersquare;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import static hypersquare.hypersquare.Hypersquare.teleportFlagMap;
import static hypersquare.hypersquare.Hypersquare.visitedLocationsMap;

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
    public static String getPlotType(World world){
        String name = world.getName();
        name = name.replace("hs.","");
        name = name.replace(".","");
        return name;
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
    public static Location deserializeLocation(String l, World world) {
        l = l.replace("Location{", "").replace("}", "");

        String[] keyValuePairs = l.split(",");
        String worldName = "";
        double x = 0.0, y = 0.0, z = 0.0, pitch = 0.0, yaw = 0.0;

        for (String pair : keyValuePairs) {
            String[] parts = pair.split("=");
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                Bukkit.broadcastMessage(key);
                switch (key) {
                    case "world":
                        worldName = value;
                        break;
                    case "x":
                        x = Double.parseDouble(value);
                        break;
                    case "y":
                        y = Double.parseDouble(value);
                        break;
                    case "z":
                        z = Double.parseDouble(value);
                        break;
                    case "pitch":
                        pitch = Double.parseDouble(value);
                        break;
                    case "yaw":
                        yaw = Double.parseDouble(value);
                        break;
                    default:
                        // Handle unknown keys or errors if needed
                        break;
                }
            }
        }

        // Use the extracted values to create a Location object
        return new Location(world, x, y, z, (float) yaw, (float) pitch);


    }

    public static void sendMultiMessage(Player recipient, List<String> messages)
    {
        for (String message : messages)
        {
            message = convertToChatColor(message);
            recipient.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', message));
        }
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

    public static final String capitalize(String str)
    {
        if (str == null || str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static void sendError(Player player, String message){
        player.sendMessage(org.bukkit.ChatColor.RED + "Error: " + org.bukkit.ChatColor.GRAY + message);
        player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_HURT_CLOSED,1,1);
    }

    public static void sendInfo(Player player, String message){
        player.sendMessage(org.bukkit.ChatColor.GREEN + "" + org.bukkit.ChatColor.BOLD + "» " + org.bukkit.ChatColor.GRAY + org.bukkit.ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendRedInfo(Player player, String message){
        player.sendMessage(org.bukkit.ChatColor.RED + "" + org.bukkit.ChatColor.BOLD + "» " + org.bukkit.ChatColor.GRAY + org.bukkit.ChatColor.translateAlternateColorCodes('&', message));
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


    public static void moveRecursively(Player player, Location location, Location boundary1, Location boundary2) {
        List<Location> visitedLocations = visitedLocationsMap.computeIfAbsent(player, k -> new ArrayList<>());
        boolean hasTeleported = teleportFlagMap.computeIfAbsent(player, k -> false);

        if (visitedLocations.contains(location) || hasTeleported) {
            return;
        }

        visitedLocations.add(location);

        if (Utilities.locationWithin(location, boundary1, boundary2)) {
            player.teleport(location);
            teleportFlagMap.put(player, true); // Set the teleport flag for this player
            visitedLocations.clear(); // Clears visited locations for this player
            return;
        }

        Location[] directions = {
                location.clone().add(0, 0, -0.5),
                location.clone().add(0.5, 0, 0),
                location.clone().add(0, 0, 0.5),
                location.clone().add(-0.5, 0, 0)
        };

        for (Location dir : directions) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    moveRecursively(player, dir, boundary1, boundary2);
                }
            }.runTaskLater((Plugin) Hypersquare.getPlugin(Hypersquare.class), (long) (20L * 0.1));
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
    public static void getWorldDataFromSlimeFun(World world){

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
        String pType1 = JsonParser.parseString(NBTUtils.Converter.convertTag(chunkData.getValue().get("worldData")).getAsString()).getAsJsonObject().get("hypercubed:plottype").getAsString();
        world.getPersistentDataContainer().set(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"plotType"), PersistentDataType.STRING,pType1);
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


}
