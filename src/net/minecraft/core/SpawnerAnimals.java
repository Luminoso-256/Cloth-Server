package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.cloth.file.GameruleManager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class SpawnerAnimals {

    private static Set field_4311_a = new HashSet();
    private static GameruleManager gameruleManager = GameruleManager.getInstance();

    public SpawnerAnimals() {
    }

    protected static ChunkPosition func_4112_a(World world, int i, int j) {
        int k = i + world.rand.nextInt(16);
        int l = world.rand.nextInt(128);
        int i1 = j + world.rand.nextInt(16);
        return new ChunkPosition(k, l, i1);
    }

    public static final int SpawnMobs(World world) {
        field_4311_a.clear();

        // GameruleManager gameruleManager =  new GameruleManager(new File("server.gamerules")); //We  are going to  hook you up to a gamerule!
        int UpperSpawnRateBound = gameruleManager.getGamerule("inversespawnrate", 50);
        int UpperJocketBound = gameruleManager.getGamerule("inverseskeletonjockeyrate", 100);

        int var1;
        for (var1 = 0; var1 < world.playerEntities.size(); ++var1) {
            EntityPlayer var2 = (EntityPlayer) world.playerEntities.get(var1);
            int var3 = MathHelper.floor_double(var2.posX / 16.0D);
            int var4 = MathHelper.floor_double(var2.posZ / 16.0D);
            byte var5 = 8;

            for (int var6 = -var5; var6 <= var5; ++var6) {
                for (int var7 = -var5; var7 <= var5; ++var7) {
                    field_4311_a.add(new ChunkCoordIntPair(var6 + var3, var7 + var4));
                }
            }
        }

        var1 = 0;

        for (int var28 = 0; var28 < EnumCreatureType.values().length; ++var28) {
            EnumCreatureType var29 = EnumCreatureType.values()[var28];
            if (world.countEntities(var29.field_4221_c) <= var29.field_4220_d * field_4311_a.size() / 256) {
                Iterator var30 = field_4311_a.iterator();

                label90:
                while (var30.hasNext()) {
                    ChunkCoordIntPair var31 = (ChunkCoordIntPair) var30.next();
                    if (world.rand.nextInt(UpperSpawnRateBound) == 0) {
                        MobSpawnerBase var32 = world.func_4077_a().func_4066_a(var31);
                        Class[] var33 = var32.getEntitiesForType(var29);
                        if (var33 != null && var33.length != 0) {
                            int var8 = world.rand.nextInt(var33.length);
                            ChunkPosition var9 = func_4112_a(world, var31.field_152_a * 16, var31.field_151_b * 16);
                            int var10 = var9.field_846_a;
                            int var11 = var9.field_845_b;
                            int var12 = var9.field_847_c;
                            if (!world.doesBlockAllowAttachment(var10, var11, var12) && world.getBlockMaterial(var10, var11, var12) == Material.air) {
                                int mobCap = 0;

                                for (int var14 = 0; var14 < 3; ++var14) {
                                    int var15 = var10;
                                    int var16 = var11;
                                    int var17 = var12;
                                    byte var18 = 6;

                                    for (int var19 = 0; var19 < 4; ++var19) {
                                        var15 += world.rand.nextInt(var18) - world.rand.nextInt(var18);
                                        var16 += world.rand.nextInt(1) - world.rand.nextInt(1);
                                        var17 += world.rand.nextInt(var18) - world.rand.nextInt(var18);
                                        if (world.doesBlockAllowAttachment(var15, var16 - 1, var17) && !world.doesBlockAllowAttachment(var15, var16, var17) && !world.getBlockMaterial(var15, var16, var17).getIsLiquid() && !world.doesBlockAllowAttachment(var15, var16 + 1, var17)) {
                                            float var20 = (float) var15 + 0.5F;
                                            float var21 = (float) var16;
                                            float var22 = (float) var17 + 0.5F;
                                            if (world.getClosestPlayer((double) var20, (double) var21, (double) var22, 24.0D) == null) {
                                                float var23 = var20 - (float) world.spawnX;
                                                float var24 = var21 - (float) world.spawnY;
                                                float var25 = var22 - (float) world.spawnZ;
                                                float var26 = var23 * var23 + var24 * var24 + var25 * var25;
                                                if (var26 >= 576.0F) {
                                                    EntityLiving var34;
                                                    try {
                                                        var34 = (EntityLiving) var33[var8].getConstructor(new Class[]{World.class}).newInstance(new Object[]{world});
                                                    } catch (Exception var27) {
                                                        var27.printStackTrace();
                                                        return var1;
                                                    }

                                                    var34.func_107_c((double) var20, (double) var21, (double) var22, world.rand.nextFloat() * 360.0F, 0.0F);
                                                    if (var34.getCanSpawnHere()) {
                                                        ++mobCap;
                                                        world.entityJoinedWorld(var34);
                                                        if (var34 instanceof EntitySpider && world.rand.nextInt(UpperJocketBound) == 0) {
                                                            EntitySkeleton var35 = new EntitySkeleton(world);
                                                            var35.func_107_c((double) var20, (double) var21, (double) var22, var34.rotationYaw, 0.0F);
                                                            world.entityJoinedWorld(var35);
                                                            var35.func_6094_e(var34);
                                                        }

                                                        if (mobCap >= var34.func_4045_i()) {
                                                            continue label90;
                                                        }
                                                    }

                                                    var1 += mobCap;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return var1;
    }
}
