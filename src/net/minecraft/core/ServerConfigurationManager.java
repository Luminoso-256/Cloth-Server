package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.cloth.file.GameruleManager;
import net.minecraft.cloth.ExploitUtils;
import net.minecraft.MinecraftServer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

public class ServerConfigurationManager
{

    public ServerConfigurationManager(MinecraftServer minecraftserver)
    {
        playerEntities = new ArrayList();
        banList = new HashSet();
        bannedIPs = new HashSet();
        ops = new HashSet();
        mcServer = minecraftserver;
        bannedPlayersFile = minecraftserver.getFile("banned-players.txt");
        ipBanFile = minecraftserver.getFile("banned-ips.txt");
        opFile = minecraftserver.getFile("ops.txt");
        whitelistFile = minecraftserver.getFile("whitelist.txt");
        playerManagerObj = new PlayerManager(minecraftserver);
        maxPlayers = minecraftserver.propertyManagerObj.getIntProperty("max-players", 20);
        readBannedPlayers();
        loadBannedList();
        loadOps();
        writeBannedPlayers();
        saveBannedList();
        saveOps();
    }

    public void setPlayerManager(WorldServer worldserver)
    {
        playerNBTManagerObj = new PlayerNBTManager(new File(worldserver.field_797_s, "players"));
    }

    public int func_640_a()
    {
        return playerManagerObj.func_542_b();
    }

    public void playerLoggedIn(EntityPlayerMP entityplayermp)
    {







        //Connect player to world
        playerEntities.add(entityplayermp);
        playerNBTManagerObj.readPlayerData(entityplayermp);
        mcServer.overworld.chunkProvider.loadChunk((int)entityplayermp.posX >> 4, (int)entityplayermp.posZ >> 4);
        for(; mcServer.overworld.getCollidingBoundingBoxes(entityplayermp, entityplayermp.boundingBox).size() != 0; entityplayermp.setPosition(entityplayermp.posX, entityplayermp.posY + 1.0D, entityplayermp.posZ)) { }
        mcServer.overworld.entityJoinedWorld(entityplayermp);
        playerManagerObj.func_9214_a(entityplayermp);

        //Check their invenotry for illageal items.
        String itemIDBlacklist = gameruleManager.getGamerule("itemidblacklist", " ");
        boolean IsInvClean = true;
        int MainInvSize = 36;
        int CraftInvSize  = 4;
        int ArmorInvSize = 4;
        ItemStack fallbackItem = new ItemStack(1);
        for(int i = 0; i < entityplayermp.inventory.getInventorySize(); i++){
            ItemStack item = entityplayermp.inventory.getStackInSlot(i);
            // System.out.println("Processing item: "+item.itemID+" in slot "+i);
            if(item != null){
                System.out.println("Slot contains item of ID: "+item.itemID);
                //Whitelist
                ExploitUtils exploitUtils = new ExploitUtils();
                if(exploitUtils.IsIdValid(item.itemID)) {

                    //Whatever makes it through whitelist check gets processed by Blacklist Check
                    if (itemIDBlacklist.contains(Integer.toString(item.itemID))) {

                        entityplayermp.inventory.setInventorySlotContents(i, fallbackItem);
                    }
                }
                else{
                    entityplayermp.inventory.setInventorySlotContents(i, fallbackItem);
                }
                }



            else{System.out.println("Slot "+i+" contained null or nonextistant item");}
        }
        //MOTD
        File tempFile = new File("motd.txt");
        boolean exists = tempFile.exists();
        String MOTD = null;
        List<String> PossibleMOTDS = null;
        Random random = new Random();
        if(exists){
            try {
                PossibleMOTDS =  Files.readAllLines( Paths.get(tempFile.getAbsolutePath()) );
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(String log:PossibleMOTDS){
                logger.info(log);
            }
            MOTD = PossibleMOTDS.get(random.nextInt(PossibleMOTDS.size()));
        }
        else {
            PropertyManager propertyManager = new PropertyManager(new File("server.properties"));
            MOTD = propertyManager.getStringProperty("motd", "Welcome %player% to %world% ! ");

        }
        String pnameReplace = MOTD.replace("%player%", entityplayermp.username);
        String finalMOTD = pnameReplace.replace("%world%", mcServer.worldName);
        sendChatMessageToPlayer(entityplayermp.username, finalMOTD);
    }

    public void func_613_b(EntityPlayerMP entityplayermp)
    {
        playerManagerObj.func_543_c(entityplayermp);
    }

    public void playerLoggedOut(EntityPlayerMP entityplayermp)
    {
        playerNBTManagerObj.writePlayerData(entityplayermp);
        mcServer.overworld.func_12016_d(entityplayermp);
        playerEntities.remove(entityplayermp);
        playerManagerObj.func_9213_b(entityplayermp);
    }

    public EntityPlayerMP login(NetLoginHandler netloginhandler, String username, String s1)
    {
        PlayerDataManager playerDataManager = new PlayerDataManager();
        if(GameruleManager.getBooleanGamerule("preview_hardcore", false) && playerDataManager.getStat(username, "game.hardcorefailed") == "yes"){

            netloginhandler.kickUser("You have failed the hardcore mode challenge, and the server has not reset!");
        }
        if(banList.contains(username.trim().toLowerCase()))
        {
            netloginhandler.kickUser("You are banned from this server!");
            return null;
        }
        if(gameruleManager.getGamerule("usewhitelist", false) && !whitelist.contains(s.trim().toLowerCase())){
            //You arent whitelisted!
            netloginhandler.kickUser("You arent whitelisted on this server!");
        }
        String s2 = netloginhandler.netManager.getRemoteAddress().toString();
        s2 = s2.substring(s2.indexOf("/") + 1);
        s2 = s2.substring(0, s2.indexOf(":"));
        if(bannedIPs.contains(s2))
        {
            netloginhandler.kickUser("Your IP address is banned from this server!");
            return null;
        }
        if(playerEntities.size() >= maxPlayers)
        {
            netloginhandler.kickUser("The server is full!");
            return null;
        }
        for(int i = 0; i < playerEntities.size(); i++)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)playerEntities.get(i);
            if(entityplayermp.username.equalsIgnoreCase(username))
            {
                entityplayermp.field_421_a.func_43_c("You logged in from another location");
            }
        }

