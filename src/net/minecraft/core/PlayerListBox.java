package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.MinecraftServer;

import javax.swing.*;
import java.util.Vector;

public class PlayerListBox extends JList
        implements IUpdatePlayerListBox {

    private MinecraftServer mcServer;
    private int updateCounter;

    public PlayerListBox(MinecraftServer minecraftserver) {
        updateCounter = 0;
        mcServer = minecraftserver;
        minecraftserver.func_6022_a(this);
    }

    public void update() {
        if (updateCounter++ % 20 == 0) {
            Vector vector = new Vector();
            for (int i = 0; i < mcServer.configManager.playerEntities.size(); i++) {
                vector.add(((EntityPlayerMP) mcServer.configManager.playerEntities.get(i)).username);
            }

            setListData(vector);
        }
    }
}
