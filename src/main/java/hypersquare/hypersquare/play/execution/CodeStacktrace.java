package hypersquare.hypersquare.play.execution;

import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.play.CodeSelection;
import hypersquare.hypersquare.play.CodeVariableScope;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CodeStacktrace {
    public final List<Frame> frames;
    public final int plotId;
    public final CodeVariableScope scope = new CodeVariableScope();
    public boolean cancelAll = false;

    public CodeStacktrace(int plotId, Frame frame) {
        frames = new LinkedList<>();
        frames.add(frame);
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
        return frames.isEmpty() || cancelAll;
    }

    public void popFrame() {
        frames.remove(frames.size() - 1);
    }

    public void pushFrame(Frame frame) {
        frames.add(frame);
    }

    public static class Frame {
        public final List<CodeActionData> actions;
        public final CodeSelection selection;
        public final HashMap<String, Object> tempData = new HashMap<>();
        public int position = 0;

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

        public CodeActionData current() {
            return actions.get(position - 1);
        }
    }
}
