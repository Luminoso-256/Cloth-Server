package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;

class PlayerInstance
{

    public PlayerInstance(PlayerManager playermanager, int i, int j)
    {
        field_1073_a = playermanager;
        field_1072_b = new ArrayList();
        field_1068_f = new short[10];
        field_1067_g = 0;
        field_1071_c = i;
        field_1070_d = j;
        field_1069_e = new ChunkCoordIntPair(i, j);
        PlayerManager.getMinecraftServer(playermanager).worldMngr.chunkProvider.loadChunk(i, j);
    }

    public void func_779_a(EntityPlayerMP entityplayermp)
    {
        if(field_1072_b.contains(entityplayermp))
        {
            throw new IllegalStateException((new StringBuilder()).append("Failed to add player. ").append(entityplayermp).append(" already is in chunk ").append(field_1071_c).append(", ").append(field_1070_d).toString());
        } else
        {



            entityplayermp.field_420_ah.add(field_1069_e);
            entityplayermp.field_421_a.sendPacket(new Packet50PreChunk(field_1069_e.field_152_a, field_1069_e.field_151_b, true));
            field_1072_b.add(entityplayermp);
            entityplayermp.field_422_ag.add(field_1069_e);
            return;
        }
    }

    public void func_778_b(EntityPlayerMP entityplayermp)
    {
        if(!field_1072_b.contains(entityplayermp))
        {
            (new IllegalStateException((new StringBuilder()).append("Failed to remove player. ").append(entityplayermp).append(" isn't in chunk ").append(field_1071_c).append(", ").append(field_1070_d).toString())).printStackTrace();
            return;
        }
        field_1072_b.remove(entityplayermp);
        if(field_1072_b.size() == 0)
        {
            long l = (long)field_1071_c + 0x7fffffffL | (long)field_1070_d + 0x7fffffffL << 32;
            PlayerManager.func_539_b(field_1073_a).func_670_b(l);
            if(field_1067_g > 0)
            {
                PlayerManager.func_533_c(field_1073_a).remove(this);
            }
            PlayerManager.getMinecraftServer(field_1073_a).worldMngr.chunkProvider.func_374_c(field_1071_c, field_1070_d);
        }
        entityplayermp.field_422_ag.remove(field_1069_e);
        if(entityplayermp.field_420_ah.contains(field_1069_e))
        {
            entityplayermp.field_421_a.sendPacket(new Packet50PreChunk(field_1071_c, field_1070_d, false));
        }
    }

    public void func_775_a(int i, int j, int k)
    {
        if(field_1067_g == 0)
        {
            PlayerManager.func_533_c(field_1073_a).add(this);
            field_1066_h = field_1065_i = i;
            field_1064_j = field_1063_k = j;
            field_1062_l = field_1061_m = k;
        }
        if(field_1066_h > i)
        {
            field_1066_h = i;
        }
        if(field_1065_i < i)
        {
            field_1065_i = i;
        }
        if(field_1064_j > j)
        {
            field_1064_j = j;
        }
        if(field_1063_k < j)
        {
            field_1063_k = j;
        }
        if(field_1062_l > k)
        {
            field_1062_l = k;
        }
        if(field_1061_m < k)
        {
            field_1061_m = k;
        }
        if(field_1067_g < 10)
        {
            short word0 = (short)(i << 12 | k << 8 | j);
            for(int l = 0; l < field_1067_g; l++)
            {
                if(field_1068_f[l] == word0)
                {
                    return;
                }
            }

            field_1068_f[field_1067_g++] = word0;
        }
    }

    public void func_776_a(Packet packet)
    {
        for(int i = 0; i < field_1072_b.size(); i++)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)field_1072_b.get(i);
            if(entityplayermp.field_420_ah.contains(field_1069_e))
            {
                entityplayermp.field_421_a.sendPacket(packet);
            }
        }

    }

    public void func_777_a()
    {
        if(field_1067_g == 0)
        {
            return;
        }
        if(field_1067_g == 1)
        {
            int i = field_1071_c * 16 + field_1066_h;
            int l = field_1064_j;
            int k1 = field_1070_d * 16 + field_1062_l;
            func_776_a(new Packet53BlockChange(i, l, k1, PlayerManager.getMinecraftServer(field_1073_a).worldMngr));
            if(Block.isBlockContainer[PlayerManager.getMinecraftServer(field_1073_a).worldMngr.getBlockId(i, l, k1)])
            {
                func_776_a(new Packet59ComplexEntity(i, l, k1, PlayerManager.getMinecraftServer(field_1073_a).worldMngr.getBlock(i, l, k1)));
            }
        } else
        if(field_1067_g == 10)
        {
            field_1064_j = (field_1064_j / 2) * 2;
            field_1063_k = (field_1063_k / 2 + 1) * 2;
            int j = field_1066_h + field_1071_c * 16;
            int i1 = field_1064_j;
            int l1 = field_1062_l + field_1070_d * 16;
            int j2 = (field_1065_i - field_1066_h) + 1;
            int l2 = (field_1063_k - field_1064_j) + 2;
            int i3 = (field_1061_m - field_1062_l) + 1;
            func_776_a(new Packet51MapChunk(j, i1, l1, j2, l2, i3, PlayerManager.getMinecraftServer(field_1073_a).worldMngr));
            List list = PlayerManager.getMinecraftServer(field_1073_a).worldMngr.func_532_d(j, i1, l1, j + j2, i1 + l2, l1 + i3);
            for(int j3 = 0; j3 < list.size(); j3++)
            {
                TileEntity tileentity = (TileEntity)list.get(j3);
                func_776_a(new Packet59ComplexEntity(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, tileentity));
            }

        } else
        {
            func_776_a(new Packet52MultiBlockChange(field_1071_c, field_1070_d, field_1068_f, field_1067_g, PlayerManager.getMinecraftServer(field_1073_a).worldMngr));
            for(int k = 0; k < field_1067_g; k++)
            {
                int j1 = field_1071_c * 16 + (field_1067_g >> 12 & 0xf);
                int i2 = field_1067_g & 0xff;
                int k2 = field_1070_d * 16 + (field_1067_g >> 8 & 0xf);
                if(Block.isBlockContainer[PlayerManager.getMinecraftServer(field_1073_a).worldMngr.getBlockId(j1, i2, k2)])
                {
                    func_776_a(new Packet59ComplexEntity(j1, i2, k2, PlayerManager.getMinecraftServer(field_1073_a).worldMngr.getBlock(j1, i2, k2)));
                }
            }

        }
        field_1067_g = 0;
    }

    private List field_1072_b;
    private int field_1071_c;
    private int field_1070_d;
    private ChunkCoordIntPair field_1069_e;
    private short field_1068_f[];
    private int field_1067_g;
    private int field_1066_h;
    private int field_1065_i;
    private int field_1064_j;
    private int field_1063_k;
    private int field_1062_l;
    private int field_1061_m;
    final PlayerManager field_1073_a; /* synthetic field */
}
