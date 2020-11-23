package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntitySpider extends EntityMobs
{

    public EntitySpider(World world)
    {
        super(world);
        field_9119_aG = "/mob/spider.png";
        setSize(1.4F, 0.9F);
        field_9126_bt = 0.8F;
    }

    public double func_130_h()
    {
        return (double)height * 0.75D - 0.5D;
    }

    protected Entity func_158_i()
    {
        float f = getEntityBrightness(1.0F);
        if(f < 0.5F)
        {
            double d = 16D;
            return worldObj.getClosestPlayerToEntity(this, d);
        } else
        {
            return null;
        }
    }

    protected String getLivingSound()
    {
        return "mob.spider";
    }

    protected String getHurtSound()
    {
        return "mob.spider";
    }

    protected String getDeathSound()
    {
        return "mob.spiderdeath";
    }

    protected void func_157_a(Entity entity, float f)
    {
        float f1 = getEntityBrightness(1.0F);
        if(f1 > 0.5F && random.nextInt(100) == 0)
        {
            field_389_ag = null;
            return;
        }
        if(f > 2.0F && f < 6F && random.nextInt(10) == 0)
        {
            if(onGround)
            {
                double d = entity.posX - posX;
                double d1 = entity.posZ - posZ;
                float f2 = MathHelper.sqrt_double(d * d + d1 * d1);
                motionX = (d / (double)f2) * 0.5D * 0.80000001192092896D + motionX * 0.20000000298023224D;
                motionZ = (d1 / (double)f2) * 0.5D * 0.80000001192092896D + motionZ * 0.20000000298023224D;
                motionY = 0.40000000596046448D;
            }
        } else
        {
            super.func_157_a(entity, f);
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
        return Item.silk.swiftedIndex;
    }
}
