// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.server;

import net.minecraft.clothutils.GameruleManager;
import net.minecraft.clothutils.WorldGenParams;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.ChunkProviderServer;
import net.minecraft.src.ConsoleLogManager;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EntityTracker;
import net.minecraft.src.ICommandListener;
import net.minecraft.src.IUpdatePlayerListBox;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NetServerHandler;
import net.minecraft.src.NetworkListenThread;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet4UpdateTime;
import net.minecraft.src.PropertyManager;
import net.minecraft.src.ServerCommand;
import net.minecraft.src.ServerConfigurationManager;
import net.minecraft.src.ServerGUI;
import net.minecraft.src.ThreadCommandReader;
import net.minecraft.src.ThreadServerApplication;
import net.minecraft.src.ThreadSleepForever;
import net.minecraft.src.Vec3D;
import net.minecraft.src.WorldManager;
import net.minecraft.src.WorldServer;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.clothutils.NameIDMappings;
public class MinecraftServer
    implements ICommandListener, Runnable
{

    public MinecraftServer()
    {
        field_6025_n = true;
        field_6032_g = false;
        field_9014_h = 0;
        field_9010_p = new ArrayList();
        commands = Collections.synchronizedList(new ArrayList());
        new ThreadSleepForever(this);
    }




    private boolean func_6008_d() throws UnknownHostException
    {

        ThreadCommandReader threadcommandreader = new ThreadCommandReader(this);
        threadcommandreader.setDaemon(true);
        threadcommandreader.start();
        ConsoleLogManager.init();
        logger.info("Cloth v1.0.0| Client Ver: Alpha1.2.6 | Original Server Ver: 0.28.0");
        if(Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L)
        {
            logger.warning("**** NOT ENOUGH RAM!");
            logger.warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar server.jar\"");
        }
        logger.info("Loading properties");
        Random random = new Random();
        propertyManagerObj = new PropertyManager(new File("server.properties"));
        GameruleManager gameruleManager = new GameruleManager(new File("server.gamerules")); //gamerule config file
        String s = propertyManagerObj.getStringProperty("server-ip", "");
        long Seed = propertyManagerObj.getLongProperty("seed", random.nextLong()); // just here to ensure property initialization  (i think)
        onlineMode = propertyManagerObj.getBooleanProperty("online-mode", true);
        noAnimals = propertyManagerObj.getBooleanProperty("spawn-animals", true);
        field_9011_n = propertyManagerObj.getBooleanProperty("pvp", true);
        InetAddress inetaddress = null;
        if(s.length() > 0)
        {
            inetaddress = InetAddress.getByName(s);
        }
        int i = propertyManagerObj.getIntProperty("server-port", 25565);
        logger.info((new StringBuilder()).append("Starting Minecraft server on ").append(s.length() != 0 ? s : "*").append(":").append(i).toString());
        try
        {
            field_6036_c = new NetworkListenThread(this, inetaddress, i);
        }
        catch(IOException ioexception)
        {
            logger.warning("**** FAILED TO BIND TO PORT!");
            logger.log(Level.WARNING, (new StringBuilder()).append("The exception was: ").append(ioexception.toString()).toString());
            logger.warning("Perhaps a server is already running on that port?");
            return false;
        }
        if(!onlineMode)
        {
            logger.warning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            logger.warning("The server will make no attempt to authenticate usernames. Beware.");
            logger.warning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            logger.warning("To change this, set \"online-mode\" to \"true\" in the server.settings file.");
        }
        configManager = new ServerConfigurationManager(this);
        field_6028_k = new EntityTracker(this);
        String s1 = propertyManagerObj.getStringProperty("level-name", "world");
        logger.info((new StringBuilder()).append("Preparing level \"").append(s1).append("\"").toString());
        func_6017_c(s1);
        logger.info("Done! For help, type \"help\" or \"?\"");
        return true;

    }

    private void func_6017_c(String s)
    {
        logger.info("Preparing start region");
        worldMngr = new WorldServer(this, new File("."), s, propertyManagerObj.getBooleanProperty("hellworld", false) ? -1 : 0);
        worldMngr.func_4072_a(new WorldManager(this));
        worldMngr.monstersEnabled = propertyManagerObj.getBooleanProperty("spawn-monsters", true) ? 1 : 0;
        configManager.setPlayerManager(worldMngr);
        byte byte0 = 10;
        for(int i = -byte0; i <= byte0; i++)
        {
            func_6019_a("Preparing spawn area", ((i + byte0) * 100) / (byte0 + byte0 + 1));
            for(int j = -byte0; j <= byte0; j++)
            {
                if(!field_6025_n)
                {
                    return;
                }
                worldMngr.field_821.loadChunk((worldMngr.spawnX >> 4) + i, (worldMngr.spawnZ >> 4) + j);
            }

        }

        func_6011_e();
    }

    private void func_6019_a(String s, int i)
    {
        field_9013_i = s;
        field_9012_j = i;
        System.out.println((new StringBuilder()).append(s).append(": ").append(i).append("%").toString());
    }

    private void func_6011_e()
    {
        field_9013_i = null;
        field_9012_j = 0;
    }

    private void saveServerWorld()
    {
        logger.info("Saving chunks");
        worldMngr.func_485_a(true, null);
    }

    private void func_6013_g()
    {
        logger.info("Stopping server");
        if(configManager != null)
        {
            configManager.savePlayerStates();
        }
        if(worldMngr != null)
        {
            saveServerWorld();
        }
    }

    public void func_6016_a()
    {
        field_6025_n = false;
    }

    public void run()
    {
        try
        {
            if(func_6008_d())
            {
                long l = System.currentTimeMillis();
                long l1 = 0L;
                while(field_6025_n) 
                {
                    long l2 = System.currentTimeMillis();
                    long l3 = l2 - l;
                    if(l3 > 2000L)
                    {
                        logger.warning("Can't keep up! Did the system time change, or is the server overloaded?");
                        l3 = 2000L;
                    }
                    if(l3 < 0L)
                    {
                        logger.warning("Time ran backwards! Did the system time change?");
                        l3 = 0L;
                    }
                    l1 += l3;
                    l = l2;
                    while(l1 > 50L) 
                    {
                        l1 -= 50L;
                        func_6018_h();
                    }
                    Thread.sleep(1L);
                }
            } else
            {
                while(field_6025_n) 
                {
                    commandLineParser();
                    try
                    {
                        Thread.sleep(10L);
                    }
                    catch(InterruptedException interruptedexception)
                    {
                        interruptedexception.printStackTrace();
                    }
                }
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            logger.log(Level.SEVERE, "Unexpected exception", exception);
            while(field_6025_n) 
            {
                commandLineParser();
                try
                {
                    Thread.sleep(10L);
                }
                catch(InterruptedException interruptedexception1)
                {
                    interruptedexception1.printStackTrace();
                }
            }
        }
        finally
        {
            func_6013_g();
            field_6032_g = true;
            System.exit(0);
        }
    }

    private void func_6018_h()
    {
        ArrayList arraylist = new ArrayList();
        for(Iterator iterator = field_6037_b.keySet().iterator(); iterator.hasNext();)
        {
            String s = (String)iterator.next();
            int k = ((Integer)field_6037_b.get(s)).intValue();
            if(k > 0)
            {
                field_6037_b.put(s, Integer.valueOf(k - 1));
            } else
            {
                arraylist.add(s);
            }
        }

        for(int i = 0; i < arraylist.size(); i++)
        {
            field_6037_b.remove(arraylist.get(i));
        }

        AxisAlignedBB.clearBoundingBoxPool();
        Vec3D.initialize();
        field_9014_h++;
        if(field_9014_h % 20 == 0)
        {
            configManager.sendPacketToAllPlayers(new Packet4UpdateTime(worldMngr.worldTime));
        }
        worldMngr.tick();
        while(worldMngr.func_6156_d()) ;
        worldMngr.func_459_b();
        field_6036_c.func_715_a();
        configManager.func_637_b();
        field_6028_k.func_607_a();
        for(int j = 0; j < field_9010_p.size(); j++)
        {
            ((IUpdatePlayerListBox)field_9010_p.get(j)).update();
        }

        try
        {
            commandLineParser();
        }
        catch(Exception exception)
        {
            logger.log(Level.WARNING, "Unexpected exception while parsing console command", exception);
        }
    }

    public void addCommand(String s, ICommandListener icommandlistener)
    {
        commands.add(new ServerCommand(s, icommandlistener));
    }

    public void commandLineParser()
    {
        do
        {
            if(commands.size() <= 0)
            {
                break;
            }
            ServerCommand servercommand = (ServerCommand)commands.remove(0);
            String s = servercommand.command;
            ICommandListener icommandlistener = servercommand.commandListener;
            String s1 = icommandlistener.getUsername();
            if(s.toLowerCase().startsWith("help") || s.toLowerCase().startsWith("?"))
            {
                icommandlistener.log("To run the server without a gui, start it like this:");
                icommandlistener.log("   java -Xmx1024M -Xms1024M -jar minecraft_server.jar nogui");
                icommandlistener.log("Console commands:");
                icommandlistener.log("   help  or  ?               shows this message");
                icommandlistener.log("   kick <player>             removes a player from the server");
                icommandlistener.log("   ban <player>              bans a player from the server");
                icommandlistener.log("   pardon <player>           pardons a banned player so that they can connect again");
                icommandlistener.log("   ban-ip <ip>               bans an IP address from the server");
                icommandlistener.log("   pardon-ip <ip>            pardons a banned IP address so that they can connect again");
                icommandlistener.log("   op <player>               turns a player into an op");
                icommandlistener.log("   deop <player>             removes op status from a player");
                icommandlistener.log("   tp <player1> <player2>    moves one player to the same location as another player");
                icommandlistener.log("   tpcord <player1> <x> <y> <z>    moves one player to the given cords");
                icommandlistener.log("   give <player> <name string> [num]  gives a player a resource");
                icommandlistener.log("   giveid <player> <id> [num]  gives a player a resource taking numeric ID");
                icommandlistener.log("   tell <player> <message>   sends a private message to a player");
                icommandlistener.log("   stop                      gracefully stops the server");
                icommandlistener.log("   save-all                  forces a server-wide level save");
                icommandlistener.log("   save-off                  disables terrain saving (useful for backup scripts)");
                icommandlistener.log("   save-on                   re-enables terrain saving");
                icommandlistener.log("   list                      lists all currently connected players");
                icommandlistener.log("   timeset <day/night/number of ticks>                   changes time");
                icommandlistener.log("   seed                   logs world seed to server console");

                icommandlistener.log("   say <message>             broadcasts a message to all players");
            } else
            if(s.toLowerCase().startsWith("seed")){
                WorldGenParams params = new WorldGenParams();
                icommandlistener.log("Seed for this world is:"+ params.GetSeedFromPropertiesFile());
            }
            if(s.toLowerCase().startsWith("timeset ")){
                String targetTime = s.substring(s.indexOf(" ")).trim();
                if(targetTime == "day"){ worldMngr.worldTime = 1000;}
                else
                if(targetTime == "night"){worldMngr.worldTime = 13000;}
                else{
                   int time =   Integer.parseInt(targetTime);
                   worldMngr.worldTime = time;
                }
            }
            if(s.toLowerCase().startsWith("list"))
            {
                icommandlistener.log((new StringBuilder()).append("Connected players: ").append(configManager.getPlayerList()).toString());
            } else
            if(s.toLowerCase().startsWith("stop"))
            {
                func_6014_a(s1, "Stopping the server..");
                field_6025_n = false;
            } else
            if(s.toLowerCase().startsWith("save-all"))
            {
                func_6014_a(s1, "Forcing save..");
                worldMngr.func_485_a(true, null);
                func_6014_a(s1, "Save complete.");
            } else
            if(s.toLowerCase().startsWith("save-off"))
            {
                func_6014_a(s1, "Disabling level saving..");
                worldMngr.field_816_A = true;
            } else
            if(s.toLowerCase().startsWith("save-on"))
            {
                func_6014_a(s1, "Enabling level saving..");
                worldMngr.field_816_A = false;
            } else
            if(s.toLowerCase().startsWith("op "))
            {
                String s2 = s.substring(s.indexOf(" ")).trim();
                configManager.opPlayer(s2);
                func_6014_a(s1, (new StringBuilder()).append("Opping ").append(s2).toString());
                configManager.sendChatMessageToPlayer(s2, "\247eYou are now op!");
            } else
            if(s.toLowerCase().startsWith("deop "))
            {
                String s3 = s.substring(s.indexOf(" ")).trim();
                configManager.deopPlayer(s3);
                configManager.sendChatMessageToPlayer(s3, "\247eYou are no longer op!");
                func_6014_a(s1, (new StringBuilder()).append("De-opping ").append(s3).toString());
            } else
            if(s.toLowerCase().startsWith("ban-ip "))
            {
                String s4 = s.substring(s.indexOf(" ")).trim();
                configManager.banIP(s4);
                func_6014_a(s1, (new StringBuilder()).append("Banning ip ").append(s4).toString());
            } else
            if(s.toLowerCase().startsWith("pardon-ip "))
            {
                String s5 = s.substring(s.indexOf(" ")).trim();
                configManager.unbanIP(s5);
                func_6014_a(s1, (new StringBuilder()).append("Pardoning ip ").append(s5).toString());
            } else
            if(s.toLowerCase().startsWith("ban "))
            {
                String s6 = s.substring(s.indexOf(" ")).trim();
                configManager.banPlayer(s6);
                func_6014_a(s1, (new StringBuilder()).append("Banning ").append(s6).toString());
                EntityPlayerMP entityplayermp = configManager.getPlayerEntity(s6);
                if(entityplayermp != null)
                {
                    entityplayermp.field_421_a.func_43_c("Banned by admin");
                }
            } else
            if(s.toLowerCase().startsWith("pardon "))
            {
                String s7 = s.substring(s.indexOf(" ")).trim();
                configManager.unbanPlayer(s7);
                func_6014_a(s1, (new StringBuilder()).append("Pardoning ").append(s7).toString());
            } else
            if(s.toLowerCase().startsWith("kick "))
            {
                String s8 = s.substring(s.indexOf(" ")).trim();
                EntityPlayerMP entityplayermp1 = null;
                for(int i = 0; i < configManager.playerEntities.size(); i++)
                {
                    EntityPlayerMP entityplayermp5 = (EntityPlayerMP)configManager.playerEntities.get(i);
                    if(entityplayermp5.username.equalsIgnoreCase(s8))
                    {
                        entityplayermp1 = entityplayermp5;
                    }
                }

                if(entityplayermp1 != null)
                {
                    entityplayermp1.field_421_a.func_43_c("Kicked by admin");
                    func_6014_a(s1, (new StringBuilder()).append("Kicking ").append(entityplayermp1.username).toString());
                } else
                {
                    icommandlistener.log((new StringBuilder()).append("Can't find user ").append(s8).append(". No kick.").toString());
                }
            } else
            if(s.toLowerCase().startsWith("tp "))
            {
                String as[] = s.split(" ");
                if(as.length == 3)
                {
                    EntityPlayerMP entityplayermp2 = configManager.getPlayerEntity(as[1]);
                    EntityPlayerMP entityplayermp3 = configManager.getPlayerEntity(as[2]);
                    if(entityplayermp2 == null)
                    {
                        icommandlistener.log((new StringBuilder()).append("Can't find user ").append(as[1]).append(". No tp.").toString());
                    } else
                    if(entityplayermp3 == null)
                    {
                        icommandlistener.log((new StringBuilder()).append("Can't find user ").append(as[2]).append(". No tp.").toString());
                    } else
                    {
                        entityplayermp2.field_421_a.func_41_a(entityplayermp3.posX, entityplayermp3.posY, entityplayermp3.posZ, entityplayermp3.rotationYaw, entityplayermp3.rotationPitch);
                        func_6014_a(s1, (new StringBuilder()).append("Teleporting ").append(as[1]).append(" to ").append(as[2]).append(".").toString());
                    }
                } else
                {
                    icommandlistener.log("Syntax error, please provice a source and a target.");
                }
            } else
            if(s.toLowerCase().startsWith("tpcord "))
            {
                String as[] = s.split(" ");
                if(as.length == 5)
                {
                    EntityPlayerMP entityplayermp2 = configManager.getPlayerEntity(as[1]);
                    int x = Integer.parseInt(as[2]);
                    int y = Integer.parseInt(as[3]);
                    int z = Integer.parseInt(as[4]);

                  //  EntityPlayerMP entityplayermp3 = configManager.getPlayerEntity(as[2]);
                    if(entityplayermp2 == null)
                    {
                        icommandlistener.log((new StringBuilder()).append("Can't find user ").append(as[1]).append(". No tp.").toString());
                    } else

                    {
                        entityplayermp2.field_421_a.func_41_a(x, y, z, entityplayermp2.rotationYaw, entityplayermp2.rotationPitch);
                        func_6014_a(s1, (new StringBuilder()).append("Teleporting ").append(as[1]).append(" to ").append(as[2]).append(".").toString());
                    }
                } else
                {
                    icommandlistener.log("Syntax error, please provice a source and 3 numbers for cordinates");
                }
            } else
            if(s.toLowerCase().startsWith("give "))
            {
                String as1[] = s.split(" ");
                if(as1.length != 3 && as1.length != 4)
                {
                    return;
                }
                String s9 = as1[1];
                EntityPlayerMP entityplayermp4 = configManager.getPlayerEntity(s9);
                if(entityplayermp4 != null)
                {
                    try
                    {
                        NameIDMappings nameIDMappings = new NameIDMappings();
                        int j = nameIDMappings.GetIDForNamespacedBlockName(as1[2]); // This is literally the only change between give and giveID
                       // logger.info("Inputed name:"+as1[2]);
                       // logger.info("Processed ID:"+j);
                        if(Item.itemsList[j] != null)
                        {
                            func_6014_a(s1, (new StringBuilder()).append("Giving ").append(entityplayermp4.username).append(" some ").append(as1[2]).toString());
                            int k = 1;
                            if(as1.length > 3)
                            {
                                k = func_6020_b(as1[3], 1);
                            }
                            if(k < 1)
                            {
                                k = 1;
                            }
                            if(k > 64)
                            {
                                k = 64;
                            }
                            entityplayermp4.func_161_a(new ItemStack(j, k));
                        } else
                        {
                            icommandlistener.log((new StringBuilder()).append("There's no item with name ").append(j).toString());
                        }
                    }
                    catch(NumberFormatException numberformatexception)
                    {
                        icommandlistener.log((new StringBuilder()).append("There's no item with name ").append(as1[2]).toString());
                    }
                } else
                {
                    icommandlistener.log((new StringBuilder()).append("Can't find user ").append(s9).toString());
                }
            } else
            if(s.toLowerCase().startsWith("giveid "))
            {
                String as1[] = s.split(" ");
                if(as1.length != 3 && as1.length != 4)
                {
                    return;
                }
                String s9 = as1[1];
                EntityPlayerMP entityplayermp4 = configManager.getPlayerEntity(s9);
                if(entityplayermp4 != null)
                {
                    try
                    {
                        int j = Integer.parseInt(as1[2]);
                        if(Item.itemsList[j] != null)
                        {
                            func_6014_a(s1, (new StringBuilder()).append("Giving ").append(entityplayermp4.username).append(" some ").append(j).toString());
                            int k = 1;
                            if(as1.length > 3)
                            {
                                k = func_6020_b(as1[3], 1);
                            }
                            if(k < 1)
                            {
                                k = 1;
                            }
                            if(k > 64)
                            {
                                k = 64;
                            }
                            entityplayermp4.func_161_a(new ItemStack(j, k));
                        } else
                        {
                            icommandlistener.log((new StringBuilder()).append("There's no item with id ").append(j).toString());
                        }
                    }
                    catch(NumberFormatException numberformatexception)
                    {
                        icommandlistener.log((new StringBuilder()).append("There's no item with id ").append(as1[2]).toString());
                    }
                } else
                {
                    icommandlistener.log((new StringBuilder()).append("Can't find user ").append(s9).toString());
                }
            } else
            if(s.toLowerCase().startsWith("say "))
            {
                s = s.substring(s.indexOf(" ")).trim();
                logger.info((new StringBuilder()).append("[").append(s1).append("] ").append(s).toString());
                configManager.sendPacketToAllPlayers(new Packet3Chat((new StringBuilder()).append("\247d[Server] ").append(s).toString()));
            } else
            if(s.toLowerCase().startsWith("tell "))
            {
                String as2[] = s.split(" ");
                if(as2.length >= 3)
                {
                    s = s.substring(s.indexOf(" ")).trim();
                    s = s.substring(s.indexOf(" ")).trim();
                    logger.info((new StringBuilder()).append("[").append(s1).append("->").append(as2[1]).append("] ").append(s).toString());
                    s = (new StringBuilder()).append("\2477").append(s1).append(" whispers ").append(s).toString();
                    logger.info(s);
                    if(!configManager.sendPacketToPlayer(as2[1], new Packet3Chat(s)))
                    {
                        icommandlistener.log("There's no player by that name online.");
                    }
                }
            } else
            {
                logger.info("Unknown console command. Type \"help\" for help.");
            }
        } while(true);
    }

    private void func_6014_a(String s, String s1)
    {
        String s2 = (new StringBuilder()).append(s).append(": ").append(s1).toString();
        configManager.sendChatMessageToAllPlayers((new StringBuilder()).append("\2477(").append(s2).append(")").toString());
        logger.info(s2);
    }

    private int func_6020_b(String s, int i)
    {
        try
        {
            return Integer.parseInt(s);
        }
        catch(NumberFormatException numberformatexception)
        {
            return i;
        }
    }

    public void func_6022_a(IUpdatePlayerListBox iupdateplayerlistbox)
    {
        field_9010_p.add(iupdateplayerlistbox);
    }

    public static void main(String args[])
    {
        try
        {
            MinecraftServer minecraftserver = new MinecraftServer();
            if(!GraphicsEnvironment.isHeadless() && (args.length <= 0 || !args[0].equals("nogui")))
            {
                ServerGUI.initGui(minecraftserver);
            }
            (new ThreadServerApplication("Server thread", minecraftserver)).start();
        }
        catch(Exception exception)
        {
            logger.log(Level.SEVERE, "Failed to start the minecraft server", exception);
        }
    }

    public File getFile(String s)
    {
        return new File(s);
    }

    public void log(String s)
    {
        logger.info(s);
    }

    public String getUsername()
    {
        return "CONSOLE";
    }

    public static boolean func_6015_a(MinecraftServer minecraftserver)
    {
        return minecraftserver.field_6025_n;
    }

    public static Logger logger = Logger.getLogger("Minecraft");
    public static HashMap field_6037_b = new HashMap();
    public NetworkListenThread field_6036_c;
    public PropertyManager propertyManagerObj;
    public WorldServer worldMngr;
    public ServerConfigurationManager configManager;
    private boolean field_6025_n;
    public boolean field_6032_g;
    int field_9014_h;
    public String field_9013_i;
    public int field_9012_j;
    private java.util.List field_9010_p;
    private java.util.List commands;
    public EntityTracker field_6028_k;
    public boolean onlineMode;
    public boolean noAnimals;
    public boolean field_9011_n;

}
