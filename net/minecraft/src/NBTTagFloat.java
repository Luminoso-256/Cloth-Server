package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class NBTTagFloat extends NBTBase
{

    public NBTTagFloat()
    {
    }

    public NBTTagFloat(float f)
    {
        floatValue = f;
    }

    void writeTagContents(DataOutput dataoutput) throws IOException
    {
        dataoutput.writeFloat(floatValue);
    }

    void readTagContents(DataInput datainput) throws IOException
    {
        floatValue = datainput.readFloat();
    }

    public byte getType()
    {
        return 5;
    }

    public String toString()
    {
        return (new StringBuilder()).append("").append(floatValue).toString();
    }

    public float floatValue;
}
