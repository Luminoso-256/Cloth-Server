package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockSnow extends Block {

    protected BlockSnow(int i, int j) {
        super(i, j, Material.snow);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        setTickOnLoad(true);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
    }

    public boolean allowsAttachment() {
        return false;
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        int l = world.getBlockId(i, j - 1, k);
        if (l == 0 || !Block.blocksList[l].allowsAttachment()) {
            return false;
        } else {
            return world.getBlockMaterial(i, j - 1, k).func_218_c();
        }
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        func_275_g(world, i, j, k);
    }

    private boolean func_275_g(World world, int i, int j, int k) {
        if (!canPlaceBlockAt(world, i, j, k)) {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
            return false;
        } else {
            return true;
        }
    }

    public void func_12007_g(World world, int i, int j, int k, int l) {
        int i1 = Item.snowball.swiftedIndex;
        float f = 0.7F;
        double d = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
        double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
        double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
        EntityItem entityitem = new EntityItem(world, (double) i + d, (double) j + d1, (double) k + d2, new ItemStack(i1));
        entityitem.field_433_ad = 10;
        world.entityJoinedWorld(entityitem);
        world.setBlockWithNotify(i, j, k, 0);
    }

    public int idDropped(int i, Random random) {
        return Item.snowball.swiftedIndex;
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    public void updateTick(World world, int i, int j, int k, Random random) {
        if (world.getSavedLightValue(EnumSkyBlock.Block, i, j, k) > 11) {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    public boolean isSideInsideCoordinate(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        Material material = iblockaccess.getBlockMaterial(i, j, k);
        if (l == 1) {
            return true;
        }
        if (material == blockMaterial) {
            return false;
        } else {
            return super.isSideInsideCoordinate(iblockaccess, i, j, k, l);
        }
    }
}
