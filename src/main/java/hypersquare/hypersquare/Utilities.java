package hypersquare.hypersquare;

import org.bukkit.Color;
import org.bukkit.World;

public class Utilities {
    public static int getPlotID(World world){
        String name = world.getName();
        name = name.replace("hs.","");
        name = name.replace(".build","");
        name = name.replace(".dev","");
        int plotID = Integer.parseInt(name);
        return plotID;
    }
    public static String getPlotType(World world){
        String name = world.getName();
        name = name.replace("hs.","");
        name = name.replace(".","");
        return name;
    }

}
