package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.dev.action.EmptyAction;
import hypersquare.hypersquare.dev.code.player.condition.IfPlayerHolding;
import hypersquare.hypersquare.dev.code.player.action.PlayerCreativeModeAction;
import hypersquare.hypersquare.dev.code.player.action.PlayerSendMessageAction;
import hypersquare.hypersquare.dev.code.var.action.AssignVariableAction;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.code.player.action.PlayerGiveItemsAction;
import hypersquare.hypersquare.item.action.ActionMenuItem;
import hypersquare.hypersquare.menu.actions.ActionMenu;
import hypersquare.hypersquare.play.ExecutionContext;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public enum Actions implements Action {
    EMPTY(new EmptyAction()),

    PLAYER_GIVE_ITEMS(new PlayerGiveItemsAction()),
    PLAYER_SEND_MESSAGE(new PlayerSendMessageAction()),

    PLAYER_CREATIVE_MODE(new PlayerCreativeModeAction()),
    IF_PLAYER_HOLDING(new IfPlayerHolding()),
    ASSIGN_VARIABLE(new AssignVariableAction())
    ;

    final Action a;
    Actions(Action a) {
        this.a = a;
    }

    public static Action getAction(String id, String codeblockId) {
        if (id == null || codeblockId == null || id.equals("empty")) return Actions.EMPTY;

        for (Action action : values()) {
            if (Objects.equals(action.getCodeblockId(), codeblockId) && Objects.equals(action.getId(), id)) return action;
        }
        return null;
    }

    @Override
    public ActionParameter[] parameters() {
        return a.parameters();
    }

    @Override
    public ActionTag[] tags() {
        return a.tags();
    }

    @Override
    public String getId() {
        return a.getId();
    }

    @Override
    public String getCodeblockId() {
        return a.getCodeblockId();
    }

    @Override
    public String getSignName() {
        return a.getSignName();
    }

    @Override
    public String getName() {
        return a.getName();
    }

    @Override
    public ActionMenuItem getCategory() {
        return a.getCategory();
    }

    @Override
    public ItemStack item() {
        return a.item();
    }

    @Override
    public ActionMenu actionMenu(CodeActionData data) {
        return a.actionMenu(data);
    }

    @Override
    public void execute(ExecutionContext ctx) {
        a.execute(ctx);
    }
}
