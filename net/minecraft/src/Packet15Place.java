package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet15Place extends Packet
{

    public Packet15Place()
    {
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        id = datainputstream.readShort();
        xPosition = datainputstream.readInt();
        yPosition = datainputstream.read();
        zPosition = datainputstream.readInt();
        direction = datainputstream.read();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeShort(id);
        dataoutputstream.writeInt(xPosition);
        dataoutputstream.write(yPosition);
        dataoutputstream.writeInt(zPosition);
        dataoutputstream.write(direction);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handlePlace(this);
    }

    public int getPacketSize()
    {
        return 12;
    }

    public int id;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int direction;
}
