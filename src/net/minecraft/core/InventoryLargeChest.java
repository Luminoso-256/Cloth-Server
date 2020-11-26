package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class InventoryLargeChest
    implements IInventory
{

    public InventoryLargeChest(String s, IInventory iinventory, IInventory iinventory1)
    {
        name = s;
        upperChest = iinventory;
        lowerChest = iinventory1;
    }

    public int getInventorySize()
    {
        return upperChest.getInventorySize() + lowerChest.getInventorySize();
    }

    public ItemStack getStackInSlot(int i)
    {
        if(i >= upperChest.getInventorySize())
        {
            return lowerChest.getStackInSlot(i - upperChest.getInventorySize());
        } else
        {
            return upperChest.getStackInSlot(i);
        }
    }

    private String name;
    private IInventory upperChest;
    private IInventory lowerChest;
}
