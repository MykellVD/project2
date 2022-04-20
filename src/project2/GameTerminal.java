   /*****************************************************************
 Battleship
 @author Jake Umlor
 @author Thanh Nguyen
 @author Ryan Smith
 @version Winter 2022
 *****************************************************************/
import java.util.Scanner;
import java.util.Random;

public class GameTerminal {
	/**
	 * makes new Scanner called scan 
	 */
	Scanner scan = new Scanner(System.in);

	/**
	 * value for the letter in the coordinates
	 */
	private char coords_input;

	/**
	 * players board
	 */
	private GameBoard playerField = new GameBoard();

	/**
	 * computers board
	 */
	private GameBoard computerField = new GameBoard();

	/**
	 * boolean for if the games over
	 */
	boolean gameOver = false;

	/**
	 * makes new Random object called random
	 */
	Random random = new Random();

	/*****************************************************************
	 Gets the length of the ship.
	 @param n the ship to get the length of 
	 @return the length of the given ship
	 *****************************************************************/
	public int getShipLength(int n) {
		
		//gets the boat in the zero position and returns the length
		if (n == 0) {
			return 5;
			
		//gets the boat in the first position and returns the length
		} else if (n == 1) {
			return 4;
			
		//gets the boat in the second position and returns the length
		} else if (n == 2) {
			return 3;
			
		//gets the boat in the third position and returns the length
		} else if (n == 3) {
			return 3;
			
		//gets the boat in the fourth position and returns the length
		} else if (n == 4) {
			return 2;
		}
		return n;
	}

	/*****************************************************************
	 Gets the character of the ship.
	 @param n the ship to get the character of 
	 @return the character for the given ship
	 *****************************************************************/
	public char getShipChar(int n) {
		
		//gets the boat in the zero position and returns the char for it
		if (n == 0) {
			System.out.println("Placing Carrier.");
			return 'c';
			
		//gets the boat in the first position and returns the char for it
		} else if (n == 1) {
			System.out.println("Placing Battleship.");
			return 'b';
			
		//gets the boat in the second position and returns the char for it
		} else if (n == 2) {
			System.out.println("Placing Destroyer.");
			return 'd';
			
		//gets the boat in the third position and returns the char for it
		} else if (n == 3) {
			System.out.println("Placing Submarine.");
			return 's';
			
		//gets the boat in the fourth position and returns the char for it
		} else if (n == 4) {
			System.out.println("Placing Patrol Boat.");
			return 'p';
		}
		return 'n';
	}

	/*****************************************************************
	 Places the ship on the grid for the player.
	 *****************************************************************/
	public void placePlayerShip() {
		int length;
		char shipLetter;
		
		//loops through the ships to place each of them on the board
		for (int n = 0; n < 5; n++) {
			System.out.println("Please enter your ship coordinates separated by a space. Ex: A 1");
			String input = scan.nextLine();
			
			//checks if invalid input
			if(input.isEmpty() || input.length() != 3 || !Character.isWhitespace(input.charAt(1))) {
				System.out.println("Can't Place Ship Please Choose Again");
			}
			input = input.toUpperCase();
			char coords_input = input.charAt(0);
			char col_input = input.charAt(2);
			int rowValue = coords_input - 'A';
			int colValue = Character.getNumericValue(col_input);
			System.out.println("Would you like the ship to be [V]ertical or [H]orizontal?");
			input = scan.nextLine();
			input = input.toUpperCase();
			char orientation = input.charAt(0);
			length = getShipLength(n);
			shipLetter = getShipChar(n);
			boolean shipFit = playerField.canShipFit(length, orientation, rowValue, colValue);
			
			//checks if the ship didn't fit 
			if (shipFit == false) {
				n--;
			} else {
				playerField.placeShip(length, shipLetter, orientation, rowValue, colValue);
			}
			System.out.println(playerField.lowerToString());
		}
	}

	/*****************************************************************
	 Places the ship on the grid for the computer.
	 *****************************************************************/
	public void placeComputerShip() {
		int length;
		char shipLetter;
		char orientation;
		
		//loops through the ships to place each of them on the board
		for (int n = 0; n < 5; n++) {
			int row_place = random.nextInt(10);
			int col_place = random.nextInt(10);
			int orientation_num = random.nextInt(2);
			
			//checks if vertical
			if (orientation_num == 0) {
				orientation = 'V';
				
			//if horizontal
			} else {
				orientation = 'H';
			}
			length = getShipLength(n);
			shipLetter = getShipChar(n);
			
			//checks if the ship can fit 
			if (computerField.canShipFit(length, orientation, row_place, col_place) == true) {
				computerField.placeShip(length, shipLetter, orientation, row_place, col_place);
				
			//if the ship can't fit
			} else {
				while (computerField.canShipFit(length, orientation, row_place, col_place) == false) {
					int row_place_rand = random.nextInt(10);
					row_place = row_place_rand;
					int col_place_rand = random.nextInt(10);
					col_place = col_place_rand;
					int orientation_num_rand = random.nextInt(2);
					if (orientation_num_rand == 0) {
						orientation = 'V';
					} else {
						orientation = 'H';
					}
				}
				computerField.placeShip(length, shipLetter, orientation, row_place, col_place);
			}

		}
	}

