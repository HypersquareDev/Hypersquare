package hypersquare.hypersquare.dev;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public enum CodeBlocks {
    DIAMOND_BLOCK(Material.DIAMOND_BLOCK, "PLAYER EVENT", "player_event", false, false, true),
    COBBLESTONE(Material.COBBLESTONE, "PLAYER ACTION", "player_action", true, false),
    OAK_PLANKS(Material.OAK_PLANKS, "IF PLAYER", "if_player", true, true),
    LAPIS_ORE(Material.LAPIS_ORE, "CALL FUNCTION", "call_function", true, false),
    EMERALD_ORE(Material.EMERALD_ORE, "CALL PROCESS", "call_process", true, false),
    GOLD_BLOCK(Material.GOLD_ORE, "ENTITY EVENT", "entity_event", false, false, true),
    MOSSY_COBBLESTONE(Material.MOSSY_COBBLESTONE, "ENTITY ACTION", "entity_action", true, false),
    BRICKS(Material.BRICKS, "IF ENTITY", "if_entity", true, true),
    LAPIS_BLOCK(Material.LAPIS_BLOCK, "FUNCTION", "function", true, false, true),
    EMERALD_BLOCK(Material.EMERALD_BLOCK, "PROCESS", "process", true, false, true),
    IRON_BLOCK(Material.IRON_BLOCK, "SET VARIABLE", "set_variable", true, false),
    OBSIDIAN(Material.OBSIDIAN, "IF VARIABLE", "if_variable", true, true),
    NETHERRACK(Material.NETHERRACK, "GAME ACTION", "game_action", true, false),
    RED_NETHER_BRICKS(Material.RED_NETHER_BRICKS, "IF GAME", "if_game", true, true),
    COAL_BLOCK(Material.COAL_BLOCK, "CONTROL", "control", true, false),
    PURPUR_BLOCK(Material.PURPUR_BLOCK, "SELECT OBJECT", "select_object", true, false),
    PRISMARINE(Material.PRISMARINE, "REPEAT", "repeat", true, true),
    END_STONE(Material.END_STONE, "ELSE", "else", true, true),
    EMPTY(Material.AIR, "empty");

     final Material material;
     final String name;
     final String id;
     final boolean isThreadStarter;
     final boolean hasChest;
     final boolean hasBrackets;
    CodeBlocks(Material material, String name, String id, boolean hasChest, boolean hasBrackets) {
        this.material = material;
        this.name = name;
        this.id = id;
        this.hasChest = hasChest;
        this.hasBrackets = hasBrackets;
        this.isThreadStarter = false;
    }

    CodeBlocks(Material material, String name, String id, boolean hasChest, boolean hasBrackets, boolean isThreadStarter) {
        this.material = material;
        this.name = name;
        this.id = id;
        this.hasChest = hasChest;
        this.hasBrackets = hasBrackets;
        this.isThreadStarter = isThreadStarter;
    }

    CodeBlocks(Material material, String id) {
        this.material = material;
        this.id = id;
        this.name = id;
        this.hasChest = false;
        this.hasBrackets = false;
        this.isThreadStarter = false;
    }

    public static CodeBlocks getByMaterial(Material material) {
        for (CodeBlocks codeBlock : CodeBlocks.values()) {
            if (codeBlock.material == material) {
                return codeBlock;
            }
        }
        return null;
    }

    public static CodeBlocks getById(String id) {
        for (CodeBlocks codeBlock : CodeBlocks.values()) {
            if (Objects.equals(codeBlock.id, id)) {
                return codeBlock;
            }
        }
        return null;
    }

    public Material material() {
        return material;
    }

    public String getName() {
        return name;
    }

    public String id() {
        return id;
    }

    public boolean isThreadStarter() {
        return isThreadStarter;
    }

    public boolean hasChest() {
        return hasChest;
    }

    public boolean hasBrackets() {
        return hasBrackets;
    }
}
