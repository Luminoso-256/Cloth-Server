package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class Pathfinder
{

    public Pathfinder(IBlockAccess iblockaccess)
    {
        path = new Path();
        pointMap = new MCHashTable();
        pathOptions = new PathPoint[32];
        worldMap = iblockaccess;
    }

    public PathEntity createEntityPathTo(Entity entity, Entity entity1, float f)
    {
        return createEntityPathTo(entity, entity1.posX, entity1.boundingBox.minY, entity1.posZ, f);
    }

    public PathEntity createEntityPathTo(Entity entity, int i, int j, int k, float f)
    {
        return createEntityPathTo(entity, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, f);
    }

    private PathEntity createEntityPathTo(Entity entity, double d, double d1, double d2, 
            float f)
    {
        path.clearPath();
        pointMap.clearMap();
        PathPoint pathpoint = openPoint(MathHelper.floor_double(entity.boundingBox.minX), MathHelper.floor_double(entity.boundingBox.minY), MathHelper.floor_double(entity.boundingBox.minZ));
        PathPoint pathpoint1 = openPoint(MathHelper.floor_double(d - (double)(entity.width / 2.0F)), MathHelper.floor_double(d1), MathHelper.floor_double(d2 - (double)(entity.width / 2.0F)));
        PathPoint pathpoint2 = new PathPoint(MathHelper.floor_float(entity.width + 1.0F), MathHelper.floor_float(entity.height + 1.0F), MathHelper.floor_float(entity.width + 1.0F));
        PathEntity pathentity = addToPath(entity, pathpoint, pathpoint1, pathpoint2, f);
        return pathentity;
    }

    private PathEntity addToPath(Entity entity, PathPoint pathpoint, PathPoint pathpoint1, PathPoint pathpoint2, float f)
    {
        pathpoint.totalPathDistance = 0.0F;
        pathpoint.distanceToNext = pathpoint.distanceTo(pathpoint1);
        pathpoint.distanceToTarget = pathpoint.distanceToNext;
        path.clearPath();
        path.addPoint(pathpoint);
        PathPoint pathpoint3 = pathpoint;
        while(!path.isPathEmpty()) 
        {
            PathPoint pathpoint4 = path.dequeue();
            if(pathpoint4.hash == pathpoint1.hash)
            {
                return createEntityPath(pathpoint, pathpoint1);
            }
            if(pathpoint4.distanceTo(pathpoint1) < pathpoint3.distanceTo(pathpoint1))
            {
                pathpoint3 = pathpoint4;
            }
            pathpoint4.isFirst = true;
            int i = findPathOptions(entity, pathpoint4, pathpoint2, pathpoint1, f);
            int j = 0;
            while(j < i) 
            {
                PathPoint pathpoint5 = pathOptions[j];
                float f1 = pathpoint4.totalPathDistance + pathpoint4.distanceTo(pathpoint5);
                if(!pathpoint5.isAssigned() || f1 < pathpoint5.totalPathDistance)
                {
                    pathpoint5.previous = pathpoint4;
                    pathpoint5.totalPathDistance = f1;
                    pathpoint5.distanceToNext = pathpoint5.distanceTo(pathpoint1);
                    if(pathpoint5.isAssigned())
                    {
                        path.changeDistance(pathpoint5, pathpoint5.totalPathDistance + pathpoint5.distanceToNext);
                    } else
                    {
                        pathpoint5.distanceToTarget = pathpoint5.totalPathDistance + pathpoint5.distanceToNext;
                        path.addPoint(pathpoint5);
                    }
                }
                j++;
            }
        }
        if(pathpoint3 == pathpoint)
        {
            return null;
        } else
        {
            return createEntityPath(pathpoint, pathpoint3);
        }
    }

    private int findPathOptions(Entity entity, PathPoint pathpoint, PathPoint pathpoint1, PathPoint pathpoint2, float f)
    {
        int i = 0;
        int j = 0;
        if(getVerticalOffset(entity, pathpoint.xCoord, pathpoint.yCoord + 1, pathpoint.zCoord, pathpoint1) > 0)
        {
            j = 1;
        }
        PathPoint pathpoint3 = getSafePoint(entity, pathpoint.xCoord, pathpoint.yCoord, pathpoint.zCoord + 1, pathpoint1, j);
        PathPoint pathpoint4 = getSafePoint(entity, pathpoint.xCoord - 1, pathpoint.yCoord, pathpoint.zCoord, pathpoint1, j);
        PathPoint pathpoint5 = getSafePoint(entity, pathpoint.xCoord + 1, pathpoint.yCoord, pathpoint.zCoord, pathpoint1, j);
        PathPoint pathpoint6 = getSafePoint(entity, pathpoint.xCoord, pathpoint.yCoord, pathpoint.zCoord - 1, pathpoint1, j);
        if(pathpoint3 != null && !pathpoint3.isFirst && pathpoint3.distanceTo(pathpoint2) < f)
        {
            pathOptions[i++] = pathpoint3;
        }
        if(pathpoint4 != null && !pathpoint4.isFirst && pathpoint4.distanceTo(pathpoint2) < f)
        {
            pathOptions[i++] = pathpoint4;
        }
        if(pathpoint5 != null && !pathpoint5.isFirst && pathpoint5.distanceTo(pathpoint2) < f)
        {
            pathOptions[i++] = pathpoint5;
        }
        if(pathpoint6 != null && !pathpoint6.isFirst && pathpoint6.distanceTo(pathpoint2) < f)
        {
            pathOptions[i++] = pathpoint6;
        }
        return i;
    }

    private PathPoint getSafePoint(Entity entity, int i, int j, int k, PathPoint pathpoint, int l)
    {
        PathPoint pathpoint1 = null;
        if(getVerticalOffset(entity, i, j, k, pathpoint) > 0)
        {
            pathpoint1 = openPoint(i, j, k);
        }
        if(pathpoint1 == null && getVerticalOffset(entity, i, j + l, k, pathpoint) > 0)
        {
            pathpoint1 = openPoint(i, j + l, k);
            j += l;
        }
        if(pathpoint1 != null)
        {
            int i1 = 0;
            for(int j1 = 0; j > 0 && (j1 = getVerticalOffset(entity, i, j - 1, k, pathpoint)) > 0; j--)
            {
                if(j1 < 0)
                {
                    return null;
                }
                if(++i1 >= 4)
                {
                    return null;
                }
            }

            if(j > 0)
            {
                pathpoint1 = openPoint(i, j, k);
            }
        }
        return pathpoint1;
    }

    private final PathPoint openPoint(int i, int j, int k)
    {
        int l = i | j << 10 | k << 20;
        PathPoint pathpoint = (PathPoint)pointMap.lookup(l);
        if(pathpoint == null)
        {
            pathpoint = new PathPoint(i, j, k);
            pointMap.addKey(l, pathpoint);
        }
        return pathpoint;
    }

    private int getVerticalOffset(Entity entity, int i, int j, int k, PathPoint pathpoint)
    {
        for(int l = i; l < i + pathpoint.xCoord; l++)
        {
            for(int i1 = j; i1 < j + pathpoint.yCoord; i1++)
            {
                for(int j1 = k; j1 < k + pathpoint.zCoord; j1++)
                {
                    Material material = worldMap.getBlockMaterial(i, j, k);
                    if(material.func_218_c())
                    {
                        return 0;
                    }
                    if(material == Material.water || material == Material.lava)
                    {
                        return -1;
                    }
                }

            }

        }

        return 1;
    }

    private PathEntity createEntityPath(PathPoint pathpoint, PathPoint pathpoint1)
    {
        int i = 1;
        for(PathPoint pathpoint2 = pathpoint1; pathpoint2.previous != null; pathpoint2 = pathpoint2.previous)
        {
            i++;
        }

        PathPoint apathpoint[] = new PathPoint[i];
        PathPoint pathpoint3 = pathpoint1;
        for(apathpoint[--i] = pathpoint3; pathpoint3.previous != null; apathpoint[--i] = pathpoint3)
        {
            pathpoint3 = pathpoint3.previous;
        }

        return new PathEntity(apathpoint);
    }

    private IBlockAccess worldMap;
    private Path path;
    private MCHashTable pointMap;
    private PathPoint pathOptions[];
}
