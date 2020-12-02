package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ItemSword extends Item {

    private int field_4210_a;

    public ItemSword(int i, int j) {
        super(i);
        maxStackSize = 1;
        maxDamage = 32 << j;
        if (j == 3) {
            maxDamage *= 4;
        }
        field_4210_a = 4 + j * 2;
    }

    public float getStrVsBlock(ItemStack itemstack, Block block) {
        return 1.5F;
    }

    public void func_9201_a(ItemStack itemstack, EntityLiving entityliving) {
        itemstack.damageItem(1);
    }

    public void hitBlock(ItemStack itemstack, int i, int j, int k, int l) {
        itemstack.damageItem(2);
    }

    public int func_9203_a(Entity entity) {
        return field_4210_a;
    }
}
