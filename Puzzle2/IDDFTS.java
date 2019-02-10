import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//Breadth First Tree Search 
// Starting with adds possible moving from heads to the Frontier queue first, starting with the state left the head
//and then moving counterclockwise.  I then do the same for the tail.  States are popped off of the 
//frontier queue onto the eval Stack which in turn adds new states to the frontier.  This conitnues
//until the state is the goal state which is Wriggle 's head or tail reaches the bottom right of puzzle.

class IDDFTS {
	
	public int height;
	public int width;
	public char[][] blocks;

//	// Frontier Queue to hold all possible moves of queue
//	private ArrayList<State> frontier ;
	
	// Stack explored will get single state form frontier and operate it
	private Stack<State> explored ;
	private ArrayList<State> evalState;
	private int rootState;
	private int childState;


	// Declare worm in puzzle to get Wriggle's position
	private Wriggle worm ;
	
	IDDFTS(int cols, int rows, int worms)
	{
		height = cols;
		width = rows;	
		blocks = new char[width][height];
		rootState = -1;
		childState = 1;
		worm = new Wriggle();
		worm.setNumWormd(worms);
		evalState = new ArrayList<State>();
		explored = new Stack<State>();

		
	}
	IDDFTS(){
		worm = new Wriggle();
		height = 0;
		width = 0;
		blocks = new char[width][height];
		rootState = -1;
		childState = 1;
		evalState = new ArrayList<State>();
		explored = new Stack<State>();
	}
	

	
	private boolean isSolved(Wriggle worm, State currentState)
	{
		if(worm.tail.row == width-1 && worm.tail.column == height-1)
		{
			return true;
		}
		else if((worm.head.row == width -1 &&  worm.head.column == height-1)
				&& ( (currentState.blocks[worm.head.row][worm.head.column]==Static.LEFT)
						|| currentState.blocks[worm.head.row][worm.head.column]==Static.UP))
		{
			return true;
		}
		return false;
//		int wormIndex = currentState.result.id;
//		char[][] blocks = currentState.blocks;
//		if(wormIndex ==0)
//		{
//			if(blocks[width-1][height-1] == '0' )
//			{
//				return true;
//			}
//			else if (blocks[width-1][height-1] == Static.LEFT 
//					|| blocks[width-1][height-1] == Static.UP)
//			{
//				if(findTailIndex(width-1,height-1, blocks) == '0')
//				{
//					return true;
//				}
//				
//			}
//			return false;
//		}
//		return false;
	}
	
	private char findTailIndex(int row, int column, char[][] blocks)
	{
		while(blocks[row][column] != '0') 
		{
			
			switch ( blocks[row][column] ) {
			case Static.LEFT:
			case Static.BODYLEFT:
				column--;
				break;
			case Static.DOWN:
			case Static.BODYDOWN:
				row--;
				break;
			case Static.RIGHT:
			case Static.BODYRIGHT:
				column++;
				break;
			case Static.UP:
			case Static.BODYUP:
				row++;
				break;
				default:
					return blocks[row][column];
			}
		}
		return blocks[row][column];

	}
	
	private boolean isValid(int row, int column, char[][] blocks, int side)
	{
		
		switch(side) {
		case 0:
			column--;
			break;
		case 1:
			row++;
			break;
		case 2:
			column++;
			break;
		case 3:
			row--;
			break;
		}
//		column = (column > 0  && side == 0)
//					? column--
//							:( (column < blocks[0].length -1 && side == 0)
//									? column++
//											:column);
//		row =(row < blocks.length  && side ==1)
//				?row++
//						:( (row>0 && side == 3)?
//								row--:
//									row );
		if(column >=0  
				&& column < this.height  
				&& row >= 0
				&& row < this.width )
		{
			if(blocks[row][column] == Static.EMPTY)
			{
				return true;
			}
		}
		return false;
	}
	
	/*
	 * function will get frontier states and explored states to excute to find the
	 * path from the initial state to goal state, It uses Breath First Search Algo to
	 * find the path from initial Wriggle'location to goal which is bottom right of
	 * the blocks 
	 * PRE : blocks must be 2 dimension characters, frontier must Queue
	 *       of State to apply FIFO for each state Explored must stack of State 
	 * POST :
	 * function will find the path from initial to goal and capture blocks into the
	 * state to ouput purpose
	 * 
	 */
	public void solvedIDDTFTS() throws InterruptedException
	{
		int depth = 0 , nextID;
		long startTime, endTime, duration;
		boolean foundInitial = false;
		ArrayList<State> listOfSteps = new ArrayList<State>();

		worm.setWrigglePosition(this.blocks);
		startTime = System.nanoTime();
		
		
		while(!solvedDLTS(depth))
		{
//			System.out.printf("Depth level : %d",depth);
			explored.clear();
			evalState.clear();
			depth++;
		}
		
		endTime = System.nanoTime();
		duration = endTime - startTime;
		/*
		*	After reaching the goal states and caputures all needed states
		*	listOfSteps will get all needed steps from Stack explored
		*	from the beginning to the goal
		*/
		
		// get final move from explored
		listOfSteps.add(0, explored.get(0));
		
		nextID = explored.get(0).parentID;

		// This loop traverses the printList by finding each steps parent state
		while (! foundInitial) {
			for (int element = 0; element < explored.size(); element++) {
				if (explored.get(element).id == nextID) {
					nextID = explored.get(element).parentID;
					listOfSteps.add(0, explored.get(element));
				}
			}
			if (nextID == 0)
				foundInitial = true;
		}

		// Initial state is not a move
		listOfSteps.remove(0);

		// Print the steps in the solution
		for (int step = 0; step < listOfSteps.size(); step++) {
			System.out.printf("%d %d %d %d \n"
					, listOfSteps.get(step).result.id
					, listOfSteps.get(step).result.wormBody
					, listOfSteps.get(step).result.column
					, listOfSteps.get(step).result.row);
		}

		/*
		 * the first state in list of explored is final state
		 */

		blocks = explored.get(0).blocks;
		
		printBoardHelper();

		// Time in nanosecond
		System.out.printf("%d\n", endTime - startTime);

		// Output list of steps
		System.out.println(listOfSteps.size());
		
	}
	
