package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class MCHashTable2
{

    public MCHashTable2()
    {
        field_952_c = 12;
        field_949_a = new HashEntry2[16];
    }

    private static int func_671_e(long l)
    {
        return func_676_a((int)(l ^ l >>> 32));
    }

    private static int func_676_a(int i)
    {
        i ^= i >>> 20 ^ i >>> 12;
        return i ^ i >>> 7 ^ i >>> 4;
    }

    private static int func_678_a(int i, int j)
    {
        return i & j - 1;
    }

    public Object func_677_a(long l)
    {
        int i = func_671_e(l);
        for(HashEntry2 hashentry2 = field_949_a[func_678_a(i, field_949_a.length)]; hashentry2 != null; hashentry2 = hashentry2.field_1027_c)
        {
            if(hashentry2.field_1025_a == l)
            {
                return hashentry2.field_1024_b;
            }
        }

        return null;
    }

    public void func_675_a(long l, Object obj)
    {
        int i = func_671_e(l);
        int j = func_678_a(i, field_949_a.length);
        for(HashEntry2 hashentry2 = field_949_a[j]; hashentry2 != null; hashentry2 = hashentry2.field_1027_c)
        {
            if(hashentry2.field_1025_a == l)
            {
                hashentry2.field_1024_b = obj;
            }
        }

        field_950_e++;
        func_679_a(i, l, obj, j);
    }

    private void func_680_b(int i)
    {
        HashEntry2 ahashentry2[] = field_949_a;
        int j = ahashentry2.length;
        if(j == 0x40000000)
        {
            field_952_c = 0x7fffffff;
            return;
        } else
        {
            HashEntry2 ahashentry2_1[] = new HashEntry2[i];
            func_673_a(ahashentry2_1);
            field_949_a = ahashentry2_1;
            field_952_c = (int)((float)i * field_951_d);
            return;
        }
    }

    private void func_673_a(HashEntry2 ahashentry2[])
    {
        HashEntry2 ahashentry2_1[] = field_949_a;
        int i = ahashentry2.length;
        for(int j = 0; j < ahashentry2_1.length; j++)
        {
            HashEntry2 hashentry2 = ahashentry2_1[j];
            if(hashentry2 == null)
            {
                continue;
            }
            ahashentry2_1[j] = null;
            do
            {
                HashEntry2 hashentry2_1 = hashentry2.field_1027_c;
                int k = func_678_a(hashentry2.field_1026_d, i);
                hashentry2.field_1027_c = ahashentry2[k];
                ahashentry2[k] = hashentry2;
                hashentry2 = hashentry2_1;
            } while(hashentry2 != null);
        }

    }

    public Object func_670_b(long l)
    {
        HashEntry2 hashentry2 = func_672_c(l);
        return hashentry2 != null ? hashentry2.field_1024_b : null;
    }

    final HashEntry2 func_672_c(long l)
    {
        int i = func_671_e(l);
        int j = func_678_a(i, field_949_a.length);
        HashEntry2 hashentry2 = field_949_a[j];
        HashEntry2 hashentry2_1;
        HashEntry2 hashentry2_2;
        for(hashentry2_1 = hashentry2; hashentry2_1 != null; hashentry2_1 = hashentry2_2)
        {
            hashentry2_2 = hashentry2_1.field_1027_c;
            if(hashentry2_1.field_1025_a == l)
            {
                field_950_e++;
                field_948_b--;
                if(hashentry2 == hashentry2_1)
                {
                    field_949_a[j] = hashentry2_2;
                } else
                {
                    hashentry2.field_1027_c = hashentry2_2;
                }
                return hashentry2_1;
            }
            hashentry2 = hashentry2_1;
        }

        return hashentry2_1;
    }

    private void func_679_a(int i, long l, Object obj, int j)
    {
        HashEntry2 hashentry2 = field_949_a[j];
        field_949_a[j] = new HashEntry2(i, l, obj, hashentry2);
        if(field_948_b++ >= field_952_c)
        {
            func_680_b(2 * field_949_a.length);
        }
    }

    static int func_674_d(long l)
    {
        return func_671_e(l);
    }

    private transient HashEntry2 field_949_a[];
    private transient int field_948_b;
    private int field_952_c;
    private final float field_951_d = 0.75F;
    private volatile transient int field_950_e;
}
