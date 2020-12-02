package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockChest extends BlockContainer {

    private Random field_656_a;

    protected BlockChest(int i) {
        super(i, Material.wood);
        field_656_a = new Random();
        blockIndexInTexture = 26;
    }

    public int getBlockTextureFromSide(int i) {
        if (i == 1) {
            return blockIndexInTexture - 1;
        }
        if (i == 0) {
            return blockIndexInTexture - 1;
        }
        if (i == 3) {
            return blockIndexInTexture + 1;
        } else {
            return blockIndexInTexture;
        }
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        int l = 0;
        if (world.getBlockId(i - 1, j, k) == blockID) {
            l++;
        }
        if (world.getBlockId(i + 1, j, k) == blockID) {
            l++;
        }
        if (world.getBlockId(i, j, k - 1) == blockID) {
            l++;
        }
        if (world.getBlockId(i, j, k + 1) == blockID) {
            l++;
        }
        if (l > 1) {
            return false;
        }
        if (isThereANeighborChest(world, i - 1, j, k)) {
            return false;
        }
        if (isThereANeighborChest(world, i + 1, j, k)) {
            return false;
        }
        if (isThereANeighborChest(world, i, j, k - 1)) {
            return false;
        }
        return !isThereANeighborChest(world, i, j, k + 1);
    }

    private boolean isThereANeighborChest(World world, int i, int j, int k) {
        if (world.getBlockId(i, j, k) != blockID) {
            return false;
        }
        if (world.getBlockId(i - 1, j, k) == blockID) {
            return true;
        }
        if (world.getBlockId(i + 1, j, k) == blockID) {
            return true;
        }
        if (world.getBlockId(i, j, k - 1) == blockID) {
            return true;
        }
        return world.getBlockId(i, j, k + 1) == blockID;
    }

    public void onBlockRemoval(World world, int i, int j, int k) {
        TileEntityChest tileentitychest = (TileEntityChest) world.getBlock(i, j, k);
        label0:
        for (int l = 0; l < tileentitychest.getInventorySize(); l++) {
            ItemStack itemstack = tileentitychest.getStackInSlot(l);
            if (itemstack == null) {
                continue;
            }
            float f = field_656_a.nextFloat() * 0.8F + 0.1F;
            float f1 = field_656_a.nextFloat() * 0.8F + 0.1F;
            float f2 = field_656_a.nextFloat() * 0.8F + 0.1F;
            do {
                if (itemstack.stackSize <= 0) {
                    continue label0;
                }
                int i1 = field_656_a.nextInt(21) + 10;
                if (i1 > itemstack.stackSize) {
                    i1 = itemstack.stackSize;
                }
                itemstack.stackSize -= i1;
                EntityItem entityitem = new EntityItem(world, (float) i + f, (float) j + f1, (float) k + f2, new ItemStack(itemstack.itemID, i1, itemstack.itemDamage));
                float f3 = 0.05F;
                entityitem.motionX = (float) field_656_a.nextGaussian() * f3;
                entityitem.motionY = (float) field_656_a.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float) field_656_a.nextGaussian() * f3;
                world.entityJoinedWorld(entityitem);
            } while (true);
        }

        super.onBlockRemoval(world, i, j, k);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        Object obj = (TileEntityChest) world.getBlock(i, j, k);
        if (world.doesBlockAllowAttachment(i, j + 1, k)) {
            return true;
        }
        if (world.getBlockId(i - 1, j, k) == blockID && world.doesBlockAllowAttachment(i - 1, j + 1, k)) {
            return true;
        }
        if (world.getBlockId(i + 1, j, k) == blockID && world.doesBlockAllowAttachment(i + 1, j + 1, k)) {
            return true;
        }
        if (world.getBlockId(i, j, k - 1) == blockID && world.doesBlockAllowAttachment(i, j + 1, k - 1)) {
            return true;
        }
        if (world.getBlockId(i, j, k + 1) == blockID && world.doesBlockAllowAttachment(i, j + 1, k + 1)) {
            return true;
        }
        if (world.getBlockId(i - 1, j, k) == blockID) {
            obj = new InventoryLargeChest("Large chest", (TileEntityChest) world.getBlock(i - 1, j, k), ((IInventory) (obj)));
        }
        if (world.getBlockId(i + 1, j, k) == blockID) {
            obj = new InventoryLargeChest("Large chest", ((IInventory) (obj)), (TileEntityChest) world.getBlock(i + 1, j, k));
        }
        if (world.getBlockId(i, j, k - 1) == blockID) {
            obj = new InventoryLargeChest("Large chest", (TileEntityChest) world.getBlock(i, j, k - 1), ((IInventory) (obj)));
        }
        if (world.getBlockId(i, j, k + 1) == blockID) {
            obj = new InventoryLargeChest("Large chest", ((IInventory) (obj)), (TileEntityChest) world.getBlock(i, j, k + 1));
        }
        entityplayer.func_166_a(((IInventory) (obj)));
        return true;
    }

    protected TileEntity func_294_a_() {
        return new TileEntityChest();
    }
}
