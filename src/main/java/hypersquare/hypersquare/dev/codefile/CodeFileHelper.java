package hypersquare.hypersquare.dev.codefile;

import hypersquare.hypersquare.dev.CodeBlocks;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.codefile.data.CodeData;
import hypersquare.hypersquare.dev.codefile.data.CodeLineData;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.item.Event;
import hypersquare.hypersquare.plot.CodeBlockManagement;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Piston;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
        return (int) Math.floor((double) location.getBlockZ() / 2) - 1;
    }

    public static int getCodelineListIndex(Location location, CodeData plotCode) {
        int position = getCodelineWorldIndex(location);

        for (CodeLineData element : plotCode.codelines) {
            if (element.position == position) return plotCode.codelines.indexOf(element);
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
        CodeLineData codeline = plotCode.codelines.get(getCodelineListIndex(location, plotCode));

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

        int codelineListIndex = getCodelineListIndex(location, plotCode);
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

    public static CodeData updateAction(Location location, CodeFile code, Action newAction, CodeBlocks codeblock) {
        CodeData plotCode = code.getCodeData();
        if (codeblock.isThreadStarter()) return plotCode; // Someone called the wrong method lol
        int codelineListIndex = getCodelineListIndex(location, plotCode);

        if (codelineListIndex == -1) {
            // We are updating a non-existent codeline (got deleted by another player)
            // Logging just in case for debug purposes
            Bukkit.getLogger().warning("Tried updating a non existent codeline @ " + code.world);
            return code.getCodeData();
        }

        CodeLineData codeline = new CodeLineData();

        if (!plotCode.codelines.isEmpty()) {
            codeline = plotCode.codelines.get(codelineListIndex);
        } else {
            plotCode.codelines.set(codelineListIndex, codeline);
        }


        List<Integer> positions;
        try {
            positions = CodeFileHelper.findCodeIndex(location);
        } catch (Exception e) {
            // We are updating a non-existent codeblock
            Bukkit.getLogger().info("Couldn't find the codeblock the player was editing @ " + code.world);
            return plotCode;
        }

        CodeActionData actionJson = codeline.actions.get(positions.get(0));
        for (int i = 1; i < positions.size(); i++) {
            actionJson = actionJson.actions.get(positions.get(i));
        }
        actionJson.action = newAction.getId();
        actionJson.arguments = new HashMap<>();

        code.setCode(plotCode.toJson().toString());
        return plotCode;
    }

    public static CodeData updateEvent(Location location, CodeFile code, Event newEvent, CodeBlocks codeblock) {
        CodeData plotCode = code.getCodeData();
        if (codeblock != CodeBlocks.DIAMOND_BLOCK && codeblock != CodeBlocks.GOLD_BLOCK) return plotCode; // Someone called the wrong method lol
        int codelineListIndex = getCodelineListIndex(location, plotCode);

        if (codelineListIndex == -1) {
            // We are updating a non-existent codeline (got deleted by another player)
            // Logging just in case for debug purposes
            Bukkit.getLogger().warning("Tried updating a non existent codeline @ " + code.world);
            return code.getCodeData();
        }

        CodeLineData codeline = new CodeLineData();

        if (!plotCode.codelines.isEmpty()) {
            codeline = plotCode.codelines.get(codelineListIndex);
        } else {
            plotCode.codelines.set(codelineListIndex, codeline);
        }

        codeline.event = newEvent.getId();
        plotCode.codelines.set(codelineListIndex, codeline);
        code.setCode(plotCode.toJson().toString());
        return plotCode;
    }

    /**
     * @return List of integers representing the path to the codeblock,
     * if the codeblock is the thread starter it returns an empty list
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
            if (location.getBlockZ() == queryLoc.getBlockZ()) {
                return trace;
            }

            if (location.getBlock().getType() == Material.STONE || location.getBlock().getType() == Material.AIR) {
                location.add(0, 0, 1);
                continue;
            }

            if (location.getBlock().getType() != Material.PISTON && location.getBlock().getType() != Material.STICKY_PISTON) {
                CodeBlocks codeblock = CodeBlocks.getByMaterial(location.getBlock().getType());
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

    public static CodeActionData getActionAt(Location location, CodeData data) {
        int codelineIndex = CodeFileHelper.getCodelineListIndex(location, data);
        if (codelineIndex < 0 || codelineIndex >= data.codelines.size()) return null;
        CodeLineData line = data.codelines.get(codelineIndex);
        try {
            List<Integer> positions = CodeFileHelper.findCodeIndex(location);

            if (positions.isEmpty()) return null;

            CodeActionData action = line.actions.get(positions.get(0));
            for (int i = 1; i < positions.size(); i++) {
                if (positions.get(i) < 0 || positions.get(i) >= action.actions.size()) return null;
                action = action.actions.get(positions.get(i));
            }

            return action;
        } catch (Exception err) {
            return null;
        }
    }

}
