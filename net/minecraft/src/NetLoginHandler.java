package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public class NetLoginHandler extends NetHandler
{

    public NetLoginHandler(MinecraftServer minecraftserver, Socket socket, String s) throws IOException
    {
        finishedProcessing = false;
        field_9005_f = 0;
        username = null;
        field_9004_h = null;
        serverId = "";
        mcServer = minecraftserver;
        netManager = new NetworkManager(socket, s, this);
    }

    public void tryLogin()
    {
        if(field_9004_h != null)
        {
            doLogin(field_9004_h);
            field_9004_h = null;
        }
        if(field_9005_f++ == 600)
        {
            kickUser("Took too long to log in");
        } else
        {
            netManager.processReadPackets();
        }
    }

    public void kickUser(String s)
    {
        try
        {
            logger.info((new StringBuilder()).append("Disconnecting ").append(getUserAndIPString()).append(": ").append(s).toString());
            netManager.addToSendQueue(new Packet255KickDisconnect(s));
            netManager.serverShutdown();
            finishedProcessing = true;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void handleHandshake(Packet2Handshake packet2handshake)
    {
        if(mcServer.onlineMode)
        {
            serverId = Long.toHexString(rand.nextLong());
            netManager.addToSendQueue(new Packet2Handshake(serverId));
        } else
        {
            netManager.addToSendQueue(new Packet2Handshake("-"));
        }
    }

    public void handleLogin(Packet1Login packet1login)
    {
        username = packet1login.username;
        if(packet1login.protocolVersion != 6)
        {
            if(packet1login.protocolVersion > 6)
            {
                kickUser("Outdated server!");
            } else
            {
                kickUser("Outdated client!");
            }
            return;
        }
        if(!mcServer.onlineMode)
        {
            doLogin(packet1login);
        } else
        {
            (new ThreadLoginVerifier(this, packet1login)).start();
        }
    }

    public void doLogin(Packet1Login packet1login)
    {
        EntityPlayerMP entityplayermp = mcServer.configManager.login(this, packet1login.username, packet1login.password);
        if(entityplayermp != null)
        {
            logger.info((new StringBuilder()).append(getUserAndIPString()).append(" logged in with entity id ").append(entityplayermp.field_331_c).toString());
            NetServerHandler netserverhandler = new NetServerHandler(mcServer, netManager, entityplayermp);
            netserverhandler.sendPacket(new Packet1Login("", "", entityplayermp.field_331_c, mcServer.worldMngr.randomSeed, (byte)mcServer.worldMngr.field_4272_q.field_6165_g));
            netserverhandler.sendPacket(new Packet6SpawnPosition(mcServer.worldMngr.spawnX, mcServer.worldMngr.spawnY, mcServer.worldMngr.spawnZ));
            mcServer.configManager.sendPacketToAllPlayers(new Packet3Chat((new StringBuilder()).append("\247e").append(entityplayermp.username).append(" joined the game.").toString()));
            mcServer.configManager.playerLoggedIn(entityplayermp);
            netserverhandler.func_41_a(entityplayermp.posX, entityplayermp.posY, entityplayermp.posZ, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
            netserverhandler.func_40_d();
            mcServer.field_6036_c.func_4108_a(netserverhandler);
            netserverhandler.sendPacket(new Packet4UpdateTime(mcServer.worldMngr.worldTime));
        }
        finishedProcessing = true;
    }

    public void handleErrorMessage(String s)
    {
        logger.info((new StringBuilder()).append(getUserAndIPString()).append(" lost connection").toString());
        finishedProcessing = true;
    }

    public void func_6001_a(Packet packet)
    {
        kickUser("Protocol error");
    }

    public String getUserAndIPString()
    {
        if(username != null)
        {
            return (new StringBuilder()).append(username).append(" [").append(netManager.getRemoteAddress().toString()).append("]").toString();
        } else
        {
            return netManager.getRemoteAddress().toString();
        }
    }

    static String getServerId(NetLoginHandler netloginhandler)
    {
        return netloginhandler.serverId;
    }

    static Packet1Login setLoginPacket(NetLoginHandler netloginhandler, Packet1Login packet1login)
    {
        return netloginhandler.field_9004_h = packet1login;
    }

    public static Logger logger = Logger.getLogger("Minecraft");
    private static Random rand = new Random();
    public NetworkManager netManager;
    public boolean finishedProcessing;
    private MinecraftServer mcServer;
    private int field_9005_f;
    private String username;
    private Packet1Login field_9004_h;
    private String serverId;

}
