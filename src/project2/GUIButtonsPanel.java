package project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIButtonsPanel extends JPanel {
    private JButton undoBut, newGameBut, resizeBut, winValBut;
    private Font myTextFont4Char = new Font(Font.SANS_SERIF, Font.BOLD, 24);

    public GUIButtonsPanel(GUI1024Board board, GUIinfoPanel infoPanel) {

        setLayout(new GridLayout(4, 1));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(80, 180));

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
                board.undo();
                infoPanel.updateAfterSlide();
            }
        });

        newGameBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.reset();
                infoPanel.updateAfterSlide();
                infoPanel.updateHighestScore();
                infoPanel.updateNumPlays();
            }
        });

        resizeBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int boardSizeRow = getBoardSize("Row");
                int boardSizeCol = getBoardSize("Column");
                board.changeBoardSize(boardSizeRow, boardSizeCol);
                infoPanel.updateAfterSlide();
                infoPanel.updateHighestScore();
                infoPanel.updateNumPlays();
            }
        });

        winValBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int winningValue = getWinningVal();
                board.changeWinningVal(winningValue);
            }
        });
    }

    public int getWinningVal() {

        int winningValue = Integer.parseInt(JOptionPane.showInputDialog("Enter the Amount of New Winning Value"));
        if (winningValue % 2 != 0)
            getWinningVal();
        return winningValue;
    }

    public int getBoardSize(String side) {
        int boardSize = Integer.parseInt(JOptionPane.showInputDialog("Enter Amount of " + side + " Between 3 and 10"));
        if (boardSize < 3 || boardSize > 10) {
            getBoardSize(side);
        }
        return boardSize;
    }
}
