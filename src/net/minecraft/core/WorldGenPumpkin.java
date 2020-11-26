package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class WorldGenPumpkin extends WorldGenerator
{

    public WorldGenPumpkin()
    {
    }

    public boolean generate(World world, Random random, int i, int j, int k)
    {
        for(int l = 0; l < 64; l++)
        {
            int i1 = (i + random.nextInt(8)) - random.nextInt(8);
            int j1 = (j + random.nextInt(4)) - random.nextInt(4);
            int k1 = (k + random.nextInt(8)) - random.nextInt(8);
            if(world.getBlockId(i1, j1, k1) == 0 && world.getBlockId(i1, j1 - 1, k1) == Block.grass.blockID && Block.pumpkin.canPlaceBlockAt(world, i1, j1, k1))
            {
                world.func_470_a(i1, j1, k1, Block.pumpkin.blockID, random.nextInt(4));
            }
        }

        return true;
    }
}
