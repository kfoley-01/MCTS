package games.tictactoe;

import games.tictactoe.TicTacToe.Outcome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;



public class GameState implements GameStateInterface {

	public int plyCount;
    int crosses;
    int noughts;
    private int legal; 
    boolean gameOver;
    
   //GameState gs;// = new GameState();
	
    private List<Integer> legalMovesBitboards = new ArrayList<Integer>();
    
    private static final int[] WINNING_PATTERNS = {7, 56, 448, 73, 146, 292, 273, 84};
    
	public GameState(boolean first){
		if (first){
			this.reset();
		}		
		
	}
	
	public GameState(){
	   //gs = new GameState();	
		//reset();
	}
	
	public void reset()
    {
		/*GameState gs = new GameState();
		gs = new GameState();
		gs.plyCount = 0;
		gs.crosses = 0;
		gs.noughts = 0;
		gs.legal = 511;
		gs.gameOver = false;
		gs.legalMovesBitboards.clear();
		*/
		
        this.plyCount = 0;
        this.crosses = 0;
        this.noughts = 0;
        this.legal = 511;            // 9 legal moves
        this.gameOver = false;
        this.legalMovesBitboards.clear();
    }
	
	//@Override
	public GameStateInterface copy() {
		GameState gs = new GameState();			
		gs.plyCount = plyCount;
        gs.gameOver = gameOver;
        gs.crosses = crosses;
        gs.noughts = noughts;
        gs.legal = legal;		
		return gs;
	}
	
	@Override
	public void makeMove(int move)
    {
        // if the move taken is illegal		
        if (move < 0 || move >= this.getNumberOfMoves()){
            throw new IllegalArgumentException("Illegal move!");
        }
        
        this.setCurrentBitboard(this.getCurrentBitboard() | this.getLegalMovesBitboards().get(move));
        this.plyCount++;
        this.computeLegalMoves();
        this.gameOver = this.checkWin(this.getOppositeBitboard()) || this.getLegalMovesBitboards().isEmpty();
        //Thread.currentThread().notifyAll();
    }
	
	
	/*public void makeMove(int move)
    {
        // if the move taken is illegal
        if (move < 0 || move >= gs.getNumberOfMoves()){
            throw new IllegalArgumentException("Illegal move!");
        }
        gs.setCurrentBitboard(gs.getCurrentBitboard() | gs.getLegalMovesBitboards().get(move));
        gs.plyCount++;
        gs.computeLegalMoves();
        gs.gameOver = gs.checkWin(gs.getOppositeBitboard()) || gs.getLegalMovesBitboards().isEmpty();
    }*/
	
	
	
	 public int getNumberOfMoves()
	    {
	        return getLegalMovesBitboards().size();
	    }
	 
	 public List<Integer> getLegalMovesBitboards()
	    {
	        if (legalMovesBitboards.isEmpty())
	            for (int i = 0; i < 9; i++)
	                if ((legal & (1 << i)) != 0)
	                    legalMovesBitboards.add(1 << i);

	        return legalMovesBitboards;
	    }
	 
	 public void setCurrentBitboard(int bitboard)
	    {
	        if (plyCount % 2 == 0)
	            crosses = bitboard;
	        else
	            noughts = bitboard;
	    }
	 
	 public int getCurrentBitboard()
	    {
	        return getCurrentPlayer() == 0 ? crosses : noughts;
	    }

	    public int getOppositeBitboard()
	    {
	        return getCurrentPlayer() == 0 ? noughts : crosses;
	    }
	    
	    public int getCurrentPlayer()
	    {
	        return plyCount % 2;
	    }
	    
	    public void computeLegalMoves()
	    {
	        legal = ~(crosses | noughts);
	        legalMovesBitboards.clear();
	    }
	    
	    public boolean checkWin(int board)
	    {
	        for (int pattern : WINNING_PATTERNS)
	            if ((board & pattern) == pattern)
	                return true;

	        return false;
	    }
	    
	    public Outcome[] getOutcomes()
	    {
	        if (!isGameOver())
	            return new Outcome[] {Outcome.NA, Outcome.NA};

	        if (checkWin(crosses))
	            return new Outcome[] {Outcome.WIN, Outcome.LOSS};

	        if (checkWin(noughts))
	            return new Outcome[] {Outcome.LOSS, Outcome.WIN};

	        return new Outcome[] {Outcome.DRAW, Outcome.DRAW};
	    }
	    
	    /*public String getWinLoss(char XO){
	    	if (XO == 'O'){
	    		if (checkWin(noughts)){
	    			return "LOSS";
	    		}
	    	}else if (XO == 'X'){
	    		if (checkWin(crosses)){
	    			return "WIN";
	    		}
	    	}
	    }*/
	    
	    public String gameToString()
	    {
	        StringBuilder builder = new StringBuilder();

	        if (!isGameOver())
	        {
	            builder.append(("Player to move: " + getCurrentPlayer() + "\n"));
	            builder.append(("Legal moves: " + getNumberOfMoves() + "\n"));
	        }
	        else
	            builder.append("Game over\n");

	        builder.append("\n");

	        for (int i = 0; i < 9; i++)
	        {
	            if ((crosses & (1 << i)) != 0)
	                builder.append(" X ");
	            else if ((noughts & (1 << i)) != 0)
	                builder.append(" O ");
	            else
	                builder.append(" - ");

	            if (i % 3 == 2)
	                builder.append("\n");
	        }

	        return builder.toString();
	    }
	    
	    public String [] gameToArray(){
	    	String game []= new String[9];

	        for (int i = 0; i < 9; i++)
	        {
	            if ((crosses & (1 << i)) != 0)
	                game[i]="X";
	            else if ((noughts & (1 << i)) != 0)
	            	game[i]="O";
	            else
	            	game[i]="-";
	        }
	        return game;
	    	
	    }
	    
	    //System.out.println(Arrays.toString(tic.getOutcomes()));
	
	/*public void next(int pacDir, int[] ghostDirs) {
        pacMan.next(pacDir, maze);
        tryEatPill();
        tryEatPower();
        eatGhosts();
        if (ghostReversal()) {
            reverseGhosts();
        } else {
            moveGhosts(ghostDirs);
        }
        if (pillsCleared() || gameTicks>=limit) {

            if(gameTicks>=limit)
                score+=10*pills.cardinality()+50*powers.cardinality();

            nextLevel();
        } else {
            if (MsPacManDeath()) {
                nLivesRemaining--;
                resetAgents();
            }
        }
        if(score>=10000 && !extraLife)
        {
            nLivesRemaining++;
            extraLife=true;
        }

        totalGameTicks++;
        gameTicks++;
    }*/
	
	public boolean isGameOver()
    {
        return gameOver;
    }
	
	

	

	
	

}
