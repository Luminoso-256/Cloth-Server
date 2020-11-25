package src.net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class StepSound
{

    public StepSound(String s, float f, float f1)
    {
        field_1029_a = s;
        field_1028_b = f;
        field_1030_c = f1;
    }

    public float func_738_a()
    {
        return field_1028_b;
    }

    public float func_739_b()
    {
        return field_1030_c;
    }

    public String func_737_c()
    {
        return (new StringBuilder()).append("step.").append(field_1029_a).toString();
    }

    public final String field_1029_a;
    public final float field_1028_b;
    public final float field_1030_c;
}
