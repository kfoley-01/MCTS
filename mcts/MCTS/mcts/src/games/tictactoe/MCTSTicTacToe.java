package games.tictactoe;

import java.util.ArrayList;
import java.util.Random;

public class MCTSTicTacToe implements TicTacToeController{

	//int player;
	public int trails = 10000;
	Random rnd;
	double C;
	//public MCTSTicTacToe() {
	//}
	
	public MCTSTicTacToe(Random rnd, double C){
		this.rnd = rnd;
		this.C = C;
	}
	
	@Override
	public int getAction(GameStateInterface gs, int player1, int game){
		TreeNodeTicTacToe tn = new TreeNodeTicTacToe(rnd, player1, C);
		TreeView tv = new TreeView(tn);
		GameState gs2 = (GameState)gs.copy();
		for (int i = 0; i < trails; i++){			
			//System.out.println("Trail: "+ i);
			if (i == 32){
				int a = 0;
				a++;
			}
			if ( (gs2.plyCount==4) && (game == 0) && (i==0)){			
				int a = 0;
				a++;				
				//tv.showTree("After " + trails+ " play outs");
			}
			tn.selectAction(gs,  player1);	
			if (i == 200){
				//tv.showTree("After " + trails+ " play outs");
			}
			
		}
		//tv.showTree("After " + trails+ " play outs");
		if ((gs2.plyCount == 4) && (game == 0))
		{
			int a = 0;
			a++;
			//tv.showTree("After " + trails+ " play outs");
		}
        //tv.showTree("After " + trails + " play outs");
        //System.out.println("Done");
		double maxValue = -1000000;//Double.MIN_VALUE;
        int dir = -1 ;
        ArrayList<TreeNodeTicTacToe> equallyGood = new ArrayList<TreeNodeTicTacToe>();
        for (int i = 0; i < tn.children.length; i++){
        	if (tn.children[i].totValue > maxValue){
        		maxValue = tn.children[i].totValue;
        		dir = tn.children[i].direction;
        	}else if (tn.children[i].totValue == maxValue){
        		equallyGood.add(tn.children[i]);
        	}
        }
        
        return dir;
	}

	


}
