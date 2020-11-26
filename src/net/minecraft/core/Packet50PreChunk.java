package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet50PreChunk extends Packet
{

    public Packet50PreChunk()
    {
    }

    public Packet50PreChunk(int i, int j, boolean flag)
    {
        isChunkDataPacket = true;
        xPosition = i;
        yPosition = j;
        mode = flag;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        xPosition = datainputstream.readInt();
        yPosition = datainputstream.readInt();
        mode = datainputstream.read() != 0;
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(xPosition);
        dataoutputstream.writeInt(yPosition);
        dataoutputstream.write(mode ? 1 : 0);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handlePreChunk(this);
    }

    public int getPacketSize()
    {
        return 9;
    }

    public int xPosition;
    public int yPosition;
    public boolean mode;
}
