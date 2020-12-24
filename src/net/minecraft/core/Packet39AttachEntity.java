package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet39AttachEntity extends Packet {

    public int field_6044_a;
    public int field_6043_b;

    public Packet39AttachEntity() {
    }

    public Packet39AttachEntity(Entity entity, Entity entity1) {
        field_6044_a = entity.field_331_c;
        field_6043_b = entity1 == null ? -1 : entity1.field_331_c;
    }

    public int getPacketSize() {
        return 8;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        field_6044_a = datainputstream.readInt();
        field_6043_b = datainputstream.readInt();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(field_6044_a);
        dataoutputstream.writeInt(field_6043_b);
    }

    public void processPacket(NetHandler nethandler) {
        nethandler.func_6003_a(this);
    }
}
