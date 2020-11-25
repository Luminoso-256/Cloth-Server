package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class EntityFallingSand extends Entity
{

    public EntityFallingSand(World world)
    {
        super(world);
        field_426_b = 0;
    }

    public EntityFallingSand(World world, float f, float f1, float f2, int i)
    {
        super(world);
        field_426_b = 0;
        field_427_a = i;
        field_329_e = true;
        setSize(0.98F, 0.98F);
        yOffset = height / 2.0F;
        setPosition(f, f1, f2);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        entityWalks = false;
        prevPosX = f;
        prevPosY = f1;
        prevPosZ = f2;
    }

    public boolean func_129_c_()
    {
        return !field_304_B;
    }

    public void onUpdate()
    {
        if(field_427_a == 0)
        {
            setEntityDead();
            return;
        }
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        field_426_b++;
        motionY -= 0.039999999105930328D;
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.98000001907348633D;
        motionY *= 0.98000001907348633D;
        motionZ *= 0.98000001907348633D;
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY);
        int k = MathHelper.floor_double(posZ);
        if(worldObj.getBlockId(i, j, k) == field_427_a)
        {
            worldObj.setBlockWithNotify(i, j, k, 0);
        }
        if(onGround)
        {
            motionX *= 0.69999998807907104D;
            motionZ *= 0.69999998807907104D;
            motionY *= -0.5D;
            setEntityDead();
            if(!worldObj.func_516_a(field_427_a, i, j, k, true) || !worldObj.setBlockWithNotify(i, j, k, field_427_a))
            {
                dropItem(field_427_a, 1,0);
            }
        } else
        if(field_426_b > 100)
        {
            dropItem(field_427_a, 1,0);
            setEntityDead();
        }
    }

    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setByte("Tile", (byte)field_427_a);
    }

    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        field_427_a = nbttagcompound.getByte("Tile") & 0xff;
    }

    public int field_427_a;
    public int field_426_b;
}
