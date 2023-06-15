package games.tictactoe;

import java.util.Random;

import utils.SavingInFile;


public class Game  {

	public final GameState gs;
	final TicTacToeController tictactoe;
	final int player1;
	static SavingInFile sFile;
	
	public Game(GameState gs, TicTacToeController tictactoe, int player1,
			SavingInFile sFile){
        this.gs = gs;        
        this.tictactoe = tictactoe;
        this.player1 = player1;
        this.sFile = sFile;
    }
	
	public void run (Random rnd, int game) throws Exception{
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
			/*if (game < 10)
			  System.out.println(gameToString());*/
		}		
		double [] values = {game, gs.getNumberOfMoves()};
		/*if (game== 0){
			sFile.saveDouble(values);
		}else{
			sFile.appending(values);
		}*/
		sFile.saveDouble(values);
		//sFile.appending(values);
		//sFile.close_files();
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
			move = tictactoe.getAction(gs, player1, game);
			gs.makeMove(move);
		}		
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
	
	public String gameToString()
    {
        StringBuilder builder = new StringBuilder();

        if (!gs.isGameOver())
        {
            builder.append(("Player to move: " + gs.getCurrentPlayer() + "\n"));
            builder.append(("Legal moves: " + gs.getNumberOfMoves() + "\n"));
        }
        else
            builder.append("Game over\n");

        builder.append("\n");

        for (int i = 0; i < 9; i++)
        {
            if ((gs.crosses & (1 << i)) != 0)
                builder.append(" X ");
            else if ((gs.noughts & (1 << i)) != 0)
                builder.append(" O ");
            else
                builder.append(" - ");

            if (i % 3 == 2)
                builder.append("\n");
        }

        return builder.toString();
    }

	

	
	/*void cycle(int delay) throws Exception {
        gs.next(mspacman.getAction(gs), ghosts.getActions(gs));

        if (gsv != null) {
            gsv.repaint();
            if (frame != null)
                frame.setTitle("Score: " + gs.score + ", time: " + gs.gameTicks);
            Thread.sleep(delay);
        }
    }*/
	
}
