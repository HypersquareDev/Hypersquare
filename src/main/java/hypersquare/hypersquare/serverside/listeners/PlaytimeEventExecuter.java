package hypersquare.hypersquare.serverside.listeners;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.serverside.plot.CodeExecution.CodeExecuter;
import hypersquare.hypersquare.serverside.utils.Utilities;
import org.bukkit.Bukkit;
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
import org.bukkit.inventory.EquipmentSlot;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.ArrayList;
import java.util.List;

public class PlaytimeEventExecuter implements Listener {
    static List<String> eventList = new ArrayList();

    public static void ExecuteEvent(String[] PlotEvents) {
        // Add short values from PlayerEventPlotAndServerEvents
        eventList.add("Join");
        eventList.add("Leave");
        eventList.add("Command");
        eventList.add("PackLoad");
        eventList.add("PackDecline");

        // Add short values from PlayerEventClickEvents
        eventList.add("RightClick");
        eventList.add("LeftClick");
        eventList.add("ClickEntity");
        eventList.add("ClickPlayer");
        eventList.add("PlaceBlock");
        eventList.add("BreakBlock");
        eventList.add("SwapHands");
        eventList.add("ChangeSlot");
        eventList.add("TameMob");

        // Add short values from PlayerEventMovementEvents
        eventList.add("Walk");
        eventList.add("Jump");
        eventList.add("Sneak");
        eventList.add("Unsneak");
        eventList.add("StartSprint");
        eventList.add("StopSprint");
        eventList.add("StartFly");
        eventList.add("StopFly");
        eventList.add("Riptide");
        eventList.add("Dismount");
        eventList.add("HorseJump");
        eventList.add("VechileJump");

        // Add short values from PlayerEventItemEvents
        eventList.add("ClickMenuSlot");
        eventList.add("ClickInvSlot");
        eventList.add("PickUpItem");
        eventList.add("DropItem");
        eventList.add("Consume");
        eventList.add("BreakItem");
        eventList.add("CloseInv");
        eventList.add("Fish");

        // Add short values from PlayerEventDamageEvents
        eventList.add("PlayerTakeDmg");
        eventList.add("PlayerDmgPlayer");
        eventList.add("DamageEntity");
        eventList.add("EntityDmgPlayer");
        eventList.add("PlayerHeal");
        eventList.add("ShootBow");
        eventList.add("ShootProjectile");
        eventList.add("ProjHit");
        eventList.add("ProjDmgPlayer");
        eventList.add("CloudImbuePlayer");

        // Add short values from PlayerEventDeathEvents
        eventList.add("Death");
        eventList.add("KillPlayer");
        eventList.add("PlayerResurrect");
        eventList.add("KillMob");
        eventList.add("MobKillPlayer");
        eventList.add("Respawn");
    }

