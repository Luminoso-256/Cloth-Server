package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockTNT extends Block
{

    public BlockTNT(int i, int j)
    {
        super(i, j, Material.tnt);
    }

    public int getBlockTextureFromSide(int i)
    {
        if(i == 0)
        {
            return blockIndexInTexture + 2;
        }
        if(i == 1)
        {
            return blockIndexInTexture + 1;
        } else
        {
            return blockIndexInTexture;
        }
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(l > 0 && Block.blocksList[l].canProvidePower() && world.isBlockIndirectlyGettingPowered(i, j, k))
        {
            onBlockDestroyedByPlayer(world, i, j, k, 0);
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }

    public void onBlockDestroyedByExplosion(World world, int i, int j, int k)
    {
        EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F);
        entitytntprimed.fuse = world.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
        world.entityJoinedWorld(entitytntprimed);
    }

    public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
    {
        if(world.multiplayerWorld)
        {
            return;
        } else
        {
            EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F);
            world.entityJoinedWorld(entitytntprimed);
            world.playSoundAtEntity(entitytntprimed, "random.fuse", 1.0F, 1.0F);
            return;
        }
    }
}
