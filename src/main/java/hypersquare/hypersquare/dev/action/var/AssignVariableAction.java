package hypersquare.hypersquare.dev.action.var;

import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.item.ActionItem;
import hypersquare.hypersquare.item.ActionMenuItem;
import hypersquare.hypersquare.item.DisplayValue;
import hypersquare.hypersquare.item.SetVariableItems;
import hypersquare.hypersquare.menu.actions.ActionMenu;
import hypersquare.hypersquare.play.CodeVariable;
import hypersquare.hypersquare.play.ExecutionContext;
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
        return "set_var_assign";
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
        return SetVariableItems.GENERAL;
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
    public void execute(ExecutionContext ctx) {
        ctx.args().<CodeVariable>single("variable").set(ctx.args().single("value"));
    }
}
