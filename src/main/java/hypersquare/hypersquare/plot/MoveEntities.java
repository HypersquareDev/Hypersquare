package hypersquare.hypersquare.plot;

import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;

public class MoveEntities {
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

    public static void entityLoop() {
        SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Hypersquare.instance, new Runnable() {
            public void run() {

                assert plugin != null;
                for (SlimeWorld world : plugin.getLoadedWorlds()) {
                    if (Bukkit.getWorld(world.getName()) == null){
                        return;
                    }
                    for (Entity entity : Bukkit.getWorld(world.getName()).getEntities()) {
                        if (entity == null){
                            return;
                        }
                        if (entity.getType() != EntityType.PLAYER){
                            commonVars(entity.getLocation());
                            int plotID = Utilities.getPlotID(entity.getWorld());
                            String plotType = (entity).getWorld().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.instance, "plotType"), PersistentDataType.STRING);
                            if (plotType == null){
                                return;
                            }

                            switch (plotType) {

                                case "Basic": {
                                    if (!Utilities.locationWithin(entity.getLocation(), commonStart.clone().add(20, 0, 0), basic)) {
                                        Utilities.moveEntityInsidePlot(entity, commonStart.clone().add(20, 0, 0), basic);
                                    }
                                    break;
                                }
                                case "Large": {
                                    if (!Utilities.locationWithin(entity.getLocation(), commonStart.clone().add(20, 0, 0), large)) {
                                        Utilities.moveEntityInsidePlot(entity, commonStart.clone().add(20, 0, 0), large);
                                    }
                                    break;
                                }
                                case "Huge": {
                                    if (!Utilities.locationWithin(entity.getLocation(), commonStart.clone().add(20, 0, 0), huge)) {
                                        Utilities.moveEntityInsidePlot(entity, commonStart.clone().add(20, 0, 0), huge);
                                    }
                                    break;
                                }
                                case "Massive": {
                                    if (!Utilities.locationWithin(entity.getLocation(), commonStart.clone().add(20, 0, 0), massive)) {
                                        Utilities.moveEntityInsidePlot(entity, commonStart.clone().add(20, 0, 0), massive);
                                    }
                                    break;
                                }
                                case "Gigantic": {
                                    if (!Utilities.locationWithin(entity.getLocation(), commonStart.clone().add(20, 0, 0), gigantic)) {
                                        Utilities.moveEntityInsidePlot(entity, commonStart.clone().add(20, 0, 0), gigantic);
                                    }
                                    break;
                                }

                            }
                        }
                    }
                }
            }
        }, 0, 1);
    }
}
