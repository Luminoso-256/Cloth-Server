package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.MinecraftServer;

public class ThreadSleepForever extends Thread {

    final MinecraftServer mc; /* synthetic field */

    public ThreadSleepForever(MinecraftServer minecraftserver) {
        mc = minecraftserver;
        setDaemon(true);
        start();
    }

    public void run() {
        do {
            try {
                Thread.sleep(0x7fffffffL);
            } catch (InterruptedException interruptedexception) {
            }
        } while (true);
    }
}
