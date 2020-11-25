package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class MCHashTable
{

    public MCHashTable()
    {
        threshold = 12;
        slots = new HashEntry[16];
    }

    private static int computeHash(int i)
    {
        i ^= i >>> 20 ^ i >>> 12;
        return i ^ i >>> 7 ^ i >>> 4;
    }

    private static int getSlotIndex(int i, int j)
    {
        return i & j - 1;
    }

    public Object lookup(int i)
    {
        int j = computeHash(i);
        for(HashEntry hashentry = slots[getSlotIndex(j, slots.length)]; hashentry != null; hashentry = hashentry.field_843_c)
        {
            if(hashentry.field_841_a == i)
            {
                return hashentry.field_840_b;
            }
        }

        return null;
    }

    public boolean containsItem(int i)
    {
        return lookupEntry(i) != null;
    }

    final HashEntry lookupEntry(int i)
    {
        int j = computeHash(i);
        for(HashEntry hashentry = slots[getSlotIndex(j, slots.length)]; hashentry != null; hashentry = hashentry.field_843_c)
        {
            if(hashentry.field_841_a == i)
            {
                return hashentry;
            }
        }

        return null;
    }

    public void addKey(int i, Object obj)
    {
        int j = computeHash(i);
        int k = getSlotIndex(j, slots.length);
        for(HashEntry hashentry = slots[k]; hashentry != null; hashentry = hashentry.field_843_c)
        {
            if(hashentry.field_841_a == i)
            {
                hashentry.field_840_b = obj;
            }
        }

        versionStamp++;
        insert(j, i, obj, k);
    }

    private void grow(int i)
    {
        HashEntry ahashentry[] = slots;
        int j = ahashentry.length;
        if(j == 0x40000000)
        {
            threshold = 0x7fffffff;
            return;
        } else
        {
            HashEntry ahashentry1[] = new HashEntry[i];
            copyTo(ahashentry1);
            slots = ahashentry1;
            threshold = (int)((float)i * growFactor);
            return;
        }
    }

    private void copyTo(HashEntry ahashentry[])
    {
        HashEntry ahashentry1[] = slots;
        int i = ahashentry.length;
        for(int j = 0; j < ahashentry1.length; j++)
        {
            HashEntry hashentry = ahashentry1[j];
            if(hashentry == null)
            {
                continue;
            }
            ahashentry1[j] = null;
            do
            {
                HashEntry hashentry1 = hashentry.field_843_c;
                int k = getSlotIndex(hashentry.field_842_d, i);
                hashentry.field_843_c = ahashentry[k];
                ahashentry[k] = hashentry;
                hashentry = hashentry1;
            } while(hashentry != null);
        }

    }

    public Object removeObject(int i)
    {
        HashEntry hashentry = removeEntry(i);
        return hashentry != null ? hashentry.field_840_b : null;
    }

    final HashEntry removeEntry(int i)
    {
        int j = computeHash(i);
        int k = getSlotIndex(j, slots.length);
        HashEntry hashentry = slots[k];
        HashEntry hashentry1;
        HashEntry hashentry2;
        for(hashentry1 = hashentry; hashentry1 != null; hashentry1 = hashentry2)
        {
            hashentry2 = hashentry1.field_843_c;
            if(hashentry1.field_841_a == i)
            {
                versionStamp++;
                count--;
                if(hashentry == hashentry1)
                {
                    slots[k] = hashentry2;
                } else
                {
                    hashentry.field_843_c = hashentry2;
                }
                return hashentry1;
            }
            hashentry = hashentry1;
        }

        return hashentry1;
    }

    public void clearMap()
    {
        versionStamp++;
        HashEntry ahashentry[] = slots;
        for(int i = 0; i < ahashentry.length; i++)
        {
            ahashentry[i] = null;
        }

        count = 0;
    }

    private void insert(int i, int j, Object obj, int k)
    {
        HashEntry hashentry = slots[k];
        slots[k] = new HashEntry(i, j, obj, hashentry);
        if(count++ >= threshold)
        {
            grow(2 * slots.length);
        }
    }

    static int getHash(int i)
    {
        return computeHash(i);
    }

    private transient HashEntry slots[];
    private transient int count;
    private int threshold;
    private final float growFactor = 0.75F;
    private volatile transient int versionStamp;
}
