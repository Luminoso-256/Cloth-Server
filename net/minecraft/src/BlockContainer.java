package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public abstract class BlockContainer extends Block
{

    protected BlockContainer(int i, Material material)
    {
        super(i, material);
        isBlockContainer[i] = true;
    }

    protected BlockContainer(int i, int j, Material material)
    {
        super(i, j, material);
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
        super.onBlockAdded(world, i, j, k);
        world.func_473_a(i, j, k, func_294_a_());
    }

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        super.onBlockRemoval(world, i, j, k);
        world.func_513_l(i, j, k);
    }

    protected abstract TileEntity func_294_a_();
}
