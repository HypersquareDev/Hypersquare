package hypercubed.hypercubed;

import org.bukkit.plugin.java.JavaPlugin;

public final class Hypercubed extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new join(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
