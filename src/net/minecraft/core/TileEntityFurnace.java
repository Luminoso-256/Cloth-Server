package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class TileEntityFurnace extends TileEntity
    implements IInventory
{

    public TileEntityFurnace()
    {
        field_489_e = new ItemStack[3];
        field_488_f = 0;
        field_487_g = 0;
        field_486_h = 0;
    }

    public int getInventorySize()
    {
        return field_489_e.length;
    }

    public ItemStack getStackInSlot(int i)
    {
        return field_489_e[i];
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        field_489_e = new ItemStack[getInventorySize()];
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if(byte0 >= 0 && byte0 < field_489_e.length)
            {
                field_489_e[byte0] = new ItemStack(nbttagcompound1);
            }
        }

        field_488_f = nbttagcompound.getShort("BurnTime");
        field_486_h = nbttagcompound.getShort("CookTime");
        field_487_g = func_194_a(field_489_e[1]);
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("BurnTime", (short)field_488_f);
        nbttagcompound.setShort("CookTime", (short)field_486_h);
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < field_489_e.length; i++)
        {
            if(field_489_e[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                field_489_e[i].writeToNBT(nbttagcompound1);
                nbttaglist.setTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

    public int func_190_d()
    {
        return 64;
    }

    public boolean func_191_e()
    {
        return field_488_f > 0;
    }

    public void updateEntity()
    {
        boolean flag = field_488_f > 0;
        boolean flag1 = false;
        if(field_488_f > 0)
        {
            field_488_f--;
        }
        if(!worldObj.multiplayerWorld)
        {
            if(field_488_f == 0 && func_193_g())
            {
                field_487_g = field_488_f = func_194_a(field_489_e[1]);
                if(field_488_f > 0)
                {
                    flag1 = true;
                    if(field_489_e[1] != null)
                    {
                        field_489_e[1].stackSize--;
                        if(field_489_e[1].stackSize == 0)
                        {
                            field_489_e[1] = null;
                        }
                    }
                }
            }
            if(func_191_e() && func_193_g())
            {
                field_486_h++;
                if(field_486_h == 200)
                {
                    field_486_h = 0;
                    func_189_f();
                    flag1 = true;
                }
            } else
            {
                field_486_h = 0;
            }
            if(flag != (field_488_f > 0))
            {
                flag1 = true;
                BlockFurnace.func_295_a(field_488_f > 0, worldObj, xCoord, yCoord, zCoord);
            }
        }
        if(flag1)
        {
            func_183_c();
        }
    }

    private boolean func_193_g()
    {
        if(field_489_e[0] == null)
        {
            return false;
        }
        int i = func_192_b(field_489_e[0].getItem().swiftedIndex);
        if(i < 0)
        {
            return false;
        }
        if(field_489_e[2] == null)
        {
            return true;
        }
        if(field_489_e[2].itemID != i)
        {
            return false;
        }
        if(field_489_e[2].stackSize < func_190_d() && field_489_e[2].stackSize < field_489_e[2].getMaxStackSize())
        {
            return true;
        }
        return field_489_e[2].stackSize < Item.itemsList[i].getItemStackLimit();
    }

    public void func_189_f()
    {
        if(!func_193_g())
        {
            return;
        }
        int i = func_192_b(field_489_e[0].getItem().swiftedIndex);
        if(field_489_e[2] == null)
        {
            field_489_e[2] = new ItemStack(i, 1);
        } else
        if(field_489_e[2].itemID == i)
        {
            field_489_e[2].stackSize++;
        }
        field_489_e[0].stackSize--;
        if(field_489_e[0].stackSize <= 0)
        {
            field_489_e[0] = null;
        }
    }

    private int func_192_b(int i)
    {
        if(i == Block.oreIron.blockID)
        {
            return Item.ingotIron.swiftedIndex;
        }
        if(i == Block.oreGold.blockID)
        {
            return Item.ingotGold.swiftedIndex;
        }
        if(i == Block.oreDiamond.blockID)
        {
            return Item.diamond.swiftedIndex;
        }
        if(i == Block.sand.blockID)
        {
            return Block.glass.blockID;
        }
        if(i == Item.porkRaw.swiftedIndex)
        {
            return Item.porkCooked.swiftedIndex;
        }
        if(i == Item.fishRaw.swiftedIndex)
        {
            return Item.fishCooked.swiftedIndex;
        }
        if(i == Block.cobblestone.blockID)
        {
            return Block.stone.blockID;
        }
        if(i == Item.clay.swiftedIndex)
        {
            return Item.brick.swiftedIndex;
        } else
        {
            return -1;
        }
    }

    private int func_194_a(ItemStack itemstack)
    {
        if(itemstack == null)
        {
            return 0;
        }
        int i = itemstack.getItem().swiftedIndex;
        if(i < 256 && Block.blocksList[i].blockMaterial == Material.wood)
        {
            return 300;
        }
        if(i == Item.stick.swiftedIndex)
        {
            return 100;
        }
        if(i == Item.coal.swiftedIndex)
        {
            return 1600;
        }
        return i != Item.bucketLava.swiftedIndex ? 0 : 20000;
    }

    private ItemStack field_489_e[];
    private int field_488_f;
    private int field_487_g;
    private int field_486_h;
}
