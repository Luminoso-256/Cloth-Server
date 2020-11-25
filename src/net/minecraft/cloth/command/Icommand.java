package src.net.minecraft.cloth.command;

import src.net.minecraft.MinecraftServer;
public interface Icommand {
    public void runCommand(MinecraftServer server, String command, String username);
}

