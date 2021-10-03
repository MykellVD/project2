package CISProjects.Project2;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GUI1024Panel extends JPanel {

	private JLabel[][] gameBoardUI;
	private NumberGameArrayList gameLogic;

	public GUI1024Panel() {
		gameLogic = new NumberGameArrayList();
		gameLogic.resizeBoard(4, 4, 2048);

		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setLayout(new GridLayout(4, 4));

		gameBoardUI = new JLabel[4][4];
		setBackground(Color.DARK_GRAY);

		Font myTextFont = new Font(Font.SANS_SERIF, Font.BOLD, 40);
		for (int k = 0; k < gameBoardUI.length; k++)
			for (int m = 0; m < gameBoardUI[k].length; m++) {
				gameBoardUI[k][m] = new JLabel();
				gameBoardUI[k][m].setFont(myTextFont);
				gameBoardUI[k][m].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				gameBoardUI[k][m].setPreferredSize(new Dimension(100, 100));
				add(gameBoardUI[k][m]);
			}

		gameLogic.reset();
		gameLogic.placeRandomValue();
		gameLogic.placeRandomValue();
		updateBoard();
		setFocusable(true);
		addKeyListener(new SlideListener());
	}

	private void updateBoard() {
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
			System.out.println(String.valueOf(Math.abs(c.value)));
			z.setForeground(c.value > 0 ? Color.WHITE : Color.RED);
			if (c.value == 2){
				z.setForeground(Color.GRAY);
			}
			if (c.value == 4){
				z.setForeground(Color.LIGHT_GRAY);
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
				z.setForeground(Color.RED);
			}
		}
	}

	private class SlideListener implements KeyListener, ActionListener {
		@Override
		public void keyTyped(KeyEvent e) { }

		@Override
		public void keyPressed(KeyEvent e) {

			boolean moved = false;
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					moved = gameLogic.slide(SlideDirection.UP);
					break;
				case KeyEvent.VK_LEFT:
					moved = gameLogic.slide(SlideDirection.LEFT);
					break;
				case KeyEvent.VK_DOWN:
					moved = gameLogic.slide(SlideDirection.DOWN);
					break;
				case KeyEvent.VK_RIGHT:
					moved = gameLogic.slide(SlideDirection.RIGHT);
					break;
				case KeyEvent.VK_U:
					try {
						System.out.println("Attempt to undo");
						gameLogic.undo();
						moved = true;
					} catch (IllegalStateException exp) {
						JOptionPane.showMessageDialog(null, "Can't undo beyond the first move");
						moved = false;
					}
			}
			if (moved) {
				updateBoard();
				gameLogic.placeRandomValue();
				updateBoard();
//                System.out.println("MOVED");
				if (gameLogic.getStatus().equals(GameStatus.USER_WON))
					JOptionPane.showMessageDialog(null, "You won");
				else if (gameLogic.getStatus().equals(GameStatus.USER_LOST)) {
					int resp = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "TentOnly Over!",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (resp == JOptionPane.YES_OPTION) {
						gameLogic.reset();
						gameLogic.placeRandomValue();
						gameLogic.placeRandomValue();
						updateBoard();
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