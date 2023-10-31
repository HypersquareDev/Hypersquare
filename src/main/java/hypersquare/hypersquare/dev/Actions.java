package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.items.Action;
import hypersquare.hypersquare.items.actions.GiveItemsAction;

import java.util.List;

public class Actions {
    public static List<Action> actions  = List.of(
            new GiveItemsAction()
    );
}
