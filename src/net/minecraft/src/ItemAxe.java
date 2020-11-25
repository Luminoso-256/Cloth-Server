package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ItemAxe extends ItemTool
{

    public ItemAxe(int i, int j)
    {
        super(i, 3, j, field_4207_bb);
    }

    private static Block field_4207_bb[];

    static 
    {
        field_4207_bb = (new Block[] {
            Block.planks, Block.bookShelf, Block.wood, Block.crate
        });
    }
}
