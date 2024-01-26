package hypersquare.hypersquare.dev.code.player.action;

import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.item.*;
import hypersquare.hypersquare.menu.actions.ActionMenu;
import hypersquare.hypersquare.play.ExecutionContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerSendMessageAction implements Action {

    @Override
    public void execute(ExecutionContext ctx) {
        for (Player p : ctx.selection().players()) {
            if (ctx.args().allNonNull("messages").isEmpty() || ctx.args().allNonNull("messages") == null) {
                p.sendMessage(Component.empty());
                return;
            }
            
            for (Component c : ctx.args().<Component>allNonNull("messages")) {
                p.sendMessage(c);
            }
        }
    }

    @Override
    public ActionParameter[] parameters() {
        return new ActionParameter[] {
                new ActionParameter(DisplayValue.TEXT, true, true, Component.text("The message(s) to send."), "messages")
        };
    }

    @Override
    public String getId() {
        return "send_message";
    }

    @Override
    public String getCodeblockId() {
        return "player_action";
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
    public ActionMenuItem getCategory() {
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
