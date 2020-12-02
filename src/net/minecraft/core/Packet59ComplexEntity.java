package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet59ComplexEntity extends Packet {

    public int xPosition;
    public int yPosition;
    public int zPosition;
    public byte entityData[];
    public NBTTagCompound entityNBT;

    public Packet59ComplexEntity() {
        isChunkDataPacket = true;
    }

    public Packet59ComplexEntity(int i, int j, int k, TileEntity tileentity) {
        isChunkDataPacket = true;
        xPosition = i;
        yPosition = j;
        zPosition = k;
        entityNBT = new NBTTagCompound();
        tileentity.writeToNBT(entityNBT);
        try {
            entityData = CompressedStreamTools.func_772_a(entityNBT);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        xPosition = datainputstream.readInt();
        yPosition = datainputstream.readShort();
        zPosition = datainputstream.readInt();
        int i = datainputstream.readShort() & 0xffff;
        entityData = new byte[i];
        datainputstream.readFully(entityData);
        entityNBT = CompressedStreamTools.func_773_a(entityData);
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(xPosition);
        dataoutputstream.writeShort(yPosition);
        dataoutputstream.writeInt(zPosition);
        dataoutputstream.writeShort((short) entityData.length);
        dataoutputstream.write(entityData);
    }

    public void processPacket(NetHandler nethandler) {
        nethandler.handleComplexEntity(this);
    }

    public int getPacketSize() {
        return entityData.length + 2 + 10;
    }
}
