package hypersquare.hypersquare.plot;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.util.Utilities;
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
    public static Location code = null;
    public static Location codeStart = null;

    public static void commonVars(Location location) {
        basic = new Location(location.getWorld(), 64, -640, 64);
        large = new Location(location.getWorld(), 128, -640, 128);
        huge = new Location(location.getWorld(), 256, -640, 256);
        massive = new Location(location.getWorld(), 512, -640, 512);
        gigantic = new Location(location.getWorld(), 1024, -640, 1024);
        commonStart = new Location(location.getWorld(), 0, 255, 0);
        code = new Location(location.getWorld(), -111, 0, 256);
        codeStart = new Location(location.getWorld(), 1, 255, 0);
    }


    public static void movementCheck(Player player) {
        if (Hypersquare.mode.get(player).equals("coding")) {
            commonVars(player.getLocation());
            if (!Utilities.locationWithinIgnoreY(player.getLocation(), codeStart, code)) {
                Utilities.moveEntityInsidePlot(player, codeStart, code);
            }
        }
        if (Hypersquare.mode.get(player).equals("building") || Hypersquare.mode.get(player).equals("playing")) {
            commonVars(player.getLocation());
            String plotType = player.getWorld().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.instance, "plotType"), PersistentDataType.STRING);

            switch (plotType) {
                case "Basic": {
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(), commonStart, basic)) {
                        Utilities.moveEntityInsidePlot(player, commonStart, basic);
                    }
                    break;
                }
                case "Large": {
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(), commonStart, large)) {
                        Utilities.moveEntityInsidePlot(player, commonStart, large);
                    }
                    break;
                }
                case "Huge": {
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(), commonStart, huge)) {
                        Utilities.moveEntityInsidePlot(player, commonStart, huge);
                    }
                    break;
                }
                case "Massive": {
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(), commonStart, massive)) {
                        Utilities.moveEntityInsidePlot(player, commonStart, massive);
                    }
                    break;
                }
                case "Gigantic": {
                    if (!Utilities.locationWithinIgnoreY(player.getLocation(), commonStart, gigantic)) {
                        Utilities.moveEntityInsidePlot(player, commonStart, gigantic);
                    }
                    break;
                }
            }
        }
    }
}
