package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class BlockBreakable extends Block
{

    protected BlockBreakable(int i, int j, Material material, boolean flag)
    {
        super(i, j, material);
        field_6084_a = flag;
    }

    public boolean allowsAttachment()
    {
        return false;
    }

    public boolean isSideInsideCoordinate(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        int i1 = iblockaccess.getBlockId(i, j, k);
        if(!field_6084_a && i1 == blockID)
        {
            return false;
        } else
        {
            return super.isSideInsideCoordinate(iblockaccess, i, j, k, l);
        }
    }

    private boolean field_6084_a;
}
