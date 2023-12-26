package hypersquare.hypersquare.dev;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.items.CodeValues;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class Number {
    Double value;
    public Number(double value){
        this.value = value;
    }
    public Number(ItemStack item){
        NamespacedKey key = new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"varitem");
        if (item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING) != null) {
            String data = item.getItemMeta().getPersistentDataContainer().get(key,PersistentDataType.STRING);
            JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
            this.value = Double.parseDouble(jsonObject.getAsJsonObject("data").get("name").getAsString());
        } else {
            this.value = null;
        }
    }

    public void setValue(double value){
        this.value = value;
    }
    public Double getValue(){
        return this.value;
    }
    public ItemStack getItem() {
        return CodeValues.NUMBER.build(value + "", false);
    }
}
