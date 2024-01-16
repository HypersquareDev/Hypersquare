package hypersquare.hypersquare.dev;

import org.bukkit.entity.Player;

public class LoadItems {
    public static void devInventory(Player player) {
        player.getInventory().setItem(0, CodeItems.PLAYER_EVENT_ITEM);
        player.getInventory().setItem(1, CodeItems.IF_PLAYER_ITEM);
        player.getInventory().setItem(2, CodeItems.PLAYER_ACTION_ITEM);
        player.getInventory().setItem(3, CodeItems.CALL_FUNCTION_ITEM);
        player.getInventory().setItem(4, CodeItems.START_PROCESS_ITEM);
        player.getInventory().setItem(9, CodeItems.CONTROL_ITEM);
        player.getInventory().setItem(10, CodeItems.SELECT_OBJECT_ITEM);
        player.getInventory().setItem(11, CodeItems.REPEAT_ITEM);
        player.getInventory().setItem(12, CodeItems.ELSE_ITEM);
        player.getInventory().setItem(18, CodeItems.SET_VARIABLE_ITEM);
        player.getInventory().setItem(19, CodeItems.IF_VARIABLE_ITEM);
        player.getInventory().setItem(20, CodeItems.GAME_ACTION_ITEM);
        player.getInventory().setItem(21, CodeItems.IF_GAME_ITEM);
        player.getInventory().setItem(27, CodeItems.ENTITY_EVENT_ITEM);
        player.getInventory().setItem(28, CodeItems.IF_ENTITY_ITEM);
        player.getInventory().setItem(29, CodeItems.ENTITY_ACTION_ITEM);
        player.getInventory().setItem(30, CodeItems.FUNCTION_ITEM);
        player.getInventory().setItem(31, CodeItems.PROCESS_ITEM);
    }
}
