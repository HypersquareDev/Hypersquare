package hypersquare.hypersquare.dev.codefile;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hypersquare.hypersquare.dev.CodeBlocks;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
        JsonArray plotCode = codeFile.getCodeJson();

        List<JsonElement> plotCodeList = plotCode.asList();
        for (JsonElement element : plotCodeList) {
            if (element.getAsJsonObject().get("position").getAsInt() == codelineIndex) {
                return plotCodeList.indexOf(element);
            }
        }
        return -1;
    }

    /**
     * Set index to -1 for the codeblock to be appended instead of inserted.
     * @return
     */
    public static JsonArray addCodeblock(Location location, String name, int index, CodeFile code) {
        JsonArray plotCode = code.getCodeJson();

        int codelineIndex = getCodelineIndex(location);

        JsonObject codeline = new JsonObject();

        int position = getCodelineListIndex(location, code);

        CodeBlocks codeblock = CodeBlocks.getByName(name);
        if (codeblock.isThreadStarter()) {
            JsonObject event = new JsonObject();
            String type = CodeBlocks.getByName(name).id();
            event.addProperty("type", type);
            if (codeblock.hasActions) {
                event.addProperty("event", type + "_empty");
            }
            event.addProperty("position", codelineIndex);
            event.add("actions", new JsonArray());
            codeline = event;
        } else {
            codeline = plotCode.get(position).getAsJsonObject();
            JsonArray actions = codeline.get("actions").getAsJsonArray();
            JsonObject action = new JsonObject();
            action.addProperty("action", genEmptyCodeblock(name));

            // Insert the element if the index is -1  or if the index is less than the size of the array
            Bukkit.broadcast(Component.text("requested insert index: " + index));
            if (index < actions.size() && index != -1) {
                JsonArray temp = new JsonArray();
                for (int i = 0; i < actions.size(); i++) {
                    if (i == index) {
                        temp.add(action);
                    }
                    temp.add(actions.get(i));
                }
                actions = temp;
            } else {
                actions.add(action);
            }

            codeline.add("actions", actions);
        }

        if (position == -1) {
            plotCode.add(codeline);
        } else {
            plotCode.set(position, codeline);
        }

        Bukkit.broadcastMessage(plotCode.toString());
        return plotCode;
    }

    public static JsonArray removeCodeBlock(Location location, CodeFile code) {
        JsonArray plotCode = code.getCodeJson();

        int codeblockIndex = getCodeblockIndex(location);

        int position = getCodelineListIndex(location, code);
        JsonObject codeline = plotCode.get(position).getAsJsonObject();
        Bukkit.broadcastMessage(codeblockIndex + "");

        if (codeblockIndex == -1) {
            // Is a thread starter (first codeblock)
            plotCode.remove(codeline);
        } else {
            JsonArray actions = codeline.get("actions").getAsJsonArray();
            actions.remove(codeblockIndex);
            plotCode.set(position, codeline);
        }
        Bukkit.broadcastMessage(plotCode + "");
        return plotCode;
    }

    public static JsonArray updateAction(Location location, CodeFile code, String newAction) {
        int codelineIndex = getCodelineIndex(location);
        int codeblockIndex = getCodeblockIndex(location);

        int position = getCodelineListIndex(location, code);

        if (position == -1) {
            // We are somehow updating a non-existent codeline
            Bukkit.getLogger().warning("Tried updating a non existent codeline @ " + code.world);
            return code.getCodeJson();
        }

        JsonArray plotCode = code.getCodeJson();
        JsonObject codeline = new JsonObject();

        if (!plotCode.isEmpty()) {
            codeline = plotCode.get(position).getAsJsonObject();
        }
        JsonArray actions = codeline.get("actions").getAsJsonArray();
        JsonObject action = actions.get(codeblockIndex).getAsJsonObject();
        action.addProperty("action", newAction);

        // TODO: GET ARGS HERE!!!!!!!! (erm well set)

        actions.set(codeblockIndex, action);
        codeline.add("actions", actions);
        plotCode.set(position, codeline);
        return plotCode;
    }

}
