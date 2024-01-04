package hypersquare.hypersquare.menus.codeblockmenus;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.items.PlayerEventItems;
import hypersquare.hypersquare.plot.PlotDatabase;
import hypersquare.hypersquare.utils.Utilities;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static hypersquare.hypersquare.Hypersquare.mm;

public class PlayerEventMenu {
    public static Gui create() {
        Gui gui = Gui.gui()
                .title(Component.text("Player Events"))
                .rows(5)
                .create();

        // Loop through all categories
        for (PlayerEventItems playerEventItem : PlayerEventItems.values()) {
            if (playerEventItem.getCategory() != null) continue; // Skip if not a category
            int slot = playerEventItem.getSlot();

            // Clicking a category
            GuiItem item = ItemBuilder.from(playerEventItem.build()).asGuiItem(event -> {
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                Utilities.sendSecondaryMenuSound(player);



                // Strip Color Codes
                Pattern pattern = Pattern.compile("<[^>]+?>");
                Matcher matcher = pattern.matcher(mm.serialize(playerEventItem.getName()));

                // Create a new gui for the category
                Gui categoryGui = Gui.gui()
                        .title(mm.deserialize("Player Events <dark_gray>> ").append(mm.deserialize(matcher.replaceAll(""))))
                        .rows(5)
                        .create();
                int plotID = Utilities.getPlotID(player.getWorld());

                // Loop through all actions in the category
                for (PlayerEventItems action : PlayerEventItems.getEvents(playerEventItem)) {
                    GuiItem actionItem = ItemBuilder.from(action.build()).asGuiItem(event2 -> {
                        event.setCancelled(true);
                        PlotDatabase.updateEventsCache(plotID);
                        if (Hypersquare.eventCache.get(plotID).containsValue(action.getId())){
                            Utilities.sendError(player,"This event already exists in the plot.");
                            player.closeInventory();
                            player.teleport(Utilities.parseLocation(Utilities.getKeyFromValue(Hypersquare.eventCache.get(plotID),action.getId()),player.getWorld()).add(-1,0,0));
                        } else {
                            Utilities.sendSuccessClickMenuSound(player);
                            Block block = player.getTargetBlock(null, 5);
                            Utilities.setAction(block, action.getId(), player);
                        }
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

