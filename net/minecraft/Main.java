package net.minecraft;

import net.minecraft.clothutils.plugins.stich.StitchLoader;
import net.minecraft.server.MinecraftServer;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom Main class for stich & convience
 * @author Luminoso-256
 */
public class Main {
    // [Cloth Version  β1.0.0]
    // [Cloth Release 1.0.0]
    public static final String VERSION_STRING = "[Cloth Version α1.8.0]";


    public static final Logger logger = Logger.getLogger("Minecraft");
    public static final MinecraftServer minecraftServer = new MinecraftServer(); //Get a reference to our lovely MC server class
    public static void main(String[] args) throws IOException {
        logger.info("[Cloth] Cloth "+VERSION_STRING+"| Client Ver: Alpha1.2.6 | Original Server Ver: 0.28.0");
        logger.info("[Stitch] Loading stitch plugins from /plugins...");
        StitchLoader stich = new StitchLoader(JsePlatform.standardGlobals()); //Gotta change these to custom globals later
        stich.RegisterAllPlugins();
        logger.info("[Stitch] Calling initiliazation hook");
        stich.CallHook("OnServerInit");



        logger.info("[Cloth] Cloth init complete. Deffering to MinecraftServer class ");
        try {
           // net.minecraft.server.MinecraftServer.main(args);
            minecraftServer.main(args);
        } catch (Throwable t) {
            logger.log(Level.SEVERE, null, t);
        }
    }
}
