package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public interface IChunkProvider
{

    public abstract boolean chunkExists(int i, int j);

    public abstract Chunk func_363_b(int i, int j);

    public abstract void populate(IChunkProvider ichunkprovider, int i, int j);

    public abstract boolean saveWorld(boolean flag, IProgressUpdate iprogressupdate);

    public abstract boolean func_361_a();

    public abstract boolean func_364_b();
}
