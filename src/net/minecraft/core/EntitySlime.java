package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntitySlime extends EntityLiving
        implements IMobs {

    public float field_401_a;
    public float field_400_b;
    public int field_403_ad;
    private int field_402_ae;

    public EntitySlime(World world) {
        super(world);
        field_402_ae = 0;
        field_403_ad = 1;
        field_9119_aG = "/mob/slime.png";
        field_403_ad = 1 << random.nextInt(3);
        yOffset = 0.0F;
        field_402_ae = random.nextInt(20) + 10;
        func_160_c(field_403_ad);
    }

    public void func_160_c(int i) {
        field_403_ad = i;
        setSize(0.6F * (float) i, 0.6F * (float) i);
        health = i * i;
        setPosition(posX, posY, posZ);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("Size", field_403_ad - 1);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        field_403_ad = nbttagcompound.getInteger("Size") + 1;
    }

    public void onUpdate() {
        field_400_b = field_401_a;
        boolean flag = onGround;
        super.onUpdate();
        if (onGround && !flag) {
            for (int i = 0; i < field_403_ad * 8; i++) {
                float f = random.nextFloat() * 3.141593F * 2.0F;
                float f1 = random.nextFloat() * 0.5F + 0.5F;
                float f2 = MathHelper.sin(f) * (float) field_403_ad * 0.5F * f1;
                float f3 = MathHelper.cos(f) * (float) field_403_ad * 0.5F * f1;
                worldObj.spawnParticle("slime", posX + (double) f2, boundingBox.minY, posZ + (double) f3, 0.0D, 0.0D, 0.0D);
            }

            if (field_403_ad > 2) {
                worldObj.playSoundAtEntity(this, "mob.slime", getSoundVolume(), ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            }
            field_401_a = -0.5F;
        }
        field_401_a = field_401_a * 0.6F;
    }

    protected void func_152_d_() {
        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 16D);
        if (entityplayer != null) {
            func_147_b(entityplayer, 10F);
        }
        if (onGround && field_402_ae-- <= 0) {
            field_402_ae = random.nextInt(20) + 10;
            if (entityplayer != null) {
                field_402_ae /= 3;
            }
            field_9128_br = true;
            if (field_403_ad > 1) {
                worldObj.playSoundAtEntity(this, "mob.slime", getSoundVolume(), ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
            }
            field_401_a = 1.0F;
            field_9131_bo = 1.0F - random.nextFloat() * 2.0F;
            field_9130_bp = 1 * field_403_ad;
        } else {
            field_9128_br = false;
            if (onGround) {
                field_9131_bo = field_9130_bp = 0.0F;
            }
        }
    }

    public void setEntityDead() {
        if (field_403_ad > 1 && health == 0) {
            for (int i = 0; i < 4; i++) {
                float f = (((float) (i % 2) - 0.5F) * (float) field_403_ad) / 4F;
                float f1 = (((float) (i / 2) - 0.5F) * (float) field_403_ad) / 4F;
                EntitySlime entityslime = new EntitySlime(worldObj);
                entityslime.func_160_c(field_403_ad / 2);
                entityslime.func_107_c(posX + (double) f, posY + 0.5D, posZ + (double) f1, random.nextFloat() * 360F, 0.0F);
                worldObj.entityJoinedWorld(entityslime);
            }

        }
        super.setEntityDead();
    }

    public void onCollideWithPlayer(EntityPlayer entityplayer) {
        if (field_403_ad > 1 && func_145_g(entityplayer) && (double) getDistanceToEntity(entityplayer) < 0.59999999999999998D * (double) field_403_ad && entityplayer.attackEntity(this, field_403_ad)) {
            worldObj.playSoundAtEntity(this, "mob.slimeattack", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }
    }

    protected String getHurtSound() {
        return "mob.slime";
    }

    protected String getDeathSound() {
        return "mob.slime";
    }

    protected int getDropItemId() {
        if (field_403_ad == 1) {
            return Item.slimeBall.swiftedIndex;
        } else {
            return 0;
        }
    }

    public boolean getCanSpawnHere() {
        Chunk chunk = worldObj.getChunkFromBlockCoords(MathHelper.floor_double(posX), MathHelper.floor_double(posZ));
        return (field_403_ad == 1 || worldObj.monstersEnabled > 0) && random.nextInt(10) == 0 && chunk.func_334_a(0x3ad8025fL).nextInt(10) == 0 && posY < 16D;
    }

    protected float getSoundVolume() {
        return 0.6F;
    }
}
