package hypersquare.hypersquare.listener;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.plot.PlayerDatabase;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Hypersquare.mode.put(player, "at spawn");
        if (!PlayerDatabase.playerExists(player.getUniqueId())) {
            PlayerDatabase.addPlayer(player.getUniqueId(), 2, 1, 0, 0, 0);
        }
        ChangeGameMode.spawn(player);
        player.showTitle(Title.title(
                Component.text("◆").color(NamedTextColor.BLUE)
                        .append(Component.text(" Hypersquare ").color(NamedTextColor.AQUA))
                        .append(Component.text("◆").color(NamedTextColor.BLUE)),
                Component.text("mchypersquare.xyz").color(NamedTextColor.AQUA),
                Title.Times.times(Ticks.duration(20), Ticks.duration(60), Ticks.duration(20))
        ));
        player.sendMessage(
                Component.text("◆").color(NamedTextColor.BLUE)
                        .append(Component.text(" Welcome back to Hypersquare. ").color(NamedTextColor.AQUA))
                        .append(Component.text("◆").color(NamedTextColor.BLUE)));
        event.joinMessage(Component.text(player.getName() + " joined.").color(NamedTextColor.GRAY));

        Utilities.sendInfo(player, Utilities.convertToChatColor("""
                #00CED1&lHypersquare public beta
                        
                #00FFFFHey there,
                        
                #00CED1Welcome to our Beta Minecraft Server!
                        
                Just a quick heads-up:
                        
                #87CEEB- Work in Progress: #FFFFFFNot all code blocks and events are in place yet. We're actively adding new features to enhance your experience.
                        
                #87CEEB- Report Bugs: #FFFFFFIf you spot any bugs, please let us know. Your input helps us improve! Join our #4169E1§lDiscord #FFFFFFfor bug reports: https://discord.gg/m6uHxSUyKX
                        
                #87CEEB- Server Lag: #FFFFFFWe're aware of lag issues due to budget constraints. We're doing our best to optimize performance.
                        
                #00FFFFThanks for being a part of our community. Let's make this server awesome together!
                """));

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.quitMessage(Component.text(player.getName() + " left.").color(NamedTextColor.GRAY));

    }

}
