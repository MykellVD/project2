package project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

public class GUI1024Panel extends JPanel{
    private static GUI1024Board board;
    private static GUIButtonsPanel buttonsPanel;
    private static GUIinfoPanel infoPanel;


    public GUI1024Panel() {
        GUI1024Board board = new GUI1024Board();
        GUIinfoPanel infoPanel = new GUIinfoPanel(board);
        GUIButtonsPanel buttonPanel = new GUIButtonsPanel(board, infoPanel);

        this.board = board;
        this.infoPanel = infoPanel;
        this.buttonsPanel = buttonPanel;

        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);

        setBackground(Color.DARK_GRAY);

        setLayout(layout);

        add(this.infoPanel);
        add(this.board);
        add(this.buttonsPanel);

        int preferredWidth = this.infoPanel.getPreferredSize().width + this.board.getPreferredSize().width +
                this.buttonsPanel.getPreferredSize().width;

        int preferredHeight = Math.max(this.infoPanel.getPreferredSize().height, this.board.getPreferredSize().height);
        preferredHeight = Math.max(preferredHeight, this.buttonsPanel.getPreferredSize().height);

        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        System.out.println(getPreferredSize());
        System.out.println(this.infoPanel.getPreferredSize());
        System.out.println(this.board.getPreferredSize());
        System.out.println(this.buttonsPanel.getPreferredSize());
        resizeBoard();

    }

    public void resizeWindow() {
        int preferredWidth = this.infoPanel.getPreferredSize().width + this.board.getPreferredSize().width +
                this.buttonsPanel.getPreferredSize().width;

        int preferredHeight = Math.max(this.infoPanel.getPreferredSize().height, this.board.getPreferredSize().height);
        preferredHeight = Math.max(preferredHeight, this.buttonsPanel.getPreferredSize().height);

        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    }

    public void undo() {
        board.undo();
        infoPanel.updateAfterSlide();
    }

    public String highscore() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###,###");
        return " " + decimalFormat.format(board.gameLogic.getHighestScore());
    }

    public void reset() {
        board.reset();
        infoPanel.updateAfterSlide();
        infoPanel.updateHighestScore();
        infoPanel.updateNumPlays();
    }

    public int getBoardSize(String side) {
        int boardSize = Integer.parseInt(JOptionPane.showInputDialog("Enter Amount of " + side + " Between 3 and 10"));
        if (boardSize < 3 || boardSize > 10) {
            return getBoardSize(side);
        }
        return boardSize;
    }

    public void resizeBoard() {
        int boardSizeRow = getBoardSize("Row");
        int boardSizeCol = getBoardSize("Column");
        board.changeBoardSize(boardSizeRow, boardSizeCol);
        infoPanel.updateAfterSlide();
        infoPanel.updateHighestScore();
        infoPanel.updateNumPlays();
    }

    public void difWinValue() {
        int winningValue = Integer.parseInt(JOptionPane.showInputDialog("Enter the Amount of New Winning Value"));
        if (winningValue % 2 != 0)
            difWinValue();
        else {
            board.changeWinningVal(winningValue);
        }
    }


    public static class SlideListener implements KeyListener, ActionListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {

            boolean moved = false;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    moved = GUI1024Panel.board.gameLogic.slide(SlideDirection.UP);
                    break;
                case KeyEvent.VK_LEFT:
                    moved = GUI1024Panel.board.gameLogic.slide(SlideDirection.LEFT);
                    break;
                case KeyEvent.VK_DOWN:
                    moved = GUI1024Panel.board.gameLogic.slide(SlideDirection.DOWN);
                    break;
                case KeyEvent.VK_RIGHT:
                    moved = GUI1024Panel.board.gameLogic.slide(SlideDirection.RIGHT);
                    break;
                case KeyEvent.VK_U:
                    try {
                        System.out.println("Attempt to undo");
                        GUI1024Panel.board.undo();
                        moved = true;
                    } catch (IllegalStateException exp) {
                        JOptionPane.showMessageDialog(null, "Can't undo beyond the first move");
                        moved = false;
                    }
            }
            if (moved) {
                GUI1024Panel.board.updateBoard();
                infoPanel.updateAfterSlide();
                if (GUI1024Panel.board.gameLogic.getStatus().equals(GameStatus.USER_WON)) {
                    int resp = JOptionPane.showConfirmDialog(null,
                            "              You Won!\nDo you want to play again?",
                            "Noice Job!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (resp == JOptionPane.YES_OPTION) {
                        GUI1024Panel.board.gameLogic.reset();
                        infoPanel.updateNumWins();
                        infoPanel.updateAfterSlide();
                        infoPanel.updateHighestScore();
                        infoPanel.updateNumPlays();
                        GUI1024Panel.board.updateBoard();
                    } else {
                        System.exit(0);
                    }
                } else if (GUI1024Panel.board.gameLogic.getStatus().equals(GameStatus.USER_LOST)) {
                    int resp = JOptionPane.showConfirmDialog(null,
                            "Do you want to play again?", "Bad... Get better...",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (resp == JOptionPane.YES_OPTION) {
                        GUI1024Panel.board.gameLogic.reset();
                        infoPanel.updateAfterSlide();
                        infoPanel.updateHighestScore();
                        infoPanel.updateNumPlays();
                        GUI1024Panel.board.updateBoard();
                    } else {
                        System.exit(0);
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }

    }
}
