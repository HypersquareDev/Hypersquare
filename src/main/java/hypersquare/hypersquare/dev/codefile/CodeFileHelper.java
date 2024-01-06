package hypersquare.hypersquare.dev.codefile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import hypersquare.hypersquare.dev.CodeBlocks;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class CodeFileHelper {
    @NotNull
    private static String genEmptyCodeblock(String name) {
        // PLAYER ACTION => player_action_empty
        return name.toLowerCase().replace(" ", "_") + "_empty";
    }

    private static String getStarterType(String name) {
        return switch (name) {
            case "PLAYER EVENT" -> "event";
            case "ENTITY EVENT" -> "entity_event";
            case "FUNCTION" -> "func";
            case "PROCESS" -> "process";
            default -> null;
        };
    }

    private static int getCodelineIndex(Location location) {
        return Math.abs(location.getBlockX() / 3) - 1;
    }

    private static int getCodeblockIndex(Location location) {
        return (int) Math.floor((double) location.getBlockZ() / 2) - 1;
    }

    private static int getCodelinePosition(Location location, CodeFile codeFile, int codelineIndex) {
        JsonArray plotCode = codeFile.getCodeJson();

        final int[] _position = {-1};
        plotCode.iterator().forEachRemaining(element -> {
            if (element.getAsJsonObject().get("position").getAsInt() == codelineIndex) {
                _position[0] = codelineIndex;
            }

        });
        int position = _position[0];
        Bukkit.broadcastMessage(codelineIndex + "");
        return position;
    }

    public static JsonArray addCodeblock(Location location, String name, CodeFile code) {
        Bukkit.broadcastMessage(location.toString());
        JsonArray plotCode = code.getCodeJson();

        int codelineIndex = getCodelineIndex(location);
        int codeblockIndex = getCodeblockIndex(location);

        Bukkit.broadcastMessage(codelineIndex + " index");
        Bukkit.broadcastMessage(location.getBlockX() + " block x");

        JsonObject codeline = new JsonObject();

        int position = getCodelinePosition(location, code, codelineIndex);
        Bukkit.broadcastMessage(codelineIndex + " codeline index");


        if (CodeBlocks.getByName(name).isThreadStarter()) {
            JsonObject event = new JsonObject();
            String type = getStarterType(name);
            event.addProperty("type", type);
            event.addProperty(type, type + "_empty");
            event.addProperty("position", codelineIndex);
            event.add("actions", new JsonArray());
            codeline = event;
        } else {
            if (!plotCode.isEmpty()) {
                codeline = plotCode.get(position).getAsJsonObject();
            }
            JsonArray actions = codeline.get("actions").getAsJsonArray();
            JsonObject action = new JsonObject();
            action.addProperty("action", genEmptyCodeblock(name));
            actions.add(action);
        }
        // Update main codeJson


        Bukkit.broadcastMessage(position + " postion");


        if (position == -1) {
            plotCode.add(codeline);

        } else {
            plotCode.set(position, codeline);
        }

        return plotCode;
    }

    public static JsonArray removeCodeBlock(Location location, CodeFile code) {
        Bukkit.broadcastMessage(location.toString());
        JsonArray plotCode = code.getCodeJson();

        int codelineIndex = getCodelineIndex(location);
        int codeblockIndex = getCodeblockIndex(location);

        Bukkit.broadcastMessage(codelineIndex + " index");
        Bukkit.broadcastMessage(location.getBlockX() + " block x");

        JsonObject codeline = new JsonObject();

        int position = getCodelinePosition(location, code, codelineIndex);

        JsonObject event = plotCode.get(position).getAsJsonObject();

        if (codeblockIndex == 0){
            plotCode.remove(position);
        } else {
            JsonArray actions = event.get("actions").getAsJsonArray();
            actions.remove(codeblockIndex);
        }




//        if (CodeBlocks.getByName(name).isThreadStarter()) {
//            JsonObject event = new JsonObject();
//            String type = getStarterType(name);
//            event.addProperty("type", type);
//            event.addProperty(type, type + "_empty");
//            event.addProperty("position", codelineIndex);
//            event.add("actions", new JsonArray());
//            codeline = event;
//        } else {
//            if (!plotCode.isEmpty()) {
//                codeline = plotCode.get(position).getAsJsonObject();
//            }
//            JsonArray actions = codeline.get("actions").getAsJsonArray();
//            JsonObject action = new JsonObject();
//            action.addProperty("action", genEmptyCodeblock(name));
//            actions.add(action);
//        }
        // Update main codeJson


        Bukkit.broadcastMessage(position + " postion");


        if (position == -1) {
            plotCode.add(codeline);

        } else {
            plotCode.set(position, codeline);
        }

        return plotCode;
    }

    public static JsonArray updateAction(Location location, CodeFile code, String newAction) {
        int codelineIndex = getCodelineIndex(location);
        int codeblockIndex = getCodeblockIndex(location);

        Bukkit.broadcastMessage(codelineIndex + " index");
        Bukkit.broadcastMessage(location.getBlockX() + " block x");

        int position = getCodelinePosition(location, code, codelineIndex);

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
