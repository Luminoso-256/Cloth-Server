package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet34EntityTeleport extends Packet
{

    public Packet34EntityTeleport()
    {
    }

    public Packet34EntityTeleport(Entity entity)
    {
        entityId = entity.field_331_c;
        xPosition = MathHelper.floor_double(entity.posX * 32D);
        yPosition = MathHelper.floor_double(entity.posY * 32D);
        zPosition = MathHelper.floor_double(entity.posZ * 32D);
        yaw = (byte)(int)((entity.rotationYaw * 256F) / 360F);
        pitch = (byte)(int)((entity.rotationPitch * 256F) / 360F);
    }

    public Packet34EntityTeleport(int i, int j, int k, int l, byte byte0, byte byte1)
    {
        entityId = i;
        xPosition = j;
        yPosition = k;
        zPosition = l;
        yaw = byte0;
        pitch = byte1;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        entityId = datainputstream.readInt();
        xPosition = datainputstream.readInt();
        yPosition = datainputstream.readInt();
        zPosition = datainputstream.readInt();
        yaw = (byte)datainputstream.read();
        pitch = (byte)datainputstream.read();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(entityId);
        dataoutputstream.writeInt(xPosition);
        dataoutputstream.writeInt(yPosition);
        dataoutputstream.writeInt(zPosition);
        dataoutputstream.write(yaw);
        dataoutputstream.write(pitch);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleEntityTeleport(this);
    }

    public int getPacketSize()
    {
        return 34;
    }

    public int entityId;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public byte yaw;
    public byte pitch;
}
