package hypersquare.hypersquare.dev;

import hypersquare.hypersquare.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class CreatePlotMenuItems {

    public static void init(){
        ItemStack CreateBasicPlot = new ItemBuilder(Material.POLISHED_ANDESITE)
                .name(net.md_5.bungee.api.ChatColor.of("#7070FF") + "Basic Plot")
                .lore(ChatColor.GRAY + "Size: 64x64")
                .lore("")
                .lore(ChatColor.GRAY + "You have used " + ChatColor.GREEN + "goofy haah" + ChatColor.GRAY + " of your Basic plots.")
                .lore("")
                .lore(ChatColor.GREEN + "Click to create!")
                .hideFlags()
                .make();
        ItemManager.addItem("menu.basic_plot", CreateBasicPlot);

        ItemStack CreateLargePlot = new ItemBuilder(Material.IRON_BLOCK)
                .name(net.md_5.bungee.api.ChatColor.of("#7FFF7F") + "Large Plot")
                .lore(ChatColor.GRAY + "Size: 128x128")
                .lore("")
                .lore(ChatColor.GRAY + "You have used " + ChatColor.GREEN + "goofy haah" + ChatColor.GRAY + " of your Large plots.")
                .lore("")
                .lore(ChatColor.GREEN + "Click to create!")
                .hideFlags()
                .make();
        ItemManager.addItem("menu.large_plot", CreateLargePlot);

        ItemStack CreateMassivePlot = new ItemBuilder(Material.GOLD_BLOCK)
                .name(net.md_5.bungee.api.ChatColor.of("#FFFFAA") + "Massive Plot")
                .lore(ChatColor.GRAY + "Size: 256x256")
                .lore("")
                .lore(ChatColor.GRAY + "You have used " + ChatColor.RED + "goofy ahaa" + ChatColor.GRAY + " of your Massive plots.")
                .lore("")
                .lore(ChatColor.GREEN + "Click to create!")
                .hideFlags()
                .make();
        ItemManager.addItem("menu.massive_plot", CreateMassivePlot);

        ItemStack CreateHugePlot  = new ItemBuilder(Material.DIAMOND_BLOCK)
                .name(net.md_5.bungee.api.ChatColor.of("#00e8ff") + "Huge Plot")
                .lore(ChatColor.GRAY + "Size: 512x512")
                .lore("")
                .lore(ChatColor.GRAY + "You have used " + ChatColor.RED + "goofy ahaa" + ChatColor.GRAY + " of your Huge Plots.")
                .lore("")
                .lore(ChatColor.GREEN + "Click to create!")
                .hideFlags()
                .make();
        ItemManager.addItem("menu.huge_plot", CreateHugePlot);

        ItemStack CreateGiganticPlot  = new ItemBuilder(Material.NETHERITE_BLOCK)
                .name(net.md_5.bungee.api.ChatColor.of("#333232") + "Gigantic Plot")
                .lore(ChatColor.GRAY + "Size: 1024x1024")
                .lore("")
                .lore(ChatColor.GRAY + "You have used " + ChatColor.RED + "goofy ahaha" + ChatColor.GRAY + " of your Gigantic Plots.")
                .lore("")
                .lore(ChatColor.GREEN + "Click to create!")
                .hideFlags()
                .make();
        ItemManager.addItem("menu.gigantic_plot", CreateGiganticPlot);

        ItemStack CreatePlot  = new ItemBuilder(Material.GREEN_STAINED_GLASS)
                .name(net.md_5.bungee.api.ChatColor.of("#7FFF7F") + "Claim new plot")
                .lore(ChatColor.GRAY + "Click here to claim a new plot!")
                .hideFlags()
                .make();
        ItemManager.addItem("menu.create_plot", CreatePlot);
    }


}
