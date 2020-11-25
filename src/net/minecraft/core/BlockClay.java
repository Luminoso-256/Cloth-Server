package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockClay extends Block
{

    public BlockClay(int i, int j)
    {
        super(i, j, Material.clay);
    }

    public int idDropped(int i, Random random)
    {
        return Item.clay.swiftedIndex;
    }

    public int quantityDropped(Random random)
    {
        return 4;
    }
}
