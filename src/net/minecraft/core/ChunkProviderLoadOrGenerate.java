package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.IOException;

public class ChunkProviderLoadOrGenerate
    implements IChunkProvider
{

    public ChunkProviderLoadOrGenerate(World world, IChunkLoader ichunkloader, IChunkProvider ichunkprovider)
    {
        chunks = new Chunk[1024];
        field_717_a = 0xc4653601;
        field_716_b = 0xc4653601;
        field_723_c = new Chunk(world, new byte[32768], 0, 0);
        field_723_c.field_678_q = true;
        field_723_c.field_679_p = true;
        worldObj = world;
        field_721_e = ichunkloader;
        field_722_d = ichunkprovider;
    }

    public boolean chunkExists(int i, int j)
    {
        if(i == field_717_a && j == field_716_b && field_718_h != null)
        {
            return true;
        } else
        {
            int k = i & 0x1f;
            int l = j & 0x1f;
            int i1 = k + l * 32;
            return chunks[i1] != null && (chunks[i1] == field_723_c || chunks[i1].func_351_a(i, j));
        }
    }

    public Chunk func_363_b(int i, int j)
    {
        if(i == field_717_a && j == field_716_b && field_718_h != null)
        {
            return field_718_h;
        }
        int k = i & 0x1f;
        int l = j & 0x1f;
        int i1 = k + l * 32;
        if(!chunkExists(i, j))
        {
            if(chunks[i1] != null)
            {
                chunks[i1].func_331_d();
                func_370_b(chunks[i1]);
                func_371_a(chunks[i1]);
            }
            Chunk chunk = func_4059_c(i, j);
            if(chunk == null)
            {
                if(field_722_d == null)
                {
                    chunk = field_723_c;
                } else
                {
                    chunk = field_722_d.func_363_b(i, j);
                }
            }
            chunks[i1] = chunk;
            chunk.func_4053_c();
            if(chunks[i1] != null)
            {
                chunks[i1].func_358_c();
            }
            if(!chunks[i1].isTerrainPopulated && chunkExists(i + 1, j + 1) && chunkExists(i, j + 1) && chunkExists(i + 1, j))
            {
                populate(this, i, j);
            }
            if(chunkExists(i - 1, j) && !func_363_b(i - 1, j).isTerrainPopulated && chunkExists(i - 1, j + 1) && chunkExists(i, j + 1) && chunkExists(i - 1, j))
            {
                populate(this, i - 1, j);
            }
            if(chunkExists(i, j - 1) && !func_363_b(i, j - 1).isTerrainPopulated && chunkExists(i + 1, j - 1) && chunkExists(i, j - 1) && chunkExists(i + 1, j))
            {
                populate(this, i, j - 1);
            }
            if(chunkExists(i - 1, j - 1) && !func_363_b(i - 1, j - 1).isTerrainPopulated && chunkExists(i - 1, j - 1) && chunkExists(i, j - 1) && chunkExists(i - 1, j))
            {
                populate(this, i - 1, j - 1);
            }
        }
        field_717_a = i;
        field_716_b = j;
        field_718_h = chunks[i1];
        return chunks[i1];
    }

    private Chunk func_4059_c(int i, int j)
    {
        if(field_721_e == null)
        {
            return null;
        }
        try
        {
            Chunk chunk = field_721_e.func_659_a(worldObj, i, j);
            if(chunk != null)
            {
                chunk.field_676_s = worldObj.worldTime;
            }
            return chunk;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }

    private void func_371_a(Chunk chunk)
    {
        if(field_721_e == null)
        {
            return;
        }
        try
        {
            field_721_e.func_4104_b(worldObj, chunk);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void func_370_b(Chunk chunk)
    {
        if(field_721_e == null)
        {
            return;
        }
        try
        {
            chunk.field_676_s = worldObj.worldTime;
            field_721_e.func_662_a(worldObj, chunk);
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public void populate(IChunkProvider ichunkprovider, int i, int j)
    {
        Chunk chunk = func_363_b(i, j);
        if(!chunk.isTerrainPopulated)
        {
            chunk.isTerrainPopulated = true;
            if(field_722_d != null)
            {
                field_722_d.populate(ichunkprovider, i, j);
                chunk.func_336_e();
            }
        }
    }

    public boolean saveWorld(boolean flag, IProgressUpdate iprogressupdate)
    {
        int i = 0;
        int j = 0;
        if(iprogressupdate != null)
        {
            for(int k = 0; k < chunks.length; k++)
            {
                if(chunks[k] != null && chunks[k].func_347_a(flag))
                {
                    j++;
                }
            }

        }
        int l = 0;
        for(int i1 = 0; i1 < chunks.length; i1++)
        {
            if(chunks[i1] == null)
            {
                continue;
            }
            if(flag && !chunks[i1].field_679_p)
            {
                func_371_a(chunks[i1]);
            }
            if(!chunks[i1].func_347_a(flag))
            {
                continue;
            }
            func_370_b(chunks[i1]);
            chunks[i1].isModified = false;
            if(++i == 2 && !flag)
            {
                return false;
            }
            if(iprogressupdate != null && ++l % 10 == 0)
            {
                iprogressupdate.func_437_a((l * 100) / j);
            }
        }

        if(flag)
        {
            if(field_721_e == null)
            {
                return true;
            }
            field_721_e.func_660_b();
        }
        return true;
    }

    public boolean func_361_a()
    {
        if(field_721_e != null)
        {
            field_721_e.func_661_a();
        }
        return field_722_d.func_361_a();
    }

    public boolean func_364_b()
    {
        return true;
    }

    private Chunk field_723_c;
    private IChunkProvider field_722_d;
    private IChunkLoader field_721_e;
    private Chunk chunks[];
    private World worldObj;
    int field_717_a;
    int field_716_b;
    private Chunk field_718_h;
}
