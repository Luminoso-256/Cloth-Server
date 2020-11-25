package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class EntitySnowball extends Entity
{

    public EntitySnowball(World world)
    {
        super(world);
        field_456_b = -1;
        field_461_ad = -1;
        field_460_ae = -1;
        field_459_af = 0;
        field_457_ag = false;
        field_458_a = 0;
        field_453_aj = 0;
        setSize(0.25F, 0.25F);
    }

    public EntitySnowball(World world, EntityLiving entityliving)
    {
        super(world);
        field_456_b = -1;
        field_461_ad = -1;
        field_460_ae = -1;
        field_459_af = 0;
        field_457_ag = false;
        field_458_a = 0;
        field_453_aj = 0;
        field_455_ah = entityliving;
        setSize(0.25F, 0.25F);
        func_107_c(entityliving.posX, entityliving.posY + (double)entityliving.func_104_p(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        float f = 0.4F;
        motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f;
        func_6141_a(motionX, motionY, motionZ, 1.5F, 1.0F);
    }

    public void func_6141_a(double d, double d1, double d2, float f, 
            float f1)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d1 += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d2 += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
        field_454_ai = 0;
    }

    public void onUpdate()
    {
        field_9071_O = posX;
        field_9070_P = posY;
        field_9069_Q = posZ;
        super.onUpdate();
        if(field_458_a > 0)
        {
            field_458_a--;
        }
        if(field_457_ag)
        {
            int i = worldObj.getBlockId(field_456_b, field_461_ad, field_460_ae);
            if(i != field_459_af)
            {
                field_457_ag = false;
                motionX *= random.nextFloat() * 0.2F;
                motionY *= random.nextFloat() * 0.2F;
                motionZ *= random.nextFloat() * 0.2F;
                field_454_ai = 0;
                field_453_aj = 0;
            } else
            {
                field_454_ai++;
                if(field_454_ai == 1200)
                {
                    setEntityDead();
                }
                return;
            }
        } else
        {
            field_453_aj++;
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
        if(!worldObj.multiplayerWorld)
        {
            Entity entity = null;
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expands(1.0D, 1.0D, 1.0D));
            double d = 0.0D;
            for(int l = 0; l < list.size(); l++)
            {
                Entity entity1 = (Entity)list.get(l);
                if(!entity1.func_129_c_() || entity1 == field_455_ah && field_453_aj < 5)
                {
                    continue;
                }
                float f4 = 0.3F;
                AxisAlignedBB axisalignedbb = entity1.boundingBox.expands(f4, f4, f4);
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
        }
        if(movingobjectposition != null)
        {
            if(movingobjectposition.entityHit != null)
            {
                if(!movingobjectposition.entityHit.attackEntity(field_455_ah, 0));
            }
            for(int j = 0; j < 8; j++)
            {
                worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            }

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
        float f1 = 0.99F;
        float f2 = 0.03F;
        if(handleWaterMovement())
        {
            for(int k = 0; k < 4; k++)
            {
                float f3 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * (double)f3, posY - motionY * (double)f3, posZ - motionZ * (double)f3, motionX, motionY, motionZ);
            }

            f1 = 0.8F;
        }
        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;
        motionY -= f2;
        setPosition(posX, posY, posZ);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)field_456_b);
        nbttagcompound.setShort("yTile", (short)field_461_ad);
        nbttagcompound.setShort("zTile", (short)field_460_ae);
        nbttagcompound.setByte("inTile", (byte)field_459_af);
        nbttagcompound.setByte("shake", (byte)field_458_a);
        nbttagcompound.setByte("inGround", (byte)(field_457_ag ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        field_456_b = nbttagcompound.getShort("xTile");
        field_461_ad = nbttagcompound.getShort("yTile");
        field_460_ae = nbttagcompound.getShort("zTile");
        field_459_af = nbttagcompound.getByte("inTile") & 0xff;
        field_458_a = nbttagcompound.getByte("shake") & 0xff;
        field_457_ag = nbttagcompound.getByte("inGround") == 1;
    }

    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
        if(field_457_ag && field_455_ah == entityplayer && field_458_a <= 0 && entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.arrow.swiftedIndex, 1)))
        {
            worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.func_163_c(this, 1);
            setEntityDead();
        }
    }

    private int field_456_b;
    private int field_461_ad;
    private int field_460_ae;
    private int field_459_af;
    private boolean field_457_ag;
    public int field_458_a;
    private EntityLiving field_455_ah;
    private int field_454_ai;
    private int field_453_aj;
}
