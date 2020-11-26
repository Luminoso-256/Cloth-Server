package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public enum EnumCreatureType
{
    monster(IMobs.class, 100),
    creature(EntityAnimals.class, 20);
/*
    public static EnumCreatureType[] func_4054_values()
    {
        return (EnumCreatureType[])field_6155_e.clone();
    }

    public static EnumCreatureType valueOf(String s)
    {
        return (EnumCreatureType)Enum.valueOf(EnumCreatureType.class, s);
    }
*/
    private EnumCreatureType(Class class1, int j)
    {
        field_4221_c = class1;
        field_4220_d = j;
    }
/*
    public static final EnumCreatureType monster;
    public static final EnumCreatureType creature;
*/
    public final Class field_4221_c;
    public final int field_4220_d;
/*
    private static final EnumCreatureType field_6155_e[]; /* synthetic field */
/*
    static 
    {
        monster = new EnumCreatureType("monster", 0, IMobs.class, 100);
        creature = new EnumCreatureType("creature", 1, EntityAnimals.class, 20);
        field_6155_e = (new EnumCreatureType[] {
            monster, creature
        });
    }
*/
}
