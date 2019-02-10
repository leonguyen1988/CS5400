import java.util.Stack;



/*
 * Small Coordinate class to get wriggle 's tail and head coordinates
 */
class Coord {
	
	public int row;
	
	public int column;
	
	Coord()
	{
		row = 0;
		column =0;
	}
}
/*
 * Wriggle class to create wriggle and get it's head and tail
 * corrdinates, class will find location of wriggle
 * Moving head or tail and chang rest of other parts of wriggle
 * 
 * */
public class Wriggle {



	public Coord tail = new Coord();
	public Coord head = new Coord();
	private Wriggle[] numWorms;
	public int depth;
	Wriggle(){ }

	void setNumWormd(int worms)
	{
		this.numWorms = new Wriggle[worms];
		for(int worm =0;worm<this.numWorms.length;worm++)
		{
			this.numWorms[worm] = new Wriggle();
		}
	}
	int getNumsSize()
	{
		return this.numWorms.length;
	}
	public void setWrigglePosition(char[][]blocks)
	{
		for (int row = 0; row < blocks.length; row++) {
			for (int column = 0; column < blocks.length; column++) {
				if (Character.isDigit(blocks[row][column])){
					int index = Character.getNumericValue( blocks[row][column]);
					this.tail.row = row;
					this.tail.column = column;
				    this.numWorms[index].tail.row = row;
					this.numWorms[index].tail.column =column;
					this.numWorms[index].head.row = getHead(blocks).row;
					this.numWorms[index].head.column = getHead(blocks).column;
				}
			}
		}			
	}
	private Coord getHead(char[][] blocks)
	{
		int row = this.tail.row;
		int col = this.tail.column;
		Coord head = new Coord();
		boolean foundHead = false;
		while(!foundHead)
		{
			//Follows the arrows to the head, stops when is finds a letter indicating a tail
    		if(row > 0 && blocks[row - 1][col] == 'v')	
    			row--;
    		else if(col < blocks.length - 1 && blocks[row][col + 1] == '<')
    			col++;
    		else if(row < blocks[0].length - 1 && blocks[row + 1][col] == '^')
    			row++;
    		else if(col > 0 && blocks[row][col - 1] == '>')
    			col--;
    		else if(row > 0 && blocks[row - 1][col] == 'D')
    		{
    			row--;
    			foundHead = true;
    		}
    		else if(col < blocks.length - 1 && blocks[row][col + 1] == 'L')
    		{
    			col++;
    			foundHead = true;
    		}
    		else if(row < blocks[0].length - 1 && blocks[row + 1][col] == 'U')
    		{
    			row++;
    			foundHead = true;
    		}
    		else if(col > 0 && blocks[row][col - 1] == 'R')
    		{
    			col--;
    			foundHead = true;
    		}
    	}
		head.row = row;
		head.column = col;
		return head;
		
	}
	/*
	 * Function will look wiggle posirion in puzzle and save their cordinates PRE:
	 * blocks must be 2 dimensional charracters POST Wriggle 's position with
	 * corresponding columns and rows
	 */
	public Wriggle getWrigglePosition(int wormID) {
		this.head.row = this.numWorms[wormID].head.row;
		this.head.column = this.numWorms[wormID].head.column;
		this.tail.row = this.numWorms[wormID].tail.row;
		this.tail.column = this.numWorms[wormID].tail.column;
		return this;
//		for (int row = 0; row < blocks.length; row++) {
//			for (int column = 0; column < blocks.length; column++) {
//				if (blocks[row][column] == (char)(wormID+48)) {
//					this.tail.row = row;
//					this.tail.colum = column;
//					this.head.row = row;
//					this.head.colum = column;
//					break;
//				} else if (blocks[row][column] == Static.UP || blocks[row][column] == Static.DOWN
//						|| blocks[row][column] == Static.LEFT || blocks[row][column] == Static.RIGHT) 
//				{
//					this.tail.row = row;
//					this.tail.colum = column;
//					this.head.row = row;
//					this.head.colum = column;
//					break;
//				}
//				
//			}
//		}
	}

	private char getDirection(int direction)
	{
		switch(direction) {
		case 0:
			return Static.LEFT;
		case 1:
			return Static.DOWN;
		case 2:
			return Static.RIGHT;
		case 3:
			return Static.UP;
		}
		return 'E';
	}
	
