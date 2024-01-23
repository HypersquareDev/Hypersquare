package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.dev.action.EmptyAction;
import hypersquare.hypersquare.dev.action.player.GiveItemsAction;
import hypersquare.hypersquare.dev.action.player.SendMessageAction;
import hypersquare.hypersquare.dev.event.player.JoinEvent;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.item.Event;

import java.util.List;
import java.util.Objects;

public class Events {
    public static List<Event> events = List.of(
            new JoinEvent()
    );

    public static Event getEvent(String id) {
        for (Event event : events) {
            if (Objects.equals(event.getId(), id)) return event;
        }
        return null;
    }
}
