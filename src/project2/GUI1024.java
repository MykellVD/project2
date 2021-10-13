package CISProjects.Project2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;



public class GUI1024 {
    public static void main(String arg[]){
        //Menubar, menu, and all the items
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem undoButton;
        JMenuItem highScore;
        JMenuItem newGame;
        JMenuItem resizeBoard;
        JMenuItem difWinValue;
        JMenuItem exitGame;

        JFrame gui = new JFrame ("Welcome to 1024!");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //creates the menubar
        menuBar = new JMenuBar();
        gui.setJMenuBar(menuBar);

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

        //the undo button
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NumberGameArrayList n = new NumberGameArrayList();
                n.undo();
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
                NumberGameArrayList n = new NumberGameArrayList();
                n.reset();
            }
        });

        //gives input for user to change the board size
        resizeBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NumberGameArrayList n = new NumberGameArrayList();
                int boardSizeRow = Integer.parseInt(JOptionPane.showInputDialog("Please Select the Amount of Rows"));
                int boardSizeCol = Integer.parseInt(JOptionPane.showInputDialog("Please Select the Amount if Columns"));
                n.resizeBoard(boardSizeRow, boardSizeCol, 2048);
                //***extra credit***

            }
        });

        //give input for user to change the winning value
        difWinValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NumberGameArrayList n = new NumberGameArrayList();
                int winningValue = Integer.parseInt(JOptionPane.showInputDialog("Please Select a New Winning Value"));
                n.resizeBoard(4,4, winningValue);
            }
        });

        //exits the game
        exitGame.addActionListener(e -> System.exit(0));

        GUI1024Panel panel = new GUI1024Panel();
        //panel.setFocusable(true);
        gui.getContentPane().add(panel);

        gui.setSize(500, 500);
        gui.setVisible(true);
    }
}
