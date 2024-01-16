package hypersquare.hypersquare.dev.codefile;

import hypersquare.hypersquare.dev.CodeBlocks;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.codefile.data.CodeData;
import hypersquare.hypersquare.dev.codefile.data.CodeLineData;
import hypersquare.hypersquare.dev.util.BracketFinder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class CodeFileHelper {
    @NotNull
    private static String genEmptyCodeblock(String name) {
        // PLAYER ACTION => player_action_empty
        return name.toLowerCase().replace(" ", "_") + "_empty";
    }

    private static int getCodelineIndex(Location location) {
        return Math.abs(location.getBlockX() / 3) - 1;
    }

    public static int getCodeblockIndex(Location location) {
        return (int) Math.floor((double) location.getBlockZ() / 2) -1;
    }

    public static int getCodelineListIndex(Location location, CodeFile codeFile) {
        int codelineIndex = getCodelineIndex(location);
        CodeData plotCode = codeFile.getCodeData();

        for (CodeLineData element : plotCode.codelines) {
            if (element.position == codelineIndex) {
                return plotCode.codelines.indexOf(element);
            }
        }
        return -1;
    }

    /**
     * Set index to -1 for the codeblock to be appended instead of inserted.
     * @return
     */
    public static CodeData addCodeblock(Location location, String name, int index, CodeFile code) {

        CodeData plotCode = code.getCodeData();

        int codelineIndex = getCodelineIndex(location);

        CodeLineData codeline = null;

        int position = getCodelineListIndex(location, code);

        CodeBlocks codeblock = CodeBlocks.getByName(name);

        if (!codeblock.isThreadStarter()){
            Bukkit.broadcastMessage(BracketFinder.isInIfStatement(location) + " is in if");
        }
        if (codeblock.isThreadStarter()) {
            CodeLineData event = new CodeLineData();
            String type = CodeBlocks.getByName(name).id();
            event.type = type;
            if (codeblock.hasActions) {
                event.event = type + "_empty";
            }
            event.position = codelineIndex;
            codeline = event;
        } else {
            codeline = plotCode.codelines.get(position);
            CodeActionData action = new CodeActionData();
            action.action = genEmptyCodeblock(name);

            if(codeblock.hasBrackets()){
                // Give the action an actions array
                // Should I make another class for CodeBracketActionData
            }

            // Insert the element if the index is -1  or if the index is less than the size of the array
            Bukkit.broadcast(Component.text("requested insert index: " + index));
            if (index < codeline.actions.size() && index != -1) {
                codeline.actions.add(index, action);
            } else {
                codeline.actions.add(action);
            }
        }

        if (position == -1) {
            plotCode.codelines.add(codeline);
        } else {
            plotCode.codelines.set(position, codeline);
        }

        Bukkit.broadcastMessage(plotCode.toString());
        return plotCode;
    }

    public static CodeData removeCodeBlock(Location location, CodeFile code) {
        CodeData plotCode = code.getCodeData();

        int codeblockIndex = getCodeblockIndex(location);

        int position = getCodelineListIndex(location, code);
        CodeLineData codeline = plotCode.codelines.get(position);
        Bukkit.broadcastMessage(codeblockIndex + "");

        if (codeblockIndex == -1) {
            // Is a thread starter (first codeblock)
            plotCode.codelines.remove(codeline);
        } else {
            codeline.actions.remove(codeblockIndex);
        }
        Bukkit.broadcastMessage(plotCode + "");
        return plotCode;
    }

    public static CodeData updateAction(Location location, CodeFile code, String newAction) {
        int codelineIndex = getCodelineIndex(location);
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

}
