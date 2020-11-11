package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet11PlayerPosition extends Packet10Flying
{

    public Packet11PlayerPosition()
    {
        moving = true;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        xPosition = datainputstream.readDouble();
        yPosition = datainputstream.readDouble();
        stance = datainputstream.readDouble();
        zPosition = datainputstream.readDouble();
        super.readPacketData(datainputstream);
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeDouble(xPosition);
        dataoutputstream.writeDouble(yPosition);
        dataoutputstream.writeDouble(stance);
        dataoutputstream.writeDouble(zPosition);
        super.writePacketData(dataoutputstream);
    }

    public int getPacketSize()
    {
        return 33;
    }
}
