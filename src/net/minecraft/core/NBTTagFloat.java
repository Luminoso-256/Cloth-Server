package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTBase {

    public float floatValue;

    public NBTTagFloat() {
    }

    public NBTTagFloat(float f) {
        floatValue = f;
    }

    void writeTagContents(DataOutput dataoutput) throws IOException {
        dataoutput.writeFloat(floatValue);
    }

    void readTagContents(DataInput datainput) throws IOException {
        floatValue = datainput.readFloat();
    }

    public byte getType() {
        return 5;
    }

    public String toString() {
        return (new StringBuilder()).append("").append(floatValue).toString();
    }
}
