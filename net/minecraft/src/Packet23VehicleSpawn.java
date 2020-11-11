package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet23VehicleSpawn extends Packet
{

    public Packet23VehicleSpawn()
    {
    }

    public Packet23VehicleSpawn(Entity entity, int i)
    {
        entityId = entity.field_331_c;
        xPosition = MathHelper.floor_double(entity.posX * 32D);
        yPosition = MathHelper.floor_double(entity.posY * 32D);
        zPosition = MathHelper.floor_double(entity.posZ * 32D);
        type = i;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        entityId = datainputstream.readInt();
        type = datainputstream.readByte();
        xPosition = datainputstream.readInt();
        yPosition = datainputstream.readInt();
        zPosition = datainputstream.readInt();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(entityId);
        dataoutputstream.writeByte(type);
        dataoutputstream.writeInt(xPosition);
        dataoutputstream.writeInt(yPosition);
        dataoutputstream.writeInt(zPosition);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleVehicleSpawn(this);
    }

    public int getPacketSize()
    {
        return 17;
    }

    public int entityId;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int type;
}
