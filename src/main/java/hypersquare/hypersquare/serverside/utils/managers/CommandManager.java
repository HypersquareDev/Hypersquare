package hypersquare.hypersquare.serverside.utils.managers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements CommandExecutor {

    private final Map<String, CommandExecutor> commands = new HashMap<>();
    private final JavaPlugin plugin;

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(String label, CommandExecutor executor) {
        commands.put(label, executor);
        plugin.getCommand(label).setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandExecutor executor = commands.get(label.toLowerCase());

        if (executor != null) {
            return executor.onCommand(sender, command, label, args);
        } else {
            sender.sendMessage("Command not found.");
            return false;
        }
    }
}
