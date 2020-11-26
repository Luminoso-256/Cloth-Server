package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockLightStone extends Block
{

    public BlockLightStone(int i, int j, Material material)
    {
        super(i, j, material);
    }

    public int idDropped(int i, Random random)
    {
        return Item.lightStoneDust.swiftedIndex;
    }
}
