package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockTorch extends Block
{

    protected BlockTorch(int i, int j)
    {
        super(i, j, Material.circuits);
        setTickOnLoad(true);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    public boolean allowsAttachment()
    {
        return false;
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        if(world.doesBlockAllowAttachment(i - 1, j, k))
        {
            return true;
        }
        if(world.doesBlockAllowAttachment(i + 1, j, k))
        {
            return true;
        }
        if(world.doesBlockAllowAttachment(i, j, k - 1))
        {
            return true;
        }
        if(world.doesBlockAllowAttachment(i, j, k + 1))
        {
            return true;
        }
        return world.doesBlockAllowAttachment(i, j - 1, k);
    }

    public void onBlockPlaced(World world, int i, int j, int k, int l)
    {
        int i1 = world.getBlockMetadata(i, j, k);
        if(l == 1 && world.doesBlockAllowAttachment(i, j - 1, k))
        {
            i1 = 5;
        }
        if(l == 2 && world.doesBlockAllowAttachment(i, j, k + 1))
        {
            i1 = 4;
        }
        if(l == 3 && world.doesBlockAllowAttachment(i, j, k - 1))
        {
            i1 = 3;
        }
        if(l == 4 && world.doesBlockAllowAttachment(i + 1, j, k))
        {
            i1 = 2;
        }
        if(l == 5 && world.doesBlockAllowAttachment(i - 1, j, k))
        {
            i1 = 1;
        }
        world.setBlockMetadataWithNotify(i, j, k, i1);
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        super.updateTick(world, i, j, k, random);
        if(world.getBlockMetadata(i, j, k) == 0)
        {
            onBlockAdded(world, i, j, k);
        }
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
        if(world.doesBlockAllowAttachment(i - 1, j, k))
        {
            world.setBlockMetadataWithNotify(i, j, k, 1);
        } else
        if(world.doesBlockAllowAttachment(i + 1, j, k))
        {
            world.setBlockMetadataWithNotify(i, j, k, 2);
        } else
        if(world.doesBlockAllowAttachment(i, j, k - 1))
        {
            world.setBlockMetadataWithNotify(i, j, k, 3);
        } else
        if(world.doesBlockAllowAttachment(i, j, k + 1))
        {
            world.setBlockMetadataWithNotify(i, j, k, 4);
        } else
        if(world.doesBlockAllowAttachment(i, j - 1, k))
        {
            world.setBlockMetadataWithNotify(i, j, k, 5);
        }
        func_279_g(world, i, j, k);
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(func_279_g(world, i, j, k))
        {
            int i1 = world.getBlockMetadata(i, j, k);
            boolean flag = false;
            if(!world.doesBlockAllowAttachment(i - 1, j, k) && i1 == 1)
            {
                flag = true;
            }
            if(!world.doesBlockAllowAttachment(i + 1, j, k) && i1 == 2)
            {
                flag = true;
            }
            if(!world.doesBlockAllowAttachment(i, j, k - 1) && i1 == 3)
            {
                flag = true;
            }
            if(!world.doesBlockAllowAttachment(i, j, k + 1) && i1 == 4)
            {
                flag = true;
            }
            if(!world.doesBlockAllowAttachment(i, j - 1, k) && i1 == 5)
            {
                flag = true;
            }
            if(flag)
            {
                dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
                world.setBlockWithNotify(i, j, k, 0);
            }
        }
    }

    private boolean func_279_g(World world, int i, int j, int k)
    {
        if(!canPlaceBlockAt(world, i, j, k))
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
            return false;
        } else
        {
            return true;
        }
    }

    public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1)
    {
        int l = world.getBlockMetadata(i, j, k) & 7;
        float f = 0.15F;
        if(l == 1)
        {
            setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        } else
        if(l == 2)
        {
            setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        } else
        if(l == 3)
        {
            setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        } else
        if(l == 4)
        {
            setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        } else
        {
            float f1 = 0.1F;
            setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, 0.6F, 0.5F + f1);
        }
        return super.collisionRayTrace(world, i, j, k, vec3d, vec3d1);
    }
}
