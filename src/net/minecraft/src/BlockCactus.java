package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockCactus extends Block
{

    protected BlockCactus(int i, int j)
    {
        super(i, j, Material.field_4214_u);
        setTickOnLoad(true);
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if(world.getBlockId(i, j + 1, k) == 0)
        {
            int l;
            for(l = 1; world.getBlockId(i, j - l, k) == blockID; l++) { }
            if(l < 3)
            {
                int i1 = world.getBlockMetadata(i, j, k);
                if(i1 == 15)
                {
                    world.setBlockWithNotify(i, j + 1, k, blockID);
                    world.setBlockMetadataWithNotify(i, j, k, 0);
                } else
                {
                    world.setBlockMetadataWithNotify(i, j, k, i1 + 1);
                }
            }
        }
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        float f = 0.0625F;
        return AxisAlignedBB.getBoundingBoxFromPool((float)i + f, j, (float)k + f, (float)(i + 1) - f, (float)(j + 1) - f, (float)(k + 1) - f);
    }

    public int getBlockTextureFromSide(int i)
    {
        if(i == 1)
        {
            return blockIndexInTexture - 1;
        }
        if(i == 0)
        {
            return blockIndexInTexture + 1;
        } else
        {
            return blockIndexInTexture;
        }
    }

    public boolean allowsAttachment()
    {
        return false;
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        if(!super.canPlaceBlockAt(world, i, j, k))
        {
            return false;
        } else
        {
            return canBlockStay(world, i, j, k);
        }
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(!canBlockStay(world, i, j, k))
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    public boolean canBlockStay(World world, int i, int j, int k)
    {
        if(world.getBlockMaterial(i - 1, j, k).func_216_a())
        {
            return false;
        }
        if(world.getBlockMaterial(i + 1, j, k).func_216_a())
        {
            return false;
        }
        if(world.getBlockMaterial(i, j, k - 1).func_216_a())
        {
            return false;
        }
        if(world.getBlockMaterial(i, j, k + 1).func_216_a())
        {
            return false;
        } else
        {
            int l = world.getBlockId(i, j - 1, k);
            return l == Block.cactus.blockID || l == Block.sand.blockID;
        }
    }

    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
        entity.attackEntity(null, 1);
    }
}
