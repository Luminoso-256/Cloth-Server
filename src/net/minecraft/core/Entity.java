package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.cloth.ExploitUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Entity
{

    public Entity(World world)
    {
        IsPlayer =false;
        field_331_c = field_384_a++;
        field_9094_h = 1.0D;
        field_329_e = false;
        onGround = false;
        field_9080_D = false;
        field_9078_E = false;
        field_9077_F = true;
        isDead = false;
        yOffset = 0.0F;
        width = 0.6F;
        height = 1.8F;
        field_9075_K = 0.0F;
        field_9074_L = 0.0F;
        entityWalks = true;
        fallDistance = 0.0F;
        field_6151_b = 1;
        field_9068_R = 0.0F;
        field_9067_S = 0.0F;
        field_9066_T = false;
        field_286_P = 0.0F;
        field_9065_V = false;
        random = new Random();
        field_9063_X = 0;
        field_9062_Y = 1;
        field_9061_Z = 0;
        field_9087_aa = 300;
        field_9085_ab = false;
        field_9083_ac = 0;
        air = 300;
        field_4131_c = true;
        field_9079_ae = false;
        field_276_Z = false;

        worldObj = world;
        setPosition(0.0D, 0.0D, 0.0D);
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof Entity)
        {
            return ((Entity)obj).field_331_c == field_331_c;
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return field_331_c;
    }

    public void setEntityDead()
    {
        isDead = true;
    }

    protected void setSize(float f, float f1)
    {
        width = f;
        height = f1;
    }

    protected void setRotation(float f, float f1)
    {
        rotationYaw = f;
        rotationPitch = f1;
    }

    public void setPosition(double d, double d1, double d2)
    {
        posX = d;
        posY = d1;
        posZ = d2;
        float f = width / 2.0F;
        float f1 = height;
        boundingBox.setBounds(d - (double)f, (d1 - (double)yOffset) + (double)field_9068_R, d2 - (double)f, d + (double)f, (d1 - (double)yOffset) + (double)field_9068_R + (double)f1, d2 + (double)f);
    }

    public void onUpdate()
    {
        func_84_k();
    }

    public void func_84_k()
    {
        if(field_327_g != null && field_327_g.isDead)
        {
            field_327_g = null;
        }
        field_9063_X++;
        field_9075_K = field_9074_L;
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        prevRotationPitch = rotationPitch;
        prevRotationYaw = rotationYaw;
        if(handleWaterMovement())
        {
            if(!field_9085_ab && !field_4131_c)
            {
                float f = MathHelper.sqrt_double(motionX * motionX * 0.20000000298023224D + motionY * motionY + motionZ * motionZ * 0.20000000298023224D) * 0.2F;
                if(f > 1.0F)
                {
                    f = 1.0F;
                }
                worldObj.playSoundAtEntity(this, "random.splash", f, 1.0F + (random.nextFloat() - random.nextFloat()) * 0.4F);
                float f1 = MathHelper.floor_double(boundingBox.minY);
                for(int i = 0; (float)i < 1.0F + width * 20F; i++)
                {
                    float f2 = (random.nextFloat() * 2.0F - 1.0F) * width;
                    float f4 = (random.nextFloat() * 2.0F - 1.0F) * width;
                    worldObj.spawnParticle("bubble", posX + (double)f2, f1 + 1.0F, posZ + (double)f4, motionX, motionY - (double)(random.nextFloat() * 0.2F), motionZ);
                }

                for(int j = 0; (float)j < 1.0F + width * 20F; j++)
                {
                    float f3 = (random.nextFloat() * 2.0F - 1.0F) * width;
                    float f5 = (random.nextFloat() * 2.0F - 1.0F) * width;
                    worldObj.spawnParticle("splash", posX + (double)f3, f1 + 1.0F, posZ + (double)f5, motionX, motionY, motionZ);
                }

            }
            fallDistance = 0.0F;
            field_9085_ab = true;
            field_9061_Z = 0;
        } else
        {
            field_9085_ab = false;
        }
        if(worldObj.multiplayerWorld)
        {
            field_9061_Z = 0;
        } else
        if(field_9061_Z > 0)
        {
            if(field_9079_ae)
            {
                field_9061_Z -= 4;
                if(field_9061_Z < 0)
                {
                    field_9061_Z = 0;
                }
            } else
            {
                if(field_9061_Z % 20 == 0)
                {


                    attackEntity(null, 1);
                }
                field_9061_Z--;
            }
        }
        if(func_112_q())
        {
            damageSources.add("lava");
            func_4040_n();
        }
        if(posY < -64D)
        {
            damageSources.add("void");

            func_4043_o();
        }
        field_4131_c = false;
    }

    protected void func_4040_n()
    {
        if(!field_9079_ae)
        {
            attackEntity(null, 4);
            field_9061_Z = 600;
        }
    }

    protected void func_4043_o()
    {
        setEntityDead();
    }

    public boolean func_133_b(double d, double d1, double d2)
    {
        AxisAlignedBB axisalignedbb = boundingBox.getOffsetBoundingBox(d, d1, d2);
        List list = worldObj.getCollidingBoundingBoxes(this, axisalignedbb);
        if(list.size() > 0)
        {
            return false;
        }
        return !worldObj.getIsAnyLiquid(axisalignedbb);
    }

    public void moveEntity(double d, double d1, double d2)
    {
        if(field_9066_T)
        {
            boundingBox.offset(d, d1, d2);
            posX = (boundingBox.minX + boundingBox.maxX) / 2D;
            posY = (boundingBox.minY + (double)yOffset) - (double)field_9068_R;
            posZ = (boundingBox.minZ + boundingBox.maxZ) / 2D;
            return;
        }
        double d3 = posX;
        double d4 = posZ;
        double d5 = d;
        double d6 = d1;
        double d7 = d2;
        AxisAlignedBB axisalignedbb = boundingBox.copy();
        boolean flag = onGround && func_9059_p();
        if(flag)
        {
            double d8 = 0.050000000000000003D;
            for(; d != 0.0D && worldObj.getCollidingBoundingBoxes(this, boundingBox.getOffsetBoundingBox(d, -1D, 0.0D)).size() == 0; d5 = d)
            {
                if(d < d8 && d >= -d8)
                {
                    d = 0.0D;
                    continue;
                }
                if(d > 0.0D)
                {
                    d -= d8;
                } else
                {
                    d += d8;
                }
            }

            for(; d2 != 0.0D && worldObj.getCollidingBoundingBoxes(this, boundingBox.getOffsetBoundingBox(0.0D, -1D, d2)).size() == 0; d7 = d2)
            {
                if(d2 < d8 && d2 >= -d8)
                {
                    d2 = 0.0D;
                    continue;
                }
                if(d2 > 0.0D)
                {
                    d2 -= d8;
                } else
                {
                    d2 += d8;
                }
            }

        }
        List list = worldObj.getCollidingBoundingBoxes(this, boundingBox.addCoord(d, d1, d2));
        for(int i = 0; i < list.size(); i++)
        {
            d1 = ((AxisAlignedBB)list.get(i)).func_701_b(boundingBox, d1);
        }

        boundingBox.offset(0.0D, d1, 0.0D);
        if(!field_9077_F && d6 != d1)
        {
            d = d1 = d2 = 0.0D;
        }
        boolean flag1 = onGround || d6 != d1 && d6 < 0.0D;
        for(int j = 0; j < list.size(); j++)
        {
            d = ((AxisAlignedBB)list.get(j)).func_710_a(boundingBox, d);
        }

        boundingBox.offset(d, 0.0D, 0.0D);
        if(!field_9077_F && d5 != d)
        {
            d = d1 = d2 = 0.0D;
        }
        for(int k = 0; k < list.size(); k++)
        {
            d2 = ((AxisAlignedBB)list.get(k)).func_709_c(boundingBox, d2);
        }

        boundingBox.offset(0.0D, 0.0D, d2);
        if(!field_9077_F && d7 != d2)
        {
            d = d1 = d2 = 0.0D;
        }
        if(field_9067_S > 0.0F && flag1 && field_9068_R < 0.05F && (d5 != d || d7 != d2))
        {
            double d9 = d;
            double d11 = d1;
            double d13 = d2;
            d = d5;
            d1 = field_9067_S;
            d2 = d7;
            AxisAlignedBB axisalignedbb1 = boundingBox.copy();
            boundingBox.setBB(axisalignedbb);
            List list1 = worldObj.getCollidingBoundingBoxes(this, boundingBox.addCoord(d, d1, d2));
            for(int j2 = 0; j2 < list1.size(); j2++)
            {
                d1 = ((AxisAlignedBB)list1.get(j2)).func_701_b(boundingBox, d1);
            }

            boundingBox.offset(0.0D, d1, 0.0D);
            if(!field_9077_F && d6 != d1)
            {
                d = d1 = d2 = 0.0D;
            }
            for(int k2 = 0; k2 < list1.size(); k2++)
            {
                d = ((AxisAlignedBB)list1.get(k2)).func_710_a(boundingBox, d);
            }

            boundingBox.offset(d, 0.0D, 0.0D);
            if(!field_9077_F && d5 != d)
            {
                d = d1 = d2 = 0.0D;
            }
            for(int l2 = 0; l2 < list1.size(); l2++)
            {
                d2 = ((AxisAlignedBB)list1.get(l2)).func_709_c(boundingBox, d2);
            }

            boundingBox.offset(0.0D, 0.0D, d2);
            if(!field_9077_F && d7 != d2)
            {
                d = d1 = d2 = 0.0D;
            }
            if(d9 * d9 + d13 * d13 >= d * d + d2 * d2)
            {
                d = d9;
                d1 = d11;
                d2 = d13;
                boundingBox.setBB(axisalignedbb1);
            } else
            {
                field_9068_R += 0.5D;
            }
        }
        posX = (boundingBox.minX + boundingBox.maxX) / 2D;
        posY = (boundingBox.minY + (double)yOffset) - (double)field_9068_R;
        posZ = (boundingBox.minZ + boundingBox.maxZ) / 2D;
        field_9084_B = d5 != d || d7 != d2;
        field_9082_C = d6 != d1;
        onGround = d6 != d1 && d6 < 0.0D;
        field_9080_D = field_9084_B || field_9082_C;
        interact(d1, onGround);
        if(d5 != d)
        {
            motionX = 0.0D;
        }
        if(d6 != d1)
        {
            motionY = 0.0D;
        }
        if(d7 != d2)
        {
            motionZ = 0.0D;
        }
        double d10 = posX - d3;
        double d12 = posZ - d4;
        if(entityWalks && !flag)
        {
            field_9074_L += (double)MathHelper.sqrt_double(d10 * d10 + d12 * d12) * 0.59999999999999998D;
            int l = MathHelper.floor_double(posX);
            int j1 = MathHelper.floor_double(posY - 0.20000000298023224D - (double)yOffset);
            int l1 = MathHelper.floor_double(posZ);
            int i3 = worldObj.getBlockId(l, j1, l1);
            if(field_9074_L > (float)field_6151_b && i3 > 0)
            {
                field_6151_b++;
                StepSound stepsound = Block.blocksList[i3].stepSound;
                if(worldObj.getBlockId(l, j1 + 1, l1) == Block.snow.blockID)
                {
                    stepsound = Block.snow.stepSound;
                    worldObj.playSoundAtEntity(this, stepsound.func_737_c(), stepsound.func_738_a() * 0.15F, stepsound.func_739_b());
                } else
                if(!Block.blocksList[i3].blockMaterial.getIsLiquid())
                {
                    worldObj.playSoundAtEntity(this, stepsound.func_737_c(), stepsound.func_738_a() * 0.15F, stepsound.func_739_b());
                }
                Block.blocksList[i3].onEntityWalking(worldObj, l, j1, l1, this);
            }
        }
        int i1 = MathHelper.floor_double(boundingBox.minX);
        int k1 = MathHelper.floor_double(boundingBox.minY);
        int i2 = MathHelper.floor_double(boundingBox.minZ);
        int j3 = MathHelper.floor_double(boundingBox.maxX);
        int k3 = MathHelper.floor_double(boundingBox.maxY);
        int l3 = MathHelper.floor_double(boundingBox.maxZ);
        for(int i4 = i1; i4 <= j3; i4++)
        {
            for(int j4 = k1; j4 <= k3; j4++)
            {
                for(int k4 = i2; k4 <= l3; k4++)
                {
                    int l4 = worldObj.getBlockId(i4, j4, k4);
                    if(l4 > 0)
                    {
                        Block.blocksList[l4].onEntityCollidedWithBlock(worldObj, i4, j4, k4, this);
                    }
                }

            }

        }

        field_9068_R *= 0.4F;
        boolean flag2 = handleWaterMovement();
        if(worldObj.func_523_c(boundingBox))
        {
            func_125_b(1);
            if(!flag2)
            {
                field_9061_Z++;
                if(field_9061_Z == 0)
                {
                    field_9061_Z = 300;
                }
            }
        } else
        if(field_9061_Z <= 0)
        {
            field_9061_Z = -field_9062_Y;
        }
        if(flag2 && field_9061_Z > 0)
        {
            worldObj.playSoundAtEntity(this, "random.fizz", 0.7F, 1.6F + (random.nextFloat() - random.nextFloat()) * 0.4F);
            field_9061_Z = -field_9062_Y;
        }
    }

    protected void interact(double d, boolean flag)
    {
        if(flag)
        {
            if(fallDistance > 0.0F)
            {


            	int xPos = MathHelper.floor_double(posX);
                int yPos = MathHelper.floor_double(posY - 0.20000000298023224D - (double)yOffset);
                int zPos = MathHelper.floor_double(posZ);
                int currentBlock = worldObj.getBlockId(xPos, yPos, zPos);
                //checks for farmland, since I only wrote that function for it
                if(currentBlock > 0 && currentBlock == Block.tilledField.blockID)
                {
                	Block.blocksList[currentBlock].onEntityFallen(worldObj, xPos, yPos, zPos, this, fallDistance);
                }
                fall(fallDistance);
                fallDistance = 0.0F;
            }
        } else
        if(d < 0.0D)
        {
            fallDistance -= d;
        }
    }

    public boolean func_9059_p()
    {
        return false;
    }

    public AxisAlignedBB func_93_n()
    {
        return null;
    }

    protected void func_125_b(int i)
    {
        if(!field_9079_ae)
        {
            attackEntity(null, i);
        }
    }

    protected void fall(float f)
    {
    }

    public boolean handleWaterMovement()
    {
        return worldObj.func_490_a(boundingBox.expands(0.0D, -0.40000000596046448D, 0.0D), Material.water, this);
    }

    public boolean isInsideOfMaterial(Material material)
    {
        double d = posY + (double)func_104_p();
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_float(MathHelper.floor_double(d));
        int k = MathHelper.floor_double(posZ);
        int l = worldObj.getBlockId(i, j, k);
        if(l != 0 && Block.blocksList[l].blockMaterial == material)
        {
            float f = BlockFluids.setFluidHeight(worldObj.getBlockMetadata(i, j, k)) - 0.1111111F;
            float f1 = (float)(j + 1) - f;
            return d < (double)f1;
        } else
        {
            return false;
        }
    }

    public float func_104_p()
    {
        return 0.0F;
    }

    public boolean func_112_q()
    {
        return worldObj.isMaterialInBB(boundingBox.expands(0.0D, -0.40000000596046448D, 0.0D), Material.lava);
    }

    public void func_90_a(float f, float f1, float f2)
    {
        float f3 = MathHelper.sqrt_float(f * f + f1 * f1);
        if(f3 < 0.01F)
        {
            return;
        }
        if(f3 < 1.0F)
        {
            f3 = 1.0F;
        }
        f3 = f2 / f3;
        f *= f3;
        f1 *= f3;
        float f4 = MathHelper.sin((rotationYaw * 3.141593F) / 180F);
        float f5 = MathHelper.cos((rotationYaw * 3.141593F) / 180F);
        motionX += f * f5 - f1 * f4;
        motionZ += f1 * f5 + f * f4;
    }

    public float getEntityBrightness(float f)
    {
        int i = MathHelper.floor_double(posX);
        double d = (boundingBox.maxY - boundingBox.minY) * 0.66000000000000003D;
        int j = MathHelper.floor_double((posY - (double)yOffset) + d);
        int k = MathHelper.floor_double(posZ);
        return worldObj.getLightBrightness(i, j, k);
    }

    public void setPositionAndRotation(double d, double d1, double d2, float f, 
            float f1)
    {
        prevPosX = posX = d;
        prevPosY = posY = d1;
        prevPosZ = posZ = d2;
        prevRotationYaw = rotationYaw = f;
        prevRotationPitch = rotationPitch = f1;
        field_9068_R = 0.0F;
        double d3 = prevRotationYaw - f;
        if(d3 < -180D)
        {
            prevRotationYaw += 360F;
        }
        if(d3 >= 180D)
        {
            prevRotationYaw -= 360F;
        }
        setPosition(posX, posY, posZ);
        setRotation(f, f1);
    }

    public void func_107_c(double d, double d1, double d2, float f, 
            float f1)
    {
        prevPosX = posX = d;
        prevPosY = posY = d1 + (double)yOffset;
        prevPosZ = posZ = d2;
        rotationYaw = f;
        rotationPitch = f1;
        setPosition(posX, posY, posZ);
    }

    public float getDistanceToEntity(Entity entity)
    {
        float f = (float)(posX - entity.posX);
        float f1 = (float)(posY - entity.posY);
        float f2 = (float)(posZ - entity.posZ);
        return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
    }

    public double getDistanceSq(double d, double d1, double d2)
    {
        double d3 = posX - d;
        double d4 = posY - d1;
        double d5 = posZ - d2;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public double getDistance(double d, double d1, double d2)
    {
        double d3 = posX - d;
        double d4 = posY - d1;
        double d5 = posZ - d2;
        return (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
    }

    public double getDistanceSqToEntity(Entity entity)
    {
        double d = posX - entity.posX;
        double d1 = posY - entity.posY;
        double d2 = posZ - entity.posZ;
        return d * d + d1 * d1 + d2 * d2;
    }

    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
    }

    public void applyEntityCollision(Entity entity)
    {
        if(entity.field_328_f == this || entity.field_327_g == this)
        {
            return;
        }
        double d = entity.posX - posX;
        double d1 = entity.posZ - posZ;
        double d2 = MathHelper.abs_max(d, d1);
        if(d2 >= 0.0099999997764825821D)
        {
            d2 = MathHelper.sqrt_double(d2);
            d /= d2;
            d1 /= d2;
            double d3 = 1.0D / d2;
            if(d3 > 1.0D)
            {
                d3 = 1.0D;
            }
            d *= d3;
            d1 *= d3;
            d *= 0.05000000074505806D;
            d1 *= 0.05000000074505806D;
            d *= 1.0F - field_286_P;
            d1 *= 1.0F - field_286_P;
            addVelocity(-d, 0.0D, -d1);
            entity.addVelocity(d, 0.0D, d1);
        }
    }

    public void addVelocity(double d, double d1, double d2)
    {
        motionX += d;
        motionY += d1;
        motionZ += d2;
    }

    protected void func_9060_u()
    {
        field_9078_E = true;
    }

    public boolean attackEntity(Entity entity, int i)
    {
        if((entity instanceof  EntityPlayer)){
            ((EntityPlayer) entity).damageSources.add("entity");
            ((EntityPlayer) entity).lastDamagingEntity = this;

        }
        func_9060_u();

        return false;
    }

    public boolean func_129_c_()
    {
        return false;
    }

    public boolean func_124_r()
    {
        return false;
    }

    public void func_96_b(Entity entity, int i)
    {
    }

    public boolean func_95_c(NBTTagCompound nbttagcompound)
    {
        String s = func_109_s();
        if(isDead || s == null)
        {
            return false;
        } else
        {
            nbttagcompound.setString("id", s);
            writeToNBT(nbttagcompound);
            return true;
        }
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setTag("Pos", func_132_a(new double[] {
            posX, posY, posZ
        }));
        nbttagcompound.setTag("Motion", func_132_a(new double[] {
            motionX, motionY, motionZ
        }));
        nbttagcompound.setTag("Rotation", func_85_a(new float[] {
            rotationYaw, rotationPitch
        }));
        nbttagcompound.setFloat("FallDistance", fallDistance);
        nbttagcompound.setShort("Fire", (short)field_9061_Z);
        nbttagcompound.setShort("Air", (short)air);
        nbttagcompound.setBoolean("OnGround", onGround);
        writeEntityToNBT(nbttagcompound);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        NBTTagList nbttaglist = nbttagcompound.getTagList("Pos");
        NBTTagList nbttaglist1 = nbttagcompound.getTagList("Motion");
        NBTTagList nbttaglist2 = nbttagcompound.getTagList("Rotation");
        setPosition(0.0D, 0.0D, 0.0D);
        motionX = ((NBTTagDouble)nbttaglist1.tagAt(0)).doubleValue;
        motionY = ((NBTTagDouble)nbttaglist1.tagAt(1)).doubleValue;
        motionZ = ((NBTTagDouble)nbttaglist1.tagAt(2)).doubleValue;
        prevPosX = field_9071_O = posX = ((NBTTagDouble)nbttaglist.tagAt(0)).doubleValue;
        prevPosY = field_9070_P = posY = ((NBTTagDouble)nbttaglist.tagAt(1)).doubleValue;
        prevPosZ = field_9069_Q = posZ = ((NBTTagDouble)nbttaglist.tagAt(2)).doubleValue;
        prevRotationYaw = rotationYaw = ((NBTTagFloat)nbttaglist2.tagAt(0)).floatValue;
        prevRotationPitch = rotationPitch = ((NBTTagFloat)nbttaglist2.tagAt(1)).floatValue;
        fallDistance = nbttagcompound.getFloat("FallDistance");
        field_9061_Z = nbttagcompound.getShort("Fire");
        air = nbttagcompound.getShort("Air");
        onGround = nbttagcompound.getBoolean("OnGround");
        setPosition(posX, posY, posZ);
        readEntityFromNBT(nbttagcompound);

    }

    protected final String func_109_s()
    {
        return EntityList.func_564_b(this);
    }

    protected abstract void readEntityFromNBT(NBTTagCompound nbttagcompound);

    protected abstract void writeEntityToNBT(NBTTagCompound nbttagcompound);

    protected NBTTagList func_132_a(double ad[])
    {
        NBTTagList nbttaglist = new NBTTagList();
        double ad1[] = ad;
        int i = ad1.length;
        for(int j = 0; j < i; j++)
        {
            double d = ad1[j];
            nbttaglist.setTag(new NBTTagDouble(d));
        }

        return nbttaglist;
    }

    protected NBTTagList func_85_a(float af[])
    {
        NBTTagList nbttaglist = new NBTTagList();
        float af1[] = af;
        int i = af1.length;
        for(int j = 0; j < i; j++)
        {
            float f = af1[j];
            nbttaglist.setTag(new NBTTagFloat(f));
        }

        return nbttaglist;
    }

    public EntityItem dropItem(int i, int j, int damage)
    {

        if(ExploitUtils.IsIdValid(i)) {
            return dropItemWithOffset(i, j, damage, 0.0F);
        }
        return dropItemWithOffset(1, j, damage, 0.0F);
    }

    public EntityItem dropItemWithOffset(int i, int j, int damage, float f)
    {
        ExploitUtils exploitUtils= new ExploitUtils();
        EntityItem entityitem;
        if(exploitUtils.IsIdValid(i)) {
         entityitem = new EntityItem(worldObj, posX, posY + (double) f, posZ, new ItemStack(i, j, damage));
        }
        else{
            entityitem = new EntityItem(worldObj, posX, posY + (double) f, posZ, new ItemStack(1, j, damage));
        }
        entityitem.field_433_ad = 10;
        worldObj.entityJoinedWorld(entityitem);
        return entityitem;
    }

    public boolean func_120_t()
    {
        return !isDead;
    }

    public boolean func_91_u()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY + (double)func_104_p());
        int k = MathHelper.floor_double(posZ);
        return worldObj.doesBlockAllowAttachment(i, j, k);
    }

    public boolean func_6092_a(EntityPlayer entityplayer)
    {
        return false;
    }

    public AxisAlignedBB func_89_d(Entity entity)
    {
        return null;
    }

    public void func_115_v()
    {
        if(field_327_g.isDead)
        {
            field_327_g = null;
            return;
        }
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        onUpdate();
        field_327_g.func_127_w();
        field_4128_e += field_327_g.rotationYaw - field_327_g.prevRotationYaw;
        field_4130_d += field_327_g.rotationPitch - field_327_g.prevRotationPitch;
        for(; field_4128_e >= 180D; field_4128_e -= 360D) { }
        for(; field_4128_e < -180D; field_4128_e += 360D) { }
        for(; field_4130_d >= 180D; field_4130_d -= 360D) { }
        for(; field_4130_d < -180D; field_4130_d += 360D) { }
        double d = field_4128_e * 0.5D;
        double d1 = field_4130_d * 0.5D;
        float f = 10F;
        if(d > (double)f)
        {
            d = f;
        }
        if(d < (double)(-f))
        {
            d = -f;
        }
        if(d1 > (double)f)
        {
            d1 = f;
        }
        if(d1 < (double)(-f))
        {
            d1 = -f;
        }
        field_4128_e -= d;
        field_4130_d -= d1;
        rotationYaw += d;
        rotationPitch += d1;
    }

    public void func_127_w()
    {
        field_328_f.setPosition(posX, posY + func_130_h() + field_328_f.func_117_x(), posZ);
    }

    public double func_117_x()
    {
        return (double)yOffset;
    }

    public double func_130_h()
    {
        return (double)height * 0.75D;
    }

    public void func_6094_e(Entity entity)
    {
        field_4130_d = 0.0D;
        field_4128_e = 0.0D;
        if(entity == null)
        {
            if(field_327_g != null)
            {
                func_107_c(field_327_g.posX, field_327_g.boundingBox.minY + (double)field_327_g.height, field_327_g.posZ, rotationYaw, rotationPitch);
                field_327_g.field_328_f = null;
            }
            field_327_g = null;
            return;
        }
        if(field_327_g == entity)
        {
            field_327_g.field_328_f = null;
            field_327_g = null;
            func_107_c(entity.posX, entity.boundingBox.minY + (double)entity.height, entity.posZ, rotationYaw, rotationPitch);
            return;
        }
        if(field_327_g != null)
        {
            field_327_g.field_328_f = null;
        }
        if(entity.field_328_f != null)
        {
            entity.field_328_f.field_327_g = null;
        }
        field_327_g = entity;
        entity.field_328_f = this;
    }



    public Vec3D func_4039_B()
    {
        return null;
    }

    private static int field_384_a = 0;
    public int field_331_c;
    public double field_9094_h;
    public boolean field_329_e;
    public Entity field_328_f;
    public Entity field_327_g;
    public World worldObj;
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    public double posX;
    public double posY;
    public double posZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    public final AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    public boolean onGround;
    public boolean field_9084_B;
    public boolean field_9082_C;
    public boolean field_9080_D;
    public boolean field_9078_E;
    public boolean field_9077_F;
    public boolean isDead;
    public float yOffset;
    public float width;
    public float height;
    public float field_9075_K;
    public float field_9074_L;
    protected boolean entityWalks;
    protected float fallDistance;
    private int field_6151_b;
    public double field_9071_O;
    public double field_9070_P;
    public double field_9069_Q;
    public float field_9068_R;
    public float field_9067_S;
    public boolean field_9066_T;
    public float field_286_P;
    public boolean field_9065_V;
    protected Random random;
    public int field_9063_X;
    public int field_9062_Y;
    public int field_9061_Z;
    protected int field_9087_aa;
    protected boolean field_9085_ab;
    public int field_9083_ac;
    public int air;
    private boolean field_4131_c;
    protected boolean field_9079_ae;
    private double field_4130_d;
    private double field_4128_e;
    public boolean field_276_Z;
    public int field_307_aa;
    public int field_305_ab;
    public int field_303_ac;
    public boolean IsPlayer;
    public List<String> damageSources = new ArrayList<String>();

}
