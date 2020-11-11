package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class BlockOreBlock extends Block
{

    public BlockOreBlock(int i, int j)
    {
        super(i, Material.iron);
        blockIndexInTexture = j;
    }

    public int getBlockTextureFromSide(int i)
    {
        return blockIndexInTexture - 16;
    }
}
