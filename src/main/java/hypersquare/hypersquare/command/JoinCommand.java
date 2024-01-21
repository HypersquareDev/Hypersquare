package hypersquare.hypersquare.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import hypersquare.hypersquare.plot.ChangeGameMode;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements HyperCommand {
    @Override
    public void register(CommandDispatcher<CommandSourceStack> cd) {
        cd.register(literal("join").then(argument("id", IntegerArgumentType.integer(0)).executes(ctx -> {
            CommandSender sender = ctx.getSource().getBukkitSender();
            if (sender instanceof Player player) {
                int plotID = ctx.getArgument("id", Integer.class);
                ChangeGameMode.playMode(player, plotID);
            } else sender.sendMessage("This command can only be used by players.");
            return DONE;
        })));
    }
}
