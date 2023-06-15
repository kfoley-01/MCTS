package games.c4;

//import games.tictactoe.GameState;

import games.core.Outcome;

import java.util.ArrayList;
import java.util.List;

public class GameState implements GameStateInterface{

	int plyCount;
	private int current;
    private long player1, player2, legal;
    private Outcome[] outcomes = {Outcome.NA, Outcome.NA};
    //private int rows, cols, cellsCount, n;
    private static  int rows, cols, cellsCount, n;
    private List<Long> moves = new ArrayList<Long>();
    private static final long LEFT_MASK = -4432676798594L;
    private static final long RIGHT_MASK = -283691315109953L;
    private static final long UP_MASK = -128L;
    private static final long DOWN_MASK = -4363686772737L;
    
    public GameState(){
    	//this(6, 7, 4);
    }
    
    public GameState(int rows, int cols, int length)
    {
        this.cellsCount = rows * cols;
        if (cellsCount > 64)
            throw new IllegalArgumentException();
        this.rows = rows;
        this.cols = cols;
        this.n = length;
        //reset();
    }
    
    public GameState(boolean first){
    	this(6,7,4);
		if (first){
			this.reset();
		}		
	}
    
    public void reset()
    {	
    	this.plyCount = 0;
    	this.current = 0;
        this.player1 = 0L;
        this.player2 = 0L;
        this.legal = 4363686772736L;
        this.outcomes[0] = Outcome.NA;
        this.outcomes[1] = Outcome.NA;
        this.moves.clear();        
    }
	
	@Override
	public GameStateInterface copy() {	
		GameState gs = new GameState();
		gs.current = current;
		gs.plyCount = plyCount;
		gs.outcomes = new Outcome[] { outcomes[0], outcomes[1] };
		gs.player1 = player1;
		gs.player2 = player2;
		gs.legal = legal;
		return gs;
	}

	@Override
	public int getNumberOfMoves() {
		return Long.bitCount(legal);		
	}
	
	public int getTotalMoves(){
		return plyCount;
	}
	
	public int getRemainingSpaces(){
		return (cellsCount-plyCount);
	}

	@Override
	public boolean isGameOver() {
		return outcomes[0] != Outcome.NA;	    
	}

	public List<String> getMoves()
    {
        return null;
    }
	
	public Outcome[] getOutcomes()
	{
	        return outcomes;
	}
	
	@Override
	public List<Integer> getLegalMovesBitboards() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Long> getBitMoves()
    {
        if (moves.isEmpty())
            for (int i = 0; i < cellsCount; i++)
                if ((legal & (1L << i)) != 0L)
                    moves.add(1L << i);

        return moves;
    }
	
	private long getCurrentPlayerBitboard()
    {
        return current == 0 ? player1 : player2;
    }
	
	private void setCurrentPlayerBitboard(long bitboard)
    {
        if (current == 0)
            player1 = bitboard;
        else
            player2 = bitboard;
    }
	
	public boolean checkWin(long bitboard, long moveBitboard)
    {
        long current = moveBitboard;
        int howMany = 1;
        // down
        while (true)
        {
            current = (current << cols) & UP_MASK;
            if ((current & bitboard) == 0L)
                break;
            if (++howMany == n)
                return true;
        }
        ////////////////////////////////////////////////////////////////////////
        // down left
        current = moveBitboard;
        howMany = 1;
        while (true)
        {
            current = (current << (cols - 1L)) & UP_MASK & RIGHT_MASK;
            if ((current & bitboard) == 0L)
                break;
            if (++howMany == n)
                return true;
        }
        // up right
        current = moveBitboard;
        while (true)
        {
            current = (current >> (cols - 1L)) & LEFT_MASK & DOWN_MASK;
            if ((current & bitboard) == 0L)
                break;
            if (++howMany == n)
                return true;
        }
        ////////////////////////////////////////////////////////////////////////
        // down right
        current = moveBitboard;
        howMany = 1;
        while (true)
        {
            current = (current << (cols + 1L)) & UP_MASK & LEFT_MASK;
            if ((current & bitboard) == 0L)
                break;
            if (++howMany == n)
                return true;
        }
        // up left
        current = moveBitboard;
        while (true)
        {
            current = (current >> (cols + 1L)) & RIGHT_MASK & DOWN_MASK;
            if ((current & bitboard) == 0L)
                break;
            if (++howMany == n)
                return true;
        }
        ////////////////////////////////////////////////////////////////
        // horizontal
        current = moveBitboard;
        howMany = 1;
        // left
        while (true)
        {
            current = (current >> 1L) & RIGHT_MASK;
            if ((current & bitboard) == 0L)
                break;
            if (++howMany == n)
                return true;
        }
        // right
        current = moveBitboard;
        while (true)
        {
            current = (current << 1L) & LEFT_MASK;
            if ((current & bitboard) == 0L)
                break;
            if (++howMany == n)
                return true;
        }
        return false;
    }

	 public int getCurrentPlayer()
	    {
	        return current;
	    }
	
	@Override
	public void makeMove(int move) {
		/*if (move < 0 ){
			if (move >= getBitMoves().size()){
				throw new IllegalArgumentException("Illegal move!");	
			}
		}*/
		if (move < 0 || move >= getBitMoves().size()){
			throw new IllegalArgumentException("Illegal move!");
		}
            

        long moveBitboard = getBitMoves().get(move);
        setCurrentPlayerBitboard(getCurrentPlayerBitboard() | moveBitboard);
        plyCount++;
        legal &= ~moveBitboard; // remove the move made
        moves.clear();

        if (checkWin(getCurrentPlayerBitboard(), moveBitboard))
        {
            outcomes[getCurrentPlayer()] = Outcome.WIN;
            outcomes[(getCurrentPlayer() + 1) % 2] = Outcome.LOSS;
        }
        else
        {
            legal |= (moveBitboard >> cols); // add the new legal move

            if (Long.bitCount(legal) == 0)
            {
                outcomes[0] = Outcome.DRAW;
                outcomes[1] = Outcome.DRAW;
            }
        }
        current = (current + 1) % 2;		
	}

	 public String gameToString()
	    {
	        StringBuilder builder = new StringBuilder();
	        builder.append(("Current player: " + getCurrentPlayer() + "\n"));
	        builder.append("\n");

	        for (long i = 0; i < cellsCount; i++)
	        {
	            if ((player1 & (1L << i)) != 0L)
	                builder.append(" \u25C9 ");
	            else if ((player2 & (1L << i)) != 0L)
	                builder.append(" \u25CE ");
	            else if ((legal & (1L << i)) != 0L)
	                builder.append(" x ");
	            else
	                builder.append(" - ");

	            if (i % cols == cols - 1)
	                builder.append("\n");
	        }

	        return builder.toString();
	    }
	
	public String toString()
    {
        return "Connect-4";
    }
	
///////////////
    // DEBUGGING //
    ///////////////

    public void printBitBoard(Long b)
    {
        for (long i = 0; i < cellsCount; i++)
        {
            if ((b & (1L << i)) != 0)
                System.out.print(" X ");
            else
                System.out.print(" - ");

            if (i % cols == cols - 1)
                System.out.println();
        }

        System.out.println("\n");
    }
			
}
