package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockGlass extends BlockBreakable
{

    public BlockGlass(int i, int j, Material material, boolean flag)
    {
        super(i, j, material, flag);
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }
}
