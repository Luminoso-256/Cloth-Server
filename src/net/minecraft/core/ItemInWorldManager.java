package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ItemInWorldManager
{

    public ItemInWorldManager(World world)
    {
        field_672_d = 0.0F;
        field_671_e = 0;
        field_670_f = 0.0F;
        field_674_b = world;
    }

    public void func_324_a(int i, int j, int k)
    {
        int l = field_674_b.getBlockId(i, j, k);
        if(l > 0 && field_672_d == 0.0F)
        {
            Block.blocksList[l].onBlockClicked(field_674_b, i, j, k, field_675_a);
        }
        if(l > 0 && Block.blocksList[l].func_254_a(field_675_a) >= 1.0F)
        {
            func_325_c(i, j, k);
        }
    }

    public void func_328_a()
    {
        field_672_d = 0.0F;
        field_671_e = 0;
    }

    public void func_326_a(int i, int j, int k, int l)
    {
        if(field_671_e > 0)
        {
            field_671_e--;
            return;
        }
        if(i == field_669_g && j == field_668_h && k == field_667_i)
        {
            int i1 = field_674_b.getBlockId(i, j, k);
            if(i1 == 0)
            {
                return;
            }
            Block block = Block.blocksList[i1];
            field_672_d += block.func_254_a(field_675_a);
            field_670_f++;
            if(field_672_d >= 1.0F)
            {
                func_325_c(i, j, k);
                field_672_d = 0.0F;
                field_673_c = 0.0F;
                field_670_f = 0.0F;
                field_671_e = 5;
            }
        } else
        {
            field_672_d = 0.0F;
            field_673_c = 0.0F;
            field_670_f = 0.0F;
            field_669_g = i;
            field_668_h = j;
            field_667_i = k;
        }
    }

    public boolean func_323_b(int i, int j, int k)
    {
        Block block = Block.blocksList[field_674_b.getBlockId(i, j, k)];
        int l = field_674_b.getBlockMetadata(i, j, k);
        boolean flag = field_674_b.setBlockWithNotify(i, j, k, 0);
        if(block != null && flag)
        {
            block.onBlockDestroyedByPlayer(field_674_b, i, j, k, l);
        }
        return flag;
    }

    public boolean func_325_c(int i, int j, int k)
    {
        int l = field_674_b.getBlockId(i, j, k);
        int i1 = field_674_b.getBlockMetadata(i, j, k);
        boolean flag = func_323_b(i, j, k);
        ItemStack itemstack = field_675_a.func_172_B();
        if(itemstack != null)
        {
            itemstack.hitBlock(l, i, j, k);
            if(itemstack.stackSize == 0)
            {
                itemstack.func_577_a(field_675_a);
                field_675_a.func_164_C();
            }
        }
        if(flag && field_675_a.canHarvest(Block.blocksList[l]))
        {
            Block.blocksList[l].func_12007_g(field_674_b, i, j, k, i1);
        }
        return flag;
    }

    public boolean func_6154_a(EntityPlayer entityplayer, World world, ItemStack itemstack)
    {
        int i = itemstack.stackSize;
        ItemStack itemstack1 = itemstack.useItemRightClick(world, entityplayer);
        if(itemstack1 != itemstack || itemstack1 != null && itemstack1.stackSize != i)
        {
            entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = itemstack1;
            if(itemstack1.stackSize == 0)
            {
                entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
            }
            return true;
        } else
        {
            return false;
        }
    }

    public boolean func_327_a(EntityPlayer entityplayer, World world, ItemStack itemstack, int i, int j, int k, int l)
    {
        int i1 = world.getBlockId(i, j, k);
        if(i1 > 0 && Block.blocksList[i1].blockActivated(world, i, j, k, entityplayer))
        {
            return true;
        }
        if(itemstack == null)
        {
            return false;
        } else
        {
            return itemstack.useItem(entityplayer, world, i, j, k, l);
        }
    }

    private World field_674_b;
    public EntityPlayer field_675_a;
    private float field_673_c;
    private float field_672_d;
    private int field_671_e;
    private float field_670_f;
    private int field_669_g;
    private int field_668_h;
    private int field_667_i;
}
