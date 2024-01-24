package hypersquare.hypersquare.play;

import hypersquare.hypersquare.dev.codefile.data.CodeActionData;

import java.util.List;

public record ExecutionContext(CodeSelection selection, ActionArguments args, CodeStacktrace trace, List<CodeActionData> containing) {
}
