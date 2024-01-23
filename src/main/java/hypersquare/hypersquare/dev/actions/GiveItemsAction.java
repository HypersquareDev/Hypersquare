package hypersquare.hypersquare.dev.actions;

import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.item.*;
import hypersquare.hypersquare.menu.actions.ActionMenu;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
                .setName(Component.text("Give Items").color(NamedTextColor.GREEN))
                .setDescription(Component.text("Gives the player all of the"),
                        Component.text("items in the barrel"))
                .setParameters(parameters())
                .addAdditionalInfo(Component.text("This is the first action"),
                        Component.text("we added!"))
                .setEnchanted(true)
                .build();
    }

    @Override
    public ActionMenu actionMenu(CodeActionData data) {
        return new ActionMenu(this, 4, data)
                .parameter("items", 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26)
                .parameter("multiplier", 35);
    }

    @Override
    public void executeBlockAction(List<Entity> targets, Barrel barrel) {
        for (Entity entity : targets) {
            if (entity instanceof Player player) {
                Utilities.sendRedInfo(player, Component.text("I know how much you wanna start coding, but our systems aren't done yet."));
            }
        }
    }

    @Override
    public ActionParameter[] parameters() {
        return new ActionParameter[]{
                new Action.ActionParameter(
                        DisplayValue.ITEM, true, false, Component.text("Item(s) to give"), "items"
                ),
                new Action.ActionParameter(
                        DisplayValue.NUMBER, false, true, Component.text("Amount to multiply by"), "multiplier"
                )
        };
    }

    public String getId() {
        return "give_items";
    }
    public String getSignName() {
        return "GiveItems";
    }

    @Override
    public String getName() {
        return "Give Items";
    }

    public PlayerActionItems getCategory() {
        return PlayerActionItems.ITEM_MANAGEMENT_CATEGORY;
    }
}
