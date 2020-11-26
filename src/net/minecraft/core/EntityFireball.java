package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class EntityFireball extends Entity
{

    public EntityFireball(World world)
    {
        super(world);
        field_9195_e = -1;
        field_9193_f = -1;
        field_9197_aj = -1;
        field_9194_ak = 0;
        field_9192_al = false;
        field_9200_a = 0;
        field_9189_ao = 0;
        setSize(1.0F, 1.0F);
    }

    public EntityFireball(World world, EntityLiving entityliving, double d, double d1, double d2)
    {
        super(world);
        field_9195_e = -1;
        field_9193_f = -1;
        field_9197_aj = -1;
        field_9194_ak = 0;
        field_9192_al = false;
        field_9200_a = 0;
        field_9189_ao = 0;
        field_9191_am = entityliving;
        setSize(1.0F, 1.0F);
        func_107_c(entityliving.posX, entityliving.posY, entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        motionX = motionY = motionZ = 0.0D;
        d += random.nextGaussian() * 0.40000000000000002D;
        d1 += random.nextGaussian() * 0.40000000000000002D;
        d2 += random.nextGaussian() * 0.40000000000000002D;
        double d3 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        field_9199_b = (d / d3) * 0.10000000000000001D;
        field_9198_c = (d1 / d3) * 0.10000000000000001D;
        field_9196_d = (d2 / d3) * 0.10000000000000001D;
    }

    public void onUpdate()
    {
        super.onUpdate();
        field_9061_Z = 10;
        if(field_9200_a > 0)
        {
            field_9200_a--;
        }
        if(field_9192_al)
        {
            int i = worldObj.getBlockId(field_9195_e, field_9193_f, field_9197_aj);
            if(i != field_9194_ak)
            {
                field_9192_al = false;
                motionX *= random.nextFloat() * 0.2F;
                motionY *= random.nextFloat() * 0.2F;
                motionZ *= random.nextFloat() * 0.2F;
                field_9190_an = 0;
                field_9189_ao = 0;
            } else
            {
                field_9190_an++;
                if(field_9190_an == 1200)
                {
                    setEntityDead();
                }
                return;
            }
        } else
        {
            field_9189_ao++;
        }
        Vec3D vec3d = Vec3D.createVector(posX, posY, posZ);
        Vec3D vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition movingobjectposition = worldObj.func_486_a(vec3d, vec3d1);
        vec3d = Vec3D.createVector(posX, posY, posZ);
        vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        if(movingobjectposition != null)
        {
            vec3d1 = Vec3D.createVector(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }
        Entity entity = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expands(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for(int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);
            if(!entity1.func_129_c_() || entity1 == field_9191_am && field_9189_ao < 25)
            {
                continue;
            }
            float f2 = 0.3F;
            AxisAlignedBB axisalignedbb = entity1.boundingBox.expands(f2, f2, f2);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.func_706_a(vec3d, vec3d1);
            if(movingobjectposition1 == null)
            {
                continue;
            }
            double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);
            if(d1 < d || d == 0.0D)
            {
                entity = entity1;
                d = d1;
            }
        }

        if(entity != null)
        {
            movingobjectposition = new MovingObjectPosition(entity);
        }
        if(movingobjectposition != null)
        {
            if(movingobjectposition.entityHit != null)
            {
                if(!movingobjectposition.entityHit.attackEntity(field_9191_am, 0));
            }
            worldObj.func_12015_a(null, posX, posY, posZ, 1.0F, true);
            setEntityDead();
        }
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
        for(rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f1 = 0.95F;
        if(handleWaterMovement())
        {
            for(int k = 0; k < 4; k++)
            {
                float f3 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * (double)f3, posY - motionY * (double)f3, posZ - motionZ * (double)f3, motionX, motionY, motionZ);
            }

            f1 = 0.8F;
        }
        motionX += field_9199_b;
        motionY += field_9198_c;
        motionZ += field_9196_d;
        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;
        worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
        setPosition(posX, posY, posZ);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)field_9195_e);
        nbttagcompound.setShort("yTile", (short)field_9193_f);
        nbttagcompound.setShort("zTile", (short)field_9197_aj);
        nbttagcompound.setByte("inTile", (byte)field_9194_ak);
        nbttagcompound.setByte("shake", (byte)field_9200_a);
        nbttagcompound.setByte("inGround", (byte)(field_9192_al ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        field_9195_e = nbttagcompound.getShort("xTile");
        field_9193_f = nbttagcompound.getShort("yTile");
        field_9197_aj = nbttagcompound.getShort("zTile");
        field_9194_ak = nbttagcompound.getByte("inTile") & 0xff;
        field_9200_a = nbttagcompound.getByte("shake") & 0xff;
        field_9192_al = nbttagcompound.getByte("inGround") == 1;
    }

    public boolean func_129_c_()
    {
        return true;
    }

    public boolean attackEntity(Entity entity, int i)
    {
        func_9060_u();
        if(entity != null)
        {
            Vec3D vec3d = entity.func_4039_B();
            if(vec3d != null)
            {
                motionX = vec3d.xCoord;
                motionY = vec3d.yCoord;
                motionZ = vec3d.zCoord;
                field_9199_b = motionX * 0.10000000000000001D;
                field_9198_c = motionY * 0.10000000000000001D;
                field_9196_d = motionZ * 0.10000000000000001D;
            }
            return true;
        } else
        {
            return false;
        }
    }

    private int field_9195_e;
    private int field_9193_f;
    private int field_9197_aj;
    private int field_9194_ak;
    private boolean field_9192_al;
    public int field_9200_a;
    private EntityLiving field_9191_am;
    private int field_9190_an;
    private int field_9189_ao;
    public double field_9199_b;
    public double field_9198_c;
    public double field_9196_d;
}
