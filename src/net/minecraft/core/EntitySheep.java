package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntitySheep extends EntityAnimals {

    public boolean sheared;

    public EntitySheep(World world) {
        super(world);
        sheared = false;
        field_9119_aG = "/mob/sheep.png";
        setSize(0.9F, 1.3F);
    }

    public boolean attackEntity(Entity entity, int i) {
        if (!worldObj.multiplayerWorld && !sheared && (entity instanceof EntityLiving)) {
            sheared = true;
            int j = 1 + random.nextInt(3);
            for (int k = 0; k < j; k++) {
                EntityItem entityitem = dropItemWithOffset(Block.cloth.blockID, 1, 0, 1.0F);
                entityitem.motionY += random.nextFloat() * 0.05F;
                entityitem.motionX += (random.nextFloat() - random.nextFloat()) * 0.1F;
                entityitem.motionZ += (random.nextFloat() - random.nextFloat()) * 0.1F;
            }

        }
        return super.attackEntity(entity, i);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Sheared", sheared);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        sheared = nbttagcompound.getBoolean("Sheared");
    }

    protected String getLivingSound() {
        return "mob.sheep";
    }

    protected String getHurtSound() {
        return "mob.sheep";
    }

    protected String getDeathSound() {
        return "mob.sheep";
    }
}
