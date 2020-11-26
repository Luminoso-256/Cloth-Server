package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;
import java.util.List;

class MinecartTrackLogic
{

    public MinecartTrackLogic(BlockMinecartTrack blockminecarttrack, World world, int i, int j, int k)
    {
        field_888_a = blockminecarttrack;
        field_889_g = new ArrayList();
        worldObj = world;
        field_893_c = i;
        field_892_d = j;
        field_891_e = k;
        field_890_f = world.getBlockMetadata(i, j, k);
        func_593_a();
    }

    private void func_593_a()
    {
        field_889_g.clear();
        if(field_890_f == 0)
        {
            field_889_g.add(new ChunkPosition(field_893_c, field_892_d, field_891_e - 1));
            field_889_g.add(new ChunkPosition(field_893_c, field_892_d, field_891_e + 1));
        } else
        if(field_890_f == 1)
        {
            field_889_g.add(new ChunkPosition(field_893_c - 1, field_892_d, field_891_e));
            field_889_g.add(new ChunkPosition(field_893_c + 1, field_892_d, field_891_e));
        } else
        if(field_890_f == 2)
        {
            field_889_g.add(new ChunkPosition(field_893_c - 1, field_892_d, field_891_e));
            field_889_g.add(new ChunkPosition(field_893_c + 1, field_892_d + 1, field_891_e));
        } else
        if(field_890_f == 3)
        {
            field_889_g.add(new ChunkPosition(field_893_c - 1, field_892_d + 1, field_891_e));
            field_889_g.add(new ChunkPosition(field_893_c + 1, field_892_d, field_891_e));
        } else
        if(field_890_f == 4)
        {
            field_889_g.add(new ChunkPosition(field_893_c, field_892_d + 1, field_891_e - 1));
            field_889_g.add(new ChunkPosition(field_893_c, field_892_d, field_891_e + 1));
        } else
        if(field_890_f == 5)
        {
            field_889_g.add(new ChunkPosition(field_893_c, field_892_d, field_891_e - 1));
            field_889_g.add(new ChunkPosition(field_893_c, field_892_d + 1, field_891_e + 1));
        } else
        if(field_890_f == 6)
        {
            field_889_g.add(new ChunkPosition(field_893_c + 1, field_892_d, field_891_e));
            field_889_g.add(new ChunkPosition(field_893_c, field_892_d, field_891_e + 1));
        } else
        if(field_890_f == 7)
        {
            field_889_g.add(new ChunkPosition(field_893_c - 1, field_892_d, field_891_e));
            field_889_g.add(new ChunkPosition(field_893_c, field_892_d, field_891_e + 1));
        } else
        if(field_890_f == 8)
        {
            field_889_g.add(new ChunkPosition(field_893_c - 1, field_892_d, field_891_e));
            field_889_g.add(new ChunkPosition(field_893_c, field_892_d, field_891_e - 1));
        } else
        if(field_890_f == 9)
        {
            field_889_g.add(new ChunkPosition(field_893_c + 1, field_892_d, field_891_e));
            field_889_g.add(new ChunkPosition(field_893_c, field_892_d, field_891_e - 1));
        }
    }

    private void func_591_b()
    {
        for(int i = 0; i < field_889_g.size(); i++)
        {
            MinecartTrackLogic minecarttracklogic = func_595_a((ChunkPosition)field_889_g.get(i));
            if(minecarttracklogic == null || !minecarttracklogic.func_590_b(this))
            {
                field_889_g.remove(i--);
            } else
            {
                field_889_g.set(i, new ChunkPosition(minecarttracklogic.field_893_c, minecarttracklogic.field_892_d, minecarttracklogic.field_891_e));
            }
        }

    }

    private boolean func_589_a(int i, int j, int k)
    {
        if(worldObj.getBlockId(i, j, k) == field_888_a.blockID)
        {
            return true;
        }
        if(worldObj.getBlockId(i, j + 1, k) == field_888_a.blockID)
        {
            return true;
        }
        return worldObj.getBlockId(i, j - 1, k) == field_888_a.blockID;
    }

