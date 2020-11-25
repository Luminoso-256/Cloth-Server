package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityGhast extends EntityFlying
    implements IMobs
{

    public EntityGhast(World world)
    {
        super(world);
        field_4099_a = 0;
        field_4097_ai = null;
        field_4103_aj = 0;
        field_4101_e = 0;
        field_4100_f = 0;
        field_9119_aG = "/mob/ghast.png";
        setSize(4F, 4F);
        field_9079_ae = true;
    }

    protected void func_152_d_()
    {
        if(worldObj.monstersEnabled == 0)
        {
            setEntityDead();
        }
        field_4101_e = field_4100_f;
        double d = field_4098_b - posX;
        double d1 = field_4104_c - posY;
        double d2 = field_4102_d - posZ;
        double d3 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        if(d3 < 1.0D || d3 > 60D)
        {
            field_4098_b = posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16F);
            field_4104_c = posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16F);
            field_4102_d = posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16F);
        }
        if(field_4099_a-- <= 0)
        {
            field_4099_a += random.nextInt(5) + 2;
            if(func_4046_a(field_4098_b, field_4104_c, field_4102_d, d3))
            {
                motionX += (d / d3) * 0.10000000000000001D;
                motionY += (d1 / d3) * 0.10000000000000001D;
                motionZ += (d2 / d3) * 0.10000000000000001D;
            } else
            {
                field_4098_b = posX;
                field_4104_c = posY;
                field_4102_d = posZ;
            }
        }
        if(field_4097_ai != null && field_4097_ai.field_304_B)
        {
            field_4097_ai = null;
        }
        if(field_4097_ai == null || field_4103_aj-- <= 0)
        {
            field_4097_ai = worldObj.getClosestPlayerToEntity(this, 100D);
            if(field_4097_ai != null)
            {
                field_4103_aj = 20;
            }
        }
        double d4 = 64D;
        if(field_4097_ai != null && field_4097_ai.getDistanceSqToEntity(this) < d4 * d4)
        {
            double d5 = field_4097_ai.posX - posX;
            double d6 = (field_4097_ai.boundingBox.minY + (double)(field_4097_ai.height / 2.0F)) - (posY + (double)(height / 2.0F));
            double d7 = field_4097_ai.posZ - posZ;
            field_9095_az = rotationYaw = (-(float)Math.atan2(d5, d7) * 180F) / 3.141593F;
            if(func_145_g(field_4097_ai))
            {
                if(field_4100_f == 10)
                {
                    worldObj.playSoundAtEntity(this, "mob.ghast.charge", getSoundVolume(), (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                }
                field_4100_f++;
                if(field_4100_f == 20)
                {
                    worldObj.playSoundAtEntity(this, "mob.ghast.fireball", getSoundVolume(), (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                    EntityFireball entityfireball = new EntityFireball(worldObj, this, d5, d6, d7);
                    double d8 = 4D;
                    Vec3D vec3d = func_141_d(1.0F);
                    entityfireball.posX = posX + vec3d.xCoord * d8;
                    entityfireball.posY = posY + (double)(height / 2.0F) + 0.5D;
                    entityfireball.posZ = posZ + vec3d.zCoord * d8;
                    worldObj.entityJoinedWorld(entityfireball);
                    field_4100_f = -40;
                }
            } else
            if(field_4100_f > 0)
            {
                field_4100_f--;
            }
        } else
        {
            field_9095_az = rotationYaw = (-(float)Math.atan2(motionX, motionZ) * 180F) / 3.141593F;
            if(field_4100_f > 0)
            {
                field_4100_f--;
            }
        }
        field_9119_aG = field_4100_f <= 10 ? "/mob/ghast.png" : "/mob/ghast_fire.png";
    }

    private boolean func_4046_a(double d, double d1, double d2, double d3)
    {
        double d4 = (field_4098_b - posX) / d3;
        double d5 = (field_4104_c - posY) / d3;
        double d6 = (field_4102_d - posZ) / d3;
        AxisAlignedBB axisalignedbb = boundingBox.copy();
        for(int i = 1; (double)i < d3; i++)
        {
            axisalignedbb.offset(d4, d5, d6);
            if(worldObj.getCollidingBoundingBoxes(this, axisalignedbb).size() > 0)
            {
                return false;
            }
        }

        return true;
    }

    protected String getLivingSound()
    {
        return "mob.ghast.moan";
    }

    protected String getHurtSound()
    {
        return "mob.ghast.scream";
    }

    protected String getDeathSound()
    {
        return "mob.ghast.death";
    }

    protected int getDropItemId()
    {
        return Item.gunpowder.swiftedIndex;
    }

    protected float getSoundVolume()
    {
        return 10F;
    }

    public boolean getCanSpawnHere()
    {
        return random.nextInt(20) == 0 && super.getCanSpawnHere() && worldObj.monstersEnabled > 0;
    }

    public int func_4045_i()
    {
        return 1;
    }

    public int field_4099_a;
    public double field_4098_b;
    public double field_4104_c;
    public double field_4102_d;
    private Entity field_4097_ai;
    private int field_4103_aj;
    public int field_4101_e;
    public int field_4100_f;
}
