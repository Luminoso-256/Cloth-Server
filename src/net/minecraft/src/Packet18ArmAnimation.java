package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet18ArmAnimation extends Packet
{

    public Packet18ArmAnimation()
    {
    }

    public Packet18ArmAnimation(Entity entity, int i)
    {
        entityId = entity.field_331_c;
        animate = i;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        entityId = datainputstream.readInt();
        animate = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(entityId);
        dataoutputstream.writeByte(animate);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleArmAnimation(this);
    }

    public int getPacketSize()
    {
        return 5;
    }

    public int entityId;
    public int animate;
}
