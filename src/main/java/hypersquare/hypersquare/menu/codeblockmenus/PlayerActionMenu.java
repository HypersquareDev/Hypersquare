package hypersquare.hypersquare.menu.codeblockmenus;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import hypersquare.hypersquare.dev.Actions;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.item.PlayerActionItems;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hypersquare.hypersquare.Hypersquare.mm;

public class PlayerActionMenu {
    public static Gui create() {
        Gui gui = Gui.gui()
                .title(Component.text("Player Actions"))
                .rows(5)
                .create();

        // Loop through all categories
        for (PlayerActionItems playerActionItem : PlayerActionItems.values()) {
            if (playerActionItem.category != null) continue; // Skip if not a category
            int slot = playerActionItem.slot;

            // Clicking a category
            GuiItem item = ItemBuilder.from(playerActionItem.build()).asGuiItem(event -> {
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                Utilities.sendSecondaryMenuSound(player);

                if (PlayerActionItems.getActions(playerActionItem).isEmpty()) return; // In case there are no actions

                // Strip Color Codes
                Pattern pattern = Pattern.compile("<[^>]+?>");
                Matcher matcher = pattern.matcher(mm.serialize(playerActionItem.getName()));

                // Create a new gui for the category
                Gui categoryGui = Gui.gui()
                        .title(mm.deserialize("Player Actions <dark_gray>> ").append(mm.deserialize(matcher.replaceAll(""))))
                        .rows(5)
                        .create();

                // Loop through all actions in the category
                for (Action action : Actions.actions) {
                    if (action.getCategory() != playerActionItem) continue;
                    GuiItem actionItem = ItemBuilder.from(action.item()).asGuiItem(event2 -> {
                        event.setCancelled(true);
                        Utilities.sendSuccessClickMenuSound(player);
                        Block block = player.getTargetBlock(null, 5);
                        Utilities.setAction(block, action.getId(), player);
                    });
                    categoryGui.addItem(actionItem);
                }

                // Open the category GUI
                categoryGui.open(player);
            });

            gui.setItem(slot, item);
        }

        return gui;
    }
}
