package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import net.minecraft.server.MinecraftServer;

public class ThreadCommandReader extends Thread
{

    public ThreadCommandReader(MinecraftServer minecraftserver)
    {
        mcServer = minecraftserver;
    }

    public void run()
    {
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        try
        {
            while(!mcServer.field_6032_g && MinecraftServer.func_6015_a(mcServer) && (s = bufferedreader.readLine()) != null) 
            {
                mcServer.addCommand(s, mcServer);
            }
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    final MinecraftServer mcServer; /* synthetic field */
}
