package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ItemTool extends Item
{

    public ItemTool(int i, int j, int k, Block ablock[])
    {
        super(i);
        field_264_aY = 4F;
        field_262_a = k;
        blocksEffectiveAgainst = ablock;
        maxStackSize = 1;
        maxDamage = 32 << k;
        if(k == 3)
        {
            maxDamage *= 4;
        }
        field_264_aY = (k + 1) * 2;
        field_263_aZ = j + k;
    }

    public float getStrVsBlock(ItemStack itemstack, Block block)
    {
        for(int i = 0; i < blocksEffectiveAgainst.length; i++)
        {
            if(blocksEffectiveAgainst[i] == block)
            {
                return field_264_aY;
            }
        }

        return 1.0F;
    }

    public void func_9201_a(ItemStack itemstack, EntityLiving entityliving)
    {
        itemstack.damageItem(2);
    }

    public void hitBlock(ItemStack itemstack, int i, int j, int k, int l)
    {
        itemstack.damageItem(1);
    }

    public int func_9203_a(Entity entity)
    {
        return field_263_aZ;
    }

    private Block blocksEffectiveAgainst[];
    private float field_264_aY;
    private int field_263_aZ;
    protected int field_262_a;
}
