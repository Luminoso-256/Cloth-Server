package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet2Handshake extends Packet
{

    public Packet2Handshake()
    {
    }

    public Packet2Handshake(String s)
    {
        username = s;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        username = datainputstream.readUTF();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeUTF(username);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleHandshake(this);
    }

    public int getPacketSize()
    {
        return 4 + username.length() + 4;
    }

    public String username;
}
