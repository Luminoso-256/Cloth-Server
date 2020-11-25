package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ItemSaddle extends Item
{

    public ItemSaddle(int i)
    {
        super(i);
        maxStackSize = 1;
        maxDamage = 64;
    }

    public void func_9202_b(ItemStack itemstack, EntityLiving entityliving)
    {
        if(entityliving instanceof EntityPig)
        {
            EntityPig entitypig = (EntityPig)entityliving;
            if(!entitypig.rideable)
            {
                entitypig.rideable = true;
                itemstack.stackSize--;
            }
        }
    }

    public void func_9201_a(ItemStack itemstack, EntityLiving entityliving)
    {
        func_9202_b(itemstack, entityliving);
    }
}
