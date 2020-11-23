package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityCreature extends EntityLiving
{

    public EntityCreature(World world)
    {
        super(world);
        field_387_ah = false;
    }

    protected void func_152_d_()
    {
        field_387_ah = false;
        float f = 16F;
        if(field_389_ag == null)
        {
            field_389_ag = func_158_i();
            if(field_389_ag != null)
            {
                field_388_a = worldObj.func_482_a(this, field_389_ag, f);
            }
        } else
        if(!field_389_ag.func_120_t())
        {
            field_389_ag = null;
        } else
        {
            float f1 = field_389_ag.getDistanceToEntity(this);
            if(func_145_g(field_389_ag))
            {
                func_157_a(field_389_ag, f1);
            }
        }
        if(!field_387_ah && field_389_ag != null && (field_388_a == null || random.nextInt(20) == 0))
        {
            field_388_a = worldObj.func_482_a(this, field_389_ag, f);
        } else
        if(field_388_a == null && random.nextInt(80) == 0 || random.nextInt(80) == 0)
        {
            boolean flag = false;
            int j = -1;
            int k = -1;
            int l = -1;
            float f2 = -99999F;
            for(int i1 = 0; i1 < 10; i1++)
            {
                int j1 = MathHelper.floor_double((posX + (double) random.nextInt(13)) - 6D);
                int k1 = MathHelper.floor_double((posY + (double) random.nextInt(7)) - 3D);
                int l1 = MathHelper.floor_double((posZ + (double) random.nextInt(13)) - 6D);
                float f3 = func_159_a(j1, k1, l1);
                if(f3 > f2)
                {
                    f2 = f3;
                    j = j1;
                    k = k1;
                    l = l1;
                    flag = true;
                }
            }

            if(flag)
            {
                field_388_a = worldObj.func_501_a(this, j, k, l, 10F);
            }
        }
        int i = MathHelper.floor_double(boundingBox.minY);
        boolean flag1 = handleWaterMovement();
        boolean flag2 = func_112_q();
        rotationPitch = 0.0F;
        if(field_388_a == null || random.nextInt(100) == 0)
        {
            super.func_152_d_();
            field_388_a = null;
            return;
        }
        Vec3D vec3d = field_388_a.getPosition(this);
        for(double d = width * 2.0F; vec3d != null && vec3d.squareDistanceTo(posX, vec3d.yCoord, posZ) < d * d;)
        {
            field_388_a.incrementPathIndex();
            if(field_388_a.isFinished())
            {
                vec3d = null;
                field_388_a = null;
            } else
            {
                vec3d = field_388_a.getPosition(this);
            }
        }

        field_9128_br = false;
        if(vec3d != null)
        {
            double d1 = vec3d.xCoord - posX;
            double d2 = vec3d.zCoord - posZ;
            double d3 = vec3d.yCoord - (double)i;
            float f4 = (float)((Math.atan2(d2, d1) * 180D) / 3.1415927410125732D) - 90F;
            float f5 = f4 - rotationYaw;
            field_9130_bp = field_9126_bt;
            for(; f5 < -180F; f5 += 360F) { }
            for(; f5 >= 180F; f5 -= 360F) { }
            if(f5 > 30F)
            {
                f5 = 30F;
            }
            if(f5 < -30F)
            {
                f5 = -30F;
            }
            rotationYaw += f5;
            if(field_387_ah && field_389_ag != null)
            {
                double d4 = field_389_ag.posX - posX;
                double d5 = field_389_ag.posZ - posZ;
                float f7 = rotationYaw;
                rotationYaw = (float)((Math.atan2(d5, d4) * 180D) / 3.1415927410125732D) - 90F;
                float f6 = (((f7 - rotationYaw) + 90F) * 3.141593F) / 180F;
                field_9131_bo = -MathHelper.sin(f6) * field_9130_bp * 1.0F;
                field_9130_bp = MathHelper.cos(f6) * field_9130_bp * 1.0F;
            }
            if(d3 > 0.0D)
            {
                field_9128_br = true;
            }
        }
        if(field_389_ag != null)
        {
            func_147_b(field_389_ag, 30F);
        }
        if(field_9084_B)
        {
            field_9128_br = true;
        }
        if(random.nextFloat() < 0.8F && (flag1 || flag2))
        {
            field_9128_br = true;
        }
    }

    protected void func_157_a(Entity entity, float f)
    {
    }

    protected float func_159_a(int i, int j, int k)
    {
        return 0.0F;
    }

    protected Entity func_158_i()
    {
        return null;
    }

    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);
        return super.getCanSpawnHere() && func_159_a(i, j, k) >= 0.0F;
    }

    private PathEntity field_388_a;
    protected Entity field_389_ag;
    protected boolean field_387_ah;
}
