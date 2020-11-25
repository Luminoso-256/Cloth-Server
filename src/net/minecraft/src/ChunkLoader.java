package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.util.*;

public class ChunkLoader
    implements IChunkLoader
{

    public ChunkLoader(File file, boolean flag)
    {
        field_945_a = file;
        field_944_b = flag;
    }

    private File func_665_a(int i, int j)
    {
        String s = (new StringBuilder()).append("c.").append(Integer.toString(i, 36)).append(".").append(Integer.toString(j, 36)).append(".dat").toString();
        String s1 = Integer.toString(i & 0x3f, 36);
        String s2 = Integer.toString(j & 0x3f, 36);
        File file = new File(field_945_a, s1);
        if(!file.exists())
        {
            if(field_944_b)
            {
                file.mkdir();
            } else
            {
                return null;
            }
        }
        file = new File(file, s2);
        if(!file.exists())
        {
            if(field_944_b)
            {
                file.mkdir();
            } else
            {
                return null;
            }
        }
        file = new File(file, s);
        if(!file.exists() && !field_944_b)
        {
            return null;
        } else
        {
            return file;
        }
    }

    public Chunk func_659_a(World world, int i, int j)
    {
        File file = func_665_a(i, j);
        if(file != null && file.exists())
        {
            try
            {
                FileInputStream fileinputstream = new FileInputStream(file);
                NBTTagCompound nbttagcompound = CompressedStreamTools.func_770_a(fileinputstream);
                if(!nbttagcompound.hasKey("Level"))
                {
                    System.out.println((new StringBuilder()).append("Chunk file at ").append(i).append(",").append(j).append(" is missing level data, skipping").toString());
                    return null;
                }
                if(!nbttagcompound.getCompoundTag("Level").hasKey("Blocks"))
                {
                    System.out.println((new StringBuilder()).append("Chunk file at ").append(i).append(",").append(j).append(" is missing block data, skipping").toString());
                    return null;
                }
                Chunk chunk = func_664_a(world, nbttagcompound.getCompoundTag("Level"));
                if(!chunk.func_351_a(i, j))
                {
                    System.out.println((new StringBuilder()).append("Chunk file at ").append(i).append(",").append(j).append(" is in the wrong location; relocating. (Expected ").append(i).append(", ").append(j).append(", got ").append(chunk.xPosition).append(", ").append(chunk.zPosition).append(")").toString());
                    nbttagcompound.setInteger("xPos", i);
                    nbttagcompound.setInteger("zPos", j);
                    chunk = func_664_a(world, nbttagcompound.getCompoundTag("Level"));
                }
                return chunk;
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public void func_662_a(World world, Chunk chunk)
    {
        world.func_476_g();
        File file = func_665_a(chunk.xPosition, chunk.zPosition);
        if(file.exists())
        {
            world.sizeOnDisk -= file.length();
        }
        try
        {
            File file1 = new File(field_945_a, "tmp_chunk.dat");
            FileOutputStream fileoutputstream = new FileOutputStream(file1);
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound.setTag("Level", nbttagcompound1);
            func_663_a(chunk, world, nbttagcompound1);
            CompressedStreamTools.func_769_a(nbttagcompound, fileoutputstream);
            fileoutputstream.close();
            if(file.exists())
            {
                file.delete();
            }
            file1.renameTo(file);
            world.sizeOnDisk += file.length();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void func_663_a(Chunk chunk, World world, NBTTagCompound nbttagcompound)
    {
        world.func_476_g();
        nbttagcompound.setInteger("xPos", chunk.xPosition);
        nbttagcompound.setInteger("zPos", chunk.zPosition);
        nbttagcompound.setLong("LastUpdate", world.worldTime);
        nbttagcompound.setByteArray("Blocks", chunk.blocks);
        nbttagcompound.setByteArray("Data", chunk.data.data);
        nbttagcompound.setByteArray("SkyLight", chunk.skylightMap.data);
        nbttagcompound.setByteArray("BlockLight", chunk.blocklightMap.data);
        nbttagcompound.setByteArray("HeightMap", chunk.heightMap);
        nbttagcompound.setBoolean("TerrainPopulated", chunk.isTerrainPopulated);
        chunk.field_677_r = false;
        NBTTagList nbttaglist = new NBTTagList();
label0:
        for(int i = 0; i < chunk.entities.length; i++)
        {
            Iterator iterator = chunk.entities[i].iterator();
            do
            {
                if(!iterator.hasNext())
                {
                    continue label0;
                }
                Entity entity = (Entity)iterator.next();
                chunk.field_677_r = true;
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                if(entity.func_95_c(nbttagcompound1))
                {
                    nbttaglist.setTag(nbttagcompound1);
                }
            } while(true);
        }

        nbttagcompound.setTag("Entities", nbttaglist);
        NBTTagList nbttaglist1 = new NBTTagList();
        NBTTagCompound nbttagcompound2;
        for(Iterator iterator1 = chunk.field_683_l.values().iterator(); iterator1.hasNext(); nbttaglist1.setTag(nbttagcompound2))
        {
            TileEntity tileentity = (TileEntity)iterator1.next();
            nbttagcompound2 = new NBTTagCompound();
            tileentity.writeToNBT(nbttagcompound2);
        }

        nbttagcompound.setTag("TileEntities", nbttaglist1);
    }

    public static Chunk func_664_a(World world, NBTTagCompound nbttagcompound)
    {
        int i = nbttagcompound.getInteger("xPos");
        int j = nbttagcompound.getInteger("zPos");
        Chunk chunk = new Chunk(world, i, j);
        chunk.blocks = nbttagcompound.getByteArray("Blocks");
        chunk.data = new NibbleArray(nbttagcompound.getByteArray("Data"));
        chunk.skylightMap = new NibbleArray(nbttagcompound.getByteArray("SkyLight"));
        chunk.blocklightMap = new NibbleArray(nbttagcompound.getByteArray("BlockLight"));
        chunk.heightMap = nbttagcompound.getByteArray("HeightMap");
        chunk.isTerrainPopulated = nbttagcompound.getBoolean("TerrainPopulated");
        if(!chunk.data.isValid())
        {
            chunk.data = new NibbleArray(chunk.blocks.length);
        }
        if(chunk.heightMap == null || !chunk.skylightMap.isValid())
        {
            chunk.heightMap = new byte[256];
            chunk.skylightMap = new NibbleArray(chunk.blocks.length);
            chunk.func_353_b();
        }
        if(!chunk.blocklightMap.isValid())
        {
            chunk.blocklightMap = new NibbleArray(chunk.blocks.length);
            chunk.func_348_a();
        }
        NBTTagList nbttaglist = nbttagcompound.getTagList("Entities");
        if(nbttaglist != null)
        {
            for(int k = 0; k < nbttaglist.tagCount(); k++)
            {
                NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(k);
                Entity entity = EntityList.func_566_a(nbttagcompound1, world);
                chunk.field_677_r = true;
                if(entity != null)
                {
                    chunk.addEntity(entity);
                }
            }

        }
        NBTTagList nbttaglist1 = nbttagcompound.getTagList("TileEntities");
        if(nbttaglist1 != null)
        {
            for(int l = 0; l < nbttaglist1.tagCount(); l++)
            {
                NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist1.tagAt(l);
                TileEntity tileentity = TileEntity.createAndLoadEntity(nbttagcompound2);
                if(tileentity != null)
                {
                    chunk.func_349_a(tileentity);
                }
            }

        }
        return chunk;
    }

    public void func_661_a()
    {
    }

    public void func_660_b()
    {
    }

    public void func_4104_b(World world, Chunk chunk)
    {
    }

    private File field_945_a;
    private boolean field_944_b;
}
