package hypersquare.hypersquare.play.execution;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.ActionTag;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.dev.value.impl.VariableValue;
import hypersquare.hypersquare.play.ActionArguments;
import hypersquare.hypersquare.play.CodeVariable;
import hypersquare.hypersquare.play.CodeVariableScope;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Pair;

import java.util.function.Function;

public record ExecutionContext(
    CodeExecutor executor,
    ActionArguments args,
    CodeStacktrace trace,
    Action action, CodeActionData data
) {
    public static final CodeVariableScope globalScope = new CodeVariableScope();

    @Contract(pure = true)
    public CodeVariableScope getScope(VariableValue.@NotNull Scope scope) {
        return switch (scope) {
            case THREAD -> trace.scope;
            case GLOBAL -> globalScope;
            default -> null; // TODO: implement other scopes
        };
    }

    @SuppressWarnings("unchecked")
    public <T> T getTag(String id, Function<String, T> o) {
        Pair<String, VariableValue.HSVar> tagValue = data.tags.getOrDefault(id, new Pair<>(action.getTag(id).defaultOption().name(), null));
        if (tagValue.getB() != null) {
            CodeVariable var = (CodeVariable) CodeValues.VARIABLE.realValue(tagValue.getB());
            var.bind(this);
            String query = var.get(CodeValues.STRING);
            for (ActionTag.Option opt : action.getTag(id).options()) {
                if (opt.text().equals(query)) return (T) opt.id();
            }
        }
        return o.apply(tagValue.getA());
    }

    public void sleep(int dur) {
        if (dur == 0) return; // TODO: warn the user about useless code
        trace.cancel = true;
        Bukkit.getScheduler().runTaskLater(Hypersquare.instance, () -> executor.continueEval(trace), dur);
    }
}
