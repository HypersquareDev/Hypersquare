package hypersquare.hypersquare;

import hypersquare.dev.CodeItem;
import hypersquare.dev.Codeblock;
import org.bukkit.Material;

public class RegisterActions {

    static CodeItem playerEvent = new CodeItem(Material.DIAMOND_BLOCK, Codeblock.PLAYER_EVENT, null, new String[]{"Used to execute code when something", "is done by (or happens to) a player."}, new String[]{"Detect when a player dies"}, null, null);
    public static void addItem(){
        ItemManager.addItem("playerEvent", playerEvent.buildItem());
    }
}
