package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.net.URL;

class ThreadLoginVerifier extends Thread
{

    ThreadLoginVerifier(NetLoginHandler netloginhandler, Packet1Login packet1login)
    {
        loginHandler = netloginhandler;
        loginPacket = packet1login;
    }

    public void run()
    {
        try
        {
            String s = NetLoginHandler.getServerId(loginHandler);
            URL url = new URL((new StringBuilder()).append("http://www.minecraft.net/game/checkserver.jsp?user=").append(loginPacket.username).append("&serverId=").append(s).toString());
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(url.openStream()));
            String s1 = bufferedreader.readLine();
            bufferedreader.close();
            System.out.println((new StringBuilder()).append("THE REPLY IS ").append(s1).toString());
            if(s1.equals("YES"))
            {
                NetLoginHandler.setLoginPacket(loginHandler, loginPacket);
            } else
            {
                loginHandler.kickUser("Failed to verify username!");
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    final Packet1Login loginPacket; /* synthetic field */
    final NetLoginHandler loginHandler; /* synthetic field */
}
