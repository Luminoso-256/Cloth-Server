package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityMobs extends EntityCreature
        implements IMobs {

    protected int field_404_af;

    public EntityMobs(World world) {
        super(world);
        field_404_af = 2;
        health = 20;
    }

    public void onLivingUpdate() {
        float f = getEntityBrightness(1.0F);
        if (f > 0.5F) {
            field_9132_bn += 2;
        }
        super.onLivingUpdate();
    }

    public void onUpdate() {
        super.onUpdate();
        if (worldObj.monstersEnabled == 0) {
            setEntityDead();
        }
    }

    protected Entity func_158_i() {
        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 16D);
        if (entityplayer != null && func_145_g(entityplayer)) {
            return entityplayer;
        } else {
            return null;
        }
    }

    public boolean attackEntity(Entity entity, int i) {
        if (super.attackEntity(entity, i)) {
            if (riddenByEntity == entity || ridingEntity == entity) {
                return true;
            }
            if (entity != this) {
                field_389_ag = entity;
            }
            return true;
        } else {
            return false;
        }
    }

    protected void func_157_a(Entity entity, float f) {
        if ((double) f < 2.5D && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY) {
            field_9103_aW = 20;
            entity.attackEntity(this, field_404_af);
        }
    }

    protected float func_159_a(int i, int j, int k) {
        return 0.5F - worldObj.getLightBrightness(i, j, k);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
    }

    public boolean getCanSpawnHere() {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);
        if (worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) > random.nextInt(32)) {
            return false;
        } else {
            int l = worldObj.getBlockLightValue(i, j, k);
            return l <= random.nextInt(8) && super.getCanSpawnHere();
        }
    }
}
