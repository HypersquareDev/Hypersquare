package hypersquare.hypersquare.dev.actions;

import hypersquare.hypersquare.items.*;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GiveItemsAction implements Action {

    public ItemStack item() {
        return new ActionItem()
            .setMaterial(Material.CHEST)
            .setName("<gold>Give Items")
            .setDescription("Gives the player all of the", "items in the chest")
            .setArguments(
                    new ActionArgument()
                            .setType(DisplayValue.ITEM)
                            .setPlural(true)
                            .setDescription("Item(s) to give"),
                    new ActionArgument()
                            .setType(DisplayValue.LIST)
                            .setPlural(false)
                            .setOptional(true)
                            .setDescription("Return list"),
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
    public void executeBlockAction(List<Entity> targets, Chest chest) {
        int multiple = 1;
        Inventory contents = chest.getBlockInventory();
        if (contents.contains(Material.SLIME_BALL)){
            for (ItemStack itemStack : contents){
                String type = CodeValues.getType(itemStack);
                if (type != null){
                    if (type == "num")
                    {
                        multiple = Integer.parseInt(CodeValues.getValue(itemStack));
                        break;
                    }
                }
            }
        }
        for (Entity entity : targets){
            if (entity instanceof Player){
                Player player = (Player) entity;
                for (ItemStack item : contents){
                    if (item != null) {
                        if (CodeValues.getType(item) == null) {
                            for (int i = 0; i != multiple; i++) {
                                player.getInventory().addItem(item);
                            }
                        }
                    }
                }
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
