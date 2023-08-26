package hypersquare.hypersquare.Menus;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import hypersquare.hypersquare.Hypersquare;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class MyPlotsMenu extends Gui {
    public MyPlotsMenu(Player player) {
        super(player, "myPlots", "My Plots", 2);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        // Icons are just ItemStacks specifically for GUIs
        Icon createPlot = new Icon(Material.GREEN_STAINED_GLASS);
        createPlot.setName(ChatColor.GREEN + "" + ChatColor.BOLD + "Create New Plot");
        addItem(17, createPlot);

        createPlot.onClick(e -> {
            e.setCancelled(true);


        });
    }
}
