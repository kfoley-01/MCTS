package mcts.tictactoe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MainTicTacToe {
	public static double iterations=0;
	
	
	public static void main(String [] args) throws InterruptedException {
		tictactoe tcc = new tictactoe();
		tcc.gameLoopVsCamera(2);
//		//tcc.testCamera();
//		tcc.gameLoopVsPlayer(1);
//		//vsRandomStats(2);
		
//		try {
//			vsRandomStats(1);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	
	static void vsRandomStats(int time ) throws IOException {
		int win = 0;
		int loss=0;
		int draw =0;
		double games=2000;
	
		
		
		File results = new File("results "+time+"ms2");
		FileWriter stats = new FileWriter("results "+time+"ms");
	
		
		
		for(int x=1;x<=2;x++) {
		
		for ( int i =0;i<games;i++) {
		
			tictactoe tcc = new tictactoe();
			int res = tcc.gameLoopVsRandom(x,time);
				if(res ==1) {
					win ++;
		}
				else if(res == -1 ) {
					loss++;
					
					
				}
				else if (res==0) {
					draw++;
					
					
				}
				System.out.println("run : "+ i );
				
		}
		System.out.println("Mcts won : "+ win+"\n Lost : "+loss+"\n Drew : "+draw);
		
		stats.write("Mcts won : "+ win+"\n Lost : "+loss+"\n Drew : "+draw+" when playing as "+x+"\n");
		stats.write("Win percentage is : "+(double)(win/games));
		stats.write("\n");
		stats.write("with average iterations of "+iterations/games);
		stats.write("\n");
		win=0;
		loss=0;draw=0;
		iterations=0;
		}
stats.close();
}
}
