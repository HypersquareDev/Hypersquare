package hypersquare.hypersquare.Menus;

import hypersquare.hypersquare.Database;
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

    public ChangeModeMenu(Player player, Icon ploticon) {
        super(player, "changeMenu", "Plot Information", 3);
    }

    public static void initItems(Icon plotIcon) {
        icon = plotIcon;
        play = new Icon(Material.DIAMOND);
        play.setName(ChatColor.of("#2AD4D4") + "Play");
        List lore = new ArrayList();
        lore.add(ChatColor.GRAY + "");
        lore.add(ChatColor.GRAY + "Click to enter your plot");
        lore.add(ChatColor.GRAY + "in " + ChatColor.of("#2AD4D4") + "play " + ChatColor.GRAY + "mode");
        lore.add(ChatColor.GRAY + "");
        play.setLore(lore);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        {
            addItem(4, icon);
        }
    }
}
