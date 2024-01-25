package hypersquare.hypersquare.play;

import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.value.impl.VariableValue;

import java.util.List;

public record ExecutionContext(CodeSelection selection, ActionArguments args, CodeStacktrace trace, List<CodeActionData> containing,
                               hypersquare.hypersquare.dev.action.Action action) {
    public CodeVariableScope getScope(VariableValue.Scope scope) {
        switch (scope) {
            case THREAD -> {
                return trace.scope;
            }
            default -> throw new IllegalStateException();
        }
    }
}
