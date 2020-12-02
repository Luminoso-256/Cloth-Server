package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;

public class BlockFence extends Block {

    public BlockFence(int i, int j) {
        super(i, j, Material.wood);
    }

    public void getCollidingBoundingBoxes(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, ArrayList arraylist) {
        arraylist.add(AxisAlignedBB.getBoundingBoxFromPool(i, j, k, i + 1, (double) j + 1.5D, k + 1));
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        if (world.getBlockId(i, j - 1, k) == blockID) {
            return false;
        }
        if (!world.getBlockMaterial(i, j - 1, k).func_216_a()) {
            return false;
        } else {
            return super.canPlaceBlockAt(world, i, j, k);
        }
    }

    public boolean allowsAttachment() {
        return false;
    }
}
