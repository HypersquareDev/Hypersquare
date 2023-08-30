package hypersquare.hypersquare;

import com.alibaba.fastjson.JSON;
import org.bukkit.Color;
import org.bukkit.World;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
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

}
