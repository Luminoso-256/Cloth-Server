package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class EntityFish extends Entity
{

    public EntityFish(World world)
    {
        super(world);
        field_4130_d = -1;
        field_4128_e = -1;
        field_4126_f = -1;
        field_4132_ai = 0;
        field_4129_aj = false;
        field_4134_a = 0;
        field_4125_al = 0;
        field_4124_am = 0;
        field_4131_c = null;
        setSize(0.25F, 0.25F);
    }

    public EntityFish(World world, EntityPlayer entityplayer)
    {
        super(world);
        field_4130_d = -1;
        field_4128_e = -1;
        field_4126_f = -1;
        field_4132_ai = 0;
        field_4129_aj = false;
        field_4134_a = 0;
        field_4125_al = 0;
        field_4124_am = 0;
        field_4131_c = null;
        field_6151_b = entityplayer;
        field_6151_b.field_6124_at = this;
        setSize(0.25F, 0.25F);
        func_107_c(entityplayer.posX, (entityplayer.posY + 1.6200000000000001D) - (double)entityplayer.yOffset, entityplayer.posZ, entityplayer.rotationYaw, entityplayer.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        float f = 0.4F;
        motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f;
        func_6142_a(motionX, motionY, motionZ, 1.5F, 1.0F);
    }

    public void func_6142_a(double d, double d1, double d2, float f, 
            float f1)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d1 += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d2 += random.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
        field_6150_ak = 0;
    }

    public void onUpdate()
    {
        super.onUpdate();
        if(field_6149_an > 0)
        {
            double d = posX + (field_6148_ao - posX) / (double)field_6149_an;
            double d1 = posY + (field_6147_ap - posY) / (double)field_6149_an;
            double d2 = posZ + (field_6146_aq - posZ) / (double)field_6149_an;
            double d4;
            for(d4 = field_6145_ar - (double)rotationYaw; d4 < -180D; d4 += 360D) { }
            for(; d4 >= 180D; d4 -= 360D) { }
            rotationYaw += d4 / (double)field_6149_an;
            rotationPitch += (field_6144_as - (double)rotationPitch) / (double)field_6149_an;
            field_6149_an--;
            setPosition(d, d1, d2);
            setRotation(rotationYaw, rotationPitch);
            return;
        }
        if(!worldObj.multiplayerWorld)
        {
            ItemStack itemstack = field_6151_b.func_172_B();
            if(field_6151_b.isDead || !field_6151_b.func_120_t() || itemstack == null || itemstack.getItem() != Item.fishingRod || getDistanceSqToEntity(field_6151_b) > 1024D)
            {
                setEntityDead();
                field_6151_b.field_6124_at = null;
                return;
            }
            if(field_4131_c != null)
            {
                if(field_4131_c.isDead)
                {
                    field_4131_c = null;
                } else
                {
                    posX = field_4131_c.posX;
                    posY = field_4131_c.boundingBox.minY + (double)field_4131_c.height * 0.80000000000000004D;
                    posZ = field_4131_c.posZ;
                    return;
                }
            }
        }
        if(field_4134_a > 0)
        {
            field_4134_a--;
        }
        if(field_4129_aj)
        {
            int i = worldObj.getBlockId(field_4130_d, field_4128_e, field_4126_f);
            if(i != field_4132_ai)
            {
                field_4129_aj = false;
                motionX *= random.nextFloat() * 0.2F;
                motionY *= random.nextFloat() * 0.2F;
                motionZ *= random.nextFloat() * 0.2F;
                field_6150_ak = 0;
                field_4125_al = 0;
            } else
            {
                field_6150_ak++;
                if(field_6150_ak == 1200)
                {
                    setEntityDead();
                }
                return;
            }
        } else
        {
            field_4125_al++;
        }
        Vec3D vec3d = Vec3D.createVector(posX, posY, posZ);
        Vec3D vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition movingobjectposition = worldObj.func_486_a(vec3d, vec3d1);
        vec3d = Vec3D.createVector(posX, posY, posZ);
        vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        if(movingobjectposition != null)
        {
            vec3d1 = Vec3D.createVector(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }
        Entity entity = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expands(1.0D, 1.0D, 1.0D));
        double d3 = 0.0D;
        for(int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);
            if(!entity1.func_129_c_() || entity1 == field_6151_b && field_4125_al < 5)
            {
                continue;
            }
            float f2 = 0.3F;
            AxisAlignedBB axisalignedbb = entity1.boundingBox.expands(f2, f2, f2);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.func_706_a(vec3d, vec3d1);
            if(movingobjectposition1 == null)
            {
                continue;
            }
            double d6 = vec3d.distanceTo(movingobjectposition1.hitVec);
            if(d6 < d3 || d3 == 0.0D)
            {
                entity = entity1;
                d3 = d6;
            }
        }

        if(entity != null)
        {
            movingobjectposition = new MovingObjectPosition(entity);
        }
        if(movingobjectposition != null)
        {
            if(movingobjectposition.entityHit != null)
            {
                if(movingobjectposition.entityHit.attackEntity(field_6151_b, 0))
                {
                    field_4131_c = movingobjectposition.entityHit;
                }
            } else
            {
                field_4129_aj = true;
            }
        }
        if(field_4129_aj)
        {
            return;
        }
        moveEntity(motionX, motionY, motionZ);
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
        for(rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f1 = 0.92F;
        if(onGround || field_9084_B)
        {
            f1 = 0.5F;
        }
        int k = 5;
        double d5 = 0.0D;
        for(int l = 0; l < k; l++)
        {
            double d8 = ((boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(l + 0)) / (double)k) - 0.125D) + 0.125D;
            double d9 = ((boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(l + 1)) / (double)k) - 0.125D) + 0.125D;
            AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBoxFromPool(boundingBox.minX, d8, boundingBox.minZ, boundingBox.maxX, d9, boundingBox.maxZ);
            if(worldObj.func_524_b(axisalignedbb1, Material.water))
            {
                d5 += 1.0D / (double)k;
            }
        }

        if(d5 > 0.0D)
        {
            if(field_4124_am > 0)
            {
                field_4124_am--;
            } else
            if(random.nextInt(500) == 0)
            {
                field_4124_am = random.nextInt(30) + 10;
                motionY -= 0.20000000298023224D;
                worldObj.playSoundAtEntity(this, "random.splash", 0.25F, 1.0F + (random.nextFloat() - random.nextFloat()) * 0.4F);
                float f3 = MathHelper.floor_double(boundingBox.minY);
                for(int i1 = 0; (float)i1 < 1.0F + width * 20F; i1++)
                {
                    float f4 = (random.nextFloat() * 2.0F - 1.0F) * width;
                    float f6 = (random.nextFloat() * 2.0F - 1.0F) * width;
                    worldObj.spawnParticle("bubble", posX + (double)f4, f3 + 1.0F, posZ + (double)f6, motionX, motionY - (double)(random.nextFloat() * 0.2F), motionZ);
                }

                for(int j1 = 0; (float)j1 < 1.0F + width * 20F; j1++)
                {
                    float f5 = (random.nextFloat() * 2.0F - 1.0F) * width;
                    float f7 = (random.nextFloat() * 2.0F - 1.0F) * width;
                    worldObj.spawnParticle("splash", posX + (double)f5, f3 + 1.0F, posZ + (double)f7, motionX, motionY, motionZ);
                }

            }
        }
        if(field_4124_am > 0)
        {
            motionY -= (double)(random.nextFloat() * random.nextFloat() * random.nextFloat()) * 0.20000000000000001D;
        }
        double d7 = d5 * 2D - 1.0D;
        motionY += 0.039999999105930328D * d7;
        if(d5 > 0.0D)
        {
            f1 = (float)((double)f1 * 0.90000000000000002D);
            motionY *= 0.80000000000000004D;
        }
        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;
        setPosition(posX, posY, posZ);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)field_4130_d);
        nbttagcompound.setShort("yTile", (short)field_4128_e);
        nbttagcompound.setShort("zTile", (short)field_4126_f);
        nbttagcompound.setByte("inTile", (byte)field_4132_ai);
        nbttagcompound.setByte("shake", (byte)field_4134_a);
        nbttagcompound.setByte("inGround", (byte)(field_4129_aj ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        field_4130_d = nbttagcompound.getShort("xTile");
        field_4128_e = nbttagcompound.getShort("yTile");
        field_4126_f = nbttagcompound.getShort("zTile");
        field_4132_ai = nbttagcompound.getByte("inTile") & 0xff;
        field_4134_a = nbttagcompound.getByte("shake") & 0xff;
        field_4129_aj = nbttagcompound.getByte("inGround") == 1;
    }

    public int func_6143_c()
    {
        byte byte0 = 0;
        if(field_4131_c != null)
        {
            double d = field_6151_b.posX - posX;
            double d2 = field_6151_b.posY - posY;
            double d4 = field_6151_b.posZ - posZ;
            double d6 = MathHelper.sqrt_double(d * d + d2 * d2 + d4 * d4);
            double d8 = 0.10000000000000001D;
            field_4131_c.motionX += d * d8;
            field_4131_c.motionY += d2 * d8 + (double)MathHelper.sqrt_double(d6) * 0.080000000000000002D;
            field_4131_c.motionZ += d4 * d8;
            byte0 = 3;
        } else
        if(field_4124_am > 0)
        {
            EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.fishRaw.swiftedIndex));
            double d1 = field_6151_b.posX - posX;
            double d3 = field_6151_b.posY - posY;
            double d5 = field_6151_b.posZ - posZ;
            double d7 = MathHelper.sqrt_double(d1 * d1 + d3 * d3 + d5 * d5);
            double d9 = 0.10000000000000001D;
            entityitem.motionX = d1 * d9;
            entityitem.motionY = d3 * d9 + (double)MathHelper.sqrt_double(d7) * 0.080000000000000002D;
            entityitem.motionZ = d5 * d9;
            worldObj.entityJoinedWorld(entityitem);
            byte0 = 1;
        }
        if(field_4129_aj)
        {
            byte0 = 2;
        }
        setEntityDead();
        field_6151_b.field_6124_at = null;
        return byte0;
    }

    private int field_4130_d;
    private int field_4128_e;
    private int field_4126_f;
    private int field_4132_ai;
    private boolean field_4129_aj;
    public int field_4134_a;
    public EntityPlayer field_6151_b;
    private int field_6150_ak;
    private int field_4125_al;
    private int field_4124_am;
    public Entity field_4131_c;
    private int field_6149_an;
    private double field_6148_ao;
    private double field_6147_ap;
    private double field_6146_aq;
    private double field_6145_ar;
    private double field_6144_as;
}
