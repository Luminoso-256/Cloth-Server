package src.net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.HashMap;
import java.util.Map;

public class EntityList
{

    public EntityList()
    {
    }

    private static void func_563_a(Class class1, String s, int i)
    {
        field_849_a.put(s, class1);
        field_848_b.put(class1, s);
        field_851_c.put(Integer.valueOf(i), class1);
        field_850_d.put(class1, Integer.valueOf(i));
    }

    public static Entity func_567_a(String s, World world)
    {
        Entity entity = null;
        try
        {
            Class class1 = (Class)field_849_a.get(s);
            if(class1 != null)
            {
                entity = (Entity)class1.getConstructor(new Class[] {
                    World.class
                }).newInstance(new Object[] {
                    world
                });
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return entity;
    }

    public static Entity func_566_a(NBTTagCompound nbttagcompound, World world)
    {
        Entity entity = null;
        try
        {
            Class class1 = (Class)field_849_a.get(nbttagcompound.getString("id"));
            if(class1 != null)
            {
                entity = (Entity)class1.getConstructor(new Class[] {
                    World.class
                }).newInstance(new Object[] {
                    world
                });
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        if(entity != null)
        {
            entity.readFromNBT(nbttagcompound);
        } else
        {
            System.out.println((new StringBuilder()).append("Skipping Entity with id ").append(nbttagcompound.getString("id")).toString());
        }
        return entity;
    }

    public static int func_565_a(Entity entity)
    {
        return ((Integer)field_850_d.get(entity.getClass())).intValue();
    }

    public static String func_564_b(Entity entity)
    {
        return (String)field_848_b.get(entity.getClass());
    }

    private static Map field_849_a = new HashMap();
    private static Map field_848_b = new HashMap();
    private static Map field_851_c = new HashMap();
    private static Map field_850_d = new HashMap();

    static 
    {
        func_563_a(EntityArrow.class, "Arrow", 10);
        func_563_a(EntitySnowball.class, "Snowball", 11);
        func_563_a(EntityItem.class, "Item", 1);
        func_563_a(EntityPainting.class, "Painting", 9);
        func_563_a(EntityLiving.class, "Mob", 48);
        func_563_a(EntityMobs.class, "Monster", 49);
        func_563_a(EntityCreeper.class, "Creeper", 50);
        func_563_a(EntitySkeleton.class, "Skeleton", 51);
        func_563_a(EntitySpider.class, "Spider", 52);
        func_563_a(EntityZombieSimple.class, "Giant", 53);
        func_563_a(EntityZombie.class, "Zombie", 54);
        func_563_a(EntitySlime.class, "Slime", 55);
        func_563_a(EntityGhast.class, "Ghast", 56);
        func_563_a(EntityPigZombie.class, "PigZombie", 57);
        func_563_a(EntityPig.class, "Pig", 90);
        func_563_a(EntitySheep.class, "Sheep", 91);
        func_563_a(EntityCow.class, "Cow", 92);
        func_563_a(EntityChicken.class, "Chicken", 93);
        func_563_a(EntityTNTPrimed.class, "PrimedTnt", 20);
        func_563_a(EntityFallingSand.class, "FallingSand", 21);
        func_563_a(EntityMinecart.class, "Minecart", 40);
        func_563_a(EntityBoat.class, "Boat", 41);
    }
}
