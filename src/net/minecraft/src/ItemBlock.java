package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ItemBlock extends Item
{

    public ItemBlock(int i)
    {
        super(i);
        field_272_a = i + 256;
        setIconIndex(Block.blocksList[i + 256].getBlockTextureFromSide(2));
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        if(world.getBlockId(i, j, k) == Block.snow.blockID)
        {
            l = 0;
        } else
        {
            if(l == 0)
            {
                j--;
            }
            if(l == 1)
            {
                j++;
            }
            if(l == 2)
            {
                k--;
            }
            if(l == 3)
            {
                k++;
            }
            if(l == 4)
            {
                i--;
            }
            if(l == 5)
            {
                i++;
            }
        }
        if(itemstack.stackSize == 0)
        {
            return false;
        }
        if(world.func_516_a(field_272_a, i, j, k, false))
        {
            Block block = Block.blocksList[field_272_a];
            if(world.setBlockWithNotify(i, j, k, field_272_a))
            {
                Block.blocksList[field_272_a].onBlockPlaced(world, i, j, k, l);
                Block.blocksList[field_272_a].onBlockPlacedBy(world, i, j, k, entityplayer);
                world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, block.stepSound.func_737_c(), (block.stepSound.func_738_a() + 1.0F) / 2.0F, block.stepSound.func_739_b() * 0.8F);
                itemstack.stackSize--;
            }
        }
        return true;
    }

    private int field_272_a;
}
