package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.MinecraftServer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class ServerWindowAdapter extends WindowAdapter {

    final MinecraftServer mcServer; /* synthetic field */

    ServerWindowAdapter(MinecraftServer minecraftserver) {
        mcServer = minecraftserver;
    }

    public void windowClosing(WindowEvent windowevent) {
        mcServer.func_6016_a();
        while (!mcServer.serverRunning) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException interruptedexception) {
                interruptedexception.printStackTrace();
            }
        }
        System.exit(0);
    }
}
