package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class TileEntityChest extends TileEntity
    implements IInventory
{

    public TileEntityChest()
    {
        field_494_e = new ItemStack[36];
    }

    public int getInventorySize()
    {
        return 27;
    }

    public ItemStack getStackInSlot(int i)
    {
        return field_494_e[i];
    }

    public void func_197_a(int i, ItemStack itemstack)
    {
        field_494_e[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > func_198_d())
        {
            itemstack.stackSize = func_198_d();
        }
        func_183_c();
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        field_494_e = new ItemStack[getInventorySize()];
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;
            if(j >= 0 && j < field_494_e.length)
            {
                field_494_e[j] = new ItemStack(nbttagcompound1);
            }
        }

    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < field_494_e.length; i++)
        {
            if(field_494_e[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                field_494_e[i].writeToNBT(nbttagcompound1);
                nbttaglist.setTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

    public int func_198_d()
    {
        return 64;
    }

    private ItemStack field_494_e[];
}
