package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class ItemBucket extends Item {

    private int field_274_a;

    public ItemBucket(int i, int j) {
        super(i);
        maxStackSize = 1;
        maxDamage = 64;
        field_274_a = j;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        float f = 1.0F;
        float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double) f;
        double d1 = (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double) f + 1.6200000000000001D) - (double) entityplayer.yOffset;
        double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double) f;
        Vec3D vec3d = Vec3D.createVector(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d3 = 5D;
        Vec3D vec3d1 = vec3d.addVector((double) f7 * d3, (double) f8 * d3, (double) f9 * d3);
        MovingObjectPosition movingobjectposition = world.func_505_a(vec3d, vec3d1, field_274_a == 0);
        if (movingobjectposition == null) {
            return itemstack;
        }
        if (movingobjectposition.typeOfHit == 0) {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
            if (!world.func_6157_a(entityplayer, i, j, k)) {
                return itemstack;
            }
            if (field_274_a == 0) {
                if (world.getBlockMaterial(i, j, k) == Material.water && world.getBlockMetadata(i, j, k) == 0) {
                    world.setBlockWithNotify(i, j, k, 0);
                    return new ItemStack(Item.bucketWater);
                }
                if (world.getBlockMaterial(i, j, k) == Material.lava && world.getBlockMetadata(i, j, k) == 0) {
                    world.setBlockWithNotify(i, j, k, 0);
                    return new ItemStack(Item.bucketLava);
                }
            } else {
                if (field_274_a < 0) {
                    return new ItemStack(Item.bucketEmpty);
                }
                if (movingobjectposition.sideHit == 0) {
                    j--;
                }
                if (movingobjectposition.sideHit == 1) {
                    j++;
                }
                if (movingobjectposition.sideHit == 2) {
                    k--;
                }
                if (movingobjectposition.sideHit == 3) {
                    k++;
                }
                if (movingobjectposition.sideHit == 4) {
                    i--;
                }
                if (movingobjectposition.sideHit == 5) {
                    i++;
                }
                if (world.getBlockId(i, j, k) == 0 || !world.getBlockMaterial(i, j, k).func_216_a()) {
                    if (world.worldProvider.field_6166_d && field_274_a == Block.waterStill.blockID) {
                        world.playSoundEffect(d + 0.5D, d1 + 0.5D, d2 + 0.5D, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
                        for (int l = 0; l < 8; l++) {
                            world.spawnParticle("largesmoke", (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0D, 0.0D, 0.0D);
                        }

                    } else {
                        world.func_507_b(i, j, k, field_274_a, 0);
                    }
                    return new ItemStack(Item.bucketEmpty);
                }
            }
        } else if (field_274_a == 0 && (movingobjectposition.entityHit instanceof EntityCow)) {
            return new ItemStack(Item.bucketMilk);
        }
        return itemstack;
    }
}
