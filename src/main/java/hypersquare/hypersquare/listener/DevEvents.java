package hypersquare.hypersquare.listener;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.Actions;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.dev.codefile.CodeFileHelper;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.codefile.data.CodeData;
import hypersquare.hypersquare.dev.codefile.data.CodeLineData;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.menu.codeblockmenus.PlayerActionMenu;
import hypersquare.hypersquare.menu.codeblockmenus.PlayerEventMenu;
import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class DevEvents implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) {
            return;
        }
        if (!Hypersquare.mode.get(event.getPlayer()).equals("coding")) {
            if (event.getClickedBlock().getLocation().getBlockX() < 0 && !Hypersquare.mode.get(event.getPlayer()).equals("editing spawn")) {
                event.setCancelled(true);
            }
            return;
        }
        if (event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {

            if (event.getClickedBlock().getLocation().getX() > -0) return;

            if (event.getClickedBlock().getType() == Material.OAK_WALL_SIGN) {
                event.setCancelled(true);
                Sign sign = (Sign) event.getClickedBlock().getState();
                switch (PlainTextComponentSerializer.plainText().serialize(sign.getSide(Side.FRONT).line(0))) {
                    case ("PLAYER EVENT"): {
                        PlayerEventMenu.open(event.getPlayer(), event.getClickedBlock().getLocation());
                        break;
                    }
                    case ("PLAYER ACTION"): {
                        PlayerActionMenu.open(event.getPlayer(), event.getClickedBlock().getLocation());
                        break;
                    }
                }
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1, 1.75f);
            } else if (event.getClickedBlock().getType() == Material.BARREL) {
                event.setCancelled(true);
                CodeData codeData = new CodeFile(event.getPlayer()).getCodeData();

                CodeActionData actionData = CodeFileHelper.getActionAt(event.getClickedBlock().getLocation().subtract(0, 1, 0), codeData);
                if (actionData == null) {
                    Utilities.sendRedInfo(event.getPlayer(), Component.text("An error occurred while scanning your plot code"));
                    return;
                }
                Action action = Actions.getAction(actionData.action);
                if (action == null) {
                    throw new NullPointerException("Bad CodeFile! (Invalid action ID: " + actionData.action + ")");
                }
                action.actionMenu().open(event.getPlayer(), event.getClickedBlock().getLocation().subtract(0, 1, 0));
            }
        }

    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            if (!Hypersquare.mode.get(player).equals("playing")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onExplode(BlockExplodeEvent event) {
        event.setCancelled(true);
    }

    public static Location basic = null;
    public static Location large = null;
    public static Location huge = null;
    public static Location massive = null;
    public static Location gigantic = null;
    public static Location commonStart = null;

    public static void commonVars(Location location) {
        basic = new Location(location.getWorld(), 64, -640, 64);
        large = new Location(location.getWorld(), 128, -640, 128);
        huge = new Location(location.getWorld(), 256, -640, 256);
        massive = new Location(location.getWorld(), 512, -640, 512);
        gigantic = new Location(location.getWorld(), 1024, -640, 1024);
        commonStart = new Location(location.getWorld(), 0, 255, 0);
    }

    @EventHandler
    public void onSpread(BlockFromToEvent event) {
        commonVars(event.getToBlock().getLocation());
        String plotType = event.getToBlock().getWorld().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.instance, "plotType"), PersistentDataType.STRING);
        if (plotType == null)
            return;
        switch (plotType) {

            case "Basic": {
                if (!Utilities.locationWithin(event.getToBlock().getLocation(), commonStart, basic)) {
                    event.setCancelled(true);
                }
                break;
            }
            case "Large": {
                if (!Utilities.locationWithin(event.getToBlock().getLocation(), commonStart, large)) {
                    event.setCancelled(true);

                }
                break;
            }
            case "Huge": {
                if (!Utilities.locationWithin(event.getToBlock().getLocation(), commonStart, huge)) {
                    event.setCancelled(true);
                }
                break;
            }
            case "Massive": {
                if (!Utilities.locationWithin(event.getToBlock().getLocation(), commonStart, massive)) {
                    event.setCancelled(true);
                }
                break;
            }
            case "Gigantic": {
                if (!Utilities.locationWithin(event.getToBlock().getLocation(), commonStart, gigantic)) {
                    event.setCancelled(true);
                }
                break;
            }
        }
    }

    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void creatureSpawnEvent(CreatureSpawnEvent event) {
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL ||
                event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.COMMAND
        ) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (!Hypersquare.mode.get(event.getPlayer()).equals("coding")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onSwapHands(PlayerSwapHandItemsEvent event) {
        String playerMode = Hypersquare.mode.get(event.getPlayer());
        if (!(playerMode.equals("coding") || playerMode.equals("building"))) return;

        Player player = event.getPlayer();
        if (Hypersquare.lastSwapHands.containsKey(player)) {

            // Swapping hands twice in 1 second
            if (System.currentTimeMillis() - Hypersquare.lastSwapHands.get(player) < 950
                    && System.currentTimeMillis() - Hypersquare.cooldownMap.get(player.getUniqueId()) > 1000) {
                String worldName = player.getWorld().getName();
                int plotID = Integer.parseInt(worldName.startsWith("hs.code.")
                        ? worldName.substring(8) : worldName.substring(3)
                );
                if (playerMode.equals("coding")) {
                    Hypersquare.lastDevLocation.put(player, player.getLocation());
                    ChangeGameMode.buildMode(player, plotID, true);
                } else {
                    Hypersquare.lastBuildLocation.put(player, player.getLocation());
                    ChangeGameMode.devMode(player, plotID, true);
                }
                Hypersquare.lastSwapHands.remove(player);
                Hypersquare.cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
                return;
            }
        }
        // Update last swap hands time
        Hypersquare.lastSwapHands.put(player, System.currentTimeMillis());
    }
}
