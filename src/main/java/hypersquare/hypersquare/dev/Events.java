package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.dev.code.player.event.*;
import hypersquare.hypersquare.item.event.Event;
import hypersquare.hypersquare.item.action.player.PlayerEventItems;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public enum Events implements Event {
    PLAYER_JOIN_EVENT(new PlayerJoinEvent()),
    PLAYER_LEAVE_EVENT(new PlayerLeaveEvent()),
    PLAYER_REJOIN_EVENT(new PlayerRejoinEvent()),
    PLAYER_RIGHT_CLICK(new PlayerRightClickEvent()),
    PLAYER_DROP_ITEM(new PlayerDropItemEvent()),
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
    public String getCodeblockId() {
        return v.getCodeblockId();
    }

    @Override
    public String getSignName() {
        return v.getSignName();
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
