package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;
import java.util.Vector;
import javax.swing.JList;
import src.net.minecraft.server.MinecraftServer;

public class PlayerListBox extends JList
    implements IUpdatePlayerListBox
{

    public PlayerListBox(MinecraftServer minecraftserver)
    {
        updateCounter = 0;
        mcServer = minecraftserver;
        minecraftserver.func_6022_a(this);
    }

    public void update()
    {
        if(updateCounter++ % 20 == 0)
        {
            Vector vector = new Vector();
            for(int i = 0; i < mcServer.configManager.playerEntities.size(); i++)
            {
                vector.add(((EntityPlayerMP)mcServer.configManager.playerEntities.get(i)).username);
            }

            setListData(vector);
        }
    }

    private MinecraftServer mcServer;
    private int updateCounter;
}
