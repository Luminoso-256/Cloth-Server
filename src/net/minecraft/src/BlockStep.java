package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockStep extends Block
{

    public BlockStep(int i, boolean flag)
    {
        super(i, 6, Material.rock);
        field_666_a = flag;
        if(!flag)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
        setLightOpacity(255);
    }

    public int getBlockTextureFromSide(int i)
    {
        return i > 1 ? 5 : 6;
    }

    public boolean allowsAttachment()
    {
        return field_666_a;
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(this != Block.stairSingle)
        {
            return;
        } else
        {
            return;
        }
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
        if(this != Block.stairSingle)
        {
            super.onBlockAdded(world, i, j, k);
        }
        int l = world.getBlockId(i, j - 1, k);
        if(l == stairSingle.blockID)
        {
            world.setBlockWithNotify(i, j, k, 0);
            world.setBlockWithNotify(i, j - 1, k, Block.stairDouble.blockID);
        }
    }

    public int idDropped(int i, Random random)
    {
        return Block.stairSingle.blockID;
    }

    public boolean isSideInsideCoordinate(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if(this != Block.stairSingle)
        {
            super.isSideInsideCoordinate(iblockaccess, i, j, k, l);
        }
        if(l == 1)
        {
            return true;
        }
        if(!super.isSideInsideCoordinate(iblockaccess, i, j, k, l))
        {
            return false;
        }
        if(l == 0)
        {
            return true;
        } else
        {
            return iblockaccess.getBlockId(i, j, k) != blockID;
        }
    }

    private boolean field_666_a;
}
