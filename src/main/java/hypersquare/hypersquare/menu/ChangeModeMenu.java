package hypersquare.hypersquare.menu;

import hypersquare.hypersquare.plot.ChangeGameMode;
import mc.obliviate.inventory.ComponentIcon;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.ArrayList;
import java.util.List;

public class ChangeModeMenu extends Gui {
    private static ComponentIcon icon;
    private static ComponentIcon play;
    private static ComponentIcon build;
    private static ComponentIcon addToFav;
    private static ComponentIcon code;
    private static int plotID = 0;

    public ChangeModeMenu(Player player) {
        super(player, "changeMenu", "Plot Information", 3);
    }

    public static void initItems(ComponentIcon plotIcon, int plotid) {
        plotID = plotid;
        icon = plotIcon;
        play = new Icon(Material.DIAMOND).toComp();
        play.setName(Component.text("Play").color(TextColor.fromHexString("#2AD4D4")));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("Click to enter your plot").color(NamedTextColor.GRAY));
        lore.add(Component.text("in ").color(NamedTextColor.GRAY)
                .append(Component.text("play").color(TextColor.fromHexString("#2AD4D4")))
                .append(Component.text("mode").color(NamedTextColor.GRAY))
        );
        lore.add(Component.text(""));
        play.setLore(lore);

        build = new Icon(Material.GRASS_BLOCK).toComp();
        build.setName(Component.text("Build").color(TextColor.fromHexString("#2AD4D4")));
        lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("Click to enter your plot").color(NamedTextColor.GRAY));
        lore.add(Component.text("in ").color(NamedTextColor.GRAY)
                .append(Component.text("build").color(TextColor.fromHexString("#2AD4D4")))
                .append(Component.text("mode").color(NamedTextColor.GRAY))
        );
        lore.add(Component.text(""));
        build.setLore(lore);

        code = new Icon(Material.COMMAND_BLOCK).toComp();

        code.setName(Component.text("Code").color(TextColor.fromHexString("#2AD4D4")));
        lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("Click to enter your plot").color(NamedTextColor.GRAY));
        lore.add(Component.text("in ").color(NamedTextColor.GRAY)
                .append(Component.text("code").color(TextColor.fromHexString("#2AD4D4")))
                .append(Component.text("mode").color(NamedTextColor.GRAY))
        );
        lore.add(Component.text(""));
        code.setLore(lore);

        addToFav = new Icon(Material.EMERALD).toComp();

        addToFav.setName(Component.text("Add to Favorites").color(NamedTextColor.GREEN));
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        {
            addItem(4, icon);
            addItem(10, play);
            addItem(12, build);
            addItem(14, code);
            addItem(16, addToFav);
            play.onClick(e -> {
                ChangeGameMode.playMode(player, plotID);
            });
            build.onClick(e -> {
                ChangeGameMode.buildMode(player, plotID);
            });
            code.onClick(e -> {
                ChangeGameMode.devMode(player, plotID);
            });
        }
    }
}
