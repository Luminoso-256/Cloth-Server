package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Packet
{

    public Packet()
    {
        isChunkDataPacket = false;
    }

    static void addIdClassMapping(int i, Class class1)
    {
        if(packetIdToClassMap.containsKey(Integer.valueOf(i)))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Duplicate packet id:").append(i).toString());
        }
        if(packetClassToIdMap.containsKey(class1))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Duplicate packet class:").append(class1).toString());
        } else
        {
            packetIdToClassMap.put(Integer.valueOf(i), class1);
            packetClassToIdMap.put(class1, Integer.valueOf(i));
            return;
        }
    }

    public static Packet getNewPacket(int i)
    {
        try
        {
            Class class1 = (Class)packetIdToClassMap.get(Integer.valueOf(i));
            if(class1 == null)
            {
                return null;
            } else
            {
                return (Packet)class1.newInstance();
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        System.out.println((new StringBuilder()).append("Skipping packet with id ").append(i).toString());
        return null;
    }

    public final int getPacketId()
    {
        return ((Integer)packetClassToIdMap.get(getClass())).intValue();
    }

    public static Packet readPacket(DataInputStream datainputstream) throws IOException
    {
        int i = datainputstream.read();
        if(i == -1)
        {
            return null;
        }
        Packet packet = getNewPacket(i);
        if(packet == null)
        {
            throw new IOException((new StringBuilder()).append("Bad packet id ").append(i).toString());
        } else
        {
            packet.readPacketData(datainputstream);
            return packet;
        }
    }

    public static void writePacket(Packet packet, DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.write(packet.getPacketId());
        packet.writePacketData(dataoutputstream);
    }

    public abstract void readPacketData(DataInputStream datainputstream) throws IOException;

    public abstract void writePacketData(DataOutputStream dataoutputstream) throws IOException;

    public abstract void processPacket(NetHandler nethandler);

    public abstract int getPacketSize();

    static Class _mthclass$(String s)
    {
        try
        {
            return Class.forName(s);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private static Map packetIdToClassMap = new HashMap();
    private static Map packetClassToIdMap = new HashMap();
    public boolean isChunkDataPacket;

    static 
    {
        addIdClassMapping(0, Packet0KeepAlive.class);
        addIdClassMapping(1, Packet1Login.class);
        addIdClassMapping(2, Packet2Handshake.class);
        addIdClassMapping(3, Packet3Chat.class);
        addIdClassMapping(4, Packet4UpdateTime.class);
        addIdClassMapping(5, Packet5PlayerInventory.class);
        addIdClassMapping(6, Packet6SpawnPosition.class);
        addIdClassMapping(7, Packet7.class);
        addIdClassMapping(8, Packet8.class);
        addIdClassMapping(9, Packet9.class);
        addIdClassMapping(10, Packet10Flying.class);
        addIdClassMapping(11, Packet11PlayerPosition.class);
        addIdClassMapping(12, Packet12PlayerLook.class);
        addIdClassMapping(13, Packet13PlayerLookMove.class);
        addIdClassMapping(14, Packet14BlockDig.class);
        addIdClassMapping(15, Packet15Place.class);
        addIdClassMapping(16, Packet16BlockItemSwitch.class);
        addIdClassMapping(17, Packet17AddToInventory.class);
        addIdClassMapping(18, Packet18ArmAnimation.class);
        addIdClassMapping(20, Packet20NamedEntitySpawn.class);
        addIdClassMapping(21, Packet21PickupSpawn.class);
        addIdClassMapping(22, Packet22Collect.class);
        addIdClassMapping(23, Packet23VehicleSpawn.class);
        addIdClassMapping(24, Packet24MobSpawn.class);
        addIdClassMapping(28, Packet28.class);
        addIdClassMapping(29, Packet29DestroyEntity.class);
        addIdClassMapping(30, Packet30Entity.class);
        addIdClassMapping(31, Packet31RelEntityMove.class);
        addIdClassMapping(32, Packet32EntityLook.class);
        addIdClassMapping(33, Packet33RelEntityMoveLook.class);
        addIdClassMapping(34, Packet34EntityTeleport.class);
        addIdClassMapping(38, Packet38.class);
        addIdClassMapping(39, Packet39.class);
        addIdClassMapping(50, Packet50PreChunk.class);
        addIdClassMapping(51, Packet51MapChunk.class);
        addIdClassMapping(52, Packet52MultiBlockChange.class);
        addIdClassMapping(53, Packet53BlockChange.class);
        addIdClassMapping(59, Packet59ComplexEntity.class);
        addIdClassMapping(60, Packet60.class);
        addIdClassMapping(255, Packet255KickDisconnect.class);
    }
}
