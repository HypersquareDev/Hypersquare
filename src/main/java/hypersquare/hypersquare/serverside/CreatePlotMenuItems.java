package hypersquare.hypersquare.serverside;

import hypersquare.hypersquare.serverside.utils.managers.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CreatePlotMenuItems {

    public static void init(){
        ItemStack CreateBasicPlot = new ItemBuilder(Material.POLISHED_ANDESITE)
                .name("<color:#7070FF>"+ "Basic Plot")
                .lore(ChatColor.GRAY + "Size: 64x64")
                .lore("")
                .lore(ChatColor.GRAY + "You have used " + ChatColor.GREEN + "goofy haah" + ChatColor.GRAY + " of your Basic plots.")
                .lore("")
                .lore(ChatColor.GREEN + "Click to create!")
                .hideFlags()
                .make();
        ItemManager.addItem("menu.basic_plot", CreateBasicPlot);

        ItemStack CreateLargePlot = new ItemBuilder(Material.IRON_BLOCK)
                .name("<color:#7FFF7F>"+ "Large Plot")
                .lore(ChatColor.GRAY + "Size: 128x128")
                .lore("")
                .lore(ChatColor.GRAY + "You have used " + ChatColor.GREEN + "goofy haah" + ChatColor.GRAY + " of your Large plots.")
                .lore("")
                .lore(ChatColor.GREEN + "Click to create!")
                .hideFlags()
                .make();
        ItemManager.addItem("menu.large_plot", CreateLargePlot);

        ItemStack CreatehugePlot = new ItemBuilder(Material.GOLD_BLOCK)
                .name("<color:#FFFFAA>"+ "Huge Plot")
                .lore(ChatColor.GRAY + "Size: 256x256")
                .lore("")
                .lore(ChatColor.GRAY + "You have used " + ChatColor.RED + "goofy ahaa" + ChatColor.GRAY + " of your huge plots.")
                .lore("")
                .lore(ChatColor.GREEN + "Click to create!")
                .hideFlags()
                .make();
        ItemManager.addItem("menu.huge_plot", CreatehugePlot);

        ItemStack CreatemassivePlot  = new ItemBuilder(Material.DIAMOND_BLOCK)
                .name("<color:#00e8ff>"+ "Massive Plot")
                .lore(ChatColor.GRAY + "Size: 512x512")
                .lore("")
                .lore(ChatColor.GRAY + "You have used " + ChatColor.RED + "goofy ahaa" + ChatColor.GRAY + " of your massive Plots.")
                .lore("")
                .lore(ChatColor.GREEN + "Click to create!")
                .hideFlags()
                .make();
        ItemManager.addItem("menu.massive_plot", CreatemassivePlot);

        ItemStack CreateGiganticPlot  = new ItemBuilder(Material.NETHERITE_BLOCK)
                .name("<color:#333232>"+ "Gigantic Plot")
                .lore(ChatColor.GRAY + "Size: 1024x1024")
                .lore("")
                .lore(ChatColor.GRAY + "You have used " + ChatColor.RED + "goofy ahaha" + ChatColor.GRAY + " of your Gigantic Plots.")
                .lore("")
                .lore(ChatColor.GREEN + "Click to create!")
                .hideFlags()
                .make();
        ItemManager.addItem("menu.gigantic_plot", CreateGiganticPlot);

        ItemStack CreatePlot  = new ItemBuilder(Material.GREEN_STAINED_GLASS)
                .name("<color:#7FFF7F>"+ "Claim new plot")
                .lore(ChatColor.GRAY + "Click here to claim a new plot!")
                .hideFlags()
                .make();
        ItemManager.addItem("menu.create_plot", CreatePlot);
    }


}
