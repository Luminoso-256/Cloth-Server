package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class EntityArrow extends Entity {

    public int field_9184_a;
    public EntityLiving field_439_ah;
    private int field_9183_c;
    private int field_9182_d;
    private int field_9180_e;
    private int field_9179_f;
    private boolean field_9181_aj;
    private int field_438_ai;
    private int field_437_aj;
    public EntityArrow(World world) {
        super(world);
        field_9183_c = -1;
        field_9182_d = -1;
        field_9180_e = -1;
        field_9179_f = 0;
        field_9181_aj = false;
        field_9184_a = 0;
        field_437_aj = 0;
        setSize(0.5F, 0.5F);
    }
    public EntityArrow(World world, EntityLiving entityliving) {
        super(world);
        field_9183_c = -1;
        field_9182_d = -1;
        field_9180_e = -1;
        field_9179_f = 0;
        field_9181_aj = false;
        field_9184_a = 0;
        field_437_aj = 0;
        field_439_ah = entityliving;
        setSize(0.5F, 0.5F);
        setLocationAndAngles(entityliving.posX, entityliving.posY + (double) entityliving.func_104_p(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
        motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F);
        motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F);
        func_177_a(motionX, motionY, motionZ, 1.5F, 1.0F);
    }

    public void func_177_a(double d, double d1, double d2, float f,
                           float f1) {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += random.nextGaussian() * 0.0074999998323619366D * (double) f1;
        d1 += random.nextGaussian() * 0.0074999998323619366D * (double) f1;
        d2 += random.nextGaussian() * 0.0074999998323619366D * (double) f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        prevRotationYaw = rotationYaw = (float) ((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
        prevRotationPitch = rotationPitch = (float) ((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
        field_438_ai = 0;
    }

    public void onUpdate() {
        super.onUpdate();
        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            prevRotationYaw = rotationYaw = (float) ((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
            prevRotationPitch = rotationPitch = (float) ((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D);
        }
        if (field_9184_a > 0) {
            field_9184_a--;
        }
        if (field_9181_aj) {
            int i = worldObj.getBlockId(field_9183_c, field_9182_d, field_9180_e);
            if (i != field_9179_f) {
                field_9181_aj = false;
                motionX *= random.nextFloat() * 0.2F;
                motionY *= random.nextFloat() * 0.2F;
                motionZ *= random.nextFloat() * 0.2F;
                field_438_ai = 0;
                field_437_aj = 0;
            } else {
                field_438_ai++;
                if (field_438_ai == 1200) {
                    setEntityDead();
                }
                return;
            }
        } else {
            field_437_aj++;
        }
        Vec3D vec3d = Vec3D.createVector(posX, posY, posZ);
        Vec3D vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition movingobjectposition = worldObj.func_486_a(vec3d, vec3d1);
        vec3d = Vec3D.createVector(posX, posY, posZ);
        vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        if (movingobjectposition != null) {
            vec3d1 = Vec3D.createVector(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }
        Entity entity = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expands(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for (int j = 0; j < list.size(); j++) {
            Entity entity1 = (Entity) list.get(j);
            if (!entity1.func_129_c_() || entity1 == field_439_ah && field_437_aj < 5) {
                continue;
            }
            float f4 = 0.3F;
            AxisAlignedBB axisalignedbb = entity1.boundingBox.expands(f4, f4, f4);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.func_706_a(vec3d, vec3d1);
            if (movingobjectposition1 == null) {
                continue;
            }
            double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);
            if (d1 < d || d == 0.0D) {
                entity = entity1;
                d = d1;
            }
        }

        if (entity != null) {
            movingobjectposition = new MovingObjectPosition(entity);
        }
        if (movingobjectposition != null) {
            if (movingobjectposition.entityHit != null) {
                if (movingobjectposition.entityHit.attackEntity(field_439_ah, 4)) {
                    worldObj.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
                    setEntityDead();
                } else {
                    motionX *= -0.10000000149011612D;
                    motionY *= -0.10000000149011612D;
                    motionZ *= -0.10000000149011612D;
                    rotationYaw += 180F;
                    prevRotationYaw += 180F;
                    field_437_aj = 0;
                }
            } else {
                field_9183_c = movingobjectposition.blockX;
                field_9182_d = movingobjectposition.blockY;
                field_9180_e = movingobjectposition.blockZ;
                field_9179_f = worldObj.getBlockId(field_9183_c, field_9182_d, field_9180_e);
                motionX = (float) (movingobjectposition.hitVec.xCoord - posX);
                motionY = (float) (movingobjectposition.hitVec.yCoord - posY);
                motionZ = (float) (movingobjectposition.hitVec.zCoord - posZ);
                float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                posX -= (motionX / (double) f1) * 0.05000000074505806D;
                posY -= (motionY / (double) f1) * 0.05000000074505806D;
                posZ -= (motionZ / (double) f1) * 0.05000000074505806D;
                worldObj.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
                field_9181_aj = true;
                field_9184_a = 7;
            }
        }
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float) ((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
        for (rotationPitch = (float) ((Math.atan2(motionY, f2) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) {
        }
        for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) {
        }
        for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) {
        }
        for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) {
        }
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f3 = 0.99F;
        float f5 = 0.03F;
        if (handleWaterMovement()) {
            for (int k = 0; k < 4; k++) {
                float f6 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * (double) f6, posY - motionY * (double) f6, posZ - motionZ * (double) f6, motionX, motionY, motionZ);
            }

            f3 = 0.8F;
        }
        motionX *= f3;
        motionY *= f3;
        motionZ *= f3;
        motionY -= f5;
        setPosition(posX, posY, posZ);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("xTile", (short) field_9183_c);
        nbttagcompound.setShort("yTile", (short) field_9182_d);
        nbttagcompound.setShort("zTile", (short) field_9180_e);
        nbttagcompound.setByte("inTile", (byte) field_9179_f);
        nbttagcompound.setByte("shake", (byte) field_9184_a);
        nbttagcompound.setByte("inGround", (byte) (field_9181_aj ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        field_9183_c = nbttagcompound.getShort("xTile");
        field_9182_d = nbttagcompound.getShort("yTile");
        field_9180_e = nbttagcompound.getShort("zTile");
        field_9179_f = nbttagcompound.getByte("inTile") & 0xff;
        field_9184_a = nbttagcompound.getByte("shake") & 0xff;
        field_9181_aj = nbttagcompound.getByte("inGround") == 1;
    }

    public void onCollideWithPlayer(EntityPlayer entityplayer) {
        if (worldObj.multiplayerWorld) {
            return;
        }
        if (field_9181_aj && field_439_ah == entityplayer && field_9184_a <= 0 && entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.arrow.swiftedIndex, 1))) {
            worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.func_163_c(this, 1);
            setEntityDead();
        }
    }
}
