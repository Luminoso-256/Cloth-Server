package src.net.minecraft.cloth.commands;

import src.net.minecraft.cloth.WorldGenParams;
import src.net.minecraft.core.ICommandListener;

public class seed {
    public static void command(String username, ICommandListener icommandlistener, String command){
        WorldGenParams params = new WorldGenParams();
        icommandlistener.log("Seed for this world is:"+ params.GetSeedFromPropertiesFile());
    }
}
