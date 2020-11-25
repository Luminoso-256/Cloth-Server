package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.MinecraftServer;

public class NetworkListenThread
{

    public NetworkListenThread(MinecraftServer minecraftserver, InetAddress inetaddress, int i) throws IOException
    {
        field_973_b = false;
        field_977_f = 0;
        field_976_g = new ArrayList();
        field_975_h = new ArrayList();
        mcServer = minecraftserver;
        field_979_d = new ServerSocket(i, 0, inetaddress);
        field_979_d.setPerformancePreferences(0, 2, 1);
        field_973_b = true;
        field_978_e = new NetworkAcceptThread(this, "Listen thread", minecraftserver);
        field_978_e.start();
    }

    public void func_4108_a(NetServerHandler netserverhandler)
    {
        field_975_h.add(netserverhandler);
    }

    private void func_717_a(NetLoginHandler netloginhandler)
    {
        if(netloginhandler == null)
        {
            throw new IllegalArgumentException("Got null pendingconnection!");
        } else
        {
            field_976_g.add(netloginhandler);
            return;
        }
    }

    public void func_715_a()
    {
        for(int i = 0; i < field_976_g.size(); i++)
        {
            NetLoginHandler netloginhandler = (NetLoginHandler)field_976_g.get(i);
            try
            {
                netloginhandler.tryLogin();
            }
            catch(Exception exception)
            {
                netloginhandler.kickUser("Internal server error");
                logger.log(Level.WARNING, (new StringBuilder()).append("Failed to handle packet: ").append(exception).toString(), exception);
            }
            if(netloginhandler.finishedProcessing)
            {
                field_976_g.remove(i--);
            }
        }

        for(int j = 0; j < field_975_h.size(); j++)
        {
            NetServerHandler netserverhandler = (NetServerHandler)field_975_h.get(j);
            try
            {
                netserverhandler.func_42_a();
            }
            catch(Exception exception1)
            {
                logger.log(Level.WARNING, (new StringBuilder()).append("Failed to handle packet: ").append(exception1).toString(), exception1);
                netserverhandler.func_43_c("Internal server error");
            }
            if(netserverhandler.field_18_c)
            {
                field_975_h.remove(j--);
            }
        }

    }

    static ServerSocket func_713_a(NetworkListenThread networklistenthread)
    {
        return networklistenthread.field_979_d;
    }

    static int func_712_b(NetworkListenThread networklistenthread)
    {
        return networklistenthread.field_977_f++;
    }

    static void func_716_a(NetworkListenThread networklistenthread, NetLoginHandler netloginhandler)
    {
        networklistenthread.func_717_a(netloginhandler);
    }

    public static Logger logger = Logger.getLogger("Minecraft");
    private ServerSocket field_979_d;
    private Thread field_978_e;
    public volatile boolean field_973_b;
    private int field_977_f;
    private ArrayList field_976_g;
    private ArrayList field_975_h;
    public MinecraftServer mcServer;

}
