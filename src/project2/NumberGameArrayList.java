package CISProjects.Project2;


import java.util.ArrayList;
import java.util.Random;

public class NumberGameArrayList implements NumberSlider {
	// Create a 2d ArrayList that holds Cell objects
	ArrayList< ArrayList<Cell> > board = new ArrayList<>();
	ArrayList<Cell> NonEmptyCells = new ArrayList<>();
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
				//clears each cell
				board.get(row).get(col).clear();
			}
		}
		//System.out.println(board.get(3).get(2).getValue()); //REMOVE checks to see if row 3 col 2 = 0
	}

	@Override
	public void setValues(int[][] ref) {
		int[][] temp = new int[ref.length][ref.length];
		for (int row = 0; row < ref[row].length - 1; row++) {
			for (int col = 0; col < ref[col].length - 1; col++) {
				temp[row][col] = ref[row][col];
			}
		}
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
			SlideRight();
			return true;
		}
		if (dir.equals(SlideDirection.DOWN)) {
			System.out.println("DOWN");
			return true;
		}
		return false;
	}

	private void SlideRight() {
		getNonEmptyTiles();
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width-1; col++) {
//				[[2,0,0,0]] -> [[0,2,0,0]] -> [[0,0,2,0]] -> [[0,0,0,2]]
				if (board.get(row).get(col).value != 0) {
					System.out.println("Before manipulation");
					printBoard();

					if (board.get(row).get(col+1).value == 0)
						board.get(row).set(col + 1, new Cell(row, col + 1, board.get(row).get(col).getValue()));
						board.get(row).set(col, new Cell());

					System.out.println("After manipulation");
					printBoard();
				}
			}
		}
	}

	public void printBoard() {
		String NUM_FORMAT = String.format("%%%dd", 4);
		String BLANK_FORMAT = "%" + (4) + "s";
		for (int k = 0; k < height; k++) {
			for (int m = 0; m < width; m++)
				if (board.get(k).get(m).value == 0)
					System.out.printf (BLANK_FORMAT, ".");
				else
					System.out.printf (NUM_FORMAT, board.get(k).get(m).value);
			System.out.println();
		}
		System.out.println();
	}

	@Override
	public ArrayList<Cell> getNonEmptyTiles() {
		//Clears NonEmptyCells to not have duplicates after sliding
		for (int i = 0; i < NonEmptyCells.size(); i++){
			NonEmptyCells.clear();
		}

		//adds Cell to NonEmptyCells if its value isnt 0
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (board.get(row).get(col).value != 0) {
					NonEmptyCells.add(board.get(row).get(col));
				}
			}

		}

		return NonEmptyCells;
	}

	@Override
	public GameStatus getStatus() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (board.get(row).get(col).getValue() == winningValue) {
					return GameStatus.USER_WON;
				}
			}
		}
		//All cells are filled with a value that isnt 0
//		if (NonEmptyCells.size() == height * width) {
//			for (int row = 0; row < height; row++) {
//				for (int col = 0; col < width; col++) {
//					if (board.get(row).get(col).getValue() == board.get(row).get(col+1).getValue())
//						return G
//				}
//			}
//
//		}
		return GameStatus.IN_PROGRESS;
	}

	@Override
	public void undo() {

	}
}
