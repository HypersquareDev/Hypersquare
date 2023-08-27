package hypersquare.hypersquare.Menus;


import com.infernalsuite.aswm.api.SlimePlugin;
import com.infernalsuite.aswm.api.exceptions.CorruptedWorldException;
import com.infernalsuite.aswm.api.exceptions.NewerFormatException;
import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import com.infernalsuite.aswm.api.loaders.SlimeLoader;
import com.infernalsuite.aswm.api.world.SlimeWorld;
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap;
import hypersquare.hypersquare.ChangeGameMode;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.Plot;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class MyPlotsMenu extends Gui {
    public static Logger logger = getLogger();
    SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
    public MyPlotsMenu(Player player) {
        super(player, "myPlots", "My Plots", 2);
    }
    @Override
    public void onOpen(InventoryOpenEvent event) {

        Icon createPlot = new Icon(Material.GREEN_STAINED_GLASS);
        createPlot.setName(ChatColor.GREEN + "" + ChatColor.BOLD + "Create New Plot");
        addItem(17, createPlot);
        SlimeLoader file = plugin.getLoader("file");


        createPlot.onClick(e -> {
            e.setCancelled(true);
            new CreatePlotsMenu((Player) event.getPlayer()).open();

        });
    }
}
