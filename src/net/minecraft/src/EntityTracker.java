package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;
import src.net.minecraft.MinecraftServer;

public class EntityTracker
{

    public EntityTracker(MinecraftServer minecraftserver)
    {
        field_911_a = new HashSet();
        field_910_b = new MCHashTable();
        mcServer = minecraftserver;
        field_912_d = minecraftserver.configManager.func_640_a();
    }

    public void func_611_a(Entity entity)
    {
        if(entity instanceof EntityPlayerMP)
        {
            func_6187_a(entity, 512, 2);
            EntityPlayerMP entityplayermp = (EntityPlayerMP)entity;
            Iterator iterator = field_911_a.iterator();
            do
            {
                if(!iterator.hasNext())
                {
                    break;
                }
                EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
                if(entitytrackerentry.field_909_a != entityplayermp)
                {
                    entitytrackerentry.func_606_a(entityplayermp);
                }
            } while(true);
        } else
        if(entity instanceof EntityFish)
        {
            func_6186_a(entity, 64, 5, true);
        } else
        if(entity instanceof EntityArrow)
        {
            func_6186_a(entity, 64, 5, true);
        } else
        if(entity instanceof EntitySnowball)
        {
            func_6186_a(entity, 64, 5, true);
        } else
        if(entity instanceof EntityItem)
        {
            func_6186_a(entity, 64, 20, true);
        } else
        if(entity instanceof EntityMinecart)
        {
            func_6186_a(entity, 160, 5, true);
        } else
        if(entity instanceof EntityBoat)
        {
            func_6186_a(entity, 160, 5, true);
        } else
        if(entity instanceof IAnimals)
        {
            func_6187_a(entity, 160, 3);
        } else
        if(entity instanceof EntityTNTPrimed)
        {
            func_6186_a(entity, 160, 10, true);
        }
    }

    public void func_6187_a(Entity entity, int i, int j)
    {
        func_6186_a(entity, i, j, false);
    }

    public void func_6186_a(Entity entity, int i, int j, boolean flag)
    {
        if(i > field_912_d)
        {
            i = field_912_d;
        }
        if(field_910_b.containsItem(entity.field_331_c))
        {
            throw new IllegalStateException("Entity is already tracked!");
        } else
        {
            EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(entity, i, j, flag);
            field_911_a.add(entitytrackerentry);
            field_910_b.addKey(entity.field_331_c, entitytrackerentry);
            entitytrackerentry.func_601_b(mcServer.overworld.playerEntities);
            return;
        }
    }

    public void DeleteEntityFromTracker(Entity entity){
        if(field_910_b.containsItem(entity.field_331_c)){
            //int EntityIndex = field_910_b.
            field_910_b.removeObject(entity.field_331_c);
        }
    }

    public void func_610_b(Entity entity)
    {
        if(entity instanceof EntityPlayerMP)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)entity;
            EntityTrackerEntry entitytrackerentry1;
            for(Iterator iterator = field_911_a.iterator(); iterator.hasNext(); entitytrackerentry1.func_12019_a(entityplayermp))
            {
                entitytrackerentry1 = (EntityTrackerEntry)iterator.next();
            }

        }
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)field_910_b.removeObject(entity.field_331_c);
        if(entitytrackerentry != null)
        {
            field_911_a.remove(entitytrackerentry);
            entitytrackerentry.func_604_a();
        }
    }

    public void func_607_a()
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = field_911_a.iterator();
        do
        {
            if(!iterator.hasNext())
            {
                break;
            }
            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
            entitytrackerentry.func_605_a(mcServer.overworld.playerEntities);
            if(entitytrackerentry.field_900_j && (entitytrackerentry.field_909_a instanceof EntityPlayerMP))
            {
                arraylist.add((EntityPlayerMP)entitytrackerentry.field_909_a);
            }
        } while(true);
label0:
        for(int i = 0; i < arraylist.size(); i++)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)arraylist.get(i);
            Iterator iterator1 = field_911_a.iterator();
            do
            {
                if(!iterator1.hasNext())
                {
                    continue label0;
                }
                EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)iterator1.next();
                if(entitytrackerentry1.field_909_a != entityplayermp)
                {
                    entitytrackerentry1.func_606_a(entityplayermp);
                }
            } while(true);
        }

    }

    public void func_12021_a(Entity entity, Packet packet)
    {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)field_910_b.lookup(entity.field_331_c);
        if(entitytrackerentry != null)
        {
            entitytrackerentry.func_603_a(packet);
        }
    }

    public void func_609_a(Entity entity, Packet packet)
    {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)field_910_b.lookup(entity.field_331_c);
        if(entitytrackerentry != null)
        {
            entitytrackerentry.func_12018_b(packet);
        }
    }

    public void func_9238_a(EntityPlayerMP entityplayermp)
    {
        EntityTrackerEntry entitytrackerentry;
        for(Iterator iterator = field_911_a.iterator(); iterator.hasNext(); entitytrackerentry.func_9219_b(entityplayermp))
        {
            entitytrackerentry = (EntityTrackerEntry)iterator.next();
        }

    }

    private Set field_911_a;
    private MCHashTable field_910_b;
    private MinecraftServer mcServer;
    private int field_912_d;
}
