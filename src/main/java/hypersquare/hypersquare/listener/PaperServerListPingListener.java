package hypersquare.hypersquare.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PaperServerListPingListener implements Listener {
    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        event.setMaxPlayers(event.getNumPlayers() + 1);
    }
}
