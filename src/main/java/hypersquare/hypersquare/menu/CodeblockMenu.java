package hypersquare.hypersquare.menu;

import hypersquare.hypersquare.dev.Actions;
import hypersquare.hypersquare.dev.Events;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import hypersquare.hypersquare.dev.codefile.CodeFileHelper;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.item.action.ActionMenuItem;
import hypersquare.hypersquare.item.event.Event;
import hypersquare.hypersquare.menu.system.Menu;
import hypersquare.hypersquare.menu.system.MenuItem;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hypersquare.hypersquare.Hypersquare.cleanMM;

public class CodeblockMenu {
    public static void open(Player player, Location targetLocation, String fullTitle, String shortTitle, int rows, ActionMenuItem[] items, boolean actions) {
        Menu menu = new Menu(Component.text(fullTitle + " Categories"), rows);

        // Loop through all categories
        for (ActionMenuItem item : items) {
            menu.slot(item.getSlot(), new MenuItem(item.build()).onClick(() -> {
                Utilities.sendSecondaryMenuSound(player);

                // Strip Color Codes
                Pattern pattern = Pattern.compile("<[^>]+?>");
                Matcher matcher = pattern.matcher(cleanMM.serialize(item.getName()));

                // Create a new gui for the category
                Menu categoryGui = new Menu(Component.text(shortTitle + " > " + matcher.replaceAll("")), 5);

                if (actions) {
                    // Loop through all actions in the category
                    for (Action action : Actions.values()) {
                        if (action.getCategory() != item || action.getId() == null) continue;
                        MenuItem actionItem = new MenuItem(action.item()).onClick(() -> {
                            Utilities.sendSuccessClickMenuSound(player);
                            Utilities.setAction(targetLocation.getBlock(), action.getSignName(), player);
                            CodeFileHelper.updateAction(targetLocation.clone().add(1, 0, 0), new CodeFile(player.getWorld()), action);
                        });
                        categoryGui.addItem(actionItem);
                    }
                } else {
                    // Loop through all event actions in the category
                    for (Event event : Events.values()) {
                        if (event.getCategory() != item || event.getId() == null) continue;
                        MenuItem actionItem = new MenuItem(event.item()).onClick(() -> {
                            Utilities.sendSuccessClickMenuSound(player);
                            Utilities.setAction(targetLocation.getBlock(), event.getSignName(), player);
                            CodeFileHelper.updateEvent(targetLocation.clone().add(1, 0, 0), new CodeFile(player.getWorld()), event);
                        });
                        categoryGui.addItem(actionItem);
                    }
                }
                // Open the category GUI
                categoryGui.open(player);
            }));
        }

        menu.open(player);
    }
}
