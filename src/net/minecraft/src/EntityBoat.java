package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class EntityBoat extends Entity
{

    public EntityBoat(World world)
    {
        super(world);
        field_9178_a = 0;
        field_9177_b = 0;
        field_436_ad = 1;
        field_329_e = true;
        setSize(1.5F, 0.6F);
        yOffset = height / 2.0F;
        entityWalks = false;
    }

    public AxisAlignedBB func_89_d(Entity entity)
    {
        return entity.boundingBox;
    }

    public AxisAlignedBB func_93_n()
    {
        return boundingBox;
    }

    public boolean func_124_r()
    {
        return true;
    }

    public EntityBoat(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1 + (double)yOffset, d2);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = d;
        prevPosY = d1;
        prevPosZ = d2;
    }

    public double func_130_h()
    {
        return (double)height * 0.0D - 0.30000001192092896D;
    }

    public boolean attackEntity(Entity entity, int i)
    {
        if(worldObj.multiplayerWorld || field_304_B)
        {
            return true;
        }
        field_436_ad = -field_436_ad;
        field_9177_b = 10;
        field_9178_a += i * 10;
        func_9060_u();
        if(field_9178_a > 40)
        {
            for(int j = 0; j < 3; j++)
            {
                dropItemWithOffset(Block.planks.blockID, 1, 0, 0.0F);
            }

            for(int k = 0; k < 2; k++)
            {
                dropItemWithOffset(Item.stick.swiftedIndex, 1,0,  0.0F);
            }

            setEntityDead();
        }
        return true;
    }

    public boolean func_129_c_()
    {
        return !field_304_B;
    }

    public void onUpdate()
    {
        super.onUpdate();
        if(field_9177_b > 0)
        {
            field_9177_b--;
        }
        if(field_9178_a > 0)
        {
            field_9178_a--;
        }
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        int i = 5;
        double d = 0.0D;
        for(int j = 0; j < i; j++)
        {
            double d4 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(j + 0)) / (double)i) - 0.125D;
            double d8 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(j + 1)) / (double)i) - 0.125D;
            AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBoxFromPool(boundingBox.minX, d4, boundingBox.minZ, boundingBox.maxX, d8, boundingBox.maxZ);
            if(worldObj.func_524_b(axisalignedbb, Material.water))
            {
                d += 1.0D / (double)i;
            }
        }

        if(worldObj.multiplayerWorld)
        {
            if(field_9176_d > 0)
            {
                double d1 = posX + (field_9174_e - posX) / (double)field_9176_d;
                double d5 = posY + (field_9172_f - posY) / (double)field_9176_d;
                double d9 = posZ + (field_9175_aj - posZ) / (double)field_9176_d;
                double d12;
                for(d12 = field_9173_ak - (double)rotationYaw; d12 < -180D; d12 += 360D) { }
                for(; d12 >= 180D; d12 -= 360D) { }
                rotationYaw += d12 / (double)field_9176_d;
                rotationPitch += (field_9171_al - (double)rotationPitch) / (double)field_9176_d;
                field_9176_d--;
                setPosition(d1, d5, d9);
                setRotation(rotationYaw, rotationPitch);
            } else
            {
                double d2 = posX + motionX;
                double d6 = posY + motionY;
                double d10 = posZ + motionZ;
                setPosition(d2, d6, d10);
                if(onGround)
                {
                    motionX *= 0.5D;
                    motionY *= 0.5D;
                    motionZ *= 0.5D;
                }
                motionX *= 0.99000000953674316D;
                motionY *= 0.94999998807907104D;
                motionZ *= 0.99000000953674316D;
            }
            return;
        }
        double d3 = d * 2D - 1.0D;
        motionY += 0.039999999105930328D * d3;
        if(field_328_f != null)
        {
            motionX += field_328_f.motionX * 0.20000000000000001D;
            motionZ += field_328_f.motionZ * 0.20000000000000001D;
        }
        double d7 = 0.40000000000000002D;
        if(motionX < -d7)
        {
            motionX = -d7;
        }
        if(motionX > d7)
        {
            motionX = d7;
        }
        if(motionZ < -d7)
        {
            motionZ = -d7;
        }
        if(motionZ > d7)
        {
            motionZ = d7;
        }
        if(onGround)
        {
            motionX *= 0.5D;
            motionY *= 0.5D;
            motionZ *= 0.5D;
        }
        moveEntity(motionX, motionY, motionZ);
        double d11 = Math.sqrt(motionX * motionX + motionZ * motionZ);
        if(d11 > 0.14999999999999999D)
        {
            double d13 = Math.cos(((double)rotationYaw * 3.1415926535897931D) / 180D);
            double d15 = Math.sin(((double)rotationYaw * 3.1415926535897931D) / 180D);
            for(int i1 = 0; (double)i1 < 1.0D + d11 * 60D; i1++)
            {
                double d18 = random.nextFloat() * 2.0F - 1.0F;
                double d20 = (double)(random.nextInt(2) * 2 - 1) * 0.69999999999999996D;
                if(random.nextBoolean())
                {
                    double d21 = (posX - d13 * d18 * 0.80000000000000004D) + d15 * d20;
                    double d23 = posZ - d15 * d18 * 0.80000000000000004D - d13 * d20;
                    worldObj.spawnParticle("splash", d21, posY - 0.125D, d23, motionX, motionY, motionZ);
                } else
                {
                    double d22 = posX + d13 + d15 * d18 * 0.69999999999999996D;
                    double d24 = (posZ + d15) - d13 * d18 * 0.69999999999999996D;
                    worldObj.spawnParticle("splash", d22, posY - 0.125D, d24, motionX, motionY, motionZ);
                }
            }

        }
        if(field_9084_B && d11 > 0.14999999999999999D)
        {
            if(!worldObj.multiplayerWorld)
            {
                setEntityDead();
                for(int k = 0; k < 3; k++)
                {
                    dropItemWithOffset(Block.planks.blockID, 1, 0, 0.0F);
                }

                for(int l = 0; l < 2; l++)
                {
                    dropItemWithOffset(Item.stick.swiftedIndex, 1, 0,0.0F);
                }

            }
        } else
        {
            motionX *= 0.99000000953674316D;
            motionY *= 0.94999998807907104D;
            motionZ *= 0.99000000953674316D;
        }
        rotationPitch = 0.0F;
        double d14 = rotationYaw;
        double d16 = prevPosX - posX;
        double d17 = prevPosZ - posZ;
        if(d16 * d16 + d17 * d17 > 0.001D)
        {
            d14 = (float)((Math.atan2(d17, d16) * 180D) / 3.1415926535897931D);
        }
        double d19;
        for(d19 = d14 - (double)rotationYaw; d19 >= 180D; d19 -= 360D) { }
        for(; d19 < -180D; d19 += 360D) { }
        if(d19 > 20D)
        {
            d19 = 20D;
        }
        if(d19 < -20D)
        {
            d19 = -20D;
        }
        rotationYaw += d19;
        setRotation(rotationYaw, rotationPitch);
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expands(0.20000000298023224D, 0.0D, 0.20000000298023224D));
        if(list != null && list.size() > 0)
        {
            for(int j1 = 0; j1 < list.size(); j1++)
            {
                Entity entity = (Entity)list.get(j1);
                if(entity != field_328_f && entity.func_124_r() && (entity instanceof EntityBoat))
                {
                    entity.applyEntityCollision(this);
                }
            }

        }
        if(field_328_f != null && field_328_f.field_304_B)
        {
            field_328_f = null;
        }
    }

    public void func_127_w()
    {
        if(field_328_f == null)
        {
            return;
        } else
        {
            double d = Math.cos(((double)rotationYaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D;
            double d1 = Math.sin(((double)rotationYaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D;
            field_328_f.setPosition(posX + d, posY + func_130_h() + field_328_f.func_117_x(), posZ + d1);
            return;
        }
    }

    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    }

    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    }

    public boolean func_6092_a(EntityPlayer entityplayer)
    {
        if(field_328_f != null && (field_328_f instanceof EntityPlayer) && field_328_f != entityplayer)
        {
            return true;
        }
        if(!worldObj.multiplayerWorld)
        {
            entityplayer.func_6094_e(this);
        }
        return true;
    }

    public int field_9178_a;
    public int field_9177_b;
    public int field_436_ad;
    private int field_9176_d;
    private double field_9174_e;
    private double field_9172_f;
    private double field_9175_aj;
    private double field_9173_ak;
    private double field_9171_al;
}
