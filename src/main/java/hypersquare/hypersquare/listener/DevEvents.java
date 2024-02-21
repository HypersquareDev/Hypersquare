package hypersquare.hypersquare.listener;

import com.google.gson.JsonObject;
import hypersquare.hypersquare.HSKeys;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.Actions;
import hypersquare.hypersquare.dev.CodeBlocks;
import hypersquare.hypersquare.dev.CodeItems;
import hypersquare.hypersquare.dev.Events;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.dev.codefile.CodeFileHelper;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.codefile.data.CodeData;
import hypersquare.hypersquare.dev.codefile.data.CodeLineData;
import hypersquare.hypersquare.dev.target.Target;
import hypersquare.hypersquare.dev.target.TargetType;
import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.dev.value.impl.*;
import hypersquare.hypersquare.item.event.Event;
import hypersquare.hypersquare.menu.ActionTargetsMenu;
import hypersquare.hypersquare.menu.CodeblockMenu;
import hypersquare.hypersquare.menu.action.ActionMenu;
import hypersquare.hypersquare.menu.action.parameter.MenuParameter;
import hypersquare.hypersquare.play.error.HSException;
import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.plot.MoveEntities;
import hypersquare.hypersquare.util.PlotUtilities;
import hypersquare.hypersquare.util.Utilities;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;

