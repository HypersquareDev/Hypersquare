package hypersquare.hypersquare.dev.codefile;

import hypersquare.hypersquare.dev.CodeBlocks;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.codefile.data.CodeData;
import hypersquare.hypersquare.dev.codefile.data.CodeLineData;
import hypersquare.hypersquare.plot.CodeBlockManagement;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Piston;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CodeFileHelper {
    @NotNull
    private static String genEmptyCodeblock(String name) {
        // PLAYER ACTION => player_action_empty
        return name.toLowerCase().replace(" ", "_") + "_empty";
    }

    private static int getCodelineWorldIndex(Location location) {
        return Math.abs(location.getBlockX() / 3) - 1;
    }

    public static int getCodeblockIndex(Location location) {
        return (int) Math.floor((double) location.getBlockZ() / 2) -1;
    }

    public static int getCodelineListIndex(Location location, CodeFile codeFile) {
        int position = getCodelineWorldIndex(location);
        CodeData plotCode = codeFile.getCodeData();

        for (CodeLineData element : plotCode.codelines) {
            if (element.position == position) {
                return plotCode.codelines.indexOf(element);
            }
        }
        return -1;
    }

    /**
     * Set index to -1 for the codeblock to be appended instead of inserted.
     */
    public static CodeData addCodeblock(Location location, String name, CodeFile code) {

        CodeData plotCode = code.getCodeData();
        CodeBlocks codeblock = CodeBlocks.getByName(name);

        if (codeblock.isThreadStarter()) {
            CodeLineData event = new CodeLineData();
            String type = CodeBlocks.getByName(name).id();
            event.type = type;
            if (codeblock.hasActions) {
                event.event = type + "_empty";
            }
            event.position = getCodelineWorldIndex(location);
            plotCode.codelines.add(event);
            return plotCode;
        }
        CodeLineData codeline = plotCode.codelines.get(getCodelineListIndex(location, code));

        List<Integer> positions;
        try {
            positions = findCodeIndex(location);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to find code positions");
            return plotCode;
        }

        CodeActionData action = new CodeActionData();
        action.action = genEmptyCodeblock(name);

        if (positions.size() == 1) {
            codeline.actions.add(positions.get(0), action);
            return plotCode;
        }

        CodeActionData parent = codeline.actions.get(positions.get(0));
        for (int i = 1; i < positions.size() - 1; i++) {
            parent = parent.actions.get(positions.get(i));
        }

        parent.actions.add(positions.get(positions.size() - 1), action);
        return plotCode;
    }

    public static CodeData removeCodeBlock(Location location, CodeFile code, boolean breakAll) {
        CodeData plotCode = code.getCodeData();

        int codelineListIndex = getCodelineListIndex(location, code);
        CodeLineData codeline = plotCode.codelines.get(codelineListIndex);

        List<Integer> positions;
        try {
            positions = findCodeIndex(location);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to find code positions");
            return plotCode;
        }

        if (positions.isEmpty()) {
            plotCode.codelines.remove(codelineListIndex);
            return plotCode;
        }

        if (positions.size() == 1) {
            int pos = positions.get(0);
            CodeActionData prev = codeline.actions.remove(pos);
            if (!breakAll) for (CodeActionData action : prev.actions) {
                codeline.actions.add(pos, action);
            }
            return plotCode;
        }

        CodeActionData parent = codeline.actions.get(positions.get(0));
        for (int i = 1; i < positions.size() - 1; i++) {
            parent = parent.actions.get(positions.get(i));
        }

        int pos = positions.get(positions.size() - 1);
        CodeActionData prev = parent.actions.remove(pos);
        if (!breakAll) for (CodeActionData action : prev.actions) {
            parent.actions.add(pos, action);
        }
        return plotCode;
    }

    public static CodeData updateAction(Location location, CodeFile code, String newAction) {
        int codeblockIndex = getCodeblockIndex(location);

        int position = getCodelineListIndex(location, code);

        if (position == -1) {
            // We are somehow updating a non-existent codeline
            Bukkit.getLogger().warning("Tried updating a non existent codeline @ " + code.world);
            return code.getCodeData();
        }

        CodeData plotCode = code.getCodeData();
        CodeLineData codeline = new CodeLineData();

        if (!plotCode.codelines.isEmpty()) {
            codeline = plotCode.codelines.get(position);
        } else {
            plotCode.codelines.set(position, codeline);
        }
        codeline.actions.get(codeblockIndex).action = newAction;
        // TODO: GET ARGS HERE!!!!!!!! (erm well set)

        return plotCode;
    }

    /**
     * @return List of integers representing the path to the codeblock,
     *         if the codeblock is the thread starter it returns an empty list
     */
    public static List<Integer> findCodeIndex(Location queryLoc) throws Exception {
        List<Integer> trace = new ArrayList<>();
        trace.add(0);

        if (queryLoc.getBlockZ() == 1) {
            // Thread starter special treatment
            return List.of();
        }

        Location end = CodeBlockManagement.findCodeEnd(queryLoc.clone());
        Location location = queryLoc.clone().set(queryLoc.getBlockX(), queryLoc.getBlockY(), 1);

        while (location.getBlockZ() <= end.getBlockZ() + 5) { // +5 for extra padding
            Bukkit.getLogger().info("Z: " + location.getBlockZ() + " Trace: " + trace);
            if (location.getBlockZ() == queryLoc.getBlockZ()) {
                return trace;
            }

            if (location.getBlock().getType() == Material.STONE || location.getBlock().getType() == Material.AIR) {
                location.add(0, 0, 1);
                continue;
            }

            if (location.getBlock().getType() != Material.PISTON && location.getBlock().getType() != Material.STICKY_PISTON) {
                CodeBlocks codeblock = CodeBlocks.getByMaterial(location.getBlock().getType());
                Bukkit.getLogger().info("Codeblock: " + codeblock + ", material: " + location.getBlock().getType());
                if (codeblock != null && !(codeblock.hasBrackets() || codeblock.isThreadStarter())) {
                    trace.set(trace.size() - 1, trace.get(trace.size() - 1) + 1); // ^ Skip the bracket codeblock or the thread starter
                }
                location.add(0, 0, 1);
                continue;
            }

            Piston piston = (Piston) location.getBlock().getBlockData();
            BlockFace face = piston.getFacing();
            if (face == BlockFace.SOUTH) {
                trace.add(0);
            } else if (face == BlockFace.NORTH) {
                trace.remove(trace.size() - 1);
                trace.set(trace.size() - 1, trace.get(trace.size() - 1) + 1); // Increment the last value
            }

            location.add(0, 0, 1);
        }

        throw new Exception("Somehow never reached the query location");
    }

}
