package games.tictactoe;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import utils.SavingInFile;

import java.util.Scanner;

import games.c4.Main;
import java.util.concurrent.SynchronousQueue;


public class Game  {

	public final GameState gs;
	final TicTacToeController tictactoe;
	final int player1;
	static SavingInFile sFile;
	static camera c;
	public dataStore ds = new dataStore();
	public dataStore__game ds_game = new dataStore__game();
	public Window w;
	
	public Game(GameState gs, TicTacToeController tictactoe, int player1,
			SavingInFile sFile) throws InterruptedException{
        this.gs = gs;        
        this.tictactoe = tictactoe;
        this.player1 = player1;
        this.sFile = sFile;
        this.w=new Window();
        
        Thread cam = new Thread() {
        	@Override
        	public void run() {
        		methodCall_game call = new methodCall_game("sending game to camera");
        		
                try {
					ds_game.getMethodCalls().put(call);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                Game g=null;
				try {
					g = call.getResult();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		try {
					new camera(g);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        };
        cam.start();
        methodCall_game game_send = ds_game.getMethodCalls().take();
        game_send.setResult(this);
        
        MethodCall call = new MethodCall("opening camera");
        ds.getMethodCalls().put(call);
        Game.c = call.getResult();
        if(c==null) {
        	System.out.println("c is null");
        }
        else {
        	System.out.println("c is not null");
        }
        Thread.sleep(12000);
        }
	
	public void run (Random rnd, int game,int PlayerTurn) throws Exception{
		int playerTurn = PlayerTurn;			
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
		System.out.println("in game cycle");
		
		//Window w= new Window(gs);
		int playermove=20;
		int move;
		int numberOfMoves;
		if (game == 1 && gs.plyCount==6){
			int a = 0;
			a++;
		}
		if (playerTurn == Constants.CPU){
			numberOfMoves = gs.getNumberOfMoves();
			//int actions [] =w.setInputs();
			playermove=20;
			Thread.sleep(100);
				 while(true) {
					 playermove=20;
					 while(playermove==20) {
					 playermove=c.output();
					 
					 }
					 playermove-=1;
					 
					 if(playermove<10) {
						 System.out.println(playermove);
						// System.out.println("move in array "+ gs.gameToArray()[playermove]);
						 if(gs.gameToArray()[playermove]=="-") {
						 
								gs.makeMove(inputConverter(playermove));
								break;
						 }
						 else {
							 System.out.println("Already value here");
							 System.out.println(gs.gameToString());
						 }
								}
					 }
		}
				
				 
			
//			}else {
				 
//				System.out.println("Already value here!!!");
//			}
//				}
//				else
//				{
//					System.out.println("Value too big or too small");
//				}
				
				 
			
			
		else if (playerTurn == Constants.MCTS){
			/*move = rnd.nextInt(numberOfMoves);
			gs.makeMove(move);	
			*/
			move = tictactoe.getAction(gs, player1, game);
			gs.makeMove(move);
		}
	else{
		System.out.println("enjoy");
	}
		
		w.refresh(gs);	
		System.out.println(gs.gameToString());
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
	public dataStore getds() {
		return ds;
	}
	public dataStore__game getds_game() {
		return ds_game;
	}
	public int inputConverter(int a) {
		
	int ar [] = new int[9];
	int count=0;
	for( int i=0;i<gs.gameToArray().length;i++) {
		if(gs.gameToArray()[i]=="-") {
			ar[i]=count;
			count++;
		}
			
		}
		return ar[a];
	}
	
}
