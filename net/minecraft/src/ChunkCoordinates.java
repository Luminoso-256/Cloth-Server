package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


final class ChunkCoordinates
{

    public ChunkCoordinates(int i, int j)
    {
        field_529_a = i;
        field_528_b = j;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof ChunkCoordinates)
        {
            ChunkCoordinates chunkcoordinates = (ChunkCoordinates)obj;
            return field_529_a == chunkcoordinates.field_529_a && field_528_b == chunkcoordinates.field_528_b;
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return field_529_a << 16 ^ field_528_b;
    }

    public final int field_529_a;
    public final int field_528_b;
}
