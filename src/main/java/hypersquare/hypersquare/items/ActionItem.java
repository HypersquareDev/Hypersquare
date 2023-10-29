package hypersquare.hypersquare.items;


import hypersquare.hypersquare.utils.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static hypersquare.hypersquare.Hypersquare.mm;

public class ActionItem {

    private ItemStack actionItem;
    String name;
    Material material;
    String description;
    ActionArgument[] arguments;

    public ActionItem setMaterial(Material material){
        this.material = material;
        return this;
    }
    public ActionItem setName(String name){
        this.name = name;
        return this;
    }
    public ActionItem setDescription(String lore){
        this.description = lore;
        return this;
    }
    public ActionItem setArguments(ActionArgument... arguments) {
        this.arguments = arguments;
        return this;
    }

    public ItemStack build(){
        actionItem = new ItemStack(material);
        ItemMeta meta = actionItem.getItemMeta();

        List<Component> lore = new ArrayList<>(List.of());

        // Description
        String[] parts = description.split("%n");
        for (String part : parts) {
            lore.add(mm.deserialize("<!italic><gray>" + part));
        }

        // Arguments
        StringBuilder builder = new StringBuilder();
        builder.append("%n<white>Chest Parameters:%n");
        for (ActionArgument actionArgument : arguments){
            builder.append(mm.serialize(actionArgument.getType().getName(actionArgument.type)));
            builder.append(actionArgument.plural ? "(s)" : "");
            builder.append(actionArgument.optional ? "*" : "");
            builder.append(" <dark_gray>- <gray>").append(actionArgument.description).append("%n");
        }
        String builderString = String.valueOf(builder);

        parts = builderString.split("%n");
        for (String part : parts) {
            lore.add(mm.deserialize("<!italic>" + part));
        }

        //Name
        meta.displayName(mm.deserialize("<!italic>" + name));

        meta.lore(lore);
        actionItem.setItemMeta(meta);
        return actionItem;
    }
}
