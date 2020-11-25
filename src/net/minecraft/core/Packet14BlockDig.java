package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet14BlockDig extends Packet
{

    public Packet14BlockDig()
    {
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        status = datainputstream.read();
        xPosition = datainputstream.readInt();
        yPosition = datainputstream.read();
        zPosition = datainputstream.readInt();
        face = datainputstream.read();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.write(status);
        dataoutputstream.writeInt(xPosition);
        dataoutputstream.write(yPosition);
        dataoutputstream.writeInt(zPosition);
        dataoutputstream.write(face);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleBlockDig(this);
    }

    public int getPacketSize()
    {
        return 11;
    }

    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int face;
    public int status;
}
