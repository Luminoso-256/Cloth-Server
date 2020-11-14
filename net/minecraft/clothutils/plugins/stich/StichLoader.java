package net.minecraft.clothutils.plugins.stich;


import java.io.File;
import java.util.logging.Logger;

/**
 * Core stich system
 * @author Luminoso-256
 *
 * Steps:
 * 1. Search plugins directory, find all plugin files
 * 2. Note each plugin files hooks and register them to the apropriate hook list
 * 3. Listen to each hook, and on call defer to the plugin
 */
public class StichLoader {
    public static void RegisterAllPlugins(){
        Logger logger = Logger.getLogger("Minecraft");
        File PluginsDirectory = new File("plugins");
        String[] PluginFiles = PluginsDirectory.list();
        for(int i=0; i<PluginFiles.length; i++) {
            logger.info("Found Plugin: "+PluginFiles[i]);
            RegisterPlugin(new File(PluginFiles[i]));

        }
    }
    private static void RegisterPlugin(File plugin){

    }
}
