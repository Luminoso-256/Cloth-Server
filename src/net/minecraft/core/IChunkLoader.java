package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.IOException;

public interface IChunkLoader
{

    public abstract Chunk func_659_a(World world, int i, int j) throws IOException;

    public abstract void func_662_a(World world, Chunk chunk) throws IOException;

    public abstract void func_4104_b(World world, Chunk chunk) throws IOException;

    public abstract void func_661_a();

    public abstract void func_660_b();
}
