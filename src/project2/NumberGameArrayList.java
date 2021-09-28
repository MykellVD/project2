package project2;

import java.util.ArrayList;
import java.util.Random;

public class NumberGameArrayList implements NumberSlider {
	// Create a 2d ArrayList that holds Cell objects
	ArrayList< ArrayList<Cell> > board = new ArrayList<>();
	int height;
	int width;
	int winningValue;

	@Override
	public void resizeBoard(int height, int width, int winningValue) {
		// errors out if winningValue isnt a multiple of 2
		if (winningValue % 2 == 0 && winningValue < 0) {
			throw new IllegalArgumentException();
		} else {
			//sets base values for later
			this.height = height;
			this.width = width;
			this.winningValue = winningValue;

			//creates a 2d ArrayList containing Cell objects
			for (int row = 0; row < height; row++) {
				//adds ArrayList to board
				board.add(new ArrayList<>(width));
				for (int col = 0; col < width; col++) {
					//adds Cell using row, column, value = 0 to each board element
					board.get(row).add(new Cell(row, col, 0));
				}
			}
			//REMOVE test to see if row and column are set correctly
			//System.out.println(board.get(3).get(2).getRow() + " " + board.get(3).get(2).getColumn());
		}
	}

	@Override
	public void reset() {
		//resets all Cell objects values to 0
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				//set Cell using row, column, value = 0 to each element
				board.get(row).get(col).setValue(0);
			}
		}
		//System.out.println(board.get(3).get(2).getValue()); //REMOVE checks to see if row 3 col 2 = 0
	}

	@Override
	public void setValues(int[][] ref) {

	}

	@Override
	public Cell placeRandomValue() {
		while (true) {
			//creates random vars
			Random rand = new Random();
			int random_row = rand.nextInt(3);
			int random_col = rand.nextInt(3);
			//determines if value will be a 2 or a 4
			int random_val = rand.nextInt(2);

			//System.out.println(random_row + " " + random_col + " " + random_val); //REMOVE

			//sets the value of a random row and column to a 2 or a 4 if its an empty cell
			if (board.get(random_row).get(random_col).getValue() == 0) {
				if (random_val == 0)
					board.get(random_row).get(random_col).setValue(2);
				else
					board.get(random_row).get(random_col).setValue(4);

				//System.out.println(board.get(random_row).get(random_col).getValue()); //REMOVE
				return board.get(random_row).get(random_col);
			}
		}
	}

	@Override
	public boolean slide(SlideDirection dir) {
		if (dir.equals(SlideDirection.LEFT)) {
			System.out.println("LEFT");
			return true;
		}
		if (dir.equals(SlideDirection.UP)) {
			System.out.println("UP");
			return true;
		}
		if (dir.equals(SlideDirection.RIGHT)) {
			System.out.println("RIGHT");
			return true;
		}
		if (dir.equals(SlideDirection.DOWN)) {
			System.out.println("DOWN");
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<Cell> getNonEmptyTiles() {
		ArrayList<Cell> NonEmptyCells = new ArrayList<>();
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (board.get(row).get(col).getValue() != 0) {
					NonEmptyCells.add(board.get(row).get(col));
				}
			}
			System.out.println(NonEmptyCells.get(row).getRow() + ", " + NonEmptyCells.get(row).getColumn() +  ", " + NonEmptyCells.get(row).getValue());
		}
		return NonEmptyCells;
	}

	@Override
	public GameStatus getStatus() {
		return GameStatus.IN_PROGRESS;
	}

	@Override
	public void undo() {

	}
}
