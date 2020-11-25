package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class EntityPigZombie extends EntityZombie
{

    public EntityPigZombie(World world)
    {
        super(world);
        field_4106_a = 0;
        field_4105_b = 0;
        field_9119_aG = "/mob/pigzombie.png";
        field_9126_bt = 0.5F;
        field_404_af = 5;
        field_9079_ae = true;
    }

    public void onUpdate()
    {
        field_9126_bt = field_389_ag == null ? 0.5F : 0.95F;
        if(field_4105_b > 0 && --field_4105_b == 0)
        {
            worldObj.playSoundAtEntity(this, "mob.zombiepig.zpigangry", getSoundVolume() * 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
        }
        super.onUpdate();
    }

    public boolean getCanSpawnHere()
    {
        return worldObj.monstersEnabled > 0 && worldObj.func_522_a(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && !worldObj.getIsAnyLiquid(boundingBox);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setShort("Anger", (short)field_4106_a);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        field_4106_a = nbttagcompound.getShort("Anger");
    }

    protected Entity func_158_i()
    {
        if(field_4106_a == 0)
        {
            return null;
        } else
        {
            return super.func_158_i();
        }
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    public boolean attackEntity(Entity entity, int i)
    {
        if(entity instanceof EntityPlayer)
        {
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expands(32D, 32D, 32D));
            for(int j = 0; j < list.size(); j++)
            {
                Entity entity1 = (Entity)list.get(j);
                if(entity1 instanceof EntityPigZombie)
                {
                    EntityPigZombie entitypigzombie = (EntityPigZombie)entity1;
                    entitypigzombie.func_4047_h(entity);
                }
            }

            func_4047_h(entity);
        }
        return super.attackEntity(entity, i);
    }

    private void func_4047_h(Entity entity)
    {
        field_389_ag = entity;
        field_4106_a = 400 + random.nextInt(400);
        field_4105_b = random.nextInt(40);
    }

    protected String getLivingSound()
    {
        return "mob.zombiepig.zpig";
    }

    protected String getHurtSound()
    {
        return "mob.zombiepig.zpighurt";
    }

    protected String getDeathSound()
    {
        return "mob.zombiepig.zpigdeath";
    }

    protected int getDropItemId()
    {
        return Item.porkCooked.swiftedIndex;
    }

    private int field_4106_a;
    private int field_4105_b;
    private static final ItemStack field_4107_c;

    static 
    {
        field_4107_c = new ItemStack(Item.swordGold, 1);
    }
}
