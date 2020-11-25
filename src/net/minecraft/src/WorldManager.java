package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import src.net.minecraft.server.MinecraftServer;

public class WorldManager
    implements IWorldAccess
{

    public WorldManager(MinecraftServer minecraftserver)
    {
        mcServer = minecraftserver;
    }

    public void spawnParticle(String s, double d, double d1, double d2, 
            double d3, double d4, double d5)
    {
    }

    public void func_681_a(Entity entity)
    {
        mcServer.field_6028_k.func_611_a(entity);
    }

    public void func_688_b(Entity entity)
    {
        mcServer.field_6028_k.func_610_b(entity);
    }

    public void playSound(String s, double d, double d1, double d2, 
            float f, float f1)
    {
    }

    public void func_685_a(int i, int j, int k, int l, int i1, int j1)
    {
    }

    public void func_684_a()
    {
    }

    public void func_683_a(int i, int j, int k)
    {
        mcServer.configManager.func_622_a(i, j, k);
    }

    public void playRecord(String s, int i, int j, int k)
    {
    }

    public void func_686_a(int i, int j, int k, TileEntity tileentity)
    {
        mcServer.configManager.sentTileEntityToPlayer(i, j, k, tileentity);
    }

    private MinecraftServer mcServer;
}
