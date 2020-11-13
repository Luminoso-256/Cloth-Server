package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.clothutils.WorldGenParams;

import java.io.*;
import java.util.*;

public class World
    implements IBlockAccess
{

    public WorldChunkManager func_4077_a()
    {
        return field_4272_q.field_4301_b;
    }

    public World(File file, String s, long l, WorldProvider worldprovider)
    {
        field_4280_a = false;
        field_821 = new ArrayList();
        EntityList = new ArrayList();
        field_790_z = new ArrayList();
        scheduledTickTreeSet = new TreeSet();
        scheduledTickSet = new HashSet();
        field_814_b = new ArrayList();
        playerEntities = new ArrayList();
        worldTime = 0L;
        field_6159_E = 0xffffffL;
        skylightSubtracted = 0;
        field_4279_g = (new Random()).nextInt();
        field_4278_h = 0x3c6ef35f;
        field_808_h = false;
        field_784_F = System.currentTimeMillis();
        field_4277_j = 40;
        rand = new Random();
        field_9212_p = false;
        field_798_r = new ArrayList();
        randomSeed = 0L;
        sizeOnDisk = 0L;
        field_9207_I = new ArrayList();
        field_4265_J = 0;
        field_4264_K = new HashSet();
        field_4263_L = rand.nextInt(12000);
        field_778_L = new ArrayList();
        multiplayerWorld = false;
        field_9211_s = file;
        field_9210_w = s;
        file.mkdirs();
        field_797_s = new File(file, s);
        field_797_s.mkdirs();
        try
        {
            File file1 = new File(field_797_s, "session.lock");
            DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
            try
            {
                dataoutputstream.writeLong(field_784_F);
            }
            finally
            {
                dataoutputstream.close();
            }
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
            throw new RuntimeException("Failed to check session lock, aborting");
        }
        Object obj = new WorldProvider();
        File file2 = new File(field_797_s, "level.dat");
        field_9212_p = !file2.exists();
        if(file2.exists())
        {
            try
            {
                NBTTagCompound nbttagcompound = CompressedStreamTools.func_770_a(new FileInputStream(file2));
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
                WorldGenParams params = new WorldGenParams();
                randomSeed = params.GetSeedFromPropertiesFile();
                spawnX = nbttagcompound1.getInteger("SpawnX");
                spawnY = nbttagcompound1.getInteger("SpawnY");
                spawnZ = nbttagcompound1.getInteger("SpawnZ");
                worldTime = nbttagcompound1.getLong("Time");
                sizeOnDisk = nbttagcompound1.getLong("SizeOnDisk");
                if(nbttagcompound1.hasKey("Player"))
                {
                    nbtCompoundPlayer = nbttagcompound1.getCompoundTag("Player");
                    int i = nbtCompoundPlayer.getInteger("Dimension");
                    if(i == -1)
                    {
                        obj = new WorldProviderHell();
                    }
                }
            }
            catch(Exception exception1)
            {
                exception1.printStackTrace();
            }
        }
        if(worldprovider != null)
        {
            obj = worldprovider;
        }
        boolean flag = false;
        if(randomSeed == 0L)
        {
            randomSeed = l;
            flag = true;
        }
        field_4272_q = ((WorldProvider) (obj));
        field_4272_q.func_4093_a(this);
        chunkProvider = func_4076_a(field_797_s);
        if(flag)
        {
            field_9209_x = true;
            spawnX = 0;
            spawnY = 64;
            for(spawnZ = 0; !field_4272_q.canCoordinateBeSpawn(spawnX, spawnZ); spawnZ += rand.nextInt(64) - rand.nextInt(64))
            {
                spawnX += rand.nextInt(64) - rand.nextInt(64);
            }

            field_9209_x = false;
        }
        calculateInitialSkylight();
    }

    protected IChunkProvider func_4076_a(File file)
    {
        return new ChunkProviderLoadOrGenerate(this, field_4272_q.getChunkLoader(file), field_4272_q.getChunkProvider());
    }

    public int func_528_f(int i, int j)
    {
        int k;
        for(k = 63; getBlockId(i, k + 1, j) != 0; k++) { }
        return getBlockId(i, k, j);
    }

    public void func_485_a(boolean flag, IProgressUpdate iprogressupdate)
    {
        if(!chunkProvider.func_364_b())
        {
            return;
        }
        if(iprogressupdate != null)
        {
            iprogressupdate.func_438_a("Saving level");
        }
        func_478_h();
        if(iprogressupdate != null)
        {
            iprogressupdate.func_439_b("Saving chunks");
        }
        chunkProvider.saveWorld(flag, iprogressupdate);
    }

    private void func_478_h()
    {
        func_476_g();
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setLong("RandomSeed", randomSeed);
        nbttagcompound.setInteger("SpawnX", spawnX);
        nbttagcompound.setInteger("SpawnY", spawnY);
        nbttagcompound.setInteger("SpawnZ", spawnZ);
        nbttagcompound.setLong("Time", worldTime);
        nbttagcompound.setLong("SizeOnDisk", sizeOnDisk);
        nbttagcompound.setLong("LastPlayed", System.currentTimeMillis());
        EntityPlayer entityplayer = null;
        if(playerEntities.size() > 0)
        {
            entityplayer = (EntityPlayer)playerEntities.get(0);
        }
        if(entityplayer != null)
        {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            entityplayer.writeToNBT(nbttagcompound1);
            nbttagcompound.setCompoundTag("Player", nbttagcompound1);
        }
        NBTTagCompound nbttagcompound2 = new NBTTagCompound();
        nbttagcompound2.setTag("Data", nbttagcompound);
        try
        {
            File file = new File(field_797_s, "level.dat_new");
            File file1 = new File(field_797_s, "level.dat_old");
            File file2 = new File(field_797_s, "level.dat");
            CompressedStreamTools.func_769_a(nbttagcompound2, new FileOutputStream(file));
            if(file1.exists())
            {
                file1.delete();
            }
            file2.renameTo(file1);
            if(file2.exists())
            {
                file2.delete();
            }
            file.renameTo(file2);
            if(file.exists())
            {
                file.delete();
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public int getBlockId(int i, int j, int k)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return 0;
        }
        if(j < 0)
        {
            return 0;
        }
        if(j >= 128)
        {
            return 0;
        } else
        {
            return getChunkFromChunkCoords(i >> 4, k >> 4).getBlockID(i & 0xf, j, k & 0xf);
        }
    }

    public boolean func_530_e(int i, int j, int k)
    {
        if(j < 0 || j >= 128)
        {
            return false;
        } else
        {
            return chunkExists(i >> 4, k >> 4);
        }
    }

    public boolean checkChunksExist(int i, int j, int k, int l, int i1, int j1)
    {
        if(i1 < 0 || j >= 128)
        {
            return false;
        }
        i >>= 4;
        j >>= 4;
        k >>= 4;
        l >>= 4;
        i1 >>= 4;
        j1 >>= 4;
        for(int k1 = i; k1 <= l; k1++)
        {
            for(int l1 = k; l1 <= j1; l1++)
            {
                if(!chunkExists(k1, l1))
                {
                    return false;
                }
            }

        }

        return true;
    }

    private boolean chunkExists(int i, int j)
    {
        return chunkProvider.chunkExists(i, j);
    }

    public Chunk getChunkFromBlockCoords(int i, int j)
    {
        return getChunkFromChunkCoords(i >> 4, j >> 4);
    }

    public Chunk getChunkFromChunkCoords(int i, int j)
    {
        return chunkProvider.func_363_b(i, j);
    }

    public boolean func_470_a(int i, int j, int k, int l, int i1)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return false;
        }
        if(j < 0)
        {
            return false;
        }
        if(j >= 128)
        {
            return false;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
            return chunk.setBlockIDWithMetadata(i & 0xf, j, k & 0xf, l, i1);
        }
    }

    public boolean setBlock(int i, int j, int k, int l)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return false;
        }
        if(j < 0)
        {
            return false;
        }
        if(j >= 128)
        {
            return false;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
            return chunk.setBlockID(i & 0xf, j, k & 0xf, l);
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

    public int getBlockMetadata(int i, int j, int k)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return 0;
        }
        if(j < 0)
        {
            return 0;
        }
        if(j >= 128)
        {
            return 0;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
            i &= 0xf;
            k &= 0xf;
            return chunk.getBlockMetadata(i, j, k);
        }
    }

    public void setBlockMetadataWithNotify(int i, int j, int k, int l)
    {
        if(setBlockMetadata(i, j, k, l))
        {
            notifyBlockChange(i, j, k, getBlockId(i, j, k));
        }
    }

    public boolean setBlockMetadata(int i, int j, int k, int l)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return false;
        }
        if(j < 0)
        {
            return false;
        }
        if(j >= 128)
        {
            return false;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
            i &= 0xf;
            k &= 0xf;
            chunk.setBlockMetadata(i, j, k, l);
            return true;
        }
    }

    public boolean setBlockWithNotify(int i, int j, int k, int l)
    {
        if(setBlock(i, j, k, l))
        {
            notifyBlockChange(i, j, k, l);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean func_507_b(int i, int j, int k, int l, int i1)
    {
        if(func_470_a(i, j, k, l, i1))
        {
            notifyBlockChange(i, j, k, l);
            return true;
        } else
        {
            return false;
        }
    }

    public void func_521_f(int i, int j, int k)
    {
        for(int l = 0; l < field_798_r.size(); l++)
        {
            ((IWorldAccess)field_798_r.get(l)).func_683_a(i, j, k);
        }

    }

    protected void notifyBlockChange(int i, int j, int k, int l)
    {
        func_521_f(i, j, k);
        notifyBlocksOfNeighborChange(i, j, k, l);
    }

    public void func_498_f(int i, int j, int k, int l)
    {
        if(k > l)
        {
            int i1 = l;
            l = k;
            k = i1;
        }
        func_519_b(i, k, j, i, l, j);
    }

    public void func_519_b(int i, int j, int k, int l, int i1, int j1)
    {
        for(int k1 = 0; k1 < field_798_r.size(); k1++)
        {
            ((IWorldAccess)field_798_r.get(k1)).func_685_a(i, j, k, l, i1, j1);
        }

    }

    public void notifyBlocksOfNeighborChange(int i, int j, int k, int l)
    {
        notifyBlockOfNeighborChange(i - 1, j, k, l);
        notifyBlockOfNeighborChange(i + 1, j, k, l);
        notifyBlockOfNeighborChange(i, j - 1, k, l);
        notifyBlockOfNeighborChange(i, j + 1, k, l);
        notifyBlockOfNeighborChange(i, j, k - 1, l);
        notifyBlockOfNeighborChange(i, j, k + 1, l);
    }

    private void notifyBlockOfNeighborChange(int i, int j, int k, int l)
    {
        if(field_808_h || multiplayerWorld)
        {
            return;
        }
        Block block = Block.blocksList[getBlockId(i, j, k)];
        if(block != null)
        {
            block.onNeighborBlockChange(this, i, j, k, l);
        }
    }

    public boolean canBlockSeeTheSky(int i, int j, int k)
    {
        return getChunkFromChunkCoords(i >> 4, k >> 4).canBlockSeeTheSky(i & 0xf, j, k & 0xf);
    }

    public int getBlockLightValue(int i, int j, int k)
    {
        return getBlockLightValue(i, j, k, true);
    }

    public int getBlockLightValue(int i, int j, int k, boolean flag)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return 15;
        }
        if(flag)
        {
            int l = getBlockId(i, j, k);
            if(l == Block.stairSingle.blockID || l == Block.tilledField.blockID)
            {
                int j1 = getBlockLightValue(i, j + 1, k, false);
                int k1 = getBlockLightValue(i + 1, j, k, false);
                int l1 = getBlockLightValue(i - 1, j, k, false);
                int i2 = getBlockLightValue(i, j, k + 1, false);
                int j2 = getBlockLightValue(i, j, k - 1, false);
                if(k1 > j1)
                {
                    j1 = k1;
                }
                if(l1 > j1)
                {
                    j1 = l1;
                }
                if(i2 > j1)
                {
                    j1 = i2;
                }
                if(j2 > j1)
                {
                    j1 = j2;
                }
                return j1;
            }
        }
        if(j < 0)
        {
            return 0;
        }
        if(j >= 128)
        {
            int i1 = 15 - skylightSubtracted;
            if(i1 < 0)
            {
                i1 = 0;
            }
            return i1;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
            i &= 0xf;
            k &= 0xf;
            return chunk.getBlockLightValue(i, j, k, skylightSubtracted);
        }
    }

    public boolean canExistingBlockSeeTheSky(int i, int j, int k)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return false;
        }
        if(j < 0)
        {
            return false;
        }
        if(j >= 128)
        {
            return true;
        }
        if(!chunkExists(i >> 4, k >> 4))
        {
            return false;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
            i &= 0xf;
            k &= 0xf;
            return chunk.canBlockSeeTheSky(i, j, k);
        }
    }

    public int getHeightValue(int i, int j)
    {
        if(i < 0xfe17b800 || j < 0xfe17b800 || i >= 0x1e84800 || j > 0x1e84800)
        {
            return 0;
        }
        if(!chunkExists(i >> 4, j >> 4))
        {
            return 0;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(i >> 4, j >> 4);
            return chunk.getHeightValue(i & 0xf, j & 0xf);
        }
    }

    public void neighborLightPropagationChanged(EnumSkyBlock enumskyblock, int i, int j, int k, int l)
    {
        if(field_4272_q.field_4306_c && enumskyblock == EnumSkyBlock.Sky)
        {
            return;
        }
        if(!func_530_e(i, j, k))
        {
            return;
        }
        if(enumskyblock == EnumSkyBlock.Sky)
        {
            if(canExistingBlockSeeTheSky(i, j, k))
            {
                l = 15;
            }
        } else
        if(enumskyblock == EnumSkyBlock.Block)
        {
            int i1 = getBlockId(i, j, k);
            if(Block.lightValue[i1] > l)
            {
                l = Block.lightValue[i1];
            }
        }
        if(getSavedLightValue(enumskyblock, i, j, k) != l)
        {
            func_483_a(enumskyblock, i, j, k, i, j, k);
        }
    }

    public int getSavedLightValue(EnumSkyBlock enumskyblock, int i, int j, int k)
    {
        if(j < 0 || j >= 128 || i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return enumskyblock.field_984_c;
        }
        int l = i >> 4;
        int i1 = k >> 4;
        if(!chunkExists(l, i1))
        {
            return 0;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(l, i1);
            return chunk.getSavedLightValue(enumskyblock, i & 0xf, j, k & 0xf);
        }
    }

    public void setLightValue(EnumSkyBlock enumskyblock, int i, int j, int k, int l)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return;
        }
        if(j < 0)
        {
            return;
        }
        if(j >= 128)
        {
            return;
        }
        if(!chunkExists(i >> 4, k >> 4))
        {
            return;
        }
        Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
        chunk.setLightValue(enumskyblock, i & 0xf, j, k & 0xf, l);
        for(int i1 = 0; i1 < field_798_r.size(); i1++)
        {
            ((IWorldAccess)field_798_r.get(i1)).func_683_a(i, j, k);
        }

    }

    public float getLightBrightness(int i, int j, int k)
    {
        return field_4272_q.lightBrightnessTable[getBlockLightValue(i, j, k)];
    }

    public boolean isDaytime()
    {
        return skylightSubtracted < 4;
    }

    public MovingObjectPosition func_486_a(Vec3D vec3d, Vec3D vec3d1)
    {
        return func_505_a(vec3d, vec3d1, false);
    }

    public MovingObjectPosition func_505_a(Vec3D vec3d, Vec3D vec3d1, boolean flag)
    {
        if(Double.isNaN(vec3d.xCoord) || Double.isNaN(vec3d.yCoord) || Double.isNaN(vec3d.zCoord))
        {
            return null;
        }
        if(Double.isNaN(vec3d1.xCoord) || Double.isNaN(vec3d1.yCoord) || Double.isNaN(vec3d1.zCoord))
        {
            return null;
        }
        int i = MathHelper.floor_double(vec3d1.xCoord);
        int j = MathHelper.floor_double(vec3d1.yCoord);
        int k = MathHelper.floor_double(vec3d1.zCoord);
        int l = MathHelper.floor_double(vec3d.xCoord);
        int i1 = MathHelper.floor_double(vec3d.yCoord);
        int j1 = MathHelper.floor_double(vec3d.zCoord);
        for(int k1 = 200; k1-- >= 0;)
        {
            if(Double.isNaN(vec3d.xCoord) || Double.isNaN(vec3d.yCoord) || Double.isNaN(vec3d.zCoord))
            {
                return null;
            }
            if(l == i && i1 == j && j1 == k)
            {
                return null;
            }
            double d = 999D;
            double d1 = 999D;
            double d2 = 999D;
            if(i > l)
            {
                d = (double)l + 1.0D;
            }
            if(i < l)
            {
                d = (double)l + 0.0D;
            }
            if(j > i1)
            {
                d1 = (double)i1 + 1.0D;
            }
            if(j < i1)
            {
                d1 = (double)i1 + 0.0D;
            }
            if(k > j1)
            {
                d2 = (double)j1 + 1.0D;
            }
            if(k < j1)
            {
                d2 = (double)j1 + 0.0D;
            }
            double d3 = 999D;
            double d4 = 999D;
            double d5 = 999D;
            double d6 = vec3d1.xCoord - vec3d.xCoord;
            double d7 = vec3d1.yCoord - vec3d.yCoord;
            double d8 = vec3d1.zCoord - vec3d.zCoord;
            if(d != 999D)
            {
                d3 = (d - vec3d.xCoord) / d6;
            }
            if(d1 != 999D)
            {
                d4 = (d1 - vec3d.yCoord) / d7;
            }
            if(d2 != 999D)
            {
                d5 = (d2 - vec3d.zCoord) / d8;
            }
            byte byte0 = 0;
            if(d3 < d4 && d3 < d5)
            {
                if(i > l)
                {
                    byte0 = 4;
                } else
                {
                    byte0 = 5;
                }
                vec3d.xCoord = d;
                vec3d.yCoord += d7 * d3;
                vec3d.zCoord += d8 * d3;
            } else
            if(d4 < d5)
            {
                if(j > i1)
                {
                    byte0 = 0;
                } else
                {
                    byte0 = 1;
                }
                vec3d.xCoord += d6 * d4;
                vec3d.yCoord = d1;
                vec3d.zCoord += d8 * d4;
            } else
            {
                if(k > j1)
                {
                    byte0 = 2;
                } else
                {
                    byte0 = 3;
                }
                vec3d.xCoord += d6 * d5;
                vec3d.yCoord += d7 * d5;
                vec3d.zCoord = d2;
            }
            Vec3D vec3d2 = Vec3D.createVector(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
            l = (int)(vec3d2.xCoord = MathHelper.floor_double(vec3d.xCoord));
            if(byte0 == 5)
            {
                l--;
                vec3d2.xCoord++;
            }
            i1 = (int)(vec3d2.yCoord = MathHelper.floor_double(vec3d.yCoord));
            if(byte0 == 1)
            {
                i1--;
                vec3d2.yCoord++;
            }
            j1 = (int)(vec3d2.zCoord = MathHelper.floor_double(vec3d.zCoord));
            if(byte0 == 3)
            {
                j1--;
                vec3d2.zCoord++;
            }
            int l1 = getBlockId(l, i1, j1);
            int i2 = getBlockMetadata(l, i1, j1);
            Block block = Block.blocksList[l1];
            if(l1 > 0 && block.canCollideCheck(i2, flag))
            {
                MovingObjectPosition movingobjectposition = block.collisionRayTrace(this, l, i1, j1, vec3d, vec3d1);
                if(movingobjectposition != null)
                {
                    return movingobjectposition;
                }
            }
        }

        return null;
    }

    public void playSoundAtEntity(Entity entity, String s, float f, float f1)
    {
        for(int i = 0; i < field_798_r.size(); i++)
        {
            ((IWorldAccess)field_798_r.get(i)).playSound(s, entity.posX, entity.posY - (double)entity.yOffset, entity.posZ, f, f1);
        }

    }

    public void playSoundEffect(double d, double d1, double d2, String s, 
            float f, float f1)
    {
        for(int i = 0; i < field_798_r.size(); i++)
        {
            ((IWorldAccess)field_798_r.get(i)).playSound(s, d, d1, d2, f, f1);
        }

    }

    public void playRecord(String s, int i, int j, int k)
    {
        for(int l = 0; l < field_798_r.size(); l++)
        {
            ((IWorldAccess)field_798_r.get(l)).playRecord(s, i, j, k);
        }

    }

    public void spawnParticle(String s, double d, double d1, double d2, 
            double d3, double d4, double d5)
    {
        for(int i = 0; i < field_798_r.size(); i++)
        {
            ((IWorldAccess)field_798_r.get(i)).spawnParticle(s, d, d1, d2, d3, d4, d5);
        }

    }

    public boolean entityJoinedWorld(Entity entity)
    {
        int i = MathHelper.floor_double(entity.posX / 16D);
        int j = MathHelper.floor_double(entity.posZ / 16D);
        boolean flag = false;
        if(entity instanceof EntityPlayer)
        {
            flag = true;
        }
        if(flag || chunkExists(i, j))
        {
            if(entity instanceof EntityPlayer)
            {
                playerEntities.add((EntityPlayer)entity);
                System.out.println((new StringBuilder()).append("Player count: ").append(playerEntities.size()).toString());
            }
            getChunkFromChunkCoords(i, j).addEntity(entity);
            EntityList.add(entity);
            func_479_b(entity);
            return true;
        } else
        {
            return false;
        }
    }

    protected void func_479_b(Entity entity)
    {
        for(int i = 0; i < field_798_r.size(); i++)
        {
            ((IWorldAccess)field_798_r.get(i)).func_681_a(entity);
        }

    }

    protected void func_531_c(Entity entity)
    {
        for(int i = 0; i < field_798_r.size(); i++)
        {
            ((IWorldAccess)field_798_r.get(i)).func_688_b(entity);
        }

    }

    public void func_12016_d(Entity entity)
    {
        entity.setEntityDead();
        if(entity instanceof EntityPlayer)
        {
            playerEntities.remove((EntityPlayer)entity);
        }
    }

    public void func_12014_e(Entity entity)
    {
        entity.setEntityDead();
        if(entity instanceof EntityPlayer)
        {
            playerEntities.remove((EntityPlayer)entity);
        }
        int i = entity.field_307_aa;
        int j = entity.field_303_ac;
        if(entity.field_276_Z && chunkExists(i, j))
        {
            getChunkFromChunkCoords(i, j).func_350_b(entity);
        }
        EntityList.remove(entity);
        func_531_c(entity);
    }

    public void func_4072_a(IWorldAccess iworldaccess)
    {
        field_798_r.add(iworldaccess);
    }

    public List getCollidingBoundingBoxes(Entity entity, AxisAlignedBB axisalignedbb)
    {
        field_9207_I.clear();
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for(int k1 = i; k1 < j; k1++)
        {
            for(int l1 = i1; l1 < j1; l1++)
            {
                if(!func_530_e(k1, 64, l1))
                {
                    continue;
                }
                for(int i2 = k - 1; i2 < l; i2++)
                {
                    Block block = Block.blocksList[getBlockId(k1, i2, l1)];
                    if(block != null)
                    {
                        block.getCollidingBoundingBoxes(this, k1, i2, l1, axisalignedbb, field_9207_I);
                    }
                }

            }

        }

        double d = 0.25D;
        List list = getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb.expands(d, d, d));
        for(int j2 = 0; j2 < list.size(); j2++)
        {
            AxisAlignedBB axisalignedbb1 = ((Entity)list.get(j2)).func_93_n();
            if(axisalignedbb1 != null && axisalignedbb1.intersectsWith(axisalignedbb))
            {
                field_9207_I.add(axisalignedbb1);
            }
            axisalignedbb1 = entity.func_89_d((Entity)list.get(j2));
            if(axisalignedbb1 != null && axisalignedbb1.intersectsWith(axisalignedbb))
            {
                field_9207_I.add(axisalignedbb1);
            }
        }

        return field_9207_I;
    }

    public int calculateSkylightSubtracted(float f)
    {
        float f1 = func_477_b(f);
        float f2 = 1.0F - (MathHelper.cos(f1 * 3.141593F * 2.0F) * 2.0F + 0.5F);
        if(f2 < 0.0F)
        {
            f2 = 0.0F;
        }
        if(f2 > 1.0F)
        {
            f2 = 1.0F;
        }
        return (int)(f2 * 11F);
    }

    public float func_477_b(float f)
    {
        return field_4272_q.func_4089_a(worldTime, f);
    }

    public int func_4075_e(int i, int j)
    {
        Chunk chunk = getChunkFromBlockCoords(i, j);
        int k;
        for(k = 127; getBlockMaterial(i, k, j).func_218_c() && k > 0; k--) { }
        i &= 0xf;
        j &= 0xf;
        while(k > 0) 
        {
            int l = chunk.getBlockID(i, k, j);
            if(l == 0 || !Block.blocksList[l].blockMaterial.func_218_c() && !Block.blocksList[l].blockMaterial.getIsLiquid())
            {
                k--;
            } else
            {
                return k + 1;
            }
        }
        return -1;
    }

    public void scheduleBlockUpdate(int i, int j, int k, int l)
    {
        NextTickListEntry nextticklistentry = new NextTickListEntry(i, j, k, l);
        byte byte0 = 8;
        if(field_4280_a)
        {
            if(checkChunksExist(nextticklistentry.xCoord - byte0, nextticklistentry.yCoord - byte0, nextticklistentry.zCoord - byte0, nextticklistentry.xCoord + byte0, nextticklistentry.yCoord + byte0, nextticklistentry.zCoord + byte0))
            {
                int i1 = getBlockId(nextticklistentry.xCoord, nextticklistentry.yCoord, nextticklistentry.zCoord);
                if(i1 == nextticklistentry.blockID && i1 > 0)
                {
                    Block.blocksList[i1].updateTick(this, nextticklistentry.xCoord, nextticklistentry.yCoord, nextticklistentry.zCoord, rand);
                }
            }
            return;
        }
        if(checkChunksExist(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0))
        {
            if(l > 0)
            {
                nextticklistentry.setScheduledTime((long)Block.blocksList[l].tickRate() + worldTime);
            }
            if(!scheduledTickSet.contains(nextticklistentry))
            {
                scheduledTickSet.add(nextticklistentry);
                scheduledTickTreeSet.add(nextticklistentry);
            }
        }
    }

    public void func_459_b()
    {
        EntityList.removeAll(field_790_z);
        for(int i = 0; i < field_790_z.size(); i++)
        {
            Entity entity = (Entity)field_790_z.get(i);
            int i1 = entity.field_307_aa;
            int k1 = entity.field_303_ac;
            if(entity.field_276_Z && chunkExists(i1, k1))
            {
                getChunkFromChunkCoords(i1, k1).func_350_b(entity);
            }
        }

        for(int j = 0; j < field_790_z.size(); j++)
        {
            func_531_c((Entity)field_790_z.get(j));
        }

        field_790_z.clear();
        for(int k = 0; k < EntityList.size(); k++)
        {
            Entity entity1 = (Entity) EntityList.get(k);
            if(entity1.field_327_g != null)
            {
                if(!entity1.field_327_g.field_304_B && entity1.field_327_g.field_328_f == entity1)
                {
                    continue;
                }
                entity1.field_327_g.field_328_f = null;
                entity1.field_327_g = null;
            }
            if(!entity1.field_304_B)
            {
                func_520_e(entity1);
            }
            if(!entity1.field_304_B)
            {
                continue;
            }
            int j1 = entity1.field_307_aa;
            int l1 = entity1.field_303_ac;
            if(entity1.field_276_Z && chunkExists(j1, l1))
            {
                getChunkFromChunkCoords(j1, l1).func_350_b(entity1);
            }
            EntityList.remove(k--);
            func_531_c(entity1);
        }

        for(int l = 0; l < field_814_b.size(); l++)
        {
            TileEntity tileentity = (TileEntity)field_814_b.get(l);
            tileentity.updateEntity();
        }

    }

    public void func_520_e(Entity entity)
    {
        func_4074_a(entity, true);
    }

    public void func_4074_a(Entity entity, boolean flag)
    {
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posZ);
        byte byte0 = 16;
        if(!flag && !checkChunksExist(i - byte0, 0, j - byte0, i + byte0, 128, j + byte0))
        {
            return;
        }
        entity.field_9071_O = entity.posX;
        entity.field_9070_P = entity.posY;
        entity.field_9069_Q = entity.posZ;
        entity.prevRotationYaw = entity.rotationYaw;
        entity.prevRotationPitch = entity.rotationPitch;
        if(flag && entity.field_276_Z)
        {
            if(entity.field_327_g != null)
            {
                entity.func_115_v();
            } else
            {
                entity.onUpdate();
            }
        }
        if(Double.isNaN(entity.posX) || Double.isInfinite(entity.posX))
        {
            entity.posX = entity.field_9071_O;
        }
        if(Double.isNaN(entity.posY) || Double.isInfinite(entity.posY))
        {
            entity.posY = entity.field_9070_P;
        }
        if(Double.isNaN(entity.posZ) || Double.isInfinite(entity.posZ))
        {
            entity.posZ = entity.field_9069_Q;
        }
        if(Double.isNaN(entity.rotationPitch) || Double.isInfinite(entity.rotationPitch))
        {
            entity.rotationPitch = entity.prevRotationPitch;
        }
        if(Double.isNaN(entity.rotationYaw) || Double.isInfinite(entity.rotationYaw))
        {
            entity.rotationYaw = entity.prevRotationYaw;
        }
        int k = MathHelper.floor_double(entity.posX / 16D);
        int l = MathHelper.floor_double(entity.posY / 16D);
        int i1 = MathHelper.floor_double(entity.posZ / 16D);
        if(!entity.field_276_Z || entity.field_307_aa != k || entity.field_305_ab != l || entity.field_303_ac != i1)
        {
            if(entity.field_276_Z && chunkExists(entity.field_307_aa, entity.field_303_ac))
            {
                getChunkFromChunkCoords(entity.field_307_aa, entity.field_303_ac).func_332_a(entity, entity.field_305_ab);
            }
            if(chunkExists(k, i1))
            {
                entity.field_276_Z = true;
                getChunkFromChunkCoords(k, i1).addEntity(entity);
            } else
            {
                entity.field_276_Z = false;
            }
        }
        if(flag && entity.field_276_Z && entity.field_328_f != null)
        {
            if(entity.field_328_f.field_304_B || entity.field_328_f.field_327_g != entity)
            {
                entity.field_328_f.field_327_g = null;
                entity.field_328_f = null;
            } else
            {
                func_520_e(entity.field_328_f);
            }
        }
    }

    public boolean func_522_a(AxisAlignedBB axisalignedbb)
    {
        List list = getEntitiesWithinAABBExcludingEntity(null, axisalignedbb);
        for(int i = 0; i < list.size(); i++)
        {
            Entity entity = (Entity)list.get(i);
            if(!entity.field_304_B && entity.field_329_e)
            {
                return false;
            }
        }

        return true;
    }

    public boolean getIsAnyLiquid(AxisAlignedBB axisalignedbb)
    {
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        if(axisalignedbb.minX < 0.0D)
        {
            i--;
        }
        if(axisalignedbb.minY < 0.0D)
        {
            k--;
        }
        if(axisalignedbb.minZ < 0.0D)
        {
            i1--;
        }
        for(int k1 = i; k1 < j; k1++)
        {
            for(int l1 = k; l1 < l; l1++)
            {
                for(int i2 = i1; i2 < j1; i2++)
                {
                    Block block = Block.blocksList[getBlockId(k1, l1, i2)];
                    if(block != null && block.blockMaterial.getIsLiquid())
                    {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public boolean func_523_c(AxisAlignedBB axisalignedbb)
    {
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for(int k1 = i; k1 < j; k1++)
        {
            for(int l1 = k; l1 < l; l1++)
            {
                for(int i2 = i1; i2 < j1; i2++)
                {
                    int j2 = getBlockId(k1, l1, i2);
                    if(j2 == Block.fire.blockID || j2 == Block.lavaStill.blockID || j2 == Block.lavaMoving.blockID)
                    {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public boolean func_490_a(AxisAlignedBB axisalignedbb, Material material, Entity entity)
    {
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        boolean flag = false;
        Vec3D vec3d = Vec3D.createVector(0.0D, 0.0D, 0.0D);
        for(int k1 = i; k1 < j; k1++)
        {
            for(int l1 = k; l1 < l; l1++)
            {
                for(int i2 = i1; i2 < j1; i2++)
                {
                    Block block = Block.blocksList[getBlockId(k1, l1, i2)];
                    if(block == null || block.blockMaterial != material)
                    {
                        continue;
                    }
                    double d1 = (float)(l1 + 1) - BlockFluids.setFluidHeight(getBlockMetadata(k1, l1, i2));
                    if((double)l >= d1)
                    {
                        flag = true;
                        block.velocityToAddToEntity(this, k1, l1, i2, entity, vec3d);
                    }
                }

            }

        }

        if(vec3d.lengthVector() > 0.0D)
        {
            vec3d = vec3d.normalize();
            double d = 0.0040000000000000001D;
            entity.motionX += vec3d.xCoord * d;
            entity.motionY += vec3d.yCoord * d;
            entity.motionZ += vec3d.zCoord * d;
        }
        return flag;
    }

    public boolean isMaterialInBB(AxisAlignedBB axisalignedbb, Material material)
    {
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for(int k1 = i; k1 < j; k1++)
        {
            for(int l1 = k; l1 < l; l1++)
            {
                for(int i2 = i1; i2 < j1; i2++)
                {
                    Block block = Block.blocksList[getBlockId(k1, l1, i2)];
                    if(block != null && block.blockMaterial == material)
                    {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public boolean func_524_b(AxisAlignedBB axisalignedbb, Material material)
    {
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for(int k1 = i; k1 < j; k1++)
        {
            for(int l1 = k; l1 < l; l1++)
            {
                for(int i2 = i1; i2 < j1; i2++)
                {
                    Block block = Block.blocksList[getBlockId(k1, l1, i2)];
                    if(block == null || block.blockMaterial != material)
                    {
                        continue;
                    }
                    int j2 = getBlockMetadata(k1, l1, i2);
                    double d = l1 + 1;
                    if(j2 < 8)
                    {
                        d = (double)(l1 + 1) - (double)j2 / 8D;
                    }
                    if(d >= axisalignedbb.minY)
                    {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public Explosion createExplosion(Entity entity, double d, double d1, double d2,
                                     float f)
    {
        return func_12015_a(entity, d, d1, d2, f, false);
    }

    public Explosion func_12015_a(Entity entity, double d, double d1, double d2, 
            float f, boolean flag)
    {
        Explosion explosion = new Explosion(this, entity, d, d1, d2, f);
        explosion.field_12031_a = flag;
        explosion.func_12023_a();
        explosion.func_732_a();
        return explosion;
    }

    public float func_494_a(Vec3D vec3d, AxisAlignedBB axisalignedbb)
    {
        double d = 1.0D / ((axisalignedbb.maxX - axisalignedbb.minX) * 2D + 1.0D);
        double d1 = 1.0D / ((axisalignedbb.maxY - axisalignedbb.minY) * 2D + 1.0D);
        double d2 = 1.0D / ((axisalignedbb.maxZ - axisalignedbb.minZ) * 2D + 1.0D);
        int i = 0;
        int j = 0;
        for(float f = 0.0F; f <= 1.0F; f = (float)((double)f + d))
        {
            for(float f1 = 0.0F; f1 <= 1.0F; f1 = (float)((double)f1 + d1))
            {
                for(float f2 = 0.0F; f2 <= 1.0F; f2 = (float)((double)f2 + d2))
                {
                    double d3 = axisalignedbb.minX + (axisalignedbb.maxX - axisalignedbb.minX) * (double)f;
                    double d4 = axisalignedbb.minY + (axisalignedbb.maxY - axisalignedbb.minY) * (double)f1;
                    double d5 = axisalignedbb.minZ + (axisalignedbb.maxZ - axisalignedbb.minZ) * (double)f2;
                    if(func_486_a(Vec3D.createVector(d3, d4, d5), vec3d) == null)
                    {
                        i++;
                    }
                    j++;
                }

            }

        }

        return (float)i / (float)j;
    }

    public TileEntity getBlock(int i, int j, int k)
    {
        Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
        if(chunk != null)
        {
            return chunk.func_338_d(i & 0xf, j, k & 0xf);
        } else
        {
            return null;
        }
    }

    public void func_473_a(int i, int j, int k, TileEntity tileentity)
    {
        Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
        if(chunk != null)
        {
            chunk.func_352_a(i & 0xf, j, k & 0xf, tileentity);
        }
    }

    public void func_513_l(int i, int j, int k)
    {
        Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
        if(chunk != null)
        {
            chunk.func_359_e(i & 0xf, j, k & 0xf);
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

    public boolean func_6156_d()
    {
        if(field_4265_J >= 50)
        {
            return false;
        }
        field_4265_J++;
        try
        {
            int i = 5000;
            for(; field_821.size() > 0; ((MetadataChunkBlock)field_821.remove(field_821.size() - 1)).func_4107_a(this))
            {
                if(--i <= 0)
                {
                    boolean flag = true;
                    return flag;
                }
            }

            boolean flag1 = false;
            return flag1;
        }
        finally
        {
            field_4265_J--;
        }
    }

    public void func_483_a(EnumSkyBlock enumskyblock, int i, int j, int k, int l, int i1, int j1)
    {
        func_484_a(enumskyblock, i, j, k, l, i1, j1, true);
    }

    public void func_484_a(EnumSkyBlock enumskyblock, int i, int j, int k, int l, int i1, int j1, 
            boolean flag)
    {
        if(field_4272_q.field_4306_c && enumskyblock == EnumSkyBlock.Sky)
        {
            return;
        }
        field_4268_y++;
        if(field_4268_y == 50)
        {
            field_4268_y--;
            return;
        }
        int k1 = (l + i) / 2;
        int l1 = (j1 + k) / 2;
        if(!func_530_e(k1, 64, l1))
        {
            field_4268_y--;
            return;
        }
        int i2 = field_821.size();
        if(flag)
        {
            int j2 = 4;
            if(j2 > i2)
            {
                j2 = i2;
            }
            for(int k2 = 0; k2 < j2; k2++)
            {
                MetadataChunkBlock metadatachunkblock = (MetadataChunkBlock)field_821.get(field_821.size() - k2 - 1);
                if(metadatachunkblock.field_957_a == enumskyblock && metadatachunkblock.func_692_a(i, j, k, l, i1, j1))
                {
                    field_4268_y--;
                    return;
                }
            }

        }
        field_821.add(new MetadataChunkBlock(enumskyblock, i, j, k, l, i1, j1));
        if(field_821.size() > 0x186a0)
        {
            field_821.clear();
        }
        field_4268_y--;
    }

    public void calculateInitialSkylight()
    {
        int i = calculateSkylightSubtracted(1.0F);
        if(i != skylightSubtracted)
        {
            skylightSubtracted = i;
        }
    }

    public void tick()
    {
        SpawnerAnimals.SpawnMobs(this);
        chunkProvider.func_361_a();
        int i = calculateSkylightSubtracted(1.0F);
        if(i != skylightSubtracted)
        {
            skylightSubtracted = i;
            for(int j = 0; j < field_798_r.size(); j++)
            {
                ((IWorldAccess)field_798_r.get(j)).func_684_a();
            }

        }
        worldTime++;
        if(worldTime % (long)field_4277_j == 0L)
        {
            func_485_a(false, null);
        }
        TickUpdates(false);
        func_4073_g();
    }

    protected void func_4073_g()
    {
        field_4264_K.clear();
        for(int i = 0; i < playerEntities.size(); i++)
        {
            EntityPlayer entityplayer = (EntityPlayer)playerEntities.get(i);
            int j = MathHelper.floor_double(entityplayer.posX / 16D);
            int l = MathHelper.floor_double(entityplayer.posZ / 16D);
            byte byte0 = 9;
            for(int j1 = -byte0; j1 <= byte0; j1++)
            {
                for(int i2 = -byte0; i2 <= byte0; i2++)
                {
                    field_4264_K.add(new ChunkCoordIntPair(j1 + j, i2 + l));
                }

            }

        }

        if(field_4263_L > 0)
        {
            field_4263_L--;
        }
        for(Iterator iterator = field_4264_K.iterator(); iterator.hasNext();)
        {
            ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)iterator.next();
            int k = chunkcoordintpair.field_152_a * 16;
            int i1 = chunkcoordintpair.field_151_b * 16;
            Chunk chunk = getChunkFromChunkCoords(chunkcoordintpair.field_152_a, chunkcoordintpair.field_151_b);
            if(field_4263_L == 0)
            {
                field_4279_g = field_4279_g * 3 + field_4278_h;
                int k1 = field_4279_g >> 2;
                int j2 = k1 & 0xf;
                int l2 = k1 >> 8 & 0xf;
                int j3 = k1 >> 16 & 0x7f;
                int l3 = chunk.getBlockID(j2, j3, l2);
                j2 += k;
                l2 += i1;
                if(l3 == 0 && getBlockLightValue(j2, j3, l2) <= rand.nextInt(8) && getSavedLightValue(EnumSkyBlock.Sky, j2, j3, l2) <= 0)
                {
                    EntityPlayer entityplayer1 = getClosestPlayer((double)j2 + 0.5D, (double)j3 + 0.5D, (double)l2 + 0.5D, 8D);
                    if(entityplayer1 != null && entityplayer1.getDistanceSq((double)j2 + 0.5D, (double)j3 + 0.5D, (double)l2 + 0.5D) > 4D)
                    {
                        playSoundEffect((double)j2 + 0.5D, (double)j3 + 0.5D, (double)l2 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + rand.nextFloat() * 0.2F);
                        field_4263_L = rand.nextInt(12000) + 6000;
                    }
                }
            }
            int l1 = 0;
            while(l1 < 80) 
            {
                field_4279_g = field_4279_g * 3 + field_4278_h;
                int k2 = field_4279_g >> 2;
                int i3 = k2 & 0xf;
                int k3 = k2 >> 8 & 0xf;
                int i4 = k2 >> 16 & 0x7f;
                byte byte1 = chunk.blocks[i3 << 11 | k3 << 7 | i4];
                if(Block.tickOnLoad[byte1])
                {
                    Block.blocksList[byte1].updateTick(this, i3 + k, i4, k3 + i1, rand);
                }
                l1++;
            }
        }

    }

    public boolean TickUpdates(boolean flag)
    {
        int i = scheduledTickTreeSet.size();
        if(i != scheduledTickSet.size())
        {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        if(i > 1000)
        {
            i = 1000;
        }
        for(int j = 0; j < i; j++)
        {
            NextTickListEntry nextticklistentry = (NextTickListEntry)scheduledTickTreeSet.first();
            if(!flag && nextticklistentry.scheduledTime > worldTime)
            {
                break;
            }
            scheduledTickTreeSet.remove(nextticklistentry);
            scheduledTickSet.remove(nextticklistentry);
            byte byte0 = 8;
            if(!checkChunksExist(nextticklistentry.xCoord - byte0, nextticklistentry.yCoord - byte0, nextticklistentry.zCoord - byte0, nextticklistentry.xCoord + byte0, nextticklistentry.yCoord + byte0, nextticklistentry.zCoord + byte0))
            {
                continue;
            }
            int k = getBlockId(nextticklistentry.xCoord, nextticklistentry.yCoord, nextticklistentry.zCoord);
            if(k == nextticklistentry.blockID && k > 0)
            {
                Block.blocksList[k].updateTick(this, nextticklistentry.xCoord, nextticklistentry.yCoord, nextticklistentry.zCoord, rand);
            }
        }

        return scheduledTickTreeSet.size() != 0;
    }

    public List getEntitiesWithinAABBExcludingEntity(Entity entity, AxisAlignedBB axisalignedbb)
    {
        field_778_L.clear();
        int i = MathHelper.floor_double((axisalignedbb.minX - 2D) / 16D);
        int j = MathHelper.floor_double((axisalignedbb.maxX + 2D) / 16D);
        int k = MathHelper.floor_double((axisalignedbb.minZ - 2D) / 16D);
        int l = MathHelper.floor_double((axisalignedbb.maxZ + 2D) / 16D);
        for(int i1 = i; i1 <= j; i1++)
        {
            for(int j1 = k; j1 <= l; j1++)
            {
                if(chunkExists(i1, j1))
                {
                    getChunkFromChunkCoords(i1, j1).getEntitiesWithinAABBForEntity(entity, axisalignedbb, field_778_L);
                }
            }

        }

        return field_778_L;
    }

    public List getEntitiesWithinAABB(Class class1, AxisAlignedBB axisalignedbb)
    {
        int i = MathHelper.floor_double((axisalignedbb.minX - 2D) / 16D);
        int j = MathHelper.floor_double((axisalignedbb.maxX + 2D) / 16D);
        int k = MathHelper.floor_double((axisalignedbb.minZ - 2D) / 16D);
        int l = MathHelper.floor_double((axisalignedbb.maxZ + 2D) / 16D);
        ArrayList arraylist = new ArrayList();
        for(int i1 = i; i1 <= j; i1++)
        {
            for(int j1 = k; j1 <= l; j1++)
            {
                if(chunkExists(i1, j1))
                {
                    getChunkFromChunkCoords(i1, j1).getEntitiesOfTypeWithinAAAB(class1, axisalignedbb, arraylist);
                }
            }

        }

        return arraylist;
    }

    public void func_515_b(int i, int j, int k, TileEntity tileentity)
    {
        if(func_530_e(i, j, k))
        {
            getChunkFromBlockCoords(i, k).func_336_e();
        }
        for(int l = 0; l < field_798_r.size(); l++)
        {
            ((IWorldAccess)field_798_r.get(l)).func_686_a(i, j, k, tileentity);
        }

    }

    public int countEntities(Class class1)
    {
        int i = 0;
        for(int j = 0; j < EntityList.size(); j++)
        {
            Entity entity = (Entity) EntityList.get(j);
            if(class1.isAssignableFrom(entity.getClass()))
            {
                i++;
            }
        }

        return i;
    }

    public void func_464_a(List list)
    {
        EntityList.addAll(list);
        for(int i = 0; i < list.size(); i++)
        {
            func_479_b((Entity)list.get(i));
        }

    }

    public void func_461_b(List list)
    {
        field_790_z.addAll(list);
    }

    public boolean func_516_a(int i, int j, int k, int l, boolean flag)
    {
        int i1 = getBlockId(j, k, l);
        Block block = Block.blocksList[i1];
        Block block1 = Block.blocksList[i];
        AxisAlignedBB axisalignedbb = block1.getCollisionBoundingBoxFromPool(this, j, k, l);
        if(flag)
        {
            axisalignedbb = null;
        }
        if(axisalignedbb != null && !func_522_a(axisalignedbb))
        {
            return false;
        }
        if(block == Block.waterStill || block == Block.waterMoving || block == Block.lavaStill || block == Block.lavaMoving || block == Block.fire || block == Block.snow)
        {
            return true;
        }
        return i > 0 && block == null && block1.canPlaceBlockAt(this, j, k, l);
    }

    public PathEntity func_482_a(Entity entity, Entity entity1, float f)
    {
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posY);
        int k = MathHelper.floor_double(entity.posZ);
        int l = (int)(f + 16F);
        int i1 = i - l;
        int j1 = j - l;
        int k1 = k - l;
        int l1 = i + l;
        int i2 = j + l;
        int j2 = k + l;
        ChunkCache chunkcache = new ChunkCache(this, i1, j1, k1, l1, i2, j2);
        return (new Pathfinder(chunkcache)).createEntityPathTo(entity, entity1, f);
    }

    public PathEntity func_501_a(Entity entity, int i, int j, int k, float f)
    {
        int l = MathHelper.floor_double(entity.posX);
        int i1 = MathHelper.floor_double(entity.posY);
        int j1 = MathHelper.floor_double(entity.posZ);
        int k1 = (int)(f + 8F);
        int l1 = l - k1;
        int i2 = i1 - k1;
        int j2 = j1 - k1;
        int k2 = l + k1;
        int l2 = i1 + k1;
        int i3 = j1 + k1;
        ChunkCache chunkcache = new ChunkCache(this, l1, i2, j2, k2, l2, i3);
        return (new Pathfinder(chunkcache)).createEntityPathTo(entity, i, j, k, f);
    }

    public boolean isBlockProvidingPowerTo(int i, int j, int k, int l)
    {
        int i1 = getBlockId(i, j, k);
        if(i1 == 0)
        {
            return false;
        } else
        {
            return Block.blocksList[i1].isIndirectlyPoweringTo(this, i, j, k, l);
        }
    }

    public boolean isBlockGettingPowered(int i, int j, int k)
    {
        if(isBlockProvidingPowerTo(i, j - 1, k, 0))
        {
            return true;
        }
        if(isBlockProvidingPowerTo(i, j + 1, k, 1))
        {
            return true;
        }
        if(isBlockProvidingPowerTo(i, j, k - 1, 2))
        {
            return true;
        }
        if(isBlockProvidingPowerTo(i, j, k + 1, 3))
        {
            return true;
        }
        if(isBlockProvidingPowerTo(i - 1, j, k, 4))
        {
            return true;
        }
        return isBlockProvidingPowerTo(i + 1, j, k, 5);
    }

    public boolean isBlockIndirectlyProvidingPowerTo(int i, int j, int k, int l)
    {
        if(doesBlockAllowAttachment(i, j, k))
        {
            return isBlockGettingPowered(i, j, k);
        }
        int i1 = getBlockId(i, j, k);
        if(i1 == 0)
        {
            return false;
        } else
        {
            return Block.blocksList[i1].isPoweringTo(this, i, j, k, l);
        }
    }

    public boolean isBlockIndirectlyGettingPowered(int i, int j, int k)
    {
        if(isBlockIndirectlyProvidingPowerTo(i, j - 1, k, 0))
        {
            return true;
        }
        if(isBlockIndirectlyProvidingPowerTo(i, j + 1, k, 1))
        {
            return true;
        }
        if(isBlockIndirectlyProvidingPowerTo(i, j, k - 1, 2))
        {
            return true;
        }
        if(isBlockIndirectlyProvidingPowerTo(i, j, k + 1, 3))
        {
            return true;
        }
        if(isBlockIndirectlyProvidingPowerTo(i - 1, j, k, 4))
        {
            return true;
        }
        return isBlockIndirectlyProvidingPowerTo(i + 1, j, k, 5);
    }

    public EntityPlayer getClosestPlayerToEntity(Entity entity, double d)
    {
        return getClosestPlayer(entity.posX, entity.posY, entity.posZ, d);
    }

    public EntityPlayer getClosestPlayer(double d, double d1, double d2, double d3)
    {
        double d4 = -1D;
        EntityPlayer entityplayer = null;
        for(int i = 0; i < playerEntities.size(); i++)
        {
            EntityPlayer entityplayer1 = (EntityPlayer)playerEntities.get(i);
            double d5 = entityplayer1.getDistanceSq(d, d1, d2);
            if((d3 < 0.0D || d5 < d3 * d3) && (d4 == -1D || d5 < d4))
            {
                d4 = d5;
                entityplayer = entityplayer1;
            }
        }

        return entityplayer;
    }

    public byte[] func_504_c(int i, int j, int k, int l, int i1, int j1)
    {
        byte abyte0[] = new byte[(l * i1 * j1 * 5) / 2];
        int k1 = i >> 4;
        int l1 = k >> 4;
        int i2 = (i + l) - 1 >> 4;
        int j2 = (k + j1) - 1 >> 4;
        int k2 = 0;
        int l2 = j;
        int i3 = j + i1;
        if(l2 < 0)
        {
            l2 = 0;
        }
        if(i3 > 128)
        {
            i3 = 128;
        }
        for(int j3 = k1; j3 <= i2; j3++)
        {
            int k3 = i - j3 * 16;
            int l3 = (i + l) - j3 * 16;
            if(k3 < 0)
            {
                k3 = 0;
            }
            if(l3 > 16)
            {
                l3 = 16;
            }
            for(int i4 = l1; i4 <= j2; i4++)
            {
                int j4 = k - i4 * 16;
                int k4 = (k + j1) - i4 * 16;
                if(j4 < 0)
                {
                    j4 = 0;
                }
                if(k4 > 16)
                {
                    k4 = 16;
                }
                k2 = getChunkFromChunkCoords(j3, i4).func_340_a(abyte0, k3, l2, j4, l3, i3, k4, k2);
            }

        }

        return abyte0;
    }

    public void func_476_g()
    {
        try
        {
            File file = new File(field_797_s, "session.lock");
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(file));
            try
            {
                if(datainputstream.readLong() != field_784_F)
                {
                    throw new MinecraftException("The save is being accessed from another location, aborting");
                }
            }
            finally
            {
                datainputstream.close();
            }
        }
        catch(IOException ioexception)
        {
            throw new MinecraftException("Failed to check session lock, aborting");
        }
    }

    public boolean func_6157_a(EntityPlayer entityplayer, int i, int j, int k)
    {
        return true;
    }

    public void func_9206_a(Entity entity, byte byte0)
    {
    }

    public boolean field_4280_a;
    private List field_821;
    public List EntityList;
    private List field_790_z;
    private TreeSet scheduledTickTreeSet;
    private Set scheduledTickSet;
    public List field_814_b;
    public List playerEntities;
    public long worldTime;
    private long field_6159_E;
    public int skylightSubtracted;
    protected int field_4279_g;
    protected int field_4278_h;
    public boolean field_808_h;
    private long field_784_F;
    protected int field_4277_j;
    public int monstersEnabled;
    public Random rand;
    public int spawnX;
    public int spawnY;
    public int spawnZ;
    public boolean field_9212_p;
    public final WorldProvider field_4272_q;
    protected List field_798_r;
    private IChunkProvider chunkProvider;
    public File field_9211_s;
    public File field_797_s;
    public long randomSeed;
    private NBTTagCompound nbtCompoundPlayer;
    public long sizeOnDisk;
    public final String field_9210_w;
    public boolean field_9209_x;
    private ArrayList field_9207_I;
    private int field_4265_J;
    static int field_4268_y = 0;
    private Set field_4264_K;
    private int field_4263_L;
    private List field_778_L;
    public boolean multiplayerWorld;

}
