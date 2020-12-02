package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.cloth.file.GameruleManager;

public class BlockPumpkin extends Block {

    private boolean field_4086_a;

    protected BlockPumpkin(int i, int j, boolean flag) {
        super(i, Material.field_4213_w);
        blockIndexInTexture = j;
        setTickOnLoad(true);
        field_4086_a = flag;
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        GameruleManager gameruleman = GameruleManager.getInstance();
        if (gameruleman.getGamerule("dopumpkinlights", false) & l > 0 && Block.blocksList[l].canProvidePower() && world.isBlockIndirectlyGettingPowered(i, j, k)) {
            world.setBlockWithNotify(i, j, k, Block.jackolantern.blockID);
        }
        if (gameruleman.getGamerule("dopumpkinlights", false) & l > 0 && Block.blocksList[l].canProvidePower() && !world.isBlockIndirectlyGettingPowered(i, j, k)) {
            world.setBlockWithNotify(i, j, k, Block.pumpkin.blockID);
        }
    }

    public int getBlockTextureFromSide(int i) {
        if (i == 1) {
            return blockIndexInTexture;
        }
        if (i == 0) {
            return blockIndexInTexture;
        }
        if (i == 3) {
            return blockIndexInTexture + 1 + 16;
        } else {
            return blockIndexInTexture + 16;
        }
    }

    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        int l = world.getBlockId(i, j, k);
        return (l == 0 || Block.blocksList[l].blockMaterial.getIsLiquid()) && world.doesBlockAllowAttachment(i, j - 1, k);
    }

    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
        int l = MathHelper.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        world.setBlockMetadataWithNotify(i, j, k, l);
    }
}
