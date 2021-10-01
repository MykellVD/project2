package project2;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NumberGameArrayList implements NumberSlider {
	// Create a 2d ArrayList that holds Cell objects
	ArrayList< ArrayList<Cell> > board = new ArrayList<>();
	ArrayList<Cell> NonEmptyCells = new ArrayList<>();
	ArrayList<Cell> EmptyCells = new ArrayList<>();

	Random rand = new Random(892349);
	int prevRandRow;
	int prevRandCol;

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
				board.get(row).get(col).value = 0;
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
		getNonEmptyTiles();
		Cell cell;
		while (true) {
			cell = EmptyCells.get(rand.nextInt(EmptyCells.size() - 1));
			System.out.printf("Random Cell (%s): \n Row: %d \n Column: %d\n Value: %d\n", cell, cell.row, cell.column, cell.value);
			System.out.printf("Previous: %d, %d\n", prevRandRow, prevRandCol);
			if (cell.row != prevRandRow || cell.column != prevRandCol) {
				break;
			}
		}



		int random_val = rand.nextInt(2);

		if (random_val == 0)
			board.get( cell.row).get( cell.column).value = 2;
		else
			board.get( cell.row).get( cell.column).value = 4;

		return board.get( cell.row).get( cell.column);

	}


	@Override
	public boolean slide(SlideDirection dir) {
		getNonEmptyTiles();
		if (dir.equals(SlideDirection.LEFT)) {
//			System.out.println("LEFT");
			SlideLeft();
			return true;
		}
		if (dir.equals(SlideDirection.UP)) {
//			System.out.println("UP");
			SlideUp();
			return true;
		}
		if (dir.equals(SlideDirection.RIGHT)) {
//			System.out.println("RIGHT");
			SlideRight();
			return true;
		}
		if (dir.equals(SlideDirection.DOWN)) {
//			System.out.println("DOWN");
			SlideDown();
			return true;
		}
		return false;
	}

	private void SlideLeft() {

		for (int x = 0; x < height; x++) {
			for (int row = 0; row < height; row++) {
				for (int col = 1; col < width; col++) {
					//current index isnt empty
					if (board.get(row).get(col).value != 0) {

						if (board.get(row).get(col - 1).value == 0) {
							board.get(row).set(col - 1, new Cell(row, col - 1, board.get(row).get(col).getValue()));
							board.get(row).get(col).value = 0;
						}



						if (board.get(row).get(col - 1).value == board.get(row).get(col).value) {
//							System.out.println("Combined");
							board.get(row).set(col - 1, new Cell(row, col - 1, board.get(row).get(col).getValue() * 2));
							board.get(row).get(col).value = 0;
						}

//						System.out.println("After manipulation");
//						printBoard();
					}
				}
			}
		}
	}

	private void SlideUp() {
		for (int x = 0; x < height; x++) {
			for (int row = 1; row < height; row++) {
				for (int col = 0; col < width; col++) {
					//current index isnt empty
					if (board.get(row).get(col).value != 0) {

						if (board.get(row - 1).get(col).value == 0) {
							board.get(row - 1).set(col, new Cell(row - 1, col, board.get(row).get(col).getValue()));
							board.get(row).get(col).value = 0;
						}



						if (board.get(row - 1).get(col).value == board.get(row).get(col).value) {
//							System.out.println("Combined");
							board.get(row - 1).set(col, new Cell(row - 1, col, board.get(row).get(col).getValue() * 2));
							board.get(row).get(col).value = 0;
						}

//						System.out.println("After manipulation");
//						printBoard();
					}
				}
			}
		}
	}

	private void SlideRight() {
		for (int x = 0; x < height; x++) {
			for (int row = height - 1; row > -1; row--) {
				for (int col = width - 2; col > -1; col--) {
					//				[[2,0,0,0]] -> [[0,2,0,0]] -> [[0,0,2,0]] -> [[0,0,0,2]]
					if (board.get(row).get(col).value != 0) {

						if (board.get(row).get(col + 1).value == 0) {
							board.get(row).set(col + 1, new Cell(row, col + 1, board.get(row).get(col).getValue()));
							board.get(row).get(col).value = 0;
						}

						if (board.get(row).get(col + 1).value == board.get(row).get(col).value) {
//							System.out.println("Combined");
							board.get(row).set(col + 1, new Cell(row, col + 1, board.get(row).get(col).getValue() * 2));
							board.get(row).get(col).value = 0;
						}

//						System.out.println("After manipulation");
//						printBoard();
					}
				}
			}
		}
	}

	private void SlideDown() {
		for (int x = 0; x < height; x++) {
			for (int row = height - 2; row > -1; row--) {
				for (int col = width - 1; col > -1; col--) {
					//				[[2,0,0,0]] -> [[0,2,0,0]] -> [[0,0,2,0]] -> [[0,0,0,2]]
					if (board.get(row).get(col).value != 0) {

						if (board.get(row + 1).get(col).value == 0) {
							board.get(row + 1).set(col, new Cell(row + 1, col, board.get(row).get(col).getValue()));
							board.get(row).get(col).value = 0;
						}

						if (board.get(row + 1).get(col).value == board.get(row).get(col).value) {
//							System.out.println("Combined");
							board.get(row + 1).set(col, new Cell(row + 1, col, board.get(row).get(col).getValue() * 2));
							board.get(row).get(col).value = 0;
						}

//						System.out.println("After manipulation");
//						printBoard();
					}
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
		NonEmptyCells.clear();
		EmptyCells.clear();

		//adds Cell to NonEmptyCells if its value isnt 0
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (board.get(row).get(col).value != 0) {
					NonEmptyCells.add(board.get(row).get(col));
				} else {
					EmptyCells.add(board.get(row).get(col));
				}
			}

		}

		Collections.shuffle(EmptyCells);

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
