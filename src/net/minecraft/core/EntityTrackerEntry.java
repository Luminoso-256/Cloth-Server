package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.cloth.ExploitUtils;
import net.minecraft.cloth.file.GameruleManager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EntityTrackerEntry {

    public Entity trackedEntity;
    public boolean field_9237_b;
    public boolean field_9236_c;
    public boolean field_9225_n;
    public int trackingDistanceThreshold;
    public int field_9234_e;
    public int encodedPosX;
    public int field_9232_g;
    public int encodedPosZ;
    public int field_9230_i;
    public int field_9229_j;
    public double field_9228_k;
    public double field_9227_l;
    public double field_9226_m;
    public int field_9221_t;
    public boolean field_900_j;
    public Set field_899_k;
    private double field_9224_q;
    private double field_9223_r;
    private double field_9222_s;
    private boolean field_12020_u;
    private boolean field_9220_u;
    public EntityTrackerEntry(Entity entity, int i, int j, boolean flag) {
        field_9237_b = false;
        field_9236_c = false;
        field_9225_n = false;
        field_9221_t = 0;
        field_12020_u = false;
        field_900_j = false;
        field_899_k = new HashSet();
        trackedEntity = entity;
        trackingDistanceThreshold = i;
        field_9234_e = j;
        field_9220_u = flag;
        encodedPosX = MathHelper.floor_double(entity.posX * 32D);
        field_9232_g = MathHelper.floor_double(entity.posY * 32D);
        encodedPosZ = MathHelper.floor_double(entity.posZ * 32D);
        field_9230_i = MathHelper.floor_float((entity.rotationYaw * 256F) / 360F);
        field_9229_j = MathHelper.floor_float((entity.rotationPitch * 256F) / 360F);
    }

    public boolean equals(Object obj) {
        if (obj instanceof EntityTrackerEntry) {
            return ((EntityTrackerEntry) obj).trackedEntity.field_331_c == trackedEntity.field_331_c;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return trackedEntity.field_331_c;
    }

    public void func_605_a(List list) {
        field_900_j = false;
        if (!field_12020_u || trackedEntity.getDistanceSq(field_9224_q, field_9223_r, field_9222_s) > 16D) {
            field_9224_q = trackedEntity.posX;
            field_9223_r = trackedEntity.posY;
            field_9222_s = trackedEntity.posZ;
            field_12020_u = true;
            field_900_j = true;
            func_601_b(list);
        }
        if (field_9221_t++ % field_9234_e == 0) {
            int i = MathHelper.floor_double(trackedEntity.posX * 32D);
            int j = MathHelper.floor_double(trackedEntity.posY * 32D);
            int k = MathHelper.floor_double(trackedEntity.posZ * 32D);
            int l = MathHelper.floor_float((trackedEntity.rotationYaw * 256F) / 360F);
            int i1 = MathHelper.floor_float((trackedEntity.rotationPitch * 256F) / 360F);
            boolean flag = i != encodedPosX || j != field_9232_g || k != encodedPosZ;
            boolean flag1 = l != field_9230_i || i1 != field_9229_j;
            int j1 = i - encodedPosX;
            int k1 = j - field_9232_g;
            int l1 = k - encodedPosZ;
            Object obj = null;
            if (j1 < -128 || j1 >= 128 || k1 < -128 || k1 >= 128 || l1 < -128 || l1 >= 128) {
                obj = new Packet34EntityTeleport(trackedEntity.field_331_c, i, j, k, (byte) l, (byte) i1);
            } else if (flag && flag1) {
                obj = new Packet33RelEntityMoveLook(trackedEntity.field_331_c, (byte) j1, (byte) k1, (byte) l1, (byte) l, (byte) i1);
            } else if (flag) {
                obj = new Packet31RelEntityMove(trackedEntity.field_331_c, (byte) j1, (byte) k1, (byte) l1);
            } else if (flag1) {
                obj = new Packet32EntityLook(trackedEntity.field_331_c, (byte) l, (byte) i1);
            } else {
                obj = new Packet30Entity(trackedEntity.field_331_c);
            }
            if (field_9220_u) {
                double d = trackedEntity.motionX - field_9228_k;
                double d1 = trackedEntity.motionY - field_9227_l;
                double d2 = trackedEntity.motionZ - field_9226_m;
                double d3 = 0.02D;
                double d4 = d * d + d1 * d1 + d2 * d2;
                if (d4 > d3 * d3 || d4 > 0.0D && trackedEntity.motionX == 0.0D && trackedEntity.motionY == 0.0D && trackedEntity.motionZ == 0.0D) {
                    field_9228_k = trackedEntity.motionX;
                    field_9227_l = trackedEntity.motionY;
                    field_9226_m = trackedEntity.motionZ;
                    func_603_a(new Packet28EntityVelocity(trackedEntity.field_331_c, field_9228_k, field_9227_l, field_9226_m));
                }
            }
            if (obj != null) {
                func_603_a(((Packet) (obj)));
            }
            if (field_9237_b && trackedEntity.ridingEntity == null) {
                field_9237_b = false;
                func_12018_b(new Packet18ArmAnimation(trackedEntity, 101));
            } else if (!field_9237_b && trackedEntity.ridingEntity != null) {
                field_9237_b = true;
                func_12018_b(new Packet18ArmAnimation(trackedEntity, 100));
            }
            if (trackedEntity instanceof EntityLiving) {
                if (field_9225_n && !trackedEntity.func_9059_p()) {
                    field_9225_n = false;
                    func_12018_b(new Packet18ArmAnimation(trackedEntity, 105));
                } else if (!field_9225_n && trackedEntity.func_9059_p()) {
                    field_9225_n = true;
                    func_12018_b(new Packet18ArmAnimation(trackedEntity, 104));
                }
            }
            if (field_9236_c && trackedEntity.field_9061_Z <= 0) {
                field_9236_c = false;
                func_12018_b(new Packet18ArmAnimation(trackedEntity, 103));
            } else if (!field_9236_c && trackedEntity.field_9061_Z > 0) {
                field_9236_c = true;
                func_12018_b(new Packet18ArmAnimation(trackedEntity, 102));
            }
            encodedPosX = i;
            field_9232_g = j;
            encodedPosZ = k;
            field_9230_i = l;
            field_9229_j = i1;
        }
        if (trackedEntity.field_9078_E) {
            func_12018_b(new Packet28EntityVelocity(trackedEntity));
            trackedEntity.field_9078_E = false;
        }
    }

    public void func_603_a(Packet packet) {
        EntityPlayerMP entityplayermp;
        for (Iterator iterator = field_899_k.iterator(); iterator.hasNext(); entityplayermp.playerNetServerHandler.sendPacket(packet)) {
            entityplayermp = (EntityPlayerMP) iterator.next();
        }

    }

    public void func_12018_b(Packet packet) {
        func_603_a(packet);
        if (trackedEntity instanceof EntityPlayerMP) {
            ((EntityPlayerMP) trackedEntity).playerNetServerHandler.sendPacket(packet);
        }
    }

    public void func_604_a() {
        func_603_a(new Packet29DestroyEntity(trackedEntity.field_331_c));
    }

    public void func_12019_a(EntityPlayerMP entityplayermp) {
        if (field_899_k.contains(entityplayermp)) {
            field_899_k.remove(entityplayermp);
        }
    }

    public void updatePlayerEntity(EntityPlayerMP entityplayermp) {
        if (entityplayermp == trackedEntity) {
            return;
        }
        double d = entityplayermp.posX - (double) (encodedPosX / 32);
        double d1 = entityplayermp.posZ - (double) (encodedPosZ / 32);
        if (d >= (double) (-trackingDistanceThreshold) && d <= (double) trackingDistanceThreshold && d1 >= (double) (-trackingDistanceThreshold) && d1 <= (double) trackingDistanceThreshold) {
            if (!field_899_k.contains(entityplayermp)) {
                field_899_k.add(entityplayermp);
                entityplayermp.playerNetServerHandler.sendPacket(getSpawnPacket());
                if (field_9225_n) {
                    entityplayermp.playerNetServerHandler.sendPacket(new Packet18ArmAnimation(trackedEntity, 104));
                }
                if (field_9237_b) {
                    entityplayermp.playerNetServerHandler.sendPacket(new Packet18ArmAnimation(trackedEntity, 100));
                }
                if (field_9236_c) {
                    entityplayermp.playerNetServerHandler.sendPacket(new Packet18ArmAnimation(trackedEntity, 102));
                }
                if (field_9220_u) {
                    entityplayermp.playerNetServerHandler.sendPacket(new Packet28EntityVelocity(trackedEntity.field_331_c, trackedEntity.motionX, trackedEntity.motionY, trackedEntity.motionZ));
                }
            }
        } else if (field_899_k.contains(entityplayermp)) {
            field_899_k.remove(entityplayermp);
            entityplayermp.playerNetServerHandler.sendPacket(new Packet29DestroyEntity(trackedEntity.field_331_c));
        }
    }

    public void func_601_b(List list) {
        for (int i = 0; i < list.size(); i++) {
            updatePlayerEntity((EntityPlayerMP) list.get(i));
        }

    }

    private Packet getSpawnPacket() {
        if (trackedEntity instanceof EntityItem) {
            EntityItem entityitem = (EntityItem) trackedEntity;
            if (ExploitUtils.IsIdValid(entityitem.item.itemID) || GameruleManager.getInstance().getGamerule("InvalidDroppedItemProtection", true)) {
                Packet21PickupSpawn packet21pickupspawn = new Packet21PickupSpawn(entityitem);
                entityitem.posX = (double) packet21pickupspawn.xPosition / 32D;
                entityitem.posY = (double) packet21pickupspawn.yPosition / 32D;
                entityitem.posZ = (double) packet21pickupspawn.zPosition / 32D;
                return packet21pickupspawn;
            }

        }
        if (trackedEntity instanceof EntityPlayerMP) {
            return new Packet20NamedEntitySpawn((EntityPlayer) trackedEntity);
        }
        if (trackedEntity instanceof EntityMinecart) {
            EntityMinecart entityminecart = (EntityMinecart) trackedEntity;
            if (entityminecart.minecartType == 0) {
                return new Packet23VehicleSpawn(trackedEntity, 10);
            }
            if (entityminecart.minecartType == 1) {
                return new Packet23VehicleSpawn(trackedEntity, 11);
            }
            if (entityminecart.minecartType == 2) {
                return new Packet23VehicleSpawn(trackedEntity, 12);
            }
        }
        if (trackedEntity instanceof EntityBoat) {
            return new Packet23VehicleSpawn(trackedEntity, 1);
        }
        if (trackedEntity instanceof IAnimals) {
            return new Packet24MobSpawn((EntityLiving) trackedEntity);
        }
        if (trackedEntity instanceof EntityFish) {
            return new Packet23VehicleSpawn(trackedEntity, 90);
        }
        if (trackedEntity instanceof EntityArrow) {
            return new Packet23VehicleSpawn(trackedEntity, 60);
        }
        if (trackedEntity instanceof EntitySnowball) {
            return new Packet23VehicleSpawn(trackedEntity, 61);
        }
        if (trackedEntity instanceof EntityTNTPrimed) {
            return new Packet23VehicleSpawn(trackedEntity, 50);
        } else {
            throw new IllegalArgumentException((new StringBuilder()).append("Don't know how to add ").append(trackedEntity.getClass()).append("!").toString());
        }
    }

    public void func_9219_b(EntityPlayerMP entityplayermp) {
        if (field_899_k.contains(entityplayermp)) {
            field_899_k.remove(entityplayermp);
            entityplayermp.playerNetServerHandler.sendPacket(new Packet29DestroyEntity(trackedEntity.field_331_c));
        }
    }
}
