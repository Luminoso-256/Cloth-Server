package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockSapling extends BlockFlower
{

    protected BlockSapling(int i, int j)
    {
        super(i, j);
        float f = 0.4F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        super.updateTick(world, i, j, k, random);
        if(world.getBlockLightValue(i, j + 1, k) >= 9 && random.nextInt(5) == 0)
        {
            int l = world.getBlockMetadata(i, j, k);
            if(l < 15)
            {
                world.setBlockMetadataWithNotify(i, j, k, l + 1);
            } else
            {
                world.setBlock(i, j, k, 0);
                Object obj = new WorldGenTrees();
                if(random.nextInt(10) == 0)
                {
                    obj = new WorldGenBigTree();
                }
                if(!((WorldGenerator) (obj)).generate(world, random, i, j, k))
                {
                    world.setBlock(i, j, k, blockID);
                }
            }
        }
    }
}
