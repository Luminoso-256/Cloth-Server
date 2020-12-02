package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import javax.swing.*;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class GuiLogOutputHandler extends Handler {

    Formatter field_999_a;
    private int field_998_b[];
    private int field_1001_c;
    private JTextArea field_1000_d;

    public GuiLogOutputHandler(JTextArea jtextarea) {
        field_998_b = new int[1024];
        field_1001_c = 0;
        field_999_a = new GuiLogFormatter(this);
        setFormatter(field_999_a);
        field_1000_d = jtextarea;
    }

    public void close() {
    }

    public void flush() {
    }

    public void publish(LogRecord logrecord) {
        int i = field_1000_d.getDocument().getLength();
        field_1000_d.append(field_999_a.format(logrecord));
        field_1000_d.setCaretPosition(field_1000_d.getDocument().getLength());
        int j = field_1000_d.getDocument().getLength() - i;
        if (field_998_b[field_1001_c] != 0) {
            field_1000_d.replaceRange("", 0, field_998_b[field_1001_c]);
        }
        field_998_b[field_1001_c] = j;
        field_1001_c = (field_1001_c + 1) % 1024;
    }
}
