package hypersquare.hypersquare.serverside.listeners;

import hypersquare.hypersquare.*;
import hypersquare.hypersquare.serverside.plot.ChangeGameMode;
import hypersquare.hypersquare.serverside.plot.PlayerDatabase;
import hypersquare.hypersquare.serverside.utils.Utilities;
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
        player.sendTitle(" <blue>◆ <aqua>Hypersquare <blue>◆",ChatColor.AQUA + "hypersquare.my.pebble.host",20,60,20);
        player.sendMessage( " <blue>◆ <aqua>Welcome back to Hypersquare.<blue> ◆");
        event.setJoinMessage(ChatColor.GRAY + player.getName() + " joined.");

        List<String> welcome = new ArrayList<>();
        welcome.add("<color:#00CED1>&lHypersquare public beta");
        welcome.add("");
        welcome.add("<color:#00FFFF>Hey there,");
        welcome.add("");
        welcome.add("<color:#00CED1>Welcome to our Beta Minecraft Server!");
        welcome.add("");
        welcome.add("Just a quick heads-up:");
        welcome.add("");
        welcome.add("<color:#87CEEB>- Work in Progress: <color:#FFFFFF>Not all code blocks and events are in place yet. We're actively adding new features to enhance your experience.");
        welcome.add("");
        welcome.add("<color:#87CEEB>- Report Bugs: <color:#FFFFFF>If you spot any bugs, please let us know. Your input helps us improve! Join our <color:#4169E1>§lDiscord <color:#FFFFFF>for bug reports: https://discord.gg/m6uHxSUyKX");
        welcome.add("");
        welcome.add("<color:#87CEEB>- Server Lag: <color:#FFFFFF>We're aware of lag issues due to budget constraints. We're doing our best to optimize performance.");
        welcome.add("");
        welcome.add("<color:#00FFFF>Thanks for being a part of our community. Let's make this server awesome together!");
        welcome.add("");

        Utilities.sendMultiMessage(player,welcome);


    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) throws IOException {
        Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.GRAY + player.getName() + " left.");

    }

}
