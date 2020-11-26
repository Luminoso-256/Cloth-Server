package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ItemRecord extends Item
{

    protected ItemRecord(int i, String s)
    {
        super(i);
        recordName = s;
        maxStackSize = 1;
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        if(world.getBlockId(i, j, k) == Block.jukebox.blockID && world.getBlockMetadata(i, j, k) == 0)
        {
            world.setBlockMetadataWithNotify(i, j, k, (swiftedIndex - Item.record13.swiftedIndex) + 1);
            world.playRecord(recordName, i, j, k);
            itemstack.stackSize--;
            return true;
        } else
        {
            return false;
        }
    }

    private String recordName;
}
