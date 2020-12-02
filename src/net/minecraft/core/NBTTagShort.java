package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBase {

    public short shortValue;

    public NBTTagShort() {
    }

    public NBTTagShort(short word0) {
        shortValue = word0;
    }

    void writeTagContents(DataOutput dataoutput) throws IOException {
        dataoutput.writeShort(shortValue);
    }

    void readTagContents(DataInput datainput) throws IOException {
        shortValue = datainput.readShort();
    }

    public byte getType() {
        return 2;
    }

    public String toString() {
        return (new StringBuilder()).append("").append(shortValue).toString();
    }
}
