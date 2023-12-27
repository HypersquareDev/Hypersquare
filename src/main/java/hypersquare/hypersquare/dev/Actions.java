package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.item.actions.GiveItemsAction;

import java.util.List;

public class Actions {
    public static List<Action> actions  = List.of(
            new GiveItemsAction()
    );
}