    private MinecartTrackLogic func_595_a(ChunkPosition chunkposition)
    {
        if(worldObj.getBlockId(chunkposition.field_846_a, chunkposition.field_845_b, chunkposition.field_847_c) == field_888_a.blockID)
        {
            return new MinecartTrackLogic(field_888_a, worldObj, chunkposition.field_846_a, chunkposition.field_845_b, chunkposition.field_847_c);
        }
        if(worldObj.getBlockId(chunkposition.field_846_a, chunkposition.field_845_b + 1, chunkposition.field_847_c) == field_888_a.blockID)
        {
            return new MinecartTrackLogic(field_888_a, worldObj, chunkposition.field_846_a, chunkposition.field_845_b + 1, chunkposition.field_847_c);
        }
        if(worldObj.getBlockId(chunkposition.field_846_a, chunkposition.field_845_b - 1, chunkposition.field_847_c) == field_888_a.blockID)
        {
            return new MinecartTrackLogic(field_888_a, worldObj, chunkposition.field_846_a, chunkposition.field_845_b - 1, chunkposition.field_847_c);
        } else
        {
            return null;
        }
    }

    private boolean func_590_b(MinecartTrackLogic minecarttracklogic)
    {
        for(int i = 0; i < field_889_g.size(); i++)
        {
            ChunkPosition chunkposition = (ChunkPosition)field_889_g.get(i);
            if(chunkposition.field_846_a == minecarttracklogic.field_893_c && chunkposition.field_847_c == minecarttracklogic.field_891_e)
            {
                return true;
            }
        }

        return false;
    }

    private boolean func_599_b(int i, int j, int k)
    {
        for(int l = 0; l < field_889_g.size(); l++)
        {
            ChunkPosition chunkposition = (ChunkPosition)field_889_g.get(l);
            if(chunkposition.field_846_a == i && chunkposition.field_847_c == k)
            {
                return true;
            }
        }

        return false;
    }

    private int func_594_c()
    {
        int i = 0;
        if(func_589_a(field_893_c, field_892_d, field_891_e - 1))
        {
            i++;
        }
        if(func_589_a(field_893_c, field_892_d, field_891_e + 1))
        {
            i++;
        }
        if(func_589_a(field_893_c - 1, field_892_d, field_891_e))
        {
            i++;
        }
        if(func_589_a(field_893_c + 1, field_892_d, field_891_e))
        {
            i++;
        }
        return i;
    }

    private boolean func_597_c(MinecartTrackLogic minecarttracklogic)
    {
        if(func_590_b(minecarttracklogic))
        {
            return true;
        }
        if(field_889_g.size() == 2)
        {
            return false;
        }
        if(field_889_g.size() == 0)
        {
            return true;
        }
        ChunkPosition chunkposition = (ChunkPosition)field_889_g.get(0);
        return minecarttracklogic.field_892_d != field_892_d || chunkposition.field_845_b != field_892_d ? true : true;
    }

    private void func_598_d(MinecartTrackLogic minecarttracklogic)
    {
        field_889_g.add(new ChunkPosition(minecarttracklogic.field_893_c, minecarttracklogic.field_892_d, minecarttracklogic.field_891_e));
        boolean flag = func_599_b(field_893_c, field_892_d, field_891_e - 1);
        boolean flag1 = func_599_b(field_893_c, field_892_d, field_891_e + 1);
        boolean flag2 = func_599_b(field_893_c - 1, field_892_d, field_891_e);
        boolean flag3 = func_599_b(field_893_c + 1, field_892_d, field_891_e);
        byte byte0 = -1;
        if(flag || flag1)
        {
            byte0 = 0;
        }
        if(flag2 || flag3)
        {
            byte0 = 1;
        }
        if(flag1 && flag3 && !flag && !flag2)
        {
            byte0 = 6;
        }
        if(flag1 && flag2 && !flag && !flag3)
        {
            byte0 = 7;
        }
        if(flag && flag2 && !flag1 && !flag3)
        {
            byte0 = 8;
        }
        if(flag && flag3 && !flag1 && !flag2)
        {
            byte0 = 9;
        }
        if(byte0 == 0)
        {
            if(worldObj.getBlockId(field_893_c, field_892_d + 1, field_891_e - 1) == field_888_a.blockID)
            {
                byte0 = 4;
            }
            if(worldObj.getBlockId(field_893_c, field_892_d + 1, field_891_e + 1) == field_888_a.blockID)
            {
                byte0 = 5;
            }
        }
        if(byte0 == 1)
        {
            if(worldObj.getBlockId(field_893_c + 1, field_892_d + 1, field_891_e) == field_888_a.blockID)
            {
                byte0 = 2;
            }
            if(worldObj.getBlockId(field_893_c - 1, field_892_d + 1, field_891_e) == field_888_a.blockID)
            {
                byte0 = 3;
            }
        }
        if(byte0 < 0)
        {
            byte0 = 0;
        }
        worldObj.setBlockMetadataWithNotify(field_893_c, field_892_d, field_891_e, byte0);
    }

