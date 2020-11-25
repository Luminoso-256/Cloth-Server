package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet5PlayerInventory extends Packet
{

    public Packet5PlayerInventory()
    {
    }

    public Packet5PlayerInventory(int i, ItemStack aitemstack[])
    {
        type = i;
        stacks = new ItemStack[aitemstack.length];
        for(int j = 0; j < stacks.length; j++)
        {
            stacks[j] = aitemstack[j] != null ? aitemstack[j].copy() : null;
        }

    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        type = datainputstream.readInt();
        short word0 = datainputstream.readShort();
        stacks = new ItemStack[word0];
        for(int i = 0; i < word0; i++)
        {
            short word1 = datainputstream.readShort();
            if(word1 >= 0)
            {
                byte byte0 = datainputstream.readByte();
                short word2 = datainputstream.readShort();
                stacks[i] = new ItemStack(word1, byte0, word2);
            }
        }

    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(type);
        dataoutputstream.writeShort(stacks.length);
        for(int i = 0; i < stacks.length; i++)
        {
            if(stacks[i] == null)
            {
                dataoutputstream.writeShort(-1);
            } else
            {
                dataoutputstream.writeShort((short)stacks[i].itemID);
                dataoutputstream.writeByte((byte)stacks[i].stackSize);
                dataoutputstream.writeShort((short)stacks[i].itemDamage);
            }
        }

    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handlePlayerInventory(this);
    }

    public int getPacketSize()
    {
        return 6 + stacks.length * 5;
    }

    public int type;
    public ItemStack stacks[];
}
