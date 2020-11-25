package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ItemSpade extends ItemTool
{

    public ItemSpade(int i, int j)
    {
        super(i, 1, j, field_4206_bb);
    }

    public boolean canHarvestBlock(Block block)
    {
        if(block == Block.snow)
        {
            return true;
        }
        return block == Block.blockSnow;
    }

    private static Block field_4206_bb[];

    static 
    {
        field_4206_bb = (new Block[] {
            Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay
        });
    }
}
