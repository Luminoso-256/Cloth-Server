package src.net.minecraft.cloth;

import src.net.minecraft.MinecraftServer;
import src.net.minecraft.core.ICommandListener;
import src.net.minecraft.cloth.commands.*;
import src.net.minecraft.core.ServerConfigurationManager;

public class commandDispatcher {
    //passthrough the mc server so we can grab anything else these commands might need
    public static void handleCommand(String command, MinecraftServer server, ICommandListener icommandlistener, String username){
        String commandLowercase = command.toLowerCase();
        switch(commandLowercase){

        }
    }
}
