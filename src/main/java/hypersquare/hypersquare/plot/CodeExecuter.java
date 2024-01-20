package hypersquare.hypersquare.plot;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.dev.Actions;
import hypersquare.hypersquare.item.Action;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CodeExecuter {
    public static void runThroughCode(Location startLoc, Entity defaultTarget) {
        startLoc.add(0, 0, 1);
        while (startLoc.clone().add(0, 0, 1).getBlock().getType() != Material.AIR
                || startLoc.clone().add(0, 0, 2).getBlock().getType() != Material.AIR
                || startLoc.clone().getBlock().getType() != Material.AIR) {
            Location signLocation = startLoc.clone().add(-1, 0, 0);
            if (signLocation.getBlock().getType() == Material.OAK_WALL_SIGN) {
                Sign sign = (Sign) signLocation.getBlock().getState();
                List<Component> signInfo = sign.getSide(Side.FRONT).lines();
                if (!signInfo.isEmpty()) {

                    List<Entity> targets = new ArrayList<>(List.of());

                    switch (PlainTextComponentSerializer.plainText().serialize(signInfo.get(3))) {
                        case "Selection": {
                            defaultTarget.sendMessage("mf!");
                            break;
                        }
                        case "Default": {
                            targets.add(defaultTarget);
                            break;
                        }
                        case "AllPlayers": {
                            for (Entity entity : startLoc.getWorld().getEntities()) {
                                if (entity instanceof Player target) {
                                    if (Hypersquare.mode.get(target).equals("playing")) {
                                        targets.add(entity);
                                        break;
                                    }
                                }
                            }
                        }
                        default: {
                            targets.add(defaultTarget);
                        }
                    }
                    Barrel barrel = (Barrel) startLoc.clone().add(0, 1, 0).getBlock().getState();

                    for (Action action : Actions.actions) {
                        if (action.getSignName().equals(signInfo.get(1).insertion())) {
                            action.executeBlockAction(targets, barrel);
                        }
                    }
                }
            }
            startLoc.add(0, 0, 1);
        }
    }
}
