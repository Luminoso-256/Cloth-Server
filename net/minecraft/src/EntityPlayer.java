package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;
import java.util.Random;

public class EntityPlayer extends EntityLiving
{

    public EntityPlayer(World world)
    {
        super(world);
        inventory = new InventoryPlayer(this);
        field_9152_am = 0;
        field_9151_an = 0;
        field_9148_aq = false;
        field_9147_ar = 0;
        field_421_a = 0;
        field_6124_at = null;
        yOffset = 1.62F;
        func_107_c((double)world.spawnX + 0.5D, world.spawnY + 1, (double)world.spawnZ + 0.5D, 0.0F, 0.0F);
        field_9109_aQ = 20;
        field_9116_aJ = "humanoid";
        field_9117_aI = 180F;
        field_9062_Y = 20;
        field_9119_aG = "/mob/char.png";
    }

    public void func_115_v()
    {
        super.func_115_v();
        field_9150_ao = field_9149_ap;
        field_9149_ap = 0.0F;
    }

    protected void func_152_d_()
    {
        if(field_9148_aq)
        {
            field_9147_ar++;
            if(field_9147_ar == 8)
            {
                field_9147_ar = 0;
                field_9148_aq = false;
            }
        } else
        {
            field_9147_ar = 0;
        }
        field_9110_aP = (float)field_9147_ar / 8F;
    }

