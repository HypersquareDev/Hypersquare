package hypersquare.hypersquare.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.play.error.HSException;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ValueCommands implements HyperCommand {

    @Override
    public void register(CommandDispatcher<CommandSourceStack> cd) {
        register(cd, CodeValues.NULL, "null", "nul");
        register(cd, CodeValues.STRING, "string", "str");
        register(cd, CodeValues.TEXT, "text", "txt");
        register(cd, CodeValues.NUMBER, "number", "num");
        register(cd, CodeValues.VARIABLE, "variable", "var");
    }

    private void register(CommandDispatcher<CommandSourceStack> cd, CodeValues value, String... cmds) {
        for (String alias : cmds) {
            cd.register(literal(alias)
                .then(argument("value", StringArgumentType.greedyString())
                    .executes(ctx -> {
                        if (ctx.getSource().getBukkitSender() instanceof Player player) {
                            if (!Hypersquare.mode.get(player).equals("coding")) return DONE;
                            String v = StringArgumentType.getString(ctx, "value");
                            try {
                                ItemStack item = value.getItem(value.fromString(v, null));
                                player.getInventory().addItem(item);
                            } catch (Exception ignored) {
                                HSException.sendError(player, "Invalid input: '" + v + "'");
                            }
                        }
                        return DONE;
                    })
                )
            );
        }
    }
}
