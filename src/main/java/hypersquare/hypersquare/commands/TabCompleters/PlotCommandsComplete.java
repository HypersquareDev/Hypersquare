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
            completions.add("help");
            completions.add("name");
            completions.add("editname");
            completions.add("icon");
            completions.add("tags");
            completions.add("dev");
            completions.add("builder");
            completions.add("codespace");
            completions.add("setspawn");
            completions.add("spawn");
            completions.add("ad");
            completions.add("kick");
            completions.add("ban");
            completions.add("banlist");
            completions.add("unban");
            completions.add("whitelist");
            completions.add("vars");
            completions.add("varpurge");
            completions.add("clear");
            completions.add("glitch");
            completions.add("g");
            completions.add("unclaim");


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
                    case "help" : {
                        completions.add("1");
                        completions.add("2");
                        completions.add("3");
                        break;

                    }
                    case "codespace" : {
                        completions.add("add");
                        completions.add("remove");
                        break;

                    }
                    case "ad" :{
                        completions.add("message");
                        completions.add("purchase");
                        break;

                    }
                    case "whitelist" :{
                        completions.add("on");
                        completions.add("off");
                        completions.add("add");
                        completions.add("remove");
                        break;

                    }
                    case "vars" : {
                        completions.add("[name/value]");
                        break;
                    }
                    case "kick":
                    case "ban": {
                        for (Player player : Bukkit.getOnlinePlayers()){
                            completions.add(player.getName());
                        }
                    }
                }
            }
            if (args.length >= 3) {
                completions.clear();
                switch (args[0]){
                    case "codespace":{
                        completions.add("-c");
                        completions.add("-d");
                        completions.add("-l");
                        completions.add("black");
                        completions.add("blue");
                        completions.add("brown");
                        completions.add("cyan");
                        completions.add("gray");
                        completions.add("green");
                        completions.add("light_blue");
                        completions.add("light_gray");
                        completions.add("lime");
                        completions.add("magenta");
                        completions.add("orange");
                        completions.add("pink");
                        completions.add("purple");
                        completions.add("red");
                        completions.add("tinted");
                        completions.add("white");
                        completions.add("yellow");
                    }
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

