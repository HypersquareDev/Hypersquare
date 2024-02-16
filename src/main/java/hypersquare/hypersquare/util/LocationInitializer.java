package hypersquare.hypersquare.util;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationInitializer {

    public static Location getBasicLocation(World world) {
        return new Location(world, 64, -640, 64);
    }

    public static Location getLargeLocation(World world) {
        return new Location(world, 128, -640, 128);
    }

    public static Location getHugeLocation(World world) {
        return new Location(world, 256, -640, 256);
    }

    public static Location getMassiveLocation(World world) {
        return new Location(world, 512, -640, 512);
    }

    public static Location getGiganticLocation(World world) {
        return new Location(world, 1024, -640, 1024);
    }

    public static Location getCommonStartLocation(World world) {
        return new Location(world, 0, 255, 0);
    }
}
