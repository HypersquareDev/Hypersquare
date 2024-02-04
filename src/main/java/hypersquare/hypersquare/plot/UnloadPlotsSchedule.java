package hypersquare.hypersquare.plot;

import hypersquare.hypersquare.Hypersquare;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class UnloadPlotsSchedule implements Runnable {
    @Override
    public void run() {
        for (Document plotDocument : PlotDatabase.getAllPlots()) {
            int plotId = plotDocument.getInteger("plotID");
            if (!Hypersquare.loadedPlots.containsKey(plotId)) continue;

            // Game Unloading - Purges variables and entities
            // World Unloading - Unloads plot worlds from memory

            World buildWorld = Bukkit.getWorld("hs." + plotId);
            World devWorld = Bukkit.getWorld("hs.code." + plotId);

            tryGameUnload(plotId);

            if(buildWorld != null && buildWorld.getPlayerCount() <= 0 && devWorld != null && devWorld.getPlayerCount() <= 0) {

                if (
                    Hypersquare.gameUnloadTimestamp.containsKey(plotId)
                     && System.currentTimeMillis() - Hypersquare.gameUnloadTimestamp.get(plotId) < 1000 * 60 * 2) {
                    continue;
                }

                // Game unload should have already happened
                Bukkit.unloadWorld(buildWorld, true);
                Bukkit.unloadWorld(devWorld, true);
                Hypersquare.loadedPlots.remove(plotId);

                Hypersquare.logger().info("Unloaded plot " + plotId);
                //TODO: thread & line & game variables will be done in the gameUnload method
            }
        }
    }

    /**
     * Tries to game-unload the plot, should be called on leave events
     * @param plotId Plot ID to unload
     */
    public static void tryGameUnload(int plotId) {
        World buildWorld = Bukkit.getWorld("hs." + plotId);
        if (buildWorld == null || !buildWorld.getPlayers().isEmpty()) return;
        gameUnload(plotId, buildWorld);
    }

    public static void tryGameUnload(World world) {
        tryGameUnload(Integer.parseInt(world.getName().replace("hs.", "")));
    }

    private static void gameUnload(int plotId, @NotNull World buildWorld) {
        Hypersquare.gameUnloadTimestamp.put(plotId, System.currentTimeMillis());
        buildWorld.getEntities().listIterator().forEachRemaining(Entity::remove);
    }
}
