package hypersquare.hypersquare;

import hypersquare.hypersquare.Listeners.PlayerJoinListener;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hypersquare extends JavaPlugin {

    public static ItemManager itemManager = new ItemManager();

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);

        ItemManager.registerItems();
        new InventoryAPI(this).init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
