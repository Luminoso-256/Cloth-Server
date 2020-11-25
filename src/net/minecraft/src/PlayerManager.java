package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;
import java.util.List;
import src.net.minecraft.MinecraftServer;

public class PlayerManager
{

    public PlayerManager(MinecraftServer minecraftserver)
    {
        field_9216_a = new ArrayList();
        field_9215_b = new MCHashTable2();
        field_833_c = new ArrayList();
        mcServer = minecraftserver;
    }

    public void func_538_a()
    {
        for(int i = 0; i < field_833_c.size(); i++)
        {
            ((PlayerInstance)field_833_c.get(i)).func_777_a();
        }

        field_833_c.clear();
    }

    private PlayerInstance func_537_a(int i, int j, boolean flag)
    {
        long l = (long)i + 0x7fffffffL | (long)j + 0x7fffffffL << 32;
        PlayerInstance playerinstance = (PlayerInstance)field_9215_b.func_677_a(l);
        if(playerinstance == null && flag)
        {
            playerinstance = new PlayerInstance(this, i, j);
            field_9215_b.func_675_a(l, playerinstance);
        }
        return playerinstance;
    }

    public void func_541_a(Packet packet, int i, int j, int k)
    {
        int l = i >> 4;
        int i1 = k >> 4;
        PlayerInstance playerinstance = func_537_a(l, i1, false);
        if(playerinstance != null)
        {
            playerinstance.func_776_a(packet);
        }
    }

    public void func_535_a(int i, int j, int k)
    {
        int l = i >> 4;
        int i1 = k >> 4;
        PlayerInstance playerinstance = func_537_a(l, i1, false);
        if(playerinstance != null)
        {
            playerinstance.func_775_a(i & 0xf, j, k & 0xf);
        }
    }

    public void func_9214_a(EntityPlayerMP entityplayermp)
    {
        int i = (int)entityplayermp.posX >> 4;
        int j = (int)entityplayermp.posZ >> 4;
        entityplayermp.field_9155_d = entityplayermp.posX;
        entityplayermp.field_9154_e = entityplayermp.posZ;
        for(int k = i - 10; k <= i + 10; k++)
        {
            for(int l = j - 10; l <= j + 10; l++)
            {
                func_537_a(k, l, true).func_779_a(entityplayermp);
            }

        }

        field_9216_a.add(entityplayermp);
    }

    public void func_9213_b(EntityPlayerMP entityplayermp)
    {
        int i = (int)entityplayermp.field_9155_d >> 4;
        int j = (int)entityplayermp.field_9154_e >> 4;
        for(int k = i - 10; k <= i + 10; k++)
        {
            for(int l = j - 10; l <= j + 10; l++)
            {
                PlayerInstance playerinstance = func_537_a(k, l, false);
                if(playerinstance != null)
                {
                    playerinstance.func_778_b(entityplayermp);
                }
            }

        }

        field_9216_a.remove(entityplayermp);
    }

    private boolean func_544_a(int i, int j, int k, int l)
    {
        int i1 = i - k;
        int j1 = j - l;
        if(i1 < -10 || i1 > 10)
        {
            return false;
        }
        return j1 >= -10 && j1 <= 10;
    }

    public void func_543_c(EntityPlayerMP entityplayermp)
    {
        int i = (int)entityplayermp.posX >> 4;
        int j = (int)entityplayermp.posZ >> 4;
        double d = entityplayermp.field_9155_d - entityplayermp.posX;
        double d1 = entityplayermp.field_9154_e - entityplayermp.posZ;
        double d2 = d * d + d1 * d1;
        if(d2 < 64D)
        {
            return;
        }
        int k = (int)entityplayermp.field_9155_d >> 4;
        int l = (int)entityplayermp.field_9154_e >> 4;
        int i1 = i - k;
        int j1 = j - l;
        if(i1 == 0 && j1 == 0)
        {
            return;
        }
        for(int k1 = i - 10; k1 <= i + 10; k1++)
        {
            for(int l1 = j - 10; l1 <= j + 10; l1++)
            {
                if(!func_544_a(k1, l1, k, l))
                {
                    func_537_a(k1, l1, true).func_779_a(entityplayermp);
                }
                if(func_544_a(k1 - i1, l1 - j1, i, j))
                {
                    continue;
                }
                PlayerInstance playerinstance = func_537_a(k1 - i1, l1 - j1, false);
                if(playerinstance != null)
                {
                    playerinstance.func_778_b(entityplayermp);
                }
            }

        }

        entityplayermp.field_9155_d = entityplayermp.posX;
        entityplayermp.field_9154_e = entityplayermp.posZ;
    }

    public int func_542_b()
    {
        return 144;
    }

    static MinecraftServer getMinecraftServer(PlayerManager playermanager)
    {
        return playermanager.mcServer;
    }

    static MCHashTable2 func_539_b(PlayerManager playermanager)
    {
        return playermanager.field_9215_b;
    }

    static List func_533_c(PlayerManager playermanager)
    {
        return playermanager.field_833_c;
    }

    private List field_9216_a;
    private MCHashTable2 field_9215_b;
    private List field_833_c;
    private MinecraftServer mcServer;
}
