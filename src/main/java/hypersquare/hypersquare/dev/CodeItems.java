package hypersquare.hypersquare.dev;


import hypersquare.hypersquare.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static hypersquare.hypersquare.Hypersquare.mm;

public class CodeItems {
    public static ItemStack PLAYER_EVENT_ITEM;
    public static ItemStack IF_PLAYER_ITEM;
    public static ItemStack PLAYER_ACTION_ITEM;
    public static ItemStack CALL_FUNCTION_ITEM;
    public static ItemStack START_PROCESS_ITEM;
    public static ItemStack ENTITY_EVENT_ITEM;
    public static ItemStack IF_ENTITY_ITEM;
    public static ItemStack ENTITY_ACTION_ITEM;
    public static ItemStack FUNCTION_ITEM;
    public static ItemStack PROCESS_ITEM;
    public static ItemStack SET_VARIABLE_ITEM;
    public static ItemStack IF_VARIABLE_ITEM;
    public static ItemStack GAME_ACTION_ITEM;
    public static ItemStack IF_GAME_ITEM;
    public static ItemStack CONTROL_ITEM;
    public static ItemStack SELECT_OBJECT_ITEM;
    public static ItemStack REPEAT_ITEM;
    public static ItemStack ELSE_ITEM;
    public static ItemStack GLITCH_STICK_ITEM;
    public static void register() {
        PLAYER_EVENT_ITEM = new ItemBuilder(Material.DIAMOND_BLOCK)
            .name(mm.deserialize("<aqua>Player Event"))
            .lore(mm.deserialize("<gray>Used to execute code when something"))
            .lore(mm.deserialize("<gray>is done by (or happens to) a player."))
            .lore("")
            .lore(mm.deserialize("<white>Example:"))
            .lore(mm.deserialize("<aqua>» <gray>Detect when a player joins the plot"))
            .lore(mm.deserialize("<aqua>» <gray>Detect when a player right clicks"))
            .lore(mm.deserialize("<aqua>» <gray>Detect when a player dies"))
            .build();

        IF_PLAYER_ITEM = new ItemBuilder(Material.OAK_PLANKS)
                .name(mm.deserialize("<gold>If Player"))
                .lore(mm.deserialize("<gray>Used to execute the code inside it"))
                .lore(mm.deserialize("<gray>if a certain condition related to a"))
                .lore(mm.deserialize("<gray>player is met."))
                .lore("")
                .lore(mm.deserialize("<white>Example:"))
                .lore(mm.deserialize("<aqua>» <gray>Check if a player is swimming"))
                .lore(mm.deserialize("<aqua>» <gray>Check if a player has an item"))
                .lore(mm.deserialize("<aqua>» <gray>Check a player's username"))
                .build();

        PLAYER_ACTION_ITEM = new ItemBuilder(Material.COBBLESTONE)
                .name(mm.deserialize("<green>Player Action"))
                .lore(mm.deserialize("<gray>Used to do something related to"))
                .lore(mm.deserialize("<gray>a player or multiple players."))
                .lore("")
                .lore(mm.deserialize("<white>Example:"))
                .lore(mm.deserialize("<aqua>» <gray>Send a message to a player"))
                .lore(mm.deserialize("<aqua>» <gray>Damage or heal a player"))
                .lore(mm.deserialize("<aqua>» <gray>Clear a player's inventory"))
                .build();

        CALL_FUNCTION_ITEM = new ItemBuilder(Material.LAPIS_ORE)
                .name(mm.deserialize("<blue>Call Function"))
                .lore(mm.deserialize("<gray>Used to call functions declared by"))
                .lore(mm.deserialize("<gray>a Function block."))
                .lore(mm.deserialize("<gray>Code will not continue past this block"))
                .lore(mm.deserialize("<gray>until the Function completes/returns."))
                .build();

        START_PROCESS_ITEM = new ItemBuilder(Material.EMERALD_ORE)
                .name(mm.deserialize("<dark_green>Start Process"))
                .lore(mm.deserialize("<gray>Used to start processes declared by"))
                .lore(mm.deserialize("<gray>a Process block."))
                .lore(mm.deserialize("<gray>Waits inside the Process will not"))
                .lore(mm.deserialize("<gray>stop this code line, unlike Functions."))
                .build();

        ENTITY_EVENT_ITEM = new ItemBuilder(Material.GOLD_BLOCK)
                .name(mm.deserialize("<yellow>Entity Event"))
                .lore(mm.deserialize("<gray>Used to execute code when an entity"))
                .lore(mm.deserialize("<gray>does something or when something"))
                .lore(mm.deserialize("<gray>happens to an entity."))
                .lore("")
                .lore(mm.deserialize("<white>Example:"))
                .lore(mm.deserialize("<aqua>» <gray>Detect when a mob takes damage"))
                .lore(mm.deserialize("<aqua>» <gray>Detect when a projectile kills a mob"))
                .lore(mm.deserialize("<aqua>» <gray>Detect when a mob kills another mob"))
                .build();

        IF_ENTITY_ITEM = new ItemBuilder(Material.BRICKS)
                .name(mm.deserialize("<dark_green>If Entity"))
                .lore(mm.deserialize("<gray>Used to execute the code inside it"))
                .lore(mm.deserialize("<gray>if a certain condition related to an"))
                .lore(mm.deserialize("<gray>entity or multiple entities is met."))
                .lore("")
                .lore(mm.deserialize("<white>Example:"))
                .lore(mm.deserialize("<aqua>» <gray>Check if an entity is a projectile"))
                .lore(mm.deserialize("<aqua>» <gray>Check if an entity is a certain type"))
                .lore(mm.deserialize("<gray>of mob"))
                .lore(mm.deserialize("<aqua>» <gray>Check the name of an entity"))
                .build();

        ENTITY_ACTION_ITEM = new ItemBuilder(Material.MOSSY_COBBLESTONE)
                .name(mm.deserialize("<red>Entity Action"))
                .lore(mm.deserialize("<gray>Used to do something related to"))
                .lore(mm.deserialize("<gray>an entity or multiple entities."))
                .lore("")
                .lore(mm.deserialize("<white>Example:"))
                .lore(mm.deserialize("<aqua>» <gray>Set the age or size of a mob"))
                .lore(mm.deserialize("<aqua>» <gray>Give a mob a potion effect"))
                .lore(mm.deserialize("<aqua>» <gray>Delete an entity from the plot"))
                .build();

        FUNCTION_ITEM = new ItemBuilder(Material.LAPIS_BLOCK)
                .name(mm.deserialize("<aqua>Function"))
                .lore(mm.deserialize("<gray>Used to define a line of code that can be"))
                .lore(mm.deserialize("<gray>called with a Call Function block."))
                .lore("")
                .lore(mm.deserialize("<blue>Additional Info:"))
                .lore(mm.deserialize("<aqua>» <gray>Set the function name by right clicking"))
                .lore(mm.deserialize("<gray>the code block sign with a <aqua>string<gray>."))
                .lore(mm.deserialize("<aqua>» <gray>Set the name color, icon, and lore of the"))
                .lore(mm.deserialize("<gray>function by putting an item in the chest."))
                .lore(mm.deserialize("<aqua>» <gray>Put <#aaffaa>parameter <gray>items to specify the"))
                .lore(mm.deserialize("<gray>parameters of the function."))
                .lore(mm.deserialize("<aqua>» <gray>Functions can also be used for continuing"))
                .lore(mm.deserialize("<gray>lines of code that are out of space."))
                .build();

        PROCESS_ITEM = new ItemBuilder(Material.EMERALD_BLOCK)
                .name(mm.deserialize("<green>Process"))
                .lore(mm.deserialize("<gray>Used to execute code when the process"))
                .lore(mm.deserialize("<gray>is started using a Start Process block."))
                .lore("")
                .lore(mm.deserialize("<blue>Additional Info:"))
                .lore(mm.deserialize("<aqua>» <gray>Set the process name by right clicking"))
                .lore(mm.deserialize("<gray>the code block sign with a <aqua>string<gray>."))
                .lore(mm.deserialize("<aqua>» <gray>Set the name color, icon, and lore of the"))
                .lore(mm.deserialize("<gray>process by putting an item in the chest."))
                .lore(mm.deserialize("<aqua>» <gray>Processes are not a replacement for"))
                .lore(mm.deserialize("<gray>Functions. Processes are much more CPU"))
                .lore(mm.deserialize("<gray>intensive than functions, and should only"))
                .lore(mm.deserialize("<gray>be used when needed."))
                .build();

        SET_VARIABLE_ITEM = new ItemBuilder(Material.IRON_BLOCK)
                .name(mm.deserialize("<yellow>Set Variable"))
                .lore(mm.deserialize("<gray>Used to set the value of a variable."))
                .lore("")
                .lore(mm.deserialize("<white>Example:"))
                .lore(mm.deserialize("<aqua>» <gray>Set a variable to a sum of numbers"))
                .lore(mm.deserialize("<aqua>» <gray>Remove all instances of a certain"))
                .lore(mm.deserialize("<gray>string from a string variable"))
                .lore(mm.deserialize("<aqua>» <gray>Set a variable to a random value"))
                .build();

        IF_VARIABLE_ITEM = new ItemBuilder(Material.OBSIDIAN)
                .name(mm.deserialize("<gold>If Variable"))
                .lore(mm.deserialize("<gray>Used to execute the code inside it"))
                .lore(mm.deserialize("<gray>if a certain condition related to the"))
                .lore(mm.deserialize("<gray>value of a variable is met."))
                .lore("")
                .lore(mm.deserialize("<white>Example:"))
                .lore(mm.deserialize("<aqua>» <gray>Check if a number variable is less"))
                .lore(mm.deserialize("<gray>than or equal to another number"))
                .lore(mm.deserialize("<aqua>» <gray>Check if a string variable contains"))
                .lore(mm.deserialize("<gray>a certain string"))
                .lore(mm.deserialize("<aqua>» <gray>Check if a variable is equal"))
                .lore(mm.deserialize("<gray>to something"))
                .build();

        GAME_ACTION_ITEM = new ItemBuilder(Material.NETHERRACK)
                .name(mm.deserialize("<red>Game Action"))
                .lore(mm.deserialize("<gray>Used to do something related to"))
                .lore(mm.deserialize("<gray>the plot and everyone playing it."))
                .lore("")
                .lore(mm.deserialize("<white>Example:"))
                .lore(mm.deserialize("<aqua>» <gray>Spawn a mob or other entity"))
                .lore(mm.deserialize("<aqua>» <gray>Change a block or block region"))
                .build();

        IF_GAME_ITEM = new ItemBuilder(Material.RED_NETHER_BRICKS)
                .name(mm.deserialize("<red>If Game"))
                .lore(mm.deserialize("<gray>Used to execute the code inside"))
                .lore(mm.deserialize("<gray>it if a certain condition related"))
                .lore(mm.deserialize("<gray>to the plot is met."))
                .lore("")
                .lore(mm.deserialize("<white>Example:"))
                .lore(mm.deserialize("<aqua>» <gray>Check if a container has all of"))
                .lore(mm.deserialize("<gray>a certain set of items"))
                .lore(mm.deserialize("<aqua>» <gray>Check if a sign's text contains"))
                .lore(mm.deserialize("<gray>a certain text"))
                .lore(mm.deserialize("<aqua>» <gray>Check if a block at a certain"))
                .lore(mm.deserialize("<gray>location is a certain block"))
                .build();

        CONTROL_ITEM = new ItemBuilder(Material.COAL_BLOCK)
                .name(mm.deserialize("<blue>Control"))
                .lore(mm.deserialize("<gray>Used to control the execution of"))
                .lore(mm.deserialize("<gray>some or all code blocks after it."))
                .lore("")
                .lore(mm.deserialize("<white>Example:"))
                .lore(mm.deserialize("<aqua>» <gray>Pause the code sequence"))
                .lore(mm.deserialize("<gray>for a certain duration"))
                .lore(mm.deserialize("<aqua>» <gray>Stop the code sequence"))
                .lore(mm.deserialize("<aqua>» <gray>Skip a repeat iteration"))
                .build();

        SELECT_OBJECT_ITEM = new ItemBuilder(Material.PURPUR_BLOCK)
                .name(mm.deserialize("<light_purple>Select Object"))
                .lore(mm.deserialize("<gray>Used to change the selection on the"))
                .lore(mm.deserialize("<gray>current line of code, which will affect"))
                .lore(mm.deserialize("<gray>the targets of most code blocks."))
                .lore("")
                .lore(mm.deserialize("<white>Example:"))
                .lore(mm.deserialize("<aqua>» <gray>Select all players on the plot"))
                .lore(mm.deserialize("<aqua>» <gray>Select a player with a certain username"))
                .lore(mm.deserialize("<aqua>» <gray>Select all players on the plot that meet"))
                .lore(mm.deserialize("<gray>a certain condition"))
                .build();

        REPEAT_ITEM = new ItemBuilder(Material.PRISMARINE)
                .name(mm.deserialize("<green>Repeat"))
                .lore(mm.deserialize("<gray>Used to repeat the code inside it."))
                .lore("")
                .lore(mm.deserialize("<white>Example:"))
                .lore(mm.deserialize("<aqua>» <gray>Repeat code forever"))
                .lore(mm.deserialize("<aqua>» <gray>Repeat code a certain number"))
                .lore(mm.deserialize("<gray>of times"))
                .lore(mm.deserialize("<aqua>» <gray>Repeat code until a certain"))
                .lore(mm.deserialize("<gray>condition is met"))
                .build();

        ELSE_ITEM = new ItemBuilder(Material.END_STONE)
                .name(mm.deserialize("<dark_aqua>Else"))
                .lore(mm.deserialize("<gray>Used to execute the code inside it"))
                .lore(mm.deserialize("<gray>if the condition directly before it"))
                .lore(mm.deserialize("<gray>was not met. Else must be placed directly"))
                .lore(mm.deserialize("<gray>after any If block's closing bracket."))
                .build();


        GLITCH_STICK_ITEM = new ItemBuilder(Material.STICK)
                .name(mm.deserialize("<red>Glitch Stick"))
                .lore(mm.deserialize("<gray>If a code block won't break,"))
                .lore(mm.deserialize("<gray>break it with this."))
                .build();

    }
}
