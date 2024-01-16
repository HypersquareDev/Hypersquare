package hypersquare.hypersquare.debug;

import static hypersquare.hypersquare.Hypersquare.mm;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Debugger {
    public static void sendDebug(String... messages) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("hypersquare.debug")) {
                for (String message : messages) {
                    player.sendMessage(mm.deserialize("<b><red>!</red></b> <white>" + message));
                }
            }
        }
    }
}
