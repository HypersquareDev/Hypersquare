package hypersquare.hypersquare.listener;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.util.component.BasicComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PaperServerListPingListener implements Listener {
    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        event.setMaxPlayers(event.getNumPlayers() + 1);
        String motd = Hypersquare.instance.getConfig().getString("MOTD");
        if (motd == null) {
            motd = "<gray><i>No MOTD! Whoops.";
        }
        event.motd(Hypersquare.cleanMM.deserialize(motd));
    }
}
