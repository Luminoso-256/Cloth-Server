package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

public class BlockLever extends Block {

    protected BlockLever(int i, int j) {
        super(i, j, Material.circuits);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
    }

    public boolean allowsAttachment() {
        return false;
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        if (world.doesBlockAllowAttachment(i - 1, j, k)) {
            return true;
        }
        if (world.doesBlockAllowAttachment(i + 1, j, k)) {
            return true;
        }
        if (world.doesBlockAllowAttachment(i, j, k - 1)) {
            return true;
        }
        if (world.doesBlockAllowAttachment(i, j, k + 1)) {
            return true;
        }
        return world.doesBlockAllowAttachment(i, j - 1, k);
    }

    public void onBlockPlaced(World world, int i, int j, int k, int l) {
        int i1 = world.getBlockMetadata(i, j, k);
        int j1 = i1 & 8;
        i1 &= 7;
        if (l == 1 && world.doesBlockAllowAttachment(i, j - 1, k)) {
            i1 = 5 + world.rand.nextInt(2);
        }
        if (l == 2 && world.doesBlockAllowAttachment(i, j, k + 1)) {
            i1 = 4;
        }
        if (l == 3 && world.doesBlockAllowAttachment(i, j, k - 1)) {
            i1 = 3;
        }
        if (l == 4 && world.doesBlockAllowAttachment(i + 1, j, k)) {
            i1 = 2;
        }
        if (l == 5 && world.doesBlockAllowAttachment(i - 1, j, k)) {
            i1 = 1;
        }
        world.setBlockMetadataWithNotify(i, j, k, i1 + j1);
    }

    public void onBlockAdded(World world, int i, int j, int k) {
        if (world.doesBlockAllowAttachment(i - 1, j, k)) {
            world.setBlockMetadataWithNotify(i, j, k, 1);
        } else if (world.doesBlockAllowAttachment(i + 1, j, k)) {
            world.setBlockMetadataWithNotify(i, j, k, 2);
        } else if (world.doesBlockAllowAttachment(i, j, k - 1)) {
            world.setBlockMetadataWithNotify(i, j, k, 3);
        } else if (world.doesBlockAllowAttachment(i, j, k + 1)) {
            world.setBlockMetadataWithNotify(i, j, k, 4);
        } else if (world.doesBlockAllowAttachment(i, j - 1, k)) {
            world.setBlockMetadataWithNotify(i, j, k, 5 + world.rand.nextInt(2));
        }
        func_274_g(world, i, j, k);
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        if (func_274_g(world, i, j, k)) {
            int i1 = world.getBlockMetadata(i, j, k) & 7;
            boolean flag = false;
            if (!world.doesBlockAllowAttachment(i - 1, j, k) && i1 == 1) {
                flag = true;
            }
            if (!world.doesBlockAllowAttachment(i + 1, j, k) && i1 == 2) {
                flag = true;
            }
            if (!world.doesBlockAllowAttachment(i, j, k - 1) && i1 == 3) {
                flag = true;
            }
            if (!world.doesBlockAllowAttachment(i, j, k + 1) && i1 == 4) {
                flag = true;
            }
            if (!world.doesBlockAllowAttachment(i, j - 1, k) && i1 == 5) {
                flag = true;
            }
            if (flag) {
                dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
                world.setBlockWithNotify(i, j, k, 0);
            }
        }
    }

    private boolean func_274_g(World world, int i, int j, int k) {
        if (!canPlaceBlockAt(world, i, j, k)) {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
            return false;
        } else {
            return true;
        }
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
        int l = iblockaccess.getBlockMetadata(i, j, k) & 7;
        float f = 0.1875F;
        if (l == 1) {
            setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        } else if (l == 2) {
            setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        } else if (l == 3) {
            setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        } else if (l == 4) {
            setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        } else {
            float f1 = 0.25F;
            setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, 0.6F, 0.5F + f1);
        }
    }

    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer) {
        blockActivated(world, i, j, k, entityplayer);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        if (world.multiplayerWorld) {
            return true;
        }
        int l = world.getBlockMetadata(i, j, k);
        int i1 = l & 7;
        int j1 = 8 - (l & 8);
        world.setBlockMetadataWithNotify(i, j, k, i1 + j1);
        world.func_519_b(i, j, k, i, j, k);
        world.playSoundEffect((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D, "random.click", 0.3F, j1 <= 0 ? 0.5F : 0.6F);
        world.notifyBlocksOfNeighborChange(i, j, k, blockID);
        if (i1 == 1) {
            world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
        } else if (i1 == 2) {
            world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
        } else if (i1 == 3) {
            world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
        } else if (i1 == 4) {
            world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
        } else {
            world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        }
        return true;
    }

    public void onBlockRemoval(World world, int i, int j, int k) {
        int l = world.getBlockMetadata(i, j, k);
        if ((l & 8) > 0) {
            world.notifyBlocksOfNeighborChange(i, j, k, blockID);
            int i1 = l & 7;
            if (i1 == 1) {
                world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
            } else if (i1 == 2) {
                world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
            } else if (i1 == 3) {
                world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
            } else if (i1 == 4) {
                world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
            } else {
                world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
            }
        }
        super.onBlockRemoval(world, i, j, k);
    }

    public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return (iblockaccess.getBlockMetadata(i, j, k) & 8) > 0;
    }

    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l) {
        int i1 = world.getBlockMetadata(i, j, k);
        if ((i1 & 8) == 0) {
            return false;
        }
        int j1 = i1 & 7;
        if (j1 == 5 && l == 1) {
            return true;
        }
        if (j1 == 4 && l == 2) {
            return true;
        }
        if (j1 == 3 && l == 3) {
            return true;
        }
        if (j1 == 2 && l == 4) {
            return true;
        }
        return j1 == 1 && l == 5;
    }

    public boolean canProvidePower() {
        return true;
    }
}
