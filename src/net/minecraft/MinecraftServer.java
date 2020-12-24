// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft;

import net.minecraft.cloth.FallbackIdMaps;
import net.minecraft.cloth.WorldGenParams;
import net.minecraft.cloth.file.*;
import net.minecraft.cloth.nether.Teleporter;
import net.minecraft.cloth.plugins.stich.StitchLoader;
import net.minecraft.core.*;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static net.minecraft.Globals.*;

public class MinecraftServer
        implements ICommandListener, Runnable {
    public static MinecraftServer singleton;
    public static Logger logger = Logger.getLogger("Minecraft");
    public static HashMap field_6037_b = new HashMap();
    public NetworkListenThread field_6036_c;
    public PropertyManager propertyManagerObj;
    public WorldServer worldMngr[];
    public ServerConfigurationManager configManager;
    public boolean serverRunning;
    public String currentTask;
    public int percentDone;
    public EntityTracker entityTracker[];
    public boolean onlineMode;
    public boolean noAnimals;
    public boolean isPvpEnabled;
    //Sleep vote
    public boolean IsSleepVoteOngoing = false;
    //  public int SleepVoteRemainingTime = 0;
    public int SleepVoteYesCount = 0;
    public int SleepVoteNoCount = 0;
    public String worldName;
    public AdvancementManager advancementManager = new AdvancementManager();
    public PlayerDataManager playerDataManager = PlayerDataManager.getInstance();
    public GameruleManager gameruleManager = GameruleManager.getInstance();
    public BlockMappingsManager blockMaps = new BlockMappingsManager(new File("blocks.mappings"));
    public StitchLoader stitch;
    public HashMap<String, String> advancementCriterion;
    int deathTime;
    FallbackIdMaps fallbackBlockMaps = new FallbackIdMaps();
    private boolean field_6025_n;
    private java.util.List field_9010_p;
    private java.util.List commands;
    private List<String> DummyList = new ArrayList<>();
    public MinecraftServer() {
        singleton = this;
        field_6025_n = true;
        serverRunning = false;
        deathTime = 0;
        field_9010_p = new ArrayList();
        commands = Collections.synchronizedList(new ArrayList());
        entityTracker = new EntityTracker[3];
        new ThreadSleepForever(this);
    }

    /// Nether Stuff
    ///
    ///

    public WorldServer getWorldManager(int i)
    {
        if(i == -1)
        {
            return worldMngr[1];
        }
        if(i == 1)
        {
            return worldMngr[2];
        } else
        {
            return worldMngr[0];
        }
    }


    public EntityTracker getEntityTracker(int i)
    {
        if(i == 1)
        {
            return entityTracker[2];
        }
        if(i == -1)
        {
            return entityTracker[1];
        } else
        {
            return entityTracker[0];
        }
    }

    ///
    ///
    ///

    public static boolean func_6015_a(MinecraftServer minecraftserver) {
        return minecraftserver.field_6025_n;
    }

    public void log(String text, String originator) {
        logger.info("[" + originator + "]" + ": " + text);
    }

    private boolean serverInit() throws UnknownHostException {

        ConsoleLogManager.init(); //No GUI till main MC class takes over
        ThreadCommandReader threadcommandreader = new ThreadCommandReader(this);
        threadcommandreader.setDaemon(true);
        threadcommandreader.start();


        logger.info("Proceeding with server initialization");
        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            logger.warning("**** NOT ENOUGH RAM!");
            logger.warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar server.jar\"");
        }
        logger.info("Loading properties");
        Random random = new Random();
        propertyManagerObj = new PropertyManager(new File("server.properties"));


        String ip = propertyManagerObj.getStringProperty("server-ip", "");
        String seedString = propertyManagerObj.getStringProperty("seed", String.valueOf(random.nextLong()));
        long seed = hashSeed(seedString);
        onlineMode = propertyManagerObj.getBooleanProperty("online-mode", true);
        noAnimals = propertyManagerObj.getBooleanProperty("spawn-animals", true);
        isPvpEnabled = propertyManagerObj.getBooleanProperty("pvp", true);
        InetAddress inetaddress = null;
        if (ip.length() > 0) {
            inetaddress = InetAddress.getByName(ip);
        }
        int i = propertyManagerObj.getIntProperty("server-port", 25565);
        logger.info((new StringBuilder()).append("Starting Minecraft server on ").append(ip.length() != 0 ? ip : "*").append(":").append(i).toString());
        try {
            field_6036_c = new NetworkListenThread(this, inetaddress, i);
        } catch (IOException ioexception) {
            logger.warning("**** FAILED TO BIND TO PORT!");
            logger.log(Level.WARNING, (new StringBuilder()).append("The exception was: ").append(ioexception.toString()).toString());
            logger.warning("Perhaps a server is already running on that port?");
            return false;
        }
        if (!onlineMode) {
            logger.warning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            logger.warning("The server will make no attempt to authenticate usernames. Beware.");
            logger.warning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            logger.warning("To change this, set \"online-mode\" to \"true\" in the server.settings file. ");
        }
        configManager = new ServerConfigurationManager(this);
        entityTracker[0] = new EntityTracker(this, 0);
        entityTracker[1] = new EntityTracker(this, -1);
        entityTracker[2] = new EntityTracker(this, 1);
        worldName = propertyManagerObj.getStringProperty("level-name", "world");
        logger.info((new StringBuilder()).append("Preparing level \"").append(worldName).append("\"").toString());
        loadWorld(worldName, seed);
        logger.info("Done! For help, type \"help\" or \"?\"");
        return true;

    }

    private long hashSeed(String seedString) {
        long seed;
        try {
            seed = Long.parseLong(seedString);
        } catch (Exception e) {
            seed = seedString.hashCode();
        }
        return seed;
    }

    private void loadWorld(String worldName, long seed) {
//        logger.info("Preparing start region");
//        overworld = new WorldServer(this, new File("."), worldName, seed, propertyManagerObj.getBooleanProperty("hellworld", false) ? -1 : 0);
//        overworld.addWorldAccess(new WorldManager(this, overworld));
//
//
//        if (gameruleManager.getGamerule("nether", true)) {
//            logger.info("[Cloth] Starting nether init");
//            netherWorld = new WorldServer(this, new File("."), worldName + "_nether", seed, -1);
//            netherWorld.addWorldAccess(new WorldManager(this, netherWorld));
//            logger.info("[Debug] created nether object with seed " + netherWorld.randomSeed);
//        }
//        overworld.monstersEnabled = propertyManagerObj.getBooleanProperty("spawn-monsters", true) ? 1 : 0;
//        configManager.setPlayerManager(overworld);
//        byte byte0 = 10;
//        for (int i = -byte0; i <= byte0; i++) {
//            outputPercentRemaining("Preparing spawn area", ((i + byte0) * 100) / (byte0 + byte0 + 1));
//            for (int j = -byte0; j <= byte0; j++) {
//                if (!field_6025_n) {
//                    return;
//                }
//                overworld.chunkProvider.loadChunk((overworld.spawnX >> 4) + i, (overworld.spawnZ >> 4) + j);
//            }
//
//        }
//
//        clearCurrentTask();
        worldMngr = new WorldServer[3];
//        SaveOldDir saveolddir = new SaveOldDir(new File("."), s, true);
        for(int i = 0; i < worldMngr.length; i++)
        {
            if(i == 0)
            {
                worldMngr[i] = new WorldServer(this, new File("."), worldName, i == 0 ? 0 : -1, seed);
            } else
            if(i == 1)
            {
                worldMngr[i] = new WorldServer(this, new File("."), worldName + "_nether", -1, seed);
            } else
            if(i == 2)
            {
                worldMngr[i] = new WorldServer(this, new File("."), worldName + "_sky", 1, seed);
                worldMngr[i].worldChunkLoadOverride = true;
            } else
            {
                worldMngr[i] = new WorldServer(this, new File("."), worldName + "_DIM" + (i - 1), i - 1, seed);
                worldMngr[i].worldChunkLoadOverride = true;
            }
            worldMngr[i].addWorldAccess(new WorldManager(this, worldMngr[i]));
            worldMngr[i].monstersEnabled = propertyManagerObj.getBooleanProperty("spawn-monsters", true) ? 1 : 0;
//            worldMngr[i].setAllowedSpawnTypes(propertyManagerObj.getBooleanProperty("spawn-monsters", true), spawnPeacefulMobs);
            configManager.setPlayerManager(worldMngr);
        }

        char c = '\304';
        long l1 = System.currentTimeMillis();
        label0:
        for(int j = 0; j < worldMngr.length; j++)
        {
            logger.info((new StringBuilder()).append("Preparing start region for level ").append(j).toString());
            if(j != 0 && !propertyManagerObj.getBooleanProperty("allow-nether", true))
            {
                continue;
            }
            WorldServer worldserver = worldMngr[j];
//            ChunkCoordIntPair chunkcoordinates = worldserver.getSpawnPoint();
            int k = -c;
            do
            {
                if(k > c || !serverRunning)
                {
                    continue label0;
                }
                for(int i1 = -c; i1 <= c && serverRunning; i1 += 16)
                {
                    long l2 = System.currentTimeMillis();
                    if(l2 < l1)
                    {
                        l1 = l2;
                    }
                    if(l2 > l1 + 1000L)
                    {
                        int j1 = (c * 2 + 1) * (c * 2 + 1);
                        int k1 = (k + c) * (c * 2 + 1) + (i1 + 1);
                        outputPercentRemaining("Preparing spawn area", (k1 * 100) / j1);
                        l1 = l2;
                    }
                    worldserver.chunkProvider.loadChunk(worldserver.spawnX + k >> 4, worldserver.spawnZ + i1 >> 4);
                    while(worldserver.func_6156_d() && serverRunning) ;
                }

                k += 16;
            } while(true);
        }

        clearCurrentTask();
    }

    private void outputPercentRemaining(String s, int i) {
        currentTask = s;
        percentDone = i;
        System.out.println((new StringBuilder()).append(s).append(": ").append(i).append("%").toString());
    }

    private void clearCurrentTask() {
        currentTask = null;
        percentDone = 0;
    }

    private void saveServerWorld() {
        logger.info("Saving chunks");
        for(int i = 0; i < worldMngr.length; i++)
        {
            WorldServer worldserver = worldMngr[i];
            worldserver.saveWorld(true, null);
//            worldserver.func_30006_w();
        }
    }

