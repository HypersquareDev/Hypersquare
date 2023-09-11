package hypersquare.hypersquare.listeners;

import hypersquare.hypersquare.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();
        ChangeGameMode.spawn(player);
        Hypersquare.plotData.put(player,Database.getPlot(player.getUniqueId().toString()));
        player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&6◆ &aEmeraldBlaze &6◆"),ChatColor.DARK_GREEN + "ip coming one day",20,60,20);
        player.sendMessage( ChatColor.translateAlternateColorCodes('&', "&4◆ &aWelcome back to EmeraldBlaze!&4 ◆") );
        event.setJoinMessage(ChatColor.GRAY + player.getName() + " joined.");

    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) throws IOException {
        Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.GRAY + player.getName() + " left.");

    }

}
