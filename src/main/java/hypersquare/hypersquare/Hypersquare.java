package hypersquare.hypersquare;

import com.onarandombox.MultiverseCore.MultiverseCore;
import hypersquare.hypersquare.Listeners.PlayerJoinListener;
import hypersquare.hypersquare.Listeners.PlayerRightClickListener;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hypersquare extends JavaPlugin {
    public static int lastUsedWorldNumber = 0;

    public static ItemManager itemManager = new ItemManager();
    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerRightClickListener(), this);
        new InventoryAPI(this).init();
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        ItemManager.initializeKey(this);
        ItemManager.registerItems();
        loadLastUsedWorldNumber();


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
