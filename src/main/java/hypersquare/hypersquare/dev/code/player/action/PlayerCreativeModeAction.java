package hypersquare.hypersquare.dev.code.player.action;

import hypersquare.hypersquare.dev.ActionTag;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.item.action.ActionItem;
import hypersquare.hypersquare.item.action.ActionMenuItem;
import hypersquare.hypersquare.item.action.player.PlayerActionItems;
import hypersquare.hypersquare.menu.actions.ActionMenu;
import hypersquare.hypersquare.play.execution.ExecutionContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerCreativeModeAction implements Action {

    @Override
    public void execute(ExecutionContext ctx) {
        for (Player p : ctx.selection().players()) {
            p.setGameMode(GameMode.CREATIVE);
        }
    }

    @Override
    public ActionParameter[] parameters() {
        return new ActionParameter[]{};
    }

    @Override
    public ActionTag[] tags() {
        return new ActionTag[] {};
    }

    @Override
    public String getId() {
        return "creative_mode";
    }
    @Override
    public String getCodeblockId() {
        return "player_action";
    }

    @Override
    public String getSignName() {
        return "CreativeMode";
    }

    @Override
    public String getName() {
        return "Set to Creative Mode";
    }

    @Override
    public ActionMenuItem getCategory() {
        return PlayerActionItems.SETTINGS_CATEGORY;
    }

    @Override
    public ItemStack item() {
        return new ActionItem()
                .setMaterial(Material.GRASS_BLOCK)
                .setName(Component.text("Set to Creative Mode").color(TextColor.color(0xAAFF55)))
                .setDescription(Component.text("Sets a player's game"),
                        Component.text("mode to Creative."))
                .setParameters(parameters())
                .build();
    }

    @Override
    public ActionMenu actionMenu(CodeActionData data) {
        return new ActionMenu(this, 3, data);
    }
}
