package hypersquare.hypersquare;

import hypersquare.hypersquare.Commands.Join;
import hypersquare.hypersquare.Listeners.PlayerJoinListener;
import hypersquare.hypersquare.Listeners.PlayerRightClickListener;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Hypersquare extends JavaPlugin {
    public static int lastUsedWorldNumber;
    private CommandManager commandManager;

    public static ItemManager itemManager = new ItemManager();
    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerRightClickListener(), this);
        new InventoryAPI(this).init();
        ItemManager.initializeKey(this);
        ItemManager.registerItems();
        loadLastUsedWorldNumber();
        commandManager = new CommandManager(this);
        commandManager.registerCommand("join", new Join());

    }

    @Override
    public void onDisable() {
        saveLastUsedWorldNumber();
    }


    private void loadLastUsedWorldNumber() {
        // Load the last used world number from the configuration file
        FileConfiguration config = getConfig();
        if (config.contains("lastUsedWorldNumber")) {
            lastUsedWorldNumber = config.getInt("lastUsedWorldNumber");
        }
    }

    private void saveLastUsedWorldNumber() {
        // Save the last used world number to the configuration file
        FileConfiguration config = getConfig();
        config.set("lastUsedWorldNumber", lastUsedWorldNumber);
        saveConfig();
    }


}