	/*
	 * function will move wriggle either from head or tail and starts from left side and moves 
	 *  counterclockwise arround blocks untill it reach the goal which is bottom right of blocks
	 * PRE: bodyPart,dicrection must be character value, blocks must be 2 dimensional charracters arrays,
	 *      explored must be State value
	 * POST : It will return the new puzzle with have new position of Wriggle
	 */
	 
	public char[][] moving(int wormID,int bodyPart, int direct, char[][] blocks) {
		char direction= getDirection(direct);
		char[][] tempBlocks = new char[blocks.length][];
		boolean moveMade = false;
		for (int row = 0; row < blocks.length; row++) {
			tempBlocks[row] = blocks[row].clone();
		}

		/*
		* If function will move body based on the moves of tail
		*/
		if (bodyPart == 1) {
			for (int row = 0; row < blocks.length; row++) {
				for (int column = 0; column < blocks[0].length; column++) {
					if (moveMade == false) {
						if (tempBlocks[row][column] == (char)wormID+48 && direction == Static.LEFT) {
							
							tempBlocks[row][column - 1] =  (char)(wormID+48);
							
							tempBlocks[row][column] = Static.BODYLEFT;
							
							tempBlocks = moveTail(row, column, tempBlocks);
							
							moveMade = true;
						}
						if (tempBlocks[row][column] == (char)(wormID+48) && direction == Static.DOWN) {
							
							tempBlocks[row + 1][column] = (char)(wormID+48);
							
							tempBlocks[row][column] = Static.BODYDOWN;
							
							tempBlocks = moveTail(row, column, tempBlocks);
							
							moveMade = true;
						}
						if (column < tempBlocks[row].length-1 
								&&tempBlocks[row][column] == (char)(wormID+48) 
								&& direction == Static.RIGHT) {
							
							tempBlocks[row][column + 1] = (char)(wormID+48);
							
							tempBlocks[row][column] = Static.BODYRIGHT;
							
							tempBlocks = moveTail(row, column, tempBlocks);
							
							moveMade = true;
						}
						if (tempBlocks[row][column] == (char)(wormID+48) && direction == Static.UP) {
							
							tempBlocks[row - 1][column] = (char)(wormID+48);
							
							tempBlocks[row][column] = Static.BODYUP;
							
							tempBlocks = moveTail(row, column, tempBlocks);
							
							moveMade = true;
						}
					}
				}
			}
			// Otherwise, body will follow moves of head
		} else {
			for (int row = 0; row < blocks.length; row++) {
				for (int column = 0; column < blocks[0].length; column++) {
					if (tempBlocks[row][column] == (char)(wormID+48)&& moveMade == false) {
						tempBlocks[row][column] = Static.EMPTY;
						tempBlocks = moveHead(row, column, tempBlocks, wormID,direction);
						moveMade = true;
					}
				}
			}
		}
		return tempBlocks;
	}

