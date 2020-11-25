package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.clothutils.GameruleManager;

import java.io.File;
import java.util.Random;

public class BlockLeaves extends BlockLeavesBase
{

    protected BlockLeaves(int i, int j)
    {
        super(i, j, Material.leaves, false);
        baseIndexInPNG = j;
        setTickOnLoad(true);
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        super.onNeighborBlockChange(world, i, j, k, l);
        return;
    }
    
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        leafDecay(world, i, j, k);
        return;
    }
    
    private void leafDecay(World world, int i, int j, int k) {
    	if(counter == 0) {
    		counter = counterReset;
    		if (world.checkChunksExist(i - rangeP1, j - rangeP1, k - rangeP1, i + rangeP1, j + rangeP1, k + rangeP1)) {
        		for(int z = -decayRange; z <= decayRange; z++) {
        			int radius = decayRange - Math.abs(z);
        			if(scanLayerForLog(world, i, j + z, k, radius)) return;
        		}
        		removeLeaves(world, i, j, k);
        	}
    	} else {
    		counter--;
    	}
    }

    private boolean scanLayerForLog(World world, int i, int j, int k, int radius) {
    	for(int x = -radius; x <= radius; x++) {
    		for(int y = -radius; y <= radius; y++) {
    			if(Math.abs(x) + Math.abs(y) > radius) continue;
    			if(world.getBlockId(i + x, j, k + y) == 17) return true;
    		}
    	}
    	return false;
    }
    
    private void removeLeaves(World world, int i, int j, int k)
    {
        if(shouldDecay == null){
            PropertyManager propertyManager = new PropertyManager(new File("server.properties"));
            boolean shouldDecayBool = propertyManager.getBooleanProperty("doleafdecay", false);
            if(shouldDecayBool){shouldDecay = "true";}else{shouldDecay = "false";}
        }
        else if (shouldDecay  == "true") {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    public int quantityDropped(Random random)
    {
        return random.nextInt(20) != 0 ? 0 : 1;
    }

    public int idDropped(int i, Random random)
    {
        return Block.sapling.blockID;
    }

    public boolean allowsAttachment()
    {
        return !graphicsLevel;
    }

    public void onEntityWalking(World world, int i, int j, int k, Entity entity)
    {
        super.onEntityWalking(world, i, j, k, entity);
    }

    int counterReset = 2;
    private int counter = counterReset;
    private int baseIndexInPNG;
    int decayRange = 4;
	int rangeP1 = decayRange + 1;
	String shouldDecay = null; //Forgive me,but this is regrettably the only type which will accept null
}
