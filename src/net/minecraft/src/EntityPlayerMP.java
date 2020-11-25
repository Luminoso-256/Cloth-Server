package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;
import src.net.minecraft.server.MinecraftServer;

public class EntityPlayerMP extends EntityPlayer
{

    public EntityPlayerMP(MinecraftServer minecraftserver, World world, String s, ItemInWorldManager iteminworldmanager)
    {
        super(world);
        field_422_ag = new LinkedList();
        field_420_ah = new HashSet();
        field_12012_al = false;
        field_9156_bu = 0xfa0a1f01;
        field_15004_bw = 60;
        int i = world.spawnX;
        int j = world.spawnZ;
        int k = world.spawnY;
        if(!world.worldProvider.field_4306_c)
        {
            i += random.nextInt(20) - 10;
            k = world.func_4075_e(i, j);
            j += random.nextInt(20) - 10;
        }
        func_107_c((double)i + 0.5D, k, (double)j + 0.5D, 0.0F, 0.0F);
        mcServer = minecraftserver;
        field_9067_S = 0.0F;
        iteminworldmanager.field_675_a = this;
        username = s;
        field_425_ad = iteminworldmanager;
        yOffset = 0.0F;
    }

    public void onUpdate()
    {
        field_15004_bw--;
    }

    public void onDeath(Entity entity)
    {
        inventory.dropAllItems();
    }

    public boolean attackEntity(Entity entity, int i)
    {
        if(field_15004_bw > 0)
        {
            return false;
        }
        if(!mcServer.field_9011_n)
        {
            if(entity instanceof EntityPlayer)
            {
                return false;
            }
            if(entity instanceof EntityArrow)
            {
                EntityArrow entityarrow = (EntityArrow)entity;
                if(entityarrow.field_439_ah instanceof EntityPlayer)
                {
                    return false;
                }
            }
        }
        return super.attackEntity(entity, i);
    }

    public void heal(int i)
    {
        super.heal(i);
    }

    public void func_175_i()
    {
        super.onUpdate();
        ChunkCoordIntPair chunkcoordintpair = null;
        double d = 0.0D;
        for(int i = 0; i < field_422_ag.size(); i++)
        {
            ChunkCoordIntPair chunkcoordintpair1 = (ChunkCoordIntPair)field_422_ag.get(i);
            double d1 = chunkcoordintpair1.func_73_a(this);
            if(i == 0 || d1 < d)
            {
                chunkcoordintpair = chunkcoordintpair1;
                d = chunkcoordintpair1.func_73_a(this);
            }
        }

        if(chunkcoordintpair != null)
        {
            boolean flag = false;
            if(d < 1024D)
            {
                flag = true;
            }
            if(field_421_a.func_38_b() < 2)
            {
                flag = true;
            }
            if(flag)
            {
                field_422_ag.remove(chunkcoordintpair);
                field_421_a.sendPacket(new Packet51MapChunk(chunkcoordintpair.field_152_a * 16, 0, chunkcoordintpair.field_151_b * 16, 16, 128, 16, mcServer.overworld));
                List list = mcServer.overworld.func_532_d(chunkcoordintpair.field_152_a * 16, 0, chunkcoordintpair.field_151_b * 16, chunkcoordintpair.field_152_a * 16 + 16, 128, chunkcoordintpair.field_151_b * 16 + 16);
                for(int j = 0; j < list.size(); j++)
                {
                    TileEntity tileentity = (TileEntity)list.get(j);
                    field_421_a.sendPacket(new Packet59ComplexEntity(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, tileentity));
                }

            }
        }
        if(health != field_9156_bu)
        {
            field_421_a.sendPacket(new Packet8(health));
            field_9156_bu = health;
        }
    }

    public void onLivingUpdate()
    {
        motionX = motionY = motionZ = 0.0D;
        field_9128_br = false;
        super.onLivingUpdate();
    }

    public void func_163_c(Entity entity, int i)
    {
        if(!entity.field_304_B)
        {
            if(entity instanceof EntityItem)
            {
                field_421_a.sendPacket(new Packet17AddToInventory(((EntityItem)entity).item, i));
                mcServer.field_6028_k.func_12021_a(entity, new Packet22Collect(entity.field_331_c, field_331_c));
            }
            if(entity instanceof EntityArrow)
            {
                field_421_a.sendPacket(new Packet17AddToInventory(new ItemStack(Item.arrow), 1));
                mcServer.field_6028_k.func_12021_a(entity, new Packet22Collect(entity.field_331_c, field_331_c));
            }
        }
        super.func_163_c(entity, i);
    }

    public void func_168_z()
    {
        if(!field_9148_aq)
        {
            field_9147_ar = -1;
            field_9148_aq = true;
            mcServer.field_6028_k.func_12021_a(this, new Packet18ArmAnimation(this, 1));
        }
    }

    public float func_104_p()
    {
        return 1.62F;
    }

    public void func_6094_e(Entity entity)
    {
        super.func_6094_e(entity);
        field_421_a.sendPacket(new Packet39(this, field_327_g));
        field_421_a.func_41_a(posX, posY, posZ, rotationYaw, rotationPitch);
    }

    protected void interact(double d, boolean flag)
    {
    }

    public void func_9153_b(double d, boolean flag)
    {
        super.interact(d, flag);
    }

    public boolean func_9059_p()
    {
        return field_12012_al;
    }

    public NetServerHandler field_421_a;
    public MinecraftServer mcServer;
    public ItemInWorldManager field_425_ad;
    public double field_9155_d;
    public double field_9154_e;
    public List field_422_ag;
    public Set field_420_ah;
    public double field_418_ai;
    public boolean field_12012_al;
    private int field_9156_bu;
    private int field_15004_bw;
}
