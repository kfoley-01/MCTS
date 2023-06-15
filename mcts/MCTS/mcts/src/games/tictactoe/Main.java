package games.tictactoe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

import utils.SavingInFile;


public class Main  {
	
	static Random rnd = new Random();
	
	 static int wins = 0;
	 static int draws = 0;
	 static int loss = 0;
	 static int totalGames = 1,
	            trails_C = 1;
	
	static double minValueC = 0.0,
				  maxValueC = 2.0;
	
	static  SavingInFile sFile;
	
	
	

	
	public Main(){}
	
	public static void main (String [] args) throws Exception{
		
		System.out.println("Play=0\nSim1");
		//double maxValueC = 3.0;
		double interval = maxValueC / trails_C;
		//double minValueC = 0.0;
		String fName = "data_";
		System.out.println("Starting the game!!!");
		//sFile = new SavingInFile();
			
			minValueC += interval;
			wins = 0; draws = 0; loss = 0;
			fName += "trail" + minValueC + ".dat";
			sFile = new SavingInFile(fName);
			sFile.saveString("%Game  \t Remaining Moves");			
				TicTacToeController ticTacToe = new MCTSTicTacToe(rnd, minValueC);
				run(ticTacToe, 10);
		
		
		sFile.close_files();
		/*System.out.println("Number of won games: " + wins + " / " + totalGames);
		System.out.println("Number of draw games: " + draws + " / " + totalGames);
		System.out.println("Number of lost games: " + loss + " / " + totalGames);*/
		//MCTSTicTacToe ticTacToe = new MCTSTicTacToe();
	}
	
	public static void run(TicTacToeController ticTacToe, int i) throws Exception{
		GameState gs = new GameState(true);
		Scanner sc = new Scanner(System.in);
		int PLAYER1;
		System.out.println("1 for MCTS first 2 for player first");
		while(true) {
		 PLAYER1 = sc.nextInt(); //Constants.CPU;
		 if(PLAYER1==1||PLAYER1==2) {
			 break;
		 }
		}
		Game game = new Game(gs, ticTacToe, PLAYER1, sFile);
		game.run(rnd, i,PLAYER1);
		String result = Arrays.toString(gs.getOutcomes());
		if (PLAYER1 == Constants.CPU){
		if (result.contains(", WIN]")){//we won
			wins++;
			System.out.println("MCTS wins");
		}else if (result.contains("[DRAW,")){
			System.out.println("DRAW");
			draws++;
		}else if (result.contains("[WIN,")){
			loss++;
			System.out.println("player wins");;
		}
		}else if (PLAYER1 == Constants.MCTS){
			if (result.contains("[WIN,")){//we won
				wins++;
				System.out.println("MCTS wins");
			}else if (result.contains("[DRAW,")){
				draws++;
				System.out.println("DRAW");
			}else if (result.contains("[LOSS,")){
				loss++;
				System.out.println("player wins");
				//System.exit(0);
			}
		}
		System.out.println(gs.gameToString());
		System.out.println("Y to play again N to close");
		String ans;
		while(true) {
			ans=sc.nextLine();
			ans=ans.toUpperCase();
			if(ans.equals("Y")) {
				run(ticTacToe, 10);
				break;
			}
			else if(ans.equals("N")) {
				System.exit(0);
			}
			else {
				System.out.println("non valid input");
			}
		}
		
		//System.out.println(Arrays.toString(gs.getOutcomes()));
		//if (i == totalGames-1)
//		{
//			if (loss == 0){
//				System.out.println("Game("+i+") ***WON ALL GAMES!*** C("+minValueC+") Won: " + wins + " Loss: " + loss + " Draw: "+ draws + " totalGames("+ totalGames+")");
//			}else{
//				System.out.println("C("+minValueC+") Won: " + wins + " Loss: " + loss + " Draw: "+ draws + " totalGames("+ totalGames+")");
//			}
//		}
	
		//System.out.println("Number of won games: " + wins + " / " + totalGames);
		//System.out.println("Number of draw games: " + draws + " / " + totalGames);
		//System.out.println("Number of lost games: " + loss + " / " + totalGames);
	}

	
	
	
}
