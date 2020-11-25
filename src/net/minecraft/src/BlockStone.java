package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockStone extends Block
{

    public BlockStone(int i, int j)
    {
        super(i, j, Material.rock);
    }

    public int idDropped(int i, Random random)
    {
        return Block.cobblestone.blockID;
    }
}
