package games.c4;

import utils.Constants;

import java.util.Random;
import utils.SavingInFile;

public class Game {
	
	public final GameState gs;
	final Connect4Controller connect4;
	final int player1;
	static SavingInFile sFile;
	
	public Game(GameState gs, Connect4Controller connect4, int player1,
			SavingInFile sFile){
        this.gs = gs;        
        this.connect4= connect4;
        this.player1 = player1;
        this.sFile = sFile;
    }
	
	public void run (Random rnd, int game) throws Exception{
		if (game < 1)
			  System.out.println(gs.gameToString());
		int playerTurn = player1;			
		while (!gs.isGameOver()){
			//System.out.println("Plays " + gs.plyCount);			
			if (playerTurn == Constants.CPU){				
				cycle(Constants.CPU,  rnd, game);//it doesnt matter			
				playerTurn = Constants.MCTS;
			}else{
				cycle(Constants.MCTS,  rnd, game);				
				playerTurn = Constants.CPU;
			}
			if (game < 1)
			  System.out.println(gs.gameToString());
		}		
		double [] values = {game, gs.getRemainingSpaces()};// gs.getNumberOfMoves()};
	
		sFile.saveDouble(values);
		
	}
	
	void cycle(int playerTurn,  Random rnd, int game) throws Exception {
		int numberOfMoves;
		int move;
		if (game == 1 && gs.plyCount==6){
			int a = 0; 
			a++;
		}
		if (playerTurn == Constants.CPU){
			numberOfMoves = gs.getNumberOfMoves();
			move = rnd.nextInt(numberOfMoves);
			gs.makeMove(move);			
		}else if (playerTurn == Constants.MCTS){
			/*move = rnd.nextInt(numberOfMoves);
			gs.makeMove(move);	
			*/
			//connect4.getAction(, playerTurn, game)
			move = connect4.getAction(gs, player1, game);
			gs.makeMove(move);
		}		
	}
	
	



}
