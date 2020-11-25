package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class WorldGenBigTree extends WorldGenerator
{

    public WorldGenBigTree()
    {
        field_759_b = new Random();
        field_756_e = 0;
        field_754_g = 0.61799999999999999D;
        field_753_h = 1.0D;
        field_752_i = 0.38100000000000001D;
        field_751_j = 1.0D;
        field_750_k = 1.0D;
        field_749_l = 1;
        field_748_m = 12;
        field_747_n = 4;
    }

    void func_424_a()
    {
        field_755_f = (int)((double)field_756_e * field_754_g);
        if(field_755_f >= field_756_e)
        {
            field_755_f = field_756_e - 1;
        }
        int i = (int)(1.3819999999999999D + Math.pow((field_750_k * (double)field_756_e) / 13D, 2D));
        if(i < 1)
        {
            i = 1;
        }
        int ai[][] = new int[i * field_756_e][4];
        int j = (field_757_d[1] + field_756_e) - field_747_n;
        int k = 1;
        int l = field_757_d[1] + field_755_f;
        int i1 = j - field_757_d[1];
        ai[0][0] = field_757_d[0];
        ai[0][1] = j;
        ai[0][2] = field_757_d[2];
        ai[0][3] = l;
        j--;
        while(i1 >= 0) 
        {
            int j1 = 0;
            float f = func_431_a(i1);
            if(f < 0.0F)
            {
                j--;
                i1--;
            } else
            {
                double d = 0.5D;
                for(; j1 < i; j1++)
                {
                    double d1 = field_751_j * ((double)f * ((double)field_759_b.nextFloat() + 0.32800000000000001D));
                    double d2 = (double)field_759_b.nextFloat() * 2D * 3.1415899999999999D;
                    int k1 = (int)(d1 * Math.sin(d2) + (double)field_757_d[0] + d);
                    int l1 = (int)(d1 * Math.cos(d2) + (double)field_757_d[2] + d);
                    int ai1[] = {
                        k1, j, l1
                    };
                    int ai2[] = {
                        k1, j + field_747_n, l1
                    };
                    if(func_427_a(ai1, ai2) != -1)
                    {
                        continue;
                    }
                    int ai3[] = {
                        field_757_d[0], field_757_d[1], field_757_d[2]
                    };
                    double d3 = Math.sqrt(Math.pow(Math.abs(field_757_d[0] - ai1[0]), 2D) + Math.pow(Math.abs(field_757_d[2] - ai1[2]), 2D));
                    double d4 = d3 * field_752_i;
                    if((double)ai1[1] - d4 > (double)l)
                    {
                        ai3[1] = l;
                    } else
                    {
                        ai3[1] = (int)((double)ai1[1] - d4);
                    }
                    if(func_427_a(ai3, ai1) == -1)
                    {
                        ai[k][0] = k1;
                        ai[k][1] = j;
                        ai[k][2] = l1;
                        ai[k][3] = ai3[1];
                        k++;
                    }
                }

                j--;
                i1--;
            }
        }
        field_746_o = new int[k][4];
        System.arraycopy(ai, 0, field_746_o, 0, k);
    }

    void func_426_a(int i, int j, int k, float f, byte byte0, int l)
    {
        int i1 = (int)((double)f + 0.61799999999999999D);
        byte byte1 = field_760_a[byte0];
        byte byte2 = field_760_a[byte0 + 3];
        int ai[] = {
            i, j, k
        };
        int ai1[] = {
            0, 0, 0
        };
        int j1 = -i1;
        int k1 = -i1;
        ai1[byte0] = ai[byte0];
        for(; j1 <= i1; j1++)
        {
            ai1[byte1] = ai[byte1] + j1;
            for(int l1 = -i1; l1 <= i1;)
            {
                double d = Math.sqrt(Math.pow((double)Math.abs(j1) + 0.5D, 2D) + Math.pow((double)Math.abs(l1) + 0.5D, 2D));
                if(d > (double)f)
                {
                    l1++;
                } else
                {
                    ai1[byte2] = ai[byte2] + l1;
                    int i2 = worldObj.getBlockId(ai1[0], ai1[1], ai1[2]);
                    if(i2 != 0 && i2 != 18)
                    {
                        l1++;
                    } else
                    {
                        worldObj.setBlock(ai1[0], ai1[1], ai1[2], l);
                        l1++;
                    }
                }
            }

        }

    }

    float func_431_a(int i)
    {
        if((double)i < (double)(float)field_756_e * 0.29999999999999999D)
        {
            return -1.618F;
        }
        float f = (float)field_756_e / 2.0F;
        float f1 = (float)field_756_e / 2.0F - (float)i;
        float f2;
        if(f1 == 0.0F)
        {
            f2 = f;
        } else
        if(Math.abs(f1) >= f)
        {
            f2 = 0.0F;
        } else
        {
            f2 = (float)Math.sqrt(Math.pow(Math.abs(f), 2D) - Math.pow(Math.abs(f1), 2D));
        }
        f2 *= 0.5F;
        return f2;
    }

    float func_429_b(int i)
    {
        if(i < 0 || i >= field_747_n)
        {
            return -1F;
        }
        return i != 0 && i != field_747_n - 1 ? 3F : 2.0F;
    }

    void func_423_a(int i, int j, int k)
    {
        int l = j;
        for(int i1 = j + field_747_n; l < i1; l++)
        {
            float f = func_429_b(l - j);
            func_426_a(i, l, k, f, (byte)1, 18);
        }

    }

    void func_425_a(int ai[], int ai1[], int i)
    {
        int ai2[] = {
            0, 0, 0
        };
        byte byte0 = 0;
        int j = 0;
        for(; byte0 < 3; byte0++)
        {
            ai2[byte0] = ai1[byte0] - ai[byte0];
            if(Math.abs(ai2[byte0]) > Math.abs(ai2[j]))
            {
                j = byte0;
            }
        }

        if(ai2[j] == 0)
        {
            return;
        }
        byte byte1 = field_760_a[j];
        byte byte2 = field_760_a[j + 3];
        byte byte3;
        if(ai2[j] > 0)
        {
            byte3 = 1;
        } else
        {
            byte3 = -1;
        }
        double d = (double)ai2[byte1] / (double)ai2[j];
        double d1 = (double)ai2[byte2] / (double)ai2[j];
        int ai3[] = {
            0, 0, 0
        };
        int k = 0;
        for(int l = ai2[j] + byte3; k != l; k += byte3)
        {
            ai3[j] = MathHelper.floor_double((double)(ai[j] + k) + 0.5D);
            ai3[byte1] = MathHelper.floor_double((double)ai[byte1] + (double)k * d + 0.5D);
            ai3[byte2] = MathHelper.floor_double((double)ai[byte2] + (double)k * d1 + 0.5D);
            worldObj.setBlock(ai3[0], ai3[1], ai3[2], i);
        }

    }

    void func_421_b()
    {
        int i = 0;
        for(int j = field_746_o.length; i < j; i++)
        {
            int k = field_746_o[i][0];
            int l = field_746_o[i][1];
            int i1 = field_746_o[i][2];
            func_423_a(k, l, i1);
        }

    }

    boolean func_430_c(int i)
    {
        return (double)i >= (double)field_756_e * 0.20000000000000001D;
    }

    void func_432_c()
    {
        int i = field_757_d[0];
        int j = field_757_d[1];
        int k = field_757_d[1] + field_755_f;
        int l = field_757_d[2];
        int ai[] = {
            i, j, l
        };
        int ai1[] = {
            i, k, l
        };
        func_425_a(ai, ai1, 17);
        if(field_749_l == 2)
        {
            ai[0]++;
            ai1[0]++;
            func_425_a(ai, ai1, 17);
            ai[2]++;
            ai1[2]++;
            func_425_a(ai, ai1, 17);
            ai[0]--;
            ai1[0]--;
            func_425_a(ai, ai1, 17);
        }
    }

    void func_428_d()
    {
        int i = 0;
        int j = field_746_o.length;
        int ai[] = {
            field_757_d[0], field_757_d[1], field_757_d[2]
        };
        for(; i < j; i++)
        {
            int ai1[] = field_746_o[i];
            int ai2[] = {
                ai1[0], ai1[1], ai1[2]
            };
            ai[1] = ai1[3];
            int k = ai[1] - field_757_d[1];
            if(func_430_c(k))
            {
                func_425_a(ai, ai2, 17);
            }
        }

    }

    int func_427_a(int ai[], int ai1[])
    {
        int ai2[] = {
            0, 0, 0
        };
        byte byte0 = 0;
        int i = 0;
        for(; byte0 < 3; byte0++)
        {
            ai2[byte0] = ai1[byte0] - ai[byte0];
            if(Math.abs(ai2[byte0]) > Math.abs(ai2[i]))
            {
                i = byte0;
            }
        }

        if(ai2[i] == 0)
        {
            return -1;
        }
        byte byte1 = field_760_a[i];
        byte byte2 = field_760_a[i + 3];
        byte byte3;
        if(ai2[i] > 0)
        {
            byte3 = 1;
        } else
        {
            byte3 = -1;
        }
        double d = (double)ai2[byte1] / (double)ai2[i];
        double d1 = (double)ai2[byte2] / (double)ai2[i];
        int ai3[] = {
            0, 0, 0
        };
        int j = 0;
        int k = ai2[i] + byte3;
        do
        {
            if(j == k)
            {
                break;
            }
            ai3[i] = ai[i] + j;
            ai3[byte1] = (int)((double)ai[byte1] + (double)j * d);
            ai3[byte2] = (int)((double)ai[byte2] + (double)j * d1);
            int l = worldObj.getBlockId(ai3[0], ai3[1], ai3[2]);
            if(l != 0 && l != 18)
            {
                break;
            }
            j += byte3;
        } while(true);
        if(j == k)
        {
            return -1;
        } else
        {
            return Math.abs(j);
        }
    }

    boolean func_422_e()
    {
        int ai[] = {
            field_757_d[0], field_757_d[1], field_757_d[2]
        };
        int ai1[] = {
            field_757_d[0], (field_757_d[1] + field_756_e) - 1, field_757_d[2]
        };
        int i = worldObj.getBlockId(field_757_d[0], field_757_d[1] - 1, field_757_d[2]);
        if(i != 2 && i != 3)
        {
            return false;
        }
        int j = func_427_a(ai, ai1);
        if(j == -1)
        {
            return true;
        }
        if(j < 6)
        {
            return false;
        } else
        {
            field_756_e = j;
            return true;
        }
    }

    public void func_420_a(double d, double d1, double d2)
    {
        field_748_m = (int)(d * 12D);
        if(d > 0.5D)
        {
            field_747_n = 5;
        }
        field_751_j = d1;
        field_750_k = d2;
    }

    public boolean generate(World world, Random random, int i, int j, int k)
    {
        worldObj = world;
        long l = random.nextLong();
        field_759_b.setSeed(l);
        field_757_d[0] = i;
        field_757_d[1] = j;
        field_757_d[2] = k;
        if(field_756_e == 0)
        {
            field_756_e = 5 + field_759_b.nextInt(field_748_m);
        }
        if(!func_422_e())
        {
            return false;
        } else
        {
            func_424_a();
            func_421_b();
            func_432_c();
            func_428_d();
            return true;
        }
    }

    static final byte field_760_a[] = {
        2, 0, 0, 1, 2, 1
    };
    Random field_759_b;
    World worldObj;
    int field_757_d[] = {
        0, 0, 0
    };
    int field_756_e;
    int field_755_f;
    double field_754_g;
    double field_753_h;
    double field_752_i;
    double field_751_j;
    double field_750_k;
    int field_749_l;
    int field_748_m;
    int field_747_n;
    int field_746_o[][];

}
