package hypersquare.hypersquare;

import com.infernalsuite.aswm.api.SlimePlugin;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import hypersquare.hypersquare.dev.CodeItems;
import hypersquare.hypersquare.listener.*;
import hypersquare.hypersquare.menu.system.MenuListeners;
import hypersquare.hypersquare.plot.*;
import hypersquare.hypersquare.util.manager.CommandManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.luckperms.api.LuckPerms;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public final class Hypersquare extends JavaPlugin {
    public static String DB_PASS;
    public static String DB_NAME;
    public static MongoClient mongoClient;
    public static int lastUsedWorldNumber;
    public static final HashMap<Player, World> lastDeathLoc = new HashMap<>();
    public static final HashMap<Player, Long> lastSwapHands = new HashMap<>();
    public static final HashMap<Player, List<Document>> plotData = new HashMap<>();

    public static final HashMap<Player, Location> lastDevLocation = new HashMap<>();
    public static final HashMap<Player, Location> lastBuildLocation = new HashMap<>();

    public static final HashMap<Player, String> mode = new HashMap<>();
    public static final HashMap<Integer, List<Object>> loadedPlots = new HashMap<>();
    public static final HashMap<Integer, Long> gameUnloadTimestamp = new HashMap<>();

    public static final HashMap<Integer, HashMap<String, String>> eventCache = new HashMap<>();
    public static final HashMap<UUID, HashMap<String, Integer>> localPlayerData = new HashMap<>();
    public static final HashMap<UUID, Long> cooldownMap = new HashMap<>();
    public final static int PLOT_VERSION = 4;

    public static final MiniMessage cleanMM = MiniMessage.builder()
            .tags(TagResolver.resolver(
                    StandardTags.decorations(), StandardTags.color(), StandardTags.font(),
                    StandardTags.gradient(), StandardTags.hoverEvent(), StandardTags.rainbow(),
                    StandardTags.transition(), StandardTags.reset(), StandardTags.newline()
            )).build();

    public static final MiniMessage minimalMM = MiniMessage.builder()
            .tags(TagResolver.resolver(
                    StandardTags.decorations(), StandardTags.color(), StandardTags.font(),
                    StandardTags.gradient(), StandardTags.rainbow(),
                    StandardTags.transition(), StandardTags.reset()
            )).build();


    public static final String pluginName = "hypersquare";
    public static final MiniMessage fullMM = MiniMessage.miniMessage();

    public static Plugin instance;
    public static LuckPerms lpPlugin;
    public static SlimePlugin slimePlugin;

    public static Logger logger() {
        if (instance == null) {
            return Bukkit.getLogger();
        }
        return instance.getLogger();
    }

    /**
     * @deprecated Use Hypersquare.logger() instead.
     */
    @Deprecated @Override
    public @NotNull Logger getLogger() {
        return super.getLogger();
    }

    @Override
    public void onEnable() {
        getLogger().info("Hypersquare starting up...");
        instance = this;
        // Register Dependencies
        slimePlugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager"); // Slime
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            lpPlugin = provider.getProvider(); // LuckPerms
        }

        saveDefaultConfig();
        getConfig().addDefault("DB_PASS", "");
        DB_PASS = (String) getConfig().get("DB_PASS");
        getConfig().addDefault("DB_NAME", "devdb");
        DB_NAME = (String) getConfig().get("DB_NAME");
        mongoClient = MongoClients.create(Hypersquare.DB_PASS);
        PlotDatabase.init();
        PlayerDatabase.init();
        PlotStats.init();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerRightClickListener(), this);
        pm.registerEvents(new PlayerRespawnListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new PaperServerListPingListener(), this);
        pm.registerEvents(new MenuListeners(), this);

        pm.registerEvents(new PlayerGoToSpawnEvent(), this);
        pm.registerEvents(new DevEvents(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new CodePlacement(), this);
        pm.registerEvents(new WorldLoadListener(), this);
        pm.registerEvents(new PlayModeListener(), this);

        loadLastUsedWorldNumber();
        CommandManager.registerCommands();

        CodeItems.register();
        MoveEntities.entityLoop();
        getServer().getScheduler().runTaskTimer(Hypersquare.instance, UnloadPlotsSchedule::run, 0L, TimeUnit.SECONDS.toMillis(2L));

        // Make sure no world's spawn chunks are kept in memory
        for (World world : Bukkit.getWorlds()) {
            world.setKeepSpawnInMemory(false);
        }

        getLogger().info("Hypersquare is ready!");
    }

    @Override
    public void onDisable() {
        saveLastUsedWorldNumber();
        getLogger().info("Byeee!!");
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
