package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.util.logging.Logger;

public class PlayerNBTManager
{

    public PlayerNBTManager(File file)
    {
        worldFile = file;
        file.mkdir();
    }

    public void writePlayerData(EntityPlayerMP entityplayermp)
    {
        try
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            entityplayermp.writeToNBT(nbttagcompound);
            File file = new File(worldFile, "_tmp_.dat");
            File file1 = new File(worldFile, (new StringBuilder()).append(entityplayermp.username).append(".dat").toString());
            CompressedStreamTools.func_769_a(nbttagcompound, new FileOutputStream(file));
            if(file1.exists())
            {
                file1.delete();
            }
            file.renameTo(file1);
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to save player data for ").append(entityplayermp.username).toString());
        }
    }

    public void readPlayerData(EntityPlayerMP entityplayermp)
    {
        try
        {
            File file = new File(worldFile, (new StringBuilder()).append(entityplayermp.username).append(".dat").toString());
            if(file.exists())
            {
                NBTTagCompound nbttagcompound = CompressedStreamTools.func_770_a(new FileInputStream(file));
                if(nbttagcompound != null)
                {
                    entityplayermp.readFromNBT(nbttagcompound);
                }
            }
        }
        catch(Exception exception)
        {
            logger.warning((new StringBuilder()).append("Failed to load player data for ").append(entityplayermp.username).toString());
        }
    }

    public static Logger logger = Logger.getLogger("Minecraft");
    private File worldFile;

}
