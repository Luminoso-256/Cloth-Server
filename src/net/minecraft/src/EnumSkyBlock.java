package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public enum EnumSkyBlock
{
    Sky(15),
    Block(0);
/*
    public static EnumSkyBlock[] func_4109_values()
    {
        return (EnumSkyBlock[])field_983_d.clone();
    }

    public static EnumSkyBlock valueOf(String s)
    {
        return (EnumSkyBlock)Enum.valueOf(EnumSkyBlock.class, s);
    }
*/
    private EnumSkyBlock(int j)
    {
        field_984_c = j;
    }
/*
    public static final EnumSkyBlock Sky;
    public static final EnumSkyBlock Block;
*/
    public final int field_984_c;
/*
    private static final EnumSkyBlock field_983_d[]; /* synthetic field */
/*
    static 
    {
        Sky = new EnumSkyBlock("Sky", 0, 15);
        Block = new EnumSkyBlock("Block", 1, 0);
        field_983_d = (new EnumSkyBlock[] {
            Sky, Block
        });
    }
*/
}
