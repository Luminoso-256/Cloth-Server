package src.net.minecraft.cloth.commands;

import src.net.minecraft.MinecraftServer;
import src.net.minecraft.core.ICommandListener;

public class advancement {
    public static void command(String username, ICommandListener icommandlistener, String command, MinecraftServer server) {
        String[] args = command.split(" ");
        server.grantAdvancement(username, args[1]);
    }
}
