package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet38 extends Packet
{

    public Packet38()
    {
    }

    public Packet38(int i, byte byte0)
    {
        field_9016_a = i;
        field_9015_b = byte0;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        field_9016_a = datainputstream.readInt();
        field_9015_b = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(field_9016_a);
        dataoutputstream.writeByte(field_9015_b);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_9001_a(this);
    }

    public int getPacketSize()
    {
        return 5;
    }

    public int field_9016_a;
    public byte field_9015_b;
}
