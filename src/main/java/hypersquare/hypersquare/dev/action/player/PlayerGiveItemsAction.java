package hypersquare.hypersquare.dev.action.player;

import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.value.type.DecimalNumber;
import hypersquare.hypersquare.item.*;
import hypersquare.hypersquare.menu.actions.ActionMenu;
import hypersquare.hypersquare.play.ActionArguments;
import hypersquare.hypersquare.play.CodeSelection;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerGiveItemsAction implements Action {

    @Override
    public void execute(CodeSelection selection, ActionArguments args) {
        List<ItemStack> items = new ArrayList<>();
        double multiplier = args.getOr("multiplier", new DecimalNumber(1, 0)).toDouble();
        for (ItemStack item : args.<ItemStack>all("items")) {
            int amount = (int) (item.getAmount() * multiplier);
            int max = item.getMaxStackSize();
            item.setAmount(max);
            while (amount > max && items.size() < 100) {
                items.add(item.clone());
                amount -= max;
            }
            item.setAmount(amount);
            items.add(item);
        }
        
        for (Player p : selection.players()) {
            for (ItemStack item : items) {
                p.getInventory().addItem(item);
            }
        }
    }

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
        return "player_action_give_items";
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
