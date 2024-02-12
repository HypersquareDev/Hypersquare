package hypersquare.hypersquare.dev.code.var.action;

import hypersquare.hypersquare.dev.ActionTag;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.item.action.ActionItem;
import hypersquare.hypersquare.item.action.ActionMenuItem;
import hypersquare.hypersquare.item.action.var.SetVariableItems;
import hypersquare.hypersquare.item.value.DisplayValue;
import hypersquare.hypersquare.menu.action.ActionMenu;
import hypersquare.hypersquare.play.CodeSelection;
import hypersquare.hypersquare.play.CodeVariable;
import hypersquare.hypersquare.play.execution.ExecutionContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AssignVariableAction implements Action {
    @Override
    public ActionParameter[] parameters() {
        return new ActionParameter[] {
                new ActionParameter(DisplayValue.VARIABLE, Component.text("The variable to set"), "variable"),
                new ActionParameter(DisplayValue.ANY, Component.text("The value to set the variable to."), "value")
        };
    }

    @Override
    public String getId() {
        return "assign";
    }

    @Override
    public String getCodeblockId() {
        return "set_variable";
    }

    @Override
    public String getSignName() {
        return "=";
    }

    @Override
    public String getName() {
        return "Assign Variable (=)";
    }

    @Override
    public ActionMenuItem getCategory() {
        return SetVariableItems.VARIABLE_SETTING;
    }

    @Override
    public ActionTag[] tags() {
        return new ActionTag[] {};
    }

    @Override
    public ItemStack item() {
        return new ActionItem()
                .setMaterial(Material.IRON_INGOT)
                .setName(Component.text("Assign Variable").color(NamedTextColor.WHITE))
                .setDescription(Component.text("Assigns a value to a variable."))
                .setParameters(parameters())
                .build();
    }

    @Override
    public ActionMenu actionMenu(CodeActionData data) {
        return new ActionMenu(this, 3, data)
                .parameter("variable",12)
                .parameter("value", 14);
    }

    @Override
    public void execute(ExecutionContext ctx, CodeSelection targetSel) {
        ctx.args().<CodeVariable>single("variable").set(ctx.args().single("value"));
    }
}
