package hypersquare.hypersquare.menu.codeblockmenus;

import hypersquare.hypersquare.dev.Actions;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.dev.codefile.CodeFileHelper;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.item.PlayerActionItems;
import hypersquare.hypersquare.menu.system.Menu;
import hypersquare.hypersquare.menu.system.MenuItem;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hypersquare.hypersquare.Hypersquare.cleanMM;

public class PlayerActionMenu {
        public static void open(Player player, Location targetLocation) {
        Menu menu = new Menu(Component.text("Player Actions"), 5);

        // Loop through all categories
        for (PlayerActionItems playerActionItem : PlayerActionItems.values()) {
            if (playerActionItem.category != null) continue; // Skip if not a category
            int slot = playerActionItem.slot;

            // Clicking a category
            MenuItem item = new MenuItem(playerActionItem.build()).onClick(() -> {
                Utilities.sendSecondaryMenuSound(player);

                // Strip Color Codes
                Pattern pattern = Pattern.compile("<[^>]+?>");
                Matcher matcher = pattern.matcher(cleanMM.serialize(playerActionItem.getName()));

                // Create a new gui for the category
                Menu categoryGui = new Menu(Component.text("Player Action > " + matcher.replaceAll("")), 5);

                // Loop through all actions in the category
                for (Action action : Actions.actions) {
                    if (action.getCategory() != playerActionItem || action.getId() == null) continue;
                    MenuItem actionItem = new MenuItem(action.item()).onClick(() -> {
                        Utilities.sendSuccessClickMenuSound(player);
                        Utilities.setAction(targetLocation.getBlock(), action.getSignName(), player);
                        CodeFileHelper.updateAction(targetLocation.clone().add(1, 0, 0), new CodeFile(player.getWorld()), action.getId());
                    });
                    categoryGui.addItem(actionItem);
                }

                // Open the category GUI
                categoryGui.open(player);
            });

            menu.slot(slot, item);
        }

        menu.open(player);
    }
}
