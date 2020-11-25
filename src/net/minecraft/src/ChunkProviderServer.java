package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.IOException;
import java.util.*;

public class ChunkProviderServer
    implements IChunkProvider
{

    public ChunkProviderServer(WorldServer worldserver, IChunkLoader ichunkloader, IChunkProvider ichunkprovider)
    {
        field_725_a = new HashSet();
        field_728_e = new HashMap();
        field_727_f = new ArrayList();
        field_724_b = new Chunk(worldserver, new byte[32768], 0, 0);
        field_724_b.field_678_q = true;
        field_724_b.field_679_p = true;
        field_726_g = worldserver;
        field_729_d = ichunkloader;
        field_730_c = ichunkprovider;
    }

    public boolean chunkExists(int i, int j)
    {
        ChunkCoordinates chunkcoordinates = new ChunkCoordinates(i, j);
        return field_728_e.containsKey(chunkcoordinates);
    }

    public void func_374_c(int i, int j)
    {
        int k = (i * 16 + 8) - field_726_g.spawnX;
        int l = (j * 16 + 8) - field_726_g.spawnZ;
        char c = '\200';
        if(k < -c || k > c || l < -c || l > c)
        {
            field_725_a.add(new ChunkCoordinates(i, j));
        }
    }

    public Chunk loadChunk(int i, int j)
    {
        ChunkCoordinates chunkcoordinates = new ChunkCoordinates(i, j);
        field_725_a.remove(new ChunkCoordinates(i, j));
        Chunk chunk = (Chunk)field_728_e.get(chunkcoordinates);
        if(chunk == null)
        {
            chunk = func_4063_e(i, j);
            if(chunk == null)
            {
                if(field_730_c == null)
                {
                    chunk = field_724_b;
                } else
                {
                    chunk = field_730_c.func_363_b(i, j);
                }
            }
            field_728_e.put(chunkcoordinates, chunk);
            field_727_f.add(chunk);
            chunk.func_4053_c();
            if(chunk != null)
            {
                chunk.func_358_c();
            }
            if(!chunk.isTerrainPopulated && chunkExists(i + 1, j + 1) && chunkExists(i, j + 1) && chunkExists(i + 1, j))
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
        return chunk;
    }

    public Chunk func_363_b(int i, int j)
    {
        ChunkCoordinates chunkcoordinates = new ChunkCoordinates(i, j);
        Chunk chunk = (Chunk)field_728_e.get(chunkcoordinates);
        if(chunk == null)
        {
            if(field_726_g.field_9209_x)
            {
                return loadChunk(i, j);
            } else
            {
                return field_724_b;
            }
        } else
        {
            return chunk;
        }
    }

    private Chunk func_4063_e(int i, int j)
    {
        if(field_729_d == null)
        {
            return null;
        }
        try
        {
            Chunk chunk = field_729_d.func_659_a(field_726_g, i, j);
            if(chunk != null)
            {
                chunk.field_676_s = field_726_g.worldTime;
            }
            return chunk;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }

    private void func_375_a(Chunk chunk)
    {
        if(field_729_d == null)
        {
            return;
        }
        try
        {
            field_729_d.func_4104_b(field_726_g, chunk);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void func_373_b(Chunk chunk)
    {
        if(field_729_d == null)
        {
            return;
        }
        try
        {
            chunk.field_676_s = field_726_g.worldTime;
            field_729_d.func_662_a(field_726_g, chunk);
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
            if(field_730_c != null)
            {
                field_730_c.populate(ichunkprovider, i, j);
                chunk.func_336_e();
            }
        }
    }

    public boolean saveWorld(boolean flag, IProgressUpdate iprogressupdate)
    {
        int i = 0;
        for(int j = 0; j < field_727_f.size(); j++)
        {
            Chunk chunk = (Chunk)field_727_f.get(j);
            if(flag && !chunk.field_679_p)
            {
                func_375_a(chunk);
            }
            if(!chunk.func_347_a(flag))
            {
                continue;
            }
            func_373_b(chunk);
            chunk.isModified = false;
            if(++i == 32 && !flag)
            {
                return false;
            }
        }

        if(flag)
        {
            if(field_729_d == null)
            {
                return true;
            }
            field_729_d.func_660_b();
        }
        return true;
    }

    public boolean func_361_a()
    {
        if(!field_726_g.field_816_A)
        {
            for(int i = 0; i < 100; i++)
            {
                if(!field_725_a.isEmpty())
                {
                    ChunkCoordinates chunkcoordinates = (ChunkCoordinates)field_725_a.iterator().next();
                    Chunk chunk = func_363_b(chunkcoordinates.field_529_a, chunkcoordinates.field_528_b);
                    chunk.func_331_d();
                    func_373_b(chunk);
                    func_375_a(chunk);
                    field_725_a.remove(chunkcoordinates);
                    field_728_e.remove(chunkcoordinates);
                    field_727_f.remove(chunk);
                }
            }

            if(field_729_d != null)
            {
                field_729_d.func_661_a();
            }
        }
        return field_730_c.func_361_a();
    }

    public boolean func_364_b()
    {
        return !field_726_g.field_816_A;
    }

    private Set field_725_a;
    private Chunk field_724_b;
    private IChunkProvider field_730_c;
    private IChunkLoader field_729_d;
    private Map field_728_e;
    private List field_727_f;
    private WorldServer field_726_g;
}
