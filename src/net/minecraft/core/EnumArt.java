package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public enum EnumArt
{
    Kebab("Kebab", 16, 16, 0, 0),
    Aztec("Aztec", 16, 16, 16, 0),
    Alban("Alban", 16, 16, 32, 0),
    Aztec2("Aztec2", 16, 16, 48, 0),
    Bomb("Bomb", 16, 16, 64, 0),
    Plant("Plant", 16, 16, 80, 0),
    Wasteland("Wasteland", 16, 16, 96, 0),
    Pool("Pool", 32, 16, 0, 32),
    Courbet("Courbet", 32, 16, 32, 32),
    Sea("Sea", 32, 16, 64, 32),
    Sunset("Sunset", 32, 16, 96, 32),
    Creebet("Creebet", 32, 16, 128, 32),
    Wanderer("Wanderer", 16, 32, 0, 64),
    Graham("Graham", 16, 32, 16, 64),
    Match("Match", 32, 32, 0, 128),
    Bust("Bust", 32, 32, 32, 128),
    Stage("Stage", 32, 32, 64, 128),
    Void("Void", 32, 32, 96, 128),
    SkullAndRoses("SkullAndRoses", 32, 32, 128, 128),
    Fighters("Fighters", 64, 32, 0, 96),
    Pointer("Pointer", 64, 64, 0, 192),
    Pigscene("Pigscene", 64, 64, 64, 192),
    Skeleton("Skeleton", 64, 48, 192, 64),
    DonkeyKong("DonkeyKong", 64, 48, 192, 112);
/*
    public static EnumArt[] func_580_values()
    {
        return (EnumArt[])field_863_D.clone();
    }

    public static EnumArt valueOf(String s)
    {
        return (EnumArt)Enum.valueOf(EnumArt.class, s);
    }
*/
    private EnumArt(String s1, int j, int k, int l, int i1)
    {
        field_857_y = s1;
        field_856_z = j;
        field_869_A = k;
        field_867_B = l;
        field_865_C = i1;
    }
/*
    public static final EnumArt Kebab;
    public static final EnumArt Aztec;
    public static final EnumArt Alban;
    public static final EnumArt Aztec2;
    public static final EnumArt Bomb;
    public static final EnumArt Plant;
    public static final EnumArt Wasteland;
    public static final EnumArt Pool;
    public static final EnumArt Courbet;
    public static final EnumArt Sea;
    public static final EnumArt Sunset;
    public static final EnumArt Creebet;
    public static final EnumArt Wanderer;
    public static final EnumArt Graham;
    public static final EnumArt Match;
    public static final EnumArt Bust;
    public static final EnumArt Stage;
    public static final EnumArt Void;
    public static final EnumArt SkullAndRoses;
    public static final EnumArt Fighters;
    public static final EnumArt Pointer;
    public static final EnumArt Pigscene;
    public static final EnumArt Skeleton;
    public static final EnumArt DonkeyKong;
*/
    public final String field_857_y;
    public final int field_856_z;
    public final int field_869_A;
    public final int field_867_B;
    public final int field_865_C;
/*
    private static final EnumArt field_863_D[]; /* synthetic field */
/*
    static 
    {
        Kebab = new EnumArt("Kebab", 0, "Kebab", 16, 16, 0, 0);
        Aztec = new EnumArt("Aztec", 1, "Aztec", 16, 16, 16, 0);
        Alban = new EnumArt("Alban", 2, "Alban", 16, 16, 32, 0);
        Aztec2 = new EnumArt("Aztec2", 3, "Aztec2", 16, 16, 48, 0);
        Bomb = new EnumArt("Bomb", 4, "Bomb", 16, 16, 64, 0);
        Plant = new EnumArt("Plant", 5, "Plant", 16, 16, 80, 0);
        Wasteland = new EnumArt("Wasteland", 6, "Wasteland", 16, 16, 96, 0);
        Pool = new EnumArt("Pool", 7, "Pool", 32, 16, 0, 32);
        Courbet = new EnumArt("Courbet", 8, "Courbet", 32, 16, 32, 32);
        Sea = new EnumArt("Sea", 9, "Sea", 32, 16, 64, 32);
        Sunset = new EnumArt("Sunset", 10, "Sunset", 32, 16, 96, 32);
        Creebet = new EnumArt("Creebet", 11, "Creebet", 32, 16, 128, 32);
        Wanderer = new EnumArt("Wanderer", 12, "Wanderer", 16, 32, 0, 64);
        Graham = new EnumArt("Graham", 13, "Graham", 16, 32, 16, 64);
        Match = new EnumArt("Match", 14, "Match", 32, 32, 0, 128);
        Bust = new EnumArt("Bust", 15, "Bust", 32, 32, 32, 128);
        Stage = new EnumArt("Stage", 16, "Stage", 32, 32, 64, 128);
        Void = new EnumArt("Void", 17, "Void", 32, 32, 96, 128);
        SkullAndRoses = new EnumArt("SkullAndRoses", 18, "SkullAndRoses", 32, 32, 128, 128);
        Fighters = new EnumArt("Fighters", 19, "Fighters", 64, 32, 0, 96);
        Pointer = new EnumArt("Pointer", 20, "Pointer", 64, 64, 0, 192);
        Pigscene = new EnumArt("Pigscene", 21, "Pigscene", 64, 64, 64, 192);
        Skeleton = new EnumArt("Skeleton", 22, "Skeleton", 64, 48, 192, 64);
        DonkeyKong = new EnumArt("DonkeyKong", 23, "DonkeyKong", 64, 48, 192, 112);
        field_863_D = (new EnumArt[] {
            Kebab, Aztec, Alban, Aztec2, Bomb, Plant, Wasteland, Pool, Courbet, Sea, 
            Sunset, Creebet, Wanderer, Graham, Match, Bust, Stage, Void, SkullAndRoses, Fighters, 
            Pointer, Pigscene, Skeleton, DonkeyKong
        });
    }
*/
}
