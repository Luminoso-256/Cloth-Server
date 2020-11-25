package net.minecraft.cloth;

//Cloth globals, in case they are needed outside of main. Better design

import net.minecraft.cloth.command.Command;
import net.minecraft.cloth.command.Icommand;

import java.util.HashMap;

public class Globals {
    // [Cloth Version  β1.0.0]
    // [Cloth Release 1.0.0]

    //-------------------Release Information
    public static final boolean IS_PREVIEW = true; //I plan to release more early or test-specific releases now, so thisl be handy
    public static final String VERSION_STRING = "Cloth Beta 1.0.0-rc2";
    public static final int STITCH_HOOK_VERSION = -10; //for plugin compat. As evidenced by the negative, not prod ready yet
    public static final String TARGET_FEATURE = "release-candidate";


    //-------------------Easter-eggs/Tidbits
    public static final String[] WELCOME_MSG = {"Its itchy!", "Get stitching!",
            "Supporting alpha!", "Its in beta!", "Also check out MineOnline!",
            "Bugs are features!", "Report issues on github!", "Unobfuscated!",
            "Free for all!", "Open source!", "Fork me!", "Experimental shennigans!",
            "With contributions from many", "Compatible with JRE14!",  "Why do these messages exist?",
            "New flying pigmen!", "an uneasy alliance!", "buggy nether time!"};

    //-------------------Deaths


    //Defines number of deathmessages for each source
    public static final HashMap deathTypeMessageList=new HashMap<String, Integer>() {{
        put("fall", 3);
        put("entity", 3);
        put("lava", 1);
        put("suffocate", 1);
        put("explosion", 1);
        put("fire", 2);
        put("void", 2);
        put("drown", 3);
    }};


    //Cloth death system - registery of damage sources in player class. %player% =  player user name, %entity% = entity damage source if one existed, %world% = world name
    public static final HashMap deathMsgNames=new HashMap<String, String>() {{
        put("fall.1", "%player% fell from a high place");
        put("fall.2", "%player% hit the ground too hard");
        put("fall.3", "%player% went bungee jumping without the cord");
        put("entity.1", "%player% was slain by %entity%");
        put("entity.2", "%player% didn't want to live in the same world as %entity%");
        put("entity.3", "%player% was removed forcibly from %world% by %entity%");
        put("lava.1", "%player% tried to swim in lava");
        put("suffocate.1", "%player% was squished too much");
        put("explosion.1", "%player% went off with a bang");
        put("fire.1", "%player% learned not to play with fire");
        put("fire.2", "%player% went up in flames");
        put("void.1", "%player% fell out of the world");
        put("void.2", "%player% went into the unknown, and died for it");
        put("drown.1", "%player% didnt come up for air");
        put("drown.2", "%player% is sleeping with the fish");
        put("drown.3", "%player% drowned");
        put("test.1", "player.username: %player%. Last damaged entity: %entity%. World name: %world%. | TestDeathMsg");
    }};

    //-------------------Advancement

    //Eventually thisl be config
    public static final HashMap advancementNames=new HashMap<String, String>() {{
        //test
        put("test.clothdeveloperadvancement", "Eureka! The cloth advancement system works :)");
        put("test.duplicitadvancementcheck", "You got this again? yay anyway!");

        //inventory
        put("inventory.log", "Punch a tree!");
        put("inventory.stone", "Stone age");
        put("inventory.iron", "Aquire Hardware");
        put("inventory.diamond", "Diamonds!");
        put("inventory.diamondhoe", "Fool me once...");
        put("inventory.furnace", "Smelting time");
        put("inventory.ironpickaxe", "Inventory.ironpickaxe name (I forgot it)");
        put("inventory.diamondpickaxe", "[Placeholder title for inventory.diamondpickaxe]");
        put("inventory.mossycobblestone", "Mossy discovery");
        put("inventory.sponge", "Developer time!");



        //travel
        put("travel.nether", "getting buggier...");
        put("travel.buildlimit", "I can see my house from here!");
        put("travel.stableworldgenend", "Edge of the world");
        //stats
        put("stats.alldeaths", "PHD in dying");
        put("stats.voidout", "Wait, thats illegal");
        put("stats.hundreddeaths", "Way more than 9 lives");
        //crafting


    }};
    //-------------------Commands
    public static final HashMap commands=new HashMap<String, Icommand>() {{
       // put("help", );
    }};
}
