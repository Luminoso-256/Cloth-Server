package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ItemPickaxe extends ItemTool
{

    public ItemPickaxe(int i, int j)
    {
        super(i, 2, j, field_4209_bb);
        field_4208_bc = j;
    }

    public boolean canHarvestBlock(Block block)
    {
        if(block == Block.obsidian)
        {
            return field_4208_bc == 3;
        }
        if(block == Block.blockDiamond || block == Block.oreDiamond)
        {
            return field_4208_bc >= 2;
        }
        if(block == Block.blockGold || block == Block.oreGold)
        {
            return field_4208_bc >= 2;
        }
        if(block == Block.blockSteel || block == Block.oreIron)
        {
            return field_4208_bc >= 1;
        }
        if(block == Block.oreRedstone || block == Block.oreRedstoneGlowing)
        {
            return field_4208_bc >= 2;
        }
        if(block.blockMaterial == Material.rock)
        {
            return true;
        }
        return block.blockMaterial == Material.iron;
    }

    private static Block field_4209_bb[];
    private int field_4208_bc;

    static 
    {
        field_4209_bb = (new Block[] {
            Block.cobblestone, Block.stairDouble, Block.stairSingle, Block.stone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold, Block.oreGold, 
            Block.oreDiamond, Block.blockDiamond, Block.ice, Block.bloodStone
        });
    }
}
