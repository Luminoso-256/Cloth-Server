package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ChunkPosition
{

    public ChunkPosition(int i, int j, int k)
    {
        field_846_a = i;
        field_845_b = j;
        field_847_c = k;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof ChunkPosition)
        {
            ChunkPosition chunkposition = (ChunkPosition)obj;
            return chunkposition.field_846_a == field_846_a && chunkposition.field_845_b == field_845_b && chunkposition.field_847_c == field_847_c;
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return field_846_a * 0x88f9fa + field_845_b * 0xef88b + field_847_c;
    }

    public final int field_846_a;
    public final int field_845_b;
    public final int field_847_c;
}
