package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class EntityCow extends EntityAnimals
{

    public EntityCow(World world)
    {
        super(world);
        unusedBoolean = false;
        field_9119_aG = "/mob/cow.png";
        setSize(0.9F, 1.3F);
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
        return "mob.cow";
    }

    protected String getHurtSound()
    {
        return "mob.cowhurt";
    }

    protected String getDeathSound()
    {
        return "mob.cowhurt";
    }

    protected float getSoundVolume()
    {
        return 0.4F;
    }

    protected int getDropItemId()
    {
        return Item.leather.swiftedIndex;
    }

    public boolean func_6092_a(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if(itemstack != null && itemstack.itemID == Item.bucketEmpty.swiftedIndex)
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.bucketMilk));
            return true;
        } else
        {
            return false;
        }
    }

    public boolean unusedBoolean;
}
