package project2;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class NumberGameArrayList implements NumberSlider {
	// Create a 2d ArrayList that holds Cell objects
	private ArrayList< ArrayList<Cell> > board = new ArrayList<>();
	private ArrayList<Cell> nonEmptyCells = new ArrayList<>();
	private ArrayList<Cell> emptyCells = new ArrayList<>();
	private Stack<int[][]> savedBoards = new Stack<>();
	private Stack<Integer> savedScores = new Stack<>(); //dont think we will need

	private Random rand = new Random();

	private int height;
	private int width;
	private int winningValue;

	private int score;
	private int numSlides;

	private int numWins;
	private int numPlays;
	private int highestScore;

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

	public void changeBoardSize(int height, int width) {
		//sets base values for later
		this.height = height;
		this.width = width;

		board.clear();

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

	public void changeWinningVal(int winningValue) {
		if (winningValue % 2 == 0 && winningValue < 0) {
			throw new IllegalArgumentException();
		} else {
			this.winningValue = winningValue;
		}
	}

	@Override
	public void reset() {
		//resets all Cell objects values to 0
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				//clears each cell
				board.get(row).set(col, new Cell(row, col, 0));
			}
		}
		savedBoards.clear();
		savedScores.clear();

		score = 0;
		numSlides = 0;

		placeRandomValue();
		placeRandomValue();
		saveBoard();
	}

	@Override
	public void setValues(int[][] ref) {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				board.get(row).get(col).value = ref[row][col];
			}
		}
	}

	@Override
	public Cell placeRandomValue() {
		getNonEmptyTiles();
		Cell cell = null;
		if (!emptyCells.isEmpty()) {
			cell = emptyCells.get(0);
		}
		if (cell != null) {
			if (rand.nextInt(2) == 0)
				board.get(cell.row).get(cell.column).value = 2;
			else
				board.get(cell.row).get(cell.column).value = 4;

			numSlides += 1;
			return board.get( cell.row).get( cell.column);
		}
		else
			return null;
	}


	@Override
	public boolean slide(SlideDirection dir) {
		getNonEmptyTiles();
		boolean bool = false;
		if (dir.equals(SlideDirection.LEFT)) {
			bool = SlideLeft();
			placeRandomValue();
			saveBoard();
		}
		if (dir.equals(SlideDirection.UP)) {
			bool = SlideUp();
			placeRandomValue();
			saveBoard();
		}
		if (dir.equals(SlideDirection.RIGHT)) {
			bool = SlideRight();
			placeRandomValue();
			saveBoard();
		}
		if (dir.equals(SlideDirection.DOWN)) {
			bool = SlideDown();
			placeRandomValue();
			saveBoard();
		}
		return bool;
	}

	private boolean SlideLeft() {
		boolean moved = false;
		for (int x = 0; x < height; x++) {
			for (int row = height-1; row >= 0; row--) {
				for (int col = width-1; col > 0; col--) {
					//current index isnt empty
					if (board.get(row).get(col).value != 0) {

						if (board.get(row).get(col - 1).value == 0) {
							board.get(row).set(col - 1, new Cell(row, col - 1, board.get(row).get(col).value));
							board.get(row).set(col, new Cell(row, col, 0));
							moved = true;
						}

						if (board.get(row).get(col - 1).value == board.get(row).get(col).value) {
							if (!board.get(row).get(col - 1).hasCombined && !board.get(row).get(col).hasCombined) {
								board.get(row).set(col - 1, new Cell(row, col - 1, board.get(row).get(col).value * 2));
								board.get(row).set(col, new Cell(row, col, 0));
								board.get(row).get(col - 1).hasCombined = true;
								moved = true;
							}
						}
					}
				}
			}
		}
		resetHasCombined();
		return moved;
	}

	private boolean SlideUp() {
		boolean moved = false;
		for (int x = 0; x < height; x++) {
			for (int row = 1; row < height; row++) {
				for (int col = 0; col < width; col++) {
					//current index isnt empty
					if (board.get(row).get(col).value != 0) {
						if (board.get(row - 1).get(col).value == 0) {
							board.get(row - 1).set(col, new Cell(row - 1, col, board.get(row).get(col).value));
							board.get(row).set(col, new Cell(row, col, 0));
							moved = true;
						}

						if (board.get(row - 1).get(col).value == board.get(row).get(col).value) {
							if (!board.get(row - 1).get(col).hasCombined && !board.get(row).get(col).hasCombined) {
								board.get(row - 1).set(col, new Cell(row - 1, col, board.get(row).get(col).value * 2));
								board.get(row).set(col, new Cell(row, col, 0));
								board.get(row - 1).get(col).hasCombined = true;
								moved = true;
							}
						}
					}
				}
			}
		}
		resetHasCombined();
		return moved;
	}

	private boolean SlideRight() {
		boolean moved = false;
		printBoard();
		for (int x = 0; x < height; x++) {
			for (int row = height - 1; row > -1; row--) {
				for (int col = width - 2; col > -1; col--) {
					if (board.get(row).get(col).value != 0) {
						if (board.get(row).get(col + 1).value == 0) {
							board.get(row).set(col + 1, new Cell(row, col + 1, board.get(row).get(col).value));
							board.get(row).set(col, new Cell(row, col, 0));
							moved = true;
						}

						if (board.get(row).get(col + 1).value == board.get(row).get(col).value) {
							if (!board.get(row).get(col + 1).hasCombined && !board.get(row).get(col).hasCombined) {
								board.get(row).set(col + 1, new Cell(row, col + 1, board.get(row).get(col).value * 2));
								board.get(row).set(col, new Cell(row, col, 0));
								board.get(row).get(col + 1).hasCombined = true;
								moved = true;
							}
						}
					}
				}
			}
			printBoard();
		}
		resetHasCombined();
		return moved;
	}

	private boolean SlideDown() {
		boolean moved = false;
		for (int x = 0; x < height; x++) {
			for (int row = height - 2; row > -1; row--) {
				for (int col = width - 1; col > -1; col--) {
					if (board.get(row).get(col).value != 0) {
						if (board.get(row + 1).get(col).value == 0) {
							board.get(row + 1).set(col, new Cell(row + 1, col, board.get(row).get(col).value));
							board.get(row).set(col, new Cell(row, col, 0));
							moved = true;
						}

						if (board.get(row + 1).get(col).value == board.get(row).get(col).value) {
							if (!board.get(row + 1).get(col).hasCombined && !board.get(row).get(col).hasCombined) {
								board.get(row + 1).set(col, new Cell(row + 1, col, board.get(row).get(col).value * 2));
								board.get(row).set(col, new Cell(row, col, 0));
								board.get(row + 1).get(col).hasCombined = true;
								moved = true;
							}
						}
					}
				}
			}
		}
		resetHasCombined();
		return moved;
	}

	private void resetHasCombined() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				board.get(row).get(col).hasCombined = false;
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
		nonEmptyCells.clear();
		emptyCells.clear();

		//adds Cell to NonEmptyCells if its value isnt 0
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (board.get(row).get(col).value != 0) {
					nonEmptyCells.add(board.get(row).get(col));
				} else {
					emptyCells.add(board.get(row).get(col));
				}
			}

		}

		Collections.shuffle(emptyCells);

		return nonEmptyCells;
	}

	@Override
	public GameStatus getStatus() {
		getNonEmptyTiles();
		boolean userLost = false;
		for (int row = 0; row < height; row++)
			for (int col = 0; col < width; col++)
				if (board.get(row).get(col).value == winningValue) {
					return GameStatus.USER_WON;
				}
		boolean lose = true;
		if (emptyCells.size() == 0) {
			for (int row = 1; row < height - 1; row++) {
				for (int col = 0; col < width; col++) {
					Cell cur = board.get(row).get(col);
					if (cur.equals(board.get(row - 1).get(col)))
						return GameStatus.IN_PROGRESS;

					if (cur.equals(board.get(row + 1).get(col)))
						return GameStatus.IN_PROGRESS;
				}
			}
			for (int row = 0; row < height; row++) {
				for (int col = 1; col < width - 1; col++) {
					Cell cur = board.get(row).get(col);
					if (cur.equals(board.get(row).get(col - 1)))
						return GameStatus.IN_PROGRESS;

					if (cur.equals(board.get(row).get(col + 1)))
						return GameStatus.IN_PROGRESS;
				}
			}
			return GameStatus.USER_LOST;
		}
		return GameStatus.IN_PROGRESS;
	}

	public void saveBoard() {
		int[][] temp = new int[height][width];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				temp[row][col] = board.get(row).get(col).value;
			}
		}
		savedBoards.push(temp);
		savedScores.push(score);
	}


	@Override
	public void undo() {
		if(savedBoards.size() > 1){

			int[][] previousBoard = savedBoards.get(savedBoards.size()-2);
			savedBoards.pop();

			int previousScore = savedScores.get(savedScores.size()-2);
			savedScores.pop();

			setValues(previousBoard);
			score = previousScore;

		}
	}

	public int getScore() {
		int total = 0;
		for (int i = 0; i < nonEmptyCells.size(); i++) {
			total += nonEmptyCells.get(i).value * nonEmptyCells.get(i).value;
		}
		return total;
	}

	public int getNumSlides() {
		return numSlides;
	}
}
