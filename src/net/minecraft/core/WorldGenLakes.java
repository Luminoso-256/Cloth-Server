package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class WorldGenLakes extends WorldGenerator
{

    public WorldGenLakes(int i)
    {
        field_15005_a = i;
    }

    public boolean generate(World world, Random random, int i, int j, int k)
    {
        i -= 8;
        for(k -= 8; j > 0 && world.getBlockId(i, j, k) == 0; j--) { }
        j -= 4;
        boolean aflag[] = new boolean[2048];
        int l = random.nextInt(4) + 4;
        for(int i1 = 0; i1 < l; i1++)
        {
            double d = random.nextDouble() * 6D + 3D;
            double d1 = random.nextDouble() * 4D + 2D;
            double d2 = random.nextDouble() * 6D + 3D;
            double d3 = random.nextDouble() * (16D - d - 2D) + 1.0D + d / 2D;
            double d4 = random.nextDouble() * (8D - d1 - 4D) + 2D + d1 / 2D;
            double d5 = random.nextDouble() * (16D - d2 - 2D) + 1.0D + d2 / 2D;
            for(int k3 = 1; k3 < 15; k3++)
            {
                for(int l3 = 1; l3 < 15; l3++)
                {
                    for(int i4 = 1; i4 < 7; i4++)
                    {
                        double d6 = ((double)k3 - d3) / (d / 2D);
                        double d7 = ((double)i4 - d4) / (d1 / 2D);
                        double d8 = ((double)l3 - d5) / (d2 / 2D);
                        double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                        if(d9 < 1.0D)
                        {
                            aflag[(k3 * 16 + l3) * 8 + i4] = true;
                        }
                    }

                }

            }

        }

        for(int j1 = 0; j1 < 16; j1++)
        {
            for(int i2 = 0; i2 < 16; i2++)
            {
                for(int l2 = 0; l2 < 8; l2++)
                {
                    boolean flag = !aflag[(j1 * 16 + i2) * 8 + l2] && (j1 < 15 && aflag[((j1 + 1) * 16 + i2) * 8 + l2] || j1 > 0 && aflag[((j1 - 1) * 16 + i2) * 8 + l2] || i2 < 15 && aflag[(j1 * 16 + (i2 + 1)) * 8 + l2] || i2 > 0 && aflag[(j1 * 16 + (i2 - 1)) * 8 + l2] || l2 < 7 && aflag[(j1 * 16 + i2) * 8 + (l2 + 1)] || l2 > 0 && aflag[(j1 * 16 + i2) * 8 + (l2 - 1)]);
                    if(!flag)
                    {
                        continue;
                    }
                    Material material = world.getBlockMaterial(i + j1, j + l2, k + i2);
                    if(l2 >= 4 && material.getIsLiquid())
                    {
                        return false;
                    }
                    if(l2 < 4 && !material.func_216_a() && world.getBlockId(i + j1, j + l2, k + i2) != field_15005_a)
                    {
                        return false;
                    }
                }

            }

        }

        for(int k1 = 0; k1 < 16; k1++)
        {
            for(int j2 = 0; j2 < 16; j2++)
            {
                for(int i3 = 0; i3 < 8; i3++)
                {
                    if(aflag[(k1 * 16 + j2) * 8 + i3])
                    {
                        world.setBlockWithNotify(i + k1, j + i3, k + j2, i3 < 4 ? field_15005_a : 0);
                    }
                }

            }

        }

        for(int l1 = 0; l1 < 16; l1++)
        {
            for(int k2 = 0; k2 < 16; k2++)
            {
                for(int j3 = 4; j3 < 8; j3++)
                {
                    if(aflag[(l1 * 16 + k2) * 8 + j3] && world.getBlockId(i + l1, (j + j3) - 1, k + k2) == Block.dirt.blockID && world.getSavedLightValue(EnumSkyBlock.Sky, i + l1, j + j3, k + k2) > 0)
                    {
                        world.setBlockWithNotify(i + l1, (j + j3) - 1, k + k2, Block.grass.blockID);
                    }
                }

            }

        }

        return true;
    }

    private int field_15005_a;
}
