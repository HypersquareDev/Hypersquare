package hypersquare.hypersquare.menus;

import hypersquare.hypersquare.ChangeGameMode;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.ArrayList;
import java.util.List;

public class ChangeModeMenu extends Gui {
    static Icon icon = null;
    static Icon play = null;
    static Icon build = null;
    static Icon addToFav = null;
    static Icon code = null;
    static int plotID = 0;

    public ChangeModeMenu(Player player) {
        super(player, "changeMenu", "Plot Information", 3);
    }

    public static void initItems(Icon plotIcon, int plotid) {
        plotID = plotid;
        icon = plotIcon;
        play = new Icon(Material.DIAMOND);
        play.setName(ChatColor.of("#2AD4D4") + "Play");
        List lore = new ArrayList();
        lore.add(ChatColor.GRAY + "");
        lore.add(ChatColor.GRAY + "Click to enter your plot");
        lore.add(ChatColor.GRAY + "in " + ChatColor.of("#2AD4D4") + "play " + ChatColor.GRAY + "mode");
        lore.add(ChatColor.GRAY + "");
        play.setLore(lore);

        build = new Icon(Material.GRASS_BLOCK);
        build.setName(ChatColor.of("#2AD42A") + "Build");
        lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "");
        lore.add(ChatColor.GRAY + "Click to enter your plot");
        lore.add(ChatColor.GRAY + "in " + ChatColor.of("#2AD42A") + "build " + ChatColor.GRAY + "mode");
        lore.add(ChatColor.GRAY + "");
        build.setLore(lore);

        code = new Icon(Material.COMMAND_BLOCK);

        code.setName(ChatColor.of("#FFFF00") + "Code");
        lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "");
        lore.add(ChatColor.GRAY + "Click to enter your plot");
        lore.add(ChatColor.GRAY + "in " + ChatColor.YELLOW + "code " + ChatColor.GRAY + "mode");
        lore.add(ChatColor.GRAY + "");
        code.setLore(lore);

        addToFav = new Icon(Material.EMERALD);

        addToFav.setName(ChatColor.GREEN + "Add to Favorites");
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        {
            addItem(4, icon);
            addItem(10,play);
            addItem(12, build);
            addItem(14,code);
            addItem(16,addToFav);
            play.onClick(e -> {
                ChangeGameMode.playMode(player,plotID);
            });
            build.onClick(e -> {
                ChangeGameMode.buildMode(player,plotID);
            });
            code.onClick(e -> {
                ChangeGameMode.devMode(player,plotID);
            });
        }
    }
}
