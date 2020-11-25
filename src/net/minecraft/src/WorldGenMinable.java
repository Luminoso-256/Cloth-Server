package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class WorldGenMinable extends WorldGenerator
{

    public WorldGenMinable(int i, int j)
    {
        minableBlockId = i;
        numberOfBlocks = j;
    }

    public boolean generate(World world, Random random, int i, int j, int k)
    {
        float f = random.nextFloat() * 3.141593F;
        double d = (float)(i + 8) + (MathHelper.sin(f) * (float)numberOfBlocks) / 8F;
        double d1 = (float)(i + 8) - (MathHelper.sin(f) * (float)numberOfBlocks) / 8F;
        double d2 = (float)(k + 8) + (MathHelper.cos(f) * (float)numberOfBlocks) / 8F;
        double d3 = (float)(k + 8) - (MathHelper.cos(f) * (float)numberOfBlocks) / 8F;
        double d4 = j + random.nextInt(3) + 2;
        double d5 = j + random.nextInt(3) + 2;
        for(int l = 0; l <= numberOfBlocks; l++)
        {
            double d6 = d + ((d1 - d) * (double)l) / (double)numberOfBlocks;
            double d7 = d4 + ((d5 - d4) * (double)l) / (double)numberOfBlocks;
            double d8 = d2 + ((d3 - d2) * (double)l) / (double)numberOfBlocks;
            double d9 = (random.nextDouble() * (double)numberOfBlocks) / 16D;
            double d10 = (double)(MathHelper.sin(((float)l * 3.141593F) / (float)numberOfBlocks) + 1.0F) * d9 + 1.0D;
            double d11 = (double)(MathHelper.sin(((float)l * 3.141593F) / (float)numberOfBlocks) + 1.0F) * d9 + 1.0D;
            for(int i1 = (int)(d6 - d10 / 2D); i1 <= (int)(d6 + d10 / 2D); i1++)
            {
                for(int j1 = (int)(d7 - d11 / 2D); j1 <= (int)(d7 + d11 / 2D); j1++)
                {
                    for(int k1 = (int)(d8 - d10 / 2D); k1 <= (int)(d8 + d10 / 2D); k1++)
                    {
                        double d12 = (((double)i1 + 0.5D) - d6) / (d10 / 2D);
                        double d13 = (((double)j1 + 0.5D) - d7) / (d11 / 2D);
                        double d14 = (((double)k1 + 0.5D) - d8) / (d10 / 2D);
                        if(d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && world.getBlockId(i1, j1, k1) == Block.stone.blockID)
                        {
                            world.setBlock(i1, j1, k1, minableBlockId);
                        }
                    }

                }

            }

        }

        return true;
    }

    private int minableBlockId;
    private int numberOfBlocks;
}
