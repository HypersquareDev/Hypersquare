package hypersquare.hypersquare.dev.action.player;

import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.item.ActionItem;
import hypersquare.hypersquare.item.DisplayValue;
import hypersquare.hypersquare.item.PlayerActionItems;
import hypersquare.hypersquare.menu.actions.ActionMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SendMessageAction implements Action {
    @Override
    public void executeBlockAction(List<Entity> targets, Barrel barrel) {

    }

    @Override
    public ActionParameter[] parameters() {
        return new ActionParameter[] {
                new ActionParameter(DisplayValue.TEXT, true, false, Component.text("The message(s) to send."), "messages")
        };
    }

    @Override
    public String getId() {
        return "player_action_send_message";
    }

    @Override
    public String getSignName() {
        return "SendMessage";
    }

    @Override
    public String getName() {
        return "Send Message";
    }

    @Override
    public PlayerActionItems getCategory() {
        return PlayerActionItems.PLAYER_ACTION_COMMUNICATION;
    }

    @Override
    public ItemStack item() {
        return new ActionItem()
                .setMaterial(Material.OAK_SIGN)
                .setName(Component.text("Send Message").color(NamedTextColor.GREEN))
                .setDescription(Component.text("Sends the player all of the"),
                        Component.text("messages in the barrel"))
                .setParameters(parameters())
                .build();
    }

    @Override
    public ActionMenu actionMenu(CodeActionData data) {
        return new ActionMenu(this, 4, data)
                .parameter("messages", 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26);
    }
}
