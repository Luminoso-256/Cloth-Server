package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockLog extends Block
{

    protected BlockLog(int i)
    {
        super(i, Material.wood);
        blockIndexInTexture = 20;
    }

    public int quantityDropped(Random random)
    {
        return 1;
    }

    public int idDropped(int i, Random random)
    {
        return Block.wood.blockID;
    }

    public int getBlockTextureFromSide(int i)
    {
        if(i == 1)
        {
            return 21;
        }
        return i != 0 ? 20 : 21;
    }
}
