package hypersquare.hypersquare.Menus;

import com.onarandombox.MultiverseCore.MultiverseCore;
import hypersquare.hypersquare.Hypersquare;
import mc.obliviate.inventory.Gui;
import mc.obliviate.inventory.Icon;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

import static org.bukkit.Bukkit.getLogger;

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
            MultiverseCore multiverseCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

            // Clone the "world" world
            String newWorldName = "dev_" + Hypersquare.lastUsedWorldNumber;
            boolean success = multiverseCore.getMVWorldManager().cloneWorld("dev", newWorldName);

            if (success) {
                getLogger().info("Cloned world successfully!");

                // Teleport the player to the new world
                Player player = Bukkit.getPlayer(event.getPlayer().getName()); // Replace with actual player name
                if (player != null) {
                    World newWorld = Bukkit.getWorld(newWorldName);
                    Location teleportLocation = new Location(newWorld, 0, -60, 0);
                    player.teleport(teleportLocation);
                }

                // Increment and save the world number
                Hypersquare.lastUsedWorldNumber++;
                newWorldName = "play_" + Hypersquare.lastUsedWorldNumber;
                multiverseCore.getMVWorldManager().cloneWorld("play", newWorldName);
            } else {
                getLogger().warning("Failed to clone world.");
            }

        });
    }
}