    public static void Join(Player player) {
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("Join")) {
                player.sendMessage("join event");
                CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"Join"), player.getWorld()),player);
            }
        }

    }

    public static void Leave(Player player) {
        int plotID = Utilities.getPlotID(player.getWorld());
        if (Hypersquare.eventCache.get(plotID).containsValue("Leave")) {
            player.sendMessage("leave event");
            CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"Leave"), player.getWorld()),player);
        }
    }

    public static void Rejoin(Player player) {
        int plotID = Utilities.getPlotID(player.getWorld());
        if (Hypersquare.eventCache.get(plotID).containsValue("Rejoin")) {

            player.sendMessage("rejoin event");
            CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"Rejoin"), player.getWorld()),player);
        }
    }


    @EventHandler
    public void Walk(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            if (event.getFrom().getBlock() != event.getTo().getBlock() && !player.isSprinting() && !player.isFlying() && !player.isSwimming()) {
                int plotID = Utilities.getPlotID(player.getWorld());
                if (Hypersquare.eventCache.get(plotID).containsValue("Walk")) {
                    player.sendMessage("walk event");
                    CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"Walk"), player.getWorld()),player);
                }
            }

        }
    }

    @EventHandler
    public void Chat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("Chat")) {
                player.sendMessage("chat event");
                Bukkit.getScheduler().runTask(Hypersquare.getPlugin(Hypersquare.class), new Runnable() {
                    @Override
                    public void run() {
                        CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"Chat"), player.getWorld()),player);
                    }
                });

            }
        }
    }

    @EventHandler
    public void PackLoad(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("PackLoad")) {
                if (event.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) {
                    player.sendMessage("pack load event");
                    CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"PackLoad"), player.getWorld()),player);
                }
            }
            if (Hypersquare.eventCache.get(plotID).containsValue("PackDecline")) {
                if (event.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
                    player.sendMessage("pack decline event");
                    CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"PackDecline"), player.getWorld()),player);
                }
            }
        }
    }

    @EventHandler
    public void Interact(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("RightClick")) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                    if (event.getHand() == EquipmentSlot.HAND) {
                        player.sendMessage("right click event");
                        CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"RightClick"), player.getWorld()),player);
                    }
            }
            if (Hypersquare.eventCache.get(plotID).containsValue("LeftClick")) {
                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
                    player.sendMessage("left click event");
                CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"LeftClick"), player.getWorld()),player);
            }
        }
    }

    @EventHandler
    public void InteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (event.getRightClicked() instanceof Player) {
                if (Hypersquare.eventCache.get(plotID).containsValue("ClickPlayer")) {
                    if (event.getHand() == EquipmentSlot.HAND) {
                        player.sendMessage("click player event");
                        CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"ClickPlayer"), player.getWorld()),player);
                    }
                }
            } else {
                if (Hypersquare.eventCache.get(plotID).containsValue("ClickEntity")) {
                    player.sendMessage("click entity event");
                    CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"ClickEntity"), player.getWorld()),player);

                }
            }
        }
    }

    @EventHandler
    public void PlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("PlaceBlock")) {
                player.sendMessage("block place event");
                CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"PlaceBlock"), player.getWorld()),player);
            }
        }
    }

    @EventHandler
    public void PlaceBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("BreakBlock")) {
                player.sendMessage("block break event");
                CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"BreakBlock"), player.getWorld()),player);
            }
        }
    }

    @EventHandler
    public void SwapHands(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("SwapHands")) {
                player.sendMessage("swap hands event");
                CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"SwapHands"), player.getWorld()),player);
            }
        }
    }

    @EventHandler
    public void ChangeSlot(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("ChangeSlot")) {
                player.sendMessage("change slot event");
                CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"ChangeSlot"), player.getWorld()),player);
            }
        }
    }

    @EventHandler
    public void Tame(EntityTameEvent event) {
        Player player = (Player) event.getOwner();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("TameMob")) {
                player.sendMessage("tame event");
                CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"TameMob"), player.getWorld()),player);
            }
        }
    }

    //MOVEMENT EVENTS

    @EventHandler
    public void Jump(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getFrom().getBlockY() < event.getTo().getBlockY()) {
            if (Hypersquare.mode.get(player).equals("playing")) {
                int plotID = Utilities.getPlotID(player.getWorld());
                if (Hypersquare.eventCache.get(plotID).containsValue("Jump")) {
                    player.sendMessage("jump event");
                    CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"Jump"), player.getWorld()),player);
                }
            }
        }
    }

    @EventHandler
    public void Sneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("Sneak")) {
                if (event.isSneaking()) {
                    player.sendMessage("sneak event");
                    CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"Sneak"), player.getWorld()),player);
                }
            }
            if (Hypersquare.eventCache.get(plotID).containsValue("Unsneak")) {
                if (!event.isSneaking()) {
                    player.sendMessage("unsneak event");
                    CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"Unsneak"), player.getWorld()),player);
                }
            }
        }
    }

    @EventHandler
    public void Sprint(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("StartSprint")) {
                if (event.isSprinting()) {
                    player.sendMessage("start sprint event");
                    CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"StartSprint"), player.getWorld()),player);
                }
            }
            if (Hypersquare.eventCache.get(plotID).containsValue("StopSprint")) {
                if (!event.isSprinting()) {
                    player.sendMessage("stop sprint event");
                    CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"StopSprint"), player.getWorld()),player);
                }
            }
        }
    }

    @EventHandler
    public void Flying(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("StartFly")) {
                if (event.isFlying()) {
                    player.sendMessage("start fly event");
                    CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"StartFly"), player.getWorld()),player);
                }
            }
            if (Hypersquare.eventCache.get(plotID).containsValue("StopFly")) {
                if (!event.isFlying()) {
                    player.sendMessage("stop fly event");
                    CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"StopFly"), player.getWorld()),player);
                }
            }
        }
    }

    @EventHandler
    public void Riptide(PlayerRiptideEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("Riptide")) {
                player.sendMessage("riptide event");
                CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"Riptide"), player.getWorld()),player);
            }
        }
    }

    @EventHandler
    public void Dismount(EntityDismountEvent event) {
        Player player = (Player) event.getDismounted();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("Dismount")) {
                player.sendMessage("dismount event");
                CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"Dismount"), player.getWorld()),player);
            }
        }
    }

    @EventHandler
    public void HorseJump(HorseJumpEvent event) {
        Player player = (Player) event.getEntity().getPassengers().get(0);
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("HorseJump")) {
                player.sendMessage("horse jump event");
                CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"HorseJump"), player.getWorld()),player);
            }
        }
    }


//    @EventHandler
//    public void VechileJump(Vechile event) {
//        Player player = event.getPlayer();
//        if (event.getFrom().getBlockY() < event.getTo().getBlockY()) {
//            if (Hypersquare.mode.get(player).equals("playing")) {
//                int plotID = Utilities.getPlotID(player.getWorld());
//                if (Hypersquare.eventCache.get(plotID).containsValue()("Jump")) {
//                    player.sendMessage("jump event");
//                }
//            }
//        }
//    }

    //ITEM EVENTS
    @EventHandler
    public void ClickMenuSlot(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).containsValue("ClickMenuSlot")) {
                if (event.getClickedInventory() != player.getInventory()) {
                    player.sendMessage("click menu event");
                    CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"ClickMenuSlot"), player.getWorld()),player);
                }
            }
            if (Hypersquare.eventCache.get(plotID).containsValue("ClickInvSlot")) {
                if (event.getClickedInventory() == player.getInventory()) {
                    player.sendMessage("click inv event");
                    CodeExecuter.runThroughCode(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),"ClickInvSlot"), player.getWorld()),player);
                }
            }
        }
    }


}



