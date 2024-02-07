package hypersquare.hypersquare.play;

import hypersquare.hypersquare.dev.ActionTag;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.dev.value.impl.VariableValue;
import oshi.util.tuples.Pair;

import java.util.List;
import java.util.function.Function;

public record ExecutionContext(
        CodeSelection selection,
        ActionArguments args,
        CodeStacktrace trace,
        List<CodeActionData> containing,
        Action action, CodeActionData data
) {
    public static final CodeVariableScope globalScope = new CodeVariableScope();

    public CodeVariableScope getScope(VariableValue.Scope scope) {
        return switch (scope) {
            case THREAD -> trace.scope;
            case GLOBAL -> globalScope;
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
}
