package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public enum EnumMobType {
    everything,
    mobs,
    players;
/*
    public static EnumMobType[] func_4110_values()
    {
        return (EnumMobType[])field_990_d.clone();
    }

    public static EnumMobType valueOf(String s)
    {
        return (EnumMobType)Enum.valueOf(EnumMobType.class, s);
    }

    private EnumMobType(String s, int i)
    {
        super(s, i);
    }

    public static final EnumMobType everything;
    public static final EnumMobType mobs;
    public static final EnumMobType players;
    private static final EnumMobType field_990_d[]; /* synthetic field */
/*
    static 
    {
        everything = new EnumMobType("everything", 0);
        mobs = new EnumMobType("mobs", 1);
        players = new EnumMobType("players", 2);
        field_990_d = (new EnumMobType[] {
            everything, mobs, players
        });
    }
*/
}
