public class GameBoard{
    /**
	 * sets the boardSize to 10
	 */
	private int boardSize = 10;

	/**
	 * creates a 2D array called upper_map
	 */
	char[][] upper_map = new char[boardSize][boardSize];

	/**
	 * creates a 2D array called lower_map
	 */
	char[][] lower_map = new char[boardSize][boardSize];

	/**
	 * sets carrier, battleship, destroyer, submarine, and patrol boat sizes
	 */
	int carrier_shipSize, battleship_shipSize, destroyer_shipSize, submarine_shipSize, patrol_BoatSize;

    /**
     *  sets carrier, battleship, destroyer, submarine, and patrol boat identifier 
     */
    char carrier_shipIdentifier, battleship_shipIdentifier, destroyer_shipIdentifier, submarine_shipIdentifier, patrol_BoatIdentifier;

	/*****************************************************************
	 Default constructor that creates the game board.
	 *****************************************************************/
	public void BOARD() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				upper_map[i][j] = '^';
				lower_map[i][j] = '^';
			}
		}
	}

    /*****************************************************************
	 Set all of the ship size
	 *****************************************************************/
    public void setupShips(){
    	//carrier size and identifier
        Carrier carrier = new Carrier();
        this.carrier_shipSize = carrier.getLength();
        this.carrier_shipIdentifier = carrier.getIdentifier();
        
        //battleship size and identifier
        Battleship battleship = new Battleship();
        this.battleship_shipSize = battleship.getLength();
        this.battleship_shipIdentifier = battleship.getIdentifier();
        
        //destroyer size and identifier
        Destroyer destroyer = new Destroyer();
        this.destroyer_shipSize = destroyer.getLength();
        this.destroyer_shipIdentifier = destroyer.getIdentifier();
        
        //submarine size and identifier
        Submarine submarine = new Submarine();
        this.submarine_shipSize = submarine.getLength();
        this.submarine_shipIdentifier = submarine.getIdentifier();
        
        //patrol boat size and identifier
        PatrolBoat patrolBoat = new PatrolBoat();
        this.patrol_BoatSize = patrolBoat.getLength();
        this.patrol_BoatIdentifier = patrolBoat.getIdentifier();
    }

	/*****************************************************************
	 This is the toString for the upper board.
	 @return s the char that goes on the board
	 *****************************************************************/
	public String upperToString() {
		String s = "";
		s = s + " ";
		for (int upper_col = 0; upper_col < 10; upper_col++) {
			s = s + (" " + upper_col);
		}
		s = s + "\n";
		for (int upper_row = 0; upper_row < 10; upper_row++) {
			s = s + ((char) (upper_row + 65) + " ");
			for (int upper_col = 0; upper_col < 10; upper_col++) {
				s = s + (upper_map[upper_row][upper_col] + " ");
			}
			s = s + "\n";
		}
		return s;
	}

	/*****************************************************************
	 This is the toString for the lower board.
	 @return s the char that goes on the board
	 *****************************************************************/
	public String lowerToString() {
		String s = "";
		s = s + " ";
		for (int upper_col = 0; upper_col < 10; upper_col++) {
			s = s + (" " + upper_col);
		}
		s = s + "\n";
		for (int lower_row = 0; lower_row < 10; lower_row++) {
			s = s + ((char) (lower_row + 65) + " ");
			for (int lower_col = 0; lower_col < 10; lower_col++) {
				s = s + (lower_map[lower_row][lower_col] + " ");
			}
			s = s + "\n";
		}
		return s;
	}

	/*****************************************************************
	 Determines if the ship has been sunk or not by checking if the 
	 ship is equal to zero.
	 @return the boolean for if the ship has been sunk or not
	 *****************************************************************/
	public boolean shootAt(int row, int col) {
		
		//checks if the enemy carrier is sunk
		if (lower_map[row][col] == 'c') {
			carrier_shipSize--;
			if (carrier_shipSize == 0) {
				System.out.println("Enemy Carrier has been sunk!");
				return true;
			}
			return true;
			
		//checks if the enemy battleship is sunk
		} else if (lower_map[row][col] == 'b') {
			battleship_shipSize--;
			if (battleship_shipSize == 0) {
				System.out.println("Enemy Battleship has been sunk!");
				return true;
			}
			return true;
			
		//checks if the enemy destroyer is sunk
		} else if (lower_map[row][col] == 'd') {
			destroyer_shipSize--;
			if (destroyer_shipSize == 0) {
				System.out.println("Enemy Destroyer has been sunk!");
				return true;
			}
			return true;
			
		//checks if the enemy submarine is sunk			
		} else if (lower_map[row][col] == 's') {
			submarine_shipSize--;
			if (submarine_shipSize == 0) {
				System.out.println("Enemy Submarine has been sunk!");
				return true;
			}
			return true;
			
		//checks if the enemy patrol boat is sunk
		} else if (lower_map[row][col] == 'p') {
			patrol_BoatSize--;
			if (patrol_BoatSize == 0) {
				System.out.println("Enemy Patrol Boat has been sunk!");
				return true;
			}
			return true;
		} else {
			return false;
		}
	}

	/*****************************************************************
	 Marks a hit on the board
	 *****************************************************************/
	public void recordHit(int row, int col) {
		
		//sets the space to x to mark the hit
		this.upper_map[row][col] = 'x';
	}

	/*****************************************************************
	 Marks a miss on the board
	 *****************************************************************/
	public void recordMiss(int row, int col) {
		
		//sets the space to o to mark a miss
		this.upper_map[row][col] = 'o';
	}

	/*****************************************************************
	 Determines if the player or the computer has any ships left
	 @return the boolean if there are ships left or not
	 *****************************************************************/
	public boolean hasNoShips() {
		
		//checks if the player/computer is out of ships
		if (carrier_shipSize == 0 && battleship_shipSize == 0 && destroyer_shipSize == 0 && submarine_shipSize == 0
				&& patrol_BoatSize == 0) {
			return true;
		} else {
			return false;
		}
	}

	/*****************************************************************
	 Determines if the ship can fit on the board in the location that
	 was selected
	 @return result if the ship can fit or not
	 *****************************************************************/
	public boolean canShipFit(int length, char orientation, int row, int col) {
		boolean result = true;
		
		//checks if the ship goes horizontal
		if (orientation == 'H') {
			if ((col + length) > 10) {
				result = false;
			} else {
				for (int i = col; i < (length + col); i++) {
					if (lower_map[row][i] != '^') {
						result = false;
					}
				}
			}
			
		//checks if the ship goes vertical
		} else if (orientation == 'V') {
			if ((row + length) > 10) {
				result = false;
			} else {
				for (int i = row; i < (length + row); i++) {
					if (lower_map[i][col] != '^') {
						result = false;
					}
				}
			}
			
		//if the input wasn't V or H
		} else {
			System.out.println("Invalid Orientation");
			result = false;
		}
		return result;
	}

	/*****************************************************************
	 Places the ship on the board
	 *****************************************************************/
	public void placeShip(int length, char shipLetter, char orientation, int row, int col) {
		
		//checks if the ship can it
		if (canShipFit(length, orientation, row, col) == true) {
			
			//checks if the ship if vertical
			if (orientation == 'V') {
				for (int i = row; i < (length + row); i++) {
					lower_map[i][col] = shipLetter;
				}
				
			//checks if the ship is horizontal
			} else if (orientation == 'H') {
				for (int i = col; i < (length + col); i++) {
					lower_map[row][i] = shipLetter;
				}
			}
			
		//if the ship can't fit
		} else {
			System.out.println("Error placing ship.");
		}
	}
}