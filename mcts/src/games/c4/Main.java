package games.c4;

import java.util.Arrays;
import java.util.Random;
import utils.SavingInFile;

public class Main  {
	//872 LOST!
	static Random rnd = new Random();
	final static int PLAYER1 = games.tictactoe.Constants.MCTS; //Constants.CPU;
	 static int wins = 0;
	 static int draws = 0;
	 static int loss = 0;
	 static int totalGames = 900,
	            trails_C = 1;
	
	static double minValueC = 0.0,
				  maxValueC = 2.0;
	
	static  SavingInFile sFile;
	
	public Main(){}
	
	public static void main (String [] args) throws Exception{
		//double maxValueC = 3.0;
		double interval = maxValueC / trails_C;
		//double minValueC = 0.0;
		String fName = "data_";
		System.out.println("Starting the game!!!");
		//sFile = new SavingInFile();
		for (int j = 0; j < trails_C; j ++){
			
			minValueC += interval;
			wins = 0; draws = 0; loss = 0;
			fName += "trail" + minValueC + ".dat";
			sFile = new SavingInFile(fName);
			sFile.saveString("%Game  \t Remaining Moves");
			for (int i = 0; i < totalGames; i++){
				rnd.setSeed(i);				
				Connect4Controller ticTacToe = new MCTSConnect4(rnd, minValueC);
				run(ticTacToe, i);
			}
		}
		sFile.close_files();		
	}
	
	public static void run(Connect4Controller ticTacToe, int i) throws Exception{
		GameState gs = new GameState(true);
		//GameState gs = new GameState();
		Game game = new Game(gs, ticTacToe, PLAYER1, sFile);
		game.run(rnd, i);
		String result = Arrays.toString(gs.getOutcomes());
		if (PLAYER1 == games.tictactoe.Constants.CPU){
		if (result.contains(", WIN]")){//we won
			wins++;
		}else if (result.contains("[DRAW,")){
			draws++;
		}else if (result.contains("[WIN,")){
			loss++;
			System.out.println("Game (" + i + ") lost ");
			System.exit(0);
		}
		}else if (PLAYER1 == games.tictactoe.Constants.MCTS){
			if (result.contains("[WIN,")){//we won
				wins++;
			}else if (result.contains("[DRAW,")){
				draws++;
			}else if (result.contains("[LOSS,")){
				loss++;
				System.out.println("Game (" + i + ") lost ");				
			}
		}		
		//if (i == totalGames-1)
		{
			if (loss == 0){
				System.out.println("Game("+i+") ***WON ALL GAMES!*** C("+minValueC+") Won: " + wins + " Loss: " + loss + " Draw: "+ draws + " totalGames("+ totalGames+")");
			}else{
				System.out.println("C("+minValueC+") Won: " + wins + " Loss: " + loss + " Draw: "+ draws + " totalGames("+ totalGames+")");
			}
		}	
	}

	
	
	
}
