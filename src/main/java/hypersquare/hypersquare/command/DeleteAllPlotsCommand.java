package hypersquare.hypersquare.command;

import com.mongodb.client.MongoDatabase;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.plot.PlotDatabase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

import static hypersquare.hypersquare.Hypersquare.DB_NAME;
import static hypersquare.hypersquare.Hypersquare.lastUsedWorldNumber;

public class DeleteAllPlotsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("taking a big dump");
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                player1.kick(Component.text("Kick from Hypersquare\n").color(NamedTextColor.RED).decoration(TextDecoration.BOLD, true)
                        .append(Component.text("Reason: massive dump").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false))
                );
            }
            MongoDatabase database = Hypersquare.mongoClient.getDatabase(DB_NAME);

            try {
                FileUtils.deleteDirectory(Path.of("plugins/FastAsyncWorldEdit/schematics").toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            database.drop();
            lastUsedWorldNumber = 1;
            PlotDatabase.setRecentPlotID(lastUsedWorldNumber);
            System.exit(1);
        }
        return true;
    }
}
