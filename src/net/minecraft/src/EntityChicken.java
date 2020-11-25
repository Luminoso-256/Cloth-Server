package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class EntityChicken extends EntityAnimals
{

    public EntityChicken(World world)
    {
        super(world);
        field_392_a = false;
        field_391_b = 0.0F;
        field_395_ad = 0.0F;
        field_390_ai = 1.0F;
        field_9119_aG = "/mob/chicken.png";
        setSize(0.3F, 0.4F);
        health = 4;
        field_396_aj = random.nextInt(6000) + 6000;
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        field_393_af = field_391_b;
        field_394_ae = field_395_ad;
        field_395_ad += (double)(onGround ? -1 : 4) * 0.29999999999999999D;
        if(field_395_ad < 0.0F)
        {
            field_395_ad = 0.0F;
        }
        if(field_395_ad > 1.0F)
        {
            field_395_ad = 1.0F;
        }
        if(!onGround && field_390_ai < 1.0F)
        {
            field_390_ai = 1.0F;
        }
        field_390_ai *= 0.90000000000000002D;
        if(!onGround && motionY < 0.0D)
        {
            motionY *= 0.59999999999999998D;
        }
        field_391_b += field_390_ai * 2.0F;
        if(!worldObj.multiplayerWorld && --field_396_aj <= 0)
        {
            worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
            dropItem(Item.egg.swiftedIndex, 1, 0);
            field_396_aj = random.nextInt(6000) + 6000;
        }
    }

    protected void fall(float f)
    {
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
    }

    protected String getLivingSound()
    {
        return "mob.chicken";
    }

    protected String getHurtSound()
    {
        return "mob.chickenhurt";
    }

    protected String getDeathSound()
    {
        return "mob.chickenhurt";
    }

    protected int getDropItemId()
    {
        return Item.feather.swiftedIndex;
    }

    public boolean field_392_a;
    public float field_391_b;
    public float field_395_ad;
    public float field_394_ae;
    public float field_393_af;
    public float field_390_ai;
    public int field_396_aj;
}
