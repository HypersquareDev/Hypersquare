package hypersquare.hypersquare.menu;

import hypersquare.hypersquare.menu.system.Menu;
import hypersquare.hypersquare.menu.system.MenuItem;
import hypersquare.hypersquare.plot.ChangeGameMode;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChangeModeMenu {
    public static void open(Player player, MenuItem plotIcon, int plotId) {
        MenuItem play = new MenuItem(Material.DIAMOND);
        play.name(Component.text("Play").color(TextColor.fromHexString("#2AD4D4")));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("Click to enter your plot").color(NamedTextColor.GRAY));
        lore.add(Component.text("in ").color(NamedTextColor.GRAY)
                .append(Component.text("play ").color(TextColor.fromHexString("#2AD4D4")))
                .append(Component.text("mode").color(NamedTextColor.GRAY))
        );
        lore.add(Component.text(""));
        play.lore(lore);

        play.onClick(() -> {
            ChangeGameMode.playMode(player, plotId);
        });

        MenuItem build = new MenuItem(Material.GRASS_BLOCK);
        build.name(Component.text("Build").color(TextColor.fromHexString("#2AD4D4")));
        lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("Click to enter your plot").color(NamedTextColor.GRAY));
        lore.add(Component.text("in ").color(NamedTextColor.GRAY)
                .append(Component.text("build ").color(TextColor.fromHexString("#2AD4D4")))
                .append(Component.text("mode").color(NamedTextColor.GRAY))
        );
        lore.add(Component.text(""));
        build.lore(lore);

        build.onClick(() -> {
            ChangeGameMode.buildMode(player, plotId);
        });

        MenuItem code = new MenuItem(Material.COMMAND_BLOCK);

        code.name(Component.text("Code").color(TextColor.fromHexString("#2AD4D4")));
        lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("Click to enter your plot").color(NamedTextColor.GRAY));
        lore.add(Component.text("in ").color(NamedTextColor.GRAY)
                .append(Component.text("code ").color(TextColor.fromHexString("#2AD4D4")))
                .append(Component.text("mode").color(NamedTextColor.GRAY))
        );
        lore.add(Component.text(""));
        code.lore(lore);

        code.onClick(() -> {
            ChangeGameMode.devMode(player, plotId);
        });

        MenuItem addToFav = new MenuItem(Material.EMERALD);

        addToFav.name(Component.text("Add to Favorites").color(NamedTextColor.GREEN));

        Menu menu = new Menu(player, Component.text("Plot Information"), 3)
                .slot(4, plotIcon)
                .slot(10, play)
                .slot(12, build)
                .slot(14, code)
                .slot(16, addToFav);
        menu.open();
    }
}
