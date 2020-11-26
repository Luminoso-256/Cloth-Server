package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class MetadataChunkBlock
{

    public MetadataChunkBlock(EnumSkyBlock enumskyblock, int i, int j, int k, int l, int i1, int j1)
    {
        field_957_a = enumskyblock;
        field_956_b = i;
        field_962_c = j;
        field_961_d = k;
        field_960_e = l;
        field_959_f = i1;
        field_958_g = j1;
    }

    public void func_4107_a(World world)
    {
        int i = (field_960_e - field_956_b) + 1;
        int j = (field_959_f - field_962_c) + 1;
        int k = (field_958_g - field_961_d) + 1;
        int l = i * j * k;
        if(l > 32768)
        {
            return;
        }
        for(int i1 = field_956_b; i1 <= field_960_e; i1++)
        {
            for(int j1 = field_961_d; j1 <= field_958_g; j1++)
            {
                if(!world.func_530_e(i1, 0, j1))
                {
                    continue;
                }
                for(int k1 = field_962_c; k1 <= field_959_f; k1++)
                {
                    if(k1 < 0 || k1 >= 128)
                    {
                        continue;
                    }
                    int l1 = world.getSavedLightValue(field_957_a, i1, k1, j1);
                    int i2 = 0;
                    int j2 = world.getBlockId(i1, k1, j1);
                    int k2 = Block.lightOpacity[j2];
                    if(k2 == 0)
                    {
                        k2 = 1;
                    }
                    int l2 = 0;
                    if(field_957_a == EnumSkyBlock.Sky)
                    {
                        if(world.canExistingBlockSeeTheSky(i1, k1, j1))
                        {
                            l2 = 15;
                        }
                    } else
                    if(field_957_a == EnumSkyBlock.Block)
                    {
                        l2 = Block.lightValue[j2];
                    }
                    if(k2 >= 15 && l2 == 0)
                    {
                        i2 = 0;
                    } else
                    {
                        int i3 = world.getSavedLightValue(field_957_a, i1 - 1, k1, j1);
                        int k3 = world.getSavedLightValue(field_957_a, i1 + 1, k1, j1);
                        int l3 = world.getSavedLightValue(field_957_a, i1, k1 - 1, j1);
                        int i4 = world.getSavedLightValue(field_957_a, i1, k1 + 1, j1);
                        int j4 = world.getSavedLightValue(field_957_a, i1, k1, j1 - 1);
                        int k4 = world.getSavedLightValue(field_957_a, i1, k1, j1 + 1);
                        i2 = i3;
                        if(k3 > i2)
                        {
                            i2 = k3;
                        }
                        if(l3 > i2)
                        {
                            i2 = l3;
                        }
                        if(i4 > i2)
                        {
                            i2 = i4;
                        }
                        if(j4 > i2)
                        {
                            i2 = j4;
                        }
                        if(k4 > i2)
                        {
                            i2 = k4;
                        }
                        i2 -= k2;
                        if(i2 < 0)
                        {
                            i2 = 0;
                        }
                        if(l2 > i2)
                        {
                            i2 = l2;
                        }
                    }
                    if(l1 == i2)
                    {
                        continue;
                    }
                    world.setLightValue(field_957_a, i1, k1, j1, i2);
                    int j3 = i2 - 1;
                    if(j3 < 0)
                    {
                        j3 = 0;
                    }
                    world.neighborLightPropagationChanged(field_957_a, i1 - 1, k1, j1, j3);
                    world.neighborLightPropagationChanged(field_957_a, i1, k1 - 1, j1, j3);
                    world.neighborLightPropagationChanged(field_957_a, i1, k1, j1 - 1, j3);
                    if(i1 + 1 >= field_960_e)
                    {
                        world.neighborLightPropagationChanged(field_957_a, i1 + 1, k1, j1, j3);
                    }
                    if(k1 + 1 >= field_959_f)
                    {
                        world.neighborLightPropagationChanged(field_957_a, i1, k1 + 1, j1, j3);
                    }
                    if(j1 + 1 >= field_958_g)
                    {
                        world.neighborLightPropagationChanged(field_957_a, i1, k1, j1 + 1, j3);
                    }
                }

            }

        }

    }

    public boolean func_692_a(int i, int j, int k, int l, int i1, int j1)
    {
        if(i >= field_956_b && j >= field_962_c && k >= field_961_d && l <= field_960_e && i1 <= field_959_f && j1 <= field_958_g)
        {
            return true;
        }
        int k1 = 1;
        if(i >= field_956_b - k1 && j >= field_962_c - k1 && k >= field_961_d - k1 && l <= field_960_e + k1 && i1 <= field_959_f + k1 && j1 <= field_958_g + k1)
        {
            int l1 = field_960_e - field_956_b;
            int i2 = field_959_f - field_962_c;
            int j2 = field_958_g - field_961_d;
            if(i > field_956_b)
            {
                i = field_956_b;
            }
            if(j > field_962_c)
            {
                j = field_962_c;
            }
            if(k > field_961_d)
            {
                k = field_961_d;
            }
            if(l < field_960_e)
            {
                l = field_960_e;
            }
            if(i1 < field_959_f)
            {
                i1 = field_959_f;
            }
            if(j1 < field_958_g)
            {
                j1 = field_958_g;
            }
            int k2 = l - i;
            int l2 = i1 - j;
            int i3 = j1 - k;
            int j3 = l1 * i2 * j2;
            int k3 = k2 * l2 * i3;
            if(k3 - j3 <= 2)
            {
                field_956_b = i;
                field_962_c = j;
                field_961_d = k;
                field_960_e = l;
                field_959_f = i1;
                field_958_g = j1;
                return true;
            }
        }
        return false;
    }

    public final EnumSkyBlock field_957_a;
    public int field_956_b;
    public int field_962_c;
    public int field_961_d;
    public int field_960_e;
    public int field_959_f;
    public int field_958_g;
}
