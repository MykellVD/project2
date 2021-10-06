package CISProjects.Project2;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class GUI1024 {
    public static void main(String arg[]){
        JMenuBar menuBar;
        JMenu menu;
        JMenu undoButton;
        JMenuItem highScore;
        JMenuItem newGame;
        JMenuItem resizeBoard;


        JFrame gui = new JFrame ("Welcome to 1024!");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        gui.setJMenuBar(menuBar);

        menu = new JMenu("Menu");
        undoButton = new JMenu("Undo");
        highScore = new JMenuItem("High Score");
        newGame = new JMenuItem("New Game");
        resizeBoard = new JMenuItem("Resize Board");

        menuBar.add(menu);
        menuBar.add(undoButton);
        menu.add(highScore);
        menu.add(newGame);
        menu.add(resizeBoard);



        GUI1024Panel panel = new GUI1024Panel();
        //panel.setFocusable(true);
        gui.getContentPane().add(panel);

        gui.setSize(500, 500);
        gui.setVisible(true);
    }
}
