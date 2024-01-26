package hypersquare.hypersquare.listener;

import com.google.gson.JsonObject;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.Actions;
import hypersquare.hypersquare.dev.CodeItems;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.dev.codefile.CodeFileHelper;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.codefile.data.CodeData;
import hypersquare.hypersquare.dev.codefile.data.CodeLineData;
import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.dev.value.impl.NumberValue;
import hypersquare.hypersquare.dev.value.impl.StringValue;
import hypersquare.hypersquare.dev.value.impl.TextValue;
import hypersquare.hypersquare.dev.value.impl.VariableValue;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.item.IfPlayerItems;
import hypersquare.hypersquare.item.PlayerActionItems;
import hypersquare.hypersquare.item.PlayerEventItems;
import hypersquare.hypersquare.item.SetVariableItems;
import hypersquare.hypersquare.menu.CodeblockMenu;
import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.util.Utilities;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.*;
import org.bukkit.block.Sign;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

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

                CodeFile codeFile = new CodeFile(event.getPlayer());
                int listIndex = CodeFileHelper.getCodelineListIndex(sign.getLocation().clone().add(1, 0, 0), codeFile.getCodeData());
                CodeActionData actionData = CodeFileHelper.getActionAt(sign.getLocation().clone().add(1, 0, 0), codeFile.getCodeData());
                CodeLineData line = codeFile.getCodeData().codelines.get(listIndex);
                // null means it's the thread starter
                if (actionData == null) switch (line.type) {
                        case "player_event": {
                            CodeblockMenu.open(
                                    event.getPlayer(), event.getClickedBlock().getLocation(),
                                    "Player Event", "Events", 5,
                                    PlayerEventItems.values(), false
                            );
                            break;
                        }
                    }
                else switch (actionData.codeblock) {
                        case "player_action": {
                            CodeblockMenu.open(
                                    event.getPlayer(), event.getClickedBlock().getLocation(),
                                    "Player Action", "Actions", 5,
                                    PlayerActionItems.values(), true
                            );
                            break;
                        }
                        case "if_player": {
                            CodeblockMenu.open(
                                    event.getPlayer(), event.getClickedBlock().getLocation(),
                                    "If Player", "Conditions", 3,
                                    IfPlayerItems.values(), true
                            );
                            break;
                        }
                        case "set_variable": {
                            CodeblockMenu.open(
                                    event.getPlayer(), event.getClickedBlock().getLocation(),
                                    "Set Variable", "Variables", 5,
                                    SetVariableItems.values(), true
                            );
                            break;
                        }
                    }
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
                    Utilities.sendError(event.getPlayer(), "Couldn't find this action in the registry (corrupted plot?)");
                    throw new NullPointerException("Bad CodeFile! (Invalid action ID: " + actionData.action + ")");
                }
                event.getPlayer().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_BARREL_OPEN, 0.75f, 1);
                action.actionMenu(actionData).open(event.getPlayer(), event.getClickedBlock().getLocation().subtract(0, 1, 0));
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

    @EventHandler
    public void onRClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!Hypersquare.mode.get(player).equals("coding")
            && !Hypersquare.mode.get(player).equals("building")) return;

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
    public void onChat(AsyncChatEvent event) {
        if (!Hypersquare.mode.get(event.getPlayer()).equals("coding")) return;
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) return;

        JsonObject json = CodeValues.getVarItemData(item);
        if (json == null) return;

        //noinspection rawtypes
        CodeValues value = CodeValues.getType(json);
        if (value == null) return;
        event.setCancelled(true);
        String raw = PlainTextComponentSerializer.plainText().serialize(event.message());
        Object v;
        try {
            v = value.fromString(raw, value.fromItem(item));
        } catch (Exception ignored) { // i want to show the exception message as a hint but red said no >:(
            Utilities.sendError(event.getPlayer(), "Invalid input: '" + raw + "'");
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
