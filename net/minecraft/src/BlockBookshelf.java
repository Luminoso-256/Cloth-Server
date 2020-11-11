package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockBookshelf extends Block
{

    public BlockBookshelf(int i, int j)
    {
        super(i, j, Material.wood);
    }

    public int getBlockTextureFromSide(int i)
    {
        if(i <= 1)
        {
            return 4;
        } else
        {
            return blockIndexInTexture;
        }
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }
}
