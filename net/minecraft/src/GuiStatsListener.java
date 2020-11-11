package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GuiStatsListener
    implements ActionListener
{

    GuiStatsListener(GuiStatsComponent guistatscomponent)
    {
        statsComponent = guistatscomponent;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        GuiStatsComponent.update(statsComponent);
    }

    final GuiStatsComponent statsComponent; /* synthetic field */
}
