package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet7UseEntity extends Packet {

    public int field_9019_a;
    public int field_9018_b;
    public int field_9020_c;

    public Packet7UseEntity() {
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        field_9019_a = datainputstream.readInt();
        field_9018_b = datainputstream.readInt();
        field_9020_c = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(field_9019_a);
        dataoutputstream.writeInt(field_9018_b);
        dataoutputstream.writeByte(field_9020_c);
    }

    public void processPacket(NetHandler nethandler) {
        nethandler.func_6006_a(this);
    }

    public int getPacketSize() {
        return 9;
    }
}
