package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet1Login extends Packet {

    public int protocolVersion;
    public String username;
    public String password;
    public long mapSeed;
    public byte dimension;

    public Packet1Login() {
    }

    public Packet1Login(String s, String s1, int i, long l, byte byte0) {
        username = s;
        password = s1;
        protocolVersion = i;
        mapSeed = l;
        dimension = byte0;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        protocolVersion = datainputstream.readInt();
        username = datainputstream.readUTF();
        password = datainputstream.readUTF();
        mapSeed = datainputstream.readLong();
        dimension = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(protocolVersion);
        dataoutputstream.writeUTF(username);
        dataoutputstream.writeUTF(password);
        dataoutputstream.writeLong(mapSeed);
        // The client doesn't properly support anything other than 0.
        // For instance, if 1 is sent, when the player respawns, the client will crash.
        dataoutputstream.writeByte(0);
    }

    public void processPacket(NetHandler nethandler) {
        nethandler.handleLogin(this);
    }

    public int getPacketSize() {
        return 4 + username.length() + password.length() + 4 + 5;
    }
}
