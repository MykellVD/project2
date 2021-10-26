package project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GUI1024 extends JFrame{
    private final JMenuBar menuBar;
    private GUI1024Panel panel;

    //Menubar, menu, and all the items
    private final JMenu menu;
    private final JMenuItem undoButton,highScore, newGame, resizeBoard, difWinValue, exitGame;

    public GUI1024(GUI1024Panel panel) {
        setTitle("Welcome to 2048!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.panel = panel;

        add(this.panel);

        setSize(800, 451);

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

        //shows the highscore FIX
        highScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "The Current Highscore Is: " + panel.highscore());
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
                panel.resizeBoard();
            }
        });

        //give input for user to change the winning value
        difWinValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.difWinValue();
            }
        });

        //exits the game
        exitGame.addActionListener(e -> System.exit(0));

    }

    public static void main(String args[]){
        GUI1024Panel panel = new GUI1024Panel();
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new GUI1024(panel).setVisible(true);
            }
        });
    }
}
