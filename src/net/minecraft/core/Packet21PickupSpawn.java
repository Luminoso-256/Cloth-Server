package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet21PickupSpawn extends Packet
{

    public Packet21PickupSpawn()
    {
    }

    public Packet21PickupSpawn(EntityItem entityitem)
    {
        entityId = entityitem.field_331_c;
        itemId = entityitem.item.itemID;
        count = entityitem.item.stackSize;
        xPosition = MathHelper.floor_double(entityitem.posX * 32D);
        yPosition = MathHelper.floor_double(entityitem.posY * 32D);
        zPosition = MathHelper.floor_double(entityitem.posZ * 32D);
        rotation = (byte)(int)(entityitem.motionX * 128D);
        pitch = (byte)(int)(entityitem.motionY * 128D);
        roll = (byte)(int)(entityitem.motionZ * 128D);
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        entityId = datainputstream.readInt();
        itemId = datainputstream.readShort();
        count = datainputstream.readByte();
        xPosition = datainputstream.readInt();
        yPosition = datainputstream.readInt();
        zPosition = datainputstream.readInt();
        rotation = datainputstream.readByte();
        pitch = datainputstream.readByte();
        roll = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(entityId);
        dataoutputstream.writeShort(itemId);
        dataoutputstream.writeByte(count);
        dataoutputstream.writeInt(xPosition);
        dataoutputstream.writeInt(yPosition);
        dataoutputstream.writeInt(zPosition);
        dataoutputstream.writeByte(rotation);
        dataoutputstream.writeByte(pitch);
        dataoutputstream.writeByte(roll);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handlePickupSpawn(this);
    }

    public int getPacketSize()
    {
        return 22;
    }

    public int entityId;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public byte rotation;
    public byte pitch;
    public byte roll;
    public int itemId;
    public int count;
}
