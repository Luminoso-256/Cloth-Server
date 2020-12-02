package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.List;

public class EntityMinecart extends Entity
        implements IInventory {

    private static final int field_468_ak[][][] = {
            {
                    {
                            0, 0, -1
                    }, {
                    0, 0, 1
            }
            }, {
            {
                    -1, 0, 0
            }, {
            1, 0, 0
    }
    }, {
            {
                    -1, -1, 0
            }, {
            1, 0, 0
    }
    }, {
            {
                    -1, 0, 0
            }, {
            1, -1, 0
    }
    }, {
            {
                    0, 0, -1
            }, {
            0, -1, 1
    }
    }, {
            {
                    0, -1, -1
            }, {
            0, 0, 1
    }
    }, {
            {
                    0, 0, 1
            }, {
            1, 0, 0
    }
    }, {
            {
                    0, 0, 1
            }, {
            -1, 0, 0
    }
    }, {
            {
                    0, 0, -1
            }, {
            -1, 0, 0
    }
    }, {
            {
                    0, 0, -1
            }, {
            1, 0, 0
    }
    }
    };
    public int field_9168_a;
    public int field_9167_b;
    public int field_477_ad;
    public int minecartType;
    public int field_9165_e;
    public double field_9164_f;
    public double field_9166_aj;
    private ItemStack cargoItems[];
    private boolean field_469_aj;
    private int field_9163_an;
    private double field_9162_ao;
    private double field_9161_ap;
    private double field_9160_aq;
    private double field_9159_ar;
    private double field_9158_as;

    public EntityMinecart(World world) {
        super(world);
        cargoItems = new ItemStack[36];
        field_9168_a = 0;
        field_9167_b = 0;
        field_477_ad = 1;
        field_469_aj = false;
        field_329_e = true;
        setSize(0.98F, 0.7F);
        yOffset = height / 2.0F;
        entityWalks = false;
    }

    public EntityMinecart(World world, double d, double d1, double d2,
                          int i) {
        this(world);
        setPosition(d, d1 + (double) yOffset, d2);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = d;
        prevPosY = d1;
        prevPosZ = d2;
        minecartType = i;
    }

    public AxisAlignedBB func_89_d(Entity entity) {
        return entity.boundingBox;
    }

    public AxisAlignedBB func_93_n() {
        return null;
    }

    public boolean func_124_r() {
        return true;
    }

    public double func_130_h() {
        return (double) height * 0.0D - 0.30000001192092896D;
    }

    public boolean attackEntity(Entity entity, int i) {
        if (worldObj.multiplayerWorld || isDead) {
            return true;
        }
        field_477_ad = -field_477_ad;
        field_9167_b = 10;
        func_9060_u();
        field_9168_a += i * 10;
        if (field_9168_a > 40) {
            dropItemWithOffset(Item.minecartEmpty.swiftedIndex, 1, 0, 0.0F);
            if (minecartType == 1) {
                dropItemWithOffset(Block.crate.blockID, 1, 0, 0.0F);
            } else if (minecartType == 2) {
                dropItemWithOffset(Block.stoneOvenIdle.blockID, 1, 0, 0.0F);
            }
            setEntityDead();
        }
        return true;
    }

    public boolean func_129_c_() {
        return !isDead;
    }

    public void setEntityDead() {
        label0:
        for (int i = 0; i < getInventorySize(); i++) {
            ItemStack itemstack = getStackInSlot(i);
            if (itemstack == null) {
                continue;
            }
            float f = random.nextFloat() * 0.8F + 0.1F;
            float f1 = random.nextFloat() * 0.8F + 0.1F;
            float f2 = random.nextFloat() * 0.8F + 0.1F;
            do {
                if (itemstack.stackSize <= 0) {
                    continue label0;
                }
                int j = random.nextInt(21) + 10;
                if (j > itemstack.stackSize) {
                    j = itemstack.stackSize;
                }
                itemstack.stackSize -= j;
                EntityItem entityitem = new EntityItem(worldObj, posX + (double) f, posY + (double) f1, posZ + (double) f2, new ItemStack(itemstack.itemID, j, itemstack.itemDamage));
                float f3 = 0.05F;
                entityitem.motionX = (float) random.nextGaussian() * f3;
                entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float) random.nextGaussian() * f3;
                worldObj.entityJoinedWorld(entityitem);
            } while (true);
        }

        super.setEntityDead();
    }

    public void onUpdate() {
        if (field_9167_b > 0) {
            field_9167_b--;
        }
        if (field_9168_a > 0) {
            field_9168_a--;
        }
        if (worldObj.multiplayerWorld && field_9163_an > 0) {
            if (field_9163_an > 0) {
                double d = posX + (field_9162_ao - posX) / (double) field_9163_an;
                double d1 = posY + (field_9161_ap - posY) / (double) field_9163_an;
                double d3 = posZ + (field_9160_aq - posZ) / (double) field_9163_an;
                double d4;
                for (d4 = field_9159_ar - (double) rotationYaw; d4 < -180D; d4 += 360D) {
                }
                for (; d4 >= 180D; d4 -= 360D) {
                }
                rotationYaw += d4 / (double) field_9163_an;
                rotationPitch += (field_9158_as - (double) rotationPitch) / (double) field_9163_an;
                field_9163_an--;
                setPosition(d, d1, d3);
                setRotation(rotationYaw, rotationPitch);
            } else {
                setPosition(posX, posY, posZ);
                setRotation(rotationYaw, rotationPitch);
            }
            return;
        }
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        motionY -= 0.039999999105930328D;
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY);
        int k = MathHelper.floor_double(posZ);
        if (worldObj.getBlockId(i, j - 1, k) == Block.minecartTrack.blockID) {
            j--;
        }
        double d2 = 0.40000000000000002D;
        boolean flag = false;
        double d5 = 0.0078125D;
        if (worldObj.getBlockId(i, j, k) == Block.minecartTrack.blockID) {
            Vec3D vec3d = func_182_g(posX, posY, posZ);
            int l = worldObj.getBlockMetadata(i, j, k);
            posY = j;
            if (l >= 2 && l <= 5) {
                posY = j + 1;
            }
            if (l == 2) {
                motionX -= d5;
            }
            if (l == 3) {
                motionX += d5;
            }
            if (l == 4) {
                motionZ += d5;
            }
            if (l == 5) {
                motionZ -= d5;
            }
            int ai[][] = field_468_ak[l];
            double d8 = ai[1][0] - ai[0][0];
            double d10 = ai[1][2] - ai[0][2];
            double d11 = Math.sqrt(d8 * d8 + d10 * d10);
            double d12 = motionX * d8 + motionZ * d10;
            if (d12 < 0.0D) {
                d8 = -d8;
                d10 = -d10;
            }
            double d13 = Math.sqrt(motionX * motionX + motionZ * motionZ);
            motionX = (d13 * d8) / d11;
            motionZ = (d13 * d10) / d11;
            double d16 = 0.0D;
            double d17 = (double) i + 0.5D + (double) ai[0][0] * 0.5D;
            double d18 = (double) k + 0.5D + (double) ai[0][2] * 0.5D;
            double d19 = (double) i + 0.5D + (double) ai[1][0] * 0.5D;
            double d20 = (double) k + 0.5D + (double) ai[1][2] * 0.5D;
            d8 = d19 - d17;
            d10 = d20 - d18;
            if (d8 == 0.0D) {
                posX = (double) i + 0.5D;
                d16 = posZ - (double) k;
            } else if (d10 == 0.0D) {
                posZ = (double) k + 0.5D;
                d16 = posX - (double) i;
            } else {
                double d21 = posX - d17;
                double d23 = posZ - d18;
                double d25 = (d21 * d8 + d23 * d10) * 2D;
                d16 = d25;
            }
            posX = d17 + d8 * d16;
            posZ = d18 + d10 * d16;
            setPosition(posX, posY + (double) yOffset, posZ);
            double d22 = motionX;
            double d24 = motionZ;
            if (field_328_f != null) {
                d22 *= 0.75D;
                d24 *= 0.75D;
            }
            if (d22 < -d2) {
                d22 = -d2;
            }
            if (d22 > d2) {
                d22 = d2;
            }
            if (d24 < -d2) {
                d24 = -d2;
            }
            if (d24 > d2) {
                d24 = d2;
            }
            moveEntity(d22, 0.0D, d24);
            if (ai[0][1] != 0 && MathHelper.floor_double(posX) - i == ai[0][0] && MathHelper.floor_double(posZ) - k == ai[0][2]) {
                setPosition(posX, posY + (double) ai[0][1], posZ);
            } else if (ai[1][1] != 0 && MathHelper.floor_double(posX) - i == ai[1][0] && MathHelper.floor_double(posZ) - k == ai[1][2]) {
                setPosition(posX, posY + (double) ai[1][1], posZ);
            }
            if (field_328_f != null) {
                motionX *= 0.99699997901916504D;
                motionY *= 0.0D;
                motionZ *= 0.99699997901916504D;
            } else {
                if (minecartType == 2) {
                    double d26 = MathHelper.sqrt_double(field_9164_f * field_9164_f + field_9166_aj * field_9166_aj);
                    if (d26 > 0.01D) {
                        flag = true;
                        field_9164_f /= d26;
                        field_9166_aj /= d26;
                        double d28 = 0.040000000000000001D;
                        motionX *= 0.80000001192092896D;
                        motionY *= 0.0D;
                        motionZ *= 0.80000001192092896D;
                        motionX += field_9164_f * d28;
                        motionZ += field_9166_aj * d28;
                    } else {
                        motionX *= 0.89999997615814209D;
                        motionY *= 0.0D;
                        motionZ *= 0.89999997615814209D;
                    }
                }
                motionX *= 0.95999997854232788D;
                motionY *= 0.0D;
                motionZ *= 0.95999997854232788D;
            }
            Vec3D vec3d1 = func_182_g(posX, posY, posZ);
            if (vec3d1 != null && vec3d != null) {
                double d27 = (vec3d.yCoord - vec3d1.yCoord) * 0.050000000000000003D;
                double d14 = Math.sqrt(motionX * motionX + motionZ * motionZ);
                if (d14 > 0.0D) {
                    motionX = (motionX / d14) * (d14 + d27);
                    motionZ = (motionZ / d14) * (d14 + d27);
                }
                setPosition(posX, vec3d1.yCoord, posZ);
            }
            int j1 = MathHelper.floor_double(posX);
            int k1 = MathHelper.floor_double(posZ);
            if (j1 != i || k1 != k) {
                double d15 = Math.sqrt(motionX * motionX + motionZ * motionZ);
                motionX = d15 * (double) (j1 - i);
                motionZ = d15 * (double) (k1 - k);
            }
            if (minecartType == 2) {
                double d29 = MathHelper.sqrt_double(field_9164_f * field_9164_f + field_9166_aj * field_9166_aj);
                if (d29 > 0.01D && motionX * motionX + motionZ * motionZ > 0.001D) {
                    field_9164_f /= d29;
                    field_9166_aj /= d29;
                    if (field_9164_f * motionX + field_9166_aj * motionZ < 0.0D) {
                        field_9164_f = 0.0D;
                        field_9166_aj = 0.0D;
                    } else {
                        field_9164_f = motionX;
                        field_9166_aj = motionZ;
                    }
                }
            }
        } else {
            if (motionX < -d2) {
                motionX = -d2;
            }
            if (motionX > d2) {
                motionX = d2;
            }
            if (motionZ < -d2) {
                motionZ = -d2;
            }
            if (motionZ > d2) {
                motionZ = d2;
            }
            if (onGround) {
                motionX *= 0.5D;
                motionY *= 0.5D;
                motionZ *= 0.5D;
            }
            moveEntity(motionX, motionY, motionZ);
            if (!onGround) {
                motionX *= 0.94999998807907104D;
                motionY *= 0.94999998807907104D;
                motionZ *= 0.94999998807907104D;
            }
        }
        rotationPitch = 0.0F;
        double d6 = prevPosX - posX;
        double d7 = prevPosZ - posZ;
        if (d6 * d6 + d7 * d7 > 0.001D) {
            rotationYaw = (float) ((Math.atan2(d7, d6) * 180D) / 3.1415926535897931D);
            if (field_469_aj) {
                rotationYaw += 180F;
            }
        }
        double d9;
        for (d9 = rotationYaw - prevRotationYaw; d9 >= 180D; d9 -= 360D) {
        }
        for (; d9 < -180D; d9 += 360D) {
        }
        if (d9 < -170D || d9 >= 170D) {
            rotationYaw += 180F;
            field_469_aj = !field_469_aj;
        }
        setRotation(rotationYaw, rotationPitch);
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expands(0.20000000298023224D, 0.0D, 0.20000000298023224D));
        if (list != null && list.size() > 0) {
            for (int i1 = 0; i1 < list.size(); i1++) {
                Entity entity = (Entity) list.get(i1);
                if (entity != field_328_f && entity.func_124_r() && (entity instanceof EntityMinecart)) {
                    entity.applyEntityCollision(this);
                }
            }

        }
        if (field_328_f != null && field_328_f.isDead) {
            field_328_f = null;
        }
        if (flag && random.nextInt(4) == 0) {
            field_9165_e--;
            if (field_9165_e < 0) {
                field_9164_f = field_9166_aj = 0.0D;
            }
            worldObj.spawnParticle("largesmoke", posX, posY + 0.80000000000000004D, posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    public Vec3D func_182_g(double d, double d1, double d2) {
        int i = MathHelper.floor_double(d);
        int j = MathHelper.floor_double(d1);
        int k = MathHelper.floor_double(d2);
        if (worldObj.getBlockId(i, j - 1, k) == Block.minecartTrack.blockID) {
            j--;
        }
        if (worldObj.getBlockId(i, j, k) == Block.minecartTrack.blockID) {
            int l = worldObj.getBlockMetadata(i, j, k);
            d1 = j;
            if (l >= 2 && l <= 5) {
                d1 = j + 1;
            }
            int ai[][] = field_468_ak[l];
            double d3 = 0.0D;
            double d4 = (double) i + 0.5D + (double) ai[0][0] * 0.5D;
            double d5 = (double) j + 0.5D + (double) ai[0][1] * 0.5D;
            double d6 = (double) k + 0.5D + (double) ai[0][2] * 0.5D;
            double d7 = (double) i + 0.5D + (double) ai[1][0] * 0.5D;
            double d8 = (double) j + 0.5D + (double) ai[1][1] * 0.5D;
            double d9 = (double) k + 0.5D + (double) ai[1][2] * 0.5D;
            double d10 = d7 - d4;
            double d11 = (d8 - d5) * 2D;
            double d12 = d9 - d6;
            if (d10 == 0.0D) {
                d = (double) i + 0.5D;
                d3 = d2 - (double) k;
            } else if (d12 == 0.0D) {
                d2 = (double) k + 0.5D;
                d3 = d - (double) i;
            } else {
                double d13 = d - d4;
                double d14 = d2 - d6;
                double d15 = (d13 * d10 + d14 * d12) * 2D;
                d3 = d15;
            }
            d = d4 + d10 * d3;
            d1 = d5 + d11 * d3;
            d2 = d6 + d12 * d3;
            if (d11 < 0.0D) {
                d1++;
            }
            if (d11 > 0.0D) {
                d1 += 0.5D;
            }
            return Vec3D.createVector(d, d1, d2);
        } else {
            return null;
        }
    }

    protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("Type", minecartType);
        if (minecartType == 2) {
            nbttagcompound.setDouble("PushX", field_9164_f);
            nbttagcompound.setDouble("PushZ", field_9166_aj);
            nbttagcompound.setShort("Fuel", (short) field_9165_e);
        } else if (minecartType == 1) {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < cargoItems.length; i++) {
                if (cargoItems[i] != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    cargoItems[i].writeToNBT(nbttagcompound1);
                    nbttaglist.setTag(nbttagcompound1);
                }
            }

            nbttagcompound.setTag("Items", nbttaglist);
        }
    }

    protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        minecartType = nbttagcompound.getInteger("Type");
        if (minecartType == 2) {
            field_9164_f = nbttagcompound.getDouble("PushX");
            field_9166_aj = nbttagcompound.getDouble("PushZ");
            field_9165_e = nbttagcompound.getShort("Fuel");
        } else if (minecartType == 1) {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
            cargoItems = new ItemStack[getInventorySize()];
            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if (j >= 0 && j < cargoItems.length) {
                    cargoItems[j] = new ItemStack(nbttagcompound1);
                }
            }

        }
    }

    public void applyEntityCollision(Entity entity) {
        if (worldObj.multiplayerWorld) {
            return;
        }
        if (entity == field_328_f) {
            return;
        }
        if ((entity instanceof EntityLiving) && !(entity instanceof EntityPlayer) && minecartType == 0 && motionX * motionX + motionZ * motionZ > 0.01D && field_328_f == null && entity.field_327_g == null) {
            entity.func_6094_e(this);
        }
        double d = entity.posX - posX;
        double d1 = entity.posZ - posZ;
        double d2 = d * d + d1 * d1;
        if (d2 >= 9.9999997473787516E-005D) {
            d2 = MathHelper.sqrt_double(d2);
            d /= d2;
            d1 /= d2;
            double d3 = 1.0D / d2;
            if (d3 > 1.0D) {
                d3 = 1.0D;
            }
            d *= d3;
            d1 *= d3;
            d *= 0.10000000149011612D;
            d1 *= 0.10000000149011612D;
            d *= 1.0F - field_286_P;
            d1 *= 1.0F - field_286_P;
            d *= 0.5D;
            d1 *= 0.5D;
            if (entity instanceof EntityMinecart) {
                double d4 = entity.motionX + motionX;
                double d5 = entity.motionZ + motionZ;
                if (((EntityMinecart) entity).minecartType == 2 && minecartType != 2) {
                    motionX *= 0.20000000298023224D;
                    motionZ *= 0.20000000298023224D;
                    addVelocity(entity.motionX - d, 0.0D, entity.motionZ - d1);
                    entity.motionX *= 0.69999998807907104D;
                    entity.motionZ *= 0.69999998807907104D;
                } else if (((EntityMinecart) entity).minecartType != 2 && minecartType == 2) {
                    entity.motionX *= 0.20000000298023224D;
                    entity.motionZ *= 0.20000000298023224D;
                    entity.addVelocity(motionX + d, 0.0D, motionZ + d1);
                    motionX *= 0.69999998807907104D;
                    motionZ *= 0.69999998807907104D;
                } else {
                    d4 /= 2D;
                    d5 /= 2D;
                    motionX *= 0.20000000298023224D;
                    motionZ *= 0.20000000298023224D;
                    addVelocity(d4 - d, 0.0D, d5 - d1);
                    entity.motionX *= 0.20000000298023224D;
                    entity.motionZ *= 0.20000000298023224D;
                    entity.addVelocity(d4 + d, 0.0D, d5 + d1);
                }
            } else {
                addVelocity(-d, 0.0D, -d1);
                entity.addVelocity(d / 4D, 0.0D, d1 / 4D);
            }
        }
    }

    public int getInventorySize() {
        return 27;
    }

    public ItemStack getStackInSlot(int i) {
        return cargoItems[i];
    }

    public boolean func_6092_a(EntityPlayer entityplayer) {
        if (minecartType == 0) {
            if (field_328_f != null && (field_328_f instanceof EntityPlayer) && field_328_f != entityplayer) {
                return true;
            }
            if (!worldObj.multiplayerWorld) {
                entityplayer.func_6094_e(this);
            }
        } else if (minecartType == 1) {
            entityplayer.func_166_a(this);
        } else if (minecartType == 2) {
            ItemStack itemstack = entityplayer.inventory.getCurrentItem();
            if (itemstack != null && itemstack.itemID == Item.coal.swiftedIndex) {
                if (--itemstack.stackSize == 0) {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }
                field_9165_e += 1200;
            }
            field_9164_f = posX - entityplayer.posX;
            field_9166_aj = posZ - entityplayer.posZ;
        }
        return true;
    }

}
