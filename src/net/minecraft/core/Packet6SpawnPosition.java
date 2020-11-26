package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet6SpawnPosition extends Packet
{

    public Packet6SpawnPosition()
    {
    }

    public Packet6SpawnPosition(int i, int j, int k)
    {
        xPosition = i;
        yPosition = j;
        zPosition = k;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        xPosition = datainputstream.readInt();
        yPosition = datainputstream.readInt();
        zPosition = datainputstream.readInt();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(xPosition);
        dataoutputstream.writeInt(yPosition);
        dataoutputstream.writeInt(zPosition);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleSpawnPosition(this);
    }

    public int getPacketSize()
    {
        return 12;
    }

    public int xPosition;
    public int yPosition;
    public int zPosition;
}
