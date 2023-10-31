package hypersquare.hypersquare.items.actions;

import hypersquare.hypersquare.items.Action;
import hypersquare.hypersquare.items.PlayerActionItems;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SendMessageAction implements Action {
    @Override
    public void executeBlockAction(List<Entity> targets, Chest chest) {

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
