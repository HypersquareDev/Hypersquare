package hypersquare.hypersquare;

import com.alibaba.fastjson.JSON;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Utilities {
    public static int getPlotID(World world){
        String name = world.getName();
        name = name.replace("hs.","");
        name = name.replace(".build","");
        name = name.replace(".dev","");
        int plotID = Integer.parseInt(name);
        return plotID;
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

    public static void sendMultiMessage(Player recipient, String[] messages)
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

}
