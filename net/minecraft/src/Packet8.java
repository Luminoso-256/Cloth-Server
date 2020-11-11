package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet8 extends Packet
{

    public Packet8()
    {
    }

    public Packet8(int i)
    {
        field_9017_a = i;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        field_9017_a = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeByte(field_9017_a);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_9003_a(this);
    }

    public int getPacketSize()
    {
        return 1;
    }

    public int field_9017_a;
}
