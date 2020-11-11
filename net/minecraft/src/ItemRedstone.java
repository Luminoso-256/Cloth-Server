package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ItemRedstone extends Item
{

    public ItemRedstone(int i)
    {
        super(i);
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        if(l == 0)
        {
            j--;
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
        if(world.getBlockId(i, j, k) != 0)
        {
            return false;
        }
        if(Block.redstoneWire.canPlaceBlockAt(world, i, j, k))
        {
            itemstack.stackSize--;
            world.setBlockWithNotify(i, j, k, Block.redstoneWire.blockID);
        }
        return true;
    }
}
