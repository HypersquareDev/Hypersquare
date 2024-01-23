package hypersquare.hypersquare.dev.action;

import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.item.PlayerActionItems;
import hypersquare.hypersquare.menu.actions.ActionMenu;
import org.bukkit.block.Barrel;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EmptyAction implements Action {
    @Override
    public void executeBlockAction(List<Entity> targets, Barrel barrel) {}

    @Override
    public ActionParameter[] parameters() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getSignName() {
        return "";
    }

    @Override
    public String getName() {
        return "Empty Action";
    }

    @Override
    public PlayerActionItems getCategory() {
        return null;
    }

    @Override
    public ItemStack item() {
        return null;
    }

    @Override
    public ActionMenu actionMenu(CodeActionData data) {
        return new ActionMenu(this, 3, data);
    }
}
