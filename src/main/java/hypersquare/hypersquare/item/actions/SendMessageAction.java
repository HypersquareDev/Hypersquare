package hypersquare.hypersquare.item.actions;

import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.item.PlayerActionItems;
import org.bukkit.block.Barrel;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SendMessageAction implements Action {
    @Override
    public void executeBlockAction(List<Entity> targets, Barrel barrel) {

    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public PlayerActionItems getCategory() {
        return null;
    }

    @Override
    public ItemStack item() {
        return null;
    }
}
