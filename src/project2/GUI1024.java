package project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI1024 extends JFrame{

    private static GUI1024Panel panel;
    private static GUI1024ButtonsPanel buttonsPanel;
    private final JPanel highscorePanel;
    private final JMenuBar menuBar;

    //Menubar, menu, and all the items
    private final JMenu menu;
    private final JMenuItem undoButton,highScore, newGame, resizeBoard, difWinValue, exitGame;

    public GUI1024(GUI1024Panel panel, GUI1024ButtonsPanel buttonPanel) {
        setTitle("Welcome to 2048!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        splitPanel = new JPanel();

        this.panel = panel;
        highscorePanel = new JPanel();
        this.buttonsPanel = buttonPanel;

        SpringLayout layout = new SpringLayout();

        getContentPane().setLayout(layout);

        getContentPane().add(highscorePanel);
        highscorePanel.setSize(200,500);

        getContentPane().add(this.panel);
        this.panel.setSize(500,500);

        getContentPane().add(this.buttonsPanel);
        this.buttonsPanel.setSize(200,500);

        layout.putConstraint(SpringLayout.WEST, highscorePanel, 3,
                SpringLayout.WEST, getContentPane());
        layout.putConstraint(SpringLayout.NORTH, highscorePanel, 3,
                SpringLayout.NORTH, getContentPane());

        layout.putConstraint(SpringLayout.WEST, this.panel, 3,
                SpringLayout.WEST, highscorePanel);
        layout.putConstraint(SpringLayout.NORTH, this.panel, 3,
                SpringLayout.NORTH, getContentPane());

        layout.putConstraint(SpringLayout.WEST, this.buttonsPanel, 3,
                SpringLayout.WEST, this.panel);
        layout.putConstraint(SpringLayout.NORTH, this.buttonsPanel, 3,
                SpringLayout.NORTH, getContentPane());

        setSize(900,500);

//        pack();

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


    public static void main(String args[]){
        GUI1024Panel panel = new GUI1024Panel();
        GUI1024ButtonsPanel buttonPanel = new GUI1024ButtonsPanel();
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new GUI1024(panel, buttonPanel).setVisible(true);
            }
        });
    }
}
