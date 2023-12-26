package hypersquare.hypersquare.dev.actions;

import hypersquare.hypersquare.dev.Text;
import hypersquare.hypersquare.items.*;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SendMessageAction implements Action {
    @Override
    public void executeBlockAction(List<Entity> targets, Chest chest) {
        StringBuilder output = new StringBuilder();
        for (ItemStack item : chest.getInventory().getContents()){
            if (item != null) {
                Text text = new Text(item);
                if (text.getValue() == null) {
                    text.setValue(MiniMessage.miniMessage().serialize(item.displayName()));
                }
                output.append(" ").append(text.getValue());
            }
        }
        String finalValue = String.valueOf(output);
        finalValue = finalValue.strip();
        for (Entity entity : targets){
            Player player = (Player) entity;
            player.sendMessage(MiniMessage.miniMessage().deserialize(finalValue));
        }

    }

    @Override
    public String getId() {
        return "SendMessage";
    }

    @Override
    public PlayerActionItems getCategory() {
        return PlayerActionItems.PLAYER_ACTION_COMMUNICATION;
    }

    @Override
    public ItemStack item() {
        return new ActionItem()
                .setMaterial(Material.BOOK)
                .setName("<green>Send Message")
                .setDescription("Sends a message to the player", "containing the text in the chest")
                .setArguments(
                        new ActionArgument()
                                .setType(DisplayValue.TEXT)
                                .setPlural(true)
                                .setDescription("Text to send")
                )
                .build();
    }
}
