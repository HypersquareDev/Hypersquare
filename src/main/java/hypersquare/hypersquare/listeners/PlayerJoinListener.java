package hypersquare.hypersquare.listeners;

import hypersquare.hypersquare.*;
import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.plot.PlayerDatabase;
import org.bukkit.Bukkit;
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
        Hypersquare.mode.put(player,"at spawn");
        if (!PlayerDatabase.playerExists(player.getUniqueId()))
        {
            PlayerDatabase.addPlayer(player.getUniqueId(),2,1,0,0,0);
        }
        ChangeGameMode.spawn(player);
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
