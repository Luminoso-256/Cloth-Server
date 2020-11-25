package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet4UpdateTime extends Packet
{

    public Packet4UpdateTime()
    {
    }

    public Packet4UpdateTime(long l)
    {
        time = l;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        time = datainputstream.readLong();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeLong(time);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleUpdateTime(this);
    }

    public int getPacketSize()
    {
        return 8;
    }

    public long time;
}
