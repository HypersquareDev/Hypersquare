package hypersquare.hypersquare;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

public class LoadCodeTemplate {
    public static String load(ItemStack codeTemplate) {
        NBTItem nbtItem = new NBTItem(codeTemplate);

        if (nbtItem.hasKey("PublicBukkitValues")) {
            String codeTemplateData = nbtItem.getCompound("PublicBukkitValues").getString("hypercube:codetemplatedata");

            JsonObject codeTemplateJson = JsonParser.parseString(codeTemplateData).getAsJsonObject();
            if (codeTemplateJson.has("code")) {
                return codeTemplateJson.get("code").getAsString();
            }


        }

        return null; // Return a default value or handle error cases
    }
}
