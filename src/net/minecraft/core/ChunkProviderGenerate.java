package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.cloth.file.GameruleManager;

import java.util.Random;

public class ChunkProviderGenerate
    implements IChunkProvider
{

    public ChunkProviderGenerate(World world, long l)
    {
        field_698_r = new double[256];
        field_697_s = new double[256];
        field_696_t = new double[256];
        field_695_u = new MapGenCaves();
        field_707_i = new int[32][32];
        worldObj = world;
        rand = new Random(l);
        field_705_k = new NoiseGeneratorOctaves(rand, 16);
        field_704_l = new NoiseGeneratorOctaves(rand, 16);
        field_703_m = new NoiseGeneratorOctaves(rand, 8);
        field_702_n = new NoiseGeneratorOctaves(rand, 4);
        field_701_o = new NoiseGeneratorOctaves(rand, 4);
        field_715_a = new NoiseGeneratorOctaves(rand, 10);
        field_714_b = new NoiseGeneratorOctaves(rand, 16);
        field_713_c = new NoiseGeneratorOctaves(rand, 8);


    }

    public void generateTerrain(int i, int j, byte abyte0[], MobSpawnerBase amobspawnerbase[], double ad[])
    {
        byte byte0 = 4;
        byte byte1 = 64;
        int k = byte0 + 1;
        byte byte2 = 17;
        int l = byte0 + 1;
        field_4224_q = func_4058_a(field_4224_q, i * byte0, 0, j * byte0, k, byte2, l);

        for(int i1 = 0; i1 < byte0; i1++)
        {
            for(int j1 = 0; j1 < byte0; j1++)
            {
                for(int k1 = 0; k1 < 16; k1++)
                {
                    double d = 0.125D;
                    double d1 = field_4224_q[((i1 + 0) * l + (j1 + 0)) * byte2 + (k1 + 0)];
                    double d2 = field_4224_q[((i1 + 0) * l + (j1 + 1)) * byte2 + (k1 + 0)];
                    double d3 = field_4224_q[((i1 + 1) * l + (j1 + 0)) * byte2 + (k1 + 0)];
                    double d4 = field_4224_q[((i1 + 1) * l + (j1 + 1)) * byte2 + (k1 + 0)];
                    double d5 = (field_4224_q[((i1 + 0) * l + (j1 + 0)) * byte2 + (k1 + 1)] - d1) * d;
                    double d6 = (field_4224_q[((i1 + 0) * l + (j1 + 1)) * byte2 + (k1 + 1)] - d2) * d;
                    double d7 = (field_4224_q[((i1 + 1) * l + (j1 + 0)) * byte2 + (k1 + 1)] - d3) * d;
                    double d8 = (field_4224_q[((i1 + 1) * l + (j1 + 1)) * byte2 + (k1 + 1)] - d4) * d;
                    for(int l1 = 0; l1 < 8; l1++)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for(int i2 = 0; i2 < 4; i2++)
                        {
                            int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
                            char c = '\200';
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            for(int k2 = 0; k2 < 4; k2++)
                            {
                                GameruleManager gameruleManager = GameruleManager.getInstance();
                                double d17 = ad[(i1 * 4 + i2) * 16 + (j1 * 4 + k2)];
                                int l2 = 0;
                                if(k1 * 8 + l1 < byte1)
                                {
                                    if(gameruleManager.getGamerule("snowworld", false) | d17 < 0.5D && k1 * 8 + l1 >= byte1 - 1)
                                    {
                                        l2 = Block.ice.blockID;
                                    } else
                                    {
                                        l2 = Block.waterMoving.blockID;
                                    }
                                }
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

    public void replaceBlocksForBiome(int i, int j, byte abyte0[], MobSpawnerBase amobspawnerbase[])
    {
        byte byte0 = 64;
        double d = 0.03125D;
        field_698_r = field_702_n.func_648_a(field_698_r, i * 16, j * 16, 0.0D, 16, 16, 1, d, d, 1.0D);
        field_697_s = field_702_n.func_648_a(field_697_s, j * 16, 109.0134D, i * 16, 16, 1, 16, d, 1.0D, d);
        field_696_t = field_701_o.func_648_a(field_696_t, i * 16, j * 16, 0.0D, 16, 16, 1, d * 2D, d * 2D, d * 2D);
        for(int k = 0; k < 16; k++)
        {
            for(int l = 0; l < 16; l++)
            {
                MobSpawnerBase mobspawnerbase = amobspawnerbase[k * 16 + l];
                boolean flag = field_698_r[k + l * 16] + rand.nextDouble() * 0.20000000000000001D > 0.0D;
                boolean flag1 = field_697_s[k + l * 16] + rand.nextDouble() * 0.20000000000000001D > 3D;
                int i1 = (int)(field_696_t[k + l * 16] / 3D + 3D + rand.nextDouble() * 0.25D);
                int j1 = -1;
                byte byte1 = mobspawnerbase.topBlock;
                byte byte2 = mobspawnerbase.fillerBlock;
                for(int k1 = 127; k1 >= 0; k1--)
                {
                    int l1 = (k * 16 + l) * 128 + k1;
                    if(k1 <= 0 + rand.nextInt(5))
                    {
                        abyte0[l1] = (byte)Block.bedrock.blockID;
                        continue;
                    }
                    byte byte3 = abyte0[l1];
                    if(byte3 == 0)
                    {
                        j1 = -1;
                        continue;
                    }
                    if(byte3 != Block.stone.blockID)
                    {
                        continue;
                    }
                    if(j1 == -1)
                    {
                        if(i1 <= 0)
                        {
                            byte1 = 0;
                            byte2 = (byte)Block.stone.blockID;
                        } else
                        if(k1 >= byte0 - 4 && k1 <= byte0 + 1)
                        {
                            byte1 = mobspawnerbase.topBlock;
                            byte2 = mobspawnerbase.fillerBlock;
                            if(flag1)
                            {
                                byte1 = 0;
                            }
                            if(flag1)
                            {
                                byte2 = (byte)Block.gravel.blockID;
                            }
                            if(flag)
                            {
                                byte1 = (byte)Block.sand.blockID;
                            }
                            if(flag)
                            {
                                byte2 = (byte)Block.sand.blockID;
                            }
                        }
                        if(k1 < byte0 && byte1 == 0)
                        {
                            byte1 = (byte)Block.waterMoving.blockID;
                        }
                        j1 = i1;
                        if(k1 >= byte0 - 1)
                        {
                            abyte0[l1] = byte1;
                        } else
                        {
                            abyte0[l1] = byte2;
                        }
                        continue;
                    }
                    if(j1 > 0)
                    {
                        j1--;
                        abyte0[l1] = byte2;
                    }
                }

            }

        }

    }

    public Chunk func_363_b(int i, int j)
    {
        rand.setSeed((long)i * 0x4f9939f508L + (long)j * 0x1ef1565bd5L);
        byte abyte0[] = new byte[32768];
        Chunk chunk = new Chunk(worldObj, abyte0, i, j);
        biomesForGeneration = worldObj.func_4077_a().loadBlockGeneratorData(biomesForGeneration, i * 16, j * 16, 16, 16);
        double ad[] = worldObj.func_4077_a().temperature;
        generateTerrain(i, j, abyte0, biomesForGeneration, ad);
        replaceBlocksForBiome(i, j, abyte0, biomesForGeneration);
        field_695_u.func_667_a(this, worldObj, i, j, abyte0);
        chunk.func_353_b();
        return chunk;
    }

    private double[] func_4058_a(double ad[], int i, int j, int k, int l, int i1, int j1)
    {
        if(ad == null)
        {
            ad = new double[l * i1 * j1];
        }
        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        double ad1[] = worldObj.func_4077_a().temperature;
        double ad2[] = worldObj.func_4077_a().humidity;
        field_4226_g = field_715_a.func_4103_a(field_4226_g, i, k, l, j1, 1.121D, 1.121D, 0.5D);
        field_4225_h = field_714_b.func_4103_a(field_4225_h, i, k, l, j1, 200D, 200D, 0.5D);
        field_4229_d = field_703_m.func_648_a(field_4229_d, i, j, k, l, i1, j1, d / 80D, d1 / 160D, d / 80D);
        field_4228_e = field_705_k.func_648_a(field_4228_e, i, j, k, l, i1, j1, d, d1, d);
        field_4227_f = field_704_l.func_648_a(field_4227_f, i, j, k, l, i1, j1, d, d1, d);
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
                double d5 = (field_4226_g[l1] + 256D) / 512D;
                d5 *= d4;
                if(d5 > 1.0D)
                {
                    d5 = 1.0D;
                }
                double d6 = field_4225_h[l1] / 8000D;
                if(d6 < 0.0D)
                {
                    d6 = -d6 * 0.29999999999999999D;
                }
                d6 = d6 * 3D - 2D;
                if(d6 < 0.0D)
                {
                    d6 /= 2D;
                    if(d6 < -1D)
                    {
                        d6 = -1D;
                    }
                    d6 /= 1.3999999999999999D;
                    d6 /= 2D;
                    d5 = 0.0D;
                } else
                {
                    if(d6 > 1.0D)
                    {
                        d6 = 1.0D;
                    }
                    d6 /= 8D;
                }
                if(d5 < 0.0D)
                {
                    d5 = 0.0D;
                }
                d5 += 0.5D;
                d6 = (d6 * (double)i1) / 16D;
                double d7 = (double)i1 / 2D + d6 * 4D;
                l1++;
                for(int j3 = 0; j3 < i1; j3++)
                {
                    double d8 = 0.0D;
                    double d9 = (((double)j3 - d7) * 12D) / d5;
                    if(d9 < 0.0D)
                    {
                        d9 *= 4D;
                    }
                    double d10 = field_4228_e[k1] / 512D;
                    double d11 = field_4227_f[k1] / 512D;
                    double d12 = (field_4229_d[k1] / 10D + 1.0D) / 2D;
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
                    d8 -= d9;
                    if(j3 > i1 - 4)
                    {
                        double d13 = (float)(j3 - (i1 - 4)) / 3F;
                        d8 = d8 * (1.0D - d13) + -10D * d13;
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
        MobSpawnerBase mobspawnerbase = worldObj.func_4077_a().func_4067_a(k + 16, l + 16);
        rand.setSeed(worldObj.randomSeed);
        long l1 = (rand.nextLong() / 2L) * 2L + 1L;
        long l2 = (rand.nextLong() / 2L) * 2L + 1L;
        rand.setSeed((long)i * l1 + (long)j * l2 ^ worldObj.randomSeed);
        double d = 0.25D;
        if(rand.nextInt(4) == 0)
        {
            int i1 = k + rand.nextInt(16) + 8;
            int k4 = rand.nextInt(128);
            int k7 = l + rand.nextInt(16) + 8;
            (new WorldGenLakes(Block.waterMoving.blockID)).generate(worldObj, rand, i1, k4, k7);
        }
        if(rand.nextInt(8) == 0)
        {
            int j1 = k + rand.nextInt(16) + 8;
            int l4 = rand.nextInt(rand.nextInt(120) + 8);
            int l7 = l + rand.nextInt(16) + 8;
            if(l4 < 64 || rand.nextInt(10) == 0)
            {
                (new WorldGenLakes(Block.lavaMoving.blockID)).generate(worldObj, rand, j1, l4, l7);
            }
        }
        for(int k1 = 0; k1 < 8; k1++)
        {
            int i5 = k + rand.nextInt(16) + 8;
            int i8 = rand.nextInt(128);
            int j10 = l + rand.nextInt(16) + 8;
            (new WorldGenDungeons()).generate(worldObj, rand, i5, i8, j10);
        }

        for(int i2 = 0; i2 < 10; i2++)
        {
            int j5 = k + rand.nextInt(16);
            int j8 = rand.nextInt(128);
            int k10 = l + rand.nextInt(16);
            (new WorldGenClay(32)).generate(worldObj, rand, j5, j8, k10);
        }

        for(int j2 = 0; j2 < 20; j2++)
        {
            int k5 = k + rand.nextInt(16);
            int k8 = rand.nextInt(128);
            int l10 = l + rand.nextInt(16);
            (new WorldGenMinable(Block.dirt.blockID, 32)).generate(worldObj, rand, k5, k8, l10);
        }

        for(int k2 = 0; k2 < 10; k2++)
        {
            int l5 = k + rand.nextInt(16);
            int l8 = rand.nextInt(128);
            int i11 = l + rand.nextInt(16);
            (new WorldGenMinable(Block.gravel.blockID, 32)).generate(worldObj, rand, l5, l8, i11);
        }

        for(int i3 = 0; i3 < 20; i3++)
        {
            int i6 = k + rand.nextInt(16);
            int i9 = rand.nextInt(128);
            int j11 = l + rand.nextInt(16);
            (new WorldGenMinable(Block.oreCoal.blockID, 16)).generate(worldObj, rand, i6, i9, j11);
        }

        for(int j3 = 0; j3 < 20; j3++)
        {
            int j6 = k + rand.nextInt(16);
            int j9 = rand.nextInt(64);
            int k11 = l + rand.nextInt(16);
            (new WorldGenMinable(Block.oreIron.blockID, 8)).generate(worldObj, rand, j6, j9, k11);
        }

        for(int k3 = 0; k3 < 2; k3++)
        {
            int k6 = k + rand.nextInt(16);
            int k9 = rand.nextInt(32);
            int l11 = l + rand.nextInt(16);
            (new WorldGenMinable(Block.oreGold.blockID, 8)).generate(worldObj, rand, k6, k9, l11);
        }

        for(int l3 = 0; l3 < 8; l3++)
        {
            int l6 = k + rand.nextInt(16);
            int l9 = rand.nextInt(16);
            int i12 = l + rand.nextInt(16);
            (new WorldGenMinable(Block.oreRedstone.blockID, 7)).generate(worldObj, rand, l6, l9, i12);
        }

        for(int i4 = 0; i4 < 1; i4++)
        {
            int i7 = k + rand.nextInt(16);
            int i10 = rand.nextInt(16);
            int j12 = l + rand.nextInt(16);
            (new WorldGenMinable(Block.oreDiamond.blockID, 7)).generate(worldObj, rand, i7, i10, j12);
        }

        d = 0.5D;
        int j4 = (int)((field_713_c.func_647_a((double)k * d, (double)l * d) / 8D + rand.nextDouble() * 4D + 4D) / 3D);
        int j7 = 0;
        if(rand.nextInt(10) == 0)
        {
            j7++;
        }
        if(mobspawnerbase == MobSpawnerBase.forest)
        {
            j7 += j4 + 5;
        }
        if(mobspawnerbase == MobSpawnerBase.rainforest)
        {
            j7 += j4 + 5;
        }
        if(mobspawnerbase == MobSpawnerBase.seasonalForest)
        {
            j7 += j4 + 2;
        }
        if(mobspawnerbase == MobSpawnerBase.taiga)
        {
            j7 += j4 + 5;
        }
        if(mobspawnerbase == MobSpawnerBase.desert)
        {
            j7 -= 20;
        }
        if(mobspawnerbase == MobSpawnerBase.tundra)
        {
            j7 -= 20;
        }
        if(mobspawnerbase == MobSpawnerBase.plains)
        {
            j7 -= 20;
        }
        Object obj = new WorldGenTrees();
        if(rand.nextInt(10) == 0)
        {
            obj = new WorldGenBigTree();
        }
        if(mobspawnerbase == MobSpawnerBase.rainforest && rand.nextInt(3) == 0)
        {
            obj = new WorldGenBigTree();
        }
        for(int k12 = 0; k12 < j7; k12++)
        {
            int k14 = k + rand.nextInt(16) + 8;
            int j17 = l + rand.nextInt(16) + 8;
            ((WorldGenerator) (obj)).func_420_a(1.0D, 1.0D, 1.0D);
            ((WorldGenerator) (obj)).generate(worldObj, rand, k14, worldObj.getHeightValue(k14, j17), j17);
        }

        for(int l12 = 0; l12 < 2; l12++)
        {
            int l14 = k + rand.nextInt(16) + 8;
            int k17 = rand.nextInt(128);
            int i20 = l + rand.nextInt(16) + 8;
            (new WorldGenFlowers(Block.plantYellow.blockID)).generate(worldObj, rand, l14, k17, i20);
        }

        if(rand.nextInt(2) == 0)
        {
            int i13 = k + rand.nextInt(16) + 8;
            int i15 = rand.nextInt(128);
            int l17 = l + rand.nextInt(16) + 8;
            (new WorldGenFlowers(Block.plantRed.blockID)).generate(worldObj, rand, i13, i15, l17);
        }
        if(rand.nextInt(4) == 0)
        {
            int j13 = k + rand.nextInt(16) + 8;
            int j15 = rand.nextInt(128);
            int i18 = l + rand.nextInt(16) + 8;
            (new WorldGenFlowers(Block.mushroomBrown.blockID)).generate(worldObj, rand, j13, j15, i18);
        }
        if(rand.nextInt(8) == 0)
        {
            int k13 = k + rand.nextInt(16) + 8;
            int k15 = rand.nextInt(128);
            int j18 = l + rand.nextInt(16) + 8;
            (new WorldGenFlowers(Block.mushroomRed.blockID)).generate(worldObj, rand, k13, k15, j18);
        }
        for(int l13 = 0; l13 < 10; l13++)
        {
            int l15 = k + rand.nextInt(16) + 8;
            int k18 = rand.nextInt(128);
            int j20 = l + rand.nextInt(16) + 8;
            (new WorldGenReed()).generate(worldObj, rand, l15, k18, j20);
        }

        if(rand.nextInt(32) == 0)
        {
            int i14 = k + rand.nextInt(16) + 8;
            int i16 = rand.nextInt(128);
            int l18 = l + rand.nextInt(16) + 8;
            (new WorldGenPumpkin()).generate(worldObj, rand, i14, i16, l18);
        }
        int j14 = 0;
        if(mobspawnerbase == MobSpawnerBase.desert)
        {
            j14 += 10;
        }
        for(int j16 = 0; j16 < j14; j16++)
        {
            int i19 = k + rand.nextInt(16) + 8;
            int k20 = rand.nextInt(128);
            int k21 = l + rand.nextInt(16) + 8;
            (new WorldGenCactus()).generate(worldObj, rand, i19, k20, k21);
        }

        for(int k16 = 0; k16 < 50; k16++)
        {
            int j19 = k + rand.nextInt(16) + 8;
            int l20 = rand.nextInt(rand.nextInt(120) + 8);
            int l21 = l + rand.nextInt(16) + 8;
            (new WorldGenLiquids(Block.waterStill.blockID)).generate(worldObj, rand, j19, l20, l21);
        }

        for(int l16 = 0; l16 < 20; l16++)
        {
            int k19 = k + rand.nextInt(16) + 8;
            int i21 = rand.nextInt(rand.nextInt(rand.nextInt(112) + 8) + 8);
            int i22 = l + rand.nextInt(16) + 8;
            (new WorldGenLiquids(Block.lavaStill.blockID)).generate(worldObj, rand, k19, i21, i22);
        }


        field_4222_w = worldObj.func_4077_a().getTemperatures(field_4222_w, k + 8, l + 8, 16, 16);
        for(int i17 = k + 8; i17 < k + 8 + 16; i17++)
        {
            for(int l19 = l + 8; l19 < l + 8 + 16; l19++)
            {
                GameruleManager gameruleManager = GameruleManager.getInstance();
                int j21 = i17 - (k + 8);
                int j22 = l19 - (l + 8);
                int k22 = worldObj.func_4075_e(i17, l19);
                double d1 = field_4222_w[j21 * 16 + j22] - ((double)(k22 - 64) / 64D) * 0.29999999999999999D;
                if(gameruleManager.getGamerule("snowworld", false) | d1 < 0.5D && k22 > 0 && k22 < 128 && worldObj.getBlockId(i17, k22, l19) == 0 && worldObj.getBlockMaterial(i17, k22 - 1, l19).func_218_c() && worldObj.getBlockMaterial(i17, k22 - 1, l19) != Material.ice)
                {
                    worldObj.setBlockWithNotify(i17, k22, l19, Block.snow.blockID);
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

    private Random rand;
    private NoiseGeneratorOctaves field_705_k;
    private NoiseGeneratorOctaves field_704_l;
    private NoiseGeneratorOctaves field_703_m;
    private NoiseGeneratorOctaves field_702_n;
    private NoiseGeneratorOctaves field_701_o;
    public NoiseGeneratorOctaves field_715_a;
    public NoiseGeneratorOctaves field_714_b;
    public NoiseGeneratorOctaves field_713_c;
    private World worldObj;
    private double field_4224_q[];
    private double field_698_r[];
    private double field_697_s[];
    private double field_696_t[];
    private MapGenBase field_695_u;
    private MobSpawnerBase biomesForGeneration[];
    double field_4229_d[];
    double field_4228_e[];
    double field_4227_f[];
    double field_4226_g[];
    double field_4225_h[];
    int field_707_i[][];
    private double field_4222_w[];
}
