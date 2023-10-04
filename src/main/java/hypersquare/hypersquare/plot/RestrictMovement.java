package hypersquare.hypersquare.plot;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.utils.Utilities;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

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
            int plotID = Utilities.getPlotID(player.getWorld());
            String plotType = player.getWorld().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"plotType"), PersistentDataType.STRING);

            switch (plotType){

                case "Basic" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart,basic)) {
                        //Hypersquare.teleportFlagMap.put(player, false);
                        //Utilities.moveRecursively(player,player.getLocation(),commonStart,basic);
                        player.teleport(new Location(player.getWorld(),
                                Math.max(player.getLocation().getX(), commonStart.getX()),
                                player.getLocation().getY(),
                                Math.max(player.getLocation().getZ(), commonStart.getZ())));
                        player.teleport(new Location(player.getWorld(),
                                Math.min(player.getLocation().getX(), basic.getX()),
                                player.getLocation().getY(),
                                Math.min(player.getLocation().getZ(), basic.getZ())));
                    }
                    break;
                }
                case "Large" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart,large)) {
                        //Hypersquare.teleportFlagMap.put(player, false);
                        //Utilities.moveRecursively(player,player.getLocation(),commonStart,large);
                        player.teleport(new Location(player.getWorld(),
                                Math.max(player.getLocation().getX(), commonStart.getX()),
                                player.getLocation().getY(),
                                Math.max(player.getLocation().getZ(), commonStart.getZ())));
                        player.teleport(new Location(player.getWorld(),
                                Math.min(player.getLocation().getX(), large.getX()),
                                player.getLocation().getY(),
                                Math.min(player.getLocation().getZ(), large.getZ())));
                    }
                    break;
                }
                case "Massive" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart,massive)) {
                        //Hypersquare.teleportFlagMap.put(player, false);
                        //Utilities.moveRecursively(player,player.getLocation(),commonStart,massive);
                        player.teleport(new Location(player.getWorld(),
                                Math.max(player.getLocation().getX(), commonStart.getX()),
                                player.getLocation().getY(),
                                Math.max(player.getLocation().getZ(), commonStart.getZ())));
                        player.teleport(new Location(player.getWorld(),
                                Math.min(player.getLocation().getX(), massive.getX()),
                                player.getLocation().getY(),
                                Math.min(player.getLocation().getZ(), massive.getZ())));
                    }
                    break;
                }
                case "Huge" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart,huge)) {
                        //Hypersquare.teleportFlagMap.put(player, false);
                        //Utilities.moveRecursively(player,player.getLocation(),commonStart,huge);
                        player.teleport(new Location(player.getWorld(),
                                Math.max(player.getLocation().getX(), commonStart.getX()),
                                player.getLocation().getY(),
                                Math.max(player.getLocation().getZ(), commonStart.getZ())));
                        player.teleport(new Location(player.getWorld(),
                                Math.min(player.getLocation().getX(), huge.getX()),
                                player.getLocation().getY(),
                                Math.min(player.getLocation().getZ(), huge.getZ())));
                    }
                    break;
                }
                case "Gigantic" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart,gigantic)) {
                        //Hypersquare.teleportFlagMap.put(player, false);
                        //Utilities.moveRecursively(player,player.getLocation(),commonStart,gigantic);
                        player.teleport(new Location(player.getWorld(),
                                Math.max(player.getLocation().getX(), commonStart.getX()),
                                player.getLocation().getY(),
                                Math.max(player.getLocation().getZ(), commonStart.getZ())));
                        player.teleport(new Location(player.getWorld(),
                                Math.min(player.getLocation().getX(), gigantic.getX()),
                                player.getLocation().getY(),
                                Math.min(player.getLocation().getZ(), gigantic.getZ())));
                    }
                    break;
                }
            }
        }
        if (Hypersquare.mode.get(player).equals("building") || Hypersquare.mode.get(player).equals("playing")) {
            commonVars(player.getLocation());
            int plotID = Utilities.getPlotID(player.getWorld());
            String plotType = player.getWorld().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"plotType"), PersistentDataType.STRING);

            switch (plotType){

                case "Basic" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart.clone().add(20,0,0),basic)) {
                        //Hypersquare.teleportFlagMap.put(player, false);
                        //Utilities.moveRecursively(player,player.getLocation(),commonStart.clone().add(20,0,0),basic);
                        player.teleport(new Location(player.getWorld(),
                                Math.max(player.getLocation().getX(), commonStart.clone().getX() + 20),
                                player.getLocation().getY(),
                                Math.max(player.getLocation().getZ(), commonStart.clone().getZ())));
                        player.teleport(new Location(player.getWorld(),
                                Math.min(player.getLocation().getX(), basic.getX()),
                                player.getLocation().getY(),
                                Math.min(player.getLocation().getZ(), basic.getZ())));
                    }
                    break;
                }
                case "Large" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart.clone().add(20,0,0),large)) {
                        //Hypersquare.teleportFlagMap.put(player, false);
                        //Utilities.moveRecursively(player,player.getLocation(),commonStart.clone().add(20,0,0),large);
                        player.teleport(new Location(player.getWorld(),
                                Math.max(player.getLocation().getX(), commonStart.clone().getX() + 20),
                                player.getLocation().getY(),
                                Math.max(player.getLocation().getZ(), commonStart.clone().getZ())));
                        player.teleport(new Location(player.getWorld(),
                                Math.min(player.getLocation().getX(), large.getX()),
                                player.getLocation().getY(),
                                Math.min(player.getLocation().getZ(), large.getZ())));
                    }
                    break;
                }
                case "Massive" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart.clone().add(20,0,0),massive)) {
                        //Hypersquare.teleportFlagMap.put(player, false);
                        //Utilities.moveRecursively(player,player.getLocation(),commonStart.clone().add(20,0,0),massive);
                        player.teleport(new Location(player.getWorld(),
                                Math.max(player.getLocation().getX(), commonStart.clone().getX() + 20),
                                player.getLocation().getY(),
                                Math.max(player.getLocation().getZ(), commonStart.clone().getZ())));
                        player.teleport(new Location(player.getWorld(),
                                Math.min(player.getLocation().getX(), massive.getX()),
                                player.getLocation().getY(),
                                Math.min(player.getLocation().getZ(), massive.getZ())));
                    }
                    break;
                }
                case "Huge" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart.clone().add(20,0,0),huge)) {
                        //Hypersquare.teleportFlagMap.put(player, false);
                        //Utilities.moveRecursively(player,player.getLocation(),commonStart.clone().add(20,0,0),huge);
                        player.teleport(new Location(player.getWorld(),
                                Math.max(player.getLocation().getX(), commonStart.clone().getX() + 20),
                                player.getLocation().getY(),
                                Math.max(player.getLocation().getZ(), commonStart.clone().getZ())));
                        player.teleport(new Location(player.getWorld(),
                                Math.min(player.getLocation().getX(), huge.getX()),
                                player.getLocation().getY(),
                                Math.min(player.getLocation().getZ(), huge.getZ())));
                    }
                    break;
                }
                case "Gigantic" : {
                    if (!Utilities.locationWithin(player.getLocation(),commonStart.clone().add(20,0,0),gigantic)) {
                        //Hypersquare.teleportFlagMap.put(player, false);
                        //Utilities.moveRecursively(player,player.getLocation(),commonStart.clone().add(20,0,0),gigantic);
                        player.teleport(new Location(player.getWorld(),
                                Math.max(player.getLocation().getX(), commonStart.clone().getX() + 20),
                                player.getLocation().getY(),
                                Math.max(player.getLocation().getZ(), commonStart.clone().getZ())));
                        player.teleport(new Location(player.getWorld(),
                                Math.min(player.getLocation().getX(), gigantic.getX()),
                                player.getLocation().getY(),
                                Math.min(player.getLocation().getZ(), gigantic.getZ())));
                    }
                    break;
                }
            }
        }
    }

}
