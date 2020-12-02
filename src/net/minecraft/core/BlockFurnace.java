package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockFurnace extends BlockContainer {

    private final boolean field_655_a;

    protected BlockFurnace(int i, boolean flag) {
        super(i, Material.rock);
        field_655_a = flag;
        blockIndexInTexture = 45;
    }

    public static void func_295_a(boolean flag, World world, int i, int j, int k) {
        int l = world.getBlockMetadata(i, j, k);
        TileEntity tileentity = world.getBlock(i, j, k);
        if (flag) {
            world.setBlockWithNotify(i, j, k, Block.stoneOvenActive.blockID);
        } else {
            world.setBlockWithNotify(i, j, k, Block.stoneOvenIdle.blockID);
        }
        world.setBlockMetadataWithNotify(i, j, k, l);
        world.func_473_a(i, j, k, tileentity);
    }

    public int idDropped(int i, Random random) {
        return Block.stoneOvenIdle.blockID;
    }

    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
        func_296_g(world, i, j, k);
    }

    private void func_296_g(World world, int i, int j, int k) {
        int l = world.getBlockId(i, j, k - 1);
        int i1 = world.getBlockId(i, j, k + 1);
        int j1 = world.getBlockId(i - 1, j, k);
        int k1 = world.getBlockId(i + 1, j, k);
        byte byte0 = 3;
        if (Block.field_540_p[l] && !Block.field_540_p[i1]) {
            byte0 = 3;
        }
        if (Block.field_540_p[i1] && !Block.field_540_p[l]) {
            byte0 = 2;
        }
        if (Block.field_540_p[j1] && !Block.field_540_p[k1]) {
            byte0 = 5;
        }
        if (Block.field_540_p[k1] && !Block.field_540_p[j1]) {
            byte0 = 4;
        }
        world.setBlockMetadataWithNotify(i, j, k, byte0);
    }

    public int getBlockTextureFromSide(int i) {
        if (i == 1) {
            return Block.stone.blockID;
        }
        if (i == 0) {
            return Block.stone.blockID;
        }
        if (i == 3) {
            return blockIndexInTexture - 1;
        } else {
            return blockIndexInTexture;
        }
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        TileEntityFurnace tileentityfurnace = (TileEntityFurnace) world.getBlock(i, j, k);
        entityplayer.func_170_a(tileentityfurnace);
        return true;
    }

    protected TileEntity func_294_a_() {
        return new TileEntityFurnace();
    }

    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
        int l = MathHelper.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        if (l == 0) {
            world.setBlockMetadataWithNotify(i, j, k, 2);
        }
        if (l == 1) {
            world.setBlockMetadataWithNotify(i, j, k, 5);
        }
        if (l == 2) {
            world.setBlockMetadataWithNotify(i, j, k, 3);
        }
        if (l == 3) {
            world.setBlockMetadataWithNotify(i, j, k, 4);
        }
    }
}
