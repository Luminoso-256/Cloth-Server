package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class ItemSnowball extends Item
{

    public ItemSnowball(int i)
    {
        super(i);
        maxStackSize = 16;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        itemstack.stackSize--;
        world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
        if(!world.multiplayerWorld)
        {
            world.entityJoinedWorld(new EntitySnowball(world, entityplayer));
        }
        return itemstack;
    }
}