        return new EntityPlayerMP(mcServer, mcServer.overworld, username, new ItemInWorldManager(mcServer.overworld));
    }

    public EntityPlayerMP func_9242_d(EntityPlayerMP entityplayermp)
    {
        mcServer.field_6028_k.func_9238_a(entityplayermp);
        mcServer.field_6028_k.func_610_b(entityplayermp);
        playerManagerObj.func_9213_b(entityplayermp);
        playerEntities.remove(entityplayermp);
        mcServer.overworld.func_12014_e(entityplayermp);
        EntityPlayerMP entityplayermp1 = new EntityPlayerMP(mcServer, mcServer.overworld, entityplayermp.username, new ItemInWorldManager(mcServer.overworld));
        entityplayermp1.field_331_c = entityplayermp.field_331_c;
        entityplayermp1.field_421_a = entityplayermp.field_421_a;
        mcServer.overworld.chunkProvider.loadChunk((int)entityplayermp1.posX >> 4, (int)entityplayermp1.posZ >> 4);
        for(; mcServer.overworld.getCollidingBoundingBoxes(entityplayermp1, entityplayermp1.boundingBox).size() != 0; entityplayermp1.setPosition(entityplayermp1.posX, entityplayermp1.posY + 1.0D, entityplayermp1.posZ)) { }
        entityplayermp1.field_421_a.sendPacket(new Packet9());
        entityplayermp1.field_421_a.func_41_a(entityplayermp1.posX, entityplayermp1.posY, entityplayermp1.posZ, entityplayermp1.rotationYaw, entityplayermp1.rotationPitch);
        playerManagerObj.func_9214_a(entityplayermp1);
        mcServer.overworld.entityJoinedWorld(entityplayermp1);
        playerEntities.add(entityplayermp1);
        return entityplayermp1;
    }

    public void func_637_b()
    {
        playerManagerObj.func_538_a();
    }

    public void func_622_a(int i, int j, int k)
    {
        playerManagerObj.func_535_a(i, j, k);
    }

    public void sendPacketToAllPlayers(Packet packet)
    {
        for(int i = 0; i < playerEntities.size(); i++)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)playerEntities.get(i);
            entityplayermp.field_421_a.sendPacket(packet);
        }

    }

    public String getPlayerList()
    {
        String s = "";
        for(int i = 0; i < playerEntities.size(); i++)
        {
            if(i > 0)
            {
                s = (new StringBuilder()).append(s).append(", ").toString();
            }
            s = (new StringBuilder()).append(s).append(((EntityPlayerMP)playerEntities.get(i)).username).toString();
        }

        return s;
    }

    public void banPlayer(String s)
    {
        banList.add(s.toLowerCase());
        writeBannedPlayers();
    }

    public void unbanPlayer(String s)
    {
        banList.remove(s.toLowerCase());
        writeBannedPlayers();
    }

    private void readBannedPlayers()
    {
        try
        {
            banList.clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(bannedPlayersFile));
            for(String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                banList.add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to load ban list: ").append(exception).toString());
        }
    }

    private void writeBannedPlayers()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(bannedPlayersFile, false));
            String s;
            for(Iterator iterator = banList.iterator(); iterator.hasNext(); printwriter.println(s))
            {
                s = (String)iterator.next();
            }

            printwriter.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to save ban list: ").append(exception).toString());
        }
    }

    public void banIP(String s)
    {
        bannedIPs.add(s.toLowerCase());
        saveBannedList();
    }

    public void unbanIP(String s)
    {
        bannedIPs.remove(s.toLowerCase());
        saveBannedList();
    }

    private void loadBannedList()
    {
        try
        {
            bannedIPs.clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(ipBanFile));
            for(String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                bannedIPs.add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to load ip ban list: ").append(exception).toString());
        }
    }

    private void saveBannedList()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(ipBanFile, false));
            String s;
            for(Iterator iterator = bannedIPs.iterator(); iterator.hasNext(); printwriter.println(s))
            {
                s = (String)iterator.next();
            }

            printwriter.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to save ip ban list: ").append(exception).toString());
        }
    }

    public void opPlayer(String s)
    {
        ops.add(s.toLowerCase());
        saveOps();
    }

    public void deopPlayer(String s)
    {
        ops.remove(s.toLowerCase());
        saveOps();
    }

    private void loadOps()
    {
        try
        {
            ops.clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(opFile));
            for(String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                ops.add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to load ip ban list: ").append(exception).toString());
        }
    }

    private void saveOps()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(opFile, false));
            String s;
            for(Iterator iterator = ops.iterator(); iterator.hasNext(); printwriter.println(s))
            {
                s = (String)iterator.next();
            }

            printwriter.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to save ip ban list: ").append(exception).toString());
        }
    }
    // whitelist
    public void whitelistPlayer(String s)
    {
        whitelist.add(s.toLowerCase());
        saveWhitelist();
    }

    public void deWhitelistPlayer(String s)
    {
        whitelist.remove(s.toLowerCase());
        saveWhitelist();
    }

    private void loadWhitelist()
    {
        try
        {
            whitelist.clear();
            BufferedReader bufferedreader = new BufferedReader(new FileReader(whitelistFile));
            for(String s = ""; (s = bufferedreader.readLine()) != null;)
            {
                whitelist.add(s.trim().toLowerCase());
            }

            bufferedreader.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("[Cloth] Failed to load whitelist: ").append(exception).toString());
        }
    }

    private void saveWhitelist()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(whitelistFile, false));
            String s;
            for(Iterator iterator = whitelist.iterator(); iterator.hasNext(); printwriter.println(s))
            {
                s = (String)iterator.next();
            }

            printwriter.close();
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("[Cloth] Failed to save whitelist: ").append(exception).toString());
        }
    }

    public boolean isInWhitelist(String player)
    {
        return whitelist.contains(player.trim().toLowerCase());
    }
    //End whitelist addition
    public boolean isOp(String s)
    {
        return ops.contains(s.trim().toLowerCase());
    }

    public EntityPlayerMP getPlayerEntity(String s)
    {
        for(int i = 0; i < playerEntities.size(); i++)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)playerEntities.get(i);
            if(entityplayermp.username.equalsIgnoreCase(s))
            {
                return entityplayermp;
            }
        }

        return null;
    }

    public void sendChatMessageToPlayer(String s, String s1)
    {
        EntityPlayerMP entityplayermp = getPlayerEntity(s);
        if(entityplayermp != null)
        {
            entityplayermp.field_421_a.sendPacket(new Packet3Chat(s1));
        }
    }

    public void func_12022_a(double d, double d1, double d2, double d3, Packet packet)
    {
        for(int i = 0; i < playerEntities.size(); i++)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)playerEntities.get(i);
            double d4 = d - entityplayermp.posX;
            double d5 = d1 - entityplayermp.posY;
            double d6 = d2 - entityplayermp.posZ;
            if(d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3)
            {
                entityplayermp.field_421_a.sendPacket(packet);
            }
        }

    }

    public void sendChatMessageToAllOps(String s)
    {
        Packet3Chat packet3chat = new Packet3Chat(s);
        for(int i = 0; i < playerEntities.size(); i++)
        {

            EntityPlayerMP entityplayermp = (EntityPlayerMP)playerEntities.get(i);
          //  System.out.println("Sending chat packet to "+entityplayermp.username);
            if(isOp(entityplayermp.username))
            {
                entityplayermp.field_421_a.sendPacket(packet3chat);
            }
        }

    }
    public void sendChatMessageToAllPlayers(String s)
    {
        Packet3Chat packet3chat = new Packet3Chat(s);
        for(int i = 0; i < playerEntities.size(); i++)
        {

            EntityPlayerMP entityplayermp = (EntityPlayerMP)playerEntities.get(i);
           // System.out.println("Sending chat packet to "+entityplayermp.username);
            entityplayermp.field_421_a.sendPacket(packet3chat);

        }

    }

    public boolean sendPacketToPlayer(String s, Packet packet)
    {
        EntityPlayerMP entityplayermp = getPlayerEntity(s);
        if(entityplayermp != null)
        {
            entityplayermp.field_421_a.sendPacket(packet);
            return true;
        } else
        {
            return false;
        }
    }

    public void sentTileEntityToPlayer(int i, int j, int k, TileEntity tileentity)
    {
        playerManagerObj.func_541_a(new Packet59ComplexEntity(i, j, k, tileentity), i, j, k);
    }

    public void savePlayerStates()
    {
        for(int i = 0; i < playerEntities.size(); i++)
        {
            playerNBTManagerObj.writePlayerData((EntityPlayerMP)playerEntities.get(i));
        }

    }

    public static Logger logger = Logger.getLogger("Minecraft");
    public List playerEntities;
    private MinecraftServer mcServer;
    public PlayerManager playerManagerObj;
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
}
