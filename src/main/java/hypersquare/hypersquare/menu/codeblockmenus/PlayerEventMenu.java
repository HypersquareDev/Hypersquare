package hypersquare.hypersquare.menu.codeblockmenus;

import hypersquare.hypersquare.item.PlayerEventItems;
import hypersquare.hypersquare.menu.system.Menu;
import hypersquare.hypersquare.menu.system.MenuItem;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hypersquare.hypersquare.Hypersquare.cleanMM;

public class PlayerEventMenu {
    public static void open(Player player, Location targetLocation) {
        Menu gui = new Menu(Component.text("Player Events"), 5);

        // Loop through all categories
        for (PlayerEventItems playerEventItem : PlayerEventItems.values()) {
            if (playerEventItem.category != null) continue; // Skip if not a category
            int slot = playerEventItem.slot;

            // Clicking a category
            MenuItem item = new MenuItem(playerEventItem.build()).onClick(() -> {
                Utilities.sendSecondaryMenuSound(player);

                // Strip Color Codes
                Pattern pattern = Pattern.compile("<[^>]+?>");
                Matcher matcher = pattern.matcher(cleanMM.serialize(playerEventItem.getName()));

                // Create a new gui for the category
                Menu categoryGui = new Menu(Component.text("Player Events > " + matcher.replaceAll("")), 5);

                // Loop through all actions in the category
                for (PlayerEventItems action : PlayerEventItems.getEvents(playerEventItem)) {
                    MenuItem actionItem = new MenuItem(action.build()).onClick(() -> {
                        Utilities.sendSuccessClickMenuSound(player);
                        Utilities.setAction(targetLocation.getBlock(), action.id, player);
                    });
                    categoryGui.addItem(actionItem);
                }

                // Open the category GUI
                categoryGui.open(player);
            });

            gui.slot(slot, item);
        }
        gui.open(player);
    }
}

