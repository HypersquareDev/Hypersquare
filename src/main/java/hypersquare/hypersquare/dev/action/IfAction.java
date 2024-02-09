package hypersquare.hypersquare.dev.action;

import hypersquare.hypersquare.play.CodeSelection;
import hypersquare.hypersquare.play.execution.CodeStacktrace;
import hypersquare.hypersquare.play.execution.ExecutionContext;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public interface IfAction extends Action {

    boolean check(Entity entity, ExecutionContext ctx);

    @Override
    default void execute(ExecutionContext ctx) {
        List<Entity> filtered = new ArrayList<>();
        for (Entity e : ctx.selection().all()) {
            if (check(e, ctx)) filtered.add(e);
        }
        if (!filtered.isEmpty()) {
            ctx.trace().pushFrame(new CodeStacktrace.Frame(ctx.containing(), new CodeSelection(filtered)));
        }
    }
}
