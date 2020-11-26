package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.cloth.file.GameruleManagerDeluxe;

public class EntityCreeper extends EntityMobs
{

    public EntityCreeper(World world)
    {
        super(world);
        field_408_ad = 30;
        field_407_ae = -1;
        field_12011_e = -1;
        field_9119_aG = "/mob/creeper.png";
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
    }

    public void onUpdate()
    {
        field_405_b = field_406_a;
        if(worldObj.multiplayerWorld)
        {
            field_406_a += field_407_ae;
            if(field_406_a < 0)
            {
                field_406_a = 0;
            }
            if(field_406_a >= field_408_ad)
            {
                field_406_a = field_408_ad;
            }
        }
        super.onUpdate();
    }

    protected void func_152_d_()
    {
        if(field_12011_e != field_407_ae)
        {
            field_12011_e = field_407_ae;
            if(field_407_ae > 0)
            {
                worldObj.func_9206_a(this, (byte)4);
            } else
            {
                worldObj.func_9206_a(this, (byte)5);
            }
        }
        field_405_b = field_406_a;
        if(worldObj.multiplayerWorld)
        {
            super.func_152_d_();
        } else
        {
            if(field_406_a > 0 && field_407_ae < 0)
            {
                field_406_a--;
            }
            if(field_407_ae >= 0)
            {
                field_407_ae = 2;
            }
            super.func_152_d_();
            if(field_407_ae != 1)
            {
                field_407_ae = -1;
            }
        }
    }

    protected String getHurtSound()
    {
        return "mob.creeper";
    }

    protected String getDeathSound()
    {
        return "mob.creeperdeath";
    }

    public void onDeath(Entity entity)
    {
        super.onDeath(entity);
        if(entity instanceof EntitySkeleton)
        {
            dropItem(Item.record13.swiftedIndex + random.nextInt(2), 0, 1);
        }
    }

    protected void func_157_a(Entity entity, float f)
    {
        if(field_407_ae <= 0 && f < 3F || field_407_ae > 0 && f < 7F)
        {
            if(field_406_a == 0)
            {
                worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 0.5F);
            }
            field_407_ae = 1;
            field_406_a++;
            if(field_406_a == field_408_ad)
            {
              //  GameruleManager gameruleManager = new GameruleManager(new File("server.gamerules"));
                if(gameruleManager.getGamerule("domobgriefing", true)) {
                    worldObj.createExplosion(this, posX, posY, posZ, 3F);
                }
                setEntityDead();
            }
            field_387_ah = true;
        }
    }

    protected int getDropItemId()
    {
        return Item.gunpowder.swiftedIndex;
    }

    int field_406_a;
    int field_405_b;
    int field_408_ad;
    int field_407_ae;
    int field_12011_e;
    
    private GameruleManagerDeluxe gameruleManager = GameruleManagerDeluxe.getInstance();
}
