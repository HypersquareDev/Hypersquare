package hypersquare.hypersquare.items;

import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Action {

    void executeBlockAction(List<Entity> targets, Chest chest);

    String getId();
    PlayerActionItems getCategory();

    ItemStack item();
}