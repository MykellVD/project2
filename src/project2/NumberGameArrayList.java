package CISProjects.Project2;

/*****************************************************************
 NumberGameArrayList, This is the 2048 game.
 @author Jake Umlor
 @author Michael Van Duine
 @version Fall 2021
 *****************************************************************/

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class NumberGameArrayList implements NumberSlider {
	/** 2d ArrayList of Cells */
	private ArrayList< ArrayList<Cell> > board = new ArrayList<>();

	/** ArrayList of Cells where Cell doesn't hold 0 */
	private ArrayList<Cell> nonEmptyCells = new ArrayList<>();

	/** ArrayList of Cells where Cell holds 0 */
	private ArrayList<Cell> emptyCells = new ArrayList<>();

	/** Stack storing a 2d integer array */
	private Stack<int[][]> savedBoards = new Stack<>();

	/** Random Object */
	private Random rand = new Random();

	/** previous row of random placed Cell */
	private int prevRandRow;

	/** previous column of random placed Cell */
	private int prevRandCol;

	/** height of ArrayList board */
	private int height;

	/** width of ArrayList board */
	private int width;

	/** value the user reaches to win */
	private int winningValue;

	/** current users total score */
	private int score = 0;

	/** number of slides the user gives */
	private int numSlides = 0;

	/** amount of times a user has won */
	private int numWins = 0;

	/** amount of times a user has won, lost, reset */
	private int numPlays = 0;

	/** highest score achieved by the user */
	private int highestScore = 0;

	/*****************************************************************
	 * A function that initializes the instance variables height,
	 * width, winningValue. Initializes the ArrayList board.
	 *
	 * @param height the number of rows in the board
	 * @param width the number of columns in the board
	 * @param winningValue the value that must appear on the board to
	 * win the game
	 * @throws IllegalArgumentException when winningValue isnt a
	 * multiple of 2
	 */
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
		}
	}

	/*****************************************************************
	 * A function that sets the instance variables height,
	 * width. Reinitializes the ArrayList board.
	 *
	 * @param height
	 * @param width
	 */
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

	}

	/*****************************************************************
	 * A function that reinitializes the instance variable
	 * winningValue.
	 *
	 * @param winningValue
	 * @throws IllegalArgumentException when winningValue isnt a
	 * multiple of 2
	 */
	public void changeWinningVal(int winningValue) {
		if (winningValue % 2 == 0 && winningValue < 0) {
			throw new IllegalArgumentException();
		} else {
			this.winningValue = winningValue;
		}
	}

	/*****************************************************************
	 * A function that reinitializes the instances variables board,
	 * score, and numSlides. Clears Stack savedBoards.
	 */
	@Override
	public void reset() {
		board.clear();
		//resets all Cell objects values to 0
		for (int row = 0; row < height; row++) {
			//adds ArrayList to board
			board.add(new ArrayList<>(width));
			for (int col = 0; col < width; col++) {
				//adds Cell using row, column, value = 0 to each board element
				board.get(row).add(new Cell(row, col, 0));
			}
		}
		savedBoards.clear();

		score = 0;
		numSlides = 0;
		updateHighestScore();

		placeRandomValue();
		placeRandomValue();
		saveBoard();
	}

	/*****************************************************************
	 * A method that sets the given 2d array to the Cells stored
	 * in board.
	 *
	 * @param ref
	 */
	@Override
	public void setValues(int[][] ref) {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				board.get(row).get(col).value = ref[row][col];
			}
		}
	}

	/*****************************************************************
	 * A method that sets a random Cells value from board to a
	 * 2 or a 4 determined using Random Object rand. Then returning
	 * the Cell that was changed.
	 *
	 * @return Cell
	 */
	@Override
	public Cell placeRandomValue() {
		getNonEmptyTiles();
		Cell cell = null;
		while (!emptyCells.isEmpty()) {
			if (emptyCells.size() > 2) {
				cell = emptyCells.get(rand.nextInt(emptyCells.size() - 1));
				if (cell.row != prevRandRow || cell.column != prevRandCol)
					break;
			} else {
				cell = emptyCells.get(0);
				break;
			}
		}
		if (cell != null) {
			prevRandRow = cell.row;
			prevRandCol = cell.column;
			if (rand.nextInt(2) == 0)
				board.get(cell.row).get(cell.column).value = 2;
			else
				board.get(cell.row).get(cell.column).value = 4;
			return board.get( cell.row).get( cell.column);
		}
		else
			return null;
	}

	/*****************************************************************
	 * A method that takes in a SlideDirection and returns moved
	 * if slide is successful.
	 *
	 * @param dir move direction of the tiles
	 * @return moved
	 */
	@Override
	public boolean slide(SlideDirection dir) {
		getNonEmptyTiles();
		boolean moved = false;
		if (dir.equals(SlideDirection.LEFT)) {
			moved = SlideLeft();
		}
		if (dir.equals(SlideDirection.UP)) {
			moved = SlideUp();
		}
		if (dir.equals(SlideDirection.RIGHT)) {
			moved = SlideRight();
		}
		if (dir.equals(SlideDirection.DOWN)) {
			moved = SlideDown();
		}
		if (moved) {
			numSlides += 1;
			placeRandomValue();
			saveBoard();
		}
		return moved;
	}

	/*****************************************************************
	 * A method that slides values in board left. Then returns if
	 * slide was successful.
	 *
	 * @return moved
	 */
	private boolean SlideLeft() {
		boolean moved = false;
		for (int x = 0; x < getLargerSide(); x++) {
			for (int row = 0; row <= height-1; row++) {
				for (int col = 1; col <= width-1; col++) {
					//current index isnt empty
					if (board.get(row).get(col).value != 0) {

						if (board.get(row).get(col - 1).value == 0) {
							board.get(row).set(col - 1, new Cell(row, col- 1, board.get(row).get(col).value));
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

	/*****************************************************************
	 * A method that slides values in board up. Then returns if
	 * slide was successful.
	 *
	 * @return moved
	 */
	private boolean SlideUp() {
		boolean moved = false;
		for (int x = 0; x < getLargerSide(); x++) {
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

	/*****************************************************************
	 * A method that slides values in board right. Then returns if
	 * slide was successful.
	 *
	 * @return moved
	 */
	private boolean SlideRight() {
		boolean moved = false;
		for (int x = 0; x < getLargerSide(); x++) {
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
		}
		resetHasCombined();
		return moved;
	}

	/*****************************************************************
	 * A method that slides values in board down. Then returns if
	 * slide was successful.
	 *
	 * @return moved
	 */
	private boolean SlideDown() {
		boolean moved = false;
		for (int x = 0; x < getLargerSide(); x++) {
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

	/*****************************************************************
	 * A helper method that returns the larger of height and width.
	 *
	 * @return larger of height and width
	 */
	public int getLargerSide() {
		if (height > width)
			return height;
		return width;
	}

	/*****************************************************************
	 * A helper method that sets all board Cells boolean hasCombined
	 * to false.
	 */
	private void resetHasCombined() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				board.get(row).get(col).hasCombined = false;
			}
		}
	}

	/*****************************************************************
	 * A helper method that outputs the Cell values stored in board
	 * to the console.
	 */
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

    /*****************************************************************
     * This method will add the Cell to the NonEmptyCells when the
     * value is not zero.
     * @return ArrayList<Cell>
     */
    @Override
    public ArrayList<Cell> getNonEmptyTiles() {
        //Clears NonEmptyCells to not have duplicates after sliding
        nonEmptyCells.clear();
        emptyCells.clear();

        //adds Cell to NonEmptyCells if its value isnt 0
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (board.get(row).get(col).value > 0) {
                    nonEmptyCells.add(board.get(row).get(col));
                } else {
                    emptyCells.add(board.get(row).get(col));
                }
            }

        }

        Collections.shuffle(emptyCells);

        return nonEmptyCells;
    }

    /*****************************************************************
     * This method returns the current state of the game.
     * @return GameStatus
     */
    @Override
    public GameStatus getStatus() {
        getNonEmptyTiles();
        boolean userLost = false;
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                if (board.get(row).get(col).value == winningValue) {
                    numWins += 1;
                    updateHighestScore();
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
            updateHighestScore();
            return GameStatus.USER_LOST;
        }
        return GameStatus.IN_PROGRESS;
    }

    /*****************************************************************
     * This method saves a temp board, used to undo.
     *
     */
    public void saveBoard() {
        int[][] temp = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                temp[row][col] = board.get(row).get(col).value;
            }
        }
        savedBoards.push(temp);
    }


    /*****************************************************************
     * Undo the most recent action, i.e. restore the board to its
     * previous state.
     */
    @Override
    public void undo() {
        if (savedBoards.size() > 1) {

            int[][] previousBoard = savedBoards.get(savedBoards.size() - 2);
            savedBoards.pop();

            setValues(previousBoard);
        }
    }

    /*****************************************************************
     * This method calculates the score of the game.
     * @return total
     */
    public int getScore() {
        int total = 0;
        for (int i = 0; i < nonEmptyCells.size(); i++) {
            total += nonEmptyCells.get(i).value * nonEmptyCells.get(i).value;
        }
        return total;
    }

    /*****************************************************************
     * This method gets the number of slides.
     * @return numSlides
     */
    public int getNumSlides() {
        return numSlides;
    }

    /*****************************************************************
     * This method gets the number of wins.
     * @return numWins
     */
    public int getNumWins() {
        return numWins;
    }

    /*****************************************************************
     * This method updates the highscore if beat.
     */
    public void updateHighestScore() {
        if (getScore() > highestScore) {
            highestScore = getScore();
        }
    }

    /*****************************************************************
     * This method gets the highest score.
     * @return highestScore
     */
    public int getHighestScore() {
        return highestScore;
    }

    /*****************************************************************
     * This method will add to the number of plays.
     */
    public void incNumPlays() {
        numPlays++;
    }

    /*****************************************************************
     * This method will return the amount of times the game has been
     * played.
     * @return numPlays
     */
    public int getNumPlays() {
        return numPlays;
    }
}
