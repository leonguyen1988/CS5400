/*
 * class holds all solutions from initial state to goal state
 * PRE: Must be only 4 variables based on output requierments
 * POST : Output requirements include the wrigle index, which part of worm moves,
 * 		  column and row of wriggle
 * */
class Result 
{
	public int  id;
	public int wormBody;
	public int column;
	public int row;
	
	/*
	 * Constructor of the class
	 * */
	public Result( int id, int parts, int column,int row){
		this.id = id;
		this.wormBody = parts;
		this.column = column;
		this.row = row;
	}
	public Result()
	{
		
	}
}

/*
 * State class capture each move of wriggle and hold it until 
 * the wriggle reaches the goal
 * PRE: id must is a next State and parentID is currentState,
 * 		State should state type, there are last four int variables
 * 		are wriggleindex, tail or head of wriggle which moved and 
 * 		wriggle 's coordinates
 * POST: Captures the state of single move of wriggle  
 * */
public class State
{
	
	int id, parentID, depth;
	char[][] blocks;
	State parent;
	Result result;
	State(){
		result = new Result();
		depth=0;
	}
	public State(int theID, char[][] puzzleBlocks, State parentState, int wriggler, int tail, int col, int row)
	{
		id = theID;
		blocks = puzzleBlocks;
		parent = parentState;
		result = new Result(wriggler,tail,col,row);
	}
	
	public State(int theID, char[][] puzzleBlocks, int passedID,int depthLevel, int wriggler, int tail, int col, int row)
	{
		id = theID;
		blocks = puzzleBlocks;
		parentID = passedID;
		depth = depthLevel;
		result = new Result(wriggler,tail,col,row);
	}
	
	
	public State(State copy,int wormID,int wormPart,int col, int row)
	{
		result = new Result();
		id = copy.id;
		parentID = copy.parentID;
		blocks = copy.blocks;
		parent = copy.parent;
		result.id = wormID;
		result.column = col;
		result.row = row;
		result.wormBody = wormPart;
		depth = copy.depth;
	}


}