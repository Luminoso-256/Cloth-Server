package net.minecraft.cloth.command;

import net.minecraft.MinecraftServer;
public interface Icommand {
    public void runCommand(MinecraftServer server, String command, String username);
}

