package hypersquare.hypersquare.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.value.CodeValue;
import hypersquare.hypersquare.dev.value.CodeValues;
import hypersquare.hypersquare.dev.value.impl.LocationValue;
import hypersquare.hypersquare.dev.value.type.DecimalNumber;
import hypersquare.hypersquare.util.Utilities;
import net.minecraft.commands.CommandSourceStack;
import org.bukkit.Material;
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
        registerLocation(cd, CodeValues.LOCATION, "location", "loc");
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
                                Utilities.sendError(player, "Invalid input: '" + v + "'");
                            }
                        }
                        return DONE;
                    })
                )
            );
        }
    }

    private void registerLocation(CommandDispatcher<CommandSourceStack> cd, CodeValues value, String... cmds) {
        for (String alias : cmds) {
            cd.register(literal(alias)
                .then(literal("get")
                    .then(argument("value", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            if (ctx.getSource().getBukkitSender() instanceof Player player) {
                                if (!Hypersquare.mode.get(player).equals("coding")) return DONE;
                                String v = StringArgumentType.getString(ctx, "value");
                                try {
                                    ItemStack item = value.getItem(value.fromString(v, null));
                                    player.getInventory().addItem(item);
                                } catch (Exception ignored) {
                                    Utilities.sendError(player, "Invalid input: '" + v + "'");
                                }
                            }
                            return DONE;
                        })
                    )
                )
                .then(literal("set")
                    .then(literal("x")
                        .then(argument("value", StringArgumentType.greedyString())
                            .executes(ctx -> {
                                if (ctx.getSource().getBukkitSender() instanceof Player player) {
                                    if (!Hypersquare.mode.get(player).equals("coding")) return DONE;
                                    if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                                        String v = StringArgumentType.getString(ctx, "value");
                                        LocationValue.HSLocation location = (LocationValue.HSLocation) value.fromItem(player.getInventory().getItemInMainHand());
                                        try {
                                            ItemStack item = value.getItem(new LocationValue.HSLocation(new DecimalNumber(Long.parseLong(v)), location.y(), location.z(), location.pitch(), location.yaw()));
                                            player.getInventory().setItemInMainHand(item);
                                        } catch (Exception ignored) {
                                            Utilities.sendError(player, "Invalid input: '" + v + "'");
                                        }
                                    }
                                }
                                return DONE;
                            })
                        )
                    )
                    .then(literal("y")
                        .then(argument("value", StringArgumentType.greedyString())
                            .executes(ctx -> {
                                if (ctx.getSource().getBukkitSender() instanceof Player player) {
                                    if (!Hypersquare.mode.get(player).equals("coding")) return DONE;
                                    if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                                        String v = StringArgumentType.getString(ctx, "value");
                                        LocationValue.HSLocation location = (LocationValue.HSLocation) value.fromItem(player.getInventory().getItemInMainHand());
                                        try {
                                            ItemStack item = value.getItem(new LocationValue.HSLocation(location.x(), new DecimalNumber(Long.parseLong(v)), location.z(), location.pitch(), location.yaw()));
                                            player.getInventory().setItemInMainHand(item);
                                        } catch (Exception ignored) {
                                            Utilities.sendError(player, "Invalid input: '" + v + "'");
                                        }
                                    }
                                }
                                return DONE;
                            })
                        )
                    )
                    .then(literal("z")
                        .then(argument("value", StringArgumentType.greedyString())
                            .executes(ctx -> {
                                if (ctx.getSource().getBukkitSender() instanceof Player player) {
                                    if (!Hypersquare.mode.get(player).equals("coding")) return DONE;
                                    if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                                        String v = StringArgumentType.getString(ctx, "value");
                                        LocationValue.HSLocation location = (LocationValue.HSLocation) value.fromItem(player.getInventory().getItemInMainHand());
                                        try {
                                            ItemStack item = value.getItem(new LocationValue.HSLocation(location.x(), location.y(), new DecimalNumber(Long.parseLong(v)), location.pitch(), location.yaw()));
                                            player.getInventory().setItemInMainHand(item);
                                        } catch (Exception ignored) {
                                            Utilities.sendError(player, "Invalid input: '" + v + "'");
                                        }
                                    }
                                }
                                return DONE;
                            })
                        )
                    )
                    .then(literal("pitch")
                        .then(argument("value", StringArgumentType.greedyString())
                            .executes(ctx -> {
                                if (ctx.getSource().getBukkitSender() instanceof Player player) {
                                    if (!Hypersquare.mode.get(player).equals("coding")) return DONE;
                                    if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                                        String v = StringArgumentType.getString(ctx, "value");
                                        LocationValue.HSLocation location = (LocationValue.HSLocation) value.fromItem(player.getInventory().getItemInMainHand());
                                        try {
                                            ItemStack item = value.getItem(new LocationValue.HSLocation(location.x(), location.y(), location.z(), new DecimalNumber(Long.parseLong(v)), location.yaw()));
                                            player.getInventory().setItemInMainHand(item);
                                        } catch (Exception ignored) {
                                            Utilities.sendError(player, "Invalid input: '" + v + "'");
                                        }
                                    }
                                }
                                return DONE;
                            })
                        )
                    )
                    .then(literal("yaw")
                        .then(argument("value", StringArgumentType.greedyString())
                            .executes(ctx -> {
                                if (ctx.getSource().getBukkitSender() instanceof Player player) {
                                    if (!Hypersquare.mode.get(player).equals("coding")) return DONE;
                                    if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                                        String v = StringArgumentType.getString(ctx, "value");
                                        LocationValue.HSLocation location = (LocationValue.HSLocation) value.fromItem(player.getInventory().getItemInMainHand());
                                        try {
                                            ItemStack item = value.getItem(new LocationValue.HSLocation(location.x(), location.y(), location.z(), location.pitch(), new DecimalNumber(Long.parseLong(v))));
                                            player.getInventory().setItemInMainHand(item);
                                        } catch (Exception ignored) {
                                            Utilities.sendError(player, "Invalid input: '" + v + "'");
                                        }
                                    }
                                }
                                return DONE;
                            })
                        )
                    )


                )
            );
        }
    }
}
