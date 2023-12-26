package hypersquare.hypersquare.dev;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.items.CodeValues;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class Text {
    String value;
    public Text(String value){
        this.value = value;
    }
    public Text(ItemStack item){
        NamespacedKey key = new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"varitem");
        if (item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING) != null) {
            String data = item.getItemMeta().getPersistentDataContainer().get(key,PersistentDataType.STRING);
            JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
            this.value = jsonObject.getAsJsonObject("data").get("name").getAsString();
        } else {
            this.value = null;
        }
    }

    public void setValue(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
    public ItemStack getItem(){
        return CodeValues.TEXT.build(value,false);
    }
    public Text parseText(ItemStack item){
        NamespacedKey key = new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"varitem");
        if (item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING) != null) {
            String data = item.getItemMeta().getPersistentDataContainer().get(key,PersistentDataType.STRING);
            JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
            return new Text(jsonObject.getAsJsonObject("data").get("name").getAsString());
        }
        return null;
    }
}
