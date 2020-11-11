package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;

public class EntityPainting extends Entity
{

    public EntityPainting(World world)
    {
        super(world);
        field_452_ad = 0;
        field_448_a = 0;
        yOffset = 0.0F;
        setSize(0.5F, 0.5F);
    }

    public EntityPainting(World world, int i, int j, int k, int l)
    {
        this(world);
        field_9188_d = i;
        field_9187_e = j;
        field_9186_f = k;
        ArrayList arraylist = new ArrayList();
        EnumArt aenumart[] = EnumArt.values();
        int i1 = aenumart.length;
        for(int j1 = 0; j1 < i1; j1++)
        {
            EnumArt enumart = aenumart[j1];
            field_9185_b = enumart;
            func_179_a(l);
            if(func_181_b())
            {
                arraylist.add(enumart);
            }
        }

        if(arraylist.size() > 0)
        {
            field_9185_b = (EnumArt)arraylist.get(field_9064_W.nextInt(arraylist.size()));
        }
        func_179_a(l);
    }

    public void func_179_a(int i)
    {
        field_448_a = i;
        prevRotationYaw = rotationYaw = i * 90;
        float f = field_9185_b.field_856_z;
        float f1 = field_9185_b.field_869_A;
        float f2 = field_9185_b.field_856_z;
        if(i == 0 || i == 2)
        {
            f2 = 0.5F;
        } else
        {
            f = 0.5F;
        }
        f /= 32F;
        f1 /= 32F;
        f2 /= 32F;
        float f3 = (float)field_9188_d + 0.5F;
        float f4 = (float)field_9187_e + 0.5F;
        float f5 = (float)field_9186_f + 0.5F;
        float f6 = 0.5625F;
        if(i == 0)
        {
            f5 -= f6;
        }
        if(i == 1)
        {
            f3 -= f6;
        }
        if(i == 2)
        {
            f5 += f6;
        }
        if(i == 3)
        {
            f3 += f6;
        }
        if(i == 0)
        {
            f3 -= func_180_c(field_9185_b.field_856_z);
        }
        if(i == 1)
        {
            f5 += func_180_c(field_9185_b.field_856_z);
        }
        if(i == 2)
        {
            f3 += func_180_c(field_9185_b.field_856_z);
        }
        if(i == 3)
        {
            f5 -= func_180_c(field_9185_b.field_856_z);
        }
        f4 += func_180_c(field_9185_b.field_869_A);
        setPosition(f3, f4, f5);
        float f7 = -0.00625F;
        boundingBox.setBounds(f3 - f - f7, f4 - f1 - f7, f5 - f2 - f7, f3 + f + f7, f4 + f1 + f7, f5 + f2 + f7);
    }

    private float func_180_c(int i)
    {
        if(i == 32)
        {
            return 0.5F;
        }
        return i != 64 ? 0.0F : 0.5F;
    }

    public void onUpdate()
    {
        if(field_452_ad++ == 100 && !func_181_b())
        {
            field_452_ad = 0;
            setEntityDead();
            worldObj.entityJoinedWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.painting)));
        }
    }

    public boolean func_181_b()
    {
        if(worldObj.getCollidingBoundingBoxes(this, boundingBox).size() > 0)
        {
            return false;
        }
        int i = field_9185_b.field_856_z / 16;
        int j = field_9185_b.field_869_A / 16;
        int k = field_9188_d;
        int l = field_9187_e;
        int i1 = field_9186_f;
        if(field_448_a == 0)
        {
            k = MathHelper.floor_double(posX - (double)((float)field_9185_b.field_856_z / 32F));
        }
        if(field_448_a == 1)
        {
            i1 = MathHelper.floor_double(posZ - (double)((float)field_9185_b.field_856_z / 32F));
        }
        if(field_448_a == 2)
        {
            k = MathHelper.floor_double(posX - (double)((float)field_9185_b.field_856_z / 32F));
        }
        if(field_448_a == 3)
        {
            i1 = MathHelper.floor_double(posZ - (double)((float)field_9185_b.field_856_z / 32F));
        }
        l = MathHelper.floor_double(posY - (double)((float)field_9185_b.field_869_A / 32F));
        for(int j1 = 0; j1 < i; j1++)
        {
            for(int k1 = 0; k1 < j; k1++)
            {
                Material material;
                if(field_448_a == 0 || field_448_a == 2)
                {
                    material = worldObj.getBlockMaterial(k + j1, l + k1, field_9186_f);
                } else
                {
                    material = worldObj.getBlockMaterial(field_9188_d, l + k1, i1 + j1);
                }
                if(!material.func_216_a())
                {
                    return false;
                }
            }

        }

        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox);
        for(int l1 = 0; l1 < list.size(); l1++)
        {
            if(list.get(l1) instanceof EntityPainting)
            {
                return false;
            }
        }

        return true;
    }

    public boolean func_129_c_()
    {
        return true;
    }

    public boolean attackEntity(Entity entity, int i)
    {
        setEntityDead();
        func_9060_u();
        worldObj.entityJoinedWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.painting)));
        return true;
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setByte("Dir", (byte)field_448_a);
        nbttagcompound.setString("Motive", field_9185_b.field_857_y);
        nbttagcompound.setInteger("TileX", field_9188_d);
        nbttagcompound.setInteger("TileY", field_9187_e);
        nbttagcompound.setInteger("TileZ", field_9186_f);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        field_448_a = nbttagcompound.getByte("Dir");
        field_9188_d = nbttagcompound.getInteger("TileX");
        field_9187_e = nbttagcompound.getInteger("TileY");
        field_9186_f = nbttagcompound.getInteger("TileZ");
        String s = nbttagcompound.getString("Motive");
        EnumArt aenumart[] = EnumArt.values();
        int i = aenumart.length;
        for(int j = 0; j < i; j++)
        {
            EnumArt enumart = aenumart[j];
            if(enumart.field_857_y.equals(s))
            {
                field_9185_b = enumart;
            }
        }

        if(field_9185_b == null)
        {
            field_9185_b = EnumArt.Kebab;
        }
        func_179_a(field_448_a);
    }

    private int field_452_ad;
    public int field_448_a;
    private int field_9188_d;
    private int field_9187_e;
    private int field_9186_f;
    public EnumArt field_9185_b;
}
