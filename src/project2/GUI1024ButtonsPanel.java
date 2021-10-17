package project2;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GUI1024ButtonsPanel extends JPanel {
    private JButton undoBut, newGameBut, resizeBut, winValBut;
    private JLabel score;

    private Font myTextFont = new Font(Font.SANS_SERIF, Font.BOLD, 80);
    private Font myTextFont3Char = new Font(Font.SANS_SERIF, Font.BOLD, 60);
    private Font myTextFont4Char = new Font(Font.SANS_SERIF, Font.BOLD, 48);

    public GUI1024ButtonsPanel() {
        setLayout(new GridLayout(4, 2));
        undoBut = new JButton("undo");
        add(undoBut);

    }

}
