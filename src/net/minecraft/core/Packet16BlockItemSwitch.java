package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet16BlockItemSwitch extends Packet {

    public int unused;
    public int id;

    public Packet16BlockItemSwitch() {
    }

    public Packet16BlockItemSwitch(int i, int j) {
        unused = i;
        id = j;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        unused = datainputstream.readInt();
        id = datainputstream.readShort();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(unused);
        dataoutputstream.writeShort(id);
    }

    public void processPacket(NetHandler nethandler) {
        nethandler.handleBlockItemSwitch(this);
    }

    public int getPacketSize() {
        return 6;
    }
}
