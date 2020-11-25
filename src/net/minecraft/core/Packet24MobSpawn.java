package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet24MobSpawn extends Packet
{

    public Packet24MobSpawn()
    {
    }

    public Packet24MobSpawn(EntityLiving entityliving)
    {
        entityId = entityliving.field_331_c;
        type = (byte)EntityList.func_565_a(entityliving);
        xPosition = MathHelper.floor_double(entityliving.posX * 32D);
        yPosition = MathHelper.floor_double(entityliving.posY * 32D);
        zPosition = MathHelper.floor_double(entityliving.posZ * 32D);
        yaw = (byte)(int)((entityliving.rotationYaw * 256F) / 360F);
        pitch = (byte)(int)((entityliving.rotationPitch * 256F) / 360F);
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        entityId = datainputstream.readInt();
        type = datainputstream.readByte();
        xPosition = datainputstream.readInt();
        yPosition = datainputstream.readInt();
        zPosition = datainputstream.readInt();
        yaw = datainputstream.readByte();
        pitch = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(entityId);
        dataoutputstream.writeByte(type);
        dataoutputstream.writeInt(xPosition);
        dataoutputstream.writeInt(yPosition);
        dataoutputstream.writeInt(zPosition);
        dataoutputstream.writeByte(yaw);
        dataoutputstream.writeByte(pitch);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleMobSpawn(this);
    }

    public int getPacketSize()
    {
        return 19;
    }

    public int entityId;
    public byte type;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public byte yaw;
    public byte pitch;
}
