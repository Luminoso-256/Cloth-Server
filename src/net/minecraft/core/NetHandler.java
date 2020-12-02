package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class NetHandler {

    public NetHandler() {
    }

    public void handleMapChunk(Packet51MapChunk packet51mapchunk) {
    }

    public void func_6001_a(Packet packet) {
    }

    public void handleErrorMessage(String s) {
    }

    public void handleKickDisconnect(Packet255KickDisconnect packet255kickdisconnect) {
        func_6001_a(packet255kickdisconnect);
    }

    public void handleLogin(Packet1Login packet1login) {
        func_6001_a(packet1login);
    }

    public void handleFlying(Packet10Flying packet10flying) {
        func_6001_a(packet10flying);
    }

    public void handleMultiBlockChange(Packet52MultiBlockChange packet52multiblockchange) {
        func_6001_a(packet52multiblockchange);
    }

    public void handleBlockDig(Packet14BlockDig packet14blockdig) {
        func_6001_a(packet14blockdig);
    }

    public void handleBlockChange(Packet53BlockChange packet53blockchange) {
        func_6001_a(packet53blockchange);
    }

    public void handlePreChunk(Packet50PreChunk packet50prechunk) {
        func_6001_a(packet50prechunk);
    }

    public void handleNamedEntitySpawn(Packet20NamedEntitySpawn packet20namedentityspawn) {
        func_6001_a(packet20namedentityspawn);
    }

    public void handleEntity(Packet30Entity packet30entity) {
        func_6001_a(packet30entity);
    }

    public void handleEntityTeleport(Packet34EntityTeleport packet34entityteleport) {
        func_6001_a(packet34entityteleport);
    }

    public void handlePlace(Packet15Place packet15place) {
        func_6001_a(packet15place);
    }

    public void handleBlockItemSwitch(Packet16BlockItemSwitch packet16blockitemswitch) {
        func_6001_a(packet16blockitemswitch);
    }

    public void handleDestroyEntity(Packet29DestroyEntity packet29destroyentity) {
        func_6001_a(packet29destroyentity);
    }

    public void handlePickupSpawn(Packet21PickupSpawn packet21pickupspawn) {
        func_6001_a(packet21pickupspawn);
    }

    public void handleCollect(Packet22Collect packet22collect) {
        func_6001_a(packet22collect);
    }

    public void handleChat(Packet3Chat packet3chat) {
        func_6001_a(packet3chat);
    }

    public void handleAddToInventory(Packet17AddToInventory packet17addtoinventory) {
        func_6001_a(packet17addtoinventory);
    }

    public void handleVehicleSpawn(Packet23VehicleSpawn packet23vehiclespawn) {
        func_6001_a(packet23vehiclespawn);
    }

    public void handleArmAnimation(Packet18ArmAnimation packet18armanimation) {
        func_6001_a(packet18armanimation);
    }

    public void handleHandshake(Packet2Handshake packet2handshake) {
        func_6001_a(packet2handshake);
    }

    public void handleMobSpawn(Packet24MobSpawn packet24mobspawn) {
        func_6001_a(packet24mobspawn);
    }

    public void handleUpdateTime(Packet4UpdateTime packet4updatetime) {
        func_6001_a(packet4updatetime);
    }

    public void handlePlayerInventory(Packet5PlayerInventory packet5playerinventory) {
        func_6001_a(packet5playerinventory);
    }

    public void handleComplexEntity(Packet59ComplexEntity packet59complexentity) {
        func_6001_a(packet59complexentity);
    }

    public void handleSpawnPosition(Packet6SpawnPosition packet6spawnposition) {
        func_6001_a(packet6spawnposition);
    }

    public void func_6002_a(Packet28 packet28) {
        func_6001_a(packet28);
    }

    public void func_6003_a(Packet39 packet39) {
        func_6001_a(packet39);
    }

    public void func_6006_a(Packet7 packet7) {
        func_6001_a(packet7);
    }

    public void func_9001_a(Packet38 packet38) {
        func_6001_a(packet38);
    }

    public void func_9003_a(Packet8 packet8) {
        func_6001_a(packet8);
    }

    public void func_9002_a(Packet9 packet9) {
        func_6001_a(packet9);
    }

    public void func_12001_a(Packet60 packet60) {
        func_6001_a(packet60);
    }
}
