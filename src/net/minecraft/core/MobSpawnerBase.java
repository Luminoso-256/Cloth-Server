package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class MobSpawnerBase
{

    public MobSpawnerBase()
    {
        topBlock = (byte)Block.grass.blockID;
        fillerBlock = (byte)Block.dirt.blockID;
        field_6161_q = 0x4ee031;
        biomeMonsters = (new Class[] {
            EntitySpider.class, EntityZombie.class, EntitySkeleton.class, EntityCreeper.class
        });
        biomeCreatures = (new Class[] {
            EntitySheep.class, EntityPig.class, EntityChicken.class, EntityCow.class
        });
    }

    public static void generateBiomeLookup()
    {
        for(int i = 0; i < 64; i++)
        {
            for(int j = 0; j < 64; j++)
            {
                biomeLookupTable[i + j * 64] = getBiome((float)i / 63F, (float)j / 63F);
            }

        }

        desert.topBlock = desert.fillerBlock = (byte)Block.sand.blockID;
        iceDesert.topBlock = iceDesert.fillerBlock = (byte)Block.sand.blockID;
    }

    protected MobSpawnerBase func_4083_b()
    {
        return this;
    }

    protected MobSpawnerBase setBiomeName(String s)
    {
        biomeName = s;
        return this;
    }

    protected MobSpawnerBase func_4080_a(int i)
    {
        field_6161_q = i;
        return this;
    }

    protected MobSpawnerBase func_4079_b(int i)
    {
        field_6162_n = i;
        return this;
    }

    public static MobSpawnerBase getBiomeFromLookup(double d, double d1)
    {
        int i = (int)(d * 63D);
        int j = (int)(d1 * 63D);
        return biomeLookupTable[i + j * 64];
    }

    public static MobSpawnerBase getBiome(float f, float f1)
    {
        f1 *= f;
        if(f < 0.1F)
        {
            return tundra;
        }
        if(f1 < 0.2F)
        {
            if(f < 0.5F)
            {
                return tundra;
            }
            if(f < 0.95F)
            {
                return savanna;
            } else
            {
                return desert;
            }
        }
        if(f1 > 0.5F && f < 0.7F)
        {
            return swampland;
        }
        if(f < 0.5F)
        {
            return taiga;
        }
        if(f < 0.97F)
        {
            if(f1 < 0.35F)
            {
                return shrubland;
            } else
            {
                return forest;
            }
        }
        if(f1 < 0.45F)
        {
            return plains;
        }
        if(f1 < 0.9F)
        {
            return seasonalForest;
        } else
        {
            return rainforest;
        }
    }

    public Class[] getEntitiesForType(EnumCreatureType enumcreaturetype)
    {
        if(enumcreaturetype == EnumCreatureType.monster)
        {
            return biomeMonsters;
        }
        if(enumcreaturetype == EnumCreatureType.creature)
        {
            return biomeCreatures;
        } else
        {
            return null;
        }
    }

    public static final MobSpawnerBase rainforest = (new MobSpawnerBase()).func_4079_b(0x8fa36).setBiomeName("Rainforest").func_4080_a(0x1ff458);
    public static final MobSpawnerBase swampland = (new MobSpawnerSwamp()).func_4079_b(0x7f9b2).setBiomeName("Swampland").func_4080_a(0x8baf48);
    public static final MobSpawnerBase seasonalForest = (new MobSpawnerBase()).func_4079_b(0x9be023).setBiomeName("Seasonal Forest");
    public static final MobSpawnerBase forest = (new MobSpawnerBase()).func_4079_b(0x56621).setBiomeName("Forest").func_4080_a(0x4eba31);
    public static final MobSpawnerBase savanna = (new MobSpawnerDesert()).func_4079_b(0xd9e023).setBiomeName("Savanna");
    public static final MobSpawnerBase shrubland = (new MobSpawnerBase()).func_4079_b(0xa1ad20).setBiomeName("Shrubland");
    public static final MobSpawnerBase taiga = (new MobSpawnerBase()).func_4079_b(0x2eb153).setBiomeName("Taiga").func_4083_b().func_4080_a(0x7bb731);
    public static final MobSpawnerBase desert = (new MobSpawnerDesert()).func_4079_b(0xfa9418).setBiomeName("Desert");
    public static final MobSpawnerBase plains = (new MobSpawnerDesert()).func_4079_b(0xffd910).setBiomeName("Plains");
    public static final MobSpawnerBase iceDesert = (new MobSpawnerDesert()).func_4079_b(0xffed93).setBiomeName("Ice Desert").func_4083_b().func_4080_a(0xc4d339);
    public static final MobSpawnerBase tundra = (new MobSpawnerBase()).func_4079_b(0x57ebf9).setBiomeName("Tundra").func_4083_b().func_4080_a(0xc4d339);
    public static final MobSpawnerBase hell = (new MobSpawnerHell()).func_4079_b(0xff0000).setBiomeName("Hell");
    public String biomeName;
    public int field_6162_n;
    public byte topBlock;
    public byte fillerBlock;
    public int field_6161_q;
    protected Class biomeMonsters[];
    protected Class biomeCreatures[];
    private static MobSpawnerBase biomeLookupTable[] = new MobSpawnerBase[4096];

    static 
    {
        generateBiomeLookup();
    }
}
