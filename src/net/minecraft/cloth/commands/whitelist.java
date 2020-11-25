package src.net.minecraft.cloth.commands;

import src.net.minecraft.cloth.file.GameruleManager;
import src.net.minecraft.core.ICommandListener;
import src.net.minecraft.core.ServerConfigurationManager;

public class whitelist {
    public static void command(String username, ICommandListener icommandlistener, String command, ServerConfigurationManager configManager){
        String[] args =  command.toLowerCase().split(" ");
        if(args[1] == "add"){
            configManager.whitelistPlayer(args[2]);
        }
        if(args[1] == "remove"){
            configManager.deWhitelistPlayer(args[2]);
        }
        if(args[1] == "on"){
            GameruleManager.setBooleanGamerule("usewhitelist", true);
        }
        if(args[1] == "off"){
            GameruleManager.setBooleanGamerule("usewhitelist", false);
        }
    }
}
