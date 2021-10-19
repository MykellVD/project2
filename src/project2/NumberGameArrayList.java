package project2;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class NumberGameArrayList implements NumberSlider {
	// Create a 2d ArrayList that holds Cell objects
	private ArrayList< ArrayList<Cell> > board = new ArrayList<>();
	private ArrayList<Cell> NonEmptyCells = new ArrayList<>();
	private ArrayList<Cell> EmptyCells = new ArrayList<>();
	private Stack<int[][]> savedBoards = new Stack<>();
	private Stack<Integer> savedScores = new Stack<>(); //dont think we will need

	private Random rand = new Random();
	private int prevRandRow;
	private int prevRandCol;

	private int height;
	private int width;
	private int winningValue;
	private int score;

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
	public void reset() { //add vars
		//resets all Cell objects values to 0
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				//clears each cell
				board.get(row).set(col, new Cell(row, col, 0));
//				board.get(row).get(col).value = 0;
			}
		}
		placeRandomValue();
		placeRandomValue();
		savedBoards.clear();
		savedScores.clear();
		saveBoard();
		//System.out.println(board.get(3).get(2).getValue()); //REMOVE checks to see if row 3 col 2 = 0
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
		while (!EmptyCells.isEmpty()) {
			if (EmptyCells.size() > 2) {
				cell = EmptyCells.get(rand.nextInt(EmptyCells.size() - 1));

				if (cell.row != prevRandRow || cell.column != prevRandCol)
					break;
			} else {
				cell = EmptyCells.get(0);
				break;
			}

		}
		if (cell != null) {
			if (rand.nextInt(2) == 0)
				board.get(cell.row).get(cell.column).value = 2;
			else
				board.get(cell.row).get(cell.column).value = 4;

			return board.get( cell.row).get( cell.column);
		}
		else
			return null;


	}


	@Override
	public boolean slide(SlideDirection dir) {
		getNonEmptyTiles();
		//printBoard();
		if (dir.equals(SlideDirection.LEFT)) {
			boolean bool = SlideLeft();
			placeRandomValue();
			saveBoard();
			return bool;
		}
		if (dir.equals(SlideDirection.UP)) {
			boolean bool = SlideUp();
			placeRandomValue();
			saveBoard();
			return bool;
		}
		if (dir.equals(SlideDirection.RIGHT)) {
			boolean bool = SlideRight();
			placeRandomValue();
			saveBoard();
			return bool;
		}
		if (dir.equals(SlideDirection.DOWN)) {
			boolean bool = SlideDown();
			placeRandomValue();
			saveBoard();
			return bool;
		}
		return false;
	}

	private boolean SlideLeft() {
		for (int x = 0; x < height; x++) {
//			System.out.println(x);
			for (int row = height-1; row >= 0; row--) {
				for (int col = width-1; col > 0; col--) {
					//current index isnt empty
					if (board.get(row).get(col).value != 0) {

						if (board.get(row).get(col - 1).value == 0) {
							board.get(row).set(col - 1, new Cell(row, col - 1, board.get(row).get(col).value));
//							board.get(row).get(col - 1).value = board.get(row).get(col).value;
							board.get(row).set(col, new Cell(row, col, 0));
//							board.get(row).get(col).value = 0;
						}



						if (board.get(row).get(col - 1).value == board.get(row).get(col).value) {
							board.get(row).set(col - 1, new Cell(row, col - 1, board.get(row).get(col).value * 2));
//							board.get(row).get(col - 1).value = board.get(row).get(col).value * 2;
							board.get(row).set(col, new Cell(row, col, 0));
//							board.get(row).get(col).value = 0;
						}

//						System.out.println("After manipulation");
						//printBoard();
					}
				}
			}

		}

//		return previousBoards.get(previousBoards.size()-2).equals(board);

		return true;
	}

	private boolean SlideUp() {
		for (int x = 0; x < height; x++) {
			for (int row = 1; row < height; row++) {
				for (int col = 0; col < width; col++) {
					//current index isnt empty
					if (board.get(row).get(col).value != 0) {

						if (board.get(row - 1).get(col).value == 0) {
							board.get(row - 1).set(col, new Cell(row - 1, col, board.get(row).get(col).value));
//							board.get(row - 1).get(col).value = board.get(row).get(col).value;
							board.get(row).set(col, new Cell(row, col, 0));
//							board.get(row).get(col).value = 0;
						}



						if (board.get(row - 1).get(col).value == board.get(row).get(col).value) {
							board.get(row - 1).set(col, new Cell(row - 1, col, board.get(row).get(col).value * 2));
//							board.get(row - 1).get(col).value = board.get(row).get(col).value * 2;
							board.get(row).set(col, new Cell(row, col, 0));
//							board.get(row).get(col).value = 0;
						}

//						System.out.println("After manipulation");
						//printBoard();
					}
				}
			}
		}

//		return previousBoards.get(previousBoards.size()-2).equals(board);

		return true;
	}

	private boolean SlideRight() {
		for (int x = 0; x < height; x++) {
			for (int row = height - 1; row > -1; row--) {
				for (int col = width - 2; col > -1; col--) {
					//				[[2,0,0,0]] -> [[0,2,0,0]] -> [[0,0,2,0]] -> [[0,0,0,2]]
					if (board.get(row).get(col).value != 0) {

						if (board.get(row).get(col + 1).value == 0) {
							board.get(row).set(col + 1, new Cell(row, col + 1, board.get(row).get(col).value));
//							board.get(row).get(col + 1).value = board.get(row).get(col).value;
							board.get(row).set(col, new Cell(row, col, 0));
//							board.get(row).get(col).value = 0;
						}

						if (board.get(row).get(col + 1).value == board.get(row).get(col).value) {
							board.get(row).set(col + 1, new Cell(row, col + 1, board.get(row).get(col).value * 2));
//							board.get(row).get(col + 1).value = board.get(row).get(col).value * 2;
							board.get(row).set(col, new Cell(row, col, 0));
//							board.get(row).get(col).value = 0;
						}

//						System.out.println("After manipulation");
						//printBoard();
					}
				}
			}
		}

//		return previousBoards.get(previousBoards.size()-2).equals(board);

		return true;
	}

	private boolean SlideDown() {
		for (int x = 0; x < height; x++) {
			for (int row = height - 2; row > -1; row--) {
				for (int col = width - 1; col > -1; col--) {
					//				[[2,0,0,0]] -> [[0,2,0,0]] -> [[0,0,2,0]] -> [[0,0,0,2]]
					if (board.get(row).get(col).value != 0) {

						if (board.get(row + 1).get(col).value == 0) {
							board.get(row + 1).set(col, new Cell(row + 1, col, board.get(row).get(col).value));
//							board.get(row + 1).get(col).value = board.get(row).get(col).value;
							board.get(row).set(col, new Cell(row, col, 0));
//							board.get(row).get(col).value = 0;
						}

						if (board.get(row + 1).get(col).value == board.get(row).get(col).value) {
							board.get(row + 1).set(col, new Cell(row + 1, col, board.get(row).get(col).value * 2));
//							board.get(row + 1).get(col).value = board.get(row).get(col).value * 2;
							board.get(row).set(col, new Cell(row, col, 0));
//							board.get(row).get(col).value = 0;
						}

//						System.out.println("After manipulation");
						//printBoard();
					}
				}
			}
		}

//		return previousBoards.get(previousBoards.size()-2).equals(board);

		return true;
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
		getNonEmptyTiles();
		boolean userLost = false;
		for (int row = 0; row < height; row++)
			for (int col = 0; col < width; col++)
				if (board.get(row).get(col).value == winningValue) {
					return GameStatus.USER_WON;
				}
		boolean lose = true;
		if (EmptyCells.size() == 0) {
			for (int row = 1; row < height - 1; row++) {
				for (int col = 0; col < width; col++) {
					Cell cur = board.get(row).get(col);
//					Remove
//					System.out.printf("(%d, %d):%d = (%d, %d):%d - %b\n", board.get(row).get(col).row, board.get(row).get(col).column, board.get(row).get(col).value, board.get(row-1).get(col).row, board.get(row-1).get(col).column, board.get(row-1).get(col).value, cur.equals(board.get(row - 1).get(col)));
//					System.out.printf("(%d, %d):%d = (%d, %d):%d - %b\n", board.get(row).get(col).row, board.get(row).get(col).column, board.get(row).get(col).value, board.get(row+1).get(col).row, board.get(row+1).get(col).column, board.get(row+1).get(col).value, cur.equals(board.get(row + 1).get(col)));
					if (cur.equals(board.get(row - 1).get(col)))
						return GameStatus.IN_PROGRESS;

					if (cur.equals(board.get(row + 1).get(col)))
						return GameStatus.IN_PROGRESS;
				}
			}
			for (int row = 0; row < height; row++) {
				for (int col = 1; col < width - 1; col++) {
					Cell cur = board.get(row).get(col);
//					Remove
//					System.out.printf("(%d, %d):%d = (%d, %d):%d - %b\n", board.get(row).get(col).row, board.get(row).get(col).column, board.get(row).get(col).value, board.get(row).get(col-1).row, board.get(row).get(col-1).column, board.get(row).get(col-1).value, cur.equals(board.get(row).get(col - 1)));
//					System.out.printf("(%d, %d):%d = (%d, %d):%d - %b\n", board.get(row).get(col).row, board.get(row).get(col).column, board.get(row).get(col).value, board.get(row).get(col+1).row, board.get(row).get(col+1).column, board.get(row).get(col+1).value, cur.equals(board.get(row).get(col + 1)));
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
		for (int i = 0; i < NonEmptyCells.size()-1; i++) {
			total += NonEmptyCells.get(0).value;
		}
		return total;
	}

	public void addHighscore() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter("highscores.txt")));
			out.println();
		}
		catch (FileNotFoundException e) {
			throw new IllegalArgumentException();
		}
		catch (IOException e){
			throw new IllegalArgumentException();
		}
		finally{
			out.close();
		}
	}

	public String[] getHighscoreList() {
		String[] highscores = new String[10];
		try {
			BufferedReader reader = new BufferedReader(new FileReader("highscores.txt"));

			highscores[0] = reader.readLine();
			highscores[1] = reader.readLine();
			highscores[2] = reader.readLine();
			highscores[3] = reader.readLine();
			highscores[4] = reader.readLine();
			highscores[5] = reader.readLine();
			highscores[6] = reader.readLine();
			highscores[7] = reader.readLine();
			highscores[8] = reader.readLine();
			highscores[9] = reader.readLine();


		} catch (IOException ex) {
			System.err.println("ERROR reading scores from file");
		}

		System.out.println(highscores);

		return highscores;
	}
}
