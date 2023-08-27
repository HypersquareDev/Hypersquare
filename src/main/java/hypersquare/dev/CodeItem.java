package hypersquare.dev;

import hypersquare.hypersquare.ItemManager;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CodeItem {
    @Getter private final Material material;
    @Getter private final String actionName;
    @Getter private final String[] description;
    @Getter private final String[] example;
    @Getter private final String[] worksWith;
    @Getter private final String[] additionalInfo;
    @Getter private final Codeblock codeblock;

    public CodeItem(Material material, Codeblock codeblock, String actionName, String[] description, String[] example, String[] worksWith, String[] additionalInfo) {
        this.material = material;
        this.codeblock = codeblock;
        this.actionName = actionName;
        this.description = description;
        this.example = example;
        this.worksWith = worksWith;
        this.additionalInfo = additionalInfo;
    }

    public ItemStack buildItem() {
        ItemStack item = new ItemStack(material);
        List<String> lore = new ArrayList<String>();
        if (description != null) {
            for (String line : description) {
                lore.add(ChatColor.GRAY + line);
            }
        }
        if (example != null) {
            lore.add("");
            lore.add(ChatColor.WHITE + "Example:");
            for (String line : example) {
                lore.add(ChatColor.AQUA + "» " + ChatColor.GRAY + line);
            }
        }
        if (worksWith != null) {
            lore.add("");
            lore.add("#xAA7FFFWorks with:");
            for (String line : worksWith) {
                lore.add(ChatColor.AQUA + "» " + ChatColor.GRAY + line);
            }
        }
        if (additionalInfo != null) {
            lore.add("");
            lore.add(ChatColor.BLUE + "Additional Info:");
            for (String line : additionalInfo) {
                lore.add(ChatColor.AQUA + "» " + ChatColor.GRAY + line);
            }
        }
        
        item = ItemManager.setItemTags(item, codeblock.getId(), actionName);
        return item;
    }
}
