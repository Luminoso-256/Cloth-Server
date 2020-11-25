package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.File;

public class WorldProvider
{

    public WorldProvider()
    {
        field_6167_c = false;
        field_6166_d = false;
        field_4306_c = false;
        lightBrightnessTable = new float[16];
        field_6165_g = 0;
        field_6164_h = new float[4];
    }

    public final void func_4093_a(World world)
    {
        field_4302_a = world;
        func_4090_a();
        generateLightBrightnessTable();
    }

    protected void generateLightBrightnessTable()
    {
        float f = 0.05F;
        for(int i = 0; i <= 15; i++)
        {
            float f1 = 1.0F - (float)i / 15F;
            lightBrightnessTable[i] = ((1.0F - f1) / (f1 * 3F + 1.0F)) * (1.0F - f) + f;
        }

    }

    protected void func_4090_a()
    {
        field_4301_b = new WorldChunkManager(field_4302_a);
    }

    public IChunkProvider getChunkProvider()
    {
        return new ChunkProviderGenerate(field_4302_a, field_4302_a.randomSeed);
    }

    public IChunkLoader getChunkLoader(File file)
    {
        return new ChunkLoader(file, true);
    }

    public boolean canCoordinateBeSpawn(int i, int j)
    {
        int k = field_4302_a.func_528_f(i, j);
        return k == Block.sand.blockID;
    }

    public float func_4089_a(long l, float f)
    {
        int i = (int)(l % 24000L);
        float f1 = ((float)i + f) / 24000F - 0.25F;
        if(f1 < 0.0F)
        {
            f1++;
        }
        if(f1 > 1.0F)
        {
            f1--;
        }
        float f2 = f1;
        f1 = 1.0F - (float)((Math.cos((double)f1 * 3.1415926535897931D) + 1.0D) / 2D);
        f1 = f2 + (f1 - f2) / 3F;
        return f1;
    }

    public static WorldProvider func_4091_a(int i)
    {
        if(i == 0)
        {
            return new WorldProvider();
        }
        if(i == -1)
        {
            return new WorldProviderHell();
        } else
        {
            return null;
        }
    }

    public World field_4302_a;
    public WorldChunkManager field_4301_b;
    public boolean field_6167_c;
    public boolean field_6166_d;
    public boolean field_4306_c;
    public float lightBrightnessTable[];
    public int field_6165_g;
    private float field_6164_h[];
}
