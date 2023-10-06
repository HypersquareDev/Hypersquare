package hypersquare.hypersquare.listeners;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class PlaytimeEventExecuter implements Listener {
    static List<String> eventList = new ArrayList();
    public static void ExecuteEvent(String[] PlotEvents){
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

    public static void Join(Player player){
        if (Hypersquare.mode.get(player).equals("playing")) {
                int plotID = Utilities.getPlotID(player.getWorld());
                if (Hypersquare.eventCache.get(plotID).contains("Join")) {
                    player.sendMessage("join event");
                }
            }

        }
    public static void Leave(Player player){
            int plotID = Utilities.getPlotID(player.getWorld());
            if (Hypersquare.eventCache.get(plotID).contains("Leave")) {
                player.sendMessage("leave event");
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
}
