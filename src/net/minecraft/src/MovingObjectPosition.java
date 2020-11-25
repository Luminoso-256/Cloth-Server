package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class MovingObjectPosition
{

    public MovingObjectPosition(int i, int j, int k, int l, Vec3D vec3d)
    {
        typeOfHit = 0;
        blockX = i;
        blockY = j;
        blockZ = k;
        sideHit = l;
        hitVec = Vec3D.createVector(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
    }

    public MovingObjectPosition(Entity entity)
    {
        typeOfHit = 1;
        entityHit = entity;
        hitVec = Vec3D.createVector(entity.posX, entity.posY, entity.posZ);
    }

    public int typeOfHit;
    public int blockX;
    public int blockY;
    public int blockZ;
    public int sideHit;
    public Vec3D hitVec;
    public Entity entityHit;
}
