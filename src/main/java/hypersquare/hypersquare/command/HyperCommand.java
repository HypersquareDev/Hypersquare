package hypersquare.hypersquare.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

public interface HyperCommand {

    int DONE = 1;

    void register(CommandDispatcher<CommandSourceStack> cd);

    default LiteralArgumentBuilder<CommandSourceStack> literal(String text) {
        return LiteralArgumentBuilder.literal(text);
    }

    default <T> RequiredArgumentBuilder<CommandSourceStack, T> argument(String text, ArgumentType<T> argument) {
        return RequiredArgumentBuilder.argument(text, argument);
    }
}
