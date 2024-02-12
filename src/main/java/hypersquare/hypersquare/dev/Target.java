package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.play.CodeSelection;
import hypersquare.hypersquare.play.execution.ExecutionContext;
import hypersquare.hypersquare.util.color.Colors;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("rawtypes")
public enum Target {
    SELECTED(Event.class, "Current Selection", Material.NETHER_STAR, Colors.GREEN_LIGHT, "The currently selected\nentity.", (_, s) -> s),
    DEFAULT_PLAYER(
        PlayerEvent.class, "Default Player", Material.POTATO, Colors.GREEN_LIGHT,
        "The player that triggered\nthe event.", (PlayerEvent e, CodeSelection _) -> of(e.getPlayer())),
    @SuppressWarnings("unchecked") ALL_PLAYERS(PlayerEvent.class, "All Players", Material.BEACON, Colors.SKY_LIGHT,
        "Every player on this plot.", (PlayerEvent e, CodeSelection _) -> new CodeSelection((List<Entity>)(List<?>) e.getPlayer().getWorld().getPlayers())),
    DEFAULT_ENTITY(EntityEvent.class, "Default Entity", Material.POTATO, Colors.GREEN_LIGHT,
        "The entity that triggered\nthe event.", (EntityEvent e, CodeSelection _) -> of(e.getEntity())),
    ALL_ENTITIES(Event.class, "All Entities", Material.DIAMOND_BLOCK, Colors.SKY_LIGHT,
        "Every entity on this plot.", (e, _) -> {
        if (e instanceof PlayerEvent pE) return new CodeSelection(pE.getPlayer().getWorld().getEntities());
        else if (e instanceof EntityEvent eE) return new CodeSelection(eE.getEntity().getWorld().getEntities());
        else return null;
    }),

    KILLER(
        EntityDeathEvent.class, "Killer", Material.IRON_SWORD, Colors.RED_DARK,
        "The entity that killed the\nevent victim.", (EntityDeathEvent e, CodeSelection _) -> of(e.getEntity().getKiller())),
    DAMAGER(
        EntityDamageByEntityEvent.class, "Damager", Material.STONE_SWORD, Colors.RED,
        "The entity that damaged the\nevent victim.", (EntityDamageByEntityEvent e, CodeSelection _) -> of(e.getDamager())),
    SHOOTER(ProjectileHitEvent.class, "Shooter", Material.BOW, Colors.YELLOW_LIGHT,
        "The player that fired a\nprojectile in this event.", (ProjectileHitEvent e, CodeSelection _) -> of((Entity) e.getEntity().getShooter())),
    VICTIM(EntityDamageEvent.class, "Victim", Material.SKELETON_SKULL, Colors.BLUE_LIGHT,
        "The entity that was damaged\nor killed in this event.", (EntityDamageEvent e, CodeSelection _) -> of(e.getEntity())),
    PROJECTILE(EntityEvent.class, "Projectile", Material.ARROW, Colors.BLUE_LIGHT,
        "The projectile in this event.", (EntityEvent e, CodeSelection _) -> {
        try {
            return of((Projectile) e.getEntity());
        } catch (Exception ignored) { return null; }
    }),

    // LAST_SPAWNED_ENTITY(Event.class, "Last Entity Spawned", Material.TURTLE_EGG, Colors.YELLOW_LIGHT,
    //     "The last entity spawned\nin this plot.", (e, _) -> {
    // }),
    ;

    public static Target getTarget(String name) {
        for (Target t : Target.values()) {
            if (t.name().equals(name) || t.name.equals(name)) return t;
        }
        return Target.SELECTED;
    }

    private static CodeSelection of(Entity... entities) {
        return new CodeSelection(entities);
    }

    public final Class<? extends Event> eventType;
    private final TargetGetter getter;
    public final String[] description;
    public final TextColor color;
    public final Material mat;
    public final String name;
    Target(Class<? extends Event> e, String n, Material m, TextColor c, String d, TargetGetter<? extends Event> g) {
        this.description = d.split("\\R");
        this.eventType = e;
        this.getter = g;
        this.color = c;
        this.name = n;
        this.mat = m;
    }

    public <T extends Event> CodeSelection get(T event, CodeSelection s) {
        if (!event.getClass().isAssignableFrom(eventType)) throw new RuntimeException("Event class is not compatible with target "+name);
        //noinspection unchecked
        return getter.get(eventType.cast(event), s);
    }
}

interface TargetGetter<T extends Event> {
    @Nullable CodeSelection get(T e, CodeSelection s);
}
