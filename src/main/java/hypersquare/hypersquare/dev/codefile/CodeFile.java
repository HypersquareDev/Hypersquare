package hypersquare.hypersquare.dev.codefile;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import hypersquare.hypersquare.HSKeys;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.codefile.data.CodeData;
import hypersquare.hypersquare.util.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.Deflater;

public class CodeFile {
    public World world;
    public Player player;

    public CodeFile(World world) {
        this.world = world;
    }

    public CodeFile(Player player) {
        this.player = player;
        this.world = player.getWorld();
    }

    public CodeFile(int plotId) {
        world = Bukkit.getWorld("hs.code." + plotId);
    }

    public CodeData getCodeData() {
        String code = getCode();
        JsonArray array = JsonParser.parseString(code).getAsJsonArray();

        if (!array.isJsonNull() && !array.isEmpty()) return new CodeData(array);
            else return new CodeData(new JsonArray());
    }

    public String getCode() {
        String code = world.getPersistentDataContainer().get(HSKeys.CODE, PersistentDataType.STRING);
        assert code != null;
        return code;
    }

    public void setCode(String newCode) {
        world.getPersistentDataContainer().set(HSKeys.CODE, PersistentDataType.STRING, newCode);
        Utilities.savePersistentData(world, Hypersquare.slimePlugin);
    }

    public String getEncodedCode() throws IOException {
        // Encode using ZLIB and Base64
        Deflater deflater = new Deflater();
        deflater.setInput(getCode().getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(getCode().length());
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        deflater.end();

        return Base64.getEncoder().encodeToString(output);
    }
}
