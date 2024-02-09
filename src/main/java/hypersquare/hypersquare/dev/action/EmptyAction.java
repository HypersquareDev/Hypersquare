package hypersquare.hypersquare.dev.action;

import hypersquare.hypersquare.dev.ActionTag;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.item.action.ActionMenuItem;
import hypersquare.hypersquare.menu.actions.ActionMenu;
import hypersquare.hypersquare.play.execution.ExecutionContext;
import org.bukkit.inventory.ItemStack;

public class EmptyAction implements Action {
    @Override
    public ActionParameter[] parameters() {
        return null;
    }

    @Override
    public ActionTag[] tags() {
        return new ActionTag[] {};
    }

    @Override
    public String getId() {
        return "empty";
    }

    @Override
    public String getCodeblockId() {
        return "";
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
    public ActionMenuItem getCategory() {
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

    @Override
    public void execute(ExecutionContext ctx) {

    }
}
