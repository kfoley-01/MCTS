package mcts.connect4;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;  

public class MainConnect4 {
	
	public static double iterations=0;
	
	public static void main(String [] args) throws CloneNotSupportedException
	{
	
		try {
			vsRandomStats(10);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
//		connect4 a4 = new connect4();
//		a4.gameLoopVsPlayer(1);
		
		
	
	}
	static void vsRandomStats(long time) throws IOException {
		int win = 0;
		int loss=0;
		int draw =0;
		double games=2000;
	
		
		
		File results = new File("results "+time+"ms2");
		FileWriter stats = new FileWriter("results "+time+"ms2");
	
		
		
		for(int x=1;x<=2;x++) {
		
		for ( int i =0;i<games;i++) {
		
			connect4 c4 = new connect4();
			int res = c4.gameLoopVsRandom(x,time);
				if(res ==1) {
					win ++;
		}
				else if(res == -1 ) {
					loss++;
					break;
					
					
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
