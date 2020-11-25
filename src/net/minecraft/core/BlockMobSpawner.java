package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockMobSpawner extends BlockContainer
{

    protected BlockMobSpawner(int i, int j)
    {
        super(i, j, Material.rock);
    }

    protected TileEntity func_294_a_()
    {
        return new TileEntityMobSpawner();
    }

    public int idDropped(int i, Random random)
    {
        return 0;
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }

    public boolean allowsAttachment()
    {
        return false;
    }
}
