package hypersquare.hypersquare;

import hypersquare.hypersquare.commands.*;
import hypersquare.hypersquare.commands.Plot;
import hypersquare.hypersquare.listeners.*;
import hypersquare.hypersquare.dev.CodeItems;
import hypersquare.hypersquare.dev.CreatePlotMenuItems;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public final class Hypersquare extends JavaPlugin {
    public static int lastUsedWorldNumber;
    private CommandManager commandManager;
    public static HashMap<Player, World> lastDeathLoc = new HashMap<>();
    public static HashMap<Player, List> plotData = new HashMap<>();

    public static HashMap<Player,String> mode = new HashMap<>();
    public static HashMap<Location,Location> bracketLoc = new HashMap<>();

    ItemManager itemManager = new ItemManager();

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerRightClickListener(), this);
        pm.registerEvents(new PlayerRespawnListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new DevEvents(), this);
        pm.registerEvents(new PlayerBreakBlockListener(),this);
        pm.registerEvents(new PlayerGoToSpawnEvent(), this);
        new InventoryAPI(this).init();
        ItemManager.initializeKey(this);
        ItemManager.registerItems();
        loadLastUsedWorldNumber();
        commandManager = new CommandManager(this);
        registerCommands(commandManager);
        CreatePlotMenuItems.init();

        CodeItems.register();

    }

    @Override
    public void onDisable() {
        saveLastUsedWorldNumber();
    }

    public static void registerCommands(CommandManager commandManager){

        commandManager.registerCommand("join", new JoinCommand());
        commandManager.registerCommand("dev", new DevCommand());
        commandManager.registerCommand("locate", new LocateCommand());
        commandManager.registerCommand("find", new LocateCommand());
        commandManager.registerCommand("spawn", new SpawnCommand());
        commandManager.registerCommand("s", new SpawnCommand());
        commandManager.registerCommand("build", new BuildCommand());
        commandManager.registerCommand("play", new PlayCommand());
        commandManager.registerCommand("fs", new FlightSpeedCommand());
        commandManager.registerCommand("plot" , new Plot());
        commandManager.registerCommand("p" , new Plot());
        commandManager.registerCommand("editspawn", new EditSpawn());
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
