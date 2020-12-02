package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ChunkCoordIntPair {

    public int field_152_a;
    public int field_151_b;

    public ChunkCoordIntPair(int i, int j) {
        field_152_a = i;
        field_151_b = j;
    }

    public int hashCode() {
        return field_152_a << 8 | field_151_b;
    }

    public boolean equals(Object obj) {
        ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair) obj;
        return chunkcoordintpair.field_152_a == field_152_a && chunkcoordintpair.field_151_b == field_151_b;
    }

    public double func_73_a(Entity entity) {
        double d = field_152_a * 16 + 8;
        double d1 = field_151_b * 16 + 8;
        double d2 = d - entity.posX;
        double d3 = d1 - entity.posZ;
        return d2 * d2 + d3 * d3;
    }
}
