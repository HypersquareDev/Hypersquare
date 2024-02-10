package hypersquare.hypersquare.listener;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.Events;
import hypersquare.hypersquare.play.CodeSelection;
import hypersquare.hypersquare.play.execution.CodeExecutor;
import hypersquare.hypersquare.util.PlotUtilities;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayModeListener implements Listener {

    private boolean cannotExecute(Player player) {
        return !Hypersquare.mode.get(player).equals("playing");
    }

    /* PLOT AND SERVER EVENTS */

    @EventHandler
    public void playerChatEvent(AsyncChatEvent event) {
        Player player = event.getPlayer();
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        executor.trigger(Events.PLAYER_CHAT_EVENT, new CodeSelection(player));
    }

    /* TODO: Uncomment once resource pack action is implemented
    @EventHandler
    public void playerPackLoadEvent(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        if (event.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) {
            executor.trigger(Events.PLAYER_PACK_LOAD_EVENT, new CodeSelection(player));
        }
        if (event.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
            executor.trigger(Events.PLAYER_PACK_DECLINE_EVENT, new CodeSelection(player));
        }
    }
    */

    /* CLICK EVENTS */

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            if (event.getHand() == EquipmentSlot.HAND)
                executor.trigger(Events.PLAYER_RIGHT_CLICK_EVENT, new CodeSelection(player));
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
            executor.trigger(Events.PLAYER_LEFT_CLICK_EVENT, new CodeSelection(player));
    }

    @EventHandler
    public void playerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        // TODO: assign getRightClicked() to the event victim
        if (event.getRightClicked() instanceof Player) {
            if (event.getHand() == EquipmentSlot.HAND)
                executor.trigger(Events.PLAYER_CLICK_PLAYER_EVENT, new CodeSelection(player));
        } else executor.trigger(Events.PLAYER_CLICK_ENTITY_EVENT, new CodeSelection(player));
    }

    @EventHandler
    public void playerPlaceBlockEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        executor.trigger(Events.PLAYER_PLACE_BLOCK_EVENT, new CodeSelection(player));
    }

    @EventHandler
    public void playerBreakBlockEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        executor.trigger(Events.PLAYER_BREAK_BLOCK_EVENT, new CodeSelection(player));
    }

    @EventHandler
    public void playerSwapHandsEvent(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        executor.trigger(Events.PLAYER_SWAP_HANDS_EVENT, new CodeSelection(player));
    }

    @EventHandler
    public void playerChangeSlotEvent(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        executor.trigger(Events.PLAYER_CHANGE_SLOT_EVENT, new CodeSelection(player));
    }

    @EventHandler
    public void playerTameEvent(EntityTameEvent event) {
        Player player = (Player) event.getOwner();
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        executor.trigger(Events.PLAYER_TAME_MOB_EVENT, new CodeSelection(player));
    }

    /* MOVEMENT EVENTS */

    @EventHandler
    public void playerWalkEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (cannotExecute(player)) return;
        if (event.getFrom().getBlock() != event.getTo().getBlock() && !player.isSprinting() && !player.isFlying() && !player.isSwimming()) {
            CodeExecutor executor = PlotUtilities.getExecutor(player);
            executor.trigger(Events.PLAYER_WALK_EVENT, new CodeSelection(player));
        }
    }

    @EventHandler
    public void playerJumpEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getFrom().getBlockY() < event.getTo().getBlockY()) {
            if (cannotExecute(player)) return;
            CodeExecutor executor = PlotUtilities.getExecutor(player);
            executor.trigger(Events.PLAYER_JUMP_EVENT, new CodeSelection(player));
        }
    }

    @EventHandler
    public void playerSneakEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        if (event.isSneaking()) executor.trigger(Events.PLAYER_SNEAK_EVENT, new CodeSelection(player));
        else executor.trigger(Events.PLAYER_UNSNEAK_EVENT, new CodeSelection(player));
    }


    @EventHandler
    public void playerSprintEvent(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        if (event.isSprinting()) executor.trigger(Events.PLAYER_START_SPRINT_EVENT, new CodeSelection(player));
        else executor.trigger(Events.PLAYER_STOP_SPRINT_EVENT, new CodeSelection(player));
    }

    @EventHandler
    public void playerFlightEvent(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        if (event.isFlying()) executor.trigger(Events.PLAYER_START_FLY_EVENT, new CodeSelection(player));
        else executor.trigger(Events.PLAYER_STOP_FLY_EVENT, new CodeSelection(player));
    }

    @EventHandler
    public void playerRiptideEvent(PlayerRiptideEvent event) {
        Player player = event.getPlayer();
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        executor.trigger(Events.PLAYER_RIPTIDE_EVENT, new CodeSelection(player));
    }

    @EventHandler
    public void playerDismountEvent(VehicleExitEvent event) {
        if (!(event.getExited() instanceof Player player)) return;
        // We can cast because we already made sure it's a player
        if (cannotExecute((Player) event.getExited())) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        executor.trigger(Events.PLAYER_DISMOUNT_EVENT, new CodeSelection(player));
    }

    @EventHandler
    public void playerHorseJumpEvent(HorseJumpEvent event) {
        Entity entity = event.getEntity().getPassengers().get(0);
        if (!(entity instanceof Player player)) return;
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        executor.trigger(Events.PLAYER_HORSE_JUMP_EVENT, new CodeSelection(player));
    }

    // TODO: Vehicle Jump

    /* ITEM EVENTS */

    @EventHandler
    public void playerClickMenuSlotEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (cannotExecute(player)) return;
        CodeExecutor executor = PlotUtilities.getExecutor(player);
        if (event.getClickedInventory() != player.getInventory())
            executor.trigger(Events.PLAYER_CLICK_MENU_SLOT_EVENT, new CodeSelection(player));

        if (event.getClickedInventory() == player.getInventory())
            executor.trigger(Events.PLAYER_CLICK_INV_SLOT_EVENT, new CodeSelection(player));

    }

    @EventHandler
    public void playerDropItemListener(PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        if (cannotExecute(p)) return;
        PlotUtilities.getExecutor(p).trigger(Events.PLAYER_DROP_ITEM_EVENT, new CodeSelection(p));
    }

    // TODO: Consume Item
    // TODO: Break Item
    // TODO: Close Inventory
    // TODO: Fish Event

    /* DAMAGE EVENTS */

    // TODO: Take Damage
    // TODO: Damage Player
    // TODO: Damage Entity
    // TODO: Entity Damage Player
    // TODO: Heal
    // TODO: Shoot Bow
    // TODO: Shoot Projectile
    // TODO: Projectile Hit (block or entity)
    // TODO: Projectile Damage Player
    // TODO: Potion Cloud Applies Potion to Player

    /* DEATH EVENTS */
    // TODO: Death
    // TODO: Kill Player
    // TODO: Revived by Totem
    // TODO: Kill Mob
    // TODO: Mob Kill Player
    // TODO: Respawn
}
