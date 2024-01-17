package hypersquare.hypersquare.menu.codeblockmenus;

import hypersquare.hypersquare.dev.Actions;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.item.PlayerActionItems;
import hypersquare.hypersquare.menu.system.Menu;
import hypersquare.hypersquare.menu.system.MenuItem;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hypersquare.hypersquare.Hypersquare.mm;

public class PlayerActionMenu {
    public static void open(Player player) {
        Menu menu = new Menu(player, Component.text("Player Actions"), 5);

        // Loop through all categories
        for (PlayerActionItems playerActionItem : PlayerActionItems.values()) {
            if (playerActionItem.category != null) continue; // Skip if not a category
            int slot = playerActionItem.slot;

            // Clicking a category
            MenuItem item = new MenuItem(playerActionItem.build()).onClick(() -> {
                Utilities.sendSecondaryMenuSound(player);

                if (PlayerActionItems.getActions(playerActionItem).isEmpty()) return; // In case there are no actions

                // Strip Color Codes
                Pattern pattern = Pattern.compile("<[^>]+?>");
                Matcher matcher = pattern.matcher(mm.serialize(playerActionItem.getName()));

                // Create a new gui for the category
                Menu categoryGui = new Menu(player,
                        Component.text("Player Action> " + matcher.replaceAll("")),
                        5
                );
                // Loop through all actions in the category
                for (Action action : Actions.actions) {
                    if (action.getCategory() != playerActionItem) continue;
                    MenuItem actionItem = new MenuItem(action.item()).onClick(() -> {
                        Utilities.sendSuccessClickMenuSound(player);
                        Block block = player.getTargetBlock(null, 5);
                        Utilities.setAction(block, action.getId(), player);
                    });
                    categoryGui.addItem(actionItem);
                }

                // Open the category GUI
                categoryGui.open();
            });

            menu.slot(slot, item);
        }

        menu.open();
    }
}
