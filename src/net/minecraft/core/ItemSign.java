package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ItemSign extends Item
{

    public ItemSign(int i)
    {
        super(i);
        maxDamage = 64;
        maxStackSize = 1;
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        if(l == 0)
        {
            return false;
        }
        if(!world.getBlockMaterial(i, j, k).func_216_a())
        {
            return false;
        }
        if(l == 1)
        {
            j++;
        }
        if(l == 2)
        {
            k--;
        }
        if(l == 3)
        {
            k++;
        }
        if(l == 4)
        {
            i--;
        }
        if(l == 5)
        {
            i++;
        }
        if(!Block.signPost.canPlaceBlockAt(world, i, j, k))
        {
            return false;
        }
        if(l == 1)
        {
            world.func_507_b(i, j, k, Block.signPost.blockID, MathHelper.floor_double((double)(((entityplayer.rotationYaw + 180F) * 16F) / 360F) + 0.5D) & 0xf);
        } else
        {
            world.func_507_b(i, j, k, Block.signWall.blockID, l);
        }
        itemstack.stackSize--;
        TileEntitySign tileentitysign = (TileEntitySign)world.getBlock(i, j, k);
        if(tileentitysign != null)
        {
            entityplayer.func_4048_a(tileentitysign);
        }
        return true;
    }
}
