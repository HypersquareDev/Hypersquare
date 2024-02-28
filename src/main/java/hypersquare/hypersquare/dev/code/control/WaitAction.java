package hypersquare.hypersquare.dev.code.control;

import hypersquare.hypersquare.dev.ActionTag;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.value.type.DecimalNumber;
import hypersquare.hypersquare.item.action.ActionItem;
import hypersquare.hypersquare.item.action.ActionMenuItem;
import hypersquare.hypersquare.item.value.DisplayValue;
import hypersquare.hypersquare.menu.action.ActionMenu;
import hypersquare.hypersquare.play.CodeSelection;
import hypersquare.hypersquare.play.execution.ExecutionContext;
import hypersquare.hypersquare.util.color.Colors;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WaitAction implements Action {
    @Override
    public ActionParameter[] parameters() {
        return new ActionParameter[]{
            new ActionParameter(DisplayValue.NUMBER, false, true, Component.text("Wait duration"), "duration")
        };
    }

    @Override
    public ActionTag[] tags() {
        return new ActionTag[]{
            new ActionTag("unit", "Time Unit", WaitTimeUnit.TICKS,
                new ActionTag.Option(WaitTimeUnit.TICKS, "Ticks", Material.REPEATER),
                new ActionTag.Option(WaitTimeUnit.SECONDS, "Seconds", Material.CLOCK),
                new ActionTag.Option(WaitTimeUnit.MINUTES, "Minutes", Material.RED_BED)
            )
        };
    }

    @Override
    public String getId() {
        return "wait";
    }

    @Override
    public String getCodeblockId() {
        return "control";
    }

    @Override
    public String getSignName() {
        return "Wait";
    }

    @Override
    public String getName() {
        return "Wait";
    }

    @Override
    public ActionMenuItem getCategory() {
        return null;
    }

    @Override
    public ItemStack item() {
        return new ActionItem()
            .setMaterial(Material.CLOCK)
            .setName(Component.text(getName()).color(Colors.YELLOW))
            .setDescription(Component.text("Waits a certain amount of time."))
            .setParameters(parameters())
            .setTagAmount(1)
            .build();
    }

    @Override
    public ActionMenu actionMenu(CodeActionData data) {
        return new ActionMenu(this, 3, data)
            .parameter("duration", 12)
            .tag("unit", 14);
    }

    @Override
    public void execute(@NotNull ExecutionContext ctx, CodeSelection targetSel) {
        WaitTimeUnit unit = ctx.getTag("unit", WaitTimeUnit::valueOf);
        DecimalNumber duration = ctx.args().getOr("duration", new DecimalNumber(1L));
        ctx.sleep(unit.get(duration.toDouble()));
    }

    enum WaitTimeUnit {
        TICKS(1),
        SECONDS(20),
        MINUTES(20 * 60),
        ;

        public final long ticks;
        WaitTimeUnit(long ticks) {
            this.ticks = ticks;
        }

        public int get(double dur) {
            return (int) (dur * ticks);
        }
    }
}
