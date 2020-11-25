package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ItemArmor extends Item
{

    public ItemArmor(int i, int j, int k, int l)
    {
        super(i);
        field_254_a = j;
        field_257_aX = l;
        field_255_aZ = k;
        field_256_aY = field_259_ba[l];
        maxDamage = field_258_bb[l] * 3 << j;
        maxStackSize = 1;
    }

    private static final int field_259_ba[] = {
        3, 8, 6, 3
    };
    private static final int field_258_bb[] = {
        11, 16, 15, 13
    };
    public final int field_254_a;
    public final int field_257_aX;
    public final int field_256_aY;
    public final int field_255_aZ;

}
