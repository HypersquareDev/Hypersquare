package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.dev.event.player.PlayerJoinEvent;
import hypersquare.hypersquare.dev.event.player.PlayerLeaveEvent;
import hypersquare.hypersquare.dev.event.player.PlayerRightClickEvent;
import hypersquare.hypersquare.item.Event;
import hypersquare.hypersquare.item.PlayerEventItems;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public enum Events implements Event {
    PLAYER_JOIN_EVENT(new PlayerJoinEvent()),
    PLAYER_LEAVE_EVENT(new PlayerLeaveEvent()),
    PLAYER_RIGHT_CLICK(new PlayerRightClickEvent())
    ;

    final Event v;
    Events(Event v) {
        this.v = v;
    }

    public static Event getEvent(String id) {
        for (Event event : Events.values()) {
            if (Objects.equals(event.getId(), id)) return event;
        }
        return null;
    }

    @Override
    public String getId() {
        return v.getId();
    }

    @Override
    public String getSignName() {
        return v.getSignName();
    }

    @Override
    public String getName() {
        return v.getName();
    }

    @Override
    public PlayerEventItems getCategory() {
        return v.getCategory();
    }

    @Override
    public ItemStack item() {
        return v.item();
    }
}
