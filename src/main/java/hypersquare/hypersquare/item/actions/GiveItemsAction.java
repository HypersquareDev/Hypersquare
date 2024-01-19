package hypersquare.hypersquare.item.actions;

import hypersquare.hypersquare.item.*;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GiveItemsAction implements Action {

    public ItemStack item() {
        return new ActionItem()
                .setMaterial(Material.CHEST)
                .setName("<green>Give Items")
                .setDescription("Gives the player all of the", "items in the chest")
                .setArguments(
                        new ActionArgument()
                                .setType(DisplayValue.ITEM)
                                .setPlural(true)
                                .setDescription("Item(s) to give"),
                        new ActionArgument()
                                .setType(DisplayValue.NUMBER)
                                .setOptional(true)
                                .setDescription("Amount to give")
                )
                .addAdditionalInfo("This is the first action", "we added!")
                .setEnchanted(true)
                .build();
    }

    @Override
    public void executeBlockAction(List<Entity> targets, Barrel barrel) {
        for (Entity entity : targets) {
            if (entity instanceof Player player) {
                Utilities.sendRedInfo(player, Component.text("I know how much you wanna start coding, but our systems aren't done yet."));
            }
        }
    }

    public String getId() {
        return "GiveItems";
    }

    public PlayerActionItems getCategory() {
        return PlayerActionItems.ITEM_MANAGEMENT_CATEGORY;
    }
}
