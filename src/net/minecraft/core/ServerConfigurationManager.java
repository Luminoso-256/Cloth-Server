package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.MinecraftServer;
import net.minecraft.cloth.ExploitUtils;
import net.minecraft.cloth.file.GameruleManager;
import net.minecraft.cloth.file.PlayerDataManager;
import net.minecraft.cloth.nether.Teleporter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

public class ServerConfigurationManager {

    public static Logger logger = Logger.getLogger("Minecraft");
    public List playerEntities;
    private PlayerManager playerManagerObj[];
    private MinecraftServer mcServer;
    private int maxPlayers;
    private Set banList;
    private Set bannedIPs;
    private Set ops;
    private Set whitelist;
    private File bannedPlayersFile;
    private File ipBanFile;
    private File opFile;
    private File whitelistFile;
    private PlayerNBTManager playerNBTManagerObj;
    private GameruleManager gameruleManager = GameruleManager.getInstance();
    private static Random random = new Random();

    public ServerConfigurationManager(MinecraftServer minecraftserver) {
        playerEntities = new ArrayList();
        banList = new HashSet();
        bannedIPs = new HashSet();
        ops = new HashSet();
        mcServer = minecraftserver;
        bannedPlayersFile = minecraftserver.getFile("banned-players.txt");
        ipBanFile = minecraftserver.getFile("banned-ips.txt");
        opFile = minecraftserver.getFile("ops.txt");
        whitelistFile = minecraftserver.getFile("whitelist.txt");
        int viewDistance = minecraftserver.propertyManagerObj.getIntProperty("view-distance", 10);
        playerManagerObj = new PlayerManager[3];
        playerManagerObj[0] = new PlayerManager(minecraftserver, 0, viewDistance);
        playerManagerObj[1] = new PlayerManager(minecraftserver, -1, viewDistance);
        playerManagerObj[2] = new PlayerManager(minecraftserver, 1, viewDistance);
        maxPlayers = minecraftserver.propertyManagerObj.getIntProperty("max-players", 20);
        readBannedPlayers();
        loadBannedList();
        loadOps();
        writeBannedPlayers();
        saveBannedList();
        saveOps();
    }

    public void readPlayerDataFromFile(EntityPlayerMP entityplayermp)
    {
        playerNBTManagerObj.readPlayerData(entityplayermp);
    }

    public void setPlayerManager(WorldServer worldserver[]) {
        playerNBTManagerObj = new PlayerNBTManager(new File(worldserver[0].field_797_s, "players"));
    }

    public int func_640_a() {
        return playerManagerObj[0].func_542_b();
    }

