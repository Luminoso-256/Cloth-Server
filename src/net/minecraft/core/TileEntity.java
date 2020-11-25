package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.HashMap;
import java.util.Map;

public class TileEntity
{

    public TileEntity()
    {
    }

    private static void addMapping(Class class1, String s)
    {
        if(classToNameMap.containsKey(s))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Duplicate id: ").append(s).toString());
        } else
        {
            nameToClassMap.put(s, class1);
            classToNameMap.put(class1, s);
            return;
        }
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        xCoord = nbttagcompound.getInteger("x");
        yCoord = nbttagcompound.getInteger("y");
        zCoord = nbttagcompound.getInteger("z");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        String s = (String)classToNameMap.get(getClass());
        if(s == null)
        {
            throw new RuntimeException((new StringBuilder()).append(getClass()).append(" is missing a mapping! This is a bug!").toString());
        } else
        {
            nbttagcompound.setString("id", s);
            nbttagcompound.setInteger("x", xCoord);
            nbttagcompound.setInteger("y", yCoord);
            nbttagcompound.setInteger("z", zCoord);
            return;
        }
    }

    public void updateEntity()
    {
    }

    public static TileEntity createAndLoadEntity(NBTTagCompound nbttagcompound)
    {
        TileEntity tileentity = null;
        try
        {
            Class class1 = (Class)nameToClassMap.get(nbttagcompound.getString("id"));
            if(class1 != null)
            {
                tileentity = (TileEntity)class1.newInstance();
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        if(tileentity != null)
        {
            tileentity.readFromNBT(nbttagcompound);
        } else
        {
            System.out.println((new StringBuilder()).append("Skipping TileEntity with id ").append(nbttagcompound.getString("id")).toString());
        }
        return tileentity;
    }

    public void func_183_c()
    {
        worldObj.func_515_b(xCoord, yCoord, zCoord, this);
    }

    static Class _mthclass$(String s)
    {
        try
        {
            return Class.forName(s);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private static Map nameToClassMap = new HashMap();
    private static Map classToNameMap = new HashMap();
    public World worldObj;
    public int xCoord;
    public int yCoord;
    public int zCoord;

    static 
    {
        addMapping(TileEntityFurnace.class, "Furnace");
        addMapping(TileEntityChest.class, "Chest");
        addMapping(TileEntitySign.class, "Sign");
        addMapping(TileEntityMobSpawner.class, "MobSpawner");
    }
}
