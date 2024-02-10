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
public enum Targets {
    SELECTED(Event.class, "Current Selection", Material.NETHER_STAR, Colors.GREEN_LIGHT, "The currently selected\nentity.", (_, ctx) -> ctx.selection()),

    DEFAULT_PLAYER(
        PlayerEvent.class, "Default Player", Material.POTATO, Colors.GREEN_LIGHT,
        "The player that triggered\nthe event.", (PlayerEvent e, ExecutionContext _) -> of(e.getPlayer())),
    KILLER(
        EntityDeathEvent.class, "Killer", Material.IRON_SWORD, Colors.RED_DARK,
        "The entity that killed the\nevent victim.", (EntityDeathEvent e, ExecutionContext _) -> of(e.getEntity().getKiller())),
    DAMAGER(
        EntityDamageByEntityEvent.class, "Damager", Material.STONE_SWORD, Colors.RED,
        "The entity that damaged the\nevent victim.", (EntityDamageByEntityEvent e, ExecutionContext _) -> of(e.getDamager())),
    SHOOTER(ProjectileHitEvent.class, "Shooter", Material.BOW, Colors.YELLOW_LIGHT,
        "The player that fired a\nprojectile in this event.", (ProjectileHitEvent e, ExecutionContext _) -> of((Entity) e.getEntity().getShooter())),
    VICTIM(EntityDamageEvent.class, "Victim", Material.SKELETON_SKULL, Colors.BLUE_LIGHT,
        "The entity that was damaged\nor killed in this event.", (EntityDamageEvent e, ExecutionContext _) -> of(e.getEntity())),
    @SuppressWarnings("unchecked") ALL_PLAYERS(PlayerEvent.class, "All Players", Material.BEACON, Colors.SKY_LIGHT,
        "Every player on this plot.", (PlayerEvent e, ExecutionContext _) -> new CodeSelection((List<Entity>)(List<?>) e.getPlayer().getWorld().getPlayers())),

    DEFAULT_ENTITY(EntityEvent.class, "Default Entity", Material.POTATO, Colors.GREEN_LIGHT,
        "The entity that triggered\nthe event.", (EntityEvent e, ExecutionContext _) -> of(e.getEntity())),
    PROJECTILE(EntityEvent.class, "Projectile", Material.ARROW, Colors.BLUE_LIGHT,
        "The projectile in this event.", (EntityEvent e, ExecutionContext _) -> {
        try {
            return of((Projectile) e.getEntity());
        } catch (Exception ignored) { return null; }
    }),
    ALL_ENTITIES(Event.class, "All Entities", Material.DIAMOND_BLOCK, Colors.SKY_LIGHT,
        "Every entity on this plot.", (e, _) -> {
        if (e instanceof PlayerEvent pE) return new CodeSelection(pE.getPlayer().getWorld().getEntities());
        else if (e instanceof EntityEvent eE) return new CodeSelection(eE.getEntity().getWorld().getEntities());
        else return null;
    }),
    // LAST_SPAWNED_ENTITY(Event.class, "Last Entity Spawned", Material.TURTLE_EGG, Colors.YELLOW_LIGHT,
    //     "The last entity spawned\nin this plot.", (e, _) -> {
    // }),
    ;

    private static CodeSelection of(Entity... entities) {
        return new CodeSelection(entities);
    }

    public final Class<? extends Event> eventType;
    private final TargetGetter getter;
    public final String[] description;
    public final TextColor color;
    public final Material mat;
    public final String name;
    Targets(Class<? extends Event> e, String n, Material m, TextColor c, String d, TargetGetter<? extends Event> g) {
        this.description = d.split("\\R");
        this.eventType = e;
        this.getter = g;
        this.color = c;
        this.name = n;
        this.mat = m;
    }

    public <T extends Event> CodeSelection get(T event, ExecutionContext ctx) {
        if (!event.getClass().isAssignableFrom(eventType)) throw new RuntimeException("Event class is not compatible with target "+name);
        //noinspection unchecked
        return getter.get(eventType.cast(event), ctx);
    }
}

interface TargetGetter<T extends Event> {
    @Nullable CodeSelection get(T e, ExecutionContext ctx);
}
