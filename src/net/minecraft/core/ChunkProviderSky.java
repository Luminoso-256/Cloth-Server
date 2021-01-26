// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.core;

import net.minecraft.cloth.file.GameruleManager;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            IChunkProvider, MapGenCaves, NoiseGeneratorOctaves, Block, 
//            BiomeGenBase, Chunk, World, WorldChunkManager, 
//            MapGenBase, BlockSand, WorldGenLakes, WorldGenDungeons, 
//            WorldGenClay, WorldGenMinable, WorldGenMelon, WorldGenTallGrass, 
//            BlockTallGrass, WorldGenShrub, WorldGenerator, WorldGenFlowers, 
//            BlockFlower, WorldGenReed, WorldGenPumpkin, WorldGenCactus, 
//            WorldGenLiquids, Material, IProgressUpdate

public class ChunkProviderSky
    implements IChunkProvider
{

    public ChunkProviderSky(World world, long l)
    {
        field_28077_r = new double[256];
        field_28076_s = new double[256];
        field_28075_t = new double[256];
        field_28074_u = new MapGenCaves();
        field_28086_i = new int[32][32];
        field_28079_p = world;
        field_28085_j = new Random(l);
        field_28084_k = new NoiseGeneratorOctaves(field_28085_j, 16);
        field_28083_l = new NoiseGeneratorOctaves(field_28085_j, 16);
        field_28082_m = new NoiseGeneratorOctaves(field_28085_j, 8);
        field_28081_n = new NoiseGeneratorOctaves(field_28085_j, 4);
        field_28080_o = new NoiseGeneratorOctaves(field_28085_j, 4);
        field_28094_a = new NoiseGeneratorOctaves(field_28085_j, 10);
        field_28093_b = new NoiseGeneratorOctaves(field_28085_j, 16);
        field_28092_c = new NoiseGeneratorOctaves(field_28085_j, 8);
    }

    public void func_28070_a(int i, int j, byte abyte0[], MobSpawnerBase abiomegenbase[], double ad[])
    {
        byte byte0 = 2;
        int k = byte0 + 1;
        byte byte1 = 33;
        int l = byte0 + 1;
        field_28078_q = func_28071_a(field_28078_q, i * byte0, 0, j * byte0, k, byte1, l);
        for(int i1 = 0; i1 < byte0; i1++)
        {
            for(int j1 = 0; j1 < byte0; j1++)
            {
                for(int k1 = 0; k1 < 32; k1++)
                {
                    double d = 0.25D;
                    double d1 = field_28078_q[((i1 + 0) * l + (j1 + 0)) * byte1 + (k1 + 0)];
                    double d2 = field_28078_q[((i1 + 0) * l + (j1 + 1)) * byte1 + (k1 + 0)];
                    double d3 = field_28078_q[((i1 + 1) * l + (j1 + 0)) * byte1 + (k1 + 0)];
                    double d4 = field_28078_q[((i1 + 1) * l + (j1 + 1)) * byte1 + (k1 + 0)];
                    double d5 = (field_28078_q[((i1 + 0) * l + (j1 + 0)) * byte1 + (k1 + 1)] - d1) * d;
                    double d6 = (field_28078_q[((i1 + 0) * l + (j1 + 1)) * byte1 + (k1 + 1)] - d2) * d;
                    double d7 = (field_28078_q[((i1 + 1) * l + (j1 + 0)) * byte1 + (k1 + 1)] - d3) * d;
                    double d8 = (field_28078_q[((i1 + 1) * l + (j1 + 1)) * byte1 + (k1 + 1)] - d4) * d;
                    for(int l1 = 0; l1 < 4; l1++)
                    {
                        double d9 = 0.125D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for(int i2 = 0; i2 < 8; i2++)
                        {
                            int j2 = i2 + i1 * 8 << 11 | 0 + j1 * 8 << 7 | k1 * 4 + l1;
                            char c = '\200';
                            double d14 = 0.125D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            for(int k2 = 0; k2 < 8; k2++)
                            {
                                int l2 = 0;
                                if(d15 > 0.0D)
                                {
                                    l2 = Block.stone.blockID;
                                }
                                abyte0[j2] = (byte)l2;
                                j2 += c;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }

                }

            }

        }

    }

    public void func_28069_a(int i, int j, byte abyte0[], MobSpawnerBase abiomegenbase[])
    {
        double d = 0.03125D;
        field_28077_r = field_28081_n.func_648_a(field_28077_r, i * 16, j * 16, 0.0D, 16, 16, 1, d, d, 1.0D);
        field_28076_s = field_28081_n.func_648_a(field_28076_s, i * 16, 109.0134D, j * 16, 16, 1, 16, d, 1.0D, d);
        field_28075_t = field_28080_o.func_648_a(field_28075_t, i * 16, j * 16, 0.0D, 16, 16, 1, d * 2D, d * 2D, d * 2D);
        for(int k = 0; k < 16; k++)
        {
            for(int l = 0; l < 16; l++)
            {
                MobSpawnerBase biomegenbase = abiomegenbase[k + l * 16];
                int i1 = (int)(field_28075_t[k + l * 16] / 3D + 3D + field_28085_j.nextDouble() * 0.25D);
                int j1 = -1;
                byte byte0 = biomegenbase.topBlock;
                byte byte1 = biomegenbase.fillerBlock;
                for(int k1 = 127; k1 >= 0; k1--)
                {
                    int l1 = (l * 16 + k) * 128 + k1;
                    byte byte2 = abyte0[l1];
                    if(byte2 == 0)
                    {
                        j1 = -1;
                        continue;
                    }
                    if(byte2 != Block.stone.blockID)
                    {
                        continue;
                    }
                    if(j1 == -1)
                    {
                        if(i1 <= 0)
                        {
                            byte0 = 0;
                            byte1 = (byte)Block.stone.blockID;
                        }
                        j1 = i1;
                        if(k1 >= 0)
                        {
                            abyte0[l1] = byte0;
                        } else
                        {
                            abyte0[l1] = byte1;
                        }
                        continue;
                    }
                    if(j1 <= 0)
                    {
                        continue;
                    }
                    j1--;
                    abyte0[l1] = byte1;
//                    if(j1 == 0 && byte1 == Block.sand.blockID)
//                    {
//                        j1 = field_28085_j.nextInt(4);
//                        byte1 = (byte)Block.sandStone.blockID;
//                    }
                }

            }

        }

    }

    public Chunk loadChunk(int i, int j)
    {
        return provideChunk(i, j);
    }

    public Chunk provideChunk(int i, int j)
    {
        field_28085_j.setSeed((long)i * 0x4f9939f508L + (long)j * 0x1ef1565bd5L);
        byte abyte0[] = new byte[32768];
        Chunk chunk = new Chunk(field_28079_p, abyte0, i, j);
        field_28073_v = field_28079_p.getWorldChunkManager().loadBlockGeneratorData(field_28073_v, i * 16, j * 16, 16, 16);
        double ad[] = field_28079_p.getWorldChunkManager().temperature;
        func_28070_a(i, j, abyte0, field_28073_v, ad);
        func_28069_a(i, j, abyte0, field_28073_v);
        field_28074_u.func_667_a(this, field_28079_p, i, j, abyte0);
        chunk.func_353_b();
        return chunk;
    }

    private double[] func_28071_a(double ad[], int i, int j, int k, int l, int i1, int j1)
    {
        if(ad == null)
        {
            ad = new double[l * i1 * j1];
        }
        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        double ad1[] = field_28079_p.getWorldChunkManager().temperature;
        double ad2[] = field_28079_p.getWorldChunkManager().humidity;
        field_28088_g = field_28094_a.func_4103_a(field_28088_g, i, k, l, j1, 1.121D, 1.121D, 0.5D);
        field_28087_h = field_28093_b.func_4103_a(field_28087_h, i, k, l, j1, 200D, 200D, 0.5D);
        d *= 2D;
        field_28091_d = field_28082_m.func_648_a(field_28091_d, i, j, k, l, i1, j1, d / 80D, d1 / 160D, d / 80D);
        field_28090_e = field_28084_k.func_648_a(field_28090_e, i, j, k, l, i1, j1, d, d1, d);
        field_28089_f = field_28083_l.func_648_a(field_28089_f, i, j, k, l, i1, j1, d, d1, d);
        int k1 = 0;
        int l1 = 0;
        int i2 = 16 / l;
        for(int j2 = 0; j2 < l; j2++)
        {
            int k2 = j2 * i2 + i2 / 2;
            for(int l2 = 0; l2 < j1; l2++)
            {
                int i3 = l2 * i2 + i2 / 2;
                double d2 = ad1[k2 * 16 + i3];
                double d3 = ad2[k2 * 16 + i3] * d2;
                double d4 = 1.0D - d3;
                d4 *= d4;
                d4 *= d4;
                d4 = 1.0D - d4;
                double d5 = (field_28088_g[l1] + 256D) / 512D;
                d5 *= d4;
                if(d5 > 1.0D)
                {
                    d5 = 1.0D;
                }
                double d6 = field_28087_h[l1] / 8000D;
                if(d6 < 0.0D)
                {
                    d6 = -d6 * 0.29999999999999999D;
                }
                d6 = d6 * 3D - 2D;
                if(d6 > 1.0D)
                {
                    d6 = 1.0D;
                }
                d6 /= 8D;
                d6 = 0.0D;
                if(d5 < 0.0D)
                {
                    d5 = 0.0D;
                }
                d5 += 0.5D;
                d6 = (d6 * (double)i1) / 16D;
                l1++;
                double d7 = (double)i1 / 2D;
                for(int j3 = 0; j3 < i1; j3++)
                {
                    double d8 = 0.0D;
                    double d9 = (((double)j3 - d7) * 8D) / d5;
                    if(d9 < 0.0D)
                    {
                        d9 *= -1D;
                    }
                    double d10 = field_28090_e[k1] / 512D;
                    double d11 = field_28089_f[k1] / 512D;
                    double d12 = (field_28091_d[k1] / 10D + 1.0D) / 2D;
                    if(d12 < 0.0D)
                    {
                        d8 = d10;
                    } else
                    if(d12 > 1.0D)
                    {
                        d8 = d11;
                    } else
                    {
                        d8 = d10 + (d11 - d10) * d12;
                    }
                    d8 -= 8D;
                    int k3 = 32;
                    if(j3 > i1 - k3)
                    {
                        double d13 = (float)(j3 - (i1 - k3)) / ((float)k3 - 1.0F);
                        d8 = d8 * (1.0D - d13) + -30D * d13;
                    }
                    k3 = 8;
                    if(j3 < k3)
                    {
                        double d14 = (float)(k3 - j3) / ((float)k3 - 1.0F);
                        d8 = d8 * (1.0D - d14) + -30D * d14;
                    }
                    ad[k1] = d8;
                    k1++;
                }

            }

        }

        return ad;
    }

    public boolean chunkExists(int i, int j)
    {
        return true;
    }

    public void populate(IChunkProvider ichunkprovider, int i, int j)
    {
        BlockSand.fallInstantly = true;
        int k = i * 16;
        int l = j * 16;
        MobSpawnerBase biomegenbase = field_28079_p.getWorldChunkManager().getBiomeGenAt(k + 16, l + 16);
        field_28085_j.setSeed(field_28079_p.randomSeed);
        long l1 = (field_28085_j.nextLong() / 2L) * 2L + 1L;
        long l2 = (field_28085_j.nextLong() / 2L) * 2L + 1L;
        field_28085_j.setSeed((long)i * l1 + (long)j * l2 ^ field_28079_p.randomSeed);
        double d = 0.25D;
        if(field_28085_j.nextInt(4) == 0)
        {
            int i1 = k + field_28085_j.nextInt(16) + 8;
            int i5 = field_28085_j.nextInt(128);
            int k8 = l + field_28085_j.nextInt(16) + 8;
            (new WorldGenLakes(Block.waterStill.blockID)).generate(field_28079_p, field_28085_j, i1, i5, k8);
        }
        if(field_28085_j.nextInt(8) == 0)
        {
            int j1 = k + field_28085_j.nextInt(16) + 8;
            int j5 = field_28085_j.nextInt(field_28085_j.nextInt(120) + 8);
            int l8 = l + field_28085_j.nextInt(16) + 8;
            if(j5 < 64 || field_28085_j.nextInt(10) == 0)
            {
                (new WorldGenLakes(Block.lavaStill.blockID)).generate(field_28079_p, field_28085_j, j1, j5, l8);
            }
        }
        for(int k1 = 0; k1 < 8; k1++)
        {
            int k5 = k + field_28085_j.nextInt(16) + 8;
            int i9 = field_28085_j.nextInt(128);
            int l11 = l + field_28085_j.nextInt(16) + 8;
            (new WorldGenDungeons()).generate(field_28079_p, field_28085_j, k5, i9, l11);
        }

        for(int i2 = 0; i2 < 10; i2++)
        {
            int l5 = k + field_28085_j.nextInt(16);
            int j9 = field_28085_j.nextInt(128);
            int i12 = l + field_28085_j.nextInt(16);
            (new WorldGenClay(32)).generate(field_28079_p, field_28085_j, l5, j9, i12);
        }

        for(int j2 = 0; j2 < 20; j2++)
        {
            int i6 = k + field_28085_j.nextInt(16);
            int k9 = field_28085_j.nextInt(128);
            int j12 = l + field_28085_j.nextInt(16);
            (new WorldGenMinable(Block.dirt.blockID, 32)).generate(field_28079_p, field_28085_j, i6, k9, j12);
        }

        for(int i3 = 0; i3 < 10; i3++)
        {
            int k6 = k + field_28085_j.nextInt(16);
            int i10 = field_28085_j.nextInt(128);
            int l12 = l + field_28085_j.nextInt(16);
            (new WorldGenMinable(Block.gravel.blockID, 32)).generate(field_28079_p, field_28085_j, k6, i10, l12);
        }

        for(int j3 = 0; j3 < 20; j3++)
        {
            int l6 = k + field_28085_j.nextInt(16);
            int j10 = field_28085_j.nextInt(128);
            int i13 = l + field_28085_j.nextInt(16);
            (new WorldGenMinable(Block.oreCoal.blockID, 16)).generate(field_28079_p, field_28085_j, l6, j10, i13);
        }

        for(int k3 = 0; k3 < 20; k3++)
        {
            int i7 = k + field_28085_j.nextInt(16);
            int k10 = field_28085_j.nextInt(64);
            int j13 = l + field_28085_j.nextInt(16);
            (new WorldGenMinable(Block.oreIron.blockID, 8)).generate(field_28079_p, field_28085_j, i7, k10, j13);
        }

        for(int l3 = 0; l3 < 2; l3++)
        {
            int j7 = k + field_28085_j.nextInt(16);
            int l10 = field_28085_j.nextInt(32);
            int k13 = l + field_28085_j.nextInt(16);
            (new WorldGenMinable(Block.oreGold.blockID, 8)).generate(field_28079_p, field_28085_j, j7, l10, k13);
        }

        for(int i4 = 0; i4 < 8; i4++)
        {
            int k7 = k + field_28085_j.nextInt(16);
            int i11 = field_28085_j.nextInt(16);
            int l13 = l + field_28085_j.nextInt(16);
            (new WorldGenMinable(Block.oreRedstone.blockID, 7)).generate(field_28079_p, field_28085_j, k7, i11, l13);
        }

        for(int j4 = 0; j4 < 1; j4++)
        {
            int l7 = k + field_28085_j.nextInt(16);
            int j11 = field_28085_j.nextInt(16);
            int i14 = l + field_28085_j.nextInt(16);
            (new WorldGenMinable(Block.oreDiamond.blockID, 7)).generate(field_28079_p, field_28085_j, l7, j11, i14);
        }

        d = 0.5D;
        int l4 = (int)((field_28092_c.func_647_a((double)k * d, (double)l * d) / 8D + field_28085_j.nextDouble() * 4D + 4D) / 3D);
        int j8 = 0;
        if(field_28085_j.nextInt(10) == 0)
        {
            j8++;
        }
        if(biomegenbase == MobSpawnerBase.forest)
        {
            j8 += l4 + 5;
        }
        if(biomegenbase == MobSpawnerBase.rainforest)
        {
            j8 += l4 + 5;
        }
        if(biomegenbase == MobSpawnerBase.seasonalForest)
        {
            j8 += l4 + 2;
        }
        if(biomegenbase == MobSpawnerBase.taiga)
        {
            j8 += l4 + 5;
        }
        if(biomegenbase == MobSpawnerBase.desert)
        {
            j8 -= 20;
        }
        if(biomegenbase == MobSpawnerBase.tundra)
        {
            j8 -= 20;
        }
        if(biomegenbase == MobSpawnerBase.plains)
        {
            j8 -= 20;
        }

        for(int l14 = 0; l14 < j8; l14++)
        {
            int i17 = k + field_28085_j.nextInt(16) + 8;
            int i20 = l + field_28085_j.nextInt(16) + 8;
            Object obj = new WorldGenTrees();
            GameruleManager gameruleManager = GameruleManager.getInstance();
            if (!gameruleManager.getGamerule("nobigtree", false) & field_28085_j.nextInt(10) == 0) {
                obj = new WorldGenBigTree();
            }
            if (biomegenbase == MobSpawnerBase.rainforest && field_28085_j.nextInt(3) == 0) {
                obj = new WorldGenBigTree();
            }
            ((WorldGenerator)obj).func_420_a(1.0D, 1.0D, 1.0D);
            ((WorldGenerator)obj).generate(field_28079_p, field_28085_j, i17, field_28079_p.getHeightValue(i17, i20), i20);
        }

        for(int i15 = 0; i15 < 2; i15++)
        {
            int j17 = k + field_28085_j.nextInt(16) + 8;
            int j20 = field_28085_j.nextInt(128);
            int i23 = l + field_28085_j.nextInt(16) + 8;
            (new WorldGenFlowers(Block.plantYellow.blockID)).generate(field_28079_p, field_28085_j, j17, j20, i23);
        }

        if(field_28085_j.nextInt(2) == 0)
        {
            int j15 = k + field_28085_j.nextInt(16) + 8;
            int k17 = field_28085_j.nextInt(128);
            int k20 = l + field_28085_j.nextInt(16) + 8;
            (new WorldGenFlowers(Block.plantRed.blockID)).generate(field_28079_p, field_28085_j, j15, k17, k20);
        }
        if(field_28085_j.nextInt(4) == 0)
        {
            int k15 = k + field_28085_j.nextInt(16) + 8;
            int l17 = field_28085_j.nextInt(128);
            int l20 = l + field_28085_j.nextInt(16) + 8;
            (new WorldGenFlowers(Block.mushroomBrown.blockID)).generate(field_28079_p, field_28085_j, k15, l17, l20);
        }
        if(field_28085_j.nextInt(8) == 0)
        {
            int l15 = k + field_28085_j.nextInt(16) + 8;
            int i18 = field_28085_j.nextInt(128);
            int i21 = l + field_28085_j.nextInt(16) + 8;
            (new WorldGenFlowers(Block.mushroomRed.blockID)).generate(field_28079_p, field_28085_j, l15, i18, i21);
        }
        for(int i16 = 0; i16 < 10; i16++)
        {
            int j18 = k + field_28085_j.nextInt(16) + 8;
            int j21 = field_28085_j.nextInt(128);
            int j23 = l + field_28085_j.nextInt(16) + 8;
            (new WorldGenReed()).generate(field_28079_p, field_28085_j, j18, j21, j23);
        }

        if(field_28085_j.nextInt(32) == 0)
        {
            int j16 = k + field_28085_j.nextInt(16) + 8;
            int k18 = field_28085_j.nextInt(128);
            int k21 = l + field_28085_j.nextInt(16) + 8;
            (new WorldGenPumpkin()).generate(field_28079_p, field_28085_j, j16, k18, k21);
        }
        int k16 = 0;
        if(biomegenbase == MobSpawnerBase.desert)
        {
            k16 += 10;
        }
        for(int l18 = 0; l18 < k16; l18++)
        {
            int l21 = k + field_28085_j.nextInt(16) + 8;
            int k23 = field_28085_j.nextInt(128);
            int l24 = l + field_28085_j.nextInt(16) + 8;
            (new WorldGenCactus()).generate(field_28079_p, field_28085_j, l21, k23, l24);
        }

        for(int i19 = 0; i19 < 50; i19++)
        {
            int i22 = k + field_28085_j.nextInt(16) + 8;
            int l23 = field_28085_j.nextInt(field_28085_j.nextInt(120) + 8);
            int i25 = l + field_28085_j.nextInt(16) + 8;
            (new WorldGenLiquids(Block.waterMoving.blockID)).generate(field_28079_p, field_28085_j, i22, l23, i25);
        }

        for(int j19 = 0; j19 < 20; j19++)
        {
            int j22 = k + field_28085_j.nextInt(16) + 8;
            int i24 = field_28085_j.nextInt(field_28085_j.nextInt(field_28085_j.nextInt(112) + 8) + 8);
            int j25 = l + field_28085_j.nextInt(16) + 8;
            (new WorldGenLiquids(Block.lavaMoving.blockID)).generate(field_28079_p, field_28085_j, j22, i24, j25);
        }

        field_28072_w = field_28079_p.getWorldChunkManager().getTemperatures(field_28072_w, k + 8, l + 8, 16, 16);
        for(int k19 = k + 8; k19 < k + 8 + 16; k19++)
        {
            for(int k22 = l + 8; k22 < l + 8 + 16; k22++)
            {
                int j24 = k19 - (k + 8);
                int k25 = k22 - (l + 8);
                int l25 = field_28079_p.func_4075_e(k19, k22);
                double d1 = field_28072_w[j24 * 16 + k25] - ((double)(l25 - 64) / 64D) * 0.29999999999999999D;
                if(d1 < 0.5D && l25 > 0 && l25 < 128 && field_28079_p.getBlockId(k19, l25, k22) == 0 && field_28079_p.getBlockMaterial(k19, l25 - 1, k22).func_218_c() && field_28079_p.getBlockMaterial(k19, l25 - 1, k22) != Material.ice)
                {
                    field_28079_p.setBlockWithNotify(k19, l25, k22, Block.snow.blockID);
                }
            }

        }

        BlockSand.fallInstantly = false;
    }

    public boolean saveWorld(boolean flag, IProgressUpdate iprogressupdate)
    {
        return true;
    }

    public boolean func_361_a()
    {
        return false;
    }

    public boolean func_364_b()
    {
        return true;
    }

    private Random field_28085_j;
    private NoiseGeneratorOctaves field_28084_k;
    private NoiseGeneratorOctaves field_28083_l;
    private NoiseGeneratorOctaves field_28082_m;
    private NoiseGeneratorOctaves field_28081_n;
    private NoiseGeneratorOctaves field_28080_o;
    public NoiseGeneratorOctaves field_28094_a;
    public NoiseGeneratorOctaves field_28093_b;
    public NoiseGeneratorOctaves field_28092_c;
    private World field_28079_p;
    private double field_28078_q[];
    private double field_28077_r[];
    private double field_28076_s[];
    private double field_28075_t[];
    private MapGenBase field_28074_u;
    private MobSpawnerBase field_28073_v[];
    double field_28091_d[];
    double field_28090_e[];
    double field_28089_f[];
    double field_28088_g[];
    double field_28087_h[];
    int field_28086_i[][];
    private double field_28072_w[];
}
