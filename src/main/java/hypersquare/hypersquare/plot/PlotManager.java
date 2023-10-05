package hypersquare.hypersquare.plot;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hypersquare.hypersquare.Hypersquare.loadedPlots;

public class PlotManager {


    public static void loadPlot(int plotID) {
        List<Object> plotData = Database.getPlotData(plotID);
        if (plotData != null) {
            loadedPlots.put(plotID, plotData);
        }
    }

    public static void unloadPlot(int plotID) {
        loadedPlots.remove(plotID);
    }

    public static List<Object> getPlotData(int plotID) {
        return loadedPlots.get(plotID);
    }

    public static String getPlotName(int plotID) {
        List<Object> plotData = getPlotData(plotID);
        if (plotData != null && plotData.size() >= 0) {
            return (String) plotData.get(0);
        }
        return null;
    }

    public static String getPlotOwner(int plotID) {
        List<Object> plotData = getPlotData(plotID);
        if (plotData != null && plotData.size() > 1) {
            return (String) plotData.get(1);
        }
        return null;
    }

    public static int getPlotNode(int plotID) {
        List<Object> plotData = getPlotData(plotID);
        if (plotData != null && plotData.size() > 2) {
            return (int) plotData.get(2);
        }
        return -1;
    }

    public static int getPlotVotes(int plotID) {
        List<Object> plotData = getPlotData(plotID);
        if (plotData != null && plotData.size() > 8) {
            return (int) plotData.get(8);
        }
        return 0;
    }
}
