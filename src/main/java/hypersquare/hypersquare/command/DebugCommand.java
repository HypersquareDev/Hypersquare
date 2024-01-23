package hypersquare.hypersquare.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import hypersquare.hypersquare.dev.codefile.CodeFile;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.entity.Player;

public class DebugCommand implements HyperCommand {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @Override
    public void register(CommandDispatcher<CommandSourceStack> cd) {
        cd.register(literal("debug").executes(ctx -> {
            if (ctx.getSource().getBukkitSender() instanceof Player player) {
                CodeFile file = new CodeFile(player.getWorld());
                player.sendMessage(gson.toJson(file.getCodeData().toJson()));
            }
            return DONE;
        }));
    }
}
