package hypersquare.hypersquare.dev.action.player;

import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.item.ActionItem;
import hypersquare.hypersquare.item.DisplayValue;
import hypersquare.hypersquare.item.PlayerActionItems;
import hypersquare.hypersquare.menu.actions.ActionMenu;
import hypersquare.hypersquare.play.ActionArguments;
import hypersquare.hypersquare.play.CodeSelection;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerCreativeModeAction implements Action {

    @Override
    public void execute(CodeSelection selection, ActionArguments args) {
          for (Player p : selection.players()) {
              p.setGameMode(GameMode.CREATIVE);
          }
    }

    @Override
    public ActionParameter[] parameters() {
        return new ActionParameter[]{};
    }

    @Override
    public String getId() {
        return "player_action_creative_mode";
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
    public PlayerActionItems getCategory() {
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
