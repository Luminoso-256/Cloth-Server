package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


class HashEntry
{

    HashEntry(int i, int j, Object obj, HashEntry hashentry)
    {
        field_840_b = obj;
        field_843_c = hashentry;
        field_841_a = j;
        field_842_d = i;
    }

    public final int func_559_a()
    {
        return field_841_a;
    }

    public final Object func_558_b()
    {
        return field_840_b;
    }

    public final boolean equals(Object obj)
    {
        if(!(obj instanceof HashEntry))
        {
            return false;
        }
        HashEntry hashentry = (HashEntry)obj;
        Integer integer = Integer.valueOf(func_559_a());
        Integer integer1 = Integer.valueOf(hashentry.func_559_a());
        if(integer == integer1 || integer != null && integer.equals(integer1))
        {
            Object obj1 = func_558_b();
            Object obj2 = hashentry.func_558_b();
            if(obj1 == obj2 || obj1 != null && obj1.equals(obj2))
            {
                return true;
            }
        }
        return false;
    }

    public final int hashCode()
    {
        return MCHashTable.getHash(field_841_a);
    }

    public final String toString()
    {
        return (new StringBuilder()).append(func_559_a()).append("=").append(func_558_b()).toString();
    }

    final int field_841_a;
    Object field_840_b;
    HashEntry field_843_c;
    final int field_842_d;
}
