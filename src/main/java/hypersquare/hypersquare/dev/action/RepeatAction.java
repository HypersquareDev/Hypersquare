package hypersquare.hypersquare.dev.action;

import hypersquare.hypersquare.play.CodeStacktrace;
import hypersquare.hypersquare.play.ExecutionContext;

public interface RepeatAction extends Action, CallbackAfterAction {

    boolean check(ExecutionContext ctx, boolean initial);

    @Override
    default void execute(ExecutionContext ctx) {
        if (check(ctx, true)) {
            ctx.trace().pushFrame(new CodeStacktrace.Frame(ctx.containing(), ctx.selection()));
        }
    }

    @Override
    default void after(ExecutionContext ctx) {
        if (check(ctx, false)) {
            ctx.trace().pushFrame(new CodeStacktrace.Frame(ctx.containing(), ctx.selection()));
        }
    }
}
