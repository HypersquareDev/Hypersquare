package hypersquare.hypersquare.item;

import org.bukkit.block.Barrel;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Action {

    void executeBlockAction(List<Entity> targets, Barrel barrel);

    String getId();
    PlayerActionItems getCategory();

    ItemStack item();
}