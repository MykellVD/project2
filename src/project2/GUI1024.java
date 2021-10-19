package project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GUI1024 extends JFrame{

    private static GUI1024Panel panel;
    private static GUIButtonsPanel buttonsPanel;
    private final GUIHighscorePanel highscorePanel;
    private final JMenuBar menuBar;

    //Menubar, menu, and all the items
    private final JMenu menu;
    private final JMenuItem undoButton,highScore, newGame, resizeBoard, difWinValue, exitGame;

    public GUI1024(GUI1024Panel panel, GUIButtonsPanel buttonPanel, GUIHighscorePanel highscorePanel) {
        setTitle("Welcome to 2048!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel = panel;
        this.highscorePanel = highscorePanel;
        this.buttonsPanel = buttonPanel;

//        SpringLayout layout = new SpringLayout();
        BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);

        setLayout(layout);

        add(highscorePanel);
        add(this.panel);
        add(this.buttonsPanel);


        setSize(900,500);

        pack();

        //creates the menubar
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //instantiating the menu and all the menuItems
        menu = new JMenu("Menu");
        undoButton = new JMenuItem("Undo");
        highScore = new JMenuItem("High Score");
        newGame = new JMenuItem("New Game");
        resizeBoard = new JMenuItem("Resize Board");
        difWinValue = new JMenuItem("Change Winning Value");
        exitGame = new JMenuItem("Exit Game");

        //adding the JMenuItems
        menuBar.add(menu);
        menu.add(undoButton);
        menu.add(highScore);
        menu.add(newGame);
        menu.add(resizeBoard);
        menu.add(difWinValue);
        menu.add(exitGame);

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.undo();
            }
        });

        //shows the highscore
        highScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "The Current Highscore Is: "); // + "highscore"
            }
        });

        //resets the board
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.reset();
            }
        });

        //gives input for user to change the board size
        resizeBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int boardSizeRow = Integer.parseInt(JOptionPane.showInputDialog("Please Select the Amount of Rows"));
                int boardSizeCol = Integer.parseInt(JOptionPane.showInputDialog("Please Select the Amount if Columns"));
                panel.changeBoardSize(boardSizeRow, boardSizeCol);
                //***extra credit***

            }
        });

        //give input for user to change the winning value
        difWinValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int winningValue = Integer.parseInt(JOptionPane.showInputDialog("Please Select a New Winning Value"));
                panel.changeWinningVal(winningValue);
            }
        });

        //exits the game
        exitGame.addActionListener(e -> System.exit(0));

    }

    public static class SlideListener implements KeyListener, ActionListener {
        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyPressed(KeyEvent e) {

            boolean moved = false;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    moved = panel.gameLogic.slide(SlideDirection.UP);
                    break;
                case KeyEvent.VK_LEFT:
                    moved = panel.gameLogic.slide(SlideDirection.LEFT);
                    break;
                case KeyEvent.VK_DOWN:
                    moved = panel.gameLogic.slide(SlideDirection.DOWN);
                    break;
                case KeyEvent.VK_RIGHT:
                    moved = panel.gameLogic.slide(SlideDirection.RIGHT);
                    break;
                case KeyEvent.VK_U:
                    try {
                        System.out.println("Attempt to undo");
                        panel.gameLogic.undo();
                        moved = true;
                    } catch (IllegalStateException exp) {
                        JOptionPane.showMessageDialog(null, "Can't undo beyond the first move");
                        moved = false;
                    }
            }
            if (moved) {
                panel.updateBoard();
//                System.out.println("MOVED");
                if (panel.gameLogic.getStatus().equals(GameStatus.USER_WON))
                    JOptionPane.showMessageDialog(null, "You won");
                else if (panel.gameLogic.getStatus().equals(GameStatus.USER_LOST)) {
                    int resp = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "TentOnly Over!",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (resp == JOptionPane.YES_OPTION) {
                        panel.gameLogic.reset();
                        panel.gameLogic.placeRandomValue();
                        panel.gameLogic.placeRandomValue();
                        panel.updateBoard();
                    } else {
                        System.exit(0);
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) { }

        @Override
        public void actionPerformed(ActionEvent e) { }


    }

    public static void main(String args[]){
        GUI1024Panel panel = new GUI1024Panel();
        GUIButtonsPanel buttonPanel = new GUIButtonsPanel(panel);
        GUIHighscorePanel highscorePanel = new GUIHighscorePanel(panel);
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new GUI1024(panel, buttonPanel, highscorePanel).setVisible(true);
            }
        });
    }
}
