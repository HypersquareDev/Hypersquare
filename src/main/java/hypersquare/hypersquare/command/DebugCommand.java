package hypersquare.hypersquare.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DebugCommand implements CommandExecutor {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player player) {
            CodeFile file = new CodeFile(player.getWorld());
            player.sendMessage(gson.toJson(file.getCodeData().toJson()));
        }
        return true;
    }
}
