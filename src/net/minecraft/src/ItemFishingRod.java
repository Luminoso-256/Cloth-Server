package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class ItemFishingRod extends Item
{

    public ItemFishingRod(int i)
    {
        super(i);
        maxDamage = 64;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        if(entityplayer.field_6124_at != null)
        {
            int i = entityplayer.field_6124_at.func_6143_c();
            itemstack.damageItem(i);
            entityplayer.func_168_z();
        } else
        {
            world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
            if(!world.multiplayerWorld)
            {
                world.entityJoinedWorld(new EntityFish(world, entityplayer));
            }
            entityplayer.func_168_z();
        }
        return itemstack;
    }
}
