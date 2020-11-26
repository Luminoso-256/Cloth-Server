package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class EntityLiving extends Entity
{

    public EntityLiving(World world)
    {
        super(world);
        field_9099_av = 20;
        field_9095_az = 0.0F;
        field_9125_aA = 0.0F;
        field_9120_aF = true;
        field_9119_aG = "/mob/char.png";
        field_9118_aH = true;
        field_9117_aI = 0.0F;
        field_9116_aJ = null;
        field_9115_aK = 1.0F;
        field_9114_aL = 0;
        field_9113_aM = 0.0F;
        field_9112_aN = false;
        field_9105_aU = 0.0F;
        field_9104_aV = 0;
        field_9103_aW = 0;
        field_9100_aZ = false;
        field_9144_ba = -1;
        field_9143_bb = (float)(Math.random() * 0.89999997615814209D + 0.10000000149011612D);
        field_9134_bl = 0.0F;
        field_9133_bm = 0;
        field_9132_bn = 0;
        field_9128_br = false;
        field_9127_bs = 0.0F;
        field_9126_bt = 0.7F;
        field_4104_c = 0;
        health = 10;
        field_329_e = true;
        field_9096_ay = (float)(Math.random() + 1.0D) * 0.01F;
        setPosition(posX, posY, posZ);
        field_9098_aw = (float)Math.random() * 12398F;
        rotationYaw = (float)(Math.random() * 3.1415927410125732D * 2D);
        field_9097_ax = 1.0F;
        field_9067_S = 0.5F;
    }

    public boolean func_145_g(Entity entity)
    {
        return worldObj.func_486_a(Vec3D.createVector(posX, posY + (double)func_104_p(), posZ), Vec3D.createVector(entity.posX, entity.posY + (double)entity.func_104_p(), entity.posZ)) == null;
    }

    public boolean func_129_c_()
    {
        return !field_304_B;
    }

    public boolean func_124_r()
    {
        return !field_304_B;
    }

    public float func_104_p()
    {
        return height * 0.85F;
    }

    public int func_146_b()
    {
        return 80;
    }

    public void func_84_k()
    {
        field_9111_aO = field_9110_aP;
        super.func_84_k();
        if(random.nextInt(1000) < field_4099_a++)
        {
            field_4099_a = -func_146_b();
            String s = getLivingSound();
            if(s != null)
            {
                worldObj.playSoundAtEntity(this, s, getSoundVolume(), (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
            }
        }
        if(func_120_t() && func_91_u())
        {
            attackEntity(null, 1);
        }
        if(field_9079_ae || worldObj.multiplayerWorld)
        {
            field_9061_Z = 0;
        }
        if(func_120_t() && isInsideOfMaterial(Material.water))
        {
            air--;
            if(air == -20)
            {
                air = 0;
                for(int i = 0; i < 8; i++)
                {
                    float f = random.nextFloat() - random.nextFloat();
                    float f1 = random.nextFloat() - random.nextFloat();
                    float f2 = random.nextFloat() - random.nextFloat();
                    worldObj.spawnParticle("bubble", posX + (double)f, posY + (double)f1, posZ + (double)f2, motionX, motionY, motionZ);
                }
                //Your drowning! oh no!
                damageSources.add("drown");
                System.out.println("DROWN_DAMAGE_SOURCE");
                attackEntity(null, 2);
            }
            field_9061_Z = 0;
        } else
        {
            air = field_9087_aa;
        }
        field_9102_aX = field_9101_aY;
        if(field_9103_aW > 0)
        {
            field_9103_aW--;
        }
        if(field_9107_aS > 0)
        {
            field_9107_aS--;
        }
        if(field_9083_ac > 0)
        {
            field_9083_ac--;
        }
        if(health <= 0)
        {
            field_9104_aV++;
            if(field_9104_aV > 20)
            {
                func_6101_K();
                setEntityDead();
                for(int j = 0; j < 20; j++)
                {
                    double d = random.nextGaussian() * 0.02D;
                    double d1 = random.nextGaussian() * 0.02D;
                    double d2 = random.nextGaussian() * 0.02D;
                    worldObj.spawnParticle("explode", (posX + (double)(random.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(random.nextFloat() * height), (posZ + (double)(random.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
                }

            }
        }
        field_9121_aE = field_9122_aD;
        field_9125_aA = field_9095_az;
        prevRotationYaw = rotationYaw;
        prevRotationPitch = rotationPitch;
    }

    public void func_156_D()
    {
        for(int i = 0; i < 20; i++)
        {
            double d = random.nextGaussian() * 0.02D;
            double d1 = random.nextGaussian() * 0.02D;
            double d2 = random.nextGaussian() * 0.02D;
            double d3 = 10D;
            worldObj.spawnParticle("explode", (posX + (double)(random.nextFloat() * width * 2.0F)) - (double)width - d * d3, (posY + (double)(random.nextFloat() * height)) - d1 * d3, (posZ + (double)(random.nextFloat() * width * 2.0F)) - (double)width - d2 * d3, d, d1, d2);
        }

    }

    public void func_115_v()
    {
        super.func_115_v();
        field_9124_aB = field_9123_aC;
        field_9123_aC = 0.0F;
    }

    public void onUpdate()
    {
        super.onUpdate();
        onLivingUpdate();
        double d = posX - prevPosX;
        double d1 = posZ - prevPosZ;
        float f = MathHelper.sqrt_double(d * d + d1 * d1);
        float f1 = field_9095_az;
        float f2 = 0.0F;
        field_9124_aB = field_9123_aC;
        float f3 = 0.0F;
        if(f > 0.05F)
        {
            f3 = 1.0F;
            f2 = f * 3F;
            f1 = ((float)Math.atan2(d1, d) * 180F) / 3.141593F - 90F;
        }
        if(field_9110_aP > 0.0F)
        {
            f1 = rotationYaw;
        }
        if(!onGround)
        {
            f3 = 0.0F;
        }
        field_9123_aC = field_9123_aC + (f3 - field_9123_aC) * 0.3F;
        float f4;
        for(f4 = f1 - field_9095_az; f4 < -180F; f4 += 360F) { }
        for(; f4 >= 180F; f4 -= 360F) { }
        field_9095_az += f4 * 0.3F;
        float f5;
        for(f5 = rotationYaw - field_9095_az; f5 < -180F; f5 += 360F) { }
        for(; f5 >= 180F; f5 -= 360F) { }
        boolean flag = f5 < -90F || f5 >= 90F;
        if(f5 < -75F)
        {
            f5 = -75F;
        }
        if(f5 >= 75F)
        {
            f5 = 75F;
        }
        field_9095_az = rotationYaw - f5;
        if(f5 * f5 > 2500F)
        {
            field_9095_az += f5 * 0.2F;
        }
        if(flag)
        {
            f2 *= -1F;
        }
        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
        for(; field_9095_az - field_9125_aA < -180F; field_9125_aA -= 360F) { }
        for(; field_9095_az - field_9125_aA >= 180F; field_9125_aA += 360F) { }
        for(; rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
        field_9122_aD += f2;
    }

    protected void setSize(float f, float f1)
    {
        super.setSize(f, f1);
    }

    public void heal(int i)
    {
        if(health <= 0)
        {
            return;
        }
        health += i;
        if(health > 20)
        {
            health = 20;
        }
        field_9083_ac = field_9099_av / 2;
    }

    public boolean attackEntity(Entity entity, int i)
    {
        if(worldObj.multiplayerWorld)
        {
            return false;
        }
        field_9132_bn = 0;
        if(health <= 0)
        {
            return false;
        }
        field_9141_bd = 1.5F;
        boolean flag = true;
        if((float)field_9083_ac > (float)field_9099_av / 2.0F)
        {
            if(i <= field_9133_bm)
            {
                return false;
            }
            takeDamage(i - field_9133_bm);
            field_9133_bm = i;
            flag = false;
        } else
        {
            field_9133_bm = i;
            field_9108_aR = health;
            field_9083_ac = field_9099_av;
            takeDamage(i);
            field_9107_aS = field_9106_aT = 10;
        }
        field_9105_aU = 0.0F;
        if(flag)
        {
            worldObj.func_9206_a(this, (byte)2);
            func_9060_u();
            if(entity != null)
            {
                double d = entity.posX - posX;
                double d1;
                for(d1 = entity.posZ - posZ; d * d + d1 * d1 < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D)
                {
                    d = (Math.random() - Math.random()) * 0.01D;
                }

                field_9105_aU = (float)((Math.atan2(d1, d) * 180D) / 3.1415927410125732D) - rotationYaw;
                func_143_a(entity, i, d, d1);
            } else
            {
                field_9105_aU = (int)(Math.random() * 2D) * 180;
            }
        }
        if(health <= 0)
        {
            if(flag)
            {
                worldObj.playSoundAtEntity(this, getDeathSound(), getSoundVolume(), (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
            }
            onDeath(entity);
        } else
        if(flag)
        {
            worldObj.playSoundAtEntity(this, getHurtSound(), getSoundVolume(), (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        }
        return true;
    }

    protected void takeDamage(int i)
    {
    	health = Math.max(health -= i, 0);
    }

    protected float getSoundVolume()
    {
        return 1.0F;
    }

    protected String getLivingSound()
    {
        return null;
    }

    protected String getHurtSound()
    {
        return "random.hurt";
    }

    protected String getDeathSound()
    {
        return "random.hurt";
    }

    public void func_143_a(Entity entity, int i, double d, double d1)
    {
        float f = MathHelper.sqrt_double(d * d + d1 * d1);
        float f1 = 0.4F;
        motionX /= 2D;
        motionY /= 2D;
        motionZ /= 2D;
        motionX -= (d / (double)f) * (double)f1;
        motionY += 0.40000000596046448D;
        motionZ -= (d1 / (double)f) * (double)f1;
        if(motionY > 0.40000000596046448D)
        {
            motionY = 0.40000000596046448D;
        }
    }

    public void onDeath(Entity entity)
    {
        if(field_9114_aL > 0 && entity != null)
        {
            entity.func_96_b(this, field_9114_aL);
        }
        field_9100_aZ = true;
        if(!worldObj.multiplayerWorld)
        {
            int i = getDropItemId();
            if(i > 0)
            {
                int j = random.nextInt(3);
                for(int k = 0; k < j; k++)
                {
                    dropItem(i, 1, 0);
                }

            }
        }
        worldObj.func_9206_a(this, (byte)3);
    }

    protected int getDropItemId()
    {
        return 0;
    }

    protected void fall(float f)
    {
        int i = (int)Math.ceil(f - 3F);
        if(i > 0)
        {
            damageSources.add("fall");
            attackEntity(null, i);
            int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(posY - 0.20000000298023224D - (double)yOffset), MathHelper.floor_double(posZ));
            if(j > 0)
            {
                StepSound stepsound = Block.blocksList[j].stepSound;
                worldObj.playSoundAtEntity(this, stepsound.func_737_c(), stepsound.func_738_a() * 0.5F, stepsound.func_739_b() * 0.75F);
            }
        }
    }

    public void func_148_c(float f, float f1)
    {
        if(handleWaterMovement())
        {
            double d = posY;
            func_90_a(f, f1, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.80000001192092896D;
            motionY *= 0.80000001192092896D;
            motionZ *= 0.80000001192092896D;
            motionY -= 0.02D;
            if(field_9084_B && func_133_b(motionX, ((motionY + 0.60000002384185791D) - posY) + d, motionZ))
            {
                motionY = 0.30000001192092896D;
            }
        } else
        if(func_112_q())
        {
            double d1 = posY;
            func_90_a(f, f1, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.5D;
            motionY *= 0.5D;
            motionZ *= 0.5D;
            motionY -= 0.02D;
            if(field_9084_B && func_133_b(motionX, ((motionY + 0.60000002384185791D) - posY) + d1, motionZ))
            {
                motionY = 0.30000001192092896D;
            }
        } else
        {
            float f2 = 0.91F;
            if(onGround)
            {
                f2 = 0.5460001F;
                int i = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                if(i > 0)
                {
                    f2 = Block.blocksList[i].slipperiness * 0.91F;
                }
            }
            float f3 = 0.1627714F / (f2 * f2 * f2);
            func_90_a(f, f1, onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;
            if(onGround)
            {
                f2 = 0.5460001F;
                int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                if(j > 0)
                {
                    f2 = Block.blocksList[j].slipperiness * 0.91F;
                }
            }
            if(func_144_E())
            {
                fallDistance = 0.0F;
                if(motionY < -0.14999999999999999D)
                {
                    motionY = -0.14999999999999999D;
                }
            }
            moveEntity(motionX, motionY, motionZ);
            if(field_9084_B && func_144_E())
            {
                motionY = 0.20000000000000001D;
            }
            motionY -= 0.080000000000000002D;
            motionY *= 0.98000001907348633D;
            motionX *= f2;
            motionZ *= f2;
        }
        field_9142_bc = field_9141_bd;
        double d2 = posX - prevPosX;
        double d3 = posZ - prevPosZ;
        float f4 = MathHelper.sqrt_double(d2 * d2 + d3 * d3) * 4F;
        if(f4 > 1.0F)
        {
            f4 = 1.0F;
        }
        field_9141_bd += (f4 - field_9141_bd) * 0.4F;
        field_386_ba += field_9141_bd;
    }

    public boolean func_144_E()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);
        return worldObj.getBlockId(i, j, k) == Block.ladder.blockID || worldObj.getBlockId(i, j + 1, k) == Block.ladder.blockID;
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("Health", (short) health);
        nbttagcompound.setShort("HurtTime", (short)field_9107_aS);
        nbttagcompound.setShort("DeathTime", (short)field_9104_aV);
        nbttagcompound.setShort("AttackTime", (short)field_9103_aW);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        health = nbttagcompound.getShort("Health");
        if(!nbttagcompound.hasKey("Health"))
        {
            health = 10;
        }
        field_9107_aS = nbttagcompound.getShort("HurtTime");
        field_9104_aV = nbttagcompound.getShort("DeathTime");
        field_9103_aW = nbttagcompound.getShort("AttackTime");
    }

    public boolean func_120_t()
    {
        return !field_304_B && health > 0;
    }

    public void onLivingUpdate()
    {
        if(field_9140_bf > 0)
        {
            double d = posX + (field_9139_bg - posX) / (double)field_9140_bf;
            double d1 = posY + (field_9138_bh - posY) / (double)field_9140_bf;
            double d2 = posZ + (field_9137_bi - posZ) / (double)field_9140_bf;
            double d3;
            for(d3 = field_9136_bj - (double)rotationYaw; d3 < -180D; d3 += 360D) { }
            for(; d3 >= 180D; d3 -= 360D) { }
            rotationYaw += d3 / (double)field_9140_bf;
            rotationPitch += (field_9135_bk - (double)rotationPitch) / (double)field_9140_bf;
            field_9140_bf--;
            setPosition(d, d1, d2);
            setRotation(rotationYaw, rotationPitch);
        }
        if(health <= 0)
        {
            field_9128_br = false;
            field_9131_bo = 0.0F;
            field_9130_bp = 0.0F;
            field_9129_bq = 0.0F;
        } else
        if(!field_9112_aN)
        {
            func_152_d_();
        }
        boolean flag = handleWaterMovement();
        boolean flag1 = func_112_q();
        if(field_9128_br)
        {
            if(flag)
            {
                motionY += 0.039999999105930328D;
            } else
            if(flag1)
            {
                motionY += 0.039999999105930328D;
            } else
            if(onGround)
            {
                func_154_F();
            }
        }
        field_9131_bo *= 0.98F;
        field_9130_bp *= 0.98F;
        field_9129_bq *= 0.9F;
        func_148_c(field_9131_bo, field_9130_bp);
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expands(0.20000000298023224D, 0.0D, 0.20000000298023224D));
        if(list != null && list.size() > 0)
        {
            for(int i = 0; i < list.size(); i++)
            {
                Entity entity = (Entity)list.get(i);
                if(entity.func_124_r())
                {
                    entity.applyEntityCollision(this);
                }
            }

        }
    }

    protected void func_154_F()
    {
        motionY = 0.41999998688697815D;
    }

    protected void func_152_d_()
    {
        field_9132_bn++;
        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, -1D);
        if(entityplayer != null)
        {
            double d = ((Entity) (entityplayer)).posX - posX;
            double d1 = ((Entity) (entityplayer)).posY - posY;
            double d2 = ((Entity) (entityplayer)).posZ - posZ;
            double d3 = d * d + d1 * d1 + d2 * d2;
            if(d3 > 16384D)
            {
                setEntityDead();
            }
            if(field_9132_bn > 600 && random.nextInt(800) == 0)
            {
                if(d3 < 1024D)
                {
                    field_9132_bn = 0;
                } else
                {
                    setEntityDead();
                }
            }
        }
        field_9131_bo = 0.0F;
        field_9130_bp = 0.0F;
        float f = 8F;
        if(random.nextFloat() < 0.02F)
        {
            EntityPlayer entityplayer1 = worldObj.getClosestPlayerToEntity(this, f);
            if(entityplayer1 != null)
            {
                field_4098_b = entityplayer1;
                field_4104_c = 10 + random.nextInt(20);
            } else
            {
                field_9129_bq = (random.nextFloat() - 0.5F) * 20F;
            }
        }
        if(field_4098_b != null)
        {
            func_147_b(field_4098_b, 10F);
            if(field_4104_c-- <= 0 || field_4098_b.field_304_B || field_4098_b.getDistanceSqToEntity(this) > (double)(f * f))
            {
                field_4098_b = null;
            }
        } else
        {
            if(random.nextFloat() < 0.05F)
            {
                field_9129_bq = (random.nextFloat() - 0.5F) * 20F;
            }
            rotationYaw += field_9129_bq;
            rotationPitch = field_9127_bs;
        }
        boolean flag = handleWaterMovement();
        boolean flag1 = func_112_q();
        if(flag || flag1)
        {
            field_9128_br = random.nextFloat() < 0.8F;
        }
    }

    public void func_147_b(Entity entity, float f)
    {
        double d = entity.posX - posX;
        double d2 = entity.posZ - posZ;
        double d1;
        if(entity instanceof EntityLiving)
        {
            EntityLiving entityliving = (EntityLiving)entity;
            d1 = (entityliving.posY + (double)entityliving.func_104_p()) - (posY + (double)func_104_p());
        } else
        {
            d1 = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2D - (posY + (double)func_104_p());
        }
        double d3 = MathHelper.sqrt_double(d * d + d2 * d2);
        float f1 = (float)((Math.atan2(d2, d) * 180D) / 3.1415927410125732D) - 90F;
        float f2 = (float)((Math.atan2(d1, d3) * 180D) / 3.1415927410125732D);
        rotationPitch = -func_140_b(rotationPitch, f2, f);
        rotationYaw = func_140_b(rotationYaw, f1, f);
    }

    private float func_140_b(float f, float f1, float f2)
    {
        float f3;
        for(f3 = f1 - f; f3 < -180F; f3 += 360F) { }
        for(; f3 >= 180F; f3 -= 360F) { }
        if(f3 > f2)
        {
            f3 = f2;
        }
        if(f3 < -f2)
        {
            f3 = -f2;
        }
        return f + f3;
    }

    public void func_6101_K()
    {
    }

    public boolean getCanSpawnHere()
    {
        return worldObj.func_522_a(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && !worldObj.getIsAnyLiquid(boundingBox);
    }

    protected void func_4043_o()
    {
        attackEntity(null, 4);
    }

    public Vec3D func_4039_B()
    {
        return func_141_d(1.0F);
    }

    public Vec3D func_141_d(float f)
    {
        if(f == 1.0F)
        {
            float f1 = MathHelper.cos(-rotationYaw * 0.01745329F - 3.141593F);
            float f3 = MathHelper.sin(-rotationYaw * 0.01745329F - 3.141593F);
            float f5 = -MathHelper.cos(-rotationPitch * 0.01745329F);
            float f7 = MathHelper.sin(-rotationPitch * 0.01745329F);
            return Vec3D.createVector(f3 * f5, f7, f1 * f5);
        } else
        {
            float f2 = prevRotationPitch + (rotationPitch - prevRotationPitch) * f;
            float f4 = prevRotationYaw + (rotationYaw - prevRotationYaw) * f;
            float f6 = MathHelper.cos(-f4 * 0.01745329F - 3.141593F);
            float f8 = MathHelper.sin(-f4 * 0.01745329F - 3.141593F);
            float f9 = -MathHelper.cos(-f2 * 0.01745329F);
            float f10 = MathHelper.sin(-f2 * 0.01745329F);
            return Vec3D.createVector(f8 * f9, f10, f6 * f9);
        }
    }

    public int func_4045_i()
    {
        return 4;
    }

    public int field_9099_av;
    public float field_9098_aw;
    public float field_9097_ax;
    public float field_9096_ay;
    public float field_9095_az;
    public float field_9125_aA;
    protected float field_9124_aB;
    protected float field_9123_aC;
    protected float field_9122_aD;
    protected float field_9121_aE;
    protected boolean field_9120_aF;
    protected String field_9119_aG;
    protected boolean field_9118_aH;
    protected float field_9117_aI;
    protected String field_9116_aJ;
    protected float field_9115_aK;
    protected int field_9114_aL;
    protected float field_9113_aM;
    public boolean field_9112_aN;
    public float field_9111_aO;
    public float field_9110_aP;
    public int health;
    public int field_9108_aR;
    private int field_4099_a;
    public int field_9107_aS;
    public int field_9106_aT;
    public float field_9105_aU;
    public int field_9104_aV;
    public int field_9103_aW;
    public float field_9102_aX;
    public float field_9101_aY;
    protected boolean field_9100_aZ;
    public int field_9144_ba;
    public float field_9143_bb;
    public float field_9142_bc;
    public float field_9141_bd;
    public float field_386_ba;
    protected int field_9140_bf;
    protected double field_9139_bg;
    protected double field_9138_bh;
    protected double field_9137_bi;
    protected double field_9136_bj;
    protected double field_9135_bk;
    float field_9134_bl;
    protected int field_9133_bm;
    protected int field_9132_bn;
    protected float field_9131_bo;
    protected float field_9130_bp;
    protected float field_9129_bq;
    protected boolean field_9128_br;
    protected float field_9127_bs;
    protected float field_9126_bt;
    private Entity field_4098_b;
    private int field_4104_c;
}
