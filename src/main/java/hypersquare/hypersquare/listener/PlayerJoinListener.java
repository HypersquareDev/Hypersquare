package hypersquare.hypersquare.listener;

import hypersquare.hypersquare.*;
import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.plot.PlayerDatabase;
import hypersquare.hypersquare.util.Utilities;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&9◆ &bHypersquare &9◆"),ChatColor.AQUA + "hypersquare.my.pebble.host",20,60,20);
        player.sendMessage( ChatColor.translateAlternateColorCodes('&', "&9◆ &bWelcome back to Hypersquare.&9 ◆") );
        event.setJoinMessage(ChatColor.GRAY + player.getName() + " joined.");

        List<String> welcome = new ArrayList<>();
        welcome.add("#00CED1&lHypersquare public beta");
        welcome.add("");
        welcome.add("#00FFFFHey there,");
        welcome.add("");
        welcome.add("#00CED1Welcome to our Beta Minecraft Server!");
        welcome.add("");
        welcome.add("Just a quick heads-up:");
        welcome.add("");
        welcome.add("#87CEEB- Work in Progress: #FFFFFFNot all code blocks and events are in place yet. We're actively adding new features to enhance your experience.");
        welcome.add("");
        welcome.add("#87CEEB- Report Bugs: #FFFFFFIf you spot any bugs, please let us know. Your input helps us improve! Join our #4169E1§lDiscord #FFFFFFfor bug reports: https://discord.gg/m6uHxSUyKX");
        welcome.add("");
        welcome.add("#87CEEB- Server Lag: #FFFFFFWe're aware of lag issues due to budget constraints. We're doing our best to optimize performance.");
        welcome.add("");
        welcome.add("#00FFFFThanks for being a part of our community. Let's make this server awesome together!");
        welcome.add("");

        Utilities.sendMultiMessage(player,welcome);


    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) throws IOException {
        Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.GRAY + player.getName() + " left.");

    }

}