    public void playerLoggedIn(EntityPlayerMP entityplayermp) {


        //Connect player to world
        playerEntities.add(entityplayermp);
        playerNBTManagerObj.readPlayerData(entityplayermp);
        WorldServer worldServer = mcServer.getWorldManager(entityplayermp.dimension);
        worldServer.chunkProvider.loadChunk((int) entityplayermp.posX >> 4, (int) entityplayermp.posZ >> 4);
        for (; worldServer.getCollidingBoundingBoxes(entityplayermp, entityplayermp.boundingBox).size() != 0; entityplayermp.setPosition(entityplayermp.posX, entityplayermp.posY + 1.0D, entityplayermp.posZ)) {
        }
        worldServer.entityJoinedWorld(entityplayermp);
        getPlayerManager(entityplayermp.dimension).addPlayer(entityplayermp);

        //Check their invenotry for illageal items.
        String itemIDBlacklist = gameruleManager.getGamerule("itemidblacklist", " ");
        boolean IsInvClean = true;
        int MainInvSize = 36;
        int CraftInvSize = 4;
        int ArmorInvSize = 4;
        ItemStack fallbackItem = new ItemStack(1);
        for (int i = 0; i < entityplayermp.inventory.getInventorySize(); i++) {
            ItemStack item = entityplayermp.inventory.getStackInSlot(i);
            // System.out.println("Processing item: "+item.itemID+" in slot "+i);
            if (item != null) {
                System.out.println("Slot contains item of ID: " + item.itemID);
                //Whitelist
                if (ExploitUtils.IsIdValid(item.itemID)) {

                    //Whatever makes it through whitelist check gets processed by Blacklist Check
                    if (itemIDBlacklist.contains(Integer.toString(item.itemID))) {

                        entityplayermp.inventory.setInventorySlotContents(i, fallbackItem);
                    }
                } else {
                    entityplayermp.inventory.setInventorySlotContents(i, fallbackItem);
                }
            } else {
//                System.out.println("Slot " + i + " contained null or nonextistant item");
            }
        }
        //MOTD
        File tempFile = new File("motd.txt");
        boolean exists = tempFile.exists();
        String MOTD = null;
        List<String> PossibleMOTDS = null;
        Random random = new Random();
        if (exists) {
            try {
                PossibleMOTDS = Files.readAllLines(Paths.get(tempFile.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (String log : PossibleMOTDS) {
                logger.info(log);
            }
            MOTD = PossibleMOTDS.get(random.nextInt(PossibleMOTDS.size()));
        } else {
            PropertyManager propertyManager = new PropertyManager(new File("server.properties"));
            MOTD = propertyManager.getStringProperty("motd", "Welcome %player% to %world% ! ");

        }
        String pnameReplace = MOTD.replace("%player%", entityplayermp.username);
        String finalMOTD = pnameReplace.replace("%world%", mcServer.worldName);
        sendChatMessageToPlayer(entityplayermp.username, finalMOTD);
    }

    public void func_613_b(EntityPlayerMP entityplayermp) {
        getPlayerManager(entityplayermp.dimension).func_543_c(entityplayermp);
    }

    public void playerLoggedOut(EntityPlayerMP entityplayermp) {
        WorldServer worldServer = mcServer.getWorldManager(entityplayermp.dimension);

        playerNBTManagerObj.writePlayerData(entityplayermp);
        worldServer.func_12016_d(entityplayermp);
        playerEntities.remove(entityplayermp);
        getPlayerManager(entityplayermp.dimension).removePlayer(entityplayermp);
    }

    public EntityPlayerMP login(NetLoginHandler netloginhandler, String username, String s1) {
        PlayerDataManager playerDataManager = PlayerDataManager.getInstance();
        //  PlayerData pd = playerDataManager.getPlayerData(username);
        if (banList.contains(username.trim().toLowerCase())) {
            netloginhandler.kickUser("You are banned from this server!");
            return null;
        }
        if (gameruleManager.getGamerule("usewhitelist", false) && !whitelist.contains(username.trim().toLowerCase())) {
            //You arent whitelisted!
            netloginhandler.kickUser("You arent whitelisted on this server!");
            return null;
        }
        String s2 = netloginhandler.netManager.getRemoteAddress().toString();
        s2 = s2.substring(s2.indexOf("/") + 1);
        s2 = s2.substring(0, s2.indexOf(":"));
        if (bannedIPs.contains(s2)) {
            netloginhandler.kickUser("Your IP address is banned from this server!");
            return null;
        }
        if (playerEntities.size() >= maxPlayers) {
            netloginhandler.kickUser("The server is full!");
            return null;
        }
        for (int i = 0; i < playerEntities.size(); i++) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) playerEntities.get(i);
            if (entityplayermp.username.equalsIgnoreCase(username)) {
                entityplayermp.playerNetServerHandler.func_43_c("You logged in from another location");
            }
        }

        WorldServer worldServer = mcServer.getWorldManager(0);

        return new EntityPlayerMP(mcServer, worldServer, username, new ItemInWorldManager(worldServer));
    }

    public EntityPlayerMP recreatePlayerEntity(EntityPlayerMP entityplayermp) {
        if(entityplayermp.dimension != 0) {
            WorldServer worldserver = mcServer.getWorldManager(entityplayermp.dimension);
            EntityTracker entityTracker = mcServer.getEntityTracker(entityplayermp.dimension);
            logger.info((new StringBuilder("Sending ")).append(entityplayermp.username).append(0).toString());
            entityTracker.func_9238_a(entityplayermp);
            entityTracker.func_610_b(entityplayermp);
            getPlayerManager(entityplayermp.dimension).removePlayer(entityplayermp);
            playerEntities.remove(entityplayermp);
            worldserver.removePlayer(entityplayermp);

            EntityPlayerMP entityplayermp1 = new EntityPlayerMP(mcServer, mcServer.worldMngr[0], entityplayermp.username, new ItemInWorldManager(mcServer.worldMngr[0]));
            entityplayermp1.dimension = 0;
            entityplayermp1.field_331_c = entityplayermp.field_331_c;
            entityplayermp1.playerNetServerHandler = entityplayermp.playerNetServerHandler;
            mcServer.worldMngr[0].chunkProvider.loadChunk((int) entityplayermp1.posX >> 4, (int) entityplayermp1.posZ >> 4);
            for (; mcServer.worldMngr[0].getCollidingBoundingBoxes(entityplayermp1, entityplayermp1.boundingBox).size() != 0; entityplayermp1.setPosition(entityplayermp1.posX, entityplayermp1.posY + 1.0D, entityplayermp1.posZ)) {
            }
            entityplayermp1.playerNetServerHandler.sendPacket(new Packet9Respawn());
            entityplayermp1.playerNetServerHandler.teleportTo(entityplayermp1.posX, entityplayermp1.posY, entityplayermp1.posZ, entityplayermp1.rotationYaw, entityplayermp1.rotationPitch);
            playerManagerObj[0].addPlayer(entityplayermp1);
            mcServer.worldMngr[0].entityJoinedWorld(entityplayermp1);
            playerEntities.add(entityplayermp1);

//            entityplayermp.isDead = false;
//            double d = entityplayermp.posX;
//            double d1 = entityplayermp.posZ;
//            double d2 = 8D;
//            if(entityplayermp.dimension == -1)
//            {
//                d /= d2;
//                d1 /= d2;
//                entityplayermp.setLocationAndAngles(d, entityplayermp.posY, d1, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
//                if(entityplayermp.isEntityAlive())
//                {
//                    worldserver.updateEntityWithOptionalForce(entityplayermp, false);
//                }
//            } else
//            {
//                d *= d2;
//                d1 *= d2;
//                entityplayermp.setLocationAndAngles(d, entityplayermp.posY, d1, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
//                if(entityplayermp.isEntityAlive())
//                {
//                    worldserver.updateEntityWithOptionalForce(entityplayermp, false);
//                }
//            }
            if(entityplayermp.isEntityAlive())
            {
                mcServer.worldMngr[0].entityJoinedWorld(entityplayermp);
                entityplayermp.setLocationAndAngles(entityplayermp1.posX, entityplayermp1.posY, entityplayermp1.posZ, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
                mcServer.worldMngr[0].updateEntityWithOptionalForce(entityplayermp, false);
                mcServer.worldMngr[0].chunkProvider.chunkLoadOverride = true;
                (new Teleporter()).func_4107_a(mcServer.worldMngr[0], entityplayermp);
                mcServer.worldMngr[0].chunkProvider.chunkLoadOverride = false;
            }
            func_28172_a(entityplayermp);
            entityplayermp.playerNetServerHandler.teleportTo(entityplayermp.posX, entityplayermp.posY, entityplayermp.posZ, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
            entityplayermp.setWorldHandler(mcServer.worldMngr[0]);
            func_28170_a(entityplayermp, mcServer.worldMngr[0]);
            func_30008_g(entityplayermp);
            return entityplayermp1;

        } else {

            WorldServer worldServer = mcServer.getWorldManager(entityplayermp.dimension);
            EntityTracker entityTracker = mcServer.getEntityTracker(entityplayermp.dimension);
            entityTracker.func_9238_a(entityplayermp);
            entityTracker.func_610_b(entityplayermp);
        playerManagerObj[0].removePlayer(entityplayermp);
            playerEntities.remove(entityplayermp);
            worldServer.removePlayer(entityplayermp);
            EntityPlayerMP entityplayermp1 = new EntityPlayerMP(mcServer, worldServer, entityplayermp.username, new ItemInWorldManager(worldServer));
            entityplayermp1.field_331_c = entityplayermp.field_331_c;
            entityplayermp1.playerNetServerHandler = entityplayermp.playerNetServerHandler;
            worldServer.chunkProvider.loadChunk((int) entityplayermp1.posX >> 4, (int) entityplayermp1.posZ >> 4);
            for (; worldServer.getCollidingBoundingBoxes(entityplayermp1, entityplayermp1.boundingBox).size() != 0; entityplayermp1.setPosition(entityplayermp1.posX, entityplayermp1.posY + 1.0D, entityplayermp1.posZ)) {
            }
            entityplayermp1.playerNetServerHandler.sendPacket(new Packet9Respawn());
            entityplayermp1.playerNetServerHandler.teleportTo(entityplayermp1.posX, entityplayermp1.posY, entityplayermp1.posZ, entityplayermp1.rotationYaw, entityplayermp1.rotationPitch);
        playerManagerObj[0].addPlayer(entityplayermp1);
            worldServer.entityJoinedWorld(entityplayermp1);
            playerEntities.add(entityplayermp1);
            return entityplayermp1;
        }
    }

    public void func_637_b() {
        for(int i = 0; i < playerManagerObj.length; i++) {
            playerManagerObj[i].func_538_a();
        }
    }

    public void func_622_a(int i, int j, int k, int l) {
        getPlayerManager(l).func_535_a(i, j, k);
    }

    public void sendPacketToAllPlayers(Packet packet) {
        for (int i = 0; i < playerEntities.size(); i++) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) playerEntities.get(i);
            entityplayermp.playerNetServerHandler.sendPacket(packet);
        }

    }

    public void sendPacketToAllPlayersInDimension(Packet packet, int i)
    {
        for(int j = 0; j < playerEntities.size(); j++)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)playerEntities.get(j);
            if(entityplayermp.dimension == i)
            {
                entityplayermp.playerNetServerHandler.sendPacket(packet);
            }
        }

    }

    public String getPlayerList() {
        String s = "";
        for (int i = 0; i < playerEntities.size(); i++) {
            if (i > 0) {
                s = (new StringBuilder()).append(s).append(", ").toString();
            }
            s = (new StringBuilder()).append(s).append(((EntityPlayerMP) playerEntities.get(i)).username).toString();
        }

        return s;
    }

    public void banPlayer(String s) {
        banList.add(s.toLowerCase());
        writeBannedPlayers();
    }

    public void unbanPlayer(String s) {
        banList.remove(s.toLowerCase());
        writeBannedPlayers();
    }

    private void readBannedPlayers() {
        try {
            banList.clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(bannedPlayersFile));
            for (String s = ""; (s = bufferedreader.readLine()) != null; ) {
                banList.add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        } catch (Exception exception) {
            logger.warning((new StringBuilder()).append("Failed to load ban list: ").append(exception).toString());
        }
    }

    private void writeBannedPlayers() {
        try {
            PrintWriter printwriter = new PrintWriter(new FileWriter(bannedPlayersFile, false));
            String s;
            for (Iterator iterator = banList.iterator(); iterator.hasNext(); printwriter.println(s)) {
                s = (String) iterator.next();
            }

            printwriter.close();
        } catch (Exception exception) {
            logger.warning((new StringBuilder()).append("Failed to save ban list: ").append(exception).toString());
        }
    }

    public void banIP(String s) {
        bannedIPs.add(s.toLowerCase());
        saveBannedList();
    }

    public void unbanIP(String s) {
        bannedIPs.remove(s.toLowerCase());
        saveBannedList();
    }

    private void loadBannedList() {
        try {
            bannedIPs.clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(ipBanFile));
            for (String s = ""; (s = bufferedreader.readLine()) != null; ) {
                bannedIPs.add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        } catch (Exception exception) {
            logger.warning((new StringBuilder()).append("Failed to load ip ban list: ").append(exception).toString());
        }
    }

    private void saveBannedList() {
        try {
            PrintWriter printwriter = new PrintWriter(new FileWriter(ipBanFile, false));
            String s;
            for (Iterator iterator = bannedIPs.iterator(); iterator.hasNext(); printwriter.println(s)) {
                s = (String) iterator.next();
            }

            printwriter.close();
        } catch (Exception exception) {
            logger.warning((new StringBuilder()).append("Failed to save ip ban list: ").append(exception).toString());
        }
    }

    public void opPlayer(String s) {
        ops.add(s.toLowerCase());
        saveOps();
    }

    public void deopPlayer(String s) {
        ops.remove(s.toLowerCase());
        saveOps();
    }

    private void loadOps() {
        try {
            ops.clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(opFile));
            for (String s = ""; (s = bufferedreader.readLine()) != null; ) {
                ops.add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        } catch (Exception exception) {
            logger.warning((new StringBuilder()).append("Failed to load ip ban list: ").append(exception).toString());
        }
    }

    private void saveOps() {
        try {
            PrintWriter printwriter = new PrintWriter(new FileWriter(opFile, false));
            String s;
            for (Iterator iterator = ops.iterator(); iterator.hasNext(); printwriter.println(s)) {
                s = (String) iterator.next();
            }

            printwriter.close();
        } catch (Exception exception) {
            logger.warning((new StringBuilder()).append("Failed to save ip ban list: ").append(exception).toString());
        }
    }

    // whitelist
    public void whitelistPlayer(String s) {
        whitelist.add(s.toLowerCase());
        saveWhitelist();
    }

    public void deWhitelistPlayer(String s) {
        whitelist.remove(s.toLowerCase());
        saveWhitelist();
    }

    private void loadWhitelist() {
        try {
            whitelist.clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(whitelistFile));
            for (String s = ""; (s = bufferedreader.readLine()) != null; ) {
                whitelist.add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        } catch (Exception exception) {
            logger.warning((new StringBuilder()).append("[Cloth] Failed to load whitelist: ").append(exception).toString());
        }
    }

    private void saveWhitelist() {
        try {
            PrintWriter printwriter = new PrintWriter(new FileWriter(whitelistFile, false));
            String s;
            for (Iterator iterator = whitelist.iterator(); iterator.hasNext(); printwriter.println(s)) {
                s = (String) iterator.next();
            }

            printwriter.close();
        } catch (Exception exception) {
            logger.warning((new StringBuilder()).append("[Cloth] Failed to save whitelist: ").append(exception).toString());
        }
    }

    public boolean isInWhitelist(String player) {
        return whitelist.contains(player.trim().toLowerCase());
    }

    //End whitelist addition
    public boolean isOp(String s) {
        return ops.contains(s.trim().toLowerCase());
    }

    public EntityPlayerMP getPlayerEntity(String s) {
        for (int i = 0; i < playerEntities.size(); i++) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) playerEntities.get(i);
            if (entityplayermp.username.equalsIgnoreCase(s)) {
                return entityplayermp;
            }
        }

        return null;
    }

    public void sendChatMessageToPlayer(String s, String s1) {
        EntityPlayerMP entityplayermp = getPlayerEntity(s);
        if (entityplayermp != null) {
            entityplayermp.playerNetServerHandler.sendPacket(new Packet3Chat(s1));
        }
    }

    public void func_12022_a(double d, double d1, double d2, double d3, Packet packet) {
        for (int i = 0; i < playerEntities.size(); i++) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) playerEntities.get(i);
            double d4 = d - entityplayermp.posX;
            double d5 = d1 - entityplayermp.posY;
            double d6 = d2 - entityplayermp.posZ;
            if (d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3) {
                entityplayermp.playerNetServerHandler.sendPacket(packet);
            }
        }

    }

    public void sendChatMessageToAllOps(String s) {
        Packet3Chat packet3chat = new Packet3Chat(s);
        for (int i = 0; i < playerEntities.size(); i++) {

            EntityPlayerMP entityplayermp = (EntityPlayerMP) playerEntities.get(i);
            //  System.out.println("Sending chat packet to "+entityplayermp.username);
            if (isOp(entityplayermp.username)) {
                entityplayermp.playerNetServerHandler.sendPacket(packet3chat);
            }
        }

    }

    public void sendChatMessageToAllPlayers(String s) {
        Packet3Chat packet3chat = new Packet3Chat(s);
        for (int i = 0; i < playerEntities.size(); i++) {

            EntityPlayerMP entityplayermp = (EntityPlayerMP) playerEntities.get(i);
            // System.out.println("Sending chat packet to "+entityplayermp.username);
            entityplayermp.playerNetServerHandler.sendPacket(packet3chat);

        }

    }

    public boolean sendPacketToPlayer(String s, Packet packet) {
        EntityPlayerMP entityplayermp = getPlayerEntity(s);
        if (entityplayermp != null) {
            entityplayermp.playerNetServerHandler.sendPacket(packet);
            return true;
        } else {
            return false;
        }
    }

    public void sentTileEntityToPlayer(int i, int j, int k, TileEntity tileentity) {
        getPlayerManager(tileentity.worldObj.worldProvider.worldType).func_541_a(new Packet59ComplexEntity(i, j, k, tileentity), i, j, k);
    }

    public void savePlayerStates() {
        for (int i = 0; i < playerEntities.size(); i++) {
            playerNBTManagerObj.writePlayerData((EntityPlayerMP) playerEntities.get(i));
        }

    }

    private PlayerManager getPlayerManager(int i)
    {
        if(i == 0)
        {
            return playerManagerObj[0];
        }
        if(i == -1)
        {
            return playerManagerObj[1];
        }
        if(i == 1)
        {
            return playerManagerObj[2];
        } else
        {
            return playerManagerObj[0];
        }
    }

    public void func_28172_a(EntityPlayerMP entityplayermp)
    {
//        getPlayerManager(entityplayermp.dimension).removePlayer(entityplayermp);
        playerManagerObj[0].removePlayer(entityplayermp);
        playerManagerObj[1].removePlayer(entityplayermp);
        playerManagerObj[2].removePlayer(entityplayermp);
        getPlayerManager(entityplayermp.dimension).addPlayer(entityplayermp);
        WorldServer worldserver = mcServer.getWorldManager(entityplayermp.dimension);
        worldserver.chunkProvider.loadChunk((int)entityplayermp.posX >> 4, (int)entityplayermp.posZ >> 4);
    }

    public void sendPlayerToOtherDimension(EntityPlayerMP entityplayermp)
    {
        WorldServer worldserver = mcServer.getWorldManager(entityplayermp.dimension);
        int i = 0;
        if(entityplayermp.dimension == -1)
        {
            i = 0;
        } else
        {
            i = -1;
        }
        logger.info((new StringBuilder("Sending ")).append(entityplayermp.username).append(i).toString());
        entityplayermp.dimension = i;
        WorldServer worldserver1 = mcServer.getWorldManager(entityplayermp.dimension);
        entityplayermp.playerNetServerHandler.sendPacket(new Packet9Respawn());
        worldserver.removePlayer(entityplayermp);
        entityplayermp.isDead = false;
        double d = entityplayermp.posX;
        double d1 = entityplayermp.posZ;
        double d2 = 8D;
        if(entityplayermp.dimension == 1)
        {
            d /= d2;
            d1 /= d2;
            entityplayermp.setLocationAndAngles(d, entityplayermp.posY, d1, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
            if(entityplayermp.isEntityAlive())
            {
                worldserver.updateEntityWithOptionalForce(entityplayermp, false);
            }
        } else
        {
            d *= d2;
            d1 *= d2;
            entityplayermp.setLocationAndAngles(d, entityplayermp.posY, d1, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
            if(entityplayermp.isEntityAlive())
            {
                worldserver.updateEntityWithOptionalForce(entityplayermp, false);
            }
        }
        if(entityplayermp.isEntityAlive())
        {
            worldserver1.entityJoinedWorld(entityplayermp);
            entityplayermp.setLocationAndAngles(d, entityplayermp.posY, d1, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
            worldserver1.updateEntityWithOptionalForce(entityplayermp, false);
            worldserver1.chunkProvider.chunkLoadOverride = true;
            (new Teleporter()).func_4107_a(worldserver1, entityplayermp);
            worldserver1.chunkProvider.chunkLoadOverride = false;
        }
        func_28172_a(entityplayermp);
        entityplayermp.playerNetServerHandler.teleportTo(entityplayermp.posX, entityplayermp.posY, entityplayermp.posZ, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
        entityplayermp.setWorldHandler(worldserver1);
        func_28170_a(entityplayermp, worldserver1);
        func_30008_g(entityplayermp);
        worldserver1.playSoundAtEntity(entityplayermp, "portal.travel", 1.0F, random.nextFloat() * 0.4F + 0.8F);
//        System.out.println("Sending login.");
//        sendPacketToPlayer(entityplayermp.username, new Packet1Login("", "", entityplayermp.field_331_c, worldserver1.randomSeed, (byte) worldserver1.worldProvider.worldType));
//        System.out.println("Sending respawn.");
//        sendPacketToPlayer(entityplayermp.username, new Packet6SpawnPosition(worldserver1.spawnX, worldserver1.spawnY, worldserver1.spawnZ));
//        entityplayermp.playerNetServerHandler.sendPacket(new Packet9Respawn());
//        entityplayermp.playerNetServerHandler.sendPacket(new Packet10Flying());
    }

    public void sendPlayerToSkyDimension(EntityPlayerMP entityplayermp)
    {
        WorldServer worldserver = mcServer.getWorldManager(entityplayermp.dimension);
        int i = 0;
        if(entityplayermp.dimension == -1)
        {
            i = 0;
        } else
        {
            i = 1;
        }
        logger.info((new StringBuilder("Sending ")).append(entityplayermp.username).append(i).toString());
        entityplayermp.dimension = i;
        WorldServer worldserver1 = mcServer.getWorldManager(entityplayermp.dimension);
        entityplayermp.playerNetServerHandler.sendPacket(new Packet9Respawn());
        worldserver.removePlayer(entityplayermp);
        entityplayermp.isDead = false;
        double d = entityplayermp.posX;
        double d1 = entityplayermp.posZ;
        double d2 = 8D;
        if(entityplayermp.dimension == -1)
        {
            d /= d2;
            d1 /= d2;
            entityplayermp.setLocationAndAngles(d, entityplayermp.posY, d1, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
            if(entityplayermp.isEntityAlive())
            {
                worldserver.updateEntityWithOptionalForce(entityplayermp, false);
            }
        } else
        {
            d *= d2;
            d1 *= d2;
            entityplayermp.setLocationAndAngles(d, entityplayermp.posY, d1, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
            if(entityplayermp.isEntityAlive())
            {
                worldserver.updateEntityWithOptionalForce(entityplayermp, false);
            }
        }
        if(entityplayermp.isEntityAlive())
        {
            worldserver1.entityJoinedWorld(entityplayermp);
            entityplayermp.setLocationAndAngles(d, entityplayermp.posY, d1, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
            worldserver1.updateEntityWithOptionalForce(entityplayermp, false);
            worldserver1.chunkProvider.chunkLoadOverride = true;
            (new Teleporter()).func_4107_a(worldserver1, entityplayermp);
            worldserver1.chunkProvider.chunkLoadOverride = false;
        }
        func_28172_a(entityplayermp);
        entityplayermp.playerNetServerHandler.teleportTo(entityplayermp.posX, entityplayermp.posY, entityplayermp.posZ, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
        entityplayermp.setWorldHandler(worldserver1);
        func_28170_a(entityplayermp, worldserver1);
        func_30008_g(entityplayermp);
        worldserver1.playSoundAtEntity(entityplayermp, "portal.travel", 1.0F, random.nextFloat() * 0.4F + 0.8F);
//        System.out.println("Sending login.");
//        sendPacketToPlayer(entityplayermp.username, new Packet1Login("", "", entityplayermp.field_331_c, worldserver1.randomSeed, (byte) worldserver1.worldProvider.worldType));
//        System.out.println("Sending respawn.");
//        sendPacketToPlayer(entityplayermp.username, new Packet6SpawnPosition(worldserver1.spawnX, worldserver1.spawnY, worldserver1.spawnZ));
//        entityplayermp.playerNetServerHandler.sendPacket(new Packet9Respawn());
//        entityplayermp.playerNetServerHandler.sendPacket(new Packet10Flying());
    }

    public void func_28170_a(EntityPlayerMP entityplayermp, WorldServer worldserver)
    {
        entityplayermp.playerNetServerHandler.sendPacket(new Packet4UpdateTime(worldserver.worldTime));
    }

    public void func_30008_g(EntityPlayerMP entityplayermp)
    {
        entityplayermp.func_30001_B();
    }
}
