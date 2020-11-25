package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class EntityPig extends EntityAnimals
{

    public EntityPig(World world)
    {
        super(world);
        rideable = false;
        field_9119_aG = "/mob/pig.png";
        setSize(0.9F, 0.9F);
        rideable = false;
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", rideable);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        rideable = nbttagcompound.getBoolean("Saddle");
    }

    protected String getLivingSound()
    {
        return "mob.pig";
    }

    protected String getHurtSound()
    {
        return "mob.pig";
    }

    protected String getDeathSound()
    {
        return "mob.pigdeath";
    }

    public boolean func_6092_a(EntityPlayer entityplayer)
    {
        if(rideable)
        {
            entityplayer.func_6094_e(this);
            return true;
        } else
        {
            return false;
        }
    }

    protected int getDropItemId()
    {
        return Item.porkRaw.swiftedIndex;
    }

    public boolean rideable;
}
