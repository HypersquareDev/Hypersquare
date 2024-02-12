package hypersquare.hypersquare.dev.targets;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;

public enum TargetType {
    PLAYER, ENTITY, ALL;

    public static TargetType ofEvent(Event bukkitEvent) {
        if (bukkitEvent instanceof PlayerEvent) return PLAYER;
        else if (bukkitEvent instanceof EntityEvent) return ENTITY;
        else return null;
    }
}
