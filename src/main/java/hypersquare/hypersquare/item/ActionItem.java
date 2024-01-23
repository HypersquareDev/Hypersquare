package hypersquare.hypersquare.item;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ActionItem {

    Component name;
    Material material;
    Component[] description;
    Action.ActionParameter[] params;
    List<Component[]> additionalInfo = List.of();
    boolean enchanted;

    public ActionItem setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ActionItem setName(Component name) {
        this.name = name;
        return this;
    }

    public ActionItem setDescription(Component... lore) {
        this.description = lore;
        return this;
    }

    public ActionItem setParameters(Action.ActionParameter... params) {
        this.params = params;
        return this;
    }

    public ActionItem addAdditionalInfo(Component... info) {
        List<Component[]> infoList = new ArrayList<>(additionalInfo);
        info[0] = Component.text("⏵")
                .color(NamedTextColor.BLUE)
                .append(Component.text(" ")
                        .color(NamedTextColor.GRAY)
                        .append(info[0]
                                .color(NamedTextColor.GRAY)));
        for (int i = 1; i < info.length; i++) {
            info[i] = info[i].color(NamedTextColor.GRAY)
                    .decoration(TextDecoration.ITALIC, false);
        }
        infoList.add(info);
        this.additionalInfo = infoList;
        return this;
    }

    public ActionItem setEnchanted(boolean enchanted) {
        this.enchanted = enchanted;
        return this;
    }

    public ItemStack build() {
        ItemStack actionItem = new ItemStack(material);
        ItemMeta meta = actionItem.getItemMeta();
        List<Component> lore = new ArrayList<>(List.of());

        //Name
        meta.displayName(name.decoration(TextDecoration.ITALIC, false));

        // Description
        for (Component part : description) {
            lore.add(part.color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        }

        // Arguments
        Component paramComp = Component.text("Chest Parameters")
                .color(NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false);
        if (params != null) {
            for (Action.ActionParameter actionParameter : params) {
                paramComp = paramComp.append(actionParameter.type().getName());
                paramComp = paramComp.append(Component.text(actionParameter.plural() ? "(s)" : ""));
                paramComp = paramComp.append(Component.text(actionParameter.optional() ? "*" : "").color(NamedTextColor.GRAY));
                paramComp = paramComp.append(Component.text(" - ").color(NamedTextColor.DARK_GRAY)).append(actionParameter.description().color(NamedTextColor.GRAY)).appendNewline();
            }
        } else paramComp = paramComp.append(Component.text("None").color(NamedTextColor.DARK_GRAY));


        // Additional Info
        if (!additionalInfo.isEmpty()) {
            lore.add(Component.text("").decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text("Additional Info").color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false));
            for (Component[] info : additionalInfo) {
                for (Component text : info) {
                    lore.add(text.decoration(TextDecoration.ITALIC, false));
                }
            }
        }

        // Enchanted
        if (enchanted) meta.addEnchant(Enchantment.LURE, 1, true);

        meta.lore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        actionItem.setItemMeta(meta);
        return actionItem;
    }
}
