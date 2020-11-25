package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


class HashEntry2
{

    HashEntry2(int i, long l, Object obj, HashEntry2 hashentry2)
    {
        field_1024_b = obj;
        field_1027_c = hashentry2;
        field_1025_a = l;
        field_1026_d = i;
    }

    public final long func_736_a()
    {
        return field_1025_a;
    }

    public final Object func_735_b()
    {
        return field_1024_b;
    }

    public final boolean equals(Object obj)
    {
        if(!(obj instanceof HashEntry2))
        {
            return false;
        }
        HashEntry2 hashentry2 = (HashEntry2)obj;
        Long long1 = Long.valueOf(func_736_a());
        Long long2 = Long.valueOf(hashentry2.func_736_a());
        if(long1 == long2 || long1 != null && long1.equals(long2))
        {
            Object obj1 = func_735_b();
            Object obj2 = hashentry2.func_735_b();
            if(obj1 == obj2 || obj1 != null && obj1.equals(obj2))
            {
                return true;
            }
        }
        return false;
    }

    public final int hashCode()
    {
        return MCHashTable2.func_674_d(field_1025_a);
    }

    public final String toString()
    {
        return (new StringBuilder()).append(func_736_a()).append("=").append(func_735_b()).toString();
    }

    final long field_1025_a;
    Object field_1024_b;
    HashEntry2 field_1027_c;
    final int field_1026_d;
}
