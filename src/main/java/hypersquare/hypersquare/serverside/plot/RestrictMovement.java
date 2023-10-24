package hypersquare.hypersquare.serverside.plot;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.serverside.utils.Utilities;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class RestrictMovement {

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
        commonStart = new Location(location.getWorld(), -20, 255, 0);
    }



    public static void movementCheck(Player player){
        if (Hypersquare.mode.get(player).equals("coding")) {
            commonVars(player.getLocation());
            int plotID = Utilities.getPlotID(player.getWorld());
            String plotType = player.getWorld().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class),"plotType"), PersistentDataType.STRING);

            switch (plotType){

                case "Basic" : {
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(),commonStart,basic)) {
                        Utilities.movePlayerInsidePlot(player, commonStart, basic);
                    }
                    break;
                }
                case "Large" : {
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(),commonStart,large)) {
                        Utilities.movePlayerInsidePlot(player, commonStart, large);

                    }
                    break;
                }
                case "Huge" : {
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(),commonStart,huge)) {
                        Utilities.movePlayerInsidePlot(player, commonStart, huge);
                    }
                    break;
                }
                case "Massive" : {
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(),commonStart,massive)) {
                        Utilities.movePlayerInsidePlot(player, commonStart, massive);
                    }
                    break;
                }
                case "Gigantic" : {
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(),commonStart,gigantic)) {
                        Utilities.movePlayerInsidePlot(player, commonStart, gigantic);
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
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(),commonStart.clone().add(20,0,0),basic)) {
                        Utilities.movePlayerInsidePlot(player, commonStart.clone().add(20,0,0), basic);
                    }
                    break;
                }
                case "Large" : {
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(),commonStart.clone().add(20,0,0),large)) {
                        Utilities.movePlayerInsidePlot(player, commonStart.clone().add(20,0,0), large);
                    }
                    break;
                }
                case "Huge" : {
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(),commonStart.clone().add(20,0,0),huge)) {
                        Utilities.movePlayerInsidePlot(player, commonStart.clone().add(20,0,0), huge);
                    }
                    break;
                }
                case "Massive" : {
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(),commonStart.clone().add(20,0,0),massive)) {
                        Utilities.movePlayerInsidePlot(player, commonStart.clone().add(20,0,0), massive);
                    }
                    break;
                }
                case "Gigantic" : {
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(),commonStart.clone().add(20,0,0),gigantic)) {
                        Utilities.movePlayerInsidePlot(player, commonStart.clone().add(20,0,0), gigantic);
                    }
                    break;
                }
            }
        }
    }

}
