package net.minecraft.src;
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

    public int func_83_a()
    {
        return upperChest.func_83_a() + lowerChest.func_83_a();
    }

    public ItemStack getStackInSlot(int i)
    {
        if(i >= upperChest.func_83_a())
        {
            return lowerChest.getStackInSlot(i - upperChest.func_83_a());
        } else
        {
            return upperChest.getStackInSlot(i);
        }
    }

    private String name;
    private IInventory upperChest;
    private IInventory lowerChest;
}
