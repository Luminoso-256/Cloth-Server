package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.MinecraftServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadCommandReader extends Thread {

    final MinecraftServer mcServer; /* synthetic field */

    public ThreadCommandReader(MinecraftServer minecraftserver) {
        mcServer = minecraftserver;
    }

    public void run() {
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        try {
            while (!mcServer.serverRunning && MinecraftServer.func_6015_a(mcServer) && (s = bufferedreader.readLine()) != null) {
                mcServer.addCommand(s, mcServer);
            }
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }
}