    public void onLivingUpdate()
    {
        if(worldObj.monstersEnabled == 0 && field_9109_aQ < 20 && (field_9063_X % 20) * 4 == 0)
        {
            heal(1);
        }
        inventory.decrementAnimations();
        field_9150_ao = field_9149_ap;
        super.onLivingUpdate();
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        float f1 = (float)Math.atan(-motionY * 0.20000000298023224D) * 15F;
        if(f > 0.1F)
        {
            f = 0.1F;
        }
        if(!onGround || field_9109_aQ <= 0)
        {
            f = 0.0F;
        }
        if(onGround || field_9109_aQ <= 0)
        {
            f1 = 0.0F;
        }
        field_9149_ap += (f - field_9149_ap) * 0.4F;
        field_9101_aY += (f1 - field_9101_aY) * 0.8F;
        if(field_9109_aQ > 0)
        {
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expands(1.0D, 0.0D, 1.0D));
            if(list != null)
            {
                for(int i = 0; i < list.size(); i++)
                {
                    func_171_h((Entity)list.get(i));
                }

            }
        }
    }

    private void func_171_h(Entity entity)
    {
        entity.onCollideWithPlayer(this);
    }

    public void onDeath(Entity entity)
    {
        super.onDeath(entity);
        setSize(0.2F, 0.2F);
        setPosition(posX, posY, posZ);
        motionY = 0.10000000149011612D;
        if(username.equals("Notch"))
        {
            func_169_a(new ItemStack(Item.appleRed, 1), true);
        }
        inventory.dropAllItems();
        if(entity != null)
        {
            motionX = -MathHelper.cos(((field_9105_aU + rotationYaw) * 3.141593F) / 180F) * 0.1F;
            motionZ = -MathHelper.sin(((field_9105_aU + rotationYaw) * 3.141593F) / 180F) * 0.1F;
        } else
        {
            motionX = motionZ = 0.0D;
        }
        yOffset = 0.1F;
    }

    public void func_96_b(Entity entity, int i)
    {
        field_9151_an += i;
    }

    public void func_161_a(ItemStack itemstack)
    {
        func_169_a(itemstack, false);
    }

    public void func_169_a(ItemStack itemstack, boolean flag)
    {
        if(itemstack == null)
        {
            return;
        }
        EntityItem entityitem = new EntityItem(worldObj, posX, (posY - 0.30000001192092896D) + (double)func_104_p(), posZ, itemstack);
        entityitem.field_433_ad = 40;
        float f = 0.1F;
        if(flag)
        {
            float f2 = field_9064_W.nextFloat() * 0.5F;
            float f4 = field_9064_W.nextFloat() * 3.141593F * 2.0F;
            entityitem.motionX = -MathHelper.sin(f4) * f2;
            entityitem.motionZ = MathHelper.cos(f4) * f2;
            entityitem.motionY = 0.20000000298023224D;
        } else
        {
            float f1 = 0.3F;
            entityitem.motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f1;
            entityitem.motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f1;
            entityitem.motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f1 + 0.1F;
            f1 = 0.02F;
            float f3 = field_9064_W.nextFloat() * 3.141593F * 2.0F;
            f1 *= field_9064_W.nextFloat();
            entityitem.motionX += Math.cos(f3) * (double)f1;
            entityitem.motionY += (field_9064_W.nextFloat() - field_9064_W.nextFloat()) * 0.1F;
            entityitem.motionZ += Math.sin(f3) * (double)f1;
        }
        func_162_a(entityitem);
    }

    protected void func_162_a(EntityItem entityitem)
    {
        worldObj.entityJoinedWorld(entityitem);
    }

    public float getCurrentPlayerStrVsBlock(Block block)
    {
        float f = inventory.getStrVsBlock(block);
        if(isInsideOfMaterial(Material.water))
        {
            f /= 5F;
        }
        if(!onGround)
        {
            f /= 5F;
        }
        return f;
    }

    public boolean func_167_b(Block block)
    {
        return inventory.canHarvestBlock(block);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Inventory");
        inventory.readFromNBT(nbttaglist);
        field_4110_as = nbttagcompound.getInteger("Dimension");
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setTag("Inventory", inventory.writeToNBT(new NBTTagList()));
        nbttagcompound.setInteger("Dimension", field_4110_as);
    }

    public void func_166_a(IInventory iinventory)
    {
    }

    public void func_174_A()
    {
    }

    public void func_163_c(Entity entity, int i)
    {
    }

    public float func_104_p()
    {
        return 0.12F;
    }

    public boolean attackEntity(Entity entity, int i)
    {
        field_9132_bn = 0;
        if(field_9109_aQ <= 0)
        {
            return false;
        }
        if((entity instanceof EntityMobs) || (entity instanceof EntityArrow))
        {
            if(worldObj.monstersEnabled == 0)
            {
                i = 0;
            }
            if(worldObj.monstersEnabled == 1)
            {
                i = i / 3 + 1;
            }
            if(worldObj.monstersEnabled == 3)
            {
                i = (i * 3) / 2;
            }
        }
        if(i == 0)
        {
            return false;
        } else
        {
            return super.attackEntity(entity, i);
        }
    }

    protected void func_6099_c(int i)
    {
        int j = 25 - inventory.getTotalArmorValue();
        int k = i * j + field_421_a;
        inventory.damageArmor(i);
        i = k / 25;
        field_421_a = k % 25;
        super.func_6099_c(i);
    }

    public void func_170_a(TileEntityFurnace tileentityfurnace)
    {
    }

    public void func_4048_a(TileEntitySign tileentitysign)
    {
    }

    public void func_9145_g(Entity entity)
    {
        entity.func_6092_a(this);
    }

    public ItemStack func_172_B()
    {
        return inventory.getCurrentItem();
    }

    public void func_164_C()
    {
        inventory.setInventorySlotContents(inventory.currentItem, null);
    }

    public double func_117_x()
    {
        return (double)(yOffset - 0.5F);
    }

    public void func_168_z()
    {
        field_9147_ar = -1;
        field_9148_aq = true;
    }

    public void func_9146_h(Entity entity)
    {
        int i = inventory.func_9157_a(entity);
        if(i > 0)
        {
            entity.attackEntity(this, i);
            ItemStack itemstack = func_172_B();
            if(itemstack != null && (entity instanceof EntityLiving))
            {
                itemstack.func_9217_a((EntityLiving)entity);
                if(itemstack.stackSize <= 0)
                {
                    itemstack.func_577_a(this);
                    func_164_C();
                }
            }
        }
    }

    public InventoryPlayer inventory;
    public byte field_9152_am;
    public int field_9151_an;
    public float field_9150_ao;
    public float field_9149_ap;
    public boolean field_9148_aq;
    public int field_9147_ar;
    public String username;
    public int field_4110_as;
    private int field_421_a;
    public EntityFish field_6124_at;
}
