package hypersquare.hypersquare.items;

import com.google.gson.*;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.utils.ItemBuilder;
import hypersquare.hypersquare.utils.Utilities;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.json.Json;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static hypersquare.hypersquare.Hypersquare.mm;

public enum CodeValues {

    TEXT(Material.BOOK,"<#7FD42A>Text", "<gray>Colors, and other tags like%n<gray><hover> are supported in default mode%n%n<gray>Used for bossbar, scoreboard,%n<gray>titles, messages, etc.%n%n<dark_gray>Legacy color codes: %l","txt"),
    STRING(Material.STRING,"<aqua>String", "No colors%nUsed for dict keys, UUIDs, etc.","string");

    @Getter Material material;
    @Getter final String name;
    @Getter
    String lore;
    @Getter String id;

    CodeValues(Material material, String name, String lore,String id) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.id = id;
    }

    public ItemStack build(String value, Boolean legacy) {
        String tempLore = lore.replace("%l", legacy ? "<green>True" : "<red>False");
        String[] parts = tempLore.split("%n");
        List<Component> list = new ArrayList<>(List.of());
        for (String part : parts) {
            list.add(mm.deserialize(part));
        }



        if (value == null){
            value = name;
        }


        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        JsonObject jsonInput = new JsonObject();
        JsonObject data = new JsonObject();

        data.addProperty("name", value);
        jsonInput.addProperty("id", id);
        jsonInput.add("data", data);


        String jsonString = gson.toJson(jsonInput);

        if (legacy){
            return new ItemBuilder(material)

                    .name(LegacyComponentSerializer.legacyAmpersand().deserialize(value))
                    .lore(list)
                    .hideFlags()
                    .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"varitem"), jsonString)
                    .build();
        } else {
            return new ItemBuilder(material)
                    .name(mm.deserialize(value))
                    .lore(list)
                    .hideFlags()
                    .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"varitem"), jsonString)
                    .build();
        }
    }

    public static String getValue(ItemStack item){
        NamespacedKey key = new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"varitem");
        if (item.getItemMeta().getPersistentDataContainer().get(key,PersistentDataType.STRING) != null) {
            String data = item.getItemMeta().getPersistentDataContainer().get(key,PersistentDataType.STRING);
            JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
            return jsonObject.getAsJsonObject("data").get("name").getAsString();
        }
        return null;

    }

    public static String getType(ItemStack item){
        NamespacedKey key = new NamespacedKey(Hypersquare.instance,"varitem");
        if (item.getItemMeta().getPersistentDataContainer().get(key,PersistentDataType.STRING) != null) {
            String data = item.getItemMeta().getPersistentDataContainer().get(key,PersistentDataType.STRING);
            JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
            return jsonObject.get("id").getAsString();
        }

        return null;
    }
}
