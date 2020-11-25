package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ItemBow extends Item
{

    public ItemBow(int i)
    {
        super(i);
        maxStackSize = 1;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        if(entityplayer.inventory.func_6127_b(Item.arrow.swiftedIndex))
        {
            world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
            if(!world.multiplayerWorld)
            {
                world.entityJoinedWorld(new EntityArrow(world, entityplayer));
            }
        }
        return itemstack;
    }
}