public class DevEvents implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) {
            return;
        }
        if (event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND) {

            if (event.getClickedBlock().getLocation().getX() > -0) return;

            Material clickedMaterial = event.getClickedBlock().getType();
            if (clickedMaterial == Material.OAK_WALL_SIGN || CodeBlocks.getByMaterial(clickedMaterial) != null) {
                event.setCancelled(true);
                Location codeblockLocation = clickedMaterial == Material.OAK_WALL_SIGN ? event.getClickedBlock().getLocation().clone().add(1, 0, 0) : event.getClickedBlock().getLocation();

                CodeFile codeFile = new CodeFile(event.getPlayer());
                CodeActionData actionData = CodeFileHelper.getActionAt(codeblockLocation, codeFile.getCodeData());
                CodeLineData line = codeFile.getCodeData().codelines.get(CodeFileHelper.getCodelineListIndex(codeblockLocation, codeFile.getCodeData()));
                String id = actionData == null ? line.type : actionData.codeblock;
                Event hsEvent = Events.getEvent(line.event);

                // TODO: if (line.type.equals("func") || line.type.equals("proc")) { find all sources of the func/proc being called }

                if (event.getPlayer().isSneaking() && actionData != null && hsEvent != null) {
                    ArrayList<Target> targets = new ArrayList<>(Arrays.asList(hsEvent.compatibleTargets()).stream().filter(t -> {
                        TargetType acceptedType = TargetType.ofCodeblock(actionData.codeblock);
                        if (acceptedType == null) return false;
                        return acceptedType == t.targetType;
                    }).toList());
                    ActionTargetsMenu.open(event.getPlayer(), targets, event.getClickedBlock().getLocation());
                } else CodeblockMenu.open(id, event.getPlayer(), event.getClickedBlock().getLocation());
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1, 1.75f);
            } else if (event.getClickedBlock().getType() == Material.BARREL) {
                event.setCancelled(true);
                CodeData codeData = new CodeFile(event.getPlayer()).getCodeData();

                CodeActionData actionData = CodeFileHelper.getActionAt(event.getClickedBlock().getLocation().subtract(0, 1, 0), codeData);
                if (actionData == null) {
                    Utilities.sendRedInfo(event.getPlayer(), Component.text("Couldn't find this action in the plot (corrupted plot?)"));
                    return;
                }
                Action action = Actions.getAction(actionData.action, actionData.codeblock);
                if (action == null) {
                    HSException.sendError(event.getPlayer(), "Couldn't find this action in the registry (corrupted plot?)");
                    throw new NullPointerException("Bad CodeFile! (Invalid action ID: " + actionData.action + ")");
                }
                event.getPlayer().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_BARREL_OPEN, 0.75f, 1);
                action.actionMenu(actionData).open(event.getPlayer(), event.getClickedBlock().getLocation().subtract(0, 1, 0));
            }
        }

    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location brokenLocation = event.getBlock().getLocation();
        Material brokenMaterial = event.getBlock().getType();
        if (brokenMaterial == Material.BARREL) {
            ItemStack mainHandItem = player.getInventory().getItemInMainHand();
            if (mainHandItem.getType() == Material.AIR) return;
            CodeFile file = new CodeFile(player);
            CodeData data = file.getCodeData();
            CodeActionData actionData = CodeFileHelper.getActionAt(brokenLocation.subtract(0, 1, 0), data);
            if (actionData == null) return;
            Action action = Actions.getByData(actionData);
            if (action == null) return;
            ActionMenu menu = action.actionMenu(actionData);
            for (int i = 0; i < menu.getSize(); i++) {
                if (!menu.items.containsKey(i)) continue;
                if (menu.items.get(i) instanceof MenuParameter param) {
                    if (!param.isEmpty(actionData)) continue;
                    if (!param.isValid(mainHandItem)) continue;
                    int oldAmount = mainHandItem.getAmount();
                    // We only want to insert 1 item (as well as remove 1 item) at a time
                    mainHandItem.setAmount(1);
                    param.replaceValue(actionData, mainHandItem);
                    file.setCode(data.toJson().toString());

                    mainHandItem.setAmount(oldAmount - 1);
                    player.getInventory().setItemInMainHand(mainHandItem);
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
                    return;
                }
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
            if (!Hypersquare.mode.get(player).equals("playing")) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplode(BlockExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onRClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!Hypersquare.mode.get(player).equals("coding") && !Hypersquare.mode.get(player).equals("building")) return;

        if (event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK || event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR) {
            ItemStack eventItem = event.getItem();
            if (eventItem == null) return;

            if (eventItem.isSimilar(CodeItems.BLOCKS_SHORTCUT)) {
                event.setCancelled(true);

                Utilities.sendOpenMenuSound(player);
                Inventory inv = Bukkit.createInventory(null, 27, Component.text("Code Blocks"));
                //First row
                inv.setItem(0, CodeItems.PLAYER_EVENT_ITEM);
                inv.setItem(1, CodeItems.IF_PLAYER_ITEM);
                inv.setItem(2, CodeItems.PLAYER_ACTION_ITEM);
                inv.setItem(3, CodeItems.CALL_FUNCTION_ITEM);
                inv.setItem(4, CodeItems.START_PROCESS_ITEM);
                inv.setItem(5, CodeItems.ENTITY_EVENT_ITEM);
                inv.setItem(6, CodeItems.IF_ENTITY_ITEM);
                //Second row
                inv.setItem(9, CodeItems.CONTROL_ITEM);
                inv.setItem(10, CodeItems.SELECT_OBJECT_ITEM);
                inv.setItem(11, CodeItems.REPEAT_ITEM);
                inv.setItem(12, CodeItems.ELSE_ITEM);
                inv.setItem(18, CodeItems.SET_VARIABLE_ITEM);
                //Third row
                inv.setItem(19, CodeItems.IF_VARIABLE_ITEM);
                inv.setItem(20, CodeItems.GAME_ACTION_ITEM);
                inv.setItem(21, CodeItems.IF_GAME_ITEM);
                inv.setItem(22, CodeItems.ENTITY_ACTION_ITEM);
                inv.setItem(23, CodeItems.FUNCTION_ITEM);
                inv.setItem(24, CodeItems.PROCESS_ITEM);
                player.openInventory(inv);
            }
            if (eventItem.isSimilar(CodeItems.VALUES_INGOT)) {
                event.setCancelled(true);

                Utilities.sendOpenMenuSound(player);
                Inventory inv = Bukkit.createInventory(null, 18, Component.text("Code Values"));

                inv.setItem(0, new StringValue().emptyValue());
                inv.setItem(1, new TextValue().emptyValue());
                inv.setItem(2, new NumberValue().emptyValue());
                inv.setItem(3, new VariableValue().emptyValue());
                inv.setItem(4,new LocationValue().emptyValue());

                inv.setItem(9, new NullValue().emptyValue());

                player.openInventory(inv);
            }
        }
    }

    public static Location basic = null;
    public static Location large = null;
    public static Location huge = null;
    public static Location massive = null;
    public static Location gigantic = null;
    public static Location commonStart = null;

    public static void commonVars() {
        basic = MoveEntities.basic;
        large = MoveEntities.large;
        huge = MoveEntities.huge;
        massive = MoveEntities.massive;
        gigantic = MoveEntities.gigantic;
        commonStart = MoveEntities.commonStart;
    }

    @EventHandler
    public void onSpread(BlockFromToEvent event) {
        commonVars();
        String plotType = event.getToBlock().getWorld().getPersistentDataContainer().get(HSKeys.PLOT_TYPE, PersistentDataType.STRING);
        if (plotType == null)
            return;
        switch (plotType) {

            case "Basic": {
                if (Utilities.notWithinLocation(event.getToBlock().getLocation(), commonStart, basic)) {
                    event.setCancelled(true);
                }
                break;
            }
            case "Large": {
                if (Utilities.notWithinLocation(event.getToBlock().getLocation(), commonStart, large)) {
                    event.setCancelled(true);

                }
                break;
            }
            case "Huge": {
                if (Utilities.notWithinLocation(event.getToBlock().getLocation(), commonStart, huge)) {
                    event.setCancelled(true);
                }
                break;
            }
            case "Massive": {
                if (Utilities.notWithinLocation(event.getToBlock().getLocation(), commonStart, massive)) {
                    event.setCancelled(true);
                }
                break;
            }
            case "Gigantic": {
                if (Utilities.notWithinLocation(event.getToBlock().getLocation(), commonStart, gigantic)) {
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
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.COMMAND) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (!Hypersquare.mode.get(event.getPlayer()).equals("coding")) return;
        event.setCancelled(true);
    }
    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (!Hypersquare.mode.get(event.getPlayer()).equals("coding")) return;
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) return;

        JsonObject json = CodeValues.getVarItemData(item);
        if (json == null) return;

        CodeValues value = CodeValues.getType(json);
        if (value == null) return;
        event.setCancelled(true);
        String raw = PlainTextComponentSerializer.plainText().serialize(event.message());
        Object v;
        try {
            v = value.fromString(raw, value.fromItem(item));
        } catch (Exception ignored) {
            HSException.sendError(event.getPlayer(), "Invalid input: '" + raw + "'");
            return;
        }
        ItemStack newItem = value.getItem(v);
        event.getPlayer().getInventory().setItemInMainHand(newItem);
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
                int plotID = PlotUtilities.getPlotId(player);
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
