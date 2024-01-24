package hypersquare.hypersquare.play;

import hypersquare.hypersquare.dev.codefile.data.CodeActionData;

import java.util.ArrayList;
import java.util.List;

public class CodeStacktrace {
    public final List<Frame> frames;
    public final int plotId;

    public CodeStacktrace(int plotId, Frame frame) {
        frames = new ArrayList<>(List.of(frame));
        this.plotId = plotId;
    }

    public Frame next() {
        Frame next = frames.get(frames.size() - 1);
        if (next != null) return next;
        frames.remove(frames.size() - 1);
        if (isDone()) return null;
        return next();
    }

    public boolean isDone() {
        return frames.isEmpty();
    }

    public void popFrame() {
        frames.remove(frames.size() - 1);
    }

    public static class Frame {
        public int position = 0;
        public final List<CodeActionData> actions;
        public final CodeSelection selection;

        public Frame(List<CodeActionData> actions, CodeSelection selection) {
            this.actions = actions;
            this.selection = selection;
        }

        public CodeActionData next() {
            if (position < actions.size()) {
                CodeActionData action = actions.get(position);
                position++;
                return action;
            }
            return null;
        }
    }
}
