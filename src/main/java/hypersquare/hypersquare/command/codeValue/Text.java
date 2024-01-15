package hypersquare.hypersquare.command.codeValue;

import hypersquare.hypersquare.Hypersquare;
import hypersquare.hypersquare.item.CodeValues;
import hypersquare.hypersquare.util.Utilities;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class Text implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (Hypersquare.mode.get(player).equalsIgnoreCase("coding")) {
                StringBuilder value = new StringBuilder();
                boolean legacy = false;
                if (args.length >= 1) {
                    for (int i = 0; i < args.length; i++) {
                        value.append(args[i]);
                        if (i < args.length - 1) {
                            value.append(" ");
                        }
                    }

                    String finalValue = String.valueOf(value);

                    if (finalValue.contains("--legacy")) {
                        legacy = true;
                        finalValue = finalValue.replace("--legacy", "").strip();
                    }


                    player.getInventory().addItem(CodeValues.TEXT.build(finalValue, legacy));
                } else {
                    player.getInventory().addItem(CodeValues.TEXT.build(null, false));
                }

            } else {
                Utilities.sendError(player, "You can only use this command in dev mode.");
            }
        }
        return true;
    }

    @EventHandler
    public void Chat(AsyncChatEvent event) {
        if (Hypersquare.mode.get(event.getPlayer()).equals("coding")) {
            System.out.println("coding");
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
            NamespacedKey key = new NamespacedKey(Hypersquare.instance, "varitem");
            if (item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING) != null) {
                event.setCancelled(true);
                boolean legacy = false;
                Component message = event.message();
                String finalValue = PlainTextComponentSerializer.plainText().serialize(message);
                System.out.println(finalValue);
                if (finalValue.contains("--legacy")) {
                    legacy = true;
                    finalValue = finalValue.replace("--legacy", "").strip();
                }


                event.getPlayer().getInventory().setItemInMainHand(CodeValues.TEXT.build(finalValue, legacy));
            }
        }
    }
}
