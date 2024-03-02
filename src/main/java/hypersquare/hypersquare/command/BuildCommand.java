package hypersquare.hypersquare.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import hypersquare.hypersquare.play.error.HSException;
import hypersquare.hypersquare.plot.ChangeGameMode;
import hypersquare.hypersquare.util.PlotUtilities;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements HyperCommand {
    @Override
    public void register(CommandDispatcher<CommandSourceStack> cd) {
        cd.register(literal("build").executes(this::run));
        cd.register(literal("b").executes(this::run));
    }

    private int run(CommandContext<CommandSourceStack> ctx){
        CommandSender sender = ctx.getSource().getBukkitSender();
        if (sender instanceof Player player) {
            int plotID = PlotUtilities.getPlotId(player.getWorld());
            if (plotID != 0) ChangeGameMode.buildMode(player, plotID);
            else HSException.sendError(player, "You must be on a plot!");
        } else sender.sendMessage("This command can only be used by players.");
        return DONE;
    }
}
