package hypersquare.hypersquare.listeners;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.utils.Utilities;
import net.minecraft.network.chat.PlayerChatMessage;
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
import org.bukkit.event.entity.PlayerDeathEvent;
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
            if (Hypersquare.eventCache.get(plotID).contains("Join")) {
                player.sendMessage("join event");
            }
        }

    }

    public static void Leave(Player player) {
        int plotID = Utilities.getPlotID(player.getWorld());
        if (Hypersquare.eventCache.get(plotID).contains("Leave")) {
            player.sendMessage("leave event");
        }
    }

    public static void Rejoin(Player player) {
        int plotID = Utilities.getPlotID(player.getWorld());
        if (Hypersquare.eventCache.get(plotID).contains("Rejoin")) {

            player.sendMessage("rejoin event");
        }
    }


    @EventHandler
    public void Walk(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            if (event.getFrom().getBlock() != event.getTo().getBlock() && !player.isSprinting() && !player.isFlying() && !player.isSwimming()) {
                int plotID = Utilities.getPlotID(player.getWorld());
                if (Hypersquare.eventCache.get(plotID).contains("Walk")) {
                    player.sendMessage("walk event");
                }
            }

        }
    }

    @EventHandler
    public void Chat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("Chat")) {
                player.sendMessage("chat event");
            }
        }
    }

    @EventHandler
    public void PackLoad(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("PackLoad")) {
                if (event.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) {
                    player.sendMessage("pack load event");
                }
            }
            if (Hypersquare.eventCache.get(plotID).contains("PackDecline")) {
                if (event.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED) {
                    player.sendMessage("pack decline event");
                }
            }
        }
    }

    @EventHandler
    public void Interact(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("RightClick")) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                    if (event.getHand() == EquipmentSlot.HAND) {
                        player.sendMessage("right click event");
                    }
            }
            if (Hypersquare.eventCache.get(plotID).contains("LeftClick")) {
                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
                    player.sendMessage("left click event");
            }
        }
    }

    @EventHandler
    public void InteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (event.getRightClicked() instanceof Player) {
                if (Hypersquare.eventCache.get(plotID).contains("ClickPlayer")) {
                    if (event.getHand() == EquipmentSlot.HAND) {
                        player.sendMessage("click player event");
                    }
                }
            } else {
                if (Hypersquare.eventCache.get(plotID).contains("ClickEntity")) {
                    player.sendMessage("click entity event");

                }
            }
        }
    }

    @EventHandler
    public void PlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("PlaceBlock")) {
                player.sendMessage("block place event");
            }
        }
    }

    @EventHandler
    public void PlaceBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("BreakBlock")) {
                player.sendMessage("block break event");
            }
        }
    }

    @EventHandler
    public void SwapHands(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("SwapHands")) {
                player.sendMessage("swap hands event");
            }
        }
    }

    @EventHandler
    public void ChangeSlot(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("ChangeSlot")) {
                player.sendMessage("change slot event");
            }
        }
    }

    @EventHandler
    public void Tame(EntityTameEvent event) {
        Player player = (Player) event.getOwner();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("TameMob")) {
                player.sendMessage("tame event");
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
                if (Hypersquare.eventCache.get(plotID).contains("Jump")) {
                    player.sendMessage("jump event");
                }
            }
        }
    }

    @EventHandler
    public void Sneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("Sneak")) {
                if (event.isSneaking()) {
                    player.sendMessage("sneak event");
                }
            }
            if (Hypersquare.eventCache.get(plotID).contains("Unsneak")) {
                if (!event.isSneaking()) {
                    player.sendMessage("unsneak event");
                }
            }
        }
    }

    @EventHandler
    public void Sprint(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("StartSprint")) {
                if (event.isSprinting()) {
                    player.sendMessage("start sprint event");
                }
            }
            if (Hypersquare.eventCache.get(plotID).contains("StopSprint")) {
                if (!event.isSprinting()) {
                    player.sendMessage("stop sprint event");
                }
            }
        }
    }

    @EventHandler
    public void Flying(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("StartFly")) {
                if (event.isFlying()) {
                    player.sendMessage("start fly event");
                }
            }
            if (Hypersquare.eventCache.get(plotID).contains("StopFly")) {
                if (!event.isFlying()) {
                    player.sendMessage("stop fly event");
                }
            }
        }
    }

    @EventHandler
    public void Riptide(PlayerRiptideEvent event) {
        Player player = event.getPlayer();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("Riptide")) {
                player.sendMessage("riptide event");
            }
        }
    }

    @EventHandler
    public void Dismount(EntityDismountEvent event) {
        Player player = (Player) event.getDismounted();
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("Dismount")) {
                player.sendMessage("dismount event");
            }
        }
    }

    @EventHandler
    public void HorseJump(HorseJumpEvent event) {
        Player player = (Player) event.getEntity().getPassengers().get(0);
        if (Hypersquare.mode.get(player).equals("playing")) {
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("HorseJump")) {
                player.sendMessage("horse jump event");
            }
        }
    }


//    @EventHandler
//    public void VechileJump(Vechile event) {
//        Player player = event.getPlayer();
//        if (event.getFrom().getBlockY() < event.getTo().getBlockY()) {
//            if (Hypersquare.mode.get(player).equals("playing")) {
//                int plotID = Utilities.getPlotID(player.getWorld());
//                if (Hypersquare.eventCache.get(plotID).contains("Jump")) {
//                    player.sendMessage("jump event");
//                }
//            }
//        }
//    }
}



