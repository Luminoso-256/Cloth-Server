package net.minecraft.cloth.command;

import net.minecraft.MinecraftServer;
import net.minecraft.core.ICommandListener;

public interface Icommand {
    public void runCommand(MinecraftServer server, String command, String username, ICommandListener commandListener);
}

