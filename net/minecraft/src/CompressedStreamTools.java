package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressedStreamTools
{

    public CompressedStreamTools()
    {
    }

    public static NBTTagCompound func_770_a(InputStream inputstream) throws IOException
    {
        DataInputStream datainputstream = new DataInputStream(new GZIPInputStream(inputstream));
        try
        {
            NBTTagCompound nbttagcompound = func_774_a(datainputstream);
            return nbttagcompound;
        }
        finally
        {
            datainputstream.close();
        }
    }

    public static void func_769_a(NBTTagCompound nbttagcompound, OutputStream outputstream) throws IOException
    {
        DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(outputstream));
        try
        {
            func_771_a(nbttagcompound, dataoutputstream);
        }
        finally
        {
            dataoutputstream.close();
        }
    }

    public static NBTTagCompound func_773_a(byte abyte0[]) throws IOException
    {
        DataInputStream datainputstream = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte0)));
        try
        {
            NBTTagCompound nbttagcompound = func_774_a(datainputstream);
            return nbttagcompound;
        }
        finally
        {
            datainputstream.close();
        }
    }

    public static byte[] func_772_a(NBTTagCompound nbttagcompound) throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(bytearrayoutputstream));
        try
        {
            func_771_a(nbttagcompound, dataoutputstream);
        }
        finally
        {
            dataoutputstream.close();
        }
        return bytearrayoutputstream.toByteArray();
    }

    public static NBTTagCompound func_774_a(DataInput datainput) throws IOException
    {
        NBTBase nbtbase = NBTBase.readTag(datainput);
        if(nbtbase instanceof NBTTagCompound)
        {
            return (NBTTagCompound)nbtbase;
        } else
        {
            throw new IOException("Root tag must be a named compound tag");
        }
    }

    public static void func_771_a(NBTTagCompound nbttagcompound, DataOutput dataoutput) throws IOException
    {
        NBTBase.writeTag(nbttagcompound, dataoutput);
    }
}
