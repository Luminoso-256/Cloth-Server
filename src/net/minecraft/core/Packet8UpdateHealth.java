package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet8UpdateHealth extends Packet {

    public int field_9017_a;

    public Packet8UpdateHealth() {
    }

    public Packet8UpdateHealth(int i) {
        field_9017_a = i;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        field_9017_a = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeByte(field_9017_a);
    }

    public void processPacket(NetHandler nethandler) {
        nethandler.func_9003_a(this);
    }

    public int getPacketSize() {
        return 1;
    }
}
