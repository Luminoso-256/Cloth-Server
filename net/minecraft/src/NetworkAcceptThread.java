package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.IOException;
import java.net.ServerSocket;
import net.minecraft.server.MinecraftServer;

class NetworkAcceptThread extends Thread
{

    NetworkAcceptThread(NetworkListenThread networklistenthread, String s, MinecraftServer minecraftserver)
    {
        super(s);
        field_985_b = networklistenthread;
        mcServer = minecraftserver;
    }

    public void run()
    {
        do
        {
            if(!field_985_b.field_973_b)
            {
                break;
            }
            try
            {
                java.net.Socket socket = NetworkListenThread.func_713_a(field_985_b).accept();
                if(socket != null)
                {
                    NetLoginHandler netloginhandler = new NetLoginHandler(mcServer, socket, (new StringBuilder()).append("Connection #").append(NetworkListenThread.func_712_b(field_985_b)).toString());
                    NetworkListenThread.func_716_a(field_985_b, netloginhandler);
                }
            }
            catch(IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        } while(true);
    }

    final MinecraftServer mcServer; /* synthetic field */
    final NetworkListenThread field_985_b; /* synthetic field */
}
