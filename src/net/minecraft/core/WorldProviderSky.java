// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.core;


// Referenced classes of package net.minecraft.src:
//            WorldProvider, WorldChunkManager, ChunkProviderSky, World, 
//            MathHelper, Vec3D, Block, Material, 
//            IChunkProvider

public class WorldProviderSky extends WorldProvider
{

    public WorldProviderSky()
    {
    }

    public void registerWorldChunkManager()
    {
        worldChunkMgr = new WorldChunkManager(field_4302_a);
        worldType = 1;
    }

    public IChunkProvider getChunkProvider()
    {
        return new ChunkProviderSky(field_4302_a, field_4302_a.randomSeed);
    }

    // TODO: Implement
//    public float calculateCelestialAngle(long l, float f)
//    {
//        int i = (int)(l % 24000L);
//        float f1 = ((float)i + f) / 24000F - 0.25F;
//        if(f1 < 0.0F)
//        {
//            f1++;
//        }
//        if(f1 > 1.0F)
//        {
//            f1--;
//        }
//        float f2 = f1;
//        f1 = 1.0F - (float)((Math.cos((double)f1 * 3.1415926535897931D) + 1.0D) / 2D);
//        f1 = f2 + (f1 - f2) / 3F;
//        return f1;
//    }

    public float[] calcSunriseSunsetColors(float f, float f1)
    {
        return null;
    }

    public Vec3D func_4096_a(float f, float f1)
    {
        int i = 0x8080a0;
        float f2 = MathHelper.cos(f * 3.141593F * 2.0F) * 2.0F + 0.5F;
        if(f2 < 0.0F)
        {
            f2 = 0.0F;
        }
        if(f2 > 1.0F)
        {
            f2 = 1.0F;
        }
        float f3 = (float)(i >> 16 & 0xff) / 255F;
        float f4 = (float)(i >> 8 & 0xff) / 255F;
        float f5 = (float)(i & 0xff) / 255F;
        f3 *= f2 * 0.94F + 0.06F;
        f4 *= f2 * 0.94F + 0.06F;
        f5 *= f2 * 0.91F + 0.09F;
        return Vec3D.createVector(f3, f4, f5);
    }

    public boolean func_28112_c()
    {
        return false;
    }

    public float getCloudHeight()
    {
        return 8F;
    }

    public boolean canCoordinateBeSpawn(int i, int j) {
        int k = field_4302_a.func_528_f(i, j);
        if (k == 0) {
            return false;
        }
        return Block.field_540_p[k];
    }

    public boolean canRespawnHere()
    {
        return true;
    }
}
