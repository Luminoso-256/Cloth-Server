package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Packet60Explosion extends Packet {

    public double field_12003_a;
    public double field_12002_b;
    public double field_12006_c;
    public float field_12005_d;
    public Set field_12004_e;

    public Packet60Explosion() {
    }

    public Packet60Explosion(double d, double d1, double d2, float f,
                             Set set) {
        field_12003_a = d;
        field_12002_b = d1;
        field_12006_c = d2;
        field_12005_d = f;
        field_12004_e = new HashSet(set);
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        field_12003_a = datainputstream.readDouble();
        field_12002_b = datainputstream.readDouble();
        field_12006_c = datainputstream.readDouble();
        field_12005_d = datainputstream.readFloat();
        int i = datainputstream.readInt();
        field_12004_e = new HashSet();
        int j = (int) field_12003_a;
        int k = (int) field_12002_b;
        int l = (int) field_12006_c;
        for (int i1 = 0; i1 < i; i1++) {
            int j1 = datainputstream.readByte() + j;
            int k1 = datainputstream.readByte() + k;
            int l1 = datainputstream.readByte() + l;
            field_12004_e.add(new ChunkPosition(j1, k1, l1));
        }

    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeDouble(field_12003_a);
        dataoutputstream.writeDouble(field_12002_b);
        dataoutputstream.writeDouble(field_12006_c);
        dataoutputstream.writeFloat(field_12005_d);
        dataoutputstream.writeInt(field_12004_e.size());
        int i = (int) field_12003_a;
        int j = (int) field_12002_b;
        int k = (int) field_12006_c;
        int j1;
        for (Iterator iterator = field_12004_e.iterator(); iterator.hasNext(); dataoutputstream.writeByte(j1)) {
            ChunkPosition chunkposition = (ChunkPosition) iterator.next();
            int l = chunkposition.field_846_a - i;
            int i1 = chunkposition.field_845_b - j;
            j1 = chunkposition.field_847_c - k;
            dataoutputstream.writeByte(l);
            dataoutputstream.writeByte(i1);
        }

    }

    public void processPacket(NetHandler nethandler) {
        nethandler.func_12001_a(this);
    }

    public int getPacketSize() {
        return 32 + field_12004_e.size() * 3;
    }
}
