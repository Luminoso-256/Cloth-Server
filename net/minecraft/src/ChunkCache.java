package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ChunkCache
    implements IBlockAccess
{

    public ChunkCache(World world, int i, int j, int k, int l, int i1, int j1)
    {
        worldObj = world;
        field_823_a = i >> 4;
        field_822_b = k >> 4;
        int k1 = l >> 4;
        int l1 = j1 >> 4;
        field_825_c = new Chunk[(k1 - field_823_a) + 1][(l1 - field_822_b) + 1];
        for(int i2 = field_823_a; i2 <= k1; i2++)
        {
            for(int j2 = field_822_b; j2 <= l1; j2++)
            {
                field_825_c[i2 - field_823_a][j2 - field_822_b] = world.getChunkFromChunkCoords(i2, j2);
            }

        }

    }

    public int getBlockId(int i, int j, int k)
    {
        if(j < 0)
        {
            return 0;
        }
        if(j >= 128)
        {
            return 0;
        } else
        {
            int l = (i >> 4) - field_823_a;
            int i1 = (k >> 4) - field_822_b;
            return field_825_c[l][i1].getBlockID(i & 0xf, j, k & 0xf);
        }
    }

    public int getBlockMetadata(int i, int j, int k)
    {
        if(j < 0)
        {
            return 0;
        }
        if(j >= 128)
        {
            return 0;
        } else
        {
            int l = (i >> 4) - field_823_a;
            int i1 = (k >> 4) - field_822_b;
            return field_825_c[l][i1].getBlockMetadata(i & 0xf, j, k & 0xf);
        }
    }

    public Material getBlockMaterial(int i, int j, int k)
    {
        int l = getBlockId(i, j, k);
        if(l == 0)
        {
            return Material.air;
        } else
        {
            return Block.blocksList[l].blockMaterial;
        }
    }

    public boolean doesBlockAllowAttachment(int i, int j, int k)
    {
        Block block = Block.blocksList[getBlockId(i, j, k)];
        if(block == null)
        {
            return false;
        } else
        {
            return block.allowsAttachment();
        }
    }

    private int field_823_a;
    private int field_822_b;
    private Chunk field_825_c[][];
    private World worldObj;
}
