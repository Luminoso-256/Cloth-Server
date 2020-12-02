package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet12PlayerLook extends Packet10Flying {

    public Packet12PlayerLook() {
        rotating = true;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        yaw = datainputstream.readFloat();
        pitch = datainputstream.readFloat();
        super.readPacketData(datainputstream);
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeFloat(yaw);
        dataoutputstream.writeFloat(pitch);
        super.writePacketData(dataoutputstream);
    }

    public int getPacketSize() {
        return 9;
    }
}
