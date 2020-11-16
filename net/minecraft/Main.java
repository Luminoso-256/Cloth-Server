package net.minecraft;

import net.minecraft.clothutils.GameruleManager;
import net.minecraft.clothutils.plugins.stich.StitchLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import org.luaj.vm2.Globals;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom Main class for stitch & convience
 * @author Luminoso-256
 */
public class Main {
    // [Cloth Version  β1.0.0]
    // [Cloth Release 1.0.0]

    public static final String VERSION_STRING = "[Cloth Alpha 1.9.5]";


    public static final Logger logger = Logger.getLogger("Minecraft");
    public static final MinecraftServer minecraftServer = new MinecraftServer(); //Get a reference to our lovely MC server class
    public static void main(String[] args){
        logger.info("[Cloth] Cloth "+VERSION_STRING+"| Client Ver: Alpha1.2.6 | Original Server Ver: 0.28.0");
        logger.info("[Stitch] Loading stitch plugins from /plugins...");
        Globals _G = JsePlatform.standardGlobals();
        //Main classes
        LuaValue MinecraftServer = CoerceJavaToLua.coerce(minecraftServer);
        LuaValue ConfigManager = CoerceJavaToLua.coerce(minecraftServer.configManager);
        LuaValue WorldManager = CoerceJavaToLua.coerce(minecraftServer.worldMngr);
        LuaValue PropertyManager = CoerceJavaToLua.coerce(minecraftServer.propertyManagerObj);
        LuaValue GameruleManager = CoerceJavaToLua.coerce(new GameruleManager());
        //LuaValue Block = CoerceJavaToLua.coerce(new Block(0, Material.air));


        //All of our lovely small classes we could ever possible need
        LuaValue EntityCreeper = CoerceJavaToLua.coerce(new EntityCreeper(minecraftServer.worldMngr));
        LuaValue EntitySpider = CoerceJavaToLua.coerce(new EntitySpider(minecraftServer.worldMngr));
        LuaValue EntitySkeleton = CoerceJavaToLua.coerce(new EntitySkeleton(minecraftServer.worldMngr));
        LuaValue EntityZombie = CoerceJavaToLua.coerce(new EntityZombie(minecraftServer.worldMngr));
        //Main classes
        _G.set("minecraft_server", MinecraftServer);
        _G.set("world", WorldManager);
        _G.set("config_manager", ConfigManager);
        _G.set("property_manager", PropertyManager);
        _G.set("gamerule_manager", GameruleManager);
        //small classes
        _G.set("entity_creeper", EntityCreeper);
        _G.set("entity_spider", EntitySpider);
        _G.set("entity_skeleton", EntitySkeleton);
        _G.set("entity_zombie", EntityZombie);


        StitchLoader stich = new StitchLoader(_G);

        stich.RegisterAllPlugins();
        logger.info("[Stitch] Calling initiliazation hook");
         List<String> DummyList = new ArrayList<>();
        stich.CallHook("OnServerInit", DummyList);

        logger.info("[Cloth] Checking world seed");
        PropertyManager propertyManager = new PropertyManager(new File("server.properties"));
        propertyManager.getLongProperty("seed", 1l);

        logger.info("[Cloth] Cloth init complete. Deffering to MinecraftServer class ");
        try {
           // net.minecraft.server.MinecraftServer.main(args);
            minecraftServer.main(args);
        } catch (Throwable t) {
            logger.log(Level.SEVERE, null, t);
        }
    }
}
