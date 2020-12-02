package net.minecraft.core;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.MinecraftServer;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.logging.Logger;

import static net.minecraft.Globals.TARGET_FEATURE;
import static net.minecraft.Globals.VERSION_STRING;

public class ServerGUI extends JComponent
        implements ICommandListener {

    public static Logger logger = Logger.getLogger("Minecraft");
    private MinecraftServer mcServer;

    public ServerGUI(MinecraftServer minecraftserver) {
        mcServer = minecraftserver;
        setPreferredSize(new Dimension(854, 480));
        setLayout(new BorderLayout());
        try {
            add(getLogComponent(), "Center");
            add(getStatsComponent(), "West");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void initGui(MinecraftServer minecraftserver) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
        }
        ServerGUI servergui = new ServerGUI(minecraftserver);
        JFrame jframe = new JFrame("Cloth-Server | " + VERSION_STRING + " - branch: " + TARGET_FEATURE);
        jframe.add(servergui);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        jframe.addWindowListener(new ServerWindowAdapter(minecraftserver));
    }

    static MinecraftServer getMinecraftServer(ServerGUI servergui) {
        return servergui.mcServer;
    }

    private JComponent getStatsComponent() {
        JPanel jpanel = new JPanel(new BorderLayout());
        jpanel.add(new GuiStatsComponent(), "North");
        jpanel.add(getPlayerListComponent(), "Center");
        jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
        return jpanel;
    }

    private JComponent getPlayerListComponent() {
        PlayerListBox playerlistbox = new PlayerListBox(mcServer);
        JScrollPane jscrollpane = new JScrollPane(playerlistbox, 22, 30);
        jscrollpane.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
        return jscrollpane;
    }

    private JComponent getLogComponent() {
        JPanel jpanel = new JPanel(new BorderLayout());
        JTextArea jtextarea = new JTextArea();
        logger.addHandler(new GuiLogOutputHandler(jtextarea));
        JScrollPane jscrollpane = new JScrollPane(jtextarea, 22, 30);
        jtextarea.setEditable(false);
        JTextField jtextfield = new JTextField();
        jtextfield.addActionListener(new ServerGuiCommandListener(this, jtextfield));
        jtextarea.addFocusListener(new ServerGuiFocusAdapter(this));
        jpanel.add(jscrollpane, "Center");
        jpanel.add(jtextfield, "South");
        jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
        return jpanel;
    }

    public void log(String s) {
        logger.info(s);
    }

    public String getUsername() {
        return "CONSOLE";
    }

}
