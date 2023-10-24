package hypersquare.hypersquare.serverside.commands;

import com.infernalsuite.aswm.api.exceptions.UnknownWorldException;
import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.serverside.plot.PlayerDatabase;
import hypersquare.hypersquare.serverside.plot.Plot;
import hypersquare.hypersquare.serverside.plot.PlotDatabase;
import hypersquare.hypersquare.serverside.plot.PlotManager;
import hypersquare.hypersquare.serverside.utils.Utilities;
import hypersquare.hypersquare.serverside.utils.managers.ItemManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static hypersquare.hypersquare.Hypersquare.lastUsedWorldNumber;

public class SpawnBillboard implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("hypersquare.spawnbillboard")) {
                if (args.length >= 1) {
                    switch (args[0]) {
                        case "respawn":{
                                for (Entity entity : Bukkit.getWorld("world").getEntities()) {
                                    if (entity.getType() == EntityType.TEXT_DISPLAY) {
                                        entity.remove();
                                    }
                                }

                                TextDisplay display = null ;
                                Transformation transformation = null;

                                display = (TextDisplay) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), 0, 3.5, 8.99), EntityType.TEXT_DISPLAY);
                                display.setBillboard(Display.Billboard.FIXED);
                                display.text(MiniMessage.miniMessage().deserialize("<white>Title"));
                                transformation = display.getTransformation();
                                transformation.getLeftRotation().set(new AxisAngle4f((float) Math.toRadians(180), 0, 1, 0));
                                transformation.getTranslation().set(0.5, 0, 0);
                                transformation.getScale().set(1.2,1.2,1);
                                display.setTransformation(transformation);
                                display.setBackgroundColor(display.getBackgroundColor().setAlpha(0));
                                display.setCustomName("Title");
                                display.setCustomNameVisible(false);
                                display.setShadowed(true);

                                display = (TextDisplay) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), 0, 3.25, 8.99), EntityType.TEXT_DISPLAY);
                                display.setBillboard(Display.Billboard.FIXED);
                                display.text(MiniMessage.miniMessage().deserialize("<gray>Body"));
                                transformation = display.getTransformation();
                                transformation.getLeftRotation().set(new AxisAngle4f((float) Math.toRadians(180), 0, 1, 0));
                                transformation.getTranslation().set(0.5, 0, 0);
                                transformation.getScale().set(0.75,0.75,0.75);
                                display.setTransformation(transformation);
                                display.setBackgroundColor(display.getBackgroundColor().setAlpha(0));
                                display.setCustomName("Body");
                                display.setCustomNameVisible(false);
                                display.setShadowed(true);
                                break;
                        }

                        case "title": {
                            if (args.length >= 2) {
                                for (Entity entity : Bukkit.getWorld("world").getEntities()) {
                                    if (entity.getType() == EntityType.TEXT_DISPLAY) {
                                        if (entity.getName().equals("Title")) {
                                            TextDisplay display = (TextDisplay) entity;
                                            StringBuilder sb = new StringBuilder();
                                            for (int i = 1; i < args.length; i++) {
                                                sb.append(args[i]);
                                                sb.append(" ");
                                            }
                                            String result = sb.toString().trim();
                                            player.sendMessage(result);
                                            display.text(MiniMessage.miniMessage().deserialize(result));
                                        }
                                    }
                                }
                            }
                            break;
                        }

                        case "body": {
                            if (args.length >= 2) {
                                for (Entity entity : Bukkit.getWorld("world").getEntities()) {
                                    if (entity.getType() == EntityType.TEXT_DISPLAY) {
                                        TextDisplay display = (TextDisplay) entity;
                                        if (entity.getName().equals("Body")) {

                                            StringBuilder sb = new StringBuilder();
                                            for (int i = 1; i < args.length; i++) {
                                                sb.append(args[i]);
                                                sb.append(" ");
                                            }
                                            String result = sb.toString().trim();

                                            player.sendMessage(result);
                                            display.text(MiniMessage.miniMessage().deserialize(result));
                                            Transformation transformation = display.getTransformation();
                                            transformation.getTranslation().set(0.5,result.length()/-260f,0);
                                            display.setTransformation(transformation);
                                            display.setLineWidth(260);

                                        }
                                    }
                                }
                            }
                            break;
                        }



                    }
                }
            } else {
                Utilities.sendError(player,"You do not have permission to execute this command.");
            }
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
