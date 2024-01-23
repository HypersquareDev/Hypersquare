package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.dev.action.EmptyAction;
import hypersquare.hypersquare.dev.action.player.SendMessageAction;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.dev.action.player.GiveItemsAction;

import java.util.List;
import java.util.Objects;

public class Actions {
    public static List<Action> actions = List.of(
            new EmptyAction(),
            new GiveItemsAction(),
            new SendMessageAction()
    );

    public static Action getAction(String id) {
        for (Action action : actions) {
            if (Objects.equals(action.getId(), id)) return action;
        }
        return null;
    }
}