	private boolean solvedDLTS(int depthLimit) throws InterruptedException {
		char[][] tempBlocks;
		State initial = new State(0,blocks,0,0,0,0,0,0);
		boolean changedState = false, addInitial = false;
		int depth =0;
		int tempChildState = 1;
		int head = 0, tail = 1;
		rootState = 0;
		childState = 1;
		Stack<State> frontier = new Stack<State>();
		State current = new State();
		frontier.push(initial);
		// pop frontier for eval

			while(!frontier.isEmpty())
			{
				explored.add(0,frontier.peek());
				worm.setWrigglePosition(frontier.peek().blocks);
				if(isSolved(worm.getWrigglePosition(0),frontier.peek()))
				{
					System.out.println();
		        	printBoardHelper(explored.get(0).blocks);
		        	System.out.println(current.depth);
		        	System.out.println();
					return true;
				}
//				evalState.add(0,frontier.pop());
				
				
				current = frontier.pop();
//				if(current.id == 0)
//				{
//					worm.getWrigglePosition(current.blocks);
//				}
				rootState = changedState ? current.id :rootState;
				
				if(current.depth <= depthLimit)
				{
					changedState = false;
					System.out.println();
		        	printBoardHelper(explored.get(0).blocks);
		        	System.out.println(current.depth);
		        	System.out.println();
//		        	Thread.sleep(500);
					rootState = changedState ? rootState + 1 :rootState;
					changedState = false;
					for(int wormID =0; wormID < worm.getNumsSize();wormID++)
					{
						
						// Get worm position in the new state
						worm.getWrigglePosition(wormID);
						/*
						 * Checking each direction
						 * */
						for(int side = 0; side < 4;side++)
						{
							boolean found = false;
							// Checking Left side of head 
 							if( isValid(worm.head.row, worm.head.column, current.blocks,side) )
							{
								State tempState = new State(current,head,wormID,worm.head.column,worm.head.row);
								tempBlocks = worm.moving(wormID,head,side,current.blocks);
								tempState.id = childState;
								tempState.blocks = tempBlocks;
								tempState.parentID = rootState;
								tempState.depth = tempState.depth +1;
								tempState.result.id = wormID;
								tempState.result.wormBody = head;
								tempState.result.column = getColumn(side,worm.head.column) ;
								tempState.result.row = getRow(side,worm.head.row);
								//tempState = new State(childState, tempBlocks, rootState, 0, 0, worm.head.colum , worm.head.row);
									
									frontier.push(tempState);
									childState++;
									changedState = true;
//								if( worm.getWrigglePosition'U')) 
//								{
//									System.out.println("FOUNDHEAD");
//									printBoardHelper(frontier.peek().blocks);
//									break;
//								}
									
							}
							
							if( isValid(worm.tail.row, worm.tail.column, current.blocks,side) )
							{
								State tempState = new State(current,tail,wormID,worm.tail.column,worm.tail.row);
								tempBlocks = worm.moving(wormID,tail,side,current.blocks);
								tempState.id = childState;
								tempState.blocks = tempBlocks;
								
								tempState.parentID = rootState;
								tempState.depth = tempState.depth +1;
								tempState.result.id = wormID ;
								tempState.result.wormBody = tail;
								tempState.result.column = getColumn(side, worm.tail.column);
								tempState.result.row = getRow(side, worm.tail.row);							
								frontier.push(tempState);
								childState++;
								changedState = true;
								if(frontier.peek().blocks[width-1][height-1]=='0' ) {
									System.out.println("FOUNDTAIL");
									printBoardHelper(frontier.peek().blocks);
									break;
								}
							}

							
						}
//						// pop frontier for eval
//						if(!frontier.isEmpty())
//						{
//							explored.add(0,frontier.pop());
//						}
						
			        	
					}
					
				}
			}
			return false;
	}
	
	private int getColumn(int side, int column)
	{

		switch(side) {
		case 0:
			column--;
			break;
		case 2:
			column++;
			break;	
		}
		return column;
	}
	
	private int getRow(int side, int row)
	{

		switch(side) {
		case 1:
			row++;
			break;
		case 3:
			row--;
			break;	
		}
		return row;
	}
	/*
	 * Helper function to output final 2D puzzle
	 */
	private void printBoardHelper(char[][] board) {
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board.length; column++) {
				System.out.printf("%c ", board[row][column]);
			}
			System.out.printf("\n");
		}
	}
	
	private void printBoardHelper() {
		for (int row = 0; row < blocks.length; row++) {
			for (int column = 0; column < blocks.length; column++) {
				System.out.printf("%c ", blocks[row][column]);
			}
			System.out.printf("\n");
		}
	}
}
