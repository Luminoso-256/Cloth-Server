package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet9Respawn extends Packet {

    public Packet9Respawn() {
    }

    public void processPacket(NetHandler nethandler) {
        nethandler.func_9002_a(this);
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
    }

    public int getPacketSize() {
        return 0;
    }
}
