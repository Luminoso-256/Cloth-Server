package net.minecraft.cloth;

import net.minecraft.MinecraftServer;
import net.minecraft.cloth.command.Icommand;
import net.minecraft.core.ICommandListener;
import static net.minecraft.Globals.*;


public class commandDispatcher {
    public static void handleCommand(MinecraftServer minecraftServer, String username, String command, ICommandListener commandListener){
        String[] CommandSplit = command.split(" ");
        Icommand targetCommand = (Icommand) commands.get(CommandSplit[0]);
        targetCommand.runCommand(minecraftServer, username, command, commandListener);
    }

}