    private boolean func_592_c(int i, int j, int k)
    {
        MinecartTrackLogic minecarttracklogic = func_595_a(new ChunkPosition(i, j, k));
        if(minecarttracklogic == null)
        {
            return false;
        } else
        {
            minecarttracklogic.func_591_b();
            return minecarttracklogic.func_597_c(this);
        }
    }

    public void func_596_a(boolean flag)
    {
        boolean flag1 = func_592_c(field_893_c, field_892_d, field_891_e - 1);
        boolean flag2 = func_592_c(field_893_c, field_892_d, field_891_e + 1);
        boolean flag3 = func_592_c(field_893_c - 1, field_892_d, field_891_e);
        boolean flag4 = func_592_c(field_893_c + 1, field_892_d, field_891_e);
        int i = -1;
        if((flag1 || flag2) && !flag3 && !flag4)
        {
            i = 0;
        }
        if((flag3 || flag4) && !flag1 && !flag2)
        {
            i = 1;
        }
        if(flag2 && flag4 && !flag1 && !flag3)
        {
            i = 6;
        }
        if(flag2 && flag3 && !flag1 && !flag4)
        {
            i = 7;
        }
        if(flag1 && flag3 && !flag2 && !flag4)
        {
            i = 8;
        }
        if(flag1 && flag4 && !flag2 && !flag3)
        {
            i = 9;
        }
        if(i == -1)
        {
            if(flag1 || flag2)
            {
                i = 0;
            }
            if(flag3 || flag4)
            {
                i = 1;
            }
            if(flag)
            {
                if(flag2 && flag4)
                {
                    i = 6;
                }
                if(flag3 && flag2)
                {
                    i = 7;
                }
                if(flag4 && flag1)
                {
                    i = 9;
                }
                if(flag1 && flag3)
                {
                    i = 8;
                }
            } else
            {
                if(flag1 && flag3)
                {
                    i = 8;
                }
                if(flag4 && flag1)
                {
                    i = 9;
                }
                if(flag3 && flag2)
                {
                    i = 7;
                }
                if(flag2 && flag4)
                {
                    i = 6;
                }
            }
        }
        if(i == 0)
        {
            if(worldObj.getBlockId(field_893_c, field_892_d + 1, field_891_e - 1) == field_888_a.blockID)
            {
                i = 4;
            }
            if(worldObj.getBlockId(field_893_c, field_892_d + 1, field_891_e + 1) == field_888_a.blockID)
            {
                i = 5;
            }
        }
        if(i == 1)
        {
            if(worldObj.getBlockId(field_893_c + 1, field_892_d + 1, field_891_e) == field_888_a.blockID)
            {
                i = 2;
            }
            if(worldObj.getBlockId(field_893_c - 1, field_892_d + 1, field_891_e) == field_888_a.blockID)
            {
                i = 3;
            }
        }
        if(i < 0)
        {
            i = 0;
        }
        field_890_f = i;
        func_593_a();
        worldObj.setBlockMetadataWithNotify(field_893_c, field_892_d, field_891_e, i);
        for(int j = 0; j < field_889_g.size(); j++)
        {
            MinecartTrackLogic minecarttracklogic = func_595_a((ChunkPosition)field_889_g.get(j));
            if(minecarttracklogic == null)
            {
                continue;
            }
            minecarttracklogic.func_591_b();
            if(minecarttracklogic.func_597_c(this))
            {
                minecarttracklogic.func_598_d(this);
            }
        }

    }

    static int func_600_a(MinecartTrackLogic minecarttracklogic)
    {
        return minecarttracklogic.func_594_c();
    }

    private World worldObj;
    private int field_893_c;
    private int field_892_d;
    private int field_891_e;
    private int field_890_f;
    private List field_889_g;
    final BlockMinecartTrack field_888_a; /* synthetic field */
}
