package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.File;
import java.util.*;
import net.minecraft.server.MinecraftServer;

public class WorldServer extends World
{

    public WorldServer(MinecraftServer minecraftserver, File file, String s, int i)
    {
        super(file, s, (new Random()).nextLong(), WorldProvider.func_4091_a(i));
        field_819_z = false;
        field_6159_E = new MCHashTable();
        field_6160_D = minecraftserver;
    }

    public void tick()
    {
        super.tick();
    }

    public void func_4074_a(Entity entity, boolean flag)
    {
        if(!field_6160_D.noAnimals && (entity instanceof EntityAnimals))
        {
            entity.setEntityDead();
        }
        if(entity.field_328_f == null || !(entity.field_328_f instanceof EntityPlayer))
        {
            super.func_4074_a(entity, flag);
        }
    }

    public void func_12017_b(Entity entity, boolean flag)
    {
        super.func_4074_a(entity, flag);
    }

    protected IChunkProvider func_4076_a(File file)
    {
        field_821 = new ChunkProviderServer(this, worldProvider.getChunkLoader(file), worldProvider.getChunkProvider());
        return field_821;
    }

    public List func_532_d(int i, int j, int k, int l, int i1, int j1)
    {
        ArrayList arraylist = new ArrayList();
        for(int k1 = 0; k1 < field_814_b.size(); k1++)
        {
            TileEntity tileentity = (TileEntity)field_814_b.get(k1);
            if(tileentity.xCoord >= i && tileentity.yCoord >= j && tileentity.zCoord >= k && tileentity.xCoord < l && tileentity.yCoord < i1 && tileentity.zCoord < j1)
            {
                arraylist.add(tileentity);
            }
        }

        return arraylist;
    }

    public boolean func_6157_a(EntityPlayer entityplayer, int i, int j, int k)
    {
        int l = (int)MathHelper.abs(i - spawnX);
        int i1 = (int)MathHelper.abs(k - spawnZ);
        if(l > i1)
        {
            i1 = l;
        }
        return i1 > 16 || field_6160_D.configManager.isOp(entityplayer.username);
    }

    protected void func_479_b(Entity entity)
    {
        super.func_479_b(entity);
        field_6159_E.addKey(entity.field_331_c, entity);
    }

    protected void func_531_c(Entity entity)
    {
        super.func_531_c(entity);
        field_6159_E.removeObject(entity.field_331_c);
    }

    public Entity func_6158_a(int i)
    {
        return (Entity)field_6159_E.lookup(i);
    }

    public void func_9206_a(Entity entity, byte byte0)
    {
        Packet38 packet38 = new Packet38(entity.field_331_c, byte0);
        field_6160_D.field_6028_k.func_609_a(entity, packet38);
    }

    public Explosion func_12015_a(Entity entity, double d, double d1, double d2, 
            float f, boolean flag)
    {
        Explosion explosion = super.func_12015_a(entity, d, d1, d2, f, flag);
        field_6160_D.configManager.func_12022_a(d, d1, d2, 64D, new Packet60(d, d1, d2, f, explosion.field_12025_g));
        return explosion;
    }

    public ChunkProviderServer field_821;
    public boolean field_819_z;
    public boolean field_816_A;
    private MinecraftServer field_6160_D;
    private MCHashTable field_6159_E;
}
