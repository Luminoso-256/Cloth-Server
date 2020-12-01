package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.cloth.ExploitUtils;

import java.io.*;

public class Packet17AddToInventory extends Packet
{

    public Packet17AddToInventory()
    {
    }

    public Packet17AddToInventory(ItemStack itemstack, int i)
    {
        id = itemstack.itemID;
        count = i;
        durability = itemstack.itemDamage;
        if(i == 0)
        {
            i = 1;
        }

    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {

        id = datainputstream.readShort();
        ExploitUtils exploitUtils = new ExploitUtils();
        if(!exploitUtils.IsIdValid(id)){
            id = 1;
        }
        count = datainputstream.readByte();
        durability = datainputstream.readShort();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        ExploitUtils exploitUtils = new ExploitUtils();
        if(!exploitUtils.IsIdValid(id)){
            id = 1;
        }
        dataoutputstream.writeShort(id);
        dataoutputstream.writeByte(count);
        dataoutputstream.writeShort(durability);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleAddToInventory(this);
    }

    public int getPacketSize()
    {
        return 5;
    }

    public int id;
    public int count;
    public int durability;
}
