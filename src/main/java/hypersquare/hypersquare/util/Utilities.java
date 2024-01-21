package hypersquare.hypersquare.util;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.google.gson.JsonParser;
import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.plot.PlotDatabase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

import static hypersquare.hypersquare.Hypersquare.cleanMM;

public class Utilities {
    public static int getPlotID(World world) {
        String name = world.getName();
        if (name.contains("hs.")) {
            name = name.replace("hs.", "");
            if (name.startsWith("code.")) name = name.substring(5);
            return Integer.parseInt(name);
        }
        return 0;
    }

    public static int findLengthOfLongestString(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return 0; // Return 0 if the list is null or empty
        }


        int maxLength = PlainTextComponentSerializer.plainText().serialize(cleanMM.deserialize(strings.get(0))).length();  // Initialize maxLength to the length of the first string

        for (String s : strings) {
            if (PlainTextComponentSerializer.plainText().serialize(cleanMM.deserialize(s)).length() > maxLength) {
                maxLength = PlainTextComponentSerializer.plainText().serialize(cleanMM.deserialize(s)).length();
                // Update maxLength if a longer string is found
            }
        }

        return maxLength;
    }

    public static void sendMultiMiniMessage(CommandSender recipient, List<String> messages) {
        int size = findLengthOfLongestString(messages);
        String before = "<#AAAAFF><strikethrough>" + " ".repeat((int) (size * 1.5)) + "<#AAAAFF>";
        String message = String.join("<newline><reset>", messages);
        message = before + "<newline><reset>" + message + "<newline>" + before;
        recipient.sendMessage(cleanMM.deserialize(message));
    }

    public static ItemStack formatItem(String lore, Material material, String name) {
        String[] parts = lore.split("%n");
        List<Component> list = new ArrayList<>(List.of());
        for (String part : parts) {
            list.add(cleanMM.deserialize(part));
        }

        return new ItemBuilder(material)
                .name(cleanMM.deserialize(name))
                .lore(list)
                .hideFlags()
                .build();
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static void sendError(CommandSender sender, String message) {
        sender.sendMessage(cleanMM.deserialize("<red>Error: <gray>" + message));
        if (sender instanceof Player player) {
            player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_HURT_CLOSED, 1, 1);
        }
    }

    public static void sendInfo(CommandSender sender, Component message) {
        sender.sendMessage(Component.text("»")
                .color(NamedTextColor.GREEN)
                .decoration(TextDecoration.BOLD, true)
                .append(Component.text(" ")
                        .color(NamedTextColor.GRAY)
                        .decoration(TextDecoration.BOLD, false)
                        .append(message)
                )
        );
    }
    public static void sendRedInfo(Player player, Component message) {
        player.sendMessage(Component.text("»")
                .color(NamedTextColor.RED)
                .decoration(TextDecoration.BOLD, true)
                .append(Component.text(" ")
                        .color(NamedTextColor.GRAY)
                        .decoration(TextDecoration.BOLD, false)
                        .append(message)
                )
        );
    }

    public static void sendOpenMenuSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_OFF, 1, 2);
    }

    public static void sendClickMenuSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1f, 1.5f);
    }

    public static void sendSecondaryMenuSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
    }

    public static void sendSuccessClickMenuSound(Player player) {
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

    public static void moveEntityInsidePlot(Entity entity, Location locA, Location locB) {
        Location entityLocation = entity.getLocation();

        double minX = Math.min(locA.getX(), locB.getX());
        double minY = Math.min(locA.getY(), locB.getY());
        double minZ = Math.min(locA.getZ(), locB.getZ());
        double maxX = Math.max(locA.getX(), locB.getX());
        double maxY = Math.max(locA.getY(), locB.getY());
        double maxZ = Math.max(locA.getZ(), locB.getZ());

        // Check if the entity is even outside the boundaries
        if (entityLocation.getX() < minX || entityLocation.getX() > maxX ||
                entityLocation.getY() < minY || entityLocation.getY() > maxY ||
                entityLocation.getZ() < minZ || entityLocation.getZ() > maxZ) {

            // Calculate a location that is within the boundaries
            double tpX = clamp(entityLocation.getX(), minX, maxX);
            double tpY = clamp(entityLocation.getY(), minY, maxY);
            double tpZ = clamp(entityLocation.getZ(), minZ, maxZ);

            Location tpLocation = new Location(entity.getWorld(), tpX, tpY, tpZ, entityLocation.getYaw(), entityLocation.getPitch());
            entity.teleport(tpLocation);
        }
    }

    public static Location parseLocation(String input, World world) {
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

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private static CompoundTag getChunkData(SlimeWorld slimeWorld) {
        if (slimeWorld == null) {
            return null;
        }

        CompoundMap extraData = slimeWorld.getExtraData().getValue();
        Optional<CompoundTag> optionalChunkData = extraData.getOrDefault("worldData", new CompoundTag("worldData", new CompoundMap())).getAsCompoundTag();
        return optionalChunkData.orElse(null);
    }

    public static void savePersistentData(World world, SlimePlugin plugin) {
        SlimeWorld slimeWorld = plugin.getWorld(world.getName());
        CompoundTag chunkData = getChunkData(slimeWorld);
        if(chunkData == null) return;
        CompoundMap compoundMap = NBTUtils.dataToCompoundMap(world.getPersistentDataContainer());
        chunkData.getValue().put("worldData", new CompoundTag("worldData", compoundMap));
        slimeWorld.getExtraData().getValue().put("worldData", chunkData);
    }

    public static void getWorldDataFromSlimeWorlds(World world) {
        SlimeWorld slimeWorld = Hypersquare.slimePlugin.getWorld(world.getName());
        CompoundTag chunkData = getChunkData(slimeWorld);
        if (chunkData == null) return;
        String pType1 = JsonParser.parseString(NBTUtils.Converter.convertTag(chunkData.getValue().get("worldData")).getAsString()).getAsJsonObject().get("hypersquare:plottype").getAsString();
        world.getPersistentDataContainer().set(new NamespacedKey(Hypersquare.instance, "plotType"), PersistentDataType.STRING, pType1);
    }

    public static String randomHSVHex(float minHue, float maxHue, float saturation, float value) {
        Random random = new Random();

        // Generate a random hue within the specified range
        float hue = minHue + random.nextFloat() * (maxHue - minHue);

        // Create a Color object using the specified HSV values
        Color color = Color.fromRGB(java.awt.Color.getHSBColor(hue / 360, saturation, value).getRed(), java.awt.Color.getHSBColor(hue / 360, saturation, value).getGreen(), java.awt.Color.getHSBColor(hue / 360, saturation, value).getBlue());

        // Convert the Color object to hex text
        return colorToHex(color);
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

    public static Boolean playerOnline(String player) {
        return Bukkit.getPlayerExact(player) != null;
    }

    public static void sendUsageError(CommandSender sender, String usage) {
        sender.sendMessage(Component.text("Usage: ")
                .color(NamedTextColor.DARK_AQUA)
                .append(Component.text(usage).color(NamedTextColor.GRAY))
        );
    }

    public static void resetPlayerStats(Player player, boolean clearInventory) {
        player.setHealth(20);
        player.setHealthScale(20);
        player.setTotalExperience(0);
        player.setFreezeTicks(0);
        player.setFoodLevel(20);
        if (clearInventory) player.getInventory().clear();
        player.getActivePotionEffects().clear();
        player.setSaturation(20);
        player.closeInventory();
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setFlySpeed(0.1f);
        player.setWalkSpeed(0.2f);
        player.setGameMode(GameMode.SURVIVAL);
    }

    public static void resetPlayerStats(Player player) {
        resetPlayerStats(player, true);
    }

    public static void setAction(Block block, String id, Player player) {
        if (block.getType() == Material.OAK_WALL_SIGN) {
            Sign sign = (Sign) block.getState();
            sign.getSide(Side.FRONT).line(1, Component.text(id));
            sign.update();
            int plotID = Utilities.getPlotID(player.getWorld());
            HashMap<String, String> map = new HashMap<>();
            map.put(LocationToString(block.getLocation().add(1, 0, 0)), id);
            PlotDatabase.addEvents(plotID, map);
            PlotDatabase.updateEventsCache(plotID);
        }
        player.closeInventory();
    }

    public static String LocationToString(Location location) {
        return location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }

    public static String getKeyFromValue(HashMap<String, String> map, String targetValue) {
        for (HashMap.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(targetValue)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
