package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet31RelEntityMove extends Packet30Entity
{

    public Packet31RelEntityMove()
    {
    }

    public Packet31RelEntityMove(int i, byte byte0, byte byte1, byte byte2)
    {
        super(i);
        xPosition = byte0;
        yPosition = byte1;
        zPosition = byte2;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        super.readPacketData(datainputstream);
        xPosition = datainputstream.readByte();
        yPosition = datainputstream.readByte();
        zPosition = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        super.writePacketData(dataoutputstream);
        dataoutputstream.writeByte(xPosition);
        dataoutputstream.writeByte(yPosition);
        dataoutputstream.writeByte(zPosition);
    }

    public int getPacketSize()
    {
        return 7;
    }
}
