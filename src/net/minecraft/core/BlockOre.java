package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockOre extends Block
{

    public BlockOre(int i, int j)
    {
        super(i, j, Material.rock);
    }

    public int idDropped(int i, Random random)
    {
        if(blockID == Block.oreCoal.blockID)
        {
            return Item.coal.swiftedIndex;
        }
        if(blockID == Block.oreDiamond.blockID)
        {
            return Item.diamond.swiftedIndex;
        } else
        {
            return blockID;
        }
    }

    public int quantityDropped(Random random)
    {
        return 1;
    }
}
