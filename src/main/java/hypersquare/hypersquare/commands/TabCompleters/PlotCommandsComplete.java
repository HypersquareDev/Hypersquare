package hypersquare.hypersquare.commands.TabCompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlotCommandsComplete implements TabCompleter {
        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
             List<String> completions = new ArrayList<>();
            completions.add("name");
            completions.add("icon");
            completions.add("dev");
            completions.add("builder");
            completions.add("glitch");
            completions.add("g");
            completions.add("unclaim");
            completions.add("stats");
            completions.add("setspawn");
            completions.add("spawn");
            completions.add("ad");



            if (args.length >= 2) {
                completions.clear();
                switch (args[0]) {

                    case "dev":
                    case "builder": {
                        completions.add("add");
                        completions.add("remove");
                        completions.add("list");
                        completions.add("clear");
                        break;
                    }
                    case "ad": {
                        completions.add("message");
                        completions.add("confirm");
                        break;
                    }
                }
            }
            if (args.length >= 3) {
                completions.clear();
                switch (args[0]){
                    case "dev":
                    case "builder":{
                        for (Player player : Bukkit.getOnlinePlayers()){
                            completions.add(player.getName());
                        }
                    }
                }
            }
            return completions;
        }
    }