//    private void saveNetherWorld() {
//        logger.info("[Nether] Saving chunks");
//        netherWorld.saveWorld(true, null);
//    }

    private void shutdown() {
        logger.info("Stopping server");
        if (configManager != null) {
            configManager.savePlayerStates();
        }
        for(int i = 0; i < worldMngr.length; i++)
        {
            WorldServer worldserver = worldMngr[i];
            if(worldserver != null)
            {
                saveServerWorld();
            }
        }
    }

    public void func_6016_a() {
        field_6025_n = false;
    }

    public String createPlayerList() {
        String players = "";
        for (int i = 0; i < configManager.playerEntities.size(); i++) {
            EntityPlayer player = (EntityPlayer) configManager.playerEntities.get(i);
            players += " ";
            players += player.username;
            players += " ";

        }
        return players;
    }

    public void run() {
        //List<EntityPlayer> PlayerOld = new List<EntityPlayer>() {}

        try {
            if (serverInit()) {

                long l = System.currentTimeMillis();
                long l1 = 0L;
                while (field_6025_n)  //MAIN GAME LOOP FOR HOOKINS
                {

                    //configManager.sendChatMessageToAllPlayers("TEST_CONFIGMANAGER_SENDCHAT");
                    //Sleep vote control logic
                    if (IsSleepVoteOngoing) {
                        int TotalNumPlayers = configManager.playerEntities.size();
                        int TotalVoted = SleepVoteNoCount + SleepVoteYesCount;
                        if (TotalVoted == TotalNumPlayers) {
                            if (SleepVoteYesCount == SleepVoteNoCount) {
                                configManager.sendChatMessageToAllPlayers("The Sleep Vote was a stalemate. No action will be taken");
                            } else if (SleepVoteYesCount > SleepVoteNoCount) {
                                configManager.sendChatMessageToAllPlayers("The Sleep Vote recieved a majority approval! Skipping to morning...");
                                worldMngr[0].worldTime = 1000;
                            } else if (SleepVoteNoCount > SleepVoteYesCount) {
                                configManager.sendChatMessageToAllPlayers("The Sleep Vote recieved a majority dissaproval. No action will be taken");
                            }
                            IsSleepVoteOngoing = false;
                        }

                    }


                    //Per-player stuff
                    for (int i = 0; i < configManager.playerEntities.size(); i++) {
                        //Important
                        EntityPlayer player = (EntityPlayer) configManager.playerEntities.get(i);
                        InventoryPlayer inventory = player.inventory;


                        //--------Advancement
                        if (gameruleManager.getGamerule("enableadvancements", false)) {
                            // System.out.println("Looping advancement inv checks");

                            //inventory
                            for (int slot = 0; slot < inventory.getInventorySize(); slot++) {
                                ItemStack item = inventory.getStackInSlot(slot);
                                // System.out.println(slot);
                                if (item != null) {

                                    //Advancements check
                                    for (Map.Entry<String, String> entry : advancementCriterion.entrySet()) {
                                        String criterion = entry.getKey();
                                        // String advName = entry.getValue();

                                        if (criterion.contains("inventory.")) {
                                            String[] tSplit = criterion.split("\\.");
                                            // System.out.println(tSplit);
                                            String Item = tSplit[1];
                                            //letsa grab the id
                                            int itemID = blockMaps.getIdForString(Item, fallbackBlockMaps.GetIDForNamespacedBlockName(Item));
                                            if (item.itemID == itemID) {
                                                grantAdvancement(player.username, criterion); //criterion also happens to be the internal name
                                            }

                                        }
                                    }
                                }
                            }
                            for (Map.Entry<String, String> entry : advancementCriterion.entrySet()) {
                                String criterion = entry.getKey();
                                // String advName = entry.getValue();


                                //with this format buildlimit advancement looks like this:
                                //travel.y.128

                                if (criterion.contains("travel.")) {
                                    String[] tSplit = criterion.split("\\.");
                                    switch (tSplit[1]) {
                                        case "x":
                                            if (player.posX == Integer.parseInt(tSplit[2])) {
                                                grantAdvancement(player.username, criterion);
                                            }
                                            break;
                                        case "y":
                                            if (player.posY == Integer.parseInt(tSplit[2])) {
                                                grantAdvancement(player.username, criterion);
                                            }
                                            break;
                                        case "z":
                                            if (player.posZ == Integer.parseInt(tSplit[2])) {
                                                grantAdvancement(player.username, criterion);
                                            }
                                            break;
                                    }
                                }
                            }

                        }

                        //--------Death

                        if (player.damageSources.size() != 0) {
                        }
                        if (gameruleManager.getGamerule("announcedeath", true) == true && player.health <= 0) {
                            // your dead. Boohoo

                            player.IsDead = true;
                            if (player.IsDead && player.HasRespawed == false) {
                                //      System.out.println("IsDead:"+ player.IsDead+" HasRespawned:"+player.HasRespawed);

                                //Hardcore mode
                                boolean IsHardcode = gameruleManager.getGamerule("preview_hardcoremode", false);
                                //statsManager.updateStat(player.username, "hardcore_is_spectator", "true");

                                //Player Data Version of Stats

                                PlayerData pd = playerDataManager.getPlayerData(player.username);
                                if (pd != null) {
                                    pd.addDeath();

                                }
                                pd.resetBackUsages();
                                pd.setLastDeathLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
                                int deathsInt = pd.getDeaths();
                                playerDataManager.setPlayerData(player.username, pd);

                                //Advancement
                                if (deathsInt >= 100) {
                                    grantAdvancement(player.username, "stats.hundreddeaths");
                                }


                                // You WILL DIE PROPERLY
                                player.setEntityDead();
                                String DeathMsg;
                                //And then we will announce it
                                if (worldMngr[0].rand.nextInt(15) <= 1) {
                                    DeathMsg = player.username + " is going ghost!";
                                } else {
                                    DeathMsg = player.username + " died in mysterious circumstances";
                                }


                                if (!gameruleManager.getGamerule("specificdeath", false)) {
                                    DeathMsg = player.username + " has died. Rest in Peace"; // eventually ill get more interesting- maybe have a registery of dmg sources?
                                } else {

                                    String lastDamageSource = "";
                                    if (player.damageSources.size() != 0) {
                                        logger.info("[Debug] Grabbing last dmg source");
                                        lastDamageSource = player.damageSources.get(player.damageSources.size() - 1);
                                    }
                                    int numMsg;
                                    String message;
                                    switch (lastDamageSource) {
                                        case "lava":
                                            numMsg = (int) deathTypeMessageList.get("lava");
                                            message = (String) deathMsgNames.get("lava." + numMsg);
                                            logger.info("[Debug] Lava is the damage source");
                                            DeathMsg = message.replace("%player%", player.username);
                                            break;
                                        case "entity":
                                            numMsg = (int) deathTypeMessageList.get("entity");
                                            message = (String) deathMsgNames.get("entity." + numMsg);
                                            String DeathMsgtmp = message.replace("%player%", player.username);
                                            DeathMsg = DeathMsgtmp.replace("%entity%", player.lastDamagingEntity.toString());
                                            break;
                                        case "fall":
                                            numMsg = (int) deathTypeMessageList.get("fall");
                                            message = (String) deathMsgNames.get("fall." + numMsg);
                                            DeathMsg = message.replace("%player%", player.username);
                                            break;
                                        case "suffocate":
                                            numMsg = (int) deathTypeMessageList.get("suffocate");
                                            message = (String) deathMsgNames.get("suffocate." + numMsg);
                                            DeathMsg = message.replace("%player%", player.username);
                                            break;
                                        case "explosion":
                                            numMsg = (int) deathTypeMessageList.get("explosion");
                                            message = (String) deathMsgNames.get("explosion." + numMsg);
                                            DeathMsg = message.replace("%player%", player.username);
                                            break;
                                        case "fire":
                                            numMsg = (int) deathTypeMessageList.get("fire");
                                            message = (String) deathMsgNames.get("fire." + numMsg);
                                            DeathMsg = message.replace("%player%", player.username);
                                            break;
                                        case "void":
                                            numMsg = (int) deathTypeMessageList.get("void");
                                            message = (String) deathMsgNames.get("void." + numMsg);
                                            DeathMsg = message.replace("%player%", player.username);
                                            break;
                                        case "drown":
                                            numMsg = (int) deathTypeMessageList.get("drown");
                                            message = (String) deathMsgNames.get("drown." + numMsg);
                                            DeathMsg = message.replace("%player%", player.username);
                                            break;
                                    }
                                }
                                player.damageSources = new ArrayList<String>(); //reset the list
                                logger.info("Sending dmsg string" + DeathMsg);
                                //Server console
                                String truncDeathMsg = DeathMsg.replace(player.username, "");
                                logger.info("<" + player.username + "> **Died:** " + truncDeathMsg);
                                //Ingame
                                configManager.sendChatMessageToAllPlayers("§7" + DeathMsg);

                                player.HasRespawed = true;
                            }


                        } else {
                            player.IsDead = false;
                            player.HasRespawed = false;
                        }
                    }


                    //Original MC main code
                    long l2 = System.currentTimeMillis();
                    long l3 = l2 - l;
                    if (l3 > gameruleManager.getGamerule("cantkeepupwarn", 2000)) {
                        logger.warning("Can't keep up! Did the system time change, or is the server overloaded?");
                        l3 = 2000L;
                    }
                    if (l3 < 0L) {
                        logger.warning("Time ran backwards! Did the system time change?");
                        l3 = 0L;
                    }
                    l1 += l3;
                    l = l2;
                    while (l1 > 50L) {
                        l1 -= 50L;
                        func_6018_h();
                    }
                    Thread.sleep(1L);
                }
            } else {
                while (field_6025_n) {
                    commandLineParser();
                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException interruptedexception) {
                        interruptedexception.printStackTrace();
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.log(Level.SEVERE, "Unexpected exception", exception);
            while (field_6025_n) {
                commandLineParser();
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException interruptedexception1) {
                    interruptedexception1.printStackTrace();
                }
            }
        } finally {
            shutdown();
            serverRunning = true;
            System.exit(0);
        }
    }

    private void func_6018_h() {
        ArrayList arraylist = new ArrayList();
        for (Iterator iterator = field_6037_b.keySet().iterator(); iterator.hasNext(); ) {
            String s = (String) iterator.next();
            int k = ((Integer) field_6037_b.get(s)).intValue();
            if (k > 0) {
                field_6037_b.put(s, Integer.valueOf(k - 1));
            } else {
                arraylist.add(s);
            }
        }

        for (int i = 0; i < arraylist.size(); i++) {
            field_6037_b.remove(arraylist.get(i));
        }

        AxisAlignedBB.clearBoundingBoxPool();
        Vec3D.initialize();
        deathTime++;
        for(int j = 0; j < worldMngr.length; j++)
        {
            if(j != 0 && !propertyManagerObj.getBooleanProperty("allow-nether", true))
            {
                continue;
            }
            WorldServer worldserver = worldMngr[j];
            if(deathTime % 20 == 0)
            {
                configManager.sendPacketToAllPlayersInDimension(new Packet4UpdateTime(worldserver.worldTime), worldserver.worldProvider.worldType);
            }
            worldserver.tick();
            worldserver.func_459_b();
            while(worldserver.func_6156_d()) ;
//            worldserver.updateEntities();
        }

//        overworld.tick();
//        netherWorld.tick();
//        stitch.CallHook("OnServerTick", DummyList);
//        while (overworld.func_6156_d()) ;
//        overworld.func_459_b();
        field_6036_c.func_715_a();
        configManager.func_637_b();

        for(int k = 0; k < entityTracker.length; k++)
        {
            entityTracker[k].func_607_a();
        }
//        overworldEntityTracker.func_607_a();
        for (int j = 0; j < field_9010_p.size(); j++) {
            ((IUpdatePlayerListBox) field_9010_p.get(j)).update();
        }

        try {
            commandLineParser();
        } catch (Exception exception) {
            logger.log(Level.WARNING, "Unexpected exception while parsing console command", exception);
        }
    }

    public void addCommand(String s, ICommandListener icommandlistener) {
        commands.add(new ServerCommand(s, icommandlistener));
    }

    public void grantAdvancement(String username, String advancementID) {
        if (advancementManager.grantAdvancement(username, advancementID)) {
            logger.info("<" + username + "> **Got advancement: ** " + advancementCriterion.get(advancementID));
            configManager.sendChatMessageToAllPlayers(username + " has made the advancement §2[" + advancementCriterion.get(advancementID) + "]");
        }
    }

    public void commandLineParser() {
        do {
            if (commands.size() <= 0) {
                break;
            }
            ServerCommand servercommand = (ServerCommand) commands.remove(0);
            String command = servercommand.command;

            ICommandListener icommandlistener = servercommand.commandListener;
            String username = icommandlistener.getUsername();
            // Preprocess string with shorthands


            //Onwards

            //Help code -- ChessChicken
            if (command.toLowerCase().startsWith("help") || command.toLowerCase().startsWith("?")) {
                //10
                String[] getArgs = command.split(" ");
                if (getArgs.length == 2) {
                    if (getArgs[1].equalsIgnoreCase("1")) {
                        icommandlistener.log("§bConsole commands (page 1)");
                        icommandlistener.log("§a/help <id>  or  ? shows this message");
                        icommandlistener.log("§a/kick <player> §bremoves a player from the server");
                        icommandlistener.log("§a/ban <player> §bbans a player from the server");
                        icommandlistener.log("§a/pardon <player> §bpardons a banned player");
                        icommandlistener.log("§a/ban-ip <ip> §bbans an IP address");
                        icommandlistener.log("§a/pardon-ip <ip> §bpardons a banned IP address");
                        icommandlistener.log("§a/op <player> §bturns a player into an op");
                        icommandlistener.log("§a/deop <player> §bremoves op status from a player");
                        icommandlistener.log("§a/tp <player1> <player2> §bmoves player1 to player2");
                        icommandlistener.log("§a/tpcord <player1><x><y><z> §bmoves player to the coords");
                        return;
                    }
                    if (getArgs[1].equalsIgnoreCase("2")) {
                        icommandlistener.log("§bConsole commands (page 2)");
                        icommandlistener.log("§a/give <player><name string>[num] §bgives a player a resource");
                        icommandlistener.log("§a/giveid <player><id>[num] §bgives a player a resource taking numeric ID");
                        icommandlistener.log("§a/tell <player><message> §bsends a private message to a player");
                        icommandlistener.log("§a/stop §bgracefully stops the server");
                        icommandlistener.log("§a/save-all §bforces a server-wide level save");
                        icommandlistener.log("§a/save-off §bdisables terrain saving (useful for backup scripts)");
                        icommandlistener.log("§a/save-on §bre-enables terrain saving");
                        icommandlistener.log("§a/list §blists all currently connected players");
                        icommandlistener.log("§a/time set <day/night/number of ticks> §bchanges time");
                        icommandlistener.log("§a/say <message> §bbroadcasts a message to all players");
                        return;
                    }

                    icommandlistener.log("§bTo run the server without a gui, start it like this:");
                    icommandlistener.log("§bjava -Xmx1024M -Xms1024M -jar minecraft_server.jar nogui");
                } else {
                    icommandlistener.log("§cError in command arguments, try §a/help 1");
                }
            } else if (command.toLowerCase().startsWith("seed")) {
                WorldGenParams params = new WorldGenParams();
                icommandlistener.log("Seed for this world is:" + params.GetSeedFromPropertiesFile());
            } else if (command.toLowerCase().startsWith("create ")) {
                String[] args = command.split(" ");
                switch (args[1]) {
                    // TODO: Reimplement.
//                    case "player":
//                        PlayerNBTManager playerNBTManagerObj = new PlayerNBTManager(new File("_create_player_cmd"));
////                        PlayerManager playerManagerObj = new PlayerManager(this, configManager.getPlayerEntity(username).dimension);
//                        EntityPlayerMP entityplayermp = new EntityPlayerMP(this, overworld, args[2], new ItemInWorldManager(overworld));
//                        configManager.playerEntities.add(entityplayermp);
//                        playerNBTManagerObj.readPlayerData(entityplayermp);
//                        overworld.chunkProvider.loadChunk((int) entityplayermp.posX >> 4, (int) entityplayermp.posZ >> 4);
//                        for (; overworld.getCollidingBoundingBoxes(entityplayermp, entityplayermp.boundingBox).size() != 0; entityplayermp.setPosition(entityplayermp.posX, entityplayermp.posY + 1.0D, entityplayermp.posZ)) {
//                        }
//                        overworld.entityJoinedWorld(entityplayermp);
//                        configManager.playerEntities.add(entityplayermp);
//                        playerManagerObj.addPlayer(entityplayermp);
//                        break;
//                    case "zombie":
//                        EntityZombie zombie = new EntityZombie(overworld);
//                        overworld.entityJoinedWorld(zombie);

                }
            } else if (command.toLowerCase().startsWith("whitelist ") && gameruleManager.getGamerule("whitelist", false)) {
                String[] args = command.toLowerCase().split(" ");
                if (args[1] == "add") {
                    configManager.whitelistPlayer(args[2]);
                }
                if (args[1] == "remove") {
                    configManager.deWhitelistPlayer(args[2]);
                }
                if (args[1] == "on") {
                    gameruleManager.setGamerule("usewhitelist", true);
                }
                if (args[1] == "off") {
                    gameruleManager.setGamerule("usewhitelist", false);
                }
            }
            if (command.toLowerCase().startsWith("grantadvancement ")) {
                String[] args = command.split(" ");
                grantAdvancement(username, args[1]);
            }
            if (command.toLowerCase().startsWith("grantadvancementall ")) {
                String[] args = command.split(" ");

                for (Object player : configManager.playerEntities) {
                    EntityPlayer playerEntity = (EntityPlayerMP) player;
                    grantAdvancement(playerEntity.username, args[1]);
                }
            }

            if (command.toLowerCase().startsWith("stitchcall")) {
                String[] args = command.split(" ");
                ArrayList<Object> hookArgs = new ArrayList<>();
                int i;
                for (i = 0; i < args.length; i++) {
                    if (i >= 2) {
                        hookArgs.add(args[i]);
                    }
                }

                stitch.CallHook(args[1], hookArgs);
            }
            if (command.toLowerCase().startsWith("nether") && gameruleManager.getGamerule("nether", true)) {
                configManager.sendPlayerToOtherDimension(configManager.getPlayerEntity(username));
            }
            if (command.toLowerCase().startsWith("sky")) {
                configManager.sendPlayerToSkyDimension(configManager.getPlayerEntity(username));
            }
            if (command.toLowerCase().startsWith("version")) {
                //WorldGenParams params = new WorldGenParams();
                icommandlistener.log(VERSION_STRING + " Branch: " + TARGET_FEATURE);
            }
            if (command.toLowerCase().startsWith("debugstat")) {
                icommandlistener.log("PlayerEntities: " + configManager.playerEntities + " player list:" + configManager.getPlayerList());
            }
            if (command.toLowerCase().startsWith("heal")) {
                EntityPlayer player = configManager.getPlayerEntity(username);
                player.heal(20);
            }
            if (command.toLowerCase().startsWith("kill")) {
                //   if(s.toLowerCase().startsWith("heal")){
                EntityPlayer player = configManager.getPlayerEntity(username);
                player.damageSources.add("entity");
                player.lastDamagingEntity = configManager.getPlayerEntity(username);
                player.exposedTakeDamage(40);

            }
            if (command.toLowerCase().startsWith("killall")) {
                //   if(s.toLowerCase().startsWith("heal")){
                EntityPlayer player = configManager.getPlayerEntity(username);
                for (int i = 0; i < getWorldManager(player.dimension).EntityList.size(); i++) {
                    Entity entity = (Entity) getWorldManager(player.dimension).EntityList.get(i);
                    if (entity != null) {
                        entity.setEntityDead();
                    } //Just in case  something wacky  happens
                }

            }
            if (command.toLowerCase().startsWith("clear")) {
                //   if(s.toLowerCase().startsWith("heal")){
                EntityPlayer player = configManager.getPlayerEntity(username);
                player.inventory.dropAllItems();

            }
            if (command.toLowerCase().startsWith("keepinvadd ")) {
                String commandparts[] = command.split(" ");
                String keepinvlist = gameruleManager.getGamerule("keepinvlist", "");
                keepinvlist += "|"; //Pipe is seperator. Actual string would look something like Redbunny1|Redbunny2
                keepinvlist += commandparts[1];
                gameruleManager.setGamerule("keepinvlist", keepinvlist);
            }
            if (command.toLowerCase().startsWith("gamerule ")) {
                //  GameruleManager gameruleManager = new GameruleManager(new File("server.gamerules"));
                String commandparts[] = command.toLowerCase().split(" ");

                String prettyValue = commandparts[2];
                switch (commandparts[2]) {
                    case "true":
                        prettyValue = "§a[true]";
                        break;
                    case "false":
                        prettyValue = "§c[false]";
                        break;
                    default:
                        prettyValue = ("§2[" + commandparts[2] + "]");
                }

                configManager.sendChatMessageToPlayer(username, "§7Changing gamerule §2[" + commandparts[1] + "]§7 to " + prettyValue);
                switch (commandparts[1]) {
                    case "announcedeath":
                        if (commandparts[2].equals("true")) {
                            gameruleManager.setGamerule("announcedeath", true);
                        } else if (commandparts[2].equals("false")) {
                            gameruleManager.setGamerule("announcedeath", false);
                        }
                        break;
                    case "inversemobspawnrate":
                        if (commandparts[2].equals("reset")) {
                            gameruleManager.setGamerule("inversemobspawnrate", 50);
                        } else {
                            int newVal = Integer.parseInt(commandparts[2]);
                            gameruleManager.setGamerule("inversemobspawnrate", newVal);
                        }
                        break;
                    case "domobgriefing":
                        if (commandparts[2].equals("true")) {
                            gameruleManager.setGamerule("domobgriefing", true);
                        } else if (commandparts[2] == "false") {
                            gameruleManager.setGamerule("domobgriefing", false);
                        }
                        break;
                    case "dosleepvote":
                        if (commandparts[2].equals("true")) {
                            gameruleManager.setGamerule("dosleepvote", true);
                        } else if (commandparts[2].equals("false")) {
                            gameruleManager.setGamerule("dosleepvote", false);
                        }
                        break;
                    case "inverseskeletonjockeyspawnrate":
                        if (commandparts[2].equals("reset")) {
                            gameruleManager.setGamerule("inverseskeletonjockeyspawnrate", 50);
                        } else {
                            int newVal = Integer.parseInt(commandparts[2]);
                            gameruleManager.setGamerule("inverseskeletonjockeyspawnrate", newVal);
                        }
                        break;
                    case "domoderntrample":
                        if (commandparts[2].equals("true")) {
                            gameruleManager.setGamerule("domoderntrample", true);
                        } else if (commandparts[2].equals("false")) {
                            gameruleManager.setGamerule("domoderntrample", false);
                        }
                        break;
                    case "usewhitelist":
                        if (commandparts[2].equals("true")) {
                            gameruleManager.setGamerule("usewhitelist", true);
                        } else if (commandparts[2].equals("false")) {
                            gameruleManager.setGamerule("usewhitelist", false);
                        }
                        break;
                    case "freezetime":
                        if (commandparts[2].equals("true")) {
                            gameruleManager.setGamerule("freezetime", true);
                        } else if (commandparts[2].equals("false")) {
                            gameruleManager.setGamerule("freezetime", false);
                        }
                        break;
                    case "snowworld":
                        if (commandparts[2].equals("true")) {
                            gameruleManager.setGamerule("snowworld", true);
                        } else if (commandparts[2].equals("false")) {
                            gameruleManager.setGamerule("snowworld", false);
                        }
                        break;
                    case "nobigtrees":
                        if (commandparts[2].equals("true")) {
                            gameruleManager.setGamerule("nobigtree", true);
                        } else if (commandparts[2].equals("false")) {
                            gameruleManager.setGamerule("nobigtree", false);
                        }
                        break;
                    case "dopumpkinlights":
                        if (commandparts[2].equals("true")) {
                            gameruleManager.setGamerule("dopumpkinlights", true);
                        } else if (commandparts[2].equals("false")) {
                            gameruleManager.setGamerule("dopumpkinlights", false);
                        }
                        break;
                    case "seedrate":
                        if (commandparts[2].equals("reset")) {
                            gameruleManager.setGamerule("seedrate", 15);
                        } else {
                            int newVal = Integer.parseInt(commandparts[2]);
                            gameruleManager.setGamerule("seedrate", newVal);
                        }
                        break;
                }
            }
            if (command.toLowerCase().startsWith("time set ")) {
                String targetTime = command.toLowerCase().split(" ")[2];
                configManager.sendChatMessageToPlayer(username, "§7Setting time to §a[" + targetTime + "]");
                if (targetTime.equals("day")) {
                    worldMngr[0].worldTime = 1000;
                } else if (targetTime.equals("night")) {
                    worldMngr[0].worldTime = 13000;
                } else {
                    int time = Integer.parseInt(targetTime);
                    worldMngr[0].worldTime = time;
                }
            }

            if (command.toLowerCase().startsWith("sethome")) {
                String[] commandspart = command.split(" ");
                String locationName = "home";

                if (!commandspart[commandspart.length - 1].equals("sethome")) {
                    locationName = commandspart[commandspart.length - 1];
                }

                PlayerDataManager pdm = PlayerDataManager.getInstance();
                PlayerData pdata = new PlayerData();
                EntityPlayerMP entityplayer = configManager.getPlayerEntity(username);
                if (pdm.getPlayerData(username) != null) {
                    pdata = pdm.getPlayerData(username);
                }
                ArrayList<Location> plocs = pdata.getLocations();
                if (pdata.getLocation(locationName, plocs) == null) {
                    Location newHome = new Location();
                    newHome.setName(locationName);
                    newHome.setLookVector(entityplayer.rotationYaw, entityplayer.rotationPitch);
                    newHome.setLocationVector(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
                    pdata.addLocation(newHome);
                    pdm.setPlayerData(username, pdata);
                    configManager.sendChatMessageToPlayer(username, "§7Home §a[" + locationName + "]§7 set!");
                } else {
                    Location oldLocation = pdata.getLocation(locationName, plocs);
                    oldLocation.setLookVector(entityplayer.rotationYaw, entityplayer.rotationPitch);
                    oldLocation.setLocationVector(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
                    pdm.setPlayerData(username, pdata);
                    configManager.sendChatMessageToPlayer(username, "§7Home §a[" + locationName + "]§7 changed!");
                }
            }

            if (command.toLowerCase().startsWith("home")) {
                String[] commandspart = command.split(" ");
                String locationName = "home";

                if (!commandspart[commandspart.length - 1].equals("home")) {
                    locationName = commandspart[commandspart.length - 1];
                }

                PlayerDataManager pdm = PlayerDataManager.getInstance();
                PlayerData pdata = new PlayerData();
                EntityPlayerMP entityplayer = configManager.getPlayerEntity(username);
                if (pdm.getPlayerData(username) != null) {
                    pdata = pdm.getPlayerData(username);
                }
                ArrayList<Location> plocs = pdata.getLocations();
                if (pdata.getLocation(locationName, plocs) != null) {
                    Location target = pdata.getLocation(locationName, plocs);
                    Vector3d locvec = target.getLocationVector();
                    Vector3f lookvec = target.getLookVector();
                    String name = target.getName();
                    entityplayer.playerNetServerHandler.teleportTo(locvec.getX(), locvec.getY(), locvec.getZ(), lookvec.getYaw(), lookvec.getPitch());
                    //System.out.println(locvec.get(0) + locvec.get(1) + locvec.get(2) + lookvec.get(0) + lookvec.get(1));

                    configManager.sendChatMessageToPlayer(username, "§7Returning to §a[" + name + "]");
                } else {
                    configManager.sendChatMessageToPlayer(username, "§7Home location §a[" + locationName + "]§7 doesn't exist!");
                }
            }

            if (command.toLowerCase().startsWith("back")) {
                PlayerDataManager pdm = PlayerDataManager.getInstance();
                PlayerData pdata = pdm.getPlayerData(username);
                if (pdata.getBackUsages() < GameruleManager.getInstance().getGamerule("maxbacks", 1)) {
                    EntityPlayerMP entityplayer = configManager.getPlayerEntity(username);
                    if (pdm.getPlayerData(username) != null) {
                        pdata = pdm.getPlayerData(username);
                    }
                    if (pdata.getLastDeathLocation() != null) {
                        Location target = pdata.getLastDeathLocation();
                        Vector3d locvec = target.getLocationVector();
                        Vector3f lookvec = target.getLookVector();
                        entityplayer.playerNetServerHandler.teleportTo(locvec.getX(), locvec.getY(), locvec.getZ(), lookvec.getYaw(), lookvec.getPitch());
                        pdata.addBackUsage();
                        pdm.setPlayerData(username, pdata);
                        configManager.sendChatMessageToPlayer(username, "§7Returning to the last place you died...");
                    } else {
                        configManager.sendChatMessageToPlayer(username, "§7Something broke!");
                    }
                } else {
                    configManager.sendChatMessageToPlayer(username, "§7Sorry! You can only do that " + GameruleManager.getInstance().getGamerule("maxbacks", 1) + " times per life on this server..");
                }
            }

            if (command.toLowerCase().startsWith("delhome")) {
                PlayerDataManager pdm = PlayerDataManager.getInstance();
                PlayerData pd = pdm.getPlayerData(username);
                if (command.toLowerCase().startsWith("delhomes")) {
                    pd.clearLocations();
                    configManager.sendChatMessageToPlayer(username, "§7Cleared all saved homes!");
                } else {
                    String[] commandspart = command.split(" ");
                    String badHome = commandspart[commandspart.length - 1];
                    ArrayList<Location> plocs = pd.getLocations();
                    if (pd.getLocation(badHome, plocs) != null) {
                        Location badLocation = pd.getLocation(badHome, plocs);
                        pd.removeLocation(badLocation);
                        configManager.sendChatMessageToPlayer(username, "§7Removed home §a[" + badHome + "]!");
                    } else {
                        configManager.sendChatMessageToPlayer(username, "§7Could not find home §a[" + badHome + "]!");
                    }
                }
                pdm.setPlayerData(username, pd);
            }


            if (command.toLowerCase().startsWith("list")) {
                icommandlistener.log((new StringBuilder()).append("Connected players: ").append(configManager.getPlayerList()).toString());
            } else if (command.toLowerCase().startsWith("stop")) {
                func_6014_a(username, "Stopping the server..");
                field_6025_n = false;
            } else if (command.toLowerCase().startsWith("save-all")) {
                func_6014_a(username, "Forcing save..");
                for(int i = 0; i < worldMngr.length; i++)
                {
                    WorldServer worldserver = worldMngr[i];
                    worldserver.saveWorld(true, null);
                }
                func_6014_a(username, "Save complete.");
            } else if (command.toLowerCase().startsWith("save-off")) {
                func_6014_a(username, "Disabling level saving..");
                for(int i = 0; i < worldMngr.length; i++) {
                    worldMngr[i].field_816_A = true;
                }
            } else if (command.toLowerCase().startsWith("save-on")) {
                func_6014_a(username, "Enabling level saving..");
                for(int i = 0; i < worldMngr.length; i++) {
                    worldMngr[i].field_816_A = false;
                }
            } else if (command.toLowerCase().startsWith("op ")) {
                String s2 = command.substring(command.indexOf(" ")).trim();
                configManager.opPlayer(s2);
                func_6014_a(username, (new StringBuilder()).append("Opping ").append(s2).toString());
                configManager.sendChatMessageToPlayer(s2, "\247eYou are now op!");
            } else if (command.toLowerCase().startsWith("deop ")) {
                String s3 = command.substring(command.indexOf(" ")).trim();
                configManager.deopPlayer(s3);
                configManager.sendChatMessageToPlayer(s3, "\247eYou are no longer op!");
                func_6014_a(username, (new StringBuilder()).append("De-opping ").append(s3).toString());
            } else if (command.toLowerCase().startsWith("ban-ip ")) {
                String s4 = command.substring(command.indexOf(" ")).trim();
                configManager.banIP(s4);
                func_6014_a(username, (new StringBuilder()).append("Banning ip ").append(s4).toString());
            } else if (command.toLowerCase().startsWith("pardon-ip ")) {
                String s5 = command.substring(command.indexOf(" ")).trim();
                configManager.unbanIP(s5);
                func_6014_a(username, (new StringBuilder()).append("Pardoning ip ").append(s5).toString());
            } else if (command.toLowerCase().startsWith("ban ")) {
                String s6 = command.substring(command.indexOf(" ")).trim();
                configManager.banPlayer(s6);
                func_6014_a(username, (new StringBuilder()).append("Banning ").append(s6).toString());
                EntityPlayerMP entityplayermp = configManager.getPlayerEntity(s6);
                if (entityplayermp != null) {
                    entityplayermp.playerNetServerHandler.func_43_c("Banned by admin");
                }
            } else if (command.toLowerCase().startsWith("pardon ")) {
                String s7 = command.substring(command.indexOf(" ")).trim();
                configManager.unbanPlayer(s7);
                func_6014_a(username, (new StringBuilder()).append("Pardoning ").append(s7).toString());
            } else if (command.toLowerCase().startsWith("kick ")) {
                String s8 = command.substring(command.indexOf(" ")).trim();
                EntityPlayerMP entityplayermp1 = null;
                for (int i = 0; i < configManager.playerEntities.size(); i++) {
                    EntityPlayerMP entityplayermp5 = (EntityPlayerMP) configManager.playerEntities.get(i);
                    if (entityplayermp5.username.equalsIgnoreCase(s8)) {
                        entityplayermp1 = entityplayermp5;
                    }
                }

                if (entityplayermp1 != null) {
                    entityplayermp1.playerNetServerHandler.func_43_c("Kicked by admin");
                    func_6014_a(username, (new StringBuilder()).append("Kicking ").append(entityplayermp1.username).toString());
                } else {
                    icommandlistener.log((new StringBuilder()).append("Can't find user ").append(s8).append(". No kick.").toString());
                }
            } else if (command.toLowerCase().startsWith("tp ")) {
                String as[] = command.split(" ");
                if (as.length == 3) {
                    EntityPlayerMP entityplayermp2 = configManager.getPlayerEntity(as[1]);
                    EntityPlayerMP entityplayermp3 = configManager.getPlayerEntity(as[2]);
                    if (entityplayermp2 == null) {
                        icommandlistener.log((new StringBuilder()).append("Can't find user ").append(as[1]).append(". No tp.").toString());
                    } else if (entityplayermp3 == null) {
                        icommandlistener.log((new StringBuilder()).append("Can't find user ").append(as[2]).append(". No tp.").toString());
                    } else if(entityplayermp2.dimension != entityplayermp3.dimension)
                    {
                        icommandlistener.log((new StringBuilder()).append("User ").append(as[1]).append(" and ").append(as[2]).append(" are in different dimensions. No tp.").toString());
                    } else {
                        entityplayermp2.playerNetServerHandler.teleportTo(entityplayermp3.posX, entityplayermp3.posY, entityplayermp3.posZ, entityplayermp3.rotationYaw, entityplayermp3.rotationPitch);
                        func_6014_a(username, (new StringBuilder()).append("Teleporting ").append(as[1]).append(" to ").append(as[2]).append(".").toString());
                    }
                } else {
                    icommandlistener.log("Syntax error, please provide a source and a target.");
                }
            } else if (command.toLowerCase().startsWith("tpcord ")) {
                String as[] = command.split(" ");
                if (as.length == 5) {
                    EntityPlayerMP entityplayermp2 = configManager.getPlayerEntity(as[1]);
                    int x = Integer.parseInt(as[2]);
                    int y = Integer.parseInt(as[3]);
                    int z = Integer.parseInt(as[4]);

                    //  EntityPlayerMP entityplayermp3 = configManager.getPlayerEntity(as[2]);
                    if (entityplayermp2 == null) {
                        icommandlistener.log((new StringBuilder()).append("Can't find user ").append(as[1]).append(". No tp.").toString());
                    } else {
                        entityplayermp2.playerNetServerHandler.teleportTo(x, y, z, entityplayermp2.rotationYaw, entityplayermp2.rotationPitch);
                        func_6014_a(username, (new StringBuilder()).append("Teleporting ").append(as[1]).append(" to ").append(as[2]).append(".").toString());
                    }
                } else {
                    icommandlistener.log("Syntax error, please provice a source and 3 numbers for cordinates");
                }
            } else if (command.toLowerCase().startsWith("give ")) {
                String as1[] = command.split(" ");
                if (as1.length != 3 && as1.length != 4) {
                    return;
                }
                String s9 = as1[1];
                EntityPlayerMP entityplayermp4 = configManager.getPlayerEntity(s9);
                if (entityplayermp4 != null) {
                    try {
                        //  NameIDMappings nameIDMappings = new NameIDMappings();

                        BlockMappingsManager BlockMappings = new BlockMappingsManager(new File("blocks.mappings"));
                        FallbackIdMaps fallbackIdMaps = new FallbackIdMaps();
                        int j = BlockMappings.getIdForString(as1[2], fallbackIdMaps.GetIDForNamespacedBlockName(as1[2])); // This is literally the only change between give and giveID
                        // logger.info("Inputed name:"+as1[2]);
                        // logger.info("Processed ID:"+j);
                        if (Item.itemsList[j] != null) {
                            func_6014_a(username, (new StringBuilder()).append("Giving ").append(entityplayermp4.username).append(" " + as1[3] + " ").append(as1[2]).toString());
                            int k = 1;
                            if (as1.length > 3) {
                                k = func_6020_b(as1[3], 1);
                            }
                            if (k < 1) {
                                k = 1;
                            }
                            if (k > 64) {
                                k = 64;
                            }
                            entityplayermp4.func_161_a(new ItemStack(j, k));
                        } else {
                            icommandlistener.log((new StringBuilder()).append("There's no item with name ").append(j).toString());
                        }
                    } catch (NumberFormatException numberformatexception) {
                        icommandlistener.log((new StringBuilder()).append("There's no item with name ").append(as1[2]).toString());
                    }
                } else {
                    icommandlistener.log((new StringBuilder()).append("Can't find user ").append(s9).toString());
                }
            } else if (command.toLowerCase().startsWith("giveid ")) {
                String as1[] = command.split(" ");
                if (as1.length != 3 && as1.length != 4) {
                    return;
                }
                String s9 = as1[1];
                EntityPlayerMP entityplayermp4 = configManager.getPlayerEntity(s9);
                if (entityplayermp4 != null) {
                    try {
                        int j = Integer.parseInt(as1[2]);
                        if (Item.itemsList[j] != null) {
                            func_6014_a(username, (new StringBuilder()).append("Giving ").append(entityplayermp4.username).append(" some ").append(j).toString());
                            int k = 1;
                            if (as1.length > 3) {
                                k = func_6020_b(as1[3], 1);
                            }
                            if (k < 1) {
                                k = 1;
                            }
                            if (k > 64) {
                                k = 64;
                            }
                            entityplayermp4.func_161_a(new ItemStack(j, k));
                        } else {
                            icommandlistener.log((new StringBuilder()).append("There's no item with id ").append(j).toString());
                        }
                    } catch (NumberFormatException numberformatexception) {
                        icommandlistener.log((new StringBuilder()).append("There's no item with id ").append(as1[2]).toString());
                    }
                } else {
                    icommandlistener.log((new StringBuilder()).append("Can't find user ").append(s9).toString());
                }
            } else if (command.toLowerCase().startsWith("say ")) {
                command = command.substring(command.indexOf(" ")).trim();
                logger.info((new StringBuilder()).append("[").append(username).append("] ").append(command).toString());
                configManager.sendPacketToAllPlayers(new Packet3Chat((new StringBuilder()).append(command).toString()));
            } else if (command.toLowerCase().startsWith("tell ")) {
                String as2[] = command.split(" ");
                if (as2.length >= 3) {
                    command = command.substring(command.indexOf(" ")).trim();
                    command = command.substring(command.indexOf(" ")).trim();
                    logger.info((new StringBuilder()).append("[").append(username).append("->").append(as2[1]).append("] ").append(command).toString());
                    command = (new StringBuilder()).append("\2477").append(username).append(" whispers ").append(command).toString();
                    logger.info(command);
                    if (!configManager.sendPacketToPlayer(as2[1], new Packet3Chat(command))) {
                        icommandlistener.log("There's no player by that name online.");
                    }
                }
            } else {
                logger.info("Unknown console command. Type \"help\" for help.");
            }
        } while (true);
    }

    private void func_6014_a(String s, String s1) {
        String s2 = (new StringBuilder()).append(s).append(": ").append(s1).toString();
        configManager.sendChatMessageToAllPlayers((new StringBuilder()).append("\2477(").append(s2).append(")").toString());
        logger.info(s2);
    }

    private int func_6020_b(String s, int i) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException numberformatexception) {
            return i;
        }
    }

    public void func_6022_a(IUpdatePlayerListBox iupdateplayerlistbox) {
        field_9010_p.add(iupdateplayerlistbox);
    }

    public File getFile(String s) {
        return new File(s);
    }

    public void log(String s) {
        logger.info(s);
    }

    public String getUsername() {
        return "CONSOLE";
    }
}
