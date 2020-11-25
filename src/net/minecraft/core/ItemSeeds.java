package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ItemSeeds extends Item
{

    public ItemSeeds(int i, int j)
    {
        super(i);
        field_271_a = j;
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        if(l != 1)
        {
            return false;
        }
        int i1 = world.getBlockId(i, j, k);
        if(i1 == Block.tilledField.blockID)
        {
            world.setBlockWithNotify(i, j + 1, k, field_271_a);
            itemstack.stackSize--;
            return true;
        } else
        {
            return false;
        }
    }

    private int field_271_a;
}
