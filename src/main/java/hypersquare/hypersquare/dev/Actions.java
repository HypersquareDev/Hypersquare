package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.dev.actions.EmptyAction;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.dev.actions.GiveItemsAction;

import java.util.List;
import java.util.Objects;

public class Actions {
    public static List<Action> actions = List.of(
            new EmptyAction(),
            new GiveItemsAction()
    );

    public static Action getAction(String id) {
        for (Action action : actions) {
            if (Objects.equals(action.getId(), id)) return action;
        }
        return null;
    }
}
