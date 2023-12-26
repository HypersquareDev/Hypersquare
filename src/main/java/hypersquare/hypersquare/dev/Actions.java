package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.dev.actions.Action;
import hypersquare.hypersquare.dev.actions.GiveItemsAction;
import hypersquare.hypersquare.dev.actions.SendMessageAction;

import java.util.List;

public class Actions {
    public static List<Action> actions  = List.of(
            new GiveItemsAction(),
            new SendMessageAction()
    );
}