	/*
	* Helper function will be called when Wriggle move head , it will updates position of body and tail
	* PRE : row and col must be integer value and tempBlocks is 2 dimensional charracter array which must be passed from BFTS class
    *       direction must be character value, this function must be include into moving function to help moving function
	*		accomplish the moving task from head
	* POST: Update new Postions of Wriggle's body and tail 
	*/
	private char[][] moveHead(int row, int col, char[][] tempBlocks,int wormID, char direction) {
		Boolean firstLink = true, finished = false;

		while (finished == false) {
			// Follows the arrows to the tail and moves it, stops when is finds a letter
			// indicating a tail
			if (col < tempBlocks.length-1 &&tempBlocks[row][col + 1] == Static.BODYLEFT) {
				col++;
				if (firstLink) {
					tempBlocks[row][col] = (char)(wormID+48);
					firstLink = false;
				}
			}
			if (row > 0 && tempBlocks[row - 1][col] == Static.BODYDOWN) {
				row--;
				if (firstLink) {
					tempBlocks[row][col] = (char)(wormID+48);
					firstLink = false;
				}
			} else if (col > 0 && tempBlocks[row][col - 1] == Static.BODYRIGHT) {
				col--;
				if (firstLink) {
					tempBlocks[row][col] = (char)(wormID+48);
					firstLink = false;
				}
			} else if (row < tempBlocks[0].length - 1 && tempBlocks[row + 1][col] == Static.BODYUP) {
				row++;
				if (firstLink) {
					tempBlocks[row][col] = (char)(wormID+48);
					firstLink = false;
				}
			} else if (col < tempBlocks.length - 1 && tempBlocks[row][col + 1] == Static.LEFT) {
				col++;
				if (firstLink) {
					tempBlocks[row][col] = (char)(wormID+48);
					firstLink = false;
				}else
				{
					tempBlocks[row][col] = Static.BODYLEFT;
				}
				finished = true;
			} else if (row > 0 && tempBlocks[row - 1][col] == Static.DOWN) {
				row--;
				if (firstLink) {
					tempBlocks[row][col] = (char)(wormID+48);
					firstLink = false;
				}else
				{
					tempBlocks[row][col] = Static.BODYDOWN;
				}
				finished = true;
			} else if (col > 0 && tempBlocks[row][col - 1] == Static.RIGHT) {
				col--;
				if (firstLink) {
					tempBlocks[row][col] = (char)(wormID+48);
					firstLink = false;
				}else
				{
					tempBlocks[row][col] = Static.BODYRIGHT;
				}
				finished = true;
			} else if (row < tempBlocks[0].length - 1 && tempBlocks[row + 1][col] == Static.UP) {
				row++;
				if (firstLink) {
					tempBlocks[row][col] = (char)(wormID+48);
					firstLink = false;
				}else
				{
					tempBlocks[row][col] = Static.BODYUP;
				}
				finished = true;
			}

		}

		// Makes the final change at the tail
		if (direction == Static.LEFT) {
			tempBlocks[row][col - 1] = Static.RIGHT;
		} else if (direction == Static.RIGHT) {
			tempBlocks[row][col + 1] = Static.LEFT;
		} else if (direction == Static.DOWN) {
			tempBlocks[row + 1][col] = Static.UP;
		} else {
			tempBlocks[row - 1][col] = Static.DOWN;
		}
		return tempBlocks;

	}

	/*
	*  Helper function will be caled from moving function when Wriggle move tail first
	*  it will update the body and head of Wriggle 's Postions
	*  PRE : row and col must be int value and tempblocks must be 2 dimensional charracter array
	*  POST : Update new Postions of Body and head of Wriggle in the puzzle
	*/
	private char[][] moveTail(int row, int col, char[][] tempBlocks) {

		char directionToLastMove = ' ';
		boolean finished = false;

		while (finished == false) {
			// Follows the arrows to the tail and moves it
			if (col < tempBlocks.length - 1 
					&& tempBlocks[row][col + 1] == Static.BODYLEFT) {
				col++;
				directionToLastMove = Static.LEFT;
			} else if (row > 0 
					&& tempBlocks[row - 1][col] == Static.BODYDOWN) {
				row--;
				directionToLastMove = Static.DOWN;
			} else if (col > 0 
					&& tempBlocks[row][col - 1] == Static.BODYRIGHT) {
				col--;
				directionToLastMove = Static.RIGHT;
			} else if (row < tempBlocks[0].length - 1 
					&& tempBlocks[row + 1][col] == Static.BODYUP) {
				row++;
				directionToLastMove = Static.UP;
			} else if (col < tempBlocks.length - 1 
					&& tempBlocks[row][col + 1] == Static.LEFT) {
				tempBlocks[row][col] = directionToLastMove;
				col++;
				tempBlocks[row][col] = Static.EMPTY;
				finished = true;
			} else if (row > 0 
					&& tempBlocks[row - 1][col] == Static.DOWN) {
				tempBlocks[row][col] = directionToLastMove;
				row--;
				tempBlocks[row][col] = Static.EMPTY;
				finished = true;
			} else if (col > 0 
					&& tempBlocks[row][col - 1] == Static.RIGHT) {
				tempBlocks[row][col] = directionToLastMove;
				col--;
				tempBlocks[row][col] = Static.EMPTY;
				finished = true;
			} else if (row < tempBlocks[0].length - 1 
					&& tempBlocks[row + 1][col] == Static.UP) {
				tempBlocks[row][col] = directionToLastMove;
				row++;
				tempBlocks[row][col] = Static.EMPTY;
				finished = true;
			}

		}
		return tempBlocks;
	}
}
