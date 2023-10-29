package hypersquare.hypersquare.items.actions;

import hypersquare.hypersquare.items.Action;
import hypersquare.hypersquare.items.ActionArgument;
import hypersquare.hypersquare.items.ActionItem;
import hypersquare.hypersquare.items.DisplayValue;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static hypersquare.hypersquare.Hypersquare.mm;

public class GiveItemsAction extends Action {

    public static ItemStack item(){
        return new ActionItem()
            .setMaterial(Material.CHEST)
            .setName("<green>Give Items")
            .setDescription("<gray>Gives the player all of the%n<gray>items in the chest")
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
            .build();
    }

}
