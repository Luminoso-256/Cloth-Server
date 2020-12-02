package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;
import java.util.Random;

public class BlockStairs extends Block {

    private Block field_651_a;

    protected BlockStairs(int i, Block block) {
        super(i, block.blockIndexInTexture, block.blockMaterial);
        field_651_a = block;
        setHardness(block.blockHardness);
        setResistance(block.blockResistance / 3F);
        setStepSound(block.stepSound);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return super.getCollisionBoundingBoxFromPool(world, i, j, k);
    }

    public boolean allowsAttachment() {
        return false;
    }

    public boolean isSideInsideCoordinate(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return super.isSideInsideCoordinate(iblockaccess, i, j, k, l);
    }

    public void getCollidingBoundingBoxes(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, ArrayList arraylist) {
        int l = world.getBlockMetadata(i, j, k);
        if (l == 0) {
            setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
            super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
            setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
        } else if (l == 1) {
            setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
            super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
            setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
        } else if (l == 2) {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
            super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
            setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
            super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
        } else if (l == 3) {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
            super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
            setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
            super.getCollidingBoundingBoxes(world, i, j, k, axisalignedbb, arraylist);
        }
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
        field_651_a.onBlockClicked(world, i, j, k, entityplayer);
    }

    public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l) {
        field_651_a.onBlockDestroyedByPlayer(world, i, j, k, l);
    }

    public float func_226_a(Entity entity) {
        return field_651_a.func_226_a(entity);
    }

    public int idDropped(int i, Random random) {
        return field_651_a.idDropped(i, random);
    }

    public int quantityDropped(Random random) {
        return field_651_a.quantityDropped(random);
    }

    public int getBlockTextureFromSide(int i) {
        return field_651_a.getBlockTextureFromSide(i);
    }

    public int tickRate() {
        return field_651_a.tickRate();
    }

    public void velocityToAddToEntity(World world, int i, int j, int k, Entity entity, Vec3D vec3d) {
        field_651_a.velocityToAddToEntity(world, i, j, k, entity, vec3d);
    }

    public boolean isCollidable() {
        return field_651_a.isCollidable();
    }

    public boolean canCollideCheck(int i, boolean flag) {
        return field_651_a.canCollideCheck(i, flag);
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        return field_651_a.canPlaceBlockAt(world, i, j, k);
    }

    public void onBlockAdded(World world, int i, int j, int k) {
        onNeighborBlockChange(world, i, j, k, 0);
        field_651_a.onBlockAdded(world, i, j, k);
    }

    public void onBlockRemoval(World world, int i, int j, int k) {
        field_651_a.onBlockRemoval(world, i, j, k);
    }

    public void dropBlockAsItemWithChance(World world, int i, int j, int k, int l, float f) {
        field_651_a.dropBlockAsItemWithChance(world, i, j, k, l, f);
    }

    public void dropBlockAsItem(World world, int i, int j, int k, int l) {
        field_651_a.dropBlockAsItem(world, i, j, k, l);
    }

    public void onEntityWalking(World world, int i, int j, int k, Entity entity) {
        field_651_a.onEntityWalking(world, i, j, k, entity);
    }

    public void updateTick(World world, int i, int j, int k, Random random) {
        field_651_a.updateTick(world, i, j, k, random);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        return field_651_a.blockActivated(world, i, j, k, entityplayer);
    }

    public void onBlockDestroyedByExplosion(World world, int i, int j, int k) {
        field_651_a.onBlockDestroyedByExplosion(world, i, j, k);
    }

    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
        int l = MathHelper.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        if (l == 0) {
            world.setBlockMetadataWithNotify(i, j, k, 2);
        }
        if (l == 1) {
            world.setBlockMetadataWithNotify(i, j, k, 1);
        }
        if (l == 2) {
            world.setBlockMetadataWithNotify(i, j, k, 3);
        }
        if (l == 3) {
            world.setBlockMetadataWithNotify(i, j, k, 0);
        }
    }
}
