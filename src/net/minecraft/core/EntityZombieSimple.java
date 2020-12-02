package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class EntityZombieSimple extends EntityMobs {
    //Why am I patching this?!

    public EntityZombieSimple(World world) {
        super(world);
        field_9119_aG = "/mob/zombie.png";
        field_9126_bt = 0.5F;
        field_404_af = 50;
        health *= 10;
        yOffset *= 6F;
        setSize(width * 6F, height * 6F);
    }

    protected float func_159_a(int i, int j, int k) {
        return worldObj.getLightBrightness(i, j, k) - 0.5F;
    }

    public void onLivingUpdate() {
        if (worldObj.isDaytime()) {
            float f = getEntityBrightness(1.0F);
            if (f > 0.5F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && random.nextFloat() * 30F < (f - 0.4F) * 2.0F) {
                field_9061_Z = 300;
            }
        }
        super.onLivingUpdate();
    }

    protected String getLivingSound() {
        return "mob.zombie";
    }

    protected String getHurtSound() {
        return "mob.zombiehurt";
    }

    protected String getDeathSound() {
        return "mob.zombiedeath";
    }

    protected int getDropItemId() {
        return Item.feather.swiftedIndex;
    }
}
