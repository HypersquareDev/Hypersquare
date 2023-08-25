package hypersquare.hypersquare.Menus;

import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class MyPlotsMenu extends Gui {
    public MyPlotsMenu(Player player) {
        super(player, "myPlots", "My Plots", 4);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        // Icons are just ItemStacks specifically for GUIs
        Icon testIcon = new Icon(Material.STONE);
        addItem(0, testIcon);

        testIcon.onClick(e -> {
            e.setCancelled(true);
            player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 1, 0.75f);
            // I think the second parameter is pitch? Someone test this and confirm, if not switch the two parameters
        });
    }
}
