package hypersquare.hypersquare.dev.codefile;

import com.google.gson.*;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.CodeBlocks;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.io.*;
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

    public JsonArray getCodeJson() {
        String code = getCode();
        world.sendMessage(Component.text(code));

        JsonArray array = JsonParser.parseString(code).getAsJsonArray();

        if (!array.isJsonNull() && !array.isEmpty()) {
            return array;
        } else {
            return new JsonArray();
        }
    }

    public String getCode() {
        String code = world.getPersistentDataContainer().get(new NamespacedKey(Hypersquare.instance, "code"), PersistentDataType.STRING);
        assert code != null;
        return code;
    }

    public void setCode(String newCode) {
        world.getPersistentDataContainer().set(new NamespacedKey(Hypersquare.instance, "code"), PersistentDataType.STRING, newCode);
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