	/*****************************************************************
	 Sets up the board after the player and computer have placed their
	 ships. 
	 *****************************************************************/
	public void setup() {
		GameBoard computer = computerField;
		computer.BOARD();
		placeComputerShip();
		System.out.println("Computer is ready!");
		System.out.println("");
		GameBoard player = playerField;
		player.BOARD();
		placePlayerShip();
		System.out.println("Player is ready!");
	}

	/*****************************************************************
	 Determines if the game is over based on if the player or the 
	 computer have any remaining ships
	 *****************************************************************/
	public boolean gameOver() {
		
		//checks if the input is Q and then ends the game
		if (coords_input == 'Q') {
			gameOver = true;
			System.out.println("User has left the game. Thanks for playing!");
			System.out.println("Player's Ship Placements:");
			System.out.println(playerField.lowerToString());
			System.out.println("Player's Attack Placements:");
			System.out.println(playerField.upperToString());
			System.out.println("Computer's Ship Placements:");
			System.out.println(computerField.lowerToString());
			System.out.println("Computer's Attack Placements:");
			System.out.println(computerField.upperToString());
			return true;
			
		//checks if the player has no ships left then shows that the computer has wone the game
		} else if (playerField.hasNoShips() == true) {
			gameOver = true;
			System.out.println("Player has no more ships, Computer has won. Thanks for playing!");
			System.out.println("Player's Ship Placements:");
			System.out.println(playerField.lowerToString());
			System.out.println("Computer's Attack Placements:");
			System.out.println(computerField.upperToString());
			return true;
			
		//checks if the computer has no ships left then show that the player has won the game
		} else if (computerField.hasNoShips() == true) {
			gameOver = true;
			System.out.println("Computer has no more ships, Player has won. Thanks for playing!");
			System.out.println("Computer's Ship Placements:");
			System.out.println(computerField.lowerToString());
			System.out.println("Player's Attack Placements:");
			System.out.println(playerField.upperToString());
			return true;
		} else {
			return false;
		}
	}

	/*****************************************************************
	 This is the flow of the game, player plays and then the computer
	 plays. It will also check to make sure the the game isn't over.
	 *****************************************************************/
	public void play() {
		
		//checks if the the game isn't over
		if (gameOver == false) {
			playerTurn();
			computerTurn();
			
		//if the game is over
		} else if (gameOver == true) {
		}
	}

	/*****************************************************************
	 Players turn gives prompt to enter coordinates, then determines
	 if its a hit or a miss.
	 *****************************************************************/
	public void playerTurn() {
		System.out.println(playerField.upperToString());
		System.out.println("Please enter your coordinates seperated by a space. Ex: A 1");
		System.out.println("Enter 'Q' as your first coordinate if you'd like to quit the game.");
		
		//loops as long as the game isn't over so the player can take their turn
		while (gameOver == false) {
			System.out.println("Enter coords:");
			String input = scan.nextLine();
			input = input.toUpperCase();
			coords_input = input.charAt(0);
			
			//checks if the player wanted to quit the game
			if (coords_input == 'Q') {
				gameOver();
				
			//if the player didn't quit the game 
			} else {
				char col_input = input.charAt(2);
				int rowValue = coords_input - 'A';
				int colValue = Character.getNumericValue(col_input);
				
				//checks if the players guess is a hit or not
				if (computerField.shootAt(rowValue, colValue) == true) {
					playerField.recordHit(rowValue, colValue);
					System.out.println(input + " is a hit!");
					System.out.println(playerField.upperToString());
				
				//checks if the players guess is a miss
				} else if (computerField.shootAt(rowValue, colValue) == false) {
					playerField.recordMiss(rowValue, colValue);
					System.out.println(input + " is a miss!");
					System.out.println(playerField.upperToString());
				}
				computerTurn();
			}
		}
	}

	/*****************************************************************
	 Computer turn gives prompt to enter coordinates, then determines
	 if its a hit or a miss.
	 *****************************************************************/
	public void computerTurn() {
		int upper_row_rand = random.nextInt(10);
		int upper_col_rand = random.nextInt(10);
		
		//checks if the computers guess is a hit
		if (playerField.shootAt(upper_row_rand, upper_col_rand) == true) {
			computerField.recordHit(upper_row_rand, upper_col_rand);
			
		//checks if the computers guess is a miss
		} else if (playerField.shootAt(upper_row_rand, upper_col_rand) == false) {
			computerField.recordMiss(upper_row_rand, upper_col_rand);
		}
	}

	/*****************************************************************
	 The general method that will allow the game to function and run.
	 *****************************************************************/
	public static void main(String[] args) {
		GameTerminal game = new GameTerminal();
		game.setup();
		game.play();
	}

}