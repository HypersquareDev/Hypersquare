package hypersquare.hypersquare.debug;

import static hypersquare.hypersquare.Hypersquare.cleanMM;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Debugger {
    public static void sendDebug(String... messages) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("hypersquare.debug")) {
                for (String message : messages) {
                    player.sendMessage(cleanMM.deserialize("<b><red>!</red></b> <white>" + message));
                }
            }
        }
    }
}
