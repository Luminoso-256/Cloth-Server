package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockRedstoneWire extends Block {

    private boolean field_652_a;

    public BlockRedstoneWire(int i, int j) {
        super(i, j, Material.circuits);
        field_652_a = true;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    }

    public static boolean func_293_b(IBlockAccess iblockaccess, int i, int j, int k) {
        int l = iblockaccess.getBlockId(i, j, k);
        if (l == Block.redstoneWire.blockID) {
            return true;
        }
        if (l == 0) {
            return false;
        }
        return Block.blocksList[l].canProvidePower();
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
    }

    public boolean allowsAttachment() {
        return false;
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        return world.doesBlockAllowAttachment(i, j - 1, k);
    }

    private void func_292_g(World world, int i, int j, int k) {
        int l = world.getBlockMetadata(i, j, k);
        int i1 = 0;
        field_652_a = false;
        boolean flag = world.isBlockIndirectlyGettingPowered(i, j, k);
        field_652_a = true;
        if (flag) {
            i1 = 15;
        } else {
            for (int j1 = 0; j1 < 4; j1++) {
                int l1 = i;
                int j2 = k;
                if (j1 == 0) {
                    l1--;
                }
                if (j1 == 1) {
                    l1++;
                }
                if (j1 == 2) {
                    j2--;
                }
                if (j1 == 3) {
                    j2++;
                }
                i1 = func_290_f(world, l1, j, j2, i1);
                if (world.doesBlockAllowAttachment(l1, j, j2) && !world.doesBlockAllowAttachment(i, j + 1, k)) {
                    i1 = func_290_f(world, l1, j + 1, j2, i1);
                    continue;
                }
                if (!world.doesBlockAllowAttachment(l1, j, j2)) {
                    i1 = func_290_f(world, l1, j - 1, j2, i1);
                }
            }

            if (i1 > 0) {
                i1--;
            } else {
                i1 = 0;
            }
        }
        if (l != i1) {
            world.setBlockMetadataWithNotify(i, j, k, i1);
            world.func_519_b(i, j, k, i, j, k);
            if (i1 > 0) {
                i1--;
            }
            for (int k1 = 0; k1 < 4; k1++) {
                int i2 = i;
                int k2 = k;
                int l2 = j - 1;
                if (k1 == 0) {
                    i2--;
                }
                if (k1 == 1) {
                    i2++;
                }
                if (k1 == 2) {
                    k2--;
                }
                if (k1 == 3) {
                    k2++;
                }
                if (world.doesBlockAllowAttachment(i2, j, k2)) {
                    l2 += 2;
                }
                int i3 = func_290_f(world, i2, j, k2, -1);
                if (i3 >= 0 && i3 != i1) {
                    func_292_g(world, i2, j, k2);
                }
                i3 = func_290_f(world, i2, l2, k2, -1);
                if (i3 >= 0 && i3 != i1) {
                    func_292_g(world, i2, l2, k2);
                }
            }

            if (l == 0 || i1 == 0) {
                world.notifyBlocksOfNeighborChange(i, j, k, blockID);
                world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
                world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
                world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
                world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
                world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
                world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
            }
        }
    }

    private void func_291_h(World world, int i, int j, int k) {
        if (world.getBlockId(i, j, k) != blockID) {
            return;
        } else {
            world.notifyBlocksOfNeighborChange(i, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
            world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
            world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
            return;
        }
    }

    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
        if (world.multiplayerWorld) {
            return;
        }
        func_292_g(world, i, j, k);
        world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
        world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        func_291_h(world, i - 1, j, k);
        func_291_h(world, i + 1, j, k);
        func_291_h(world, i, j, k - 1);
        func_291_h(world, i, j, k + 1);
        if (world.doesBlockAllowAttachment(i - 1, j, k)) {
            func_291_h(world, i - 1, j + 1, k);
        } else {
            func_291_h(world, i - 1, j - 1, k);
        }
        if (world.doesBlockAllowAttachment(i + 1, j, k)) {
            func_291_h(world, i + 1, j + 1, k);
        } else {
            func_291_h(world, i + 1, j - 1, k);
        }
        if (world.doesBlockAllowAttachment(i, j, k - 1)) {
            func_291_h(world, i, j + 1, k - 1);
        } else {
            func_291_h(world, i, j - 1, k - 1);
        }
        if (world.doesBlockAllowAttachment(i, j, k + 1)) {
            func_291_h(world, i, j + 1, k + 1);
        } else {
            func_291_h(world, i, j - 1, k + 1);
        }
    }

    public void onBlockRemoval(World world, int i, int j, int k) {
        super.onBlockRemoval(world, i, j, k);
        if (world.multiplayerWorld) {
            return;
        }
        world.notifyBlocksOfNeighborChange(i, j + 1, k, blockID);
        world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        func_292_g(world, i, j, k);
        func_291_h(world, i - 1, j, k);
        func_291_h(world, i + 1, j, k);
        func_291_h(world, i, j, k - 1);
        func_291_h(world, i, j, k + 1);
        if (world.doesBlockAllowAttachment(i - 1, j, k)) {
            func_291_h(world, i - 1, j + 1, k);
        } else {
            func_291_h(world, i - 1, j - 1, k);
        }
        if (world.doesBlockAllowAttachment(i + 1, j, k)) {
            func_291_h(world, i + 1, j + 1, k);
        } else {
            func_291_h(world, i + 1, j - 1, k);
        }
        if (world.doesBlockAllowAttachment(i, j, k - 1)) {
            func_291_h(world, i, j + 1, k - 1);
        } else {
            func_291_h(world, i, j - 1, k - 1);
        }
        if (world.doesBlockAllowAttachment(i, j, k + 1)) {
            func_291_h(world, i, j + 1, k + 1);
        } else {
            func_291_h(world, i, j - 1, k + 1);
        }
    }

    private int func_290_f(World world, int i, int j, int k, int l) {
        if (world.getBlockId(i, j, k) != blockID) {
            return l;
        }
        int i1 = world.getBlockMetadata(i, j, k);
        if (i1 > l) {
            return i1;
        } else {
            return l;
        }
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        if (world.multiplayerWorld) {
            return;
        }
        int i1 = world.getBlockMetadata(i, j, k);
        boolean flag = canPlaceBlockAt(world, i, j, k);
        if (!flag) {
            dropBlockAsItem(world, i, j, k, i1);
            world.setBlockWithNotify(i, j, k, 0);
        } else {
            func_292_g(world, i, j, k);
        }
        super.onNeighborBlockChange(world, i, j, k, l);
    }

    public int idDropped(int i, Random random) {
        return Item.redstone.swiftedIndex;
    }

    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l) {
        if (!field_652_a) {
            return false;
        } else {
            return isPoweringTo(world, i, j, k, l);
        }
    }

    public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        if (!field_652_a) {
            return false;
        }
        if (iblockaccess.getBlockMetadata(i, j, k) == 0) {
            return false;
        }
        if (l == 1) {
            return true;
        }
        boolean flag = func_293_b(iblockaccess, i - 1, j, k) || !iblockaccess.doesBlockAllowAttachment(i - 1, j, k) && func_293_b(iblockaccess, i - 1, j - 1, k);
        boolean flag1 = func_293_b(iblockaccess, i + 1, j, k) || !iblockaccess.doesBlockAllowAttachment(i + 1, j, k) && func_293_b(iblockaccess, i + 1, j - 1, k);
        boolean flag2 = func_293_b(iblockaccess, i, j, k - 1) || !iblockaccess.doesBlockAllowAttachment(i, j, k - 1) && func_293_b(iblockaccess, i, j - 1, k - 1);
        boolean flag3 = func_293_b(iblockaccess, i, j, k + 1) || !iblockaccess.doesBlockAllowAttachment(i, j, k + 1) && func_293_b(iblockaccess, i, j - 1, k + 1);
        if (!iblockaccess.doesBlockAllowAttachment(i, j + 1, k)) {
            if (iblockaccess.doesBlockAllowAttachment(i - 1, j, k) && func_293_b(iblockaccess, i - 1, j + 1, k)) {
                flag = true;
            }
            if (iblockaccess.doesBlockAllowAttachment(i + 1, j, k) && func_293_b(iblockaccess, i + 1, j + 1, k)) {
                flag1 = true;
            }
            if (iblockaccess.doesBlockAllowAttachment(i, j, k - 1) && func_293_b(iblockaccess, i, j + 1, k - 1)) {
                flag2 = true;
            }
            if (iblockaccess.doesBlockAllowAttachment(i, j, k + 1) && func_293_b(iblockaccess, i, j + 1, k + 1)) {
                flag3 = true;
            }
        }
        if (!flag2 && !flag1 && !flag && !flag3 && l >= 2 && l <= 5) {
            return true;
        }
        if (l == 2 && flag2 && !flag && !flag1) {
            return true;
        }
        if (l == 3 && flag3 && !flag && !flag1) {
            return true;
        }
        if (l == 4 && flag && !flag2 && !flag3) {
            return true;
        }
        return l == 5 && flag1 && !flag2 && !flag3;
    }

    public boolean canProvidePower() {
        return field_652_a;
    }
}
