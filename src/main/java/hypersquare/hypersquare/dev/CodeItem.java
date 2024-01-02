package hypersquare.hypersquare.dev;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static hypersquare.hypersquare.Hypersquare.mm;

public class CodeItem {
    private final Material material;
    private String name;
    private String[] description;
    private String[] examples;
    private String[] additionalInfo;

    public CodeItem(Material material) {
        this.material = material;
    }

    public CodeItem name(String name) {
        this.name = name;
        return this;
    }

    public CodeItem description(String... description) {
        this.description = description;
        return this;
    }

    public CodeItem examples(String... examples) {
        this.examples = examples;
        return this;
    }

    public CodeItem additionalInfo(String... additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    private void addSection(List<Component> lore, String header, String[] lines) {
        if (lines != null) {
            lore.add(Component.empty());
            lore.add(mm.deserialize(header));

            // Create a new entry in the list for each %n effectively creating a newline
            List<String> finalLines = new ArrayList<>(Arrays.asList(lines));
            for (int i = 0; i < finalLines.size(); i++) {
                finalLines.set(i, "<aqua>» " + finalLines.get(i));
                while (finalLines.get(i).contains("%n")) {
                    String[] split = finalLines.get(i).split("%n", 2);
                    finalLines.set(i, split[0]);
                    if (split.length > 1) {
                        finalLines.add(i + 1, split[1]);
                        i++;
                    }
                }
            }
            lines = finalLines.toArray(new String[0]);

            for (String line : lines) {
                lore.add(mm.deserialize("<gray>" + line));
            }
        }
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(mm.deserialize(name).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        List<Component> lore = new ArrayList<>();

        // Treat %n as a newline
        for (String s : description) {
            lore.add(mm.deserialize("<gray>" + s));
        }

        // Examples and Additional Info share same function due to similar formatting
        addSection(lore, "<white>Examples:", examples);
        addSection(lore, "<blue>Additional Info:", additionalInfo);

        lore.replaceAll(component -> component.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE));

        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }
}