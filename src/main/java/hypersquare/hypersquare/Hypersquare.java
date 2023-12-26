package hypersquare.hypersquare;

import hypersquare.hypersquare.commands.*;
import hypersquare.hypersquare.commands.PlotCommands;
import hypersquare.hypersquare.commands.TabCompleters.PlotCommandsComplete;
import hypersquare.hypersquare.commands.codeValues.Text;
import hypersquare.hypersquare.listeners.*;
import hypersquare.hypersquare.dev.CodeItems;
import hypersquare.hypersquare.plot.MoveEntities;
import hypersquare.hypersquare.plot.PlayerDatabase;
import hypersquare.hypersquare.plot.PlotDatabase;
import hypersquare.hypersquare.plot.PlotStats;
import hypersquare.hypersquare.utils.managers.CommandManager;

import mc.obliviate.inventory.InventoryAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;
import java.util.logging.Logger;

public final class Hypersquare extends JavaPlugin {
    public static String DB_PASS;
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
    public static HashMap<UUID,Long> cooldownMap = new HashMap<>();
    public static int plotVersion = 5;

    public static MiniMessage mm = MiniMessage.miniMessage();

    private final String serverAddress = "0.0.0.0";
    private final int serverPort = 25566;
    public static Logger logger = Logger.getLogger(Hypersquare.class.getName());

    public static Plugin instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getConfig().addDefault("DB_PASS", "");
        DB_PASS = (String) getConfig().get("DB_PASS");
        PlotDatabase plotDatabase = new PlotDatabase();
        PlayerDatabase playerDatabase = new PlayerDatabase();
        PlotStats plotStats = new PlotStats();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerRightClickListener(), this);
        pm.registerEvents(new PlayerRespawnListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new DevEvents(), this);
        pm.registerEvents(new PlayerGoToSpawnEvent(), this);
        pm.registerEvents(new PlayerMoveListener(),this);
        pm.registerEvents(new PlaytimeEventExecuter(), this);
        pm.registerEvents(new CodePlacement(), this);
        pm.registerEvents(new Text(),this);
        new InventoryAPI(this).init();

        loadLastUsedWorldNumber();
        commandManager = new CommandManager(this);
        registerCommands(commandManager);

        CodeItems.register();
        MoveEntities.entityLoop();
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
        commandManager.registerCommand("text",new Text());
        commandManager.registerCommand("txt", new Text());
        commandManager.registerCommand("ping", new PingCommand());

        //Tab Completers

        getCommand("plot").setTabCompleter(new PlotCommandsComplete());
        getCommand("p").setTabCompleter(new PlotCommandsComplete());

    }




    private void loadLastUsedWorldNumber() {
        // Load the last used world number from the configuration file

            lastUsedWorldNumber = PlotDatabase.getRecentPlotID();

    }

    private void saveLastUsedWorldNumber() {
        // Save the last used world number to the configuration file
        PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
    }


}
