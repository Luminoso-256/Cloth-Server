package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntitySkeleton extends EntityMobs
{

    public EntitySkeleton(World world)
    {
        super(world);
        field_9119_aG = "/mob/skeleton.png";
    }

    protected String getLivingSound()
    {
        return "mob.skeleton";
    }

    protected String getHurtSound()
    {
        return "mob.skeletonhurt";
    }

    protected String getDeathSound()
    {
        return "mob.skeletonhurt";
    }

    public void onLivingUpdate()
    {
        if(worldObj.isDaytime())
        {
            float f = getEntityBrightness(1.0F);
            if(f > 0.5F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && random.nextFloat() * 30F < (f - 0.4F) * 2.0F)
            {
                field_9061_Z = 300;
            }
        }
        super.onLivingUpdate();
    }

    protected void func_157_a(Entity entity, float f)
    {
        if(f < 10F)
        {
            double d = entity.posX - posX;
            double d1 = entity.posZ - posZ;
            if(field_9103_aW == 0)
            {
                EntityArrow entityarrow = new EntityArrow(worldObj, this);
                entityarrow.posY += 1.3999999761581421D;
                double d2 = entity.posY - 0.20000000298023224D - entityarrow.posY;
                float f1 = MathHelper.sqrt_double(d * d + d1 * d1) * 0.2F;
                worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
                worldObj.entityJoinedWorld(entityarrow);
                entityarrow.func_177_a(d, d2 + (double)f1, d1, 0.6F, 12F);
                field_9103_aW = 30;
            }
            rotationYaw = (float)((Math.atan2(d1, d) * 180D) / 3.1415927410125732D) - 90F;
            field_387_ah = true;
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
    }

    protected int getDropItemId()
    {
        return Item.arrow.swiftedIndex;
    }

    private static final ItemStack field_4108_a;

    static 
    {
        field_4108_a = new ItemStack(Item.bow, 1);
    }
}
