package project2;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;
public class GUI1024Board extends JPanel {

	private JLabel[][] gameBoardUI;
	public NumberGameArrayList gameLogic;
	private Font myTextFont = new Font(Font.SANS_SERIF, Font.BOLD, 80);

	private JButton undoBut, newGameBut, resizeBut, winValBut;
	private JLabel score;


	public GUI1024Board() {
		gameLogic = new NumberGameArrayList();
		gameLogic.resizeBoard(4, 4, 2048);

		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(new GridLayout(4, 4));

		gameBoardUI = new JLabel[4][4];
		setBackground(Color.DARK_GRAY);


		for (int k = 0; k < gameBoardUI.length; k++)
			for (int m = 0; m < gameBoardUI[k].length; m++) {
				gameBoardUI[k][m] = new JLabel("",SwingConstants.CENTER);
				gameBoardUI[k][m].setFont(myTextFont);
				gameBoardUI[k][m].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				gameBoardUI[k][m].setMinimumSize(new Dimension(100, 100));
				gameBoardUI[k][m].setPreferredSize(new Dimension(200, 140));
				add(gameBoardUI[k][m]);
			}

		undoBut = new JButton("undo");

		gameLogic.reset();
		updateBoard();
		setFocusable(true);
		addKeyListener(new GUI1024Panel.SlideListener());
	}


	public void updateBoard() {
		for (JLabel[] row : gameBoardUI)
			for (JLabel s : row) {
				s.setText("");
				s.setBackground(Color.BLACK);
			}

		ArrayList<Cell> out = gameLogic.getNonEmptyTiles();
		if (out == null) {
			JOptionPane.showMessageDialog(null, "Incomplete implementation getNonEmptyTiles()");
			return;
		}
		for (Cell c : out) {
			JLabel z = gameBoardUI[c.row][c.column];
			z.setText(String.valueOf(Math.abs(c.value)));

			double sideLen = gameLogic.getLargerSide() * .2;
			int fontSize = 80;
			fontSize /= sideLen;
			if (z.getText().length() == 2)
				fontSize -= 10;
			if (z.getText().length() == 3)
				fontSize -= 15;
			if (z.getText().length() == 4)
				fontSize -= 20;

			z.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));

			if (c.value == 2){
				z.setForeground(Color.LIGHT_GRAY);
			}
			if (c.value == 4){
				z.setForeground(Color.WHITE);
			}
			if (c.value == 8){
				z.setForeground(Color.PINK);
			}
			if (c.value == 16){
				z.setForeground(Color.RED);
			}
			if (c.value == 32){
				z.setForeground(Color.MAGENTA);
			}
			if (c.value == 64){
				z.setForeground(Color.ORANGE);
			}
			if (c.value == 128){
				z.setForeground(Color.YELLOW);
			}
			if (c.value == 256){
				z.setForeground(Color.GREEN);
			}
			if (c.value == 512){
				z.setForeground(Color.CYAN);
			}
			if (c.value == 1024){
				z.setForeground(Color.BLUE);
			}
			if (c.value == 2048){
				z.setForeground(new Color(102, 0, 153)); //purple
			}
		}
	}

	public void undo() {
		gameLogic.undo();
		updateBoard();
	}

	public void reset() {
		gameLogic.reset();
		updateBoard();
	}

	public void changeBoardSize(int height, int width) {
		gameLogic.changeBoardSize(height, width);

		removeAll();

		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(new GridLayout(height, width));

		gameBoardUI = new JLabel[height][width];
		setBackground(Color.DARK_GRAY);


		for (int k = 0; k < gameBoardUI.length; k++)
			for (int m = 0; m < gameBoardUI[k].length; m++) {
				gameBoardUI[k][m] = new JLabel("",SwingConstants.CENTER);
				gameBoardUI[k][m].setFont(myTextFont);
				gameBoardUI[k][m].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				gameBoardUI[k][m].setMinimumSize(new Dimension(100, 100));
				add(gameBoardUI[k][m]);
			}

		gameLogic.reset();
		updateBoard();
		setFocusable(true);
		addKeyListener(new GUI1024Panel.SlideListener());
	}

	public void changeWinningVal(int winningVal) {
		gameLogic.changeWinningVal(winningVal);
	}

}