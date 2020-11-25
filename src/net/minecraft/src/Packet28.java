package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet28 extends Packet
{

    public Packet28()
    {
    }

    public Packet28(Entity entity)
    {
        this(entity.field_331_c, entity.motionX, entity.motionY, entity.motionZ);
    }

    public Packet28(int i, double d, double d1, double d2)
    {
        field_6040_a = i;
        double d3 = 3.8999999999999999D;
        if(d < -d3)
        {
            d = -d3;
        }
        if(d1 < -d3)
        {
            d1 = -d3;
        }
        if(d2 < -d3)
        {
            d2 = -d3;
        }
        if(d > d3)
        {
            d = d3;
        }
        if(d1 > d3)
        {
            d1 = d3;
        }
        if(d2 > d3)
        {
            d2 = d3;
        }
        field_6039_b = (int)(d * 8000D);
        field_6042_c = (int)(d1 * 8000D);
        field_6041_d = (int)(d2 * 8000D);
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        field_6040_a = datainputstream.readInt();
        field_6039_b = datainputstream.readShort();
        field_6042_c = datainputstream.readShort();
        field_6041_d = datainputstream.readShort();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(field_6040_a);
        dataoutputstream.writeShort(field_6039_b);
        dataoutputstream.writeShort(field_6042_c);
        dataoutputstream.writeShort(field_6041_d);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_6002_a(this);
    }

    public int getPacketSize()
    {
        return 10;
    }

    public int field_6040_a;
    public int field_6039_b;
    public int field_6042_c;
    public int field_6041_d;
}
