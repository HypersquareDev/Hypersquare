package hypersquare.hypersquare;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.*;

public class RestrictMovement {

    public static Location basic = null;
    public static Location large = null;
    public static Location massive = null;
    public static Location huge = null;
    public static Location gigantic = null;
    public static Location commonStart = null;

    public static void commonVars(Location location) {
        basic = new Location(location.getWorld(), 64, -640, 64);
        large = new Location(location.getWorld(), 128, -640, 128);
        massive = new Location(location.getWorld(), 256, -640, 256);
        huge = new Location(location.getWorld(), 512, -640, 512);
        gigantic = new Location(location.getWorld(), 1024, -640, 1024);
        commonStart = new Location(location.getWorld(), -20, 255, 0);
    }



    public static void movementCheck(Player player){
        if (Hypersquare.mode.get(player).equals("coding")) {
            commonVars(player.getLocation());
            player.sendMessage(basic.toString());
            int plotID = Utilities.getPlotID(player.getWorld());
            String plotType = player.getWorld().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"plotType"), PersistentDataType.STRING);
            List visitedLocations = new ArrayList();

            switch (plotType){

                case "Basic" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart,basic)) {
                        Hypersquare.teleportFlagMap.put(player, false);
                        visitedLocations.add(1);
                        Utilities.moveRecursively(player,player.getLocation(),commonStart,basic);
                    }
                    break;
                }
                case "Large" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart,large)) {
                        Hypersquare.teleportFlagMap.put(player, false);
                        visitedLocations.add(1);
                        Utilities.moveRecursively(player,player.getLocation(),commonStart,large);
                    }
                    break;
                }
                case "Massive" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart,massive)) {
                        Hypersquare.teleportFlagMap.put(player, false);
                        visitedLocations.add(1);
                        Utilities.moveRecursively(player,player.getLocation(),commonStart,massive);
                    }
                    break;
                }
                case "Huge" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart,huge)) {
                        Hypersquare.teleportFlagMap.put(player, false);
                        visitedLocations.add(1);
                        Utilities.moveRecursively(player,player.getLocation(),commonStart,huge);
                    }
                    break;
                }
                case "Gigantic" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart,gigantic)) {
                        Hypersquare.teleportFlagMap.put(player, false);
                        visitedLocations.add(1);
                        Utilities.moveRecursively(player,player.getLocation(),commonStart,gigantic);
                    }
                    break;
                }
            }
        }
        if (Hypersquare.mode.get(player).equals("building") || Hypersquare.mode.get(player).equals("playing")) {
            commonVars(player.getLocation());
            player.sendMessage(basic.toString());
            int plotID = Utilities.getPlotID(player.getWorld());
            String plotType = player.getWorld().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"plotType"), PersistentDataType.STRING);
            List visitedLocations = new ArrayList();

            switch (plotType){

                case "Basic" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart.clone().add(20,0,0),basic)) {
                        Hypersquare.teleportFlagMap.put(player, false);
                        visitedLocations.add(1);
                        Utilities.moveRecursively(player,player.getLocation(),commonStart.clone().add(20,0,0),basic);
                    }
                    break;
                }
                case "Large" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart.clone().add(20,0,0),large)) {
                        Hypersquare.teleportFlagMap.put(player, false);
                        visitedLocations.add(1);
                        Utilities.moveRecursively(player,player.getLocation(),commonStart.clone().add(20,0,0),large);
                    }
                    break;
                }
                case "Massive" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart.clone().add(20,0,0),massive)) {
                        Hypersquare.teleportFlagMap.put(player, false);
                        visitedLocations.add(1);
                        Utilities.moveRecursively(player,player.getLocation(),commonStart.clone().add(20,0,0),massive);
                    }
                    break;
                }
                case "Huge" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart.clone().add(20,0,0),huge)) {
                        Hypersquare.teleportFlagMap.put(player, false);
                        visitedLocations.add(1);
                        Utilities.moveRecursively(player,player.getLocation(),commonStart.clone().add(20,0,0),huge);
                    }
                    break;
                }
                case "Gigantic" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart.clone().add(20,0,0),gigantic)) {
                        Hypersquare.teleportFlagMap.put(player, false);
                        visitedLocations.add(1);
                        Utilities.moveRecursively(player,player.getLocation(),commonStart.clone().add(20,0,0),gigantic);
                    }
                    break;
                }
            }
        }
    }

}
