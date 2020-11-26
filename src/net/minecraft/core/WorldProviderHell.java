package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.File;

public class WorldProviderHell extends WorldProvider
{

    public WorldProviderHell()
    {
    }

    public void func_4090_a()
    {
        field_4301_b = new WorldChunkManagerHell(MobSpawnerBase.hell, 1.0D, 0.0D);
        field_6167_c = true;
        field_6166_d = true;
        field_4306_c = true;
        field_6165_g = -1;
    }

    protected void generateLightBrightnessTable()
    {
        float f = 0.1F;
        for(int i = 0; i <= 15; i++)
        {
            float f1 = 1.0F - (float)i / 15F;
            lightBrightnessTable[i] = ((1.0F - f1) / (f1 * 3F + 1.0F)) * (1.0F - f) + f;
        }

    }

    public IChunkProvider getChunkProvider()
    {
        return new ChunkProviderHell(field_4302_a, field_4302_a.randomSeed);
    }

    public IChunkLoader getChunkLoader(File file)
    {
        File file1 = new File(file, "DIM-1");
        file1.mkdirs();
        return new ChunkLoader(file1, true);
    }

    public boolean canCoordinateBeSpawn(int i, int j)
    {
        int k = field_4302_a.func_528_f(i, j);
        if(k == Block.bedrock.blockID)
        {
            return false;
        }
        if(k == 0)
        {
            return false;
        }
        return Block.field_540_p[k];
    }

    public float func_4089_a(long l, float f)
    {
        return 0.5F;
    }
}
