package hypersquare.hypersquare;

import hypersquare.hypersquare.serverside.codeblockmenuitems.PlayerActionItems;
import hypersquare.hypersquare.serverside.commands.*;
import hypersquare.hypersquare.serverside.commands.PlotCommands;
import hypersquare.hypersquare.serverside.commands.TabCompleters.PlotCommandsComplete;
import hypersquare.hypersquare.serverside.codeblockmenuitems.PlayerEventItems;
import hypersquare.hypersquare.serverside.listeners.*;
import hypersquare.hypersquare.serverside.dev.CodeItems;
import hypersquare.hypersquare.serverside.dev.CreatePlotMenuItems;
import hypersquare.hypersquare.serverside.plot.MoveEntities;
import hypersquare.hypersquare.serverside.plot.PlayerDatabase;
import hypersquare.hypersquare.serverside.plot.PlotDatabase;
import hypersquare.hypersquare.serverside.utils.managers.CommandManager;
import hypersquare.hypersquare.serverside.utils.managers.ItemManager;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public final class Hypersquare extends JavaPlugin {
    public static int lastUsedWorldNumber;
    private CommandManager commandManager;


    public static HashMap<Player, World> lastDeathLoc = new HashMap<>();
    public static HashMap<Player, List> plotData = new HashMap<>();

    public static HashMap<Player,String> mode = new HashMap<>();
    public static Map<Player, List<Location>> visitedLocationsMap = new HashMap<>();
    public static Map<Player, Boolean> teleportFlagMap = new HashMap<>();
    public static Map<Integer, List<Object>> loadedPlots = new HashMap<>();

    public static Map<Integer, HashMap<String,String>> eventCache = new HashMap<>();
    public static HashMap<UUID,HashMap<String,Integer>> localPlayerData = new HashMap<>();



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
        pm.registerEvents(new PlayerMoveListener(),this);
        pm.registerEvents(new PlaytimeEventExecuter(), this);
        new InventoryAPI(this).init();
        ItemManager.initializeKey(this);
        ItemManager.registerItems();
        loadLastUsedWorldNumber();
        commandManager = new CommandManager(this);
        registerCommands(commandManager);
        CreatePlotMenuItems.init();
        PlotDatabase plotDatabase = new PlotDatabase();
        PlayerDatabase playerDatabase = new PlayerDatabase();
        CodeItems.register();
        PlayerEventItems.initItems();
        MoveEntities.entityLoop();
        PlayerActionItems.initItems();
    }

    @Override
    public void onDisable() {
        saveLastUsedWorldNumber();
    }

    public void registerCommands(CommandManager commandManager){

        commandManager.registerCommand("join", new JoinCommand());
        commandManager.registerCommand("dev", new DevCommand());
        commandManager.registerCommand("locate", new LocateCommand());
        commandManager.registerCommand("find", new LocateCommand());
        commandManager.registerCommand("spawn", new SpawnCommand());
        commandManager.registerCommand("s", new SpawnCommand());
        commandManager.registerCommand("build", new BuildCommand());
        commandManager.registerCommand("play", new PlayCommand());
        commandManager.registerCommand("fs", new FlightSpeedCommand());
        commandManager.registerCommand("plot" , new PlotCommands());
        commandManager.registerCommand("p" , new PlotCommands());
        commandManager.registerCommand("editspawn", new EditSpawn());
        commandManager.registerCommand("fly", new FlyCommand());
        commandManager.registerCommand("dumplots", new DeleteAllPlotsCommand());
        commandManager.registerCommand("giveplot", new GivePlotsCommand());

        //Tab Completers

        getCommand("plot").setTabCompleter(new PlotCommandsComplete());
        getCommand("p").setTabCompleter(new PlotCommandsComplete());

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
