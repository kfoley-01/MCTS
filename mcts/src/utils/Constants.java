package utils;

import java.util.Random;

public interface Constants {
	Random rnd = new Random();//= new Random();
	//rnd = rnd.setSeed(0);
	int PLAYER1 = 1,
		PLAYER2 = 2;
	
	final int MCTS = 1,
	 	CPU = 2;
	
	final int ROOT = 100;
     
	void setSeed(Random rnd);
	
}
