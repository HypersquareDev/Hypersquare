package hypersquare.hypersquare.dev.actions;

import hypersquare.hypersquare.item.Action;
import hypersquare.hypersquare.item.DisplayValue;
import hypersquare.hypersquare.item.PlayerActionItems;
import hypersquare.hypersquare.menu.actions.ActionMenu;
import net.kyori.adventure.text.Component;
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
        return "send_message";
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
        return null;
    }

    @Override
    public ActionMenu actionMenu() {
        return null;
    }
}
