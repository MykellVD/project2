package project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUIButtonsPanel extends JPanel {
    private JButton undoBut, newGameBut, resizeBut, winValBut;
    private GUI1024Panel panel;
    private Font myTextFont4Char = new Font(Font.SANS_SERIF, Font.BOLD, 24);

    public GUIButtonsPanel(GUI1024Panel panel) {
        this.panel = panel;

        setLayout(new GridLayout(4, 1));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.BLACK);

        undoBut = new JButton("Undo");
        newGameBut = new JButton("New Game");
        resizeBut = new JButton("Resize Board");
        winValBut = new JButton("End Value");

        add(undoBut);
        add(newGameBut);
        add(resizeBut);
        add(winValBut);

        Component[] component = getComponents();

        for (int i = 0; i < component.length; i++) {
            component[i].setFont(myTextFont4Char);
            component[i].setBackground(Color.DARK_GRAY);
            component[i].setForeground(Color.WHITE);
            component[i].setFocusable(false);
        }

        undoBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.undo();
            }
        });

        newGameBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.reset();
            }
        });

        resizeBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int boardSizeRow = Integer.parseInt(JOptionPane.showInputDialog("Please Select the Amount of Rows"));
                int boardSizeCol = Integer.parseInt(JOptionPane.showInputDialog("Please Select the Amount if Columns"));
                panel.changeBoardSize(boardSizeRow, boardSizeCol);
            }
        });

        winValBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int winningValue = Integer.parseInt(JOptionPane.showInputDialog("Please Select a New Winning Value"));
                panel.changeWinningVal(winningValue);
            }
        });
    }

}
