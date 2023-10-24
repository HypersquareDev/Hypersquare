package hypersquare.hypersquare;

import hypersquare.hypersquare.serverside.codeblockmenuitems.PlayerActionItems;
import hypersquare.hypersquare.serverside.commands.*;
import hypersquare.hypersquare.serverside.commands.PlotCommands;
import hypersquare.hypersquare.serverside.commands.TabCompleters.PlotCommandsComplete;
import hypersquare.hypersquare.serverside.codeblockmenuitems.PlayerEventItems;
import hypersquare.hypersquare.serverside.commands.TabCompleters.SpawnBillboardCommandsComplete;
import hypersquare.hypersquare.serverside.listeners.*;
import hypersquare.hypersquare.serverside.CodeItems;
import hypersquare.hypersquare.serverside.CreatePlotMenuItems;
import hypersquare.hypersquare.serverside.plot.MoveEntities;
import hypersquare.hypersquare.serverside.plot.PlayerDatabase;
import hypersquare.hypersquare.serverside.plot.PlotDatabase;
import hypersquare.hypersquare.serverside.utils.managers.CommandManager;
import hypersquare.hypersquare.serverside.utils.managers.ItemManager;
import mc.obliviate.inventory.InventoryAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

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
    public static int plotVersion = 3;


    ItemManager itemManager = new ItemManager();


    private final String serverAddress = "0.0.0.0";
    private final int serverPort = 25566;

    @Override
    public void onEnable() {
        PlotDatabase plotDatabase = new PlotDatabase();
        PlayerDatabase playerDatabase = new PlayerDatabase();
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
        commandManager.registerCommand("spawnbillboard",new SpawnBillboard());
        commandManager.registerCommand("sb",new SpawnBillboard());


        //Tab Completers

        getCommand("plot").setTabCompleter(new PlotCommandsComplete());
        getCommand("p").setTabCompleter(new PlotCommandsComplete());
        getCommand("spawnbillboard").setTabCompleter(new SpawnBillboardCommandsComplete());
        getCommand("sb").setTabCompleter(new SpawnBillboardCommandsComplete());


    }




    private void loadLastUsedWorldNumber() {
        // Load the last used world number from the configuration file

            lastUsedWorldNumber = PlotDatabase.getRecentPlotID();

    }

    private void saveLastUsedWorldNumber() {
        // Save the last used world number to the configuration file
        PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
    }
    private boolean isServerOnline(String ipAddress, int port) {
        try (Socket socket = new Socket()) {
            InetSocketAddress endpoint = new InetSocketAddress(ipAddress, port);
            socket.connect(endpoint, 1000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


}
