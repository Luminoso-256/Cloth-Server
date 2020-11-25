package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockSign extends BlockContainer
{

    protected BlockSign(int i, Class class1, boolean flag)
    {
        super(i, Material.wood);
        field_653_b = flag;
        blockIndexInTexture = 4;
        field_654_a = class1;
        float f = 0.25F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
    {
        if(field_653_b)
        {
            return;
        }
        int l = iblockaccess.getBlockMetadata(i, j, k);
        float f = 0.28125F;
        float f1 = 0.78125F;
        float f2 = 0.0F;
        float f3 = 1.0F;
        float f4 = 0.125F;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        if(l == 2)
        {
            setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
        }
        if(l == 3)
        {
            setBlockBounds(f2, f, 0.0F, f3, f1, f4);
        }
        if(l == 4)
        {
            setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3);
        }
        if(l == 5)
        {
            setBlockBounds(0.0F, f, f2, f4, f1, f3);
        }
    }

    public boolean allowsAttachment()
    {
        return false;
    }

    protected TileEntity func_294_a_()
    {
        try
        {
            return (TileEntity)field_654_a.newInstance();
        }
        catch(Exception exception)
        {
            throw new RuntimeException(exception);
        }
    }

    public int idDropped(int i, Random random)
    {
        return Item.sign.swiftedIndex;
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        boolean flag = false;
        if(field_653_b)
        {
            if(!world.getBlockMaterial(i, j - 1, k).func_216_a())
            {
                flag = true;
            }
        } else
        {
            int i1 = world.getBlockMetadata(i, j, k);
            flag = true;
            if(i1 == 2 && world.getBlockMaterial(i, j, k + 1).func_216_a())
            {
                flag = false;
            }
            if(i1 == 3 && world.getBlockMaterial(i, j, k - 1).func_216_a())
            {
                flag = false;
            }
            if(i1 == 4 && world.getBlockMaterial(i + 1, j, k).func_216_a())
            {
                flag = false;
            }
            if(i1 == 5 && world.getBlockMaterial(i - 1, j, k).func_216_a())
            {
                flag = false;
            }
        }
        if(flag)
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
        }
        super.onNeighborBlockChange(world, i, j, k, l);
    }

    private Class field_654_a;
    private boolean field_653_b;
}
