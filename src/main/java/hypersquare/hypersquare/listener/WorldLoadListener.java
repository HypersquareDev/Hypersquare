package hypersquare.hypersquare.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldLoadListener implements Listener  {
    @EventHandler
    public void onLoadWorld(WorldLoadEvent event){
        event.getWorld().setKeepSpawnInMemory(false);
    }
}
