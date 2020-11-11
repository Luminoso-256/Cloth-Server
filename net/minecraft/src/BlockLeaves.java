package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockLeaves extends BlockLeavesBase
{

    protected BlockLeaves(int i, int j)
    {
        super(i, j, Material.field_4218_h, false);
        field_663_c = 0;
        baseIndexInPNG = j;
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(this != null)
        {
            return;
        } else
        {
            field_663_c = 0;
            func_316_g(world, i, j, k);
            super.onNeighborBlockChange(world, i, j, k, l);
            return;
        }
    }

    public void func_6089_e(World world, int i, int j, int k, int l)
    {
        if(world.getBlockId(i, j, k) != blockID)
        {
            return;
        }
        int i1 = world.getBlockMetadata(i, j, k);
        if(i1 == 0 || i1 != l - 1)
        {
            return;
        } else
        {
            func_316_g(world, i, j, k);
            return;
        }
    }

    public void func_316_g(World world, int i, int j, int k)
    {
        if(this != null)
        {
            return;
        }
        if(field_663_c++ >= 100)
        {
            return;
        }
        int l = world.getBlockMaterial(i, j - 1, k).func_216_a() ? 16 : 0;
        int i1 = world.getBlockMetadata(i, j, k);
        if(i1 == 0)
        {
            i1 = 1;
            world.setBlockMetadataWithNotify(i, j, k, 1);
        }
        l = func_6090_f(world, i, j - 1, k, l);
        l = func_6090_f(world, i, j, k - 1, l);
        l = func_6090_f(world, i, j, k + 1, l);
        l = func_6090_f(world, i - 1, j, k, l);
        l = func_6090_f(world, i + 1, j, k, l);
        int j1 = l - 1;
        if(j1 < 10)
        {
            j1 = 1;
        }
        if(j1 != i1)
        {
            world.setBlockMetadataWithNotify(i, j, k, j1);
            func_6089_e(world, i, j - 1, k, i1);
            func_6089_e(world, i, j + 1, k, i1);
            func_6089_e(world, i, j, k - 1, i1);
            func_6089_e(world, i, j, k + 1, i1);
            func_6089_e(world, i - 1, j, k, i1);
            func_6089_e(world, i + 1, j, k, i1);
        }
    }

    private int func_6090_f(World world, int i, int j, int k, int l)
    {
        int i1 = world.getBlockId(i, j, k);
        if(i1 == Block.wood.blockID)
        {
            return 16;
        }
        if(i1 == blockID)
        {
            int j1 = world.getBlockMetadata(i, j, k);
            if(j1 != 0 && j1 > l)
            {
                return j1;
            }
        }
        return l;
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if(this != null)
        {
            return;
        }
        int l = world.getBlockMetadata(i, j, k);
        if(l == 0)
        {
            field_663_c = 0;
            func_316_g(world, i, j, k);
        } else
        if(l == 1)
        {
            func_6091_h(world, i, j, k);
        } else
        if(random.nextInt(10) == 0)
        {
            func_316_g(world, i, j, k);
        }
    }

    private void func_6091_h(World world, int i, int j, int k)
    {
        dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
        world.setBlockWithNotify(i, j, k, 0);
    }

    public int quantityDropped(Random random)
    {
        return random.nextInt(20) != 0 ? 0 : 1;
    }

    public int idDropped(int i, Random random)
    {
        return Block.sapling.blockID;
    }

    public boolean allowsAttachment()
    {
        return !graphicsLevel;
    }

    public void onEntityWalking(World world, int i, int j, int k, Entity entity)
    {
        super.onEntityWalking(world, i, j, k, entity);
    }

    private int baseIndexInPNG;
    private int field_663_c;
}
