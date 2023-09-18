package hypersquare.hypersquare.dev;


import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.utils.managers.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class CodeItems {
    public static void register() {
        ItemStack playerEvent = new ItemBuilder(Material.DIAMOND_BLOCK)
            .name(ChatColor.AQUA + "Player Event")
            .lore(ChatColor.GRAY + "Used to execute code when something")
            .lore(ChatColor.GRAY + "is done by (or happens to) a player.")
            .lore("")
            .lore(ChatColor.WHITE + "Example:")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Detect when a player joins the plot")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Detect when a player right clicks")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Detect when a player dies")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "false")

            .make();


        ItemManager.addItem("dev.event", playerEvent);
        ItemStack ifPlayer = new ItemBuilder(Material.OAK_PLANKS)
            .name(ChatColor.GOLD + "If Player")
            .lore(ChatColor.GRAY + "Used to execute the code inside it")
            .lore(ChatColor.GRAY + "if a certain condition related to a")
            .lore(ChatColor.GRAY + "player is met.")
            .lore("")
            .lore(ChatColor.WHITE + "Example:")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check if a player is swimming")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check if a player has an item")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check a player's username")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "true")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.if_player", ifPlayer);

        ItemStack playerAction = new ItemBuilder(Material.COBBLESTONE)
            .name(ChatColor.GREEN + "Player Action")
            .lore(ChatColor.GRAY + "Used to do something related to")
            .lore(ChatColor.GRAY + "a player or multiple players.")
            .lore("")
            .lore(ChatColor.WHITE + "Example:")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Send a message to a player")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Damage or heal a player")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Clear a player's inventory")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.player_action", playerAction);

        ItemStack callFunction = new ItemBuilder(Material.LAPIS_ORE)
            .name(ChatColor.BLUE + "Call Function")
            .lore(ChatColor.GRAY + "Used to call functions declared by")
            .lore(ChatColor.GRAY + "a Function block.")
            .lore(ChatColor.GRAY + "Code will note continue past this block")
            .lore(ChatColor.GRAY + "until the Function completes/returns.")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "false")
            .make();
        ItemManager.addItem("dev.call_func", callFunction);

        ItemStack startProcess = new ItemBuilder(Material.EMERALD_ORE)
            .name(ChatColor.DARK_GREEN + "Start Process")
            .lore(ChatColor.GRAY + "Used to start processes declared by")
            .lore(ChatColor.GRAY + "a Process block.")
            .lore(ChatColor.GRAY + "Waits inside the Process will not")
            .lore(ChatColor.GRAY + "stop this code line, unlike Functions.")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.start_process", startProcess);

        ItemStack entityEvent = new ItemBuilder(Material.GOLD_BLOCK)
            .name(ChatColor.YELLOW + "Entity Action")
            .lore(ChatColor.GRAY + "Used to execute code when an entity")
            .lore(ChatColor.GRAY + "does something or when something")
            .lore(ChatColor.GRAY + "happens to an entity.")
            .lore("")
            .lore(ChatColor.WHITE + "Example:")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Detect when a mob takes damage")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Detect when a projectile kills a mob")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Detect when a mob kills another mob")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.entity_event", entityEvent);

        ItemStack ifEntity = new ItemBuilder(Material.BRICKS)
            .name(ChatColor.DARK_GREEN + "If Entity")
            .lore(ChatColor.GRAY + "Used to execute the code inside it")
            .lore(ChatColor.GRAY + "if a certain condition related to an")
            .lore(ChatColor.GRAY + "entity or multiple entities is met.")
            .lore("")
            .lore(ChatColor.WHITE + "Example:")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check if an entity is a projectile")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check if an entity is a certain type")
            .lore(ChatColor.GRAY + "of mob")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check the name of an entity")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "true")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.if_entity", ifEntity);

        ItemStack entityAction = new ItemBuilder(Material.MOSSY_COBBLESTONE)
            .name(ChatColor.RED + "Entity Action")
            .lore(ChatColor.GRAY + "Used to do something related to")
            .lore(ChatColor.GRAY + "an entity or multiple entities.")
            .lore("")
            .lore(ChatColor.WHITE + "Example:")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Set the age or size of a mob")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Give a mob a potion effect")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Delete an entity from the plot")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.entity_action", entityAction);

        ItemStack function = new ItemBuilder(Material.LAPIS_BLOCK)
            .name(ChatColor.AQUA + "Function")
            .lore(ChatColor.GRAY + "Used to define a line of code that can be")
            .lore(ChatColor.GRAY + "called with a Call Function block.")
            .lore("")
            .lore(ChatColor.BLUE + "Additional Info:")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Set the function name by right clicking")
            .lore(ChatColor.GRAY + "the code block sign with a " + ChatColor.AQUA + "string" + ChatColor.GRAY + ".")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Set the name color, icon, and lore of the")
            .lore(ChatColor.GRAY + "function by putting an item in the chest.")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Put " + net.md_5.bungee.api.ChatColor.of("#AAFFAA") + "parameter" + ChatColor.GRAY + " items to specify the")
            .lore(ChatColor.GRAY + "parameters of the function.")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Functions can also be used for continuing")
            .lore(ChatColor.GRAY + "lines of code that are out of space.")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.func", function);

        ItemStack process = new ItemBuilder(Material.EMERALD_BLOCK)
            .name(ChatColor.GREEN + "Process")
            .lore(ChatColor.GRAY + "Used to execute code when the process")
            .lore(ChatColor.GRAY + "is started using a Start Process block.")
            .lore("")
            .lore(ChatColor.BLUE + "Additional Info:")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Set the process name by right clicking")
            .lore(ChatColor.GRAY + "the code block sign with a " + ChatColor.AQUA + "string" + ChatColor.GRAY + ".")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Set the name color, icon, and lore of the")
            .lore(ChatColor.GRAY + "process by putting an item in the chest.")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Processes are not a replacement for")
            .lore(ChatColor.GRAY + "Functions. Processes are much more CPU")
            .lore(ChatColor.GRAY + "intensive than functions, and should only")
            .lore(ChatColor.GRAY + "be used when needed.")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.process", process);

        ItemStack setVariable = new ItemBuilder(Material.IRON_BLOCK)
            .name(ChatColor.YELLOW + "Set Variable")
            .lore(ChatColor.GRAY + "Used to set the value of a variable.")
            .lore("")
            .lore(ChatColor.WHITE + "Example:")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Set a variable to a sum of numbers")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Remove all instances of a certain")
            .lore(ChatColor.GRAY + "string from a string variable")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Set a variable to a random value")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.set_var", setVariable);

        ItemStack ifVariable = new ItemBuilder(Material.OBSIDIAN)
            .name(ChatColor.GOLD + "If Variable")
            .lore(ChatColor.GRAY + "Used to execute the code inside it")
            .lore(ChatColor.GRAY + "if a certain condition related to the")
            .lore(ChatColor.GRAY + "value of a variable is met.")
            .lore("")
            .lore(ChatColor.WHITE + "Example:")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check if a number variable is less")
            .lore(ChatColor.GRAY + "than or equal to another number")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check if a string variable contains")
            .lore(ChatColor.GRAY + "a certain string")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check if a variable is equal")
            .lore(ChatColor.GRAY + "to something")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "true")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.if_var", ifVariable);

        ItemStack gameAction = new ItemBuilder(Material.NETHERRACK)
            .name(ChatColor.RED + "Game Action")
            .lore(ChatColor.GRAY + "Used to do something related to")
            .lore(ChatColor.GRAY + "the plot and everyone playing it.")
            .lore("")
            .lore(ChatColor.WHITE + "Example:")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Spawn a mob or other entity")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Change a block or block region")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.game_action", gameAction);

        ItemStack ifGame = new ItemBuilder(Material.RED_NETHER_BRICKS)
            .name(ChatColor.RED + "If Game")
            .lore(ChatColor.GRAY + "Used to execute the code inside")
            .lore(ChatColor.GRAY + "it if a certain condition related")
            .lore(ChatColor.GRAY + "to the plot is met.")
            .lore("")
            .lore(ChatColor.WHITE + "Example:")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check if a container has all of")
            .lore(ChatColor.GRAY + "a certain set of items")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check if a sign's text contains")
            .lore(ChatColor.GRAY + "a certain text")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Check if a block at a certain")
            .lore(ChatColor.GRAY + "location is a certain block")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "true")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.if_game", ifGame);

        ItemStack control = new ItemBuilder(Material.COAL_BLOCK)
            .name(ChatColor.BLUE + "Control")
            .lore(ChatColor.GRAY + "Used to control the execution of")
            .lore(ChatColor.GRAY + "some or all code blocks after it.")
            .lore("")
            .lore(ChatColor.WHITE + "Example:")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Pause the code sequence")
            .lore(ChatColor.GRAY + "for a certain duration")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Stop the code sequence")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Skip a repeat iteration")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.control", control);

        ItemStack selectObject = new ItemBuilder(Material.PURPUR_BLOCK)
            .name(ChatColor.LIGHT_PURPLE + "Select Object")
            .lore(ChatColor.GRAY + "Used to change the selection on the")
            .lore(ChatColor.GRAY + "current line of code, which will affect")
            .lore(ChatColor.GRAY + "the targets of most code blocks.")
            .lore("")
            .lore(ChatColor.WHITE + "Example:")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Select all players on the plot")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Select a player with a certain username")
            .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Select all players on the plot that meet")
            .lore(ChatColor.GRAY + "a certain condition")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "false")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.select_obj", selectObject);

        ItemStack repeat = new ItemBuilder(Material.PRISMARINE)
                .name(ChatColor.GREEN + "Repeat")
                .lore(ChatColor.GRAY + "Used to repeat the code inside it.")
                .lore("")
                .lore(ChatColor.WHITE + "Example:")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Repeat code forever")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Repeat code a certain number")
                .lore(ChatColor.GRAY + "of times")
                .lore(ChatColor.AQUA + "» " + ChatColor.GRAY + "Repeat code until a certain")
                .lore(ChatColor.GRAY + "condition is met")
                .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "true")
                .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
                .make();
        ItemManager.addItem("dev.repeat", repeat);

        ItemStack notElse = new ItemBuilder(Material.END_STONE)
            .name(ChatColor.DARK_AQUA + "Else")
            .lore(ChatColor.GRAY + "Used to execute the code inside it")
            .lore(ChatColor.GRAY + "if the condition directly before it")
            .lore(ChatColor.GRAY + "was not met. Else must be placed directly")
            .lore(ChatColor.GRAY + "after any If block's closing bracket.")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "brackets"), "true")
            .setCustomTag(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "chest"), "true")
            .make();
        ItemManager.addItem("dev.else", notElse);

        ItemStack glitch = new ItemBuilder(Material.STICK)
                .name(ChatColor.RED + "Glitch Stick")
                .lore(ChatColor.GRAY + "If a codeblock wont break,")
                .lore(ChatColor.GRAY + "break it with this.")
                .make();
        ItemManager.addItem("dev.glitch", glitch);


    }
}
