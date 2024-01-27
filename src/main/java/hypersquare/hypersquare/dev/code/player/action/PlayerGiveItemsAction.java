package hypersquare.hypersquare.dev.code.player.action;

import hypersquare.hypersquare.dev.ActionTag;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.value.type.DecimalNumber;
import hypersquare.hypersquare.item.action.ActionItem;
import hypersquare.hypersquare.item.action.ActionMenuItem;
import hypersquare.hypersquare.item.action.player.PlayerActionItems;
import hypersquare.hypersquare.item.value.DisplayValue;
import hypersquare.hypersquare.menu.actions.ActionMenu;
import hypersquare.hypersquare.play.ExecutionContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerGiveItemsAction implements Action {

    @Override
    public void execute(ExecutionContext ctx) {
        List<ItemStack> items = new ArrayList<>();
        double multiplier = ctx.args().getOr("multiplier", new DecimalNumber(1, 0)).toDouble();
        for (ItemStack item : ctx.args().<ItemStack>allNonNull("items")) {
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
        
        for (Player p : ctx.selection().players()) {
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

    @Override
    public ActionTag[] tags() {
        return new ActionTag[] {};
    }

    public String getId() {
        return "give_items";
    }

    @Override
    public String getCodeblockId() {
        return "player_action";
    }

    public String getSignName() {
        return "GiveItems";
    }

    @Override
    public String getName() {
        return "Give Items";
    }

    @Override
    public ActionMenuItem getCategory() {
        return PlayerActionItems.ITEM_MANAGEMENT_CATEGORY;
    }
}
