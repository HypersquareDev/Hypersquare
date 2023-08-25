package hypersquare.hypersquare;

import org.bukkit.plugin.java.JavaPlugin;

public final class Hypersquare extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Join(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
